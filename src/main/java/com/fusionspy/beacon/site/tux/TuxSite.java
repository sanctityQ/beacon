package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorSite;
import com.fusionspy.beacon.site.tux.entity.TuxInTimeData;
import com.fusionspy.beacon.site.tux.entity.TuxIniData;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.fusionspy.beacon.system.entity.SysrecsEntity;
import com.sinosoft.one.util.encode.JaxbBinder;

/**
 * tuxSite
 * User: qc
 * Date: 11-8-31
 * Time: 下午1:47
 */
public class TuxSite extends MonitorSite{

//    private JaxbBinder jaxbBinder = new JaxbBinder(TuxInTimeData.class, TuxsvrsEntity.class);
//
//    private JaxbBinder iniBinder = new JaxbBinder(SysrecsEntity.class,TuxIniData.class,TuxsvrsEntity.class);


    private TuxService tuxService;


    public void setTuxService(TuxService tuxService) {
        this.tuxService = tuxService;
    }

    private volatile TuxHisData tuxHisData;

    @Override
    protected void recordInitData(InitData initData) {

//       String initXml = connect.startSiteThread(iniXml,this.getSiteName(),siteIp,sitePort,0);
//       logger.debug("get initXml Xml: {}",initXml);
//       TuxIniData tuxIniData = iniBinder.fromXml(initXml);
        //  logger.debug("----tuxIniData------:{} service : {}",tuxIniData,tuxService);
        TuxIniData tuxIniData = (TuxIniData) initData;
        tuxIniData.setSiteName(getSiteName());
        tuxService.processInitData(tuxIniData);
        iniHisData();
        tuxHisData.setMonitorCount(++monitorCount);
        tuxHisData.setTuxIniData(tuxIniData);
    }

    private void iniHisData(){
         if(tuxHisData == null){
           tuxHisData = new TuxHisData();
            tuxHisData.setTuxInTimeData(new TuxInTimeData());
         }
    }

    @Override
    protected void recordInTimeData(InTimeData inTimeData) {
    //    logger.debug("get inTime Xml: {}",inTimeXml);
        TuxInTimeData thisData = (TuxInTimeData)inTimeData;
        //first load
        iniHisData();
        tuxService.processInTimeData(this.getSiteName(),this.getPeriod(),thisData,tuxHisData);
        //record monitorData
        tuxHisData.setMonitorCount(++monitorCount);
    }

    @Override
    public TuxHisData getMonitorData() {
        return tuxHisData!=null?tuxHisData:TuxHisData.EMPTY;
    }

}
