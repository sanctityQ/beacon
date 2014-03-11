package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Condition;
import com.fusionspy.beacon.report.ConditionInitData;
import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsWebappDao;
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
public class WebAppOpenSessionCurrentCountReport extends StatisticForwardReport implements WlsReport    {

    private Attribute attribute;

    @Autowired
    private WlsWebappDao wlsWebappDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {
        String webAppName = (String)condition.get("webAppName");
        if(StringUtils.isBlank(webAppName)){
            webAppName = StringUtils.EMPTY;
        }
        return Maps.uniqueIndex(wlsWebappDao.statisticOpenSessionCurrentCount(resourceId, new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()),webAppName),
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



    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        LinkedHashSet<ConditionInitData> conditionInitDatas = Sets.newLinkedHashSet();
        Set<String> webApps = this.wlsWebappDao.distinctWebAppName();
        if (!webApps.isEmpty()) {
            ConditionInitData conditionInitData = new ConditionInitData();
            conditionInitData.setName("webAppName");
            conditionInitData.setValues(Sets.newTreeSet(webApps));
            conditionInitDatas.add(conditionInitData);
        }
        return conditionInitDatas;
    }
}
