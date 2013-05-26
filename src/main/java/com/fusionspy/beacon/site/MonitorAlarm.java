package com.fusionspy.beacon.site;

import java.util.List;

/**
 * monitor alarm
 * User: qc
 * Date: 11-9-21
 * Time: 下午4:06
 */
public interface MonitorAlarm {
      void alarm(List<String> to, String message);
}
