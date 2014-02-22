package com.fusionspy.beacon.report;


import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Service
public abstract class StatisticForwardReport implements StatisticReport{


    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public final ReportResult getStatistic(String resourceId, DateSeries dateSeries) {
        ReportQuery query = dateSeries.getQuery();
        ReportResult reportResult = new ReportResult();
        for(TimePeriod timePeriod:query.getPeriods()){
            for(Statistics statistics:statistic(resourceId,timePeriod)){
                reportResult.addStatistics(statistics);
            }
        }
        reportResult.setStartTime(query.getStartDateTime());
        reportResult.setEndTime(query.getEndDateTime());
        return reportResult;
    }

    List<Statistics> statistic(String resourceId,TimePeriod timePeriod){

        List<Statistics> statistics =  statisticsRepository.findByResourceIdAndAttributeAndStartTimeAndEndTime
                (resourceId, getAttribute().getAttribute(),
                        new Timestamp(timePeriod.getStartDateTime().getMillis()),
                        new Timestamp(timePeriod.getEndDateTime().getMillis()));

        if(statistics.isEmpty()){
            statistics = createAndSaveStatistics(resourceId,timePeriod);
        }

        return  statistics;
    }

    boolean canSave(TimePeriod timePeriod){
        DateTime now = DateTime.now();
        //如果统计区间段的开启时间为当前系统时间的起始日期，那么得看获取时间是否正好为结束日期，不存储
        if(timePeriod.getStartDateTime().equals(now.withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0))){
           return false;
        }
        return true;
    }

    List<Statistics> createAndSaveStatistics(String resourceId,TimePeriod timePeriod){
        Map<String,Statistics>  map =  getStatistic(resourceId, timePeriod.getStartDateTime(), timePeriod.getEndDateTime());

        for(String name:map.keySet()) {
            Statistics statistics = map.get(name);
            statistics.setName(name);
            statistics.setResourceId(resourceId);
            statistics.setResourceType(getAttribute().getResourceType());
            statistics.setAttribute(getAttribute().getAttribute());
            statistics.setStartTime(timePeriod.getStartDateTime().toDate());
            statistics.setEndTime(timePeriod.getEndDateTime().toDate());
            if (canSave(timePeriod))
                statisticsRepository.save(statistics);
        }
        return Lists.newArrayList(map.values());
    }


    public abstract Map<String,Statistics> getStatistic(String resourceId,DateTime startDate, DateTime endDate);

}
