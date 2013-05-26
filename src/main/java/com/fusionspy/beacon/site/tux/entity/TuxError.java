package com.fusionspy.beacon.site.tux.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * intem tux error
 * User: qc
 * Date: 11-9-21
 * Time: 下午3:05
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TuxError {

    private String error;

    @XmlAttribute(name="Reason")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
