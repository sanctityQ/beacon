package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.tux.entity.TuxcltsStatsEntity;
import com.fusionspy.beacon.site.tux.entity.TuxresourceEntity;
import com.fusionspy.beacon.site.tux.entity.TuxsvrStatsEntity;
import com.fusionspy.beacon.site.wls.entity.WlsResource;
import com.fusionspy.beacon.util.QueuesHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * wls his data trace
 * User: qc
 * Date: 11-10-20
 * Time: 下午11:27
 */
public class WlsDataTrace {

    private static final int point = 20;

    private static final String TRACE = "_TRACE";

    private static final String WLS_RES = "_WLS_RES" + TRACE;

    private WlsDataTrace() {
    }

    private static WlsDataTrace dataTrace = new WlsDataTrace();

    public static WlsDataTrace getInstance() {
        return dataTrace;
    }


    public void addWlsRes(String siteName,WlsResource wlsResource) {

    }

    public Iterator<WlsResource> getResQue(String siteName) {
        BlockingQueue<WlsResource> blockingQueue =  QueuesHolder.getQueue(siteName + WLS_RES, point);
        return blockingQueue.iterator();
    }
}
