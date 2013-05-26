package com.fusionspy.beacon.site;

import com.google.common.collect.MapMaker;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * site info log
 */
public class SiteInfoLogger {

    private static SiteInfoLogger siteInfoLogger = new SiteInfoLogger();

    private  SiteInfoLogger(){};

    public static SiteInfoLogger getInstance(){
        return   siteInfoLogger;
    }

    private static final String LOG = "_EVENT";

    private static final int CAPACITY = 10;

    private ConcurrentMap<String, List<String>> pool = new MapMaker().concurrencyLevel(32).makeMap();


    public void logInf(String siteName, String format, Object... arg) {
        String msg = MessageFormatter.format(format, arg).getMessage();
        List t =  new ArrayList<String>();
        t.add(msg);
        logInf(siteName, t);
    }

    public void logInf(String siteName, List<String> msg) {

         pool.put(siteName,msg) ;
    }


    public List<String> getLog(String siteName) {
        return  pool.get(siteName)==null?new ArrayList<String>():pool.get(siteName);
    }

}
