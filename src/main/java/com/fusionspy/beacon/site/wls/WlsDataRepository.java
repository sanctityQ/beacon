package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.*;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.URL;


@Component(value = "wlsDataRepository")
public class WlsDataRepository implements MonitorDataRepository<WlsSite> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    /** 处理初始化报文数据binder*/
    private JaxbBinder iniBinder = new JaxbBinder(WlsIniData.class);
    /** 处理实时报文数据binder*/
    private JaxbBinder inTimeBinder = new JaxbBinder(WlsInTimeData.class);
    @Autowired
    private WlsService wlsService;
    @Autowired
    private Connect connect;

    private Document xmlDocument;

    @Override
    public WlsIniData getInitData(WlsSite wlsSite) {

        Element node = (Element) xmlDocument.selectSingleNode("/wlsagent/Domain");
        node.attribute("adminAddress").setData(wlsSite.getWebLogicIp());  //Weblogic监听地址
        node.attribute("adminPort").setData(wlsSite.getWebLogicPort()); //Weblogic监听端口
        node.attribute("name").setData(wlsSite.getDomainName());
        node.attribute("principal").setData(wlsSite.getUserName()); //Weblogic用户名
        node.attribute("password").setData(wlsSite.getPassword()); //密码
        String initXml = connect.startSiteThread( wlsSite.getSiteName(), wlsSite.getSiteIp(), wlsSite.getSitePort(),
                xmlDocument.asXML());
      //  logger.debug("siteName:{} get initXml Xml: {}", siteName,initXml);
        WlsIniData initData = iniBinder.fromXml(initXml);
        initData.setWlsService(wlsService);
        initData.setSiteName(wlsSite.getSiteName());
        return initData.defaultData();
    }

    @PostConstruct
    void initDocument(){
        URL initXmlReq =Connect.class.getClassLoader().getResource("site/WlsInitReq.xml");
        SAXReader xmlReader = new SAXReader();
        try {
            xmlDocument = xmlReader.read(initXmlReq);
        } catch (DocumentException e) {
            throw new RuntimeException("无法加载site/WlsInitReq.xml",e);
        }
    }

    /**
     * 获取实时监控数据
     * @param siteName
     * @return
     */
    @Override
    public InTimeData getInTimeData(String siteName) {
        String inTimeXml = connect.getInTimeData(siteName);
       // logger.debug("get inTime Xml: {}", inTimeXml);
        WlsInTimeData inTimeData = inTimeBinder.fromXml(inTimeXml);
        return inTimeData.defaultData();
    }

    @Override
    public void stopSite(String siteName) {
        connect.stopSiteThreadByName(siteName);
    }
}
