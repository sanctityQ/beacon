package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.HisData;
import com.fusionspy.beacon.site.tux.entity.TuxInTimeData;
import com.fusionspy.beacon.site.tux.entity.TuxIniData;

/**
 * tux history data (only last data)
 * User: qc
 */
public class  TuxHisData implements HisData {

    public final static TuxHisData EMPTY;

    static{
        EMPTY = new TuxHisData();
        EMPTY.setTuxIniData(TuxIniData.EMPTY);
        EMPTY.setTuxInTimeData(TuxInTimeData.EMPTY);
        EMPTY.setProcessResult(ProcessInTimeResult.EMPTY);
    }

    private TuxInTimeData tuxInTimeData;

    private TuxIniData tuxIniData;

    private int rqDoneCount = -1;

    private ProcessInTimeResult processResult;

    private int monitorCount;

    public TuxInTimeData getTuxInTimeData() {
        return tuxInTimeData;
    }

    void setTuxInTimeData(TuxInTimeData tuxInTimeData) {
        this.tuxInTimeData = tuxInTimeData;
    }

    public int getRqDoneCount() {
        return rqDoneCount;
    }

    void setRqDoneCount(int rqDoneCount) {
        this.rqDoneCount = rqDoneCount;
    }

    public TuxIniData getTuxIniData() {
        return tuxIniData;
    }

    void setTuxIniData(TuxIniData tuxIniData) {
        this.tuxIniData = tuxIniData;
    }

    void setProcessResult(ProcessInTimeResult processResult) {
        this.processResult = processResult;
    }

    public ProcessInTimeResult getProcessResult() {
        if(processResult != null)
            return processResult;
        else
            return ProcessInTimeResult.EMPTY;
    }

    /**
     * set monitor count
     * @param monitorCount
     */
    void setMonitorCount(int monitorCount) {
        this.monitorCount = monitorCount;
    }

    /**
     * get monitor count
     * @return
     */
    public int getMonitorCount() {
        return monitorCount;
    }
}
