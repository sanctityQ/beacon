package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.MonitorDataRepository;
import com.fusionspy.beacon.site.MonitorSite;
import com.fusionspy.beacon.site.SitesHolder;
import com.fusionspy.beacon.site.tux.entity.SiteListEntity;
import com.fusionspy.beacon.site.wls.WlsSite;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.system.service.SystemService;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ThreadFactory;

@Component
class TuxSiteHolder extends SitesHolder<TuxSite>{


    @Autowired
    private SystemService systemService;

    @Resource(name = "tuxDataConnectRepo")
    private TuxDataRepository conRep;

    @Resource(name = "tuxDataSimulationRep")
    private TuxDataSimulationRepository demoRep;

    @Autowired
    private TuxService tuxService;

    @PostConstruct
    void init(){
        for(SiteListEntity siteListEntity: systemService.getSites()){
             create(siteListEntity);
        }
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.Tuxedo;
    }

    @Override
    public TuxSite getMonitorSite(String siteName) {

        TuxSite tuxSite = siteMap.get(siteName);
        if (tuxSite == null) {
            SiteListEntity siteListEntity = systemService.getSite(siteName);

            if (siteListEntity != null) {
                tuxSite =  create(siteListEntity);
            } else {
                throw new IllegalStateException("tux查询不到此站点:["+siteName+"]，请检查");
            }
        }
        return tuxSite;
    }

    TuxSite create(SiteListEntity siteListEntity){
        TuxSite tuxSite = new TuxSite(siteListEntity);
        tuxSite.setTuxService(tuxService);
        tuxSite.setAttributeCache(this.attributeCache);
        tuxSite.setResourcesCache(this.resourcesCache);
        if (demo) {
            tuxSite.setMonitorDataRepository(demoRep);
        } else {
            tuxSite.setMonitorDataRepository(conRep);
        }
        tuxSite.setScheduledExecutorService(executorService);
       return siteMap.putIfAbsent(tuxSite.getSiteName(), tuxSite);
    }
}
