package com.fusionspy.beacon.report;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@DirtiesContext
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class ReportScheduleTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private  ReportSchedule reportSchedule;


    @Test
    public void statistic(){

        reportSchedule.statistic();

    }

}
