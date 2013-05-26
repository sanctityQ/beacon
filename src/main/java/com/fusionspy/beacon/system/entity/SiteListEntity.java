package com.fusionspy.beacon.system.entity;

import javax.persistence.*;

/**
 * siteList
 * User: qc
 * Date: 11-8-10
 * Time: 下午6:25
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "GE_MONITOR_APP_SERVER")
@Entity
public class SiteListEntity {
    private String siteName;

    private Integer interval;

    @Column(name = "siteName")
    @Id
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    private String siteType;

    @Column(name = "siteType")
    @Basic
    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    private String siteIp;

    @Column(name = "siteIP")
    @Basic
    public String getSiteIp() {
        return siteIp;
    }

    public void setSiteIp(String siteIp) {
        this.siteIp = siteIp;
    }

    private int sitePort;

    @Column(name = "sitePort")
    @Basic
    public int getSitePort() {
        return sitePort;
    }

    public void setSitePort(int sitePort) {
        this.sitePort = sitePort;
    }

    private String siteAuth;

    @javax.persistence.Column(name = "siteAuth")
    @Basic
    public String getSiteAuth() {
        return siteAuth;
    }

    public void setSiteAuth(String siteAuth) {
        this.siteAuth = siteAuth;
    }

    private String siteWd;

    @javax.persistence.Column(name = "siteWd")
    @Basic
    public String getSiteWd() {
        return siteWd;
    }

    public void setSiteWd(String siteWd) {
        this.siteWd = siteWd;
    }

    private String wlsAdminUrl;

    @javax.persistence.Column(name = "wlsAdminUrl")
    @Basic
    public String getWlsAdminUrl() {
        return wlsAdminUrl;
    }

    public void setWlsAdminUrl(String wlsAdminUrl) {
        this.wlsAdminUrl = wlsAdminUrl;
    }

    private String wlsVer;

    @javax.persistence.Column(name = "wlsVer")
    @Basic
    public String getWlsVer() {
        return wlsVer;
    }

    public void setWlsVer(String wlsVer) {
        this.wlsVer = wlsVer;
    }

    private int wlsSSL;

    @javax.persistence.Column(name = "wlsSSL")
    @Basic
    public int getWlsSSL() {
        return wlsSSL;
    }

    public void setWlsSSL(int wlsSSL) {
        this.wlsSSL = wlsSSL;
    }

    private String databaseFile;

    @javax.persistence.Column(name = "databaseFile")
    @Basic
    public String getDatabaseFile() {
        return databaseFile;
    }

    public void setDatabaseFile(String databaseFile) {
        this.databaseFile = databaseFile;
    }

    private String siteFlag;

    @javax.persistence.Column(name = "siteFlag")
    @Basic
    public String getSiteFlag() {
        return siteFlag;
    }

    public void setSiteFlag(String siteFlag) {
        this.siteFlag = siteFlag;
    }

    @Column(name = "`interval`")
    public Integer getInterval(){
       return this.interval;
    }

    public void setInterval(Integer interval){
        this.interval = interval;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteListEntity that = (SiteListEntity) o;

        if (sitePort != that.sitePort) return false;
        if (wlsSSL != that.wlsSSL) return false;
        if (databaseFile != null ? !databaseFile.equals(that.databaseFile) : that.databaseFile != null) return false;
        if (siteAuth != null ? !siteAuth.equals(that.siteAuth) : that.siteAuth != null) return false;
        if (siteFlag != null ? !siteFlag.equals(that.siteFlag) : that.siteFlag != null) return false;
        if (siteIp != null ? !siteIp.equals(that.siteIp) : that.siteIp != null) return false;
        if (siteName != null ? !siteName.equals(that.siteName) : that.siteName != null) return false;
        if (siteType != null ? !siteType.equals(that.siteType) : that.siteType != null) return false;
        if (siteWd != null ? !siteWd.equals(that.siteWd) : that.siteWd != null) return false;
        if (wlsAdminUrl != null ? !wlsAdminUrl.equals(that.wlsAdminUrl) : that.wlsAdminUrl != null) return false;
        if (wlsVer != null ? !wlsVer.equals(that.wlsVer) : that.wlsVer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = siteName != null ? siteName.hashCode() : 0;
        result = 31 * result + (siteType != null ? siteType.hashCode() : 0);
        result = 31 * result + (siteIp != null ? siteIp.hashCode() : 0);
        result = 31 * result + sitePort;
        result = 31 * result + (siteAuth != null ? siteAuth.hashCode() : 0);
        result = 31 * result + (siteWd != null ? siteWd.hashCode() : 0);
        result = 31 * result + (wlsAdminUrl != null ? wlsAdminUrl.hashCode() : 0);
        result = 31 * result + (wlsVer != null ? wlsVer.hashCode() : 0);
        result = 31 * result + wlsSSL;
        result = 31 * result + (databaseFile != null ? databaseFile.hashCode() : 0);
        result = 31 * result + (siteFlag != null ? siteFlag.hashCode() : 0);
        return result;
    }
}
