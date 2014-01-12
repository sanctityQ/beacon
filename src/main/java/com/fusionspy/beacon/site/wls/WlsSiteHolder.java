package com.fusionspy.beacon.site.wls;


import com.fusionspy.beacon.site.SitesHolder;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
class WlsSiteHolder extends SitesHolder<WlsSite>{

    @Autowired
    private WlsService wlsService;

    /** weblogic监控数据仓库 */
    @Resource(name = "wlsDataRepository")
    private WlsDataRepository wlsRep;

    /** weblogic监控数据示例仓库 */
    @Resource(name = "wlsDataSimulationRepository")
    private WlsDataSimulationRepository wlsDemoRep;


    @PostConstruct
    void init(){
       for(WlsServer wlsServer: wlsService.getSites()){
           create(wlsServer);
       }
    }


    @Override
    public ResourceType getResourceType() {
        return ResourceType.WEBLOGIC;
    }

    @Override
    public WlsSite getMonitorSite(String siteName){
        WlsSite monitorSite = siteMap.get(siteName);
        if(monitorSite == null) {
            WlsServer wlsServer = wlsService.getSite(siteName);
            if(wlsServer != null) {
                create(wlsServer);
            }else{
                throw new IllegalStateException("wls查询不到此站点:["+siteName+"]，请检查");
            }
        }
        return monitorSite;
    }

    WlsSite create(WlsServer wlsServer){
        WlsSite wlsSite = new WlsSite(wlsServer);
        wlsSite.setAttributeCache(this.attributeCache);
        wlsSite.setResourcesCache(this.resourcesCache);
        if (demo) {
            wlsSite.setMonitorDataRepository(wlsDemoRep);
        } else {
            wlsSite.setMonitorDataRepository(wlsRep);
        }
        wlsSite.setScheduledExecutorService(executorService);
        return siteMap.putIfAbsent(wlsServer.getSiteName(), wlsSite);
    }


}
