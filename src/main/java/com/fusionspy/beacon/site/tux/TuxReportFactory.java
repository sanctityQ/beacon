package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.StatisticReport;
import com.fusionspy.beacon.report.StatisticReportFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.google.common.collect.Maps.newHashMap;


@Component
class TuxReportFactory extends StatisticReportFactory {


    void addStatisticReport(StatisticReport statisticReport){
        reportMap.put(statisticReport.getAttribute(),statisticReport);
        attributeMapMap.put(statisticReport.getAttribute().getAttribute(),statisticReport.getAttribute());
    }


    @Autowired
    private Set<TuxReport> tuxReports;

    private Ordering<Attribute> naturalOrdering =  Ordering.natural();


    private Map<Attribute,StatisticReport> reportMap = newHashMap();
    private Map<String,Attribute> attributeMapMap = newHashMap();

    @Override
    protected ResourceType getResourceType() {
        return ResourceType.Tuxedo;
    }

    @Override
    protected void initChild() {
        for (Iterator<TuxReport> iterator = tuxReports.iterator(); iterator.hasNext(); ) {
            addStatisticReport(iterator.next());
        }
    }

    @Override
    public List<Attribute> getAttribute() {
        return naturalOrdering.sortedCopy(reportMap.keySet());
    }


    @Override
    public StatisticReport create(String attribute){
        return reportMap.get(attributeMapMap.get(attribute));
    }

}
