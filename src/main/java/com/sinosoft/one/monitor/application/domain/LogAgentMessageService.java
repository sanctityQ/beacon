package com.sinosoft.one.monitor.application.domain;

import com.alibaba.fastjson.JSON;
import com.sinosoft.one.monitor.application.model.*;
import com.sinosoft.one.monitor.application.repository.MethodResponseTimeRepository;
import com.sinosoft.one.monitor.application.repository.UrlTraceLogRepository;
import com.sinosoft.one.monitor.application.repository.UrlVisitsStaRepository;
import com.sinosoft.one.monitor.common.*;
import com.sinosoft.one.monitor.utils.DateUtil;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 日志代理端消息处理服务类.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
@Service("logAgentMessageService")
public class LogAgentMessageService implements AgentMessageService {
	@Autowired
	private UrlTraceLogRepository urlTraceLogRepository;
	@Autowired
	private UrlVisitsStaRepository urlVisitsStaRepository;
	@Autowired
	private AlarmMessageBuilder alarmMessageBuilder;
	@Autowired
	private MethodResponseTimeRepository methodResponseTimeRepository;

	/**
	 * 处理代理端日志消息
	 * @param applicationId 应用系统ID
	 * @param data 日志数据
	 */
	@Transactional
	public void handleMessage(String applicationId, String data) {
		UrlTraceLog urlTraceLog = JSON.parseObject(data, UrlTraceLog.class);
		urlTraceLog.setApplicationId(applicationId);
		if(!urlTraceLog.getHasException()) {
			alarmMessageBuilder.newMessageBase(applicationId)
					.alarmSource(AlarmSource.LOG)
					.subResourceType(ResourceType.APPLICATION_SCENARIO_URL)
					.subResourceId(urlTraceLog.getUrlId())
					.addAlarmAttribute(AttributeName.ResponseTime, urlTraceLog.getConsumeTime() + "")
					.alarmId(urlTraceLog.getAlarmId())
					.alarm();
		}
		urlTraceLog.setRecordTime(new Date());
		urlTraceLogRepository.save(urlTraceLog);
		Date currentHourDate = DateUtil.getHoursDate(urlTraceLog.getBeginTime());
		UrlVisitsSta urlVisitsSta = urlVisitsStaRepository.findByUrlIdAndRecordTime(urlTraceLog.getUrlId(), currentHourDate);
		if(urlVisitsSta != null) {
			urlVisitsSta.increaseVisitNumber();
		} else {
			urlVisitsSta = new UrlVisitsSta();
			urlVisitsSta.setApplicationId(applicationId);
			urlVisitsSta.setRecordTime(DateUtil.getHoursDate(urlTraceLog.getBeginTime()));
			urlVisitsSta.setUrlId(urlTraceLog.getUrlId());
			urlVisitsSta.setVisitNumber(1);
		}

		urlVisitsStaRepository.save(urlVisitsSta);

		// 处理方法响应时间统计
		handleMethodResponseTime(applicationId, urlTraceLog.getUrlId(), urlTraceLog.getMethodTraceLogList());
	}

	private void handleMethodResponseTime(String applicationId, String urlId, List<MethodTraceLog> methodTraceLogList) {
		List<String> methodIds = new ArrayList<String>();
		Map<String, MethodTraceLog> methodMap = new HashMap<String, MethodTraceLog>();
		for(MethodTraceLog methodTraceLog : methodTraceLogList) {
			methodIds.add(methodTraceLog.getMethodId());
			methodMap.put(methodTraceLog.getMethodId(), methodTraceLog);
		}
		List<MethodResponseTime> methodResponseTimes = methodResponseTimeRepository.selectMethodResponseTimes(applicationId,
				urlId, methodIds, LocalDateTime.now().toString("yyyy-MM-dd HH"));

		List<MethodResponseTime> toUpdateMethodResponseTimes = new ArrayList<MethodResponseTime>();
		Date currentDate = new Date();
		if(methodResponseTimes != null && methodResponseTimes.size() > 0) {

			for(MethodResponseTime methodResponseTime : methodResponseTimes) {
				MethodTraceLog methodTraceLog = methodMap.get(methodResponseTime.getMethodId());
				if(methodTraceLog != null) {
					long responseTime = methodTraceLog.getConsumeTime();
					if(methodResponseTime.getMinResponseTime() > responseTime) {
						methodResponseTime.setMinResponseTime(responseTime);
					} else if(methodResponseTime.getMaxResponseTime() < responseTime) {
						methodResponseTime.setMaxResponseTime(responseTime);
					}

					methodResponseTime.addTotalResponseTime(responseTime);
					methodResponseTime.setApplicationId(applicationId);
					methodResponseTime.setUrlId(urlId);
					methodResponseTime.setMethodId(methodTraceLog.getMethodId());
					methodResponseTime.setRecordTime(currentDate);
					methodResponseTime.increaseTotalCount();
					toUpdateMethodResponseTimes.add(methodResponseTime);
				}
			}
		} else {
			for(MethodTraceLog methodTraceLog : methodTraceLogList) {
				long responseTime = methodTraceLog.getConsumeTime();
				MethodResponseTime methodResponseTime = new MethodResponseTime();
				methodResponseTime.setApplicationId(applicationId);
				methodResponseTime.setUrlId(urlId);
				methodResponseTime.setMethodId(methodTraceLog.getMethodId());
				methodResponseTime.setRecordTime(currentDate);
				methodResponseTime.setMethodName(methodTraceLog.getFullMethodName());
				methodResponseTime.setMinResponseTime(responseTime);
				methodResponseTime.setMaxResponseTime(responseTime);
				methodResponseTime.setTotalResponseTime(responseTime);
				methodResponseTime.increaseTotalCount();
				toUpdateMethodResponseTimes.add(methodResponseTime);
			}
		}
		methodResponseTimeRepository.save(toUpdateMethodResponseTimes);
	}
}
