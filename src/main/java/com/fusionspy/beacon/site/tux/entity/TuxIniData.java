package com.fusionspy.beacon.site.tux.entity;

import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorData;
import com.fusionspy.beacon.site.tux.dao.SysrecsDao;
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
      sysrecsEntity.setAgentver(InitData.EMPTY);
      sysrecsEntity.setSiteName(InitData.EMPTY);
      sysrecsEntity.setOstype(InitData.EMPTY);
      sysrecsEntity.setSystemboot(InitData.EMPTY);
      sysrecsEntity.setProductver(InitData.EMPTY);
      EMPTY.setSysrecsEntity(sysrecsEntity);
    }

    private SysrecsDao sysrecsDao;

    @XmlElement(name = "INITBUF")
    public SysrecsEntity getSysrecsEntity() {
        return sysrecsEntity;
    }

    public void setSysrecsEntity(SysrecsEntity sysrecsEntity) {
        this.sysrecsEntity = sysrecsEntity;
    }

    @Override
    public void process() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSysrecsDao(SysrecsDao sysrecsDao) {
        this.sysrecsDao = sysrecsDao;
    }
}
