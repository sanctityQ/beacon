package com.sinosoft.one.monitor.common;

import java.math.BigDecimal;

/**
 * 告警消息
 * User: carvin
 * Date: 13-3-1
 * Time: 下午2:34
 *
 */
public final class AlarmAttribute {
	private String attributeName;
	private String attributeValue;
	private AlarmAttribute() {

	}

	public static AlarmAttribute valueOf(AttributeName attributeName, String attributeValue) {
		AlarmAttribute alarmAttribute = new AlarmAttribute();
		alarmAttribute.attributeName = attributeName.name();
		alarmAttribute.attributeValue = attributeValue;
		return alarmAttribute;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public boolean isAvailabilityAlarm() {
		return AttributeName.Availability.name().equals(this.attributeName);
	}

	public boolean isExceptionAlarm() {
		return AttributeName.Exception.name().equals(this.attributeName);
	}
}
