package com.fusionspy.beacon.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public final class BeaconLocale {

    private static Logger logger = LoggerFactory.getLogger(BeaconLocale.class);

    private static Locale lo = new Locale("zh");;


    public  static void setLocale(Locale locale) {
        lo = locale;
    }


    public static Locale getBeaconLocale(){
       //todo 需要调整为自动获取locale，
        return lo;
    }

}
