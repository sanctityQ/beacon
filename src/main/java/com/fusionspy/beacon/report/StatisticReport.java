package com.fusionspy.beacon.report;


import com.sinosoft.one.monitor.attribute.model.Attribute;

import java.util.List;

public interface StatisticReport{

    ReportResult getStatistic(String resourceId,DateSeries dateSeries);

    public Attribute getAttribute();

}
