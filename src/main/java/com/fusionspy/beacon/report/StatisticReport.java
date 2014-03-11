package com.fusionspy.beacon.report;


import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public interface StatisticReport extends Report{

    ReportResult getStatistic(String resourceId,DateSeries dateSeries,Condition condition);

    LinkedHashSet<ConditionInitData> getConditionInitData();
}
