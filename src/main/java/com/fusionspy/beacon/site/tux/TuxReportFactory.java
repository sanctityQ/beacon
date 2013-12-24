package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.Report;
import com.fusionspy.beacon.report.StatisticReport;
import com.fusionspy.beacon.report.StatisticReportFactory;
import com.fusionspy.beacon.report.StatisticTopReport;
import com.google.common.collect.Ordering;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;


@Component
class TuxReportFactory extends StatisticReportFactory {


    @Autowired
    private Set<TuxReport> tuxReports;


    @Override
    public ResourceType getResourceType() {
        return ResourceType.Tuxedo;
    }

    @Override
    protected void initChild() {
        for (Iterator<TuxReport> iterator = tuxReports.iterator(); iterator.hasNext(); ) {
            Report report =  (Report)iterator.next();
            if(report instanceof StatisticTopReport){
                staticTopAttributes.add(report.getAttribute());
            }
            if(report instanceof  StatisticReport){
                staticAttributes.add(report.getAttribute());
            }
            attributes.add(report.getAttribute());
        }
    }




}
