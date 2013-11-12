package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorSite;
import com.fusionspy.beacon.site.tux.entity.TuxInTimeData;
import com.fusionspy.beacon.site.tux.entity.TuxIniData;
import com.sinosoft.one.monitor.resources.model.Resource;
import org.apache.commons.lang.StringUtils;

/**
 * tuxSite
 * User: qc
 * Date: 11-8-31
 * Time: 下午1:47
 */
public class TuxSite extends MonitorSite{



    private TuxService tuxService;


    public void setTuxService(TuxService tuxService) {
        this.tuxService = tuxService;
    }

    private volatile TuxHisData tuxHisData;

    public TuxSite() {
        super();
        hisData = new TuxHisData(this.getSiteName());
        tuxHisData = (TuxHisData)hisData;
    }


    @Override
    protected void recordInitData(InitData initData) {

        TuxIniData tuxIniData = (TuxIniData) initData;
        tuxIniData.setSiteName(getSiteName());
        tuxHisData.setTuxIniData(tuxIniData);

        //初始化数据返回有tuxedo失败信息
        if(tuxHisData.isTuxedoStop()){
            tuxHisData.setTuxIniData(TuxIniData.EMPTY);
            tuxHisData.setTuxInTimeData(TuxInTimeData.EMPTY);
            throw new IllegalStateException("被监控Tuxedo系统并无运行，请检查");
        }

        initData.process();
        tuxService.processInitData(tuxIniData);
//       tuxHisData.setMonitorCount(++monitorCount);

    }

//    private void iniHisData(){
//         if(tuxHisData == null) {
//             tuxHisData = new TuxHisData(this.getSiteName());
//             tuxHisData.setTuxInTimeData(new TuxInTimeData());
//         }
//    }


    @Override
    protected void recordInTimeData(InTimeData inTimeData) {


        TuxInTimeData thisData = (TuxInTimeData)inTimeData;
        this.tuxHisData.setTuxInTimeData(thisData);
        if(tuxHisData.isTuxedoStop()){
            return;
        }
        //first load
        // iniHisData();
        tuxService.processInTimeData(this.getSiteName(),this.getPeriod(),tuxHisData);
        //record monitorData
//        tuxHisData.setMonitorCount(++monitorCount);
    }

    @Override
    public TuxHisData getMonitorData() {
        return (TuxHisData)hisData;
    }

}
