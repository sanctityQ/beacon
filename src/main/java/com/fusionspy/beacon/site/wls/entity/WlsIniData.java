package com.fusionspy.beacon.site.wls.entity;

import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorData;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-21
 * Time: 上午12:10
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "MONITOR")
@XmlAccessorType(XmlAccessType.FIELD)
public class WlsIniData extends MonitorData implements InitData {


    public static WlsIniData EMPTY = null;

    static {
        EMPTY = new WlsIniData();
        EMPTY.setWlsSysrec(new WlsSysrec());
    }

    @XmlElement(name = "INITBUF")
    private WlsSysrec wlsSysrec;

    public WlsSysrec getWlsSysrec() {
        return wlsSysrec;
    }

    public void setWlsSysrec(WlsSysrec wlsSysrec) {
        this.wlsSysrec = wlsSysrec;
    }

    public WlsIniData defaultData() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for(Field field : fields) {
                if(field.isAnnotationPresent(XmlElement.class)) {
                    field.setAccessible(true);
                    Object fieldVal = field.get(this);
                    if(fieldVal == null) continue;
                    if(field.getType() == List.class) {
                        List<?> fieldList = (List<?>) fieldVal;
                        for(Object obj : fieldList) {
                            for(Field subField : obj.getClass().getDeclaredFields()) {
                                subField.setAccessible(true);
                                if(subField.isAnnotationPresent(XmlAttribute.class) && subField.getType()==String.class) {
                                    Object subFieldVal =subField.get(obj);
                                    if(subFieldVal == null || StringUtils.isBlank(subFieldVal.toString())) {
                                        subField.set(obj, "N/A");
                                    }
                                    if("recTime".equals(subField.getName()) && subField.getType()== java.util.Date.class) {
                                        subField.set(obj, new Date());
                                    }
                                }
                            }
                        }
                    } else {
                        for(Field subField : fieldVal.getClass().getDeclaredFields()) {
                            subField.setAccessible(true);
                            if(subField.isAnnotationPresent(XmlAttribute.class) && subField.getType()==String.class) {
                                Object subFieldVal = subField.get(fieldVal);
                                if(subFieldVal == null || StringUtils.isBlank(subFieldVal.toString())) {
                                    subField.set(fieldVal, "N/A");
                                }
                                if("recTime".equals(subField.getName()) && subField.getType()== java.util.Date.class) {
                                    subField.set(fieldVal, new Date());
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }
}
