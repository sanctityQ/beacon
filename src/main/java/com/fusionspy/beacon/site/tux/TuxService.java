package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.site.AlarmMessageFormat;
import com.fusionspy.beacon.site.tux.dao.*;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.system.service.SystemService;
import com.sinosoft.one.monitor.action.domain.ActionService;
import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.attribute.domain.AttributeCache;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.attribute.model.AttributeAction;
import com.sinosoft.one.monitor.common.AlarmSource;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesCache;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.domain.ThresholdService;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.threshold.model.Threshold;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * tux service
 * User: qc
 * Date: 11-8-31
 * Time: 下午1:36
 */
@Component
public class TuxService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TuxcltsDao tuxcltsDao;

    @Autowired
    private TuxcltsStatsDao tuxcltsStatsDao;

    @Autowired
    private SystemService systemService;

    @Autowired
    private TuxQueueDao tuxQueueDao;

    @Autowired
    private TuxQueueStatsDao tuxQueueStatsDao;

    @Autowired
    private TuxSvrsDao tuxSvrsDao;

    @Autowired
    private TuxSvrsStatsDao tuxSvrsStatsDao;

    @Autowired
    private TuxResourceDao tuxResourceDao;


    @Autowired
    private AttributeCache attributeCache;
    @Autowired
    private ResourcesCache resourcesCache;
    @Autowired
    private ThresholdService thresholdService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private ActionService actionService;


    /**
     * process intime data
     * @param siteName
     * @param period
     * @param prvData
     */
    @Transactional
    public void processInTimeData(String siteName,int period,TuxHisData prvData) {
        logger.debug("start process data");
        SiteSettings siteSettings =  systemService.getSiteSetting(siteName);
        processData(siteName, period,  prvData,siteSettings);
    }

    ProcessInTimeResult processData(String siteName,int period, TuxHisData prvData,SiteSettings siteSettings){

      //  ProcessInTimeResult processInTimeResult = new ProcessData(siteName,period,tuxInTimeData,prvData,siteSettings,recordDB).process().log(siteName);
      //  TuxAlertMessage message =processInTimeResult.getTuxAlertMessage();
        //alarm采用每个属性都是一个独立的alarm方式


        return new ProcessData(siteName,period,prvData,siteSettings).process().log(siteName);//.alarm(siteSettings.getAlertType());
    }

    Attribute getAttribute(String siteName,AttributeName attributeName){
        Resource resource = resourcesCache.getResource(siteName);
        return attributeCache.getAttribute(resource.getResourceType(), attributeName.name());
    }


    private class ProcessData {

        private final Resource resource;

        private SiteSettings siteSettings;

        private TuxInTimeData tuxInTimeData;

        private Date now = new Date();

        private int period;

        private int svrSize;

        private int cltSize;

        private int queSize;

        private float svrCpuUsed;

        private int svrMemUsed;

        private int rqdone;

        private TuxHisData tuxHisData;

        private ProcessInTimeResult processResult;

        private String siteName;

        private boolean recordDB;

        ProcessData(String siteName,int period,TuxHisData prvHisData,SiteSettings siteSettings) {
            this.siteName = siteName;
            this.siteSettings = siteSettings;
            this.tuxInTimeData = prvHisData.getTuxInTimeData();
            this.period = period;
            this.tuxHisData =  prvHisData;
            this.recordDB = siteSettings.getDataSave().getSaveAll().equals(DataSave.SaveFlag.ENABLE);
            processResult = new ProcessInTimeResult(siteName);
            this.resource = resourcesCache.getResource(siteSettings.getSiteName());
        }

        ProcessInTimeResult process(){
           processResult.setReceiveDate(now);
           if(alarmStopRunning()){
               Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.SystemStop.name());
               //停机发告警
               alarmMessage(this.resource,attribute,this.siteName,SeverityLevel.CRITICAL,
                       processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_STOP));
               return processResult;
           }
           this.processClt();
           this.processQueue();
           this.processServer();
           this.processRes();
           this.recordHisData();
           return processResult;
        }

        private void recordHisData(){
             this.tuxHisData.setRqDoneCount(rqdone);
             this.tuxHisData.setProcessResult(processResult);
        }

        private void processClt() {
            int busycount = 0;
            boolean isSave = checkSaveClts(siteSettings);
            List<TuxcltsEntity> list = tuxInTimeData.getClients();
            this.cltSize = list.size();
            for (Iterator<TuxcltsEntity> iter = list.iterator(); iter.hasNext(); ) {
                TuxcltsEntity tuxcltsEntity = iter.next();
                if (Conditions.Status.BUSY.equals(tuxcltsEntity.getClientstatus())) {
                    busycount++;
                }
                if (isSave) {
                    tuxcltsEntity.setRectime(now);
                    tuxcltsEntity.setSitename(this.siteName);
                    tuxcltsDao.save(tuxcltsEntity);
                }
                //record saved data
                processResult.recordTuxClts(tuxcltsEntity);
            }
            TuxcltsStatsEntity tuxcltsStats = new TuxcltsStatsEntity();
            tuxcltsStats.setSitename(siteSettings.getSiteName());
            tuxcltsStats.setRectime(now);
            tuxcltsStats.setBusycount(String.valueOf(busycount));
            tuxcltsStats.setTotalcount(String.valueOf(cltSize));
            //record stats result
            processResult.recordTuxCltsStats(tuxcltsStats);
            if (recordDB) tuxcltsStatsDao.save(tuxcltsStats);
        }

        private void processQueue() {
            List<TuxquesEntity> tuxquesList = this.tuxInTimeData.getQueues();

            if (tuxquesList == null)
                return;
            this.queSize = tuxquesList.size();
            boolean top10 = getQueType().equals(DataSave.Queue.TOP10);
            Collections.sort(tuxquesList, TuxDataComparatorFactory.getComparatorQue());

            int queuedSum = 0;

            Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.QueuedNumber.name());
            if(attribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.QueuedNumber.name());
            }
            //获取阈值
            Threshold threshold = thresholdService.queryThreshold(this.siteSettings.getSiteName(), attribute.getId());
            SeverityLevel severityLevel = SeverityLevel.UNKNOWN;
            for (int i = 0; i < this.queSize; i++) {
                TuxquesEntity t = tuxquesList.get(i);
                t.setSitename(siteSettings.getSiteName());
                t.setRectime(now);
                queuedSum += t.getQueued();
                if (!top10 || i < 10) {
                    //record saved tuxquesEntitys
                    processResult.recordTuxQue(t);
                    if(recordDB)
                      tuxQueueDao.save(t);
                }
                // 取alarm级别最高的作为报警级别
                SeverityLevel temp =  queueAlarm(t, threshold);
                /*TODO 警告级别正常来说是需要分类的，这块有bug，如，a是严重、b是正常，c是警告由于按照最高级别走，所以变成 abc均是严重 */
                if(severityLevel.ordinal()>temp.ordinal()){
                    severityLevel =  temp;
                }
            }
            alarmMessage(this.resource,attribute,this.siteName,severityLevel,processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_QUE));

            TuxqueStatsEntity stats = new TuxqueStatsEntity();
            stats.setSitename(siteSettings.getSiteName());
            stats.setQueuenum(queuedSum);
            stats.setRectime(now);
            processResult.recordTuxQuesStats(stats);
            if (recordDB) tuxQueueStatsDao.save(stats);
        }

        private void processServer() {
            List<TuxsvrsEntity> svrs = this.tuxInTimeData.getServers();
            if (svrs == null)
                return;
            DataSave.Server type = getServerType();
            Collections.sort(svrs, TuxDataComparatorFactory.getComparatorServer(type));
            this.svrSize = svrs.size();
            this.rqdone=0;

            Attribute cpuAttribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.CPUUtilization.name());
            Attribute memAttribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.UsedMemory.name());
            //TODO 获取不到属性，是否直接报错
            if(cpuAttribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.CPUUtilization.name());
            }
            if(cpuAttribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.CPUUtilization.name());
            }
            //获取阈值
            Threshold cpuThreshold = thresholdService.queryThreshold(this.siteSettings.getSiteName(), cpuAttribute.getId());
            Threshold memThreshold = thresholdService.queryThreshold(this.siteSettings.getSiteName(), memAttribute.getId());

            SeverityLevel cpuSeverityLevel = SeverityLevel.UNKNOWN;
            SeverityLevel memSeverityLevel = SeverityLevel.UNKNOWN;

            Map<String,TuxsvrsEntity> processMap = new HashMap<String,TuxsvrsEntity>();
            for(int i=0;i<svrSize;i++){
                TuxsvrsEntity svr = svrs.get(i);
                svr.setSitename(this.siteSettings.getSiteName());
                svr.setRectime(now);
                rqdone+=svr.getRqdone();
                this.svrCpuUsed+=svr.getCpuuse();
                this.svrMemUsed+=svr.getMemoryuse();
                // 取alarm级别最高的作为报警级别
                SeverityLevel memTemp =  this.memAlarm(svr, memThreshold);
                if(memSeverityLevel.ordinal()>memTemp.ordinal()){
                    memSeverityLevel =  memTemp;
                }
                SeverityLevel cpuTemp =  this.cpuAlarm(svr, cpuThreshold);
                if(cpuSeverityLevel.ordinal()>cpuTemp.ordinal()){
                    cpuSeverityLevel =  cpuTemp;
                }
                processMap.put(genProcessFlag(svr.getProgname(), svr.getProcessid()), svr);

                if(type.equals(DataSave.Server.ALL)||i<10){
                     if(recordDB)
                       tuxSvrsDao.save(svr);
                }
                //record  tux svr
               processResult.recordTuxSvrs(svr);
            }

            alarmMessage(this.resource,cpuAttribute,this.siteName,cpuSeverityLevel,processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_CPU));
            alarmMessage(this.resource,memAttribute,this.siteName,memSeverityLevel,processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_MEM));

            serverDiedAlarmAndNoTransAndLongBusy(processMap);
            TuxsvrStatsEntity stats = new TuxsvrStatsEntity();
            stats.setSitename(this.siteSettings.getSiteName());
            stats.setRqdone(rqdone);
            stats.setRectime(now);
            int preRqDoneCount = this.tuxHisData.getRqDoneCount();
            //first time,default is -1
            if(preRqDoneCount == -1){
                stats.setTpsdone(0);
            }else{
               stats.setTpsdone((rqdone - preRqDoneCount)/period);
            }
            //recourd tux svrstats
            processResult.recordTuxSvrStats(stats);
            if(recordDB)
               tuxSvrsStatsDao.save(stats);
        }

        private SeverityLevel queueAlarm(TuxquesEntity que, Threshold threshold) {

            if(threshold==null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match(String.valueOf(que.getQueued()));
            if(severityLevel!= SeverityLevel.UNKNOWN) {
                processResult.addQueueAlarm(que.getProgname());
            }
            return severityLevel;
        }

        private SeverityLevel memAlarm(TuxsvrsEntity svr, Threshold threshold){
            //int memLimit = this.siteSettings.getConditions().getProcessMemory().getUsedMemory();
            if(threshold == null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match(String.valueOf(svr.getMemoryuse()));
            if(severityLevel!=SeverityLevel.UNKNOWN){
                processResult.addMemAlarm(svr.getProgname());
            }
            return severityLevel;
        }

        private SeverityLevel cpuAlarm(TuxsvrsEntity svr,Threshold threshold){
            //  float cpuLimit = this.siteSettings.getConditions().getOsCpu().getUsed();
            if(threshold == null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match(String.valueOf(svr.getCpuuse()));
            //  threshold.match();     cpuLimit<(100-svr.getCpuuse())
            if(severityLevel!=SeverityLevel.UNKNOWN){
                processResult.addCpuAlarm(svr.getProgname());
            }
            return severityLevel;
        }

        /**
         * generate process id flag
         * @param progName
         * @param processId
         * @return
         */
        private String genProcessFlag(String progName,String processId){
            return progName+"_beacon_"+processId;
        }

        private boolean alartEnable(String alertValue){
           return  alertValue.equals("1")?true:false;
        }


        private void serverDiedAlarmAndNoTransAndLongBusy(Map<String,TuxsvrsEntity> processMap){

            List<TuxsvrsEntity>  oldSvrs= this.tuxHisData.getTuxInTimeData().getServers();

            //no old data
            if(oldSvrs == null)
                return;

            int prvSvrSize = oldSvrs.size();

            //alarm server
//            String diedServerNames = this.siteSettings.getConditions().getServerDied().getName();
//            String noTransServerNames =  this.siteSettings.getConditions().getServerNoTrans().getName();
//            String busyServerNames =  this.siteSettings.getConditions().getServerBusy().getName();

            //获取serverDied属性
            Attribute serverDiedAttribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.ServerDied.name());
            if(serverDiedAttribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.ServerDied.name());
            }
            //获取阈值
            Threshold diedServerThreshold = thresholdService.queryThreshold(this.siteSettings.getSiteName(), serverDiedAttribute.getId());
            SeverityLevel diedServerSeverityLevel = SeverityLevel.UNKNOWN;
            //获取serverDied属性
            Attribute noTransAttribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.ServerNoTrans.name());
            Attribute busyServerAttribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.ServerBusy.name());

            if(noTransAttribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.ServerNoTrans.name());
            }
            //获取阈值
            Threshold noTransThreshold = thresholdService.queryThreshold(this.siteSettings.getSiteName(), noTransAttribute.getId());
            Threshold busyServerThreshold = thresholdService.queryThreshold(this.siteSettings.getSiteName(), busyServerAttribute.getId());
            SeverityLevel noTransSeverityLevel = SeverityLevel.UNKNOWN;
            SeverityLevel busyServerSeverityLevel = SeverityLevel.UNKNOWN;

            for (TuxsvrsEntity old : oldSvrs) {
                String processId = genProcessFlag(old.getProgname(), old.getProcessid());
                //new data not contain old data
                if (!processMap.containsKey(processId)) {
                    if(diedServerThreshold==null)
                        continue;
                    SeverityLevel severityLevel = diedServerThreshold.match(old.getProgname());

                    if(diedServerSeverityLevel.ordinal()>severityLevel.ordinal()){
                        diedServerSeverityLevel = severityLevel;
                    }

                    //config server data contains serverName
                    if (severityLevel!=SeverityLevel.UNKNOWN) {
                        processResult.addDiedServerName(old.getProgname()+"["+old.getProcessid()+"]");
                    }

                }
                //contains data
                else {
                    TuxsvrsEntity now = processMap.get(processId);
                    if (now.getRqdone() == old.getRqdone()) {


                        //no trans
                        if (noTransThreshold!=null) {
                            SeverityLevel severityLevel = noTransThreshold.match(now.getProgname());
                            if(noTransSeverityLevel.ordinal()>severityLevel.ordinal()){
                                noTransSeverityLevel = severityLevel;
                            }
                            if(now.getCurrenctsvc().equals("IDLE")&&severityLevel!=SeverityLevel.UNKNOWN){
                                processResult.addNoTrans(now.getProgname()+"["+now.getProcessid()+"]");
                            }
                        }
                        //long busy
                        if(busyServerThreshold!=null){
                            SeverityLevel severityLevel = busyServerThreshold.match(now.getProgname());
                             if(busyServerSeverityLevel.ordinal()>severityLevel.ordinal()){
                                busyServerSeverityLevel = severityLevel;
                            }
                            if(!now.getCurrenctsvc().equals("IDLE")&&!old.getCurrenctsvc().equals("IDLE")&&severityLevel!=SeverityLevel.UNKNOWN){
                                processResult.addLongBusy(now.getProgname()+"["+now.getProcessid()+"]");
                            }
                        }
                    }
                }
            }
            alarmMessage(this.resource,serverDiedAttribute,this.siteName,diedServerSeverityLevel,processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_DIED));
            alarmMessage(this.resource,noTransAttribute,this.siteName,noTransSeverityLevel,processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_NOTRAN));
            alarmMessage(this.resource,busyServerAttribute,this.siteName,busyServerSeverityLevel,processResult.getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_BUSY));
        }

        //return tux stop signal
        private boolean alarmStopRunning(){
            if(this.tuxInTimeData.getTuxError()!=null&&StringUtils.isNotEmpty(this.tuxInTimeData.getTuxError().getError())) {
                //TODO 待讨论是否还需要判断系统已经停止，如果已经停止，代表当前监控的tuxedo已经不可用，应该立即记录
                //tux stop alarm flag
                //            boolean alarmStop = alartEnable(this.siteSettings.getConditions().getSystemStop().getAlert());
                // if alarm stop need rcord his data
                //  if(true){
                //tux stop info
                processResult.setStopServer(true, this.siteSettings.getSiteName());
                this.recordHisData();
                //   }
                return true;
            }
            return false;
        }

        //res process
        private void processRes(){
            TuxSysData sysData = this.tuxInTimeData.getTuxSysData();
            String freeMem = StringUtils.substringBeforeLast(sysData.getFreeMem(),"M");
            TuxresourceEntity resource = new TuxresourceEntity();
            resource.setSitename(this.siteSettings.getSiteName());
            resource.setAllsvrcpuuse(this.svrCpuUsed);
            resource.setAllsvrmemused(this.svrMemUsed);
            resource.setCpuidle(sysData.getIdleCPU());
            resource.setMemfree(Float.valueOf(freeMem));
            resource.setRectime(now);
            resource.setTuxrunclt(this.cltSize);
            resource.setTuxrunqueue(this.queSize);
            resource.setTuxrunsvr(this.svrSize);
            processResult.recordTuxRes(resource);
            if(recordDB){
                tuxResourceDao.save(resource);
            }
        }

        private DataSave.Server getServerType() {
            return siteSettings.getDataSave().getSaveServerData();
        }

        private DataSave.Queue getQueType() {
            return siteSettings.getDataSave().getSaveQueueData();
        }

        private boolean checkSaveClts(SiteSettings siteSettings) {
            return recordDB&&siteSettings.getDataSave().getSaveAllClient().equals(DataSave.SaveFlag.ENABLE);
        }





    }

    private String controlFixedLength(String message){
        String back=null;
        if(message.length()>500){
            back =  StringUtils.substring(message,0,500);
            back =back + "...";
        }else {
            back = message;
        }
        return back;
    }

     void alarmMessage(Resource resource,Attribute attribute,String siteName, SeverityLevel severityLevel,String message){
        if(attribute==null||severityLevel == SeverityLevel.UNKNOWN ||StringUtils.isBlank(message))
            return;
        Alarm alarm = new Alarm(UUID.randomUUID().toString().replaceAll("-", ""));
        alarm.setAttributeId(attribute.getId());
        alarm.setMonitorId(siteName);
        alarm.setAlarmSource(AlarmSource.APP_SERVER);
        alarm.setMonitorType(ResourceType.Tuxedo.name());
        alarm.setSeverity(severityLevel);
        alarm.setMessage(controlFixedLength(message));
        alarm.setCreateTime(new Date());
//        alarm.setSubResourceId(siteName);
//        alarm.setSubResourceType(ResourceType.APP_SERVER);
        alarmService.saveAlarm(alarm);

        //发送邮件
        List<AttributeAction> thresholdAttributeActions = actionService.queryAttributeActions(resource.getResourceId(), attribute.getId(), severityLevel);

        //处理动作
        if(thresholdAttributeActions != null && thresholdAttributeActions.size() > 0) {
            actionService.doActions(thresholdAttributeActions, resource, attribute, severityLevel, message);
        }
    }

    public static void main(String[] args){
        System.out.println(new Date(1383989138321l));
    }

}
