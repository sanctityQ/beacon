package com.fusionspy.beacon.site;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.fusionspy.beacon.attribute.domain.AttributeCache;
import com.fusionspy.beacon.common.ResourceType;
import com.fusionspy.beacon.resources.domain.ResourcesCache;
import com.sinosoft.one.util.thread.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public abstract class SitesHolder<T extends MonitorSite> {

    protected ConcurrentMap<String, T> siteMap = new MapMaker().concurrencyLevel(32).makeMap();//监控站点线程

    protected ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20, new ThreadUtils.CustomizableThreadFactory(getResourceType().name()));


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


    void remove(String siteName){
        Assert.hasText(siteName);
        siteMap.remove(siteName);
    }


    public List<T> getAll() {
        return Lists.newArrayList(siteMap.values());
    }

    public final T getMonitorSite(final String siteName){
        T site = siteMap.get(siteName);
        if (site == null) {
            site= createSite(siteName);
            siteMap.putIfAbsent(site.getSiteName(), site);
        }
        return site;
    }

    protected abstract T createSite(String siteName);

    protected void addMonitorSite(T site){
        siteMap.putIfAbsent(site.getSiteName(), site);
    }

}
