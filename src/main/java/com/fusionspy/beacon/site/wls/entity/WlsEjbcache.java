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
 * WlsEjbcache.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "ge_monitor_wls_ejbcache")
public class WlsEjbcache implements java.io.Serializable {

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
     * 缓存访问数量.
     */
    @XmlAttribute(name = "CacheAccessCount")
    private Integer cacheAccessCount;
    /**
     * 激活数量.
     */
    @XmlAttribute(name = "ActivationCount")
    private Integer activationCount;
    /**
     * 缓存bean当前数量.
     */
    @XmlAttribute(name = "CachedBeansCurrentCount")
    private Integer cacheBeansCurrentCount;
    /**
     * 缓存击中数量.
     */
    @XmlAttribute(name = "CacheHitCount")
    private Integer cacheHitCount;
    /**
     * 缓存错失数量.
     */
    @XmlAttribute(name = "CacheMissCount")
    private Integer cacheMissCount;
    /**
     * 钝化数量.
     */
    @XmlAttribute(name = "PassivationCount")
    private Integer passivationCount;

    public WlsEjbcache() {
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true)
    public Integer getId() {
        return this.id;
    }

    @Column(name = "site_name")
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

    @Column(name = "cache_access_count")
    public Integer getCacheAccessCount() {
        return this.cacheAccessCount;
    }

    public void setCacheAccessCount(Integer cacheAccessCount) {
        this.cacheAccessCount = cacheAccessCount;
    }

    @Column(name = "activation_count")
    public Integer getActivationCount() {
        return this.activationCount;
    }

    public void setActivationCount(Integer activationCount) {
        this.activationCount = activationCount;
    }

    @Column(name = "cache_beans_current_count")
    public Integer getCacheBeansCurrentCount() {
        return this.cacheBeansCurrentCount;
    }

    public void setCacheBeansCurrentCount(Integer cacheBeansCurrentCount) {
        this.cacheBeansCurrentCount = cacheBeansCurrentCount;
    }

    @Column(name = "cache_hit_count")
    public Integer getCacheHitCount() {
        return this.cacheHitCount;
    }

    public void setCacheHitCount(Integer cacheHitCount) {
        this.cacheHitCount = cacheHitCount;
    }

    @Column(name = "cache_miss_count")
    public Integer getCacheMissCount() {
        return this.cacheMissCount;
    }

    public void setCacheMissCount(Integer cacheMissCount) {
        this.cacheMissCount = cacheMissCount;
    }

    @Column(name = "passivation_count")
    public Integer getPassivationCount() {
        return this.passivationCount;
    }

    public void setPassivationCount(Integer passivationCount) {
        this.passivationCount = passivationCount;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


