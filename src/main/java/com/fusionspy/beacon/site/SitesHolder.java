package com.fusionspy.beacon.site;

import com.fusionspy.beacon.site.tux.TuxService;
import com.fusionspy.beacon.site.tux.TuxSite;
import com.fusionspy.beacon.site.tux.entity.SiteListEntity;
import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.WlsSite;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.system.service.SystemService;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.sinosoft.one.monitor.attribute.domain.AttributeCache;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesCache;
import com.sinosoft.one.util.thread.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public abstract class SitesHolder<T extends MonitorSite> {

    protected ConcurrentMap<String, T> siteMap = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程

    protected ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10, new ThreadUtils.CustomizableThreadFactory(getResourceType().name()));


    @Autowired
    protected AttributeCache attributeCache;

    @Autowired
    protected ResourcesCache resourcesCache;

    protected boolean demo = false;

    public abstract ResourceType getResourceType();
    /**
     * is demo
     *
     * @param demo
     */
    public void setDemo(boolean demo) {
        this.demo = demo;
    }


    public void remove(String siteName){
        Assert.hasText(siteName);
        siteMap.remove(siteName);
    }


    public List<T> getAll() {
        return Lists.newArrayList(siteMap.values());
    }

    public abstract MonitorSite getMonitorSite(String siteName);


//    {
//        Assert.hasText(siteName);
//        MonitorSite monitorSite = null;
//        monitorSite = tuxSiteMap.get(siteName);
//        if (monitorSite == null) {
//            SiteListEntity siteListEntity = systemService.getSite(siteName);
//            MonitorSite newMonitorSite = null;
//            if (siteListEntity != null && siteListEntity instanceof  SiteListEntity) {
//
//                newMonitorSite = getTuxSite();
//                newMonitorSite.setSiteName(siteName);
//                newMonitorSite.setSiteIp(siteListEntity.getSiteIp());
//                newMonitorSite.setSitePort(siteListEntity.getSitePort());
//                newMonitorSite.setPeriod(siteListEntity.getInterval());
//                monitorSite = tuxSiteMap.putIfAbsent(siteName, newMonitorSite);
//
//            } else {
//                monitorSite = wlsSiteMap.get(siteName);
//                if(monitorSite == null) {
//                    WlsServer wlsServer = wlsService.getSite(siteName);
//                    if(wlsServer != null) {
//                        newMonitorSite = getWlsSite();
//                        newMonitorSite.setSiteName(siteName);
//                        newMonitorSite.setSiteIp(wlsServer.getListenAddress());
//                        newMonitorSite.setSitePort(wlsServer.getListenPort());
//                        newMonitorSite.setPeriod(wlsServer.getInterval());
//                        monitorSite = wlsSiteMap.putIfAbsent(siteName, newMonitorSite);
//                    }
//
//                }
//
//            }
//
//            if (monitorSite == null)
//                monitorSite = newMonitorSite;
//        }
//        return monitorSite;
//    }

//    @Bean
//    public MonitorSite getTuxSite() {
//        TuxSite tuxSite = new TuxSite();
//        tuxSite.setTuxService(tuxService);
//        tuxSite.setAttributeCache(this.attributeCache);
//        tuxSite.setResourcesCache(this.resourcesCache);
//        if (demo) {
//            tuxSite.setMonitorDataRepository(demoRep);
//        } else {
//            tuxSite.setMonitorDataRepository(conRep);
//        }
//
//        return tuxSite;
//    }

    /**
     * 初始化weblogic站点，并设置监控仓库及持久层service
     * @return
     */
//    @Bean
//    public MonitorSite getWlsSite() {
//        WlsSite wlsSite = new WlsSite();
//        wlsSite.setWlsService(wlsService);
//        wlsSite.setAttributeCache(this.attributeCache);
//        wlsSite.setResourcesCache(this.resourcesCache);
//        if (demo) {
//            wlsSite.setMonitorDataRepository(wlsDemoRep);
//        } else {
//            wlsSite.setMonitorDataRepository(wlsRep);
//        }
//
//        return wlsSite;
//    }
}
