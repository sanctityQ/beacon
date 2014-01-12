package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.*;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;


public class WlsSite extends MonitorSite {

    private WlsService wlsService;

    private final String webLogicIp;
    private final Integer webLogicPort;

    private final String domainName;

    private final String userName;
    private final String password;

    public void setWlsService(WlsService wlsService) {
        this.wlsService = wlsService;
    }

    public WlsSite(final WlsServer wlsServer){
        hisData = new WlsHisData();
        this.setSiteIp(wlsServer.getListenAddress());
        this.setSiteName(wlsServer.getSiteName());
        this.setSitePort(wlsServer.getListenPort());
        this.setPeriod(wlsServer.getInterval());
        this.webLogicIp = wlsServer.getWeblogicIp();
        this.webLogicPort =  wlsServer.getWeblogicPort();
        this.domainName = wlsServer.getDomainName();
        this.userName = wlsServer.getUserName();
        this.password = wlsServer.getPassword();
    }

    public String getWebLogicIp(){
       return this.webLogicIp;
    }

    /**
     * 记录初始化数据
     * @param initData
     */
    @Override
    protected void recordInitData(InitData initData) {
        WlsIniData wlsIniData = (WlsIniData) initData;
        ((WlsHisData)hisData).setWlsIniData(wlsIniData);

        if (wlsIniData.isStop()) { //验证weblogic服务器是否启动 (未启动时)
            Resource resource = this.resourcesCache.getResource(wlsIniData.getSiteName());
            Attribute alarmAttribute = this.attributeCache.getAttribute(resource.getResourceType(), AttributeName.SystemStop.name());
            WlsAlertMessage wlsAlertMessage = new WlsAlertMessage();
            wlsAlertMessage.setStopServerName(wlsIniData.getSiteName());
            wlsService.alarmMessage(resource, alarmAttribute, wlsIniData.getSiteName(), SeverityLevel.CRITICAL, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_STOP));
            throw new IllegalStateException("被监控Welogic系统并无运行，请检查");
        }
      //  iniHisData(); //构建HisData,关联初始化数据
    }

    @Override
    protected void recordInTimeData(InTimeData inTimeData) {
        ((WlsHisData)hisData).setWlsInTimeData((WlsInTimeData)inTimeData);
       // iniHisData();
        wlsService.processInTimeData(this.getSiteName(), this.getPeriod(), (WlsInTimeData)inTimeData, (WlsHisData)hisData);
    }


    Integer getWebLogicPort() {
        return webLogicPort;
    }

    String getDomainName() {
        return domainName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
