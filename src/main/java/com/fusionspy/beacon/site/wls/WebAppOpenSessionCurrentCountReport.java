package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsWebappDao;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.util.date.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Map;

@Service
public class WebAppOpenSessionCurrentCountReport extends StatisticForwardReport implements WlsReport    {

    private Attribute attribute;

    @Autowired
    private WlsWebappDao wlsWebappDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        String start = DateUtils.toFormatString(startDate.toDate(), DateUtils.Formatter.YEAR_TO_SECOND);
        String end = DateUtils.toFormatString(endDate.toDate(), DateUtils.Formatter.YEAR_TO_SECOND);
        return Maps.uniqueIndex(wlsWebappDao.statisticOpenSessionCurrentCount(resourceId, start,end),
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
            attribute.setAttribute("OpenSessionsCurrentCount");
            attribute.setAttributeCn("webApp当前会话数");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("个");
        }
        return attribute;
    }
}
