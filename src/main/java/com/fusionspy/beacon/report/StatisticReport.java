package com.fusionspy.beacon.report;


public interface StatisticReport extends Report{

    ReportResult getStatistic(String resourceId,DateSeries dateSeries);

}
