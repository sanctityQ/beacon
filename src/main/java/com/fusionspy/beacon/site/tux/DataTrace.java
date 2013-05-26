package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.tux.entity.TuxcltsStatsEntity;
import com.fusionspy.beacon.site.tux.entity.TuxresourceEntity;
import com.fusionspy.beacon.site.tux.entity.TuxsvrStatsEntity;
import com.fusionspy.beacon.util.QueuesHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * tux his data trace
 * User: qc
 * Date: 11-10-20
 * Time: 下午11:27
 */
public class DataTrace {

    private static final int point = 20;

    private static final String TRACE = "_TRACE";

    private static final String TUX_RES = "_TUX_RES" + TRACE;

    private static final String TUX_CLT_STAT = "_TUX_CLT_STAT" + TRACE;

    private static final String TUX_SVR_STAT = "_TUX_SVR_STAT" + TRACE;


    private DataTrace() {
    }

    private static  DataTrace dataTrace = new DataTrace();

    public static DataTrace getInstance() {
        return dataTrace;
    }


    public void addTuxRes(String siteName,TuxresourceEntity tuxresource) {
        try {
            Assert.state(StringUtils.equals(siteName,tuxresource.getSitename()));
            QueuesHolder.getQueue(siteName + TUX_RES, point).put(tuxresource);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addTuxCltStats(String siteName,TuxcltsStatsEntity tuxcltsStats) {
        try {
            Assert.state(StringUtils.equals(siteName,tuxcltsStats.getSitename()));
            QueuesHolder.getQueue(siteName + TUX_CLT_STAT, point).put(tuxcltsStats);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addTuxSvrStats(String siteName,TuxsvrStatsEntity tuxsvrStats) {
        try {
            Assert.state(StringUtils.equals(siteName,tuxsvrStats.getSitename()));
            QueuesHolder.getQueue(tuxsvrStats.getSitename() + TUX_SVR_STAT, point).put(tuxsvrStats);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Iterator<TuxcltsStatsEntity> getCltStatsQue(String siteName) {
        BlockingQueue<TuxcltsStatsEntity> blockingQueue =  QueuesHolder.getQueue(siteName + TUX_CLT_STAT, point);
        return blockingQueue.iterator();
    }

    public Iterator<TuxresourceEntity> getResQue(String siteName) {
        BlockingQueue<TuxresourceEntity> blockingQueue =  QueuesHolder.getQueue(siteName + TUX_RES, point);
        return blockingQueue.iterator();
    }

    public Iterator<TuxsvrStatsEntity> getSvrStatsQue(String siteName) {
        BlockingQueue<TuxsvrStatsEntity> blockingQueue =  QueuesHolder.getQueue(siteName + TUX_SVR_STAT, point);
        return blockingQueue.iterator();
    }

}
