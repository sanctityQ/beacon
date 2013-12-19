package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsJmsDao;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class JMSByteCurrentCountReport extends StatisticForwardReport implements WlsReport  {

    private Attribute attribute;

    @Autowired
    private WlsJmsDao wlsJmsDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        return Maps.uniqueIndex(wlsJmsDao.statisticBytesCurrentCount(resourceId, new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis())), new Function<Statistics, String>() {
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
            attribute.setAttribute("JMS_BYTE_CURRENT_COUNT");
            attribute.setAttributeCn("JMS当前字节数");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("");
        }
        return attribute;
    }
}
