package com.fusionspy.beacon.site.tux.entity;

import javax.persistence.*;

/**
 * siteList
 * User: qc
 * Date: 11-8-10
 * Time: 下午6:25
 */
@Table(name = "GE_MONITOR_TUX_SERVER")
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
        if (siteAuth != null ? !siteAuth.equals(that.siteAuth) : that.siteAuth != null) return false;
        if (siteIp != null ? !siteIp.equals(that.siteIp) : that.siteIp != null) return false;
        if (siteName != null ? !siteName.equals(that.siteName) : that.siteName != null) return false;
        if (siteWd != null ? !siteWd.equals(that.siteWd) : that.siteWd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = siteName != null ? siteName.hashCode() : 0;
        result = 31 * result + (siteIp != null ? siteIp.hashCode() : 0);
        result = 31 * result + sitePort;
        result = 31 * result + (siteAuth != null ? siteAuth.hashCode() : 0);
        result = 31 * result + (siteWd != null ? siteWd.hashCode() : 0);
        return result;
    }
}
