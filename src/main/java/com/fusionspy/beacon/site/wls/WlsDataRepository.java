package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.Connect;
import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorDataRepository;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-21
 * Time: 上午12:06
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class WlsDataRepository implements MonitorDataRepository {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    /** 处理初始化报文数据binder*/
    private JaxbBinder iniBinder = new JaxbBinder(WlsIniData.class);
    /** 处理实时报文数据binder*/
    private JaxbBinder inTimeBinder = new JaxbBinder(WlsInTimeData.class);
    @Autowired
    private WlsService wlsService;
    @Autowired
    private Connect connect;

    /**
     * 获取初始化监控数据
     * @param siteName
     * @param ip
     * @param port
     * @return
     */
    @Override
    public InitData getInitData(String siteName, String ip, int port) {
        //TODO 修改第一个参数：初始化数据请求报文资源文件路径
        URL initXmlReq =Connect.class.getClassLoader().getResource("site/WlsInitReq.xml");
        SAXReader xmlReader = new SAXReader();
        Document xmlDocument = null;
        try {
            xmlDocument = (Document) xmlReader.read(initXmlReq);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element node = (Element) xmlDocument.selectSingleNode("/wlsagent/Domain");
        WlsServer wlsServer = wlsService.getSite(siteName);
        //TODO domain名称是否需要配置，待确认？  用户名和密码是否使用站点的？
        node.attribute("adminAddress").setData(wlsServer.getWeblogicIp());  //Weblogic监听地址
        node.attribute("adminPort").setData(wlsServer.getWeblogicPort()); //Weblogic监听端口
        node.attribute("principal").setData(wlsServer.getUserName()); //Weblogic用户名
        node.attribute("password").setData(wlsServer.getPassword()); //密码
        System.out.println(xmlDocument.asXML());
        String initXml = connect.startSiteThread(xmlDocument, siteName, ip, port, 0);
        logger.debug("get initXml Xml: {}", initXml);
        WlsIniData initData = iniBinder.fromXml(initXml);
        return initData.defaultData();
    }

    /**
     * 获取实时监控数据
     * @param siteName
     * @return
     */
    @Override
    public InTimeData getInTimeData(String siteName) {
        String inTimeXml = connect.getInTimeData(siteName);
        logger.debug("get inTime Xml: {}", inTimeXml);
        WlsInTimeData inTimeData = inTimeBinder.fromXml(inTimeXml);
        return inTimeData.defaultData();
    }

    @Override
    public void stopSite(String siteName) {
        connect.stopSiteThreadByName(siteName);
    }
}
