package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.HisData;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;

/**
 * wls history data (only last data)
 * User: bao
 */
public class WlsHisData implements HisData {

    public final static WlsHisData EMPTY;

    static{
        EMPTY = new WlsHisData();
        EMPTY.setWlsIniData(WlsIniData.EMPTY);
        EMPTY.setWlsInTimeData(WlsInTimeData.EMPTY);
    }
    /** 记录初始化数据*/
    private WlsInTimeData wlsInTimeData;
    /** 记录最后一次实时数据*/
    private WlsIniData wlsIniData;

    private int rqDoneCount = -1;
    /** 监控次数*/
    private int monitorCount;

    public int getRqDoneCount() {
        return rqDoneCount;
    }

    void setRqDoneCount(int rqDoneCount) {
        this.rqDoneCount = rqDoneCount;
    }

    public void setMonitorCount(int monitorCount) {
        this.monitorCount = monitorCount;
    }

    public int getMonitorCount() {
        return monitorCount;
    }

    public WlsInTimeData getWlsInTimeData() {
        return wlsInTimeData;
    }

    public void setWlsInTimeData(WlsInTimeData wlsInTimeData) {
        this.wlsInTimeData = wlsInTimeData;
    }

    public WlsIniData getWlsIniData() {
        return wlsIniData;
    }

    public void setWlsIniData(WlsIniData wlsIniData) {
        this.wlsIniData = wlsIniData;
    }
}
