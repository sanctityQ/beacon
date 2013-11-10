package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorDataRepository;
import com.fusionspy.beacon.site.wls.entity.*;
import com.google.common.collect.MapMaker;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-21
 * Time: 上午12:07
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class WlsDataSimulationRepository implements MonitorDataRepository {

    // private HashMap
    private ConcurrentMap<String, InTimeData> last = new MapMaker().concurrencyLevel(32).makeMap();

    @Override
    public InitData getInitData(String siteName, String ip, int port) {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("site/WlsInitDemoResp.xml");
            SAXReader xmlReader = new SAXReader();
            Document xmlDocument = (Document) xmlReader.read(in);
            String resp = xmlDocument.asXML();
            JaxbBinder jaxbBinder2 = new JaxbBinder(WlsIniData.class);
            WlsIniData initData = jaxbBinder2.fromXml(resp);
            initData.getWlsSysrec().setAdminServerName(siteName);
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
            WlsInTimeData inTimeData = jaxbBinder2.fromXml(resp);

            WlsResource resource = inTimeData.getResource();
            String cpu = resource.getCpu();
            cpu = (Integer.parseInt(cpu) + new Random().nextInt(60)) + ""; //初始值为10  范围为10-70变动
            resource.setCpu(cpu); //将随机数重新设置，模拟动态效果
            String mem = resource.getMem();
            String mem_use = mem.split("M")[0];
            String mem_sum = mem.split("M")[1];
            mem = (Integer.parseInt(mem_use) + new Random().nextInt(500)) + "M" + mem_sum;
            resource.setMem(mem);

            for(WlsJvm jvm : inTimeData.getJvmRuntimes()) {
                int freePercent = Integer.parseInt(jvm.getFreePercent()) + new Random().nextInt(40);
                jvm.setFreePercent(freePercent+"");
            }
            for(WlsThread thread : inTimeData.getThreadPoolRuntimes()) {
                int thoughput = thread.getThoughput() + new Random().nextInt(5);
                thread.setThoughput(thoughput);
                thread.setIdleCount(new Random().nextInt(8));
                thread.setStandbyCount(new Random().nextInt(8));
            }
            return inTimeData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void stopSite(String siteName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
