package com.fusionspy.beacon.report;


import java.util.List;

public interface StatisticTopReport<T> extends StatisticReport{

    List<T> statisticByTop(String resourceId,DateSeries dateSeries,TopFilter top);

    ReportAttribute reportAttribute();
}
