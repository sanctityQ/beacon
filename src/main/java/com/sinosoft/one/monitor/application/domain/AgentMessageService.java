package com.sinosoft.one.monitor.application.domain;


/**
 * Agent端消息服务类
 * User: carvin
 * Date: 13-3-3
 * Time: 下午11:46
 */
public interface AgentMessageService {
	/**
	 * 处理代理端信息
	 * @param applicationId 应用系统ID
	 * @param data 信息数据
	 */
	public void handleMessage(String applicationId, String data);

}
