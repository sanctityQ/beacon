package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorDataRepository;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-21
 * Time: 上午12:07
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class WlsDataSimulationRepository implements MonitorDataRepository {
    @Override
    public InitData getInitData(String siteName, String ip, int port) {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("site/WlsInitDemoResp.xml");
            SAXReader xmlReader = new SAXReader();
            Document xmlDocument = (Document) xmlReader.read(in);
            String resp = xmlDocument.asXML();
            JaxbBinder jaxbBinder2 = new JaxbBinder(WlsIniData.class);
            InitData initData = jaxbBinder2.fromXml(resp);
            return initData;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public InTimeData getInTimeData(String siteName) {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("site/WlsInTimeDemoResp.xml");
            SAXReader xmlReader = new SAXReader();
            Document xmlDocument = (Document) xmlReader.read(in);
            String resp = xmlDocument.asXML();
            JaxbBinder jaxbBinder2 = new JaxbBinder(WlsInTimeData.class);
            return jaxbBinder2.fromXml(resp);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void stopSite(String siteName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
