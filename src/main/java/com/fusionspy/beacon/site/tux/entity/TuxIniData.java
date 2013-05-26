package com.fusionspy.beacon.site.tux.entity;

import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorData;
import com.fusionspy.beacon.system.entity.SysrecsEntity;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * tux ini data
 * User: qc
 * Date: 11-9-13
 * Time: 下午11:04
 */
@XmlRootElement(name = "MONITOR")
public class TuxIniData extends MonitorData implements InitData {

    private SysrecsEntity sysrecsEntity;

    @XmlTransient
    public static TuxIniData EMPTY;

    static {
      EMPTY  = new TuxIniData();
      SysrecsEntity sysrecsEntity = new SysrecsEntity();
      sysrecsEntity.setRectime(null);
      sysrecsEntity.setAgentver(StringUtils.EMPTY);
      sysrecsEntity.setSiteName(StringUtils.EMPTY);
      sysrecsEntity.setOstype(StringUtils.EMPTY);
      sysrecsEntity.setSystemboot(StringUtils.EMPTY);
      sysrecsEntity.setProductver(StringUtils.EMPTY);
      EMPTY.setSysrecsEntity(sysrecsEntity);
    }

    @XmlElement(name = "INITBUF")
    public SysrecsEntity getSysrecsEntity() {
        return sysrecsEntity;
    }

    public void setSysrecsEntity(SysrecsEntity sysrecsEntity) {
        this.sysrecsEntity = sysrecsEntity;
    }

    public void setSiteName(String siteName) {
        this.sysrecsEntity.setSiteName(siteName);
    }
}
