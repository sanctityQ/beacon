package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.*;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-20
 * Time: 下午11:38
 * To change this template use File | Settings | File Templates.
 */
public class WlsSite extends MonitorSite {

    private WlsService wlsService;

    private WlsHisData wlsHisData;

    public void setWlsService(WlsService wlsService) {
        this.wlsService = wlsService;
    }

    /**
     * 记录初始化数据
     * @param initData
     */
    @Override
    protected void recordInitData(InitData initData) {
        WlsIniData wlsIniData = (WlsIniData) initData;
        if (wlsIniData.isStop()) { //验证weblogic服务器是否启动 (未启动时)
            Resource resource = this.resourcesCache.getResource(wlsIniData.getSiteName());
            Attribute alarmAttribute = this.attributeCache.getAttribute(resource.getResourceType(), AttributeName.SystemStop.name());
            WlsAlertMessage wlsAlertMessage = new WlsAlertMessage();
            wlsAlertMessage.setStopServerName(wlsIniData.getSiteName());
            wlsService.alarmMessage(resource, alarmAttribute, wlsIniData.getSiteName(), SeverityLevel.CRITICAL, wlsAlertMessage.getMessageByAlarmMessageFormat(AlarmMessageFormat.WLS_STOP));
            throw new IllegalStateException("被监控Welogic系统并无运行，请检查");
        }
        iniHisData(); //构建HisData,关联初始化数据
        wlsHisData.setWlsIniData(wlsIniData);
    }

    @Override
    protected void recordInTimeData(InTimeData inTimeData) {
        iniHisData();
        wlsService.processInTimeData(this.getSiteName(), this.getPeriod(), (WlsInTimeData)inTimeData, wlsHisData);
        wlsHisData.setMonitorCount(++monitorCount);
    }

    @Override
    public HisData getMonitorData() {
        if(wlsHisData == null)
            return WlsHisData.EMPTY;
        return wlsHisData;
    }

    private void iniHisData(){
        if(wlsHisData == null){
            wlsHisData = new WlsHisData();
            wlsHisData.setWlsInTimeData(new WlsInTimeData());
        }
    }
}
