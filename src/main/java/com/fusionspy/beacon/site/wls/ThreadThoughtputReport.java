package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsThreadDao;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class ThreadThoughtputReport extends StatisticForwardReport implements WlsReport    {

    private Attribute attribute;

    @Autowired
    private WlsThreadDao wlsThreadDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        return Maps.uniqueIndex(wlsThreadDao.statisticThought(resourceId, new Timestamp(startDate.getMillis()), new Timestamp(endDate.getMillis())),
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
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("THREAD_THOUGHT");
            attribute.setAttributeCn("吞吐量");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("");
        }
        return attribute;
    }
}
