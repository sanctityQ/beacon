package com.fusionspy.beacon.site;

import com.fusionspy.beacon.site.tux.TuxHisData;
import com.sinosoft.one.monitor.attribute.domain.AttributeCache;
import com.sinosoft.one.monitor.attribute.domain.AttributeService;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.resources.domain.ResourcesCache;
import com.sinosoft.one.monitor.resources.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.ConnectException;
import java.util.concurrent.ScheduledFuture;

/**
 * monitor site
 * User: qc
 * Date: 11-9-13
 * Time: 下午8:50
 */
public abstract class MonitorSite {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    //default 30s
    private volatile int period = 30;

    protected boolean saveDbFlag = false;

    private MonitorDataRepository repository;

    protected volatile HisData hisData;

    protected InitData initData;

    protected ResourcesCache resourcesCache;

    protected AttributeCache attributeCache;

    protected Attribute alarmAttribute;

    protected Resource resource;

    protected void setMonitorDataRepository(MonitorDataRepository repository) {
        this.repository = repository;
    }

    //初始化连接的xml
    protected String iniXml;

    private String siteName;

    public int getMonitorCount() {
        return monitorCount;
    }

    protected int monitorCount = 0;

    protected String siteIp;

    //default value set 100
    protected int resetMonitorCount = 100;

    void setResetMonitorCount(int resetMonitorCount) {
        this.resetMonitorCount = resetMonitorCount;
    }


    public String getSiteName() {
        return siteName;
    }


    void setSiteIp(String siteIp) {
        this.siteIp = siteIp;
    }


    protected int sitePort;

    void setSitePort(int sitePort) {
        this.sitePort = sitePort;
    }

    /**
     * 设置是否运行
     * @param running
     */
    void setRunning(boolean running) {
        isRunning = running;
    }

    //启动标识
    private volatile boolean isRunning  = false;

    //agent端是否运行标识
    private volatile boolean agentRunning = false;

    public int getPeriod() {
        return period;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 设置时间，默认为30S，注意传入数值为s
     * @param period
     */
    void setPeriod(int period) {
        this.period = period;
    }

    /**
     * switch save db flag
     * @return  save db flag
     */
    boolean switchSaveFlag() {
       this.saveDbFlag = this.saveDbFlag?false:true;
       return this.saveDbFlag;
    }


    @PostConstruct
	void checkSetting() {
        Assert.isTrue(period >= 30);
	}

    void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    private ScheduledFuture scheduledFuture;


    /**
     * record init data
     * @param initData
     */
    protected abstract <T extends InitData> void recordInitData(T initData);

    /**
     * record inTime data
     * @param inTimeData
     */
    protected abstract <T extends InTimeData> void recordInTimeData(T inTimeData);


    Runnable start() {
        return new Runnable() {



            @Override
            public void run() {

                try {
                    before();
                    init();
                    //in time data
                    recordInTimeData(repository.getInTimeData(siteName));
                    //监控计数
                    monitorCount++;
                } catch (Throwable t) {

                    if(t instanceof ConnectAgentException){
                        //连接失败重试一次连接
                        reset();
                        //agent运行不正常，设置agent状态
                        agentRunning = false;
                        t = t.getCause();
                    }
                    logger.error("siteName:{} has  ConnectAgentException , detail is:", getSiteName(), t);
                }

            }





        };
    }

    private void init(){
        //start
        if (!isRunning) {
            logger.debug("siteName:{} started", siteName);
            initData = repository.getInitData(siteName, siteIp, sitePort);
            agentRunning = true;

            recordInitData(initData);
            initData.process();
            isRunning = true;
        }
    }

    private void before(){
        //如果监控次数超过1000重置连接
        if(monitorCount > 0 && monitorCount%resetMonitorCount==0){
            reset();
        }
    }

    private void reset() {
        try {
          repository.stopSite(siteName);
          logger.debug("site name is: {},reset successful",siteName);
        } catch (Throwable t) {
            logger.error("site name is: {}, reset failed：{}", siteName, t);
        } finally {
            this.isRunning = false;
        }
        // repository.getInitData(siteName, siteIp, sitePort);
    }



    @PreDestroy
    void stop(){
        Assert.notNull(this.scheduledFuture);
        this.isRunning = false;
        this.scheduledFuture.cancel(true);
        repository.stopSite(siteName);

    }

    public abstract <T extends HisData> T getMonitorData();

    protected void setResourcesCache(ResourcesCache resourcesCache) {
        this.resourcesCache = resourcesCache;
    }

    protected void setAttributeCache(AttributeCache attributeCache) {
        this.attributeCache = attributeCache;
    }


    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public boolean isAgentRunning() {
        return agentRunning;
    }
}
