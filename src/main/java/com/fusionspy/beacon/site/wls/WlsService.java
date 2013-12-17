package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.AlarmMessageFormat;
import com.fusionspy.beacon.site.tux.ProcessInTimeResult;
import com.fusionspy.beacon.site.tux.TuxDataComparatorFactory;
import com.fusionspy.beacon.site.tux.TuxHisData;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.site.wls.dao.*;
import com.fusionspy.beacon.site.wls.entity.*;
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
import com.sinosoft.one.monitor.resources.domain.ResourcesService;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.domain.ThresholdService;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.threshold.model.Threshold;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.runtime.resource.ResourceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-20
 * Time: 下午11:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WlsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private WlsSysrecDao wlsSysrecDao;
    @Autowired
    private WlsEjbCacheDao wlsEjbCacheDao;
    @Autowired
    private WlsEjbPoolDao wlsEjbPoolDao;
    @Autowired
    private WlsJdbcDao wlsJdbcDao;
    @Autowired
    private WlsJmsDao wlsJmsDao;
    @Autowired
    private WlsJvmDao wlsJvmDao;
    @Autowired
    private WlsResourceDao wlsResourceDao;
    @Autowired
    private WlsThreadDao wlsThreadDao;
    @Autowired
    private WlsWebappDao wlsWebappDao;
    @Autowired
    private WlsServerDao wlsServerDao;
    @Autowired
    private AttributeCache attributeCache;
    @Autowired
    private ResourcesCache resourcesCache;
    @Autowired
    private ThresholdService thresholdService;

    /**
     * process init data
     * @param wlsIniData
     * @return
     */
    @Transactional
    public int processInitData(WlsIniData wlsIniData) {
        wlsIniData.defaultData();
        WlsSysrec wlsSysrec = wlsIniData.getWlsSysrec();
        wlsSysrec.setRecTime(new Date());
        wlsSysrecDao.save(wlsSysrec);
        return 1;
    }


    /**
     * process intime data
     * @param siteName
     * @param period
     * @param inTimeData
     * @param hisData
     */
    @Transactional
    public void processInTimeData(String siteName,int period,WlsInTimeData inTimeData,WlsHisData hisData) {
        inTimeData.defaultData();  //赋默认值
        new ProcessData(siteName, period, inTimeData, hisData).process();

    }

    private Integer parsetInt(String str) {
        try{
           return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    @Transactional
    public void save(WlsServer wlsServer) {
        wlsServer.setRecTime(new Date());
        wlsServerDao.save(wlsServer);
        Resource resource = new Resource();
        resource.setResourceId(wlsServer.getServerName());
        resource.setResourceName(wlsServer.getServerName());
        resource.setResourceType(ResourceType.WEBLOGIC.toString());
        resourcesService.saveResource(resource);

    }

    @Transactional
    public void delete(String serverName) {
        //TODO delete--Resource
        wlsServerDao.delete(serverName);
    }

    @Transactional(readOnly = true)
    public WlsServer getSite(String serverName) {
        return wlsServerDao.findOne(serverName);
    }

    public List<WlsServer> getSites() {
        return (List<WlsServer>) wlsServerDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<WlsServer> list() {
        return (List<WlsServer>) wlsServerDao.findAll();
    }

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private ActionService actionService;

    /**
     * 记录实时监控数据
     */
    private class ProcessData {
        private int period;
        private String siteName;
        private WlsInTimeData inTimeData;
        private WlsHisData hisData;
        private Resource resource;
        private Date now = new Date();

        private WlsAlertMessage wlsAlertMessage = new WlsAlertMessage();

        ProcessData(String siteName, int period, WlsInTimeData inTimeData, WlsHisData hisData) {
            this.siteName = siteName;
            this.inTimeData = inTimeData;
            this.period = period;
            this.hisData =  hisData;
            this.resource = resourcesCache.getResource(siteName);
        }

        public void process() {
            this.processJvm();
            this.processThread();
            this.processJdbc();
            this.processEjbCache();
            this.processEjbPool();
            this.processJms();
            this.processWebapp();
            this.processWlsResource();
            WlsError error = inTimeData.getError();
            if(error != null && StringUtils.isNotBlank(error.getErrMsg().trim())) {
                Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.SystemStop.name());
                //停机发告警
                alarmMessage(resource, attribute, siteName, SeverityLevel.CRITICAL, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_STOP));
            }
            hisData.setWlsInTimeData(inTimeData);
            hisData.addWlsIntimeData(siteName, inTimeData);
        }

        /**
         *
         */
        public void processJvm() {
            List<WlsJvm> jvmRuntimes = this.inTimeData.getJvmRuntimes();
            if (jvmRuntimes == null)
                return;
            Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.FreeHeap.name());
            if(attribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.FreeHeap.name());
            }
            //获取阈值
            Threshold threshold = thresholdService.queryThreshold(siteName, attribute.getId());
            SeverityLevel severityLevel = SeverityLevel.UNKNOWN;
            for (int i = 0; i < jvmRuntimes.size(); i++) {
                WlsJvm t = jvmRuntimes.get(i);
                // 取alarm级别最高的作为报警级别
                SeverityLevel temp =  jvmAlarm(t, threshold);
                if(severityLevel.ordinal()>temp.ordinal()){
                    severityLevel =  temp;
                }
            }
            alarmMessage(resource, attribute, siteName, severityLevel, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_HEAP));
            wlsJvmDao.save(jvmRuntimes);
        }

        private void processThread() {
            List<WlsThread> threadPoolRuntimes = inTimeData.getThreadPoolRuntimes();
            if (threadPoolRuntimes == null)
                return;

            Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.ThreadUtilization.name());
            if(attribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.ThreadUtilization.name());
            }
            //获取阈值
            Threshold threshold = thresholdService.queryThreshold(siteName, attribute.getId());
            SeverityLevel severityLevel = SeverityLevel.UNKNOWN;
            for (int i = 0; i < threadPoolRuntimes.size(); i++) {
                WlsThread t = threadPoolRuntimes.get(i);
                // 取alarm级别最高的作为报警级别
                SeverityLevel temp =  threadAlarm(t, threshold);
                if(severityLevel.ordinal()>temp.ordinal()){
                    severityLevel =  temp;
                }
            }
            alarmMessage(resource, attribute, siteName, severityLevel, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_THREAD));
            wlsThreadDao.save(threadPoolRuntimes);
        }

        private void processJdbc() {
            List<WlsJdbc> jdbcDataSourceRuntimes = inTimeData.getJdbcDataSourceRuntimes();
            if (jdbcDataSourceRuntimes == null)
                return;

            Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.JdbcUtilization.name());
            if(attribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.JdbcUtilization.name());
            }
            //获取阈值
            Threshold threshold = thresholdService.queryThreshold(siteName, attribute.getId());
            SeverityLevel severityLevel = SeverityLevel.UNKNOWN;
            for (int i = 0; i < jdbcDataSourceRuntimes.size(); i++) {
                WlsJdbc t = jdbcDataSourceRuntimes.get(i);
                // 取alarm级别最高的作为报警级别
                SeverityLevel temp =  jdbcAlarm(t, threshold);
                if(severityLevel.ordinal()>temp.ordinal()){
                    severityLevel =  temp;
                }
            }
            alarmMessage(resource, attribute, siteName, severityLevel, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_JDBC));
            wlsJdbcDao.save(jdbcDataSourceRuntimes);
        }

        private void processWebapp() {
            List<WlsWebapp> componentRuntimes = inTimeData.getComponentRuntimes();
            for(WlsWebapp webapp : componentRuntimes) {
                webapp.setOpenSessionsHigh(parsetInt(webapp.getOpenSessionsHighCount()));
                webapp.setOpenSessionsCurrent(parsetInt(webapp.getOpenSessionsCurrentCount()));
                webapp.setSessionsOpenedTotal(parsetInt(webapp.getSessionsOpenedTotalCount()));
            }
            wlsWebappDao.save(componentRuntimes);

        }

        private void processJms() {
            List<WlsJms> jmsServers = inTimeData.getJmsServers();
            wlsJmsDao.save(jmsServers);
        }

        private void processEjbPool() {
            List<WlsEjbpool> poolRuntimes = inTimeData.getPoolRuntimes();
            wlsEjbPoolDao.save(poolRuntimes);
        }

        private void processEjbCache() {
            List<WlsEjbcache> cacheRuntime = inTimeData.getCacheRuntime();
            wlsEjbCacheDao.save(cacheRuntime);
        }

        private void processWlsResource() {
            WlsSysrec sysrec = hisData.getWlsIniData().getWlsSysrec();
            //汇总信息处理
            WlsResource wlsResource = inTimeData.getResource();
            Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.CPUUtilization.name());
            if(attribute == Attribute.EMPTY){
                logger.error("{}'s attribute is empty,fix it!",AttributeName.CPUUtilization.name());
            }
            String mem = wlsResource.getMem();
            wlsResource.setRunServerNumber(hisData.getWlsInTimeData().getJvmRuntimes().size()); //服务器数量
            wlsResource.setServerNumber(sysrec.getServerNum()); //运行服务器数量
            if(StringUtils.isNotBlank(wlsResource.getCpu())) { //CPU空闲
                wlsResource.setCpuIdle(Integer.parseInt(wlsResource.getCpu().trim()));
            } else {
                wlsResource.setCpuIdle(0);
            }
            wlsResource.setOsType(sysrec.getOsType());
            if(StringUtils.isNotBlank(mem)) { //获取当前'M'之前的数值
                wlsResource.setMemFree(mem.substring(0, mem.indexOf('M')));
            } else {
                wlsResource.setMemFree("0");
            }
            //获取阈值
            Threshold threshold = thresholdService.queryThreshold(siteName, attribute.getId());
            SeverityLevel severityLevel = cpuAlarm(wlsResource, threshold);
            alarmMessage(resource, attribute, siteName, severityLevel, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_CPU));
            wlsResourceDao.save(wlsResource);
        }

        /**
         * java内存使用率
         * @param jvm
         * @param threshold
         * @return
         */
        private SeverityLevel jvmAlarm(WlsJvm jvm, Threshold threshold){
            if(threshold==null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match(jvm.getFreePercent());
            if(severityLevel != SeverityLevel.UNKNOWN) {
                wlsAlertMessage.addHeapAlarm(jvm.getServerName());
            }
            return severityLevel;
        }

        /**
         * 线程使用率
         * @param thread
         * @param threshold
         * @return
         */
        private SeverityLevel threadAlarm(WlsThread thread, Threshold threshold){
            if(threshold==null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match(thread.getThdusage().toString());
            if(severityLevel != SeverityLevel.UNKNOWN) {
                wlsAlertMessage.addThreadAlarm(thread.getServerName());
            }
            return severityLevel;
        }

        /**
         * 数据库连接诶
         * @param jdbc
         * @param threshold
         * @return
         */
        private SeverityLevel jdbcAlarm(WlsJdbc jdbc, Threshold threshold){
            if(threshold==null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match(jdbc.getJdbcusage().multiply(new BigDecimal("100")).toString());
            if(severityLevel != SeverityLevel.UNKNOWN) {
                wlsAlertMessage.addJdbcAlarm(jdbc.getServerName());
            }
            return severityLevel;
        }

        /**
         * CPU使用率
         * @param wlsResource
         * @param threshold
         * @return
         */
        private SeverityLevel cpuAlarm(WlsResource wlsResource, Threshold threshold){
            if(threshold==null)
                return SeverityLevel.UNKNOWN;
            SeverityLevel severityLevel = threshold.match((100-wlsResource.getCpuIdle())+"");
            if(severityLevel != SeverityLevel.UNKNOWN) {
                wlsAlertMessage.addCpuAlarm(siteName);
            }
            return severityLevel;
        }

        private void alarmMessage(Resource resource, Attribute attribute,String siteName, SeverityLevel severityLevel,String message){
            if(attribute==null||severityLevel == SeverityLevel.UNKNOWN || org.apache.commons.lang.StringUtils.isBlank(message))
                return;
            Alarm alarm = new Alarm(UUID.randomUUID().toString().replaceAll("-", ""));
            alarm.setAttributeId(attribute.getId());
            alarm.setMonitorId(siteName);
            alarm.setAlarmSource(AlarmSource.WEBLOGIC);
            alarm.setMonitorType(ResourceType.WEBLOGIC.name());
            alarm.setSeverity(severityLevel);
            alarm.setMessage(controlFixedLength(message));
            alarm.setCreateTime(now);
            alarm.setSubResourceId(siteName);
            alarm.setSubResourceType(ResourceType.WEBLOGIC);
            alarmService.saveAlarm(alarm);

            //发送邮件
            List<AttributeAction> thresholdAttributeActions = actionService.queryAttributeActions(resource.getResourceId(), attribute.getId(), severityLevel);

            //处理动作
            if(thresholdAttributeActions != null && thresholdAttributeActions.size() > 0) {
                actionService.doActions(thresholdAttributeActions, resource, attribute, severityLevel, message);
            }
        }

        private String controlFixedLength(String message){
            String back=null;
            if(message.length()>500){
                back =  org.apache.commons.lang.StringUtils.substring(message, 0, 500);
                back =back + "...";
            }else {
                back = message;
            }
            return back;
        }
    }
}
