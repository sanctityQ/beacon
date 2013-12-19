package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsJdbcDao;
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
public class JDBCActiveCountReport extends StatisticForwardReport implements WlsReport  {

    private Attribute attribute;

    @Autowired
    private WlsJdbcDao wlsJdbcDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        return Maps.uniqueIndex(wlsJdbcDao.statisticActiveCount(resourceId, new Timestamp(startDate.getMillis()),
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
            attribute.setAttribute("JDBC_ACTIVE_COUNT");
            attribute.setAttributeCn("JDBC活动连接数");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("个");
        }
        return attribute;
    }
}
