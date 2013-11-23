package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-9-20 23:23:18 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsWebapp.
* 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="ge_monitor_wls_webapp")
public class WlsWebapp  implements java.io.Serializable {

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
    * 应用名称.
    */
    @XmlAttribute(name = "Name")
    private String name;
    /**
    * 部署状态.
    */
    @XmlAttribute(name = "DeploymentState")
    private String deploymentState;
    /**
    * 状态.
    */
    @XmlAttribute(name = "Status")
    private String status;
    /**
    * 组件名称.
    */
    @XmlAttribute(name = "ComponentName")
    private String componentName;

    private Integer openSessionsHigh;

    private Integer openSessionsCurrent;

    private Integer sessionsOpenedTotal;

    /**
    * 最高会话数.
    */
    @XmlAttribute(name = "OpenSessionsHighCount")
    private String openSessionsHighCount;
    /**
    * 当前会话数.
    */
    @XmlAttribute(name = "OpenSessionsCurrentCount")
    private String openSessionsCurrentCount;
    /**
    * 累计打开会话数.
    */
    @XmlAttribute(name = "SessionsOpenedTotalCount")
    private String sessionsOpenedTotalCount;

    public WlsWebapp() {
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
    
    @Column(name="name")
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
    this.name = name;
    }
    
    @Column(name="deployment_state")
    public String getDeploymentState() {
    return this.deploymentState;
    }

    public void setDeploymentState(String deploymentState) {
    this.deploymentState = deploymentState;
    }
    
    @Column(name="status")
    public String getStatus() {
    return this.status;
    }

    public void setStatus(String status) {
    this.status = status;
    }
    
    @Column(name="component_name")
    public String getComponentName() {
    return this.componentName;
    }

    public void setComponentName(String componentName) {
    this.componentName = componentName;
    }

    @Transient
    public String getOpenSessionsHighCount() {
    return this.openSessionsHighCount;
    }

    public void setOpenSessionsHighCount(String openSessionsHighCount) {
    this.openSessionsHighCount = openSessionsHighCount;
    }

    @Transient
    public String getOpenSessionsCurrentCount() {
    return this.openSessionsCurrentCount;
    }

    public void setOpenSessionsCurrentCount(String openSessionsCurrentCount) {
    this.openSessionsCurrentCount = openSessionsCurrentCount;
    }

    @Transient
    public String getSessionsOpenedTotalCount() {
    return this.sessionsOpenedTotalCount;
    }

    public void setSessionsOpenedTotalCount(String sessionsOpenedTotalCount) {
    this.sessionsOpenedTotalCount = sessionsOpenedTotalCount;
    }

    @Column(name="open_sessions_high_count")
    public Integer getOpenSessionsHigh() {
        return openSessionsHigh;
    }

    public void setOpenSessionsHigh(Integer openSessionsHigh) {
        this.openSessionsHigh = openSessionsHigh;
    }

    @Column(name="open_sessions_current_count")
    public Integer getOpenSessionsCurrent() {
        return openSessionsCurrent;
    }

    public void setOpenSessionsCurrent(Integer openSessionsCurrent) {
        this.openSessionsCurrent = openSessionsCurrent;
    }

    @Column(name="sessions_opened_total_count")
    public Integer getSessionsOpenedTotal() {
        return sessionsOpenedTotal;
    }

    public void setSessionsOpenedTotal(Integer sessionsOpenedTotal) {
        this.sessionsOpenedTotal = sessionsOpenedTotal;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


