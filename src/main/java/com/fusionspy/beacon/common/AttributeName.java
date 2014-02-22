package com.fusionspy.beacon.common;

/**
 * 属性名称枚举类.
 * Time: 上午11:37
 */
public enum AttributeName {
	ResponseTime,
	Exception,
	Availability,
	Health,
	CPUUtilization,
	DiskUtilization,
	PhysicalMemoryUtilization,
	SwapMemoryUtilization,
	ActiveConnection,
	BufferHitRatio,
    //tux
    ServerDied,ServerNoTrans,ServerBusy,UsedMemory,QueuedNumber,SystemStop,
    //Weblogic
    FreeHeap,ThreadUtilization,JdbcUtilization
}
