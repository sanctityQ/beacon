package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Condition;
import com.fusionspy.beacon.report.ConditionInitData;
import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsJvmDao;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class JVMFreeHeapReport extends StatisticForwardReport implements WlsReport {

    private Attribute attribute;

    @Autowired
    private WlsJvmDao wlsJvmDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {
        List<Statistics> list = wlsJvmDao.statisticFreeHeap(resourceId, new Timestamp(startDate.getMillis()), new Timestamp(endDate.getMillis()));
        return Maps.uniqueIndex(list,
                new Function<Statistics, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable Statistics input) {
                        return input.getName();
                    }
                });
    }

    @Override
    public Attribute getAttribute() {
        if (attribute == null) {
            attribute = new Attribute();
            attribute.setAttribute("HEAP_FREE");
            attribute.setAttributeCn("空闲HEAP");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("M");
        }
        return attribute;
    }


    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        return Sets.newLinkedHashSet();
    }
}
