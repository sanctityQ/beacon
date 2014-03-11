package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.wls.dao.WlsEjbPoolDao;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Sets;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newTreeSet;

@Service
public class EJBPoolBeansInUseCurrentCountReport extends StatisticForwardReport implements WlsReport {

    private Attribute attribute;

    @Autowired
    private WlsEjbPoolDao wlsEjbPoolDao;

    @Autowired
    private ConditionDataCache conditionDataCache;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {
        String ejbName = (String)condition.get("ejbName");
        if(StringUtils.isBlank(ejbName)){
            ejbName = StringUtils.EMPTY;
        }
        return Maps.uniqueIndex(wlsEjbPoolDao.statisticBeansInUseCurrentCount(resourceId,
                new Timestamp(startDate.getMillis()), new Timestamp(endDate.getMillis()),ejbName),
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
            attribute.setAttribute("BeansInUseCurrentCount");
            attribute.setAttributeCn("EJB当前使用bean数量");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("个");
        }
        return attribute;
    }


    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        LinkedHashSet<ConditionInitData> conditionInitDatas = com.google.common.collect.Sets.newLinkedHashSet();
        Set<String> ejbNames =  wlsEjbPoolDao.findDistinctEjbName();
        if(!ejbNames.isEmpty()){
            ConditionInitData conditionInitData = new ConditionInitData();
            conditionInitData.setName("ejbName");
            conditionInitData.setValues(newTreeSet(wlsEjbPoolDao.findDistinctEjbName()));
            conditionInitDatas.add(conditionInitData);
        }
        return conditionInitDatas;
    }
}
