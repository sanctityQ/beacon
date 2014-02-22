package com.fusionspy.beacon.report;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.fusionspy.beacon.attribute.model.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Maps.newHashMap;

public class StatisticsTopCacheReport implements StatisticTopReport{


    private static final Logger logger  = LoggerFactory.getLogger(StatisticsTopCacheReport.class);


    private final StatisticTopReport statisticTopReport;


    Cache<String,Cache<DateSeries,Map<TopFilter,List>>> topCache =CacheBuilder.newBuilder().maximumSize(1024)
            .expireAfterWrite(1, TimeUnit.DAYS).build();

    StatisticsTopCacheReport(StatisticTopReport statisticTopReport){
        this.statisticTopReport = statisticTopReport;
    }


    void clean(){
        topCache.cleanUp();
        logger.debug("statistics cache clean up successful");
    }

    @Override
    public List statisticByTop(final String resourceId, final DateSeries dateSeries, TopFilter top) {
        Cache<DateSeries,Map<TopFilter,List>> cache = topCache.getIfPresent(resourceId);

        if(cache == null){
            cache =  CacheBuilder.newBuilder().maximumSize(1024).expireAfterWrite(1, TimeUnit.DAYS).build();
            topCache.put(resourceId,cache);
        }


        if(dateSeries!=DateSeries.today){
            try {
                Map<TopFilter, List> map = cache.get(dateSeries,new Callable<Map<TopFilter, List>>() {
                    @Override
                    public Map<TopFilter, List> call() throws Exception {
                        Map<TopFilter, List> map = newHashMap();
                        for(TopFilter t:TopFilter.values()){
                            map.put(t,statisticTopReport.statisticByTop(resourceId,dateSeries,t));
                        }
                        return map;
                    }
                });
                return map.get(top);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        //today data is dynamic,so don't do cache
        else {
            return statisticTopReport.statisticByTop(resourceId,dateSeries,top);
        }
    }

    @Override
    public ReportAttribute reportAttribute() {
        return statisticTopReport.reportAttribute();
    }

    @Override
    public Attribute getAttribute() {
        return statisticTopReport.getAttribute();
    }
}
