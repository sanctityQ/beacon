package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Report;
import com.fusionspy.beacon.report.StatisticReportFactory;
import com.fusionspy.beacon.site.tux.TuxReport;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;


@Component
class WlsReportFactory extends StatisticReportFactory {


    @Autowired
    private Set<WlsReport> wlsReports;

    //private Ordering<Attribute> naturalOrdering =  Ordering.natural();

    private List<Attribute> attributes = newArrayList();;


    @Override
    public ResourceType getResourceType() {
        return ResourceType.WEBLOGIC;
    }

    @Override
    protected void initChild() {
        for (Iterator<WlsReport> iterator = wlsReports.iterator(); iterator.hasNext(); ) {
            attributes.add(((Report)iterator.next()).getAttribute());
        }
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }


}