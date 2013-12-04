package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.AlarmObserver;
import com.fusionspy.beacon.site.SiteInfoLogger;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.site.tux.entity.AlertType;
import com.fusionspy.beacon.site.tux.entity.DataSave;
import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * process result
 * User: qc
 */
public class ProcessInTimeResult {

    private static Logger logger = LoggerFactory.getLogger(ProcessInTimeResult.class);

    public static final ProcessInTimeResult EMPTY;


    private SiteInfoLogger siteInfoLogger = SiteInfoLogger.getInstance();

    private AlarmObserver alarmObserver = AlarmObserver.getInstance();

    private String siteName;

    private DataTrace dataTrace = DataTrace.getInstance();


    static {
        EMPTY = new ProcessInTimeResult(StringUtils.EMPTY);
        ArrayList arrayList = new ArrayList();
        //tux resource empty
        TuxresourceEntity tuxresourceEntity = new TuxresourceEntity();
        tuxresourceEntity.setAllsvrcpuuse(0);
        tuxresourceEntity.setAllsvrmemused(0);
        tuxresourceEntity.setCpuidle(0);
        tuxresourceEntity.setMemfree(0);
        tuxresourceEntity.setRectime(new Date());
        tuxresourceEntity.setSitename(StringUtils.EMPTY);
        tuxresourceEntity.setTuxrunclt(0);
        tuxresourceEntity.setTuxrunqueue(0);
        tuxresourceEntity.setTuxrunsvr(0);
        EMPTY.recordTuxRes(tuxresourceEntity);
        EMPTY.setStopServer(true,StringUtils.EMPTY);
    }


    public ProcessInTimeResult(String siteName) {
        this.siteName = siteName;
    }

    //save tux clts
    private ArrayList<TuxcltsEntity> tuxClts;

    //tuxcltsstats
    private TuxcltsStatsEntity tuxcltsStats;

    private ArrayList<TuxquesEntity> tuxQues;

    private TuxqueStatsEntity tuxQuesStats;

    private ArrayList<TuxsvrsEntity> tuxSvrs;

    private TuxsvrStatsEntity tuxSvrStats;

    private TuxresourceEntity tuxRes;

    //que top 5
    private ArrayList<TuxquesEntity> top5Que = new ArrayList<TuxquesEntity>();

    private TuxAlertMessage tuxAlertMessage = new TuxAlertMessage();

    private AlarmService alarmService;

    /**
     * get top5 que
     *
     * @returnArrayList
     */
    public ArrayList<TuxquesEntity> getTop5Que() {
        return top5Que;
    }

    private ArrayList<TuxsvrsEntity> top5Tran = new ArrayList<TuxsvrsEntity>();

    public ArrayList<TuxsvrsEntity> getTop5Tran() {
        if (top5Tran.isEmpty() && this.tuxSvrs != null) {
            Collections.sort(this.tuxSvrs, TuxDataComparatorFactory.getComparatorServer(DataSave.Server.RQDONE));
            for (int i = 0, n = this.tuxSvrs.size(); i < n && i < 5; i++) {
                this.top5Tran.add(this.tuxSvrs.get(i));
            }
        }
        return this.top5Tran;
    }

    private ArrayList<TuxsvrsEntity> top5Mem = new ArrayList<TuxsvrsEntity>();

    public ArrayList<TuxsvrsEntity> getTop5Mem() {
        //  logger.debug("top mem empty = {},tuxSvrs = {}" ,top5Mem.isEmpty(),this.tuxSvrs);
        if (top5Mem.isEmpty() && this.tuxSvrs != null) {
            Collections.sort(this.tuxSvrs, TuxDataComparatorFactory.getComparatorServer(DataSave.Server.MEMORY));
            for (int i = 0, n = this.tuxSvrs.size(); i < n && i < 5; i++) {
                this.top5Mem.add(this.tuxSvrs.get(i));
            }
        }
        return this.top5Mem;
    }


    public Iterator<TuxcltsStatsEntity> getCltsStatsQue() {
        return this.dataTrace.getCltStatsQue(this.siteName);
    }

    public Iterator<TuxresourceEntity> getResQue() {
        return this.dataTrace.getResQue(this.siteName);
    }

    public Iterator<TuxsvrStatsEntity> getSvrStatsQue() {
        return this.dataTrace.getSvrStatsQue(this.siteName);
    }

