package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.Condition;
import com.fusionspy.beacon.report.ConditionInitData;
import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class TuxStatisticReport extends StatisticForwardReport {

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {
        Map<String,Statistics> statisticsMap = Maps.newHashMap();
        statisticsMap.put(resourceId,statistic(resourceId,startDate,endDate));
        return statisticsMap;
    }

    public abstract Statistics statistic(String resourceId, DateTime startDate, DateTime endDate);

    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData(){
        return Sets.newLinkedHashSet();
    }
}
