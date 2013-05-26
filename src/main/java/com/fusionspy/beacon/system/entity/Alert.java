package com.fusionspy.beacon.system.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * xml alert
 * User: qc
 * Date: 11-9-6
 * Time: 上午10:25
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Alert {

    public static final String ENABLE = "1";

    public static final String DISABLE = "0";

    @XmlAttribute(name = "Alert")
    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    private String alert = DISABLE;

    public  boolean enable(){
        return this.alert.equals(ENABLE);
    }

}
