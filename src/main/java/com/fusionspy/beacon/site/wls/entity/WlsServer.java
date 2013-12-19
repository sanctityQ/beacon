package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-10-14 0:49:30 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsServer.
 */
@Entity
@Table(name = "ge_monitor_wls_server")
public class WlsServer implements java.io.Serializable {

    /**
     * weblogic版本
     */
    private String version;
    /**
     * Server名称
     */
    private String siteName;
    /**
     * 记录时间
     */
    private Date recTime;
    /**
     * IP地址
     */
    private String listenAddress;
    /**
     * 端口
     */
    private Integer listenPort;
    /**
     * 轮询时间
     */
    private Integer interval;
    /**
     * weblogic监听地址
     */
    private String weblogicIp;
    /**
     * weblogic监听端口
     */
    private Integer weblogicPort;

    private String domainName;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     */
    private Integer isSsl;

    private Integer status;

    public WlsServer() {
    }

    @Column(name = "version", length = 20)
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Id
    @Column(name = "site_name", unique = true)
    public String getSiteName() {
        return this.siteName;
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

    @Column(name = "listen_address")
    public String getListenAddress() {
        return this.listenAddress;
    }

    public void setListenAddress(String listenAddress) {
        this.listenAddress = listenAddress;
    }

    @Column(name = "listen_port", length = 10)
    public Integer getListenPort() {
        return this.listenPort;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    @Column(name = "interval_")
    public Integer getInterval() {
        return this.interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "is_SSL")
    public Integer getIsSsl() {
        return this.isSsl;
    }

    public void setIsSsl(Integer isSsl) {
        this.isSsl = isSsl;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "weblogic_ip")
    public String getWeblogicIp() {
        return weblogicIp;
    }

    public void setWeblogicIp(String weblogicIp) {
        this.weblogicIp = weblogicIp;
    }

    @Column(name = "weblogic_port")
    public Integer getWeblogicPort() {
        return weblogicPort;
    }

    public void setWeblogicPort(Integer weblogicPort) {
        this.weblogicPort = weblogicPort;
    }

    @Column(name = "domain_name")
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


