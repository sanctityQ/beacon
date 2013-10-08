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
 * WlsSysrec.
* 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="ge_monitor_wls_sysrec")
public class WlsSysrec  implements java.io.Serializable {

    /**
    *  id
    */
    @XmlTransient
    private Integer id;
    /**
    * 记录时间.
    */
    @XmlTransient
    private Date recTime;
    /**
    * Agent版本.
    */
   @XmlAttribute(name = "AgentVersion")
    private String agentVersion;
    /**
    * 中间件版本.
    */
    @XmlAttribute(name = "DomainVersion")
    private String domainVersion;
    /**
    * 系统启动时间.
    */
    @XmlAttribute(name = "Activationtime")
    private String systemBoot;
    /**
    * os类型.(OS版本)
    */
    @XmlAttribute(name = "OSName")
    private String osType;

    /*-----------------------以下属性不计入数据库-------------------------------*/

    /**
     * 管理服务器名称
     */
    @XmlAttribute(name = "AdminServerName")
    private String adminServerName;
    /**
     * domain 名称
     */
    @XmlAttribute(name = "Name")
    private String name;
    /**
     * 守护进程
     */
    @XmlAttribute(name = "GuardianEnabled")
    private String guardianEnabled;
    /**
     * JDK厂商
     */
    @XmlAttribute(name = "JDKVendor")
    private String jdkVendor;
    /**
     * JDK版本
     */
    @XmlAttribute(name = "JDKVersion")
    private String jdkVersion;
    /**
     * OS版本
     */
    @XmlAttribute(name = "OSVersion")
    private String osVersion;
    /**
     * 产品模式
     */
    @XmlAttribute(name = "ProductionModeEnabled")
    private String productionModeEnabled;
    /**
     * root目录
     */
    @XmlAttribute(name = "RootDirectory")
    private String rootDirectory;
    /**
     * server数量
     */
    @XmlAttribute(name = "ServerNum")
    private int serverNum;

    public WlsSysrec() {
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
    
    @Column(name="agent_version")
    public String getAgentVersion() {
    return this.agentVersion;
    }

    public void setAgentVersion(String agentVersion) {
    this.agentVersion = agentVersion;
    }
    
    @Column(name="domain_version")
    public String getDomainVersion() {
    return this.domainVersion;
    }

    public void setDomainVersion(String domainVersion) {
    this.domainVersion = domainVersion;
    }
    
    @Column(name="system_boot")
    public String getSystemBoot() {
    return this.systemBoot;
    }

    public void setSystemBoot(String systemBoot) {
    this.systemBoot = systemBoot;
    }
    
    @Column(name="os_type")
    public String getOsType() {
    return this.osType;
    }

    public void setOsType(String osType) {
    this.osType = osType;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    @Transient
    public String getAdminServerName() {
        return adminServerName;
    }

    public void setAdminServerName(String adminServerName) {
        this.adminServerName = adminServerName;
    }

    @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getGuardianEnabled() {
        return guardianEnabled;
    }


    public void setGuardianEnabled(String guardianEnabled) {
        this.guardianEnabled = guardianEnabled;
    }

    @Transient
    public String getJdkVendor() {
        return jdkVendor;
    }

    public void setJdkVendor(String jdkVendor) {
        this.jdkVendor = jdkVendor;
    }

    @Transient
    public String getJdkVersion() {
        return jdkVersion;
    }

    public void setJdkVersion(String jdkVersion) {
        this.jdkVersion = jdkVersion;
    }

    @Transient
    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @Transient
    public String getProductionModeEnabled() {
        return productionModeEnabled;
    }

    public void setProductionModeEnabled(String productionModeEnabled) {
        this.productionModeEnabled = productionModeEnabled;
    }

    @Transient
    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Transient
    public int getServerNum() {
        return serverNum;
    }

    public void setServerNum(int serverNum) {
        this.serverNum = serverNum;
    }

}


