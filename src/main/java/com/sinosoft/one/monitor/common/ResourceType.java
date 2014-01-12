package com.sinosoft.one.monitor.common;

/**
 * 资源类型枚举类.
 * User: carvin
 * Date: 13-3-1
 * Time: 下午2:43
 */
public enum ResourceType {

    Tuxedo("Tuxedo"),
    WEBLOGIC("WEBLOGIC");

	private String _cnName;

	private ResourceType(String cnName) {
		this._cnName = cnName;
	}

	public String getDescription() {
		return _cnName;
	}

}
