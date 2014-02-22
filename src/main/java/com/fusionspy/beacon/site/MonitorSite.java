package com.fusionspy.beacon.site;

import com.fusionspy.beacon.attribute.domain.AttributeCache;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.resources.domain.ResourcesCache;
import com.fusionspy.beacon.resources.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * monitor site
 * User: qc
 * Date: 11-9-13
 * Time: 下午8:50
 */
public abstract class MonitorSite {

    private ScheduledExecutorService scheduledExecutorService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    //default 30s
    private volatile int period = 30;

    private MonitorDataRepository repository;

    protected volatile HisData hisData;

    protected InitData initData;

    protected ResourcesCache resourcesCache;

    protected AttributeCache attributeCache;

    protected Attribute alarmAttribute;

    protected Resource resource;

    public  <T extends MonitorDataRepository> void setMonitorDataRepository(T repository) {
        this.repository = repository;
    }

    //初始化连接的xml
    protected String iniXml;

    private String siteName;

    public int getMonitorCount() {
        return monitorCount;
    }

    protected int monitorCount = 0;

    public String getSiteIp() {
        return siteIp;
    }

    protected String siteIp;

    //default value set 100
    protected int resetMonitorCount = 100;

    void setResetMonitorCount(int resetMonitorCount) {
        this.resetMonitorCount = resetMonitorCount;
    }


    public String getSiteName() {
        return siteName;
    }


    protected void setSiteIp(String siteIp) {
        this.siteIp = siteIp;
    }


    protected int sitePort;

    protected void setSitePort(int sitePort) {
        this.sitePort = sitePort;
    }

    public int getSitePort(){
        return this.sitePort;
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

    private volatile boolean needRefresh = false;

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
    protected void setPeriod(int period) {
        this.period = period;
    }



    @PostConstruct
	void checkSetting() {
        Assert.isTrue(period >= 30);
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


    void start(){
        if(!this.isRunning()){
            this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new monitorRunnable(), 0, this.getPeriod(), TimeUnit.SECONDS);
            logger.info("Site Name: {} start now", siteName);
        }else{
            logger.info("Site Name: {} has started, so do nothing", siteName);
        }
    }

    private void init(){
        //start
        if (!isRunning||needRefresh) {
            logger.debug("siteName:{} started", siteName);
            //set is running flag
            isRunning = true;

            initData = repository.getInitData(this);
            agentRunning = true;

            recordInitData(initData);
            initData.process();
        }
    }

    //TODO 有无更好方案？譬如：如果心跳几次始终无数据，自动关闭？在此处理还是放置在Connect端更合理？
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
            this.needRefresh = true;
        }
        // repository.getInitData(siteName, siteIp, sitePort);
    }



    @PreDestroy
    void stop(){
        if(!this.isRunning())
            return;
        Assert.notNull(this.scheduledFuture);
        this.scheduledFuture.cancel(true);
        this.isRunning = false;
        if(agentRunning){
          repository.stopSite(siteName);
        }

    }

    public  <T extends HisData> T getMonitorData(){
        return (T)hisData;
    }

    public void setResourcesCache(ResourcesCache resourcesCache) {
        this.resourcesCache = resourcesCache;
    }

    public void setAttributeCache(AttributeCache attributeCache) {
        this.attributeCache = attributeCache;
    }


    protected void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public boolean isAgentRunning() {
        return agentRunning;
    }

    public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }


    class monitorRunnable implements Runnable{

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
                    //连接失败重试一次连接? TODO 设置从Connect返回信号需要重置才进行重置

                    ConnectAgentException connectAgentException = (ConnectAgentException)t;

                    //agent运行不正常，设置agent状态
                    agentRunning = false;

                    //设置刷新标识位
                    needRefresh = true;
                    t = t.getCause();
                    logger.error("siteName[{}] ExceptionMessage :{}", new Object[]{getSiteName(),
                            connectAgentException.getMessage()});
                }

                logger.error("site:["+siteName+"]运行期间异常，请检查",t);
            }
        }
    }
}
