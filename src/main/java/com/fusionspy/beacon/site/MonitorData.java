package com.fusionspy.beacon.site;

import com.fusionspy.beacon.site.tux.entity.TuxError;
import com.fusionspy.beacon.site.wls.entity.WlsError;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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

    private String siteName;

    @XmlAttribute(name="Date")
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    private TuxError tuxError;

    @XmlElement(name = "ERROR")
    public TuxError getTuxError() {
        return tuxError;
    }

    public void setTuxError(TuxError tuxError) {
        this.tuxError = tuxError;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
