package com.sinosoft.one.monitor.application.domain;

import com.alibaba.fastjson.JSON;
import com.sinosoft.one.monitor.application.model.ExceptionInfo;
import com.sinosoft.one.monitor.application.repository.ExceptionInfoRepository;
import com.sinosoft.one.monitor.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 异常代理端消息处理服务类.
 * User: carvin
 * Date: 13-3-4
 * Time: 下午5:06
 */
@Service("exceptionAgentMessageService")
public class ExceptionAgentMessageService implements AgentMessageService{
	@Autowired
	private ExceptionInfoRepository exceptionInfoRepository;
	@Autowired
	private AlarmMessageBuilder alarmMessageBuilder;

	/**
	 * 处理代理端异常消息
	 * @param applicationId 应用系统ID
	 * @param data 异常数据
	 */
	public void handleMessage(String applicationId, String data) {
		ExceptionInfo exceptionInfo = JSON.parseObject(data, ExceptionInfo.class);
		exceptionInfo.setApplicationId(applicationId);
		alarmMessageBuilder.newMessageBase(applicationId)
				.alarmSource(AlarmSource.EXCEPTION)
				.addAlarmAttribute(AttributeName.Exception, "0")
				.subResourceId(exceptionInfo.getUrlId())
				.subResourceType(ResourceType.APPLICATION_SCENARIO_URL)
				.alarmId(exceptionInfo.getAlarmId())
				.alarm();
		exceptionInfoRepository.save(exceptionInfo);
	}
}
