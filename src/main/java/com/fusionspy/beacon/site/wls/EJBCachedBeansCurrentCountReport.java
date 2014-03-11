package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Condition;
import com.fusionspy.beacon.report.ConditionInitData;
import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsEjbCacheDao;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;


@Service
public class EJBCachedBeansCurrentCountReport extends StatisticForwardReport implements WlsReport {

    private Attribute attribute;

    @Autowired
    private WlsEjbCacheDao wlsEjbCacheDao;

    @Override
    public Attribute getAttribute() {
        if (attribute == null) {
            attribute = new Attribute();
            attribute.setAttribute("CachedBeansCurrentCount");
            attribute.setAttributeCn("EJB缓存bean当前数量");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("个");
        }
        return attribute;
    }

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {
        String ejbName = (String)condition.get("ejbName");
        if(StringUtils.isBlank(ejbName)){
            ejbName = StringUtils.EMPTY;
        }
        Iterable<Statistics> iterable = wlsEjbCacheDao.statisticCacheBeanCurCount(resourceId, new Timestamp(startDate.getMillis()), new Timestamp(endDate.getMillis()),ejbName);

        return Maps.uniqueIndex(iterable, new Function<Statistics, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Statistics input) {
                return input.getName();
            }
        });
    }



    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        LinkedHashSet<ConditionInitData> conditionInitDatas = Sets.newLinkedHashSet();

        Set<String> ejbNames = wlsEjbCacheDao.findDistinctEjbName();
        if(!ejbNames.isEmpty()) {
            ConditionInitData conditionInitData = new ConditionInitData();
            conditionInitData.setName("ejbName");
            conditionInitData.setValues(Sets.newTreeSet(ejbNames));
            conditionInitDatas.add(conditionInitData);
        }
        return conditionInitDatas;
    }
}
