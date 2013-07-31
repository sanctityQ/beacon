package com.fusionspy.beacon.site.tux.dao;

import com.fusionspy.beacon.report.Statistics;
import com.sinosoft.one.util.test.SpringTxTestCase;
import junit.framework.Assert;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Timestamp;

@DirtiesContext
@ContextConfiguration(locations = {"/spring/applicationContext.xml","/spring/applicationContext-web.xml"})
public class TuxResourceDaoTest extends SpringTxTestCase {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private  TuxResourceDao dao;

    @Test
    public void findByRectimeBetweenTest(){
        Statistics  statisticses = dao.statisticHostCpuUsedByRectimeBetween("1", new Timestamp(dateTimeFormatter.parseDateTime("2012-06-24 22:00:00").toDate().getTime()),
                new Timestamp(dateTimeFormatter.parseDateTime("2012-06-25 22:30:00").toDate().getTime())
        );

        Assert.assertEquals(93.8235d,statisticses.getAvg());
    }
}
