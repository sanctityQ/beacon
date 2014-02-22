package com.fusionspy.beacon.site;

import com.google.common.collect.Maps;
import com.fusionspy.beacon.common.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * monitor manage
 * User: qc
 * Date: 11-9-13
 * Time: 下午8:49
 */
@Component
public class MonitorManage {

    private static Logger logger = LoggerFactory.getLogger(MonitorManage.class);

//    private static final int CORE_POOL_SIZE = 20;

//    private AlertMessage alertMessage = new AlertMessage();

//    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE, new ThreadUtils.CustomizableThreadFactory("tux"));

    @Inject
    private Set<SitesHolder> sitesHolders;

    Map<ResourceType,SitesHolder> sitesHolderMap = Maps.newHashMap();

//    private SiteInfoLogger siteInfoLogger = SiteInfoLogger.getInstance();

    @PostConstruct
    public void init(){

      for(SitesHolder sitesHolder : sitesHolders){
          //init start monitor
          List<MonitorSite> monitorSites = sitesHolder.getAll();
          for(MonitorSite site:monitorSites){
              site.start();
          }
          sitesHolderMap.put(sitesHolder.getResourceType(),sitesHolder);
      }

    }



    /**
     * start monitor
     *
     * @param siteName
     */
    public void monitor(String siteName,ResourceType resourceType) {
        MonitorSite monitorSite = sitesHolderMap.get(resourceType).getMonitorSite(siteName);

        monitorSite.start();
    }


//    private void monitor(MonitorSite monitorSite) {
//        ScheduledFuture scheduledFuture = executorService.scheduleAtFixedRate(monitorSite.start(), 0, monitorSite.getPeriod(), TimeUnit.SECONDS);
//        monitorSite.setScheduledFuture(scheduledFuture);
//    }


//    public int getQueueSize() {
//        return ((ScheduledThreadPoolExecutor) executorService).getQueue().size();
//    }

    /**
     * stop monitor
     *
     * @param siteName
     * @param resourceType
     */
    public void cancel(String siteName, ResourceType resourceType) {
        Assert.hasText(siteName);
        MonitorSite monitorSite = sitesHolderMap.get(resourceType).getMonitorSite(siteName);
        monitorSite.stop();
        sitesHolderMap.get(resourceType).remove(siteName);
    }


    public void cancelAndRemove(String siteName, ResourceType resourceType){
        cancel(siteName,resourceType);
        sitesHolderMap.get(resourceType).remove(siteName);
    }





    /**
     * get monitor data
     *
     *
     *
     * @param siteName
     * @param resourceType
     * @return MonitorSite
     */
    public <T extends MonitorSite> T getMonitorInf(String siteName, ResourceType resourceType) {
        Assert.hasText(siteName);
        MonitorSite monitorSite = sitesHolderMap.get(resourceType).getMonitorSite(siteName);
        return (T) monitorSite;
    }

}
