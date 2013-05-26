package com.fusionspy.beacon.system.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;


@javax.persistence.Table(name = "GE_MONITOR_TUX_SETTING")
@Entity
public class SiteSettingsEntity {
    private String siteName;

    @javax.persistence.Column(name = "siteName")
    @Id
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    private String siteType;

    @javax.persistence.Column(name = "siteType")
    @Basic
    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    private String siteSetting;

    @javax.persistence.Column(name = "siteSetting")
    @Basic
    public String getSiteSetting() {
        return siteSetting;
    }

    public void setSiteSetting(String siteSetting) {
        this.siteSetting = siteSetting;
    }

    private String flag;

    @javax.persistence.Column(name = "flag")
    @Basic
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteSettingsEntity that = (SiteSettingsEntity) o;

        if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;
        if (siteName != null ? !siteName.equals(that.siteName) : that.siteName != null) return false;
        if (siteSetting != null ? !siteSetting.equals(that.siteSetting) : that.siteSetting != null) return false;
        if (siteType != null ? !siteType.equals(that.siteType) : that.siteType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = siteName != null ? siteName.hashCode() : 0;
        result = 31 * result + (siteType != null ? siteType.hashCode() : 0);
        result = 31 * result + (siteSetting != null ? siteSetting.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        return result;
    }
}
