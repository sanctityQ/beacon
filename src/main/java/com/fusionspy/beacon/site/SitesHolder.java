package com.fusionspy.beacon.site;

import com.fusionspy.beacon.site.tux.TuxService;
import com.fusionspy.beacon.site.tux.TuxSite;
import com.fusionspy.beacon.system.entity.SiteListEntity;
import com.fusionspy.beacon.system.service.SystemService;
import com.google.common.collect.MapMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentMap;

/**
 * sites holder
 * User: qc
 * Date: 11-8-31
 * Time: 下午4:40
 */
public class SitesHolder {

    private static ConcurrentMap<String, MonitorSite> tuxSiteMap = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程

    @Autowired
    private SystemService systemService;

    @Resource(name="tuxDataConnectRepo")
    private MonitorDataRepository conRep;

    @Resource(name="tuxDataSimulationRep")
    private MonitorDataRepository  demoRep;

    @Autowired
    private TuxService tuxService;

    private boolean demo=false;

    /**
     * is demo
     * @param demo
     */
    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    public MonitorSite getMonitorSite(String siteName) {
        Assert.hasText(siteName);
        MonitorSite monitorSite = tuxSiteMap.get(siteName);
        if (monitorSite == null) {
            SiteListEntity siteListEntity = systemService.getSite(siteName);
            MonitorSite newMonitorSite = null;
            if (siteListEntity.getSiteType().equals("1")) {
                newMonitorSite = getTuxSite();
                newMonitorSite.setSiteName(siteName);
                newMonitorSite.setSiteIp(siteListEntity.getSiteIp());
                newMonitorSite.setSitePort(siteListEntity.getSitePort());
            } else {

            }
            monitorSite = tuxSiteMap.putIfAbsent(siteName, newMonitorSite);
            if(monitorSite == null)
               monitorSite = newMonitorSite;
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




}
