package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.HisData;
import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorSite;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;

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
        wlsService.processInitData(wlsIniData); //记录初始化数据
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
