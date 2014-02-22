package com.fusionspy.beacon.common;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 告警消息事件处理类.
 * User: carvin
 * Date: 13-3-2
 * Time: 上午10:14
 */
public class MessageBaseEventHandler implements EventHandler<MessageBaseEvent> {
	private AlarmMessageHandler alarmMessageHandler;
	private Logger logger = LoggerFactory.getLogger(MessageBaseEventHandler.class);
	@Override
	public void onEvent(MessageBaseEvent event, long sequence, boolean endOfBatch) throws Exception {
		try {
			alarmMessageHandler.doMessage(event.getMessageBase(), event.getAlarmId());
		} catch (Throwable throwable) {
			logger.error("handler MessageBaseEvent exception.", throwable);
		}
	}

	public void setAlarmMessageHandler(AlarmMessageHandler alarmMessageHandler) {
		this.alarmMessageHandler = alarmMessageHandler;
	}
}
