package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.AlarmMessageFormat;
import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorSite;
import com.fusionspy.beacon.site.tux.entity.TuxInTimeData;
import com.fusionspy.beacon.site.tux.entity.TuxIniData;
import com.sinosoft.one.monitor.common.AttributeName;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
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
        tuxHisData.setTuxIniData(tuxIniData);

        //初始化数据返回有Tuxedo无正常启动的信息
        if(tuxIniData.isStop()){
            this.resource = this.resourcesCache.getResource(this.getSiteName());
            this.alarmAttribute = this.attributeCache.getAttribute(resource.getResourceType(), AttributeName.SystemStop.name());
            tuxHisData.getProcessResult().setStopServer(true,this.getSiteName());
            this.tuxService.alarmMessage(resource,alarmAttribute,this.getSiteName(), SeverityLevel.CRITICAL,
                    tuxHisData.getProcessResult().getTuxAlertMessage().getMessageByAlarmMessageFormat(AlarmMessageFormat.TUX_STOP));
            //tuxHisData.setTuxIniData(TuxIniData.EMPTY);
            throw new IllegalStateException("被监控Tuxedo系统并无运行，请检查");
        }

    }


    @Override
    protected void recordInTimeData(InTimeData inTimeData) {


        TuxInTimeData thisData = (TuxInTimeData)inTimeData;
        this.tuxHisData.setTuxInTimeData(thisData);
//        if(tuxHisData.isTuxedoStop()){
//            return;
//        }
        //first load
        tuxService.processInTimeData(this.getSiteName(),this.getPeriod(),tuxHisData);
    }

    @Override
    public TuxHisData getMonitorData() {
        return (TuxHisData)hisData;
    }

}
