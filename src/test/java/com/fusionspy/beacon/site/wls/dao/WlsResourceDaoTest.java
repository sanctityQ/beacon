package com.fusionspy.beacon.site.wls.dao;

import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.sinosoft.one.util.encode.JaxbBinder;
import com.sinosoft.one.util.test.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

@DirtiesContext
@ContextConfiguration(locations = {"/spring/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class WlsResourceDaoTest extends SpringTxTestCase {

    @Autowired
    private WlsService wlsService;

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
}