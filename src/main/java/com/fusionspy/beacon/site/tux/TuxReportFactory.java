package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.StatisticReport;
import com.fusionspy.beacon.report.StatisticReportFactory;
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

    //private Ordering<Attribute> naturalOrdering =  Ordering.natural();

    private List<Attribute> attributes = newArrayList();;


    @Override
    public ResourceType getResourceType() {
        return ResourceType.Tuxedo;
    }

    @Override
    protected void initChild() {
        for (Iterator<TuxReport> iterator = tuxReports.iterator(); iterator.hasNext(); ) {
            attributes.add(((StatisticReport)iterator.next()).getAttribute());
        }
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }


}
