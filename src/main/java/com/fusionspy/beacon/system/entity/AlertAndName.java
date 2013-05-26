package com.fusionspy.beacon.system.entity;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * xml alert and name.
 * User: qc
 * Date: 11-9-6
 * Time: 上午10:28
 */
 @XmlAccessorType(XmlAccessType.PROPERTY)
public class AlertAndName extends Alert{

      static  final AlertAndName  DEFAULT = new AlertAndName();

      static {
           DEFAULT.setName(StringUtils.EMPTY);
           DEFAULT.setAlert(Alert.DISABLE);
      }

      @XmlAttribute(name="Name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        private String name;

}
