package com.fusionspy.beacon.site;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * monitor data for xml
 * User: qc
 * Date: 11-9-13
 * Time: 下午7:49
 */
@XmlRootElement
public class MonitorData {

    private String Date;

    @XmlAttribute(name="Date")
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }

}
