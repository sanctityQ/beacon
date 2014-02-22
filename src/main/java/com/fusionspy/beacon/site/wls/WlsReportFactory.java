package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Report;
import com.fusionspy.beacon.report.StatisticReport;
import com.fusionspy.beacon.report.StatisticReportFactory;
import com.fusionspy.beacon.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;


@Component
class WlsReportFactory extends StatisticReportFactory {


    @Autowired
    private Set<WlsReport> wlsReports;

    //private Ordering<Attribute> naturalOrdering = Ordering.natural();

    @Override
    public ResourceType getResourceType() {
        return ResourceType.WEBLOGIC;
    }

    @Override
    protected void initChild() {
        for (Iterator<WlsReport> iterator = wlsReports.iterator(); iterator.hasNext(); ) {

            Report report = (Report) iterator.next();
            if(report instanceof StatisticReport){
                staticAttributes.add(report.getAttribute());
                statisticReports.add((StatisticReport)report);
            }
            attributes.add(report.getAttribute());
        }
    }

}
