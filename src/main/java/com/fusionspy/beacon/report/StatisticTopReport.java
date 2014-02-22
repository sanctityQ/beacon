package com.fusionspy.beacon.report;


import java.util.List;
import java.util.Map;

public interface StatisticTopReport extends Report{

    List<Map<String,String>> statisticByTop(String resourceId,DateSeries dateSeries,TopFilter top);

    ReportAttribute reportAttribute();
}
