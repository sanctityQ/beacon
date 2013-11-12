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


    private ResourcesCache resourcesCache;

    private AttributeCache attributeCache;

    private Attribute alarmAttribute;

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
        Resource resource = resourcesCache.getResource(this.getSiteName());
        this.alarmAttribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.SystemStop.name());

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


                    //TODO check this site is exists，can do right
                    //start
                    if (!isRunning) {
                        logger.debug("siteName:{} started", siteName);
                        //    String initXml = connect.startSiteThread(iniXml,siteName,siteIp,sitePort,0);
                        recordInitData(repository.getInitData(siteName, siteIp, sitePort));

                        isRunning = true;
                        agentRunning = true;
                    }
                    //如果监控次数超过1000重置连接
                    if(monitorCount > 0&&monitorCount%1000==0){
                        reset();
                    }
                    //in time data
                    recordInTimeData(repository.getInTimeData(siteName));
                    monitorCount++;
                } catch (Throwable t) {

                    //连接失败重试一次连接
                    if(t instanceof ConnectAgentException){
                        reset();
                        agentRunning = false;
                    }

                    t.printStackTrace();
                }

            }
        };
    }

    private void reset() {
        try {
            repository.stopSite(siteName);
            this.isRunning = false;

        } catch (Throwable t) {
            logger.warn("重置连接异常：",t);
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
