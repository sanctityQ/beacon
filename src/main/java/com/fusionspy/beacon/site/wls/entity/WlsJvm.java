package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-9-20 23:23:18 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsJvm.
* 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="ge_monitor_wls_jvm")
public class WlsJvm  implements java.io.Serializable {

    /**
        */
    @XmlTransient
    private Integer id;
    /**
     * 站点名称
     */
    @XmlTransient
    private String siteName;
    /**
    * 入库时间.
    */
    @XmlTransient
    private Date recTime;
    /**
    * Server名称.
    */
    @XmlAttribute(name = "serverName")
    private String serverName;
    /**
    * 空闲heap.
    */
    @XmlAttribute(name = "HeapFreeCurrent")
    private String freeHeap;
    /**
    * 当前heap使用数.
    */
    @XmlAttribute(name = "HeapSizeCurrent")
    private String currentHeap;
    /**
    * 空闲heap百分比.
    */
    @XmlAttribute(name = "HeapFreePercent")
    private String freePercent;

    public WlsJvm() {
    }

   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true)
    public Integer getId() {
    return this.id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    @Column(name = "site_name")
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="rec_time", length=19)
    public Date getRecTime() {
    return this.recTime;
    }

    public void setRecTime(Date recTime) {
    this.recTime = recTime;
    }
    
    @Column(name="server_name")
    public String getServerName() {
    return this.serverName;
    }

    public void setServerName(String serverName) {
    this.serverName = serverName;
    }
    
    @Column(name="free_heap")
    public String getFreeHeap() {
    return this.freeHeap;
    }

    public void setFreeHeap(String freeHeap) {
    this.freeHeap = freeHeap;
    }
    
    @Column(name="current_heap")
    public String getCurrentHeap() {
    return this.currentHeap;
    }

    public void setCurrentHeap(String currentHeap) {
    this.currentHeap = currentHeap;
    }
    
    @Column(name="free_percent")
    public String getFreePercent() {
    return this.freePercent;
    }

    public void setFreePercent(String freePercent) {
    this.freePercent = freePercent;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


