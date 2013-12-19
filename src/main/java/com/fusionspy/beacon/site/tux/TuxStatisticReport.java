package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;

import java.util.Map;


public abstract class TuxStatisticReport extends StatisticForwardReport {

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        Map<String,Statistics> statisticsMap = Maps.newHashMap();
        statisticsMap.put(resourceId,statistic(resourceId,startDate,endDate));
        return statisticsMap;
    }

    public abstract Statistics statistic(String resourceId, DateTime startDate, DateTime endDate);
}
