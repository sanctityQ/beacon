package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-9-20 23:23:18 by One Data Tools 1.0.0


import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * WlsResource.
* 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="ge_monitor_wls_resource")
public class WlsResource  implements java.io.Serializable {

    /**
        */
    @XmlTransient
    private Integer id;
    /**
    * 记录时间.
    */
    @XmlTransient
    private Date recTime;
    /**
    * 服务器数量.
    */
    @XmlTransient
    private Integer serverNumber;
    /**
    * 运行服务器数量.
    */
    @XmlTransient
    private Integer runServerNumber;
    /**
    * cpu空闲.
    */
    @XmlTransient
    private Integer cpuIdle;
    /**
    * os类型.
    */
    @XmlTransient
    private String osType;

    @Transient
    @XmlAttribute(name = "CPU")
    private String cpu;

    @Transient
    @XmlAttribute(name = "MEM")
    private String mem;

    @Transient
    @XmlTransient
    private Integer memFree;

    public WlsResource() {
    }

   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true)
    public Integer getId() {
    return this.id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="rec_time", length=19)
    public Date getRecTime() {
    return this.recTime;
    }

    public void setRecTime(Date recTime) {
    this.recTime = recTime;
    }
    
    @Column(name="server_number")
    public Integer getServerNumber() {
    return this.serverNumber;
    }

    public void setServerNumber(Integer serverNumber) {
    this.serverNumber = serverNumber;
    }
    
    @Column(name="run_server_number")
    public Integer getRunServerNumber() {
    return this.runServerNumber;
    }

    public void setRunServerNumber(Integer runServerNumber) {
    this.runServerNumber = runServerNumber;
    }
    
    @Column(name="cpu_idle")
    public Integer getCpuIdle() {
    return this.cpuIdle;
    }

    public void setCpuIdle(Integer cpuIdle) {
    this.cpuIdle = cpuIdle;
    }
    
    @Column(name="os_type")
    public String getOsType() {
    return this.osType;
    }

    public void setOsType(String osType) {
    this.osType = osType;
    }

    @Transient
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Transient
    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    @Transient
    public Integer getMemFree() {
        return memFree;
    }

    public void setMemFree(Integer memFree) {
        this.memFree = memFree;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


