package com.fusionspy.beacon.site.wls;


import com.fusionspy.beacon.site.SitesHolder;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
class WlsSiteHolder extends SitesHolder<WlsSite> {

    @Autowired
    private WlsService wlsService;

    /**
     * weblogic监控数据仓库
     */
    @Resource(name = "wlsDataRepository")
    private WlsDataRepository wlsRep;

    /**
     * weblogic监控数据示例仓库
     */
    @Resource(name = "wlsDataSimulationRepository")
    private WlsDataSimulationRepository wlsDemoRep;


    @PostConstruct
    void init() {
        for (WlsServer wlsServer : wlsService.getSites()) {
            addMonitorSite(create(wlsServer));
        }
    }


    @Override
    public ResourceType getResourceType() {
        return ResourceType.WEBLOGIC;
    }

    @Override
    public WlsSite createSite(String siteName) {
        WlsServer wlsServer = wlsService.getSite(siteName);
        if (wlsServer != null) {
            return create(wlsServer);
        } else {
            throw new IllegalStateException("wls查询不到此站点:[" + siteName + "]，请检查");
        }
    }

    WlsSite create(WlsServer wlsServer) {
        WlsSite wlsSite = new WlsSite(wlsServer);
        wlsSite.setAttributeCache(this.attributeCache);
        wlsSite.setResourcesCache(this.resourcesCache);
        if (demo) {
            wlsSite.setMonitorDataRepository(wlsDemoRep);
        } else {
            wlsSite.setMonitorDataRepository(wlsRep);
        }
        wlsSite.setWlsService(wlsService);
        wlsSite.setScheduledExecutorService(executorService);
        return wlsSite;
    }


}
