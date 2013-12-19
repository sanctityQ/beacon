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
 * WlsEjbpool.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "ge_monitor_wls_ejbpool")
public class WlsEjbpool implements java.io.Serializable {

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
     * 名称.
     */
    @XmlAttribute(name = "Name")
    private String name;
    /**
     * 使用中bean数量.
     */
    @XmlAttribute(name = "BeansInUseCount")
    private Integer beansInUseCount;
    /**
     * 当前使用中bean数量.
     */
    @XmlAttribute(name = "BeansInUseCurrentCount")
    private Integer beansInUserCurrentCount;
    /**
     * 总计访问数.
     */
    @XmlAttribute(name = "AccessTotalCount")
    private Integer accessTotalCount;
    /**
     * 总计销毁数.
     */
    @XmlAttribute(name = "DestroyedTotalCount")
    private Integer destroyedTotalCount;
    /**
     * 空闲bean数量.
     */
    @XmlAttribute(name = "IdleBeansCount")
    private Integer idleBeansCount;
    /**
     * 总计错失数量.
     */
    @XmlAttribute(name = "MissTotalCount")
    private Integer missTotalCount;
    /**
     * 当前池化bean数量.
     */
    @XmlAttribute(name = "PooledBeansCurrentCount")
    private Integer pooledBeansCurrentCount;
    /**
     * 总计超时数量.
     */
    @XmlAttribute(name = "TimeoutTotalCount")
    private Integer timeoutTotalCount;
    /**
     * 当前等待数量.
     */
    @XmlAttribute(name = "WaiterCurrentCount")
    private Integer waiterCurrentCount;

    public WlsEjbpool() {
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true)
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
    @Column(name = "rec_time", length = 19)
    public Date getRecTime() {
        return this.recTime;
    }

    public void setRecTime(Date recTime) {
        this.recTime = recTime;
    }

    @Column(name = "server_name")
    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "beans_inUse_count")
    public Integer getBeansInUseCount() {
        return this.beansInUseCount;
    }

    public void setBeansInUseCount(Integer beansInUseCount) {
        this.beansInUseCount = beansInUseCount;
    }

    @Column(name = "beans_inUser_current_count")
    public Integer getBeansInUserCurrentCount() {
        return this.beansInUserCurrentCount;
    }

    public void setBeansInUserCurrentCount(Integer beansInUserCurrentCount) {
        this.beansInUserCurrentCount = beansInUserCurrentCount;
    }

    @Column(name = "access_total_count")
    public Integer getAccessTotalCount() {
        return this.accessTotalCount;
    }

    public void setAccessTotalCount(Integer accessTotalCount) {
        this.accessTotalCount = accessTotalCount;
    }

    @Column(name = "destroyed_total_count")
    public Integer getDestroyedTotalCount() {
        return this.destroyedTotalCount;
    }

    public void setDestroyedTotalCount(Integer destroyedTotalCount) {
        this.destroyedTotalCount = destroyedTotalCount;
    }

    @Column(name = "idle_beans_count")
    public Integer getIdleBeansCount() {
        return this.idleBeansCount;
    }

    public void setIdleBeansCount(Integer idleBeansCount) {
        this.idleBeansCount = idleBeansCount;
    }

    @Column(name = "miss_total_count")
    public Integer getMissTotalCount() {
        return this.missTotalCount;
    }

    public void setMissTotalCount(Integer missTotalCount) {
        this.missTotalCount = missTotalCount;
    }

    @Column(name = "pooled_beans_current_count")
    public Integer getPooledBeansCurrentCount() {
        return this.pooledBeansCurrentCount;
    }

    public void setPooledBeansCurrentCount(Integer pooledBeansCurrentCount) {
        this.pooledBeansCurrentCount = pooledBeansCurrentCount;
    }

    @Column(name = "timeout_total_count")
    public Integer getTimeoutTotalCount() {
        return this.timeoutTotalCount;
    }

    public void setTimeoutTotalCount(Integer timeoutTotalCount) {
        this.timeoutTotalCount = timeoutTotalCount;
    }

    @Column(name = "waiter_current_count")
    public Integer getWaiterCurrentCount() {
        return this.waiterCurrentCount;
    }

    public void setWaiterCurrentCount(Integer waiterCurrentCount) {
        this.waiterCurrentCount = waiterCurrentCount;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


