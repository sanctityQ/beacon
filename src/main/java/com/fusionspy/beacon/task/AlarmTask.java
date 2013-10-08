package com.fusionspy.beacon.task;

import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component(value = "alarmTask")
class AlarmTask {

    @Autowired
    private AlarmRepository alarmRepository;

    /**
     * 删除一个月以前的数据，alarm表数据最多只保留30天。
     */
    @Transactional
    public void execute(){
        DateTime start = DateTime.now().minusMonths(1);
        alarmRepository.delete(alarmRepository.findByCreateTimeBefore(start.toDate()));
    }
}
