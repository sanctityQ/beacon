package com.fusionspy.beacon.report;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Maps.newHashMap;

/**
 * 统计报表缓存
 * @param <T>
 */
class StatisticCacheReport<T> implements StatisticTopReport<T>{

    private static final Logger logger  = LoggerFactory.getLogger(StatisticCacheReport.class);

    private  Cache<String,Cache<DateSeries,ReportResult>> resourceReportCache = CacheBuilder.newBuilder().maximumSize(1024)
            .expireAfterWrite(1, TimeUnit.DAYS).build();


    private final StatisticReport statisticReport;


    StatisticCacheReport(StatisticReport statisticReport){
        this.statisticReport = statisticReport;
    }

     void clean(){

        resourceReportCache.cleanUp();
        topCache.cleanUp();
        logger.debug("statistics cache clean up successful");
    }

    @Nullable
    @Override
    public ReportResult getStatistic(final String resourceId, final DateSeries dateSeries){

        Cache<DateSeries,ReportResult>  cache = resourceReportCache.getIfPresent(resourceId);

        if(cache == null){
            cache =  CacheBuilder.newBuilder().maximumSize(1024).expireAfterWrite(1,TimeUnit.DAYS).build();
            resourceReportCache.put(resourceId,cache);
        }

        try {
            if(dateSeries != DateSeries.today){
                return cache.get(dateSeries,new Callable<ReportResult>() {
                    @Override
                    public ReportResult call() throws Exception {
                        logger.debug("no cache's so load data,resource id is{},attribute is {},dateSeries is {}:",
                               new Object[]{ resourceId,
                                getAttribute().getAttribute(),dateSeries});
                        return statisticReport.getStatistic(resourceId,dateSeries);
                    }
                });
            }
            //today data is dynamic,so don't do cache
            else{
                return statisticReport.getStatistic(resourceId,dateSeries);
            }


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public Attribute getAttribute() {
        return statisticReport.getAttribute();
    }

    Cache<String,Cache<DateSeries,Map<TopFilter,List>>> topCache =CacheBuilder.newBuilder().maximumSize(1024)
    .expireAfterWrite(1, TimeUnit.DAYS).build();

    @Override
    public List statisticByTop(final String resourceId, final DateSeries dateSeries, final TopFilter top) {

        Cache<DateSeries,Map<TopFilter,List>>  cache = topCache.getIfPresent(resourceId);

        if(cache == null){
            cache =  CacheBuilder.newBuilder().maximumSize(1024).expireAfterWrite(1,TimeUnit.DAYS).build();
            topCache.put(resourceId,cache);
        }

        final StatisticTopReport statisticTopReport = toStatisticTopReport();

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
        return toStatisticTopReport().reportAttribute();
    }


    StatisticTopReport toStatisticTopReport(){
        if(statisticReport instanceof StatisticTopReport){
            return  (StatisticTopReport)statisticReport;
        }else {
            throw new RuntimeException("can't access this method,it not a StatisticTopReport");
        }
    }
}
