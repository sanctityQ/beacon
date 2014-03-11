package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Condition;
import com.fusionspy.beacon.report.ConditionInitData;
import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsJdbcDao;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
public class JDBCActiveCountReport extends StatisticForwardReport implements WlsReport {

    private Attribute attribute;

    @Autowired
    private WlsJdbcDao wlsJdbcDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {

        String jdbcName = (String)condition.get("jdbcName");
        if(StringUtils.isBlank(jdbcName)){
            jdbcName = StringUtils.EMPTY;
        }

        return Maps.uniqueIndex(wlsJdbcDao.statisticActiveCount(resourceId, new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()),jdbcName),
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
            attribute.setAttribute("JDBC_ACTIVE_COUNT");
            attribute.setAttributeCn("JDBC活动连接数");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("个");
        }
        return attribute;
    }

    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        LinkedHashSet<ConditionInitData> conditionInitDatas = Sets.newLinkedHashSet();
        Set<String> jdbcNames = wlsJdbcDao.distinctJdbcName();
        if(!jdbcNames.isEmpty()){
            ConditionInitData conditionInitData = new ConditionInitData();
            conditionInitData.setName("jdbcName");
            conditionInitData.setValues(Sets.newTreeSet(wlsJdbcDao.distinctJdbcName()));
            conditionInitDatas.add(conditionInitData);
        }
        return conditionInitDatas;
    }
}
