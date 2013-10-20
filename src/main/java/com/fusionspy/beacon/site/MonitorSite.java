package com.fusionspy.beacon.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

/**
 * monitor site
 * User: qc
 * Date: 11-9-13
 * Time: 下午8:50
 */
public abstract class MonitorSite<E> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    //default 30s
    private volatile int period = 30;

    protected boolean saveDbFlag = false;

    private MonitorDataRepository repository;

    public void setMonitorDataRepository(MonitorDataRepository repository) {
        this.repository = repository;
    }

    //初始化连接的xml
    protected String iniXml;

    private String siteName;

    protected int monitorCount = 0;

    protected String siteIp;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }


    public void setSiteIp(String siteIp) {
        this.siteIp = siteIp;
    }


    protected int sitePort;

    public void setSitePort(int sitePort) {
        this.sitePort = sitePort;
    }

    /**
     * 设置是否运行
     * @param running
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    //启动标识
    private volatile boolean isRunning  = false;

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
    public void setPeriod(int period) {
        this.period = period;
    }

    /**
     * switch save db flag
     * @return  save db flag
     */
    public boolean switchSaveFlag() {
       this.saveDbFlag = this.saveDbFlag?false:true;
       return this.saveDbFlag;
    }


    @PostConstruct
	public void checkSetting() {
		Assert.isTrue(period >= 30);
	}

    protected void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    private ScheduledFuture scheduledFuture;


    /**
     * record init data
     * @param initData
     */
    protected abstract void recordInitData(InitData initData);

    /**
     * record inTime data
     * @param inTimeData
     */
    protected abstract void recordInTimeData(InTimeData inTimeData);


    public Runnable start() {
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
                    }
                    //in time data
                    recordInTimeData(repository.getInTimeData(siteName));

                } catch (Throwable t) {
                    t.printStackTrace();
                }

            }
        };
    }

    public void stop(){
        Assert.notNull(this.scheduledFuture);
        this.isRunning = false;
        this.scheduledFuture.cancel(true);
        repository.stopSite(siteName);

    }

    public abstract <T extends HisData> T getMonitorData();
}
