package com.fusionspy.beacon.site.wls;


import com.fusionspy.beacon.site.MonitorData;
import com.fusionspy.beacon.site.wls.entity.WlsError;

import javax.xml.bind.annotation.XmlElement;

public class WlsMonitorData extends MonitorData{

  private WlsError wlsError;

  @XmlElement(name = "SYSTEM")
  public WlsError getWlsError() {
        return wlsError;
  }

  public void setWlsError(WlsError wlsError) {
        this.wlsError = wlsError;
  }
}
