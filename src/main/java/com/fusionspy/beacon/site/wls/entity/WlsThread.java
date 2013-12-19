package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-9-20 23:23:18 by One Data Tools 1.0.0


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsThread.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "ge_monitor_wls_thread")
public class WlsThread implements java.io.Serializable {

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
     * 空闲数量.
     */
    @XmlAttribute(name = "ExecuteThreadIdleCount")
    private Integer idleCount;
    /**
     * 备用数量.
     */
    @XmlAttribute(name = "StandbyThreadCount")
    private Integer standbyCount;
    /**
     * 总量.
     */
    @XmlAttribute(name = "ExecuteThreadTotalCount")
    private Integer totalCount;
    /**
     * 吞吐量.
     */
    @XmlAttribute(name = "CompletedRequestCount")
    private Integer thoughput;
    /**
     * 队列大小.
     */
    @XmlAttribute(name = "QueueLength")
    private Integer queueLength;

    public WlsThread() {
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

    @Column(name = "idle_count")
    public Integer getIdleCount() {
        return this.idleCount;
    }

    public void setIdleCount(Integer idleCount) {
        this.idleCount = idleCount;
    }

    @Column(name = "standby_count")
    public Integer getStandbyCount() {
        return this.standbyCount;
    }

    public void setStandbyCount(Integer standbyCount) {
        this.standbyCount = standbyCount;
    }

    @Column(name = "total_count")
    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Column(name = "thoughput")
    public Integer getThoughput() {
        return this.thoughput;
    }

    public void setThoughput(Integer thoughput) {
        this.thoughput = thoughput;
    }

    @Column(name = "queue_length")
    public Integer getQueueLength() {
        return this.queueLength;
    }

    public void setQueueLength(Integer queueLength) {
        this.queueLength = queueLength;
    }

    @Transient
    public BigDecimal getThdusage() {
        BigDecimal thdusage = new BigDecimal((totalCount - idleCount - standbyCount) + "").divide(new BigDecimal(totalCount + ""), 2, RoundingMode.HALF_UP);
        return thdusage.multiply(new BigDecimal("100"));
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


