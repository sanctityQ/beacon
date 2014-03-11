package com.fusionspy.beacon.report;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.fusionspy.beacon.attribute.model.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Maps.newHashMap;


class StatisticCacheReport implements StatisticReport{

    private static final Logger logger  = LoggerFactory.getLogger(StatisticCacheReport.class);

    private  Cache<String,Cache<DateSeries,ReportResult>> resourceReportCache = CacheBuilder.newBuilder().maximumSize(1024)
            .expireAfterWrite(1, TimeUnit.DAYS).build();


    private final StatisticReport statisticReport;

    private ConditionDataCache conditionDataCache = ConditionDataCache.build();


    StatisticCacheReport(StatisticReport statisticReport){
        this.statisticReport = statisticReport;
    }

     void clean(){
        resourceReportCache.cleanUp();
        logger.debug("statistics cache clean up successful");
    }

    @Nullable
    @Override
    public ReportResult getStatistic(final String resourceId, final DateSeries dateSeries,final Condition condition){

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
                        return statisticReport.getStatistic(resourceId,dateSeries,condition);
                    }
                });
            }
            //today data is dynamic,so don't do cache
            else{
                return statisticReport.getStatistic(resourceId,dateSeries,condition);
            }


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        return conditionDataCache.merge(statisticReport);
    }



    @Override
    public Attribute getAttribute() {
        return statisticReport.getAttribute();
    }






}
