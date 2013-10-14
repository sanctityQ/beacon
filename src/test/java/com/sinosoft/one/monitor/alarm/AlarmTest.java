package com.sinosoft.one.monitor.alarm;

import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext-*.xml")
@TransactionConfiguration
@Transactional
public class AlarmTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected AlarmRepository alarmRepository = null;


    @Test
    public  void testFindByCreateTimeBetweenAndMonitorType(){
        Date startTime =null, endTime =null;
        String resourceType = ResourceType.APP_SERVER.name();
        Pageable pageable  = new PageRequest(1,10);
        alarmRepository.findByCreateTimeBetweenAndMonitorType(startTime,endTime,resourceType,pageable);
    }
}
