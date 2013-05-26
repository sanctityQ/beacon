package com.fusionspy.beacon.system.entity;

import javax.xml.bind.annotation.*;

/**
 * site settings.
 * User: qc
 * Date: 11-9-1
 * Time: 下午1:51
 */
@XmlRootElement(name="site")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SiteSettings {

    public final static SiteSettings DEFAULT = new SiteSettings();

    static{
        DEFAULT.setConditions(Conditions.DEFAULT);
        DEFAULT.setAlertType(AlertType.DEFAULT);
        DEFAULT.setDataSave(DataSave.DEFAULT);
    }

    private String siteName;

    @XmlTransient
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    private String siteType;

    @XmlTransient
    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    private Conditions conditions;

    @XmlElement(name="CONDITIONS")
    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    private AlertType alertType;

    @XmlElement(name="ALERTTYPE")
    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    private DataSave dataSave;

    @XmlElement(name="DATASAVING")
    public DataSave getDataSave() {
        return dataSave;
    }

    public void setDataSave(DataSave dataSave) {
        this.dataSave = dataSave;
    }


}
