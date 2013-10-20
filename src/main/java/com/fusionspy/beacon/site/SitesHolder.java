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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * sites holder
 * User: qc
 * Date: 11-8-31
 * Time: 下午4:40
 */
public class SitesHolder {

    private static ConcurrentMap<String, MonitorSite> tuxSiteMap = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程
    private static ConcurrentMap<String, MonitorSite> wlsSiteMap = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程

    @Autowired
    private SystemService systemService;

    @Resource(name="tuxDataConnectRepo")
    private MonitorDataRepository conRep;

    @Resource(name="tuxDataSimulationRep")
    private MonitorDataRepository  demoRep;

    /** weblogic监控数据仓库 */
    @Resource(name = "wlsDataRepository")
    private MonitorDataRepository wlsRep;

    /** weblogic监控数据示例仓库 */
    @Resource(name = "wlsDataSimulationRepository")
    private MonitorDataRepository wlsDemoRep;

    @Autowired
    private TuxService tuxService;

    @Autowired
    private WlsService wlsService;

    private boolean demo=false;

    /**
     * is demo
     * @param demo
     */
    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    public void removeMonitorSite(String siteName) {
        Assert.hasText(siteName);
        tuxSiteMap.remove(siteName);
    }




    public List<MonitorSite> getMonitorSites(){
       return Lists.newArrayList(tuxSiteMap.values()) ;
    }

    public MonitorSite getMonitorSite(String siteName) {
        Assert.hasText(siteName);
        MonitorSite monitorSite = null;
        monitorSite = tuxSiteMap.get(siteName);
        if (monitorSite == null) {
            SiteListEntity siteListEntity = systemService.getSite(siteName);
            MonitorSite newMonitorSite = null;
            if (siteListEntity != null) {
            if (siteListEntity instanceof  SiteListEntity) {
                newMonitorSite = getTuxSite();
                newMonitorSite.setSiteName(siteName);
                newMonitorSite.setSiteIp(siteListEntity.getSiteIp());
                newMonitorSite.setSitePort(siteListEntity.getSitePort());
                monitorSite = tuxSiteMap.putIfAbsent(siteName, newMonitorSite);
            } else {
            }

            monitorSite = wlsSiteMap.get(siteName);
            if(monitorSite == null) {
                WlsServer wlsServer = wlsService.getSite(siteName);
                if(wlsServer != null) {
                    newMonitorSite = getWlsSite();
                    newMonitorSite.setSiteName(siteName);
                    newMonitorSite.setSiteIp(wlsServer.getListenAddress());
                    newMonitorSite.setSitePort(wlsServer.getListenPort());
                    monitorSite = wlsSiteMap.putIfAbsent(siteName, newMonitorSite);
                }
            }
            if(monitorSite == null)
                monitorSite = newMonitorSite;
            }
        }
        return monitorSite;
    }

    @Bean
    public MonitorSite getTuxSite() {
        TuxSite tuxSite = new TuxSite();
        tuxSite.setTuxService(tuxService);
        if (demo) {
            tuxSite.setMonitorDataRepository(demoRep);
        } else {
            tuxSite.setMonitorDataRepository(conRep);
        }

        return tuxSite;
    }

    /**
     * 初始化weblogic站点，并设置监控仓库及持久层service
     * @return
     */
    @Bean
    public MonitorSite getWlsSite() {
        WlsSite wlsSite = new WlsSite();
        wlsSite.setWlsService(wlsService);
        if (demo) {
            wlsSite.setMonitorDataRepository(wlsDemoRep);
        } else {
            wlsSite.setMonitorDataRepository(wlsRep);
        }

        return wlsSite;
    }
}
