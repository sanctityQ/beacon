package com.fusionspy.beacon.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * struts2 maintain beacon locale
 * struts.xml
 * <constant name="struts.locale" value="en" />
 * User: qc
 * Date: 11-9-22
 * Time: 下午4:44
 */
public final class BeaconLocale {

    private static Logger logger = LoggerFactory.getLogger(BeaconLocale.class);

    private static Locale lo;

    public  static void setLocale(Locale locale) {
            lo = locale;
    }


    public static Locale getBeaconLocale(){
       //  return  ActionContext.getContext().getLocale();
       //todo 需要调整为自动获取locale，
        return new Locale("zh_CN");
    }

}
