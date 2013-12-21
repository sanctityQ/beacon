package com.fusionspy.beacon.site.wls.dao;

import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.Connect;
import com.fusionspy.beacon.site.wls.WlsHisData;
import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.sinosoft.one.util.date.DateUtils;
import com.sinosoft.one.util.encode.JaxbBinder;
import com.sinosoft.one.util.test.SpringTxTestCase;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@DirtiesContext
@ContextConfiguration(locations = {"/spring/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class WlsResourceDaoTest extends SpringTxTestCase {

    @Autowired
    private WlsService wlsService;

    @Resource
    private WlsServerDao wlsServerDao;

    @Test
    public void testProcessInitData() {
        String wlsIniData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MONITOR Date=\"2009-10-20 21:12:51\">\n" +
                "   <INITBUF AdminServerName=\"examplesServer\" DomainVersion=\"8.1.6.0\" Name=\"examples\" ActivationTime=\"2009-10-20 21:00:18\" RootDirectory=\".\" ProductionModeEnabled=\"false\" JDKVendor=\"Sun Microsystems Inc.\" JDKVersion=\"1.4.2_11\" OSVersion=\"5.1\" OSName=\"Windows XP\" ServerNum=\"2\" AgentVersion=\"1.0 - FORMAL LICENSE\"/>\n" +
                "</MONITOR>";
        JaxbBinder jaxbBinder = new JaxbBinder(WlsIniData.class);
        WlsIniData initData = jaxbBinder.fromXml(wlsIniData);
        wlsService.processInitData(initData);
    }

    @Test
    public void testProcessIntimeData() throws Exception {
        String wlsIniData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MONITOR Date=\"2009-10-20 21:12:51\">\n" +
                "   <INITBUF AdminServerName=\"examplesServer\" DomainVersion=\"8.1.6.0\" Name=\"examples\" ActivationTime=\"2009-10-20 21:00:18\" RootDirectory=\".\" ProductionModeEnabled=\"false\" JDKVendor=\"Sun Microsystems Inc.\" JDKVersion=\"1.4.2_11\" OSVersion=\"5.1\" OSName=\"Windows XP\" ServerNum=\"2\" AgentVersion=\"1.0 - FORMAL LICENSE\"/>\n" +
                "</MONITOR>";
        JaxbBinder jaxbBinder1 = new JaxbBinder(WlsIniData.class);
        WlsIniData initData = jaxbBinder1.fromXml(wlsIniData);
        WlsHisData hisData = new WlsHisData();
        hisData.setWlsIniData(initData.defaultData());

        InputStream in = Test.class.getClassLoader().getResourceAsStream("a.xml");
        SAXReader xmlReader = new SAXReader();
        Document xmlDocument = (Document) xmlReader.read(in);
        String resp = xmlDocument.asXML();
        JaxbBinder jaxbBinder = new JaxbBinder(WlsInTimeData.class);
        WlsInTimeData inTimeData = jaxbBinder.fromXml(resp);
        wlsService.processInTimeData("", 5, inTimeData.defaultData(), hisData);
    }

    @Test
    public void testSave() {
        WlsServer wlsServer = new WlsServer();
        wlsServer.setSiteName("aaa");
        wlsServer.setListenAddress("aaa");
        wlsServer.setListenPort(11);
        wlsServer.setInterval(30);
        wlsServer.setUserName("aaa");
        wlsServer.setPassword("aaa");
        wlsServer.setIsSsl(0);
        wlsServer.setVersion("aaa");
        wlsServer.setStatus(0);
        wlsServerDao.save(wlsServer);
    }


    @Autowired
    private WlsEjbCacheDao wlsEjbCacheDao;

    @Autowired
    private WlsResourceDao wlsResourceDao;

    @Test
    public void testReport0() {
        Date date = new Date();
        DateTime end = new DateTime(date);
        DateTime start = end.minusDays(1);
        Statistics s = wlsResourceDao.statisticHostCpuUsedByRectimeBetween("115.28.16.154", new Timestamp(start.getMillis()), new Timestamp(end.getMillis()));
        System.out.println(s);
    }

    @Test
    public void testReport() {

        Date date = new Date();
        DateTime endDate = new DateTime(date);
        DateTime startDate = endDate.minusDays(1);
        String start = DateUtils.toFormatString(startDate.toDate(), DateUtils.Formatter.YEAR_TO_SECOND);
        String end = DateUtils.toFormatString(endDate.toDate(), DateUtils.Formatter.YEAR_TO_SECOND);
        Iterable<Statistics> iterable = wlsEjbCacheDao.statisticCacheBeanCurCount("115.28.16.154", start, end);
        System.out.println(iterable);
    }

    @Autowired
    private WlsJvmDao wlsJvmDao;

    @Test
    public void testAA() {
        System.out.println(Double.MAX_VALUE);
        List<Statistics> list = wlsJvmDao.statisticFreeHeap("115.28.16.154", "2013-12-21 14:00:00", "2013-12-21 15:00:00");
        System.out.println(list);
    }
}
