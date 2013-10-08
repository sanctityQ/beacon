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
 * WlsSvr.
* 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="ge_monitor_wls_svr")
public class WlsSvr  implements java.io.Serializable {

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
    * Server名称.
    */
    @XmlAttribute(name = "serverName")
    private String serverName;
    /**
    * 监听地址.
    */
    @XmlAttribute(name = "ListenAddress")
    private String listenAddress;
    /**
    * 监听端口.
    */
    @XmlAttribute(name = "ListenPort")
    private String listenPort;
    /**
    * 健康状态.
    */
    @XmlAttribute(name = "HealthState")
    private String health;
    /**
    * 状态.
    */
    @XmlAttribute(name = "State")
    private String state;
    /**
    * 开启端口数.
    */
    @XmlAttribute(name = "OpenSocketsCurrentCount")
    private Integer openSocketNum;

    public WlsSvr() {
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
    
    @Column(name="server_name")
    public String getServerName() {
    return this.serverName;
    }

    public void setServerName(String serverName) {
    this.serverName = serverName;
    }
    
    @Column(name="listen_address")
    public String getListenAddress() {
    return this.listenAddress;
    }

    public void setListenAddress(String listenAddress) {
    this.listenAddress = listenAddress;
    }
    
    @Column(name="listen_port")
    public String getListenPort() {
    return this.listenPort;
    }

    public void setListenPort(String listenPort) {
    this.listenPort = listenPort;
    }
    
    @Column(name="health")
    public String getHealth() {
    return this.health;
    }

    public void setHealth(String health) {
    this.health = health;
    }
    
    @Column(name="state")
    public String getState() {
    return this.state;
    }

    public void setState(String state) {
    this.state = state;
    }
    
    @Column(name="open_socket_num")
    public Integer getOpenSocketNum() {
    return this.openSocketNum;
    }

    public void setOpenSocketNum(Integer openSocketNum) {
    this.openSocketNum = openSocketNum;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


