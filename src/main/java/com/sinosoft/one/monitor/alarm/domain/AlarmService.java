package com.sinosoft.one.monitor.alarm.domain;

import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.common.AlarmSource;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesService;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 处理告警信息业务逻辑类
 * User: carvin
 * Date: 13-3-1
 * Time: 上午10:33
 */
@Service
public class AlarmService {

    @Autowired
	private AlarmRepository alarmRepository;

    @Autowired
    private ResourcesService resourcesService;


	public void saveAlarm(Alarm alarm) {
		alarmRepository.save(alarm);
	}

    public Alarm findAlarm(String alarmId){
        return alarmRepository.findOne(alarmId);
    }

    /**
     * 获取最近的50条记录，按记录时间排序
     * @return
     */
    public List<Alarm> queryLatestAlarmsRows(int rowNo){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"));
        PageRequest pageRequest = new PageRequest(0,rowNo,sort);
        List<Alarm> alarms = this.queryAlarmsByPage(pageRequest).getContent();
        return alarms;
    }

    public List<Alarm> queryLatestAlarms(String monitorId,int size) {
        Page<Alarm> alarmPage = alarmRepository.selectAlarmsByMonitorId(new PageRequest(0, size), monitorId);
        return alarmPage.getContent();
    }


	public Alarm queryLatestAlarm(String monitorId) {
		Page<Alarm> alarmPage = alarmRepository.selectAlarmsByMonitorId(new PageRequest(0, 1), monitorId);
		if(alarmPage.hasContent()) {
			return alarmPage.iterator().next();
		}
		return null;
    }

    public Page<Alarm> queryAlarmsByPage(PageRequest pageRequest) {
        Assert.notNull(pageRequest);
        Page<Alarm> page = alarmRepository.findAll(pageRequest);
        return  page;
    }

    public Page<Alarm> queryAlarmsByPageAndSeverityLevel(PageRequest pageRequest,SeverityLevel severityLevel){
        Assert.notNull(pageRequest);
        Assert.notNull(severityLevel);
        return alarmRepository.findBySeverity(severityLevel,pageRequest);
    }

}