    /**
     * StopAlarmSignal
     *
     * @return
     */
    public boolean isStopAlarmSignal() {
        return stopAlarmSignal;
    }

    private boolean stopAlarmSignal;


    /**
     * set stop server info
     *
     * @param stopAlarmSignal
     * @param siteName
     */
    void setStopServer(boolean stopAlarmSignal, String siteName) {
        this.stopAlarmSignal = stopAlarmSignal;
        this.tuxAlertMessage.setStopServerName(siteName);
    }

    private <T> ArrayList add(ArrayList<T> list, T e) {
        if (list == null)
            list = new ArrayList<T>();
        list.add(e);
        return list;
    }

    /**
     * record save tuxcltsEntity
     *
     * @param tuxcltsEntity
     */
    void recordTuxClts(TuxcltsEntity tuxcltsEntity) {
        this.tuxClts = add(this.tuxClts, tuxcltsEntity);
    }

    /**
     * record save tuxcltsStats
     *
     * @param tuxcltsStats
     */
    void recordTuxCltsStats(TuxcltsStatsEntity tuxcltsStats) {
        this.tuxcltsStats = tuxcltsStats;
        this.dataTrace.addTuxCltStats(this.siteName,tuxcltsStats);
    }

    void recordTuxQue(TuxquesEntity tuxquesEntity) {
        if (top5Que.size() < 5) {
            top5Que.add(tuxquesEntity);
        }
        this.tuxQues = add(this.tuxQues, tuxquesEntity);
    }

    void recordTuxQuesStats(TuxqueStatsEntity stats) {
        this.tuxQuesStats = stats;
    }

    void recordTuxSvrs(TuxsvrsEntity svr) {
        this.tuxSvrs = add(this.tuxSvrs, svr);
    }

    void recordTuxSvrStats(TuxsvrStatsEntity tuxSvrStats) {
        this.tuxSvrStats = tuxSvrStats;
        this.dataTrace.addTuxSvrStats(this.siteName,tuxSvrStats);
    }

    void recordTuxRes(TuxresourceEntity tuxRes) {
        this.tuxRes = tuxRes;
        if(StringUtils.isNotEmpty(this.siteName))
            this.dataTrace.addTuxRes(this.siteName,tuxRes);
    }


    public ArrayList<TuxcltsEntity> getTuxClts() {
        return tuxClts;
    }

    public TuxcltsStatsEntity getTuxcltsStats() {
        return tuxcltsStats;
    }

    public ArrayList<TuxquesEntity> getTuxQues() {
        return tuxQues;
    }

    public TuxqueStatsEntity getTuxQuesStats() {
        return tuxQuesStats;
    }

    public ArrayList<TuxsvrsEntity> getTuxSvrs() {
        return tuxSvrs;
    }

    public TuxsvrStatsEntity getTuxSvrStats() {
        return tuxSvrStats;
    }

    public TuxresourceEntity getTuxRes() {
        return tuxRes;
    }

    void setReceiveDate(Date now) {
        this.tuxAlertMessage.setReceiveDate(now);
    }

    void addQueueAlarm(String progname) {
        this.tuxAlertMessage.addQueueAlarm(progname);
    }

    void addMemAlarm(String progname) {
        this.tuxAlertMessage.addMemAlarm(progname);
    }

    void addCpuAlarm(String progname) {
        this.tuxAlertMessage.addCpuAlarm(progname);
    }

    void addDiedServerName(String progname) {
        this.tuxAlertMessage.addDiedServerName(progname);
    }

    void addNoTrans(String progname) {
        this.tuxAlertMessage.addNoTrans(progname);
    }

    public void addLongBusy(String progname) {
        this.tuxAlertMessage.addLongBusy(progname);
    }

    public  TuxAlertMessage getTuxAlertMessage(){
        return this.tuxAlertMessage;
    }


    ProcessInTimeResult log(String siteName) {
        for (String message : this.tuxAlertMessage.getMessages()) {
            logMessage(siteName, message);
        }
        return this;
    }

    private void logMessage(String siteName, String message) {

        //log
        logger.info(message);
        //message add queue
        siteInfoLogger.logInf(siteName, message);
    }

    public ProcessInTimeResult alarm(AlertType alertType) {

        alarmObserver.alarm(alertType, this.tuxAlertMessage.getAlarmMessage());
        return this;
    }

}
