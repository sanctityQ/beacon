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
    /**
    * 最高会话数.
    */
    @XmlAttribute(name = "OpenSessionsHighCount")
    private Integer openSessionsHighCount;
    /**
    * 当前会话数.
    */
    @XmlAttribute(name = "OpenSessionsCurrentCount")
    private Integer openSessionsCurrentCount;
    /**
    * 累计打开会话数.
    */
    @XmlAttribute(name = "SessionsOpenedTotalCount")
    private Integer sessionsOpenedTotalCount;

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
    
    @Column(name="open_sessions_high_count")
    public Integer getOpenSessionsHighCount() {
    return this.openSessionsHighCount;
    }

    public void setOpenSessionsHighCount(Integer openSessionsHighCount) {
    this.openSessionsHighCount = openSessionsHighCount;
    }
    
    @Column(name="open_sessions_current_count")
    public Integer getOpenSessionsCurrentCount() {
    return this.openSessionsCurrentCount;
    }

    public void setOpenSessionsCurrentCount(Integer openSessionsCurrentCount) {
    this.openSessionsCurrentCount = openSessionsCurrentCount;
    }
    
    @Column(name="sessions_opened_total_count")
    public Integer getSessionsOpenedTotalCount() {
    return this.sessionsOpenedTotalCount;
    }

    public void setSessionsOpenedTotalCount(Integer sessionsOpenedTotalCount) {
    this.sessionsOpenedTotalCount = sessionsOpenedTotalCount;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


