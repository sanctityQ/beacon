package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.report.Condition;
import com.fusionspy.beacon.report.ConditionInitData;
import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsJmsDao;
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
public class JMSByteCurrentCountReport extends StatisticForwardReport implements WlsReport {

    private Attribute attribute;

    @Autowired
    private WlsJmsDao wlsJmsDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate,Condition condition) {

        String jmsName = (String)condition.get("jmsName");
        if(StringUtils.isBlank(jmsName)){
            jmsName = StringUtils.EMPTY;
        }
        return Maps.uniqueIndex(wlsJmsDao.statisticBytesCurrentCount(resourceId, new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()),jmsName),
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
            attribute.setAttribute("JMS_BYTE_CURRENT_COUNT");
            attribute.setAttributeCn("JMS当前字节数");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("");
        }
        return attribute;
    }


    @Override
    public LinkedHashSet<ConditionInitData> getConditionInitData() {
        LinkedHashSet<ConditionInitData> conditionInitDatas = Sets.newLinkedHashSet();
        Set<String> jmsNames = wlsJmsDao.distinctJmsName();
        if (!jmsNames.isEmpty()) {
            ConditionInitData conditionInitData = new ConditionInitData();
            conditionInitData.setName("jmsName");
            conditionInitData.setValues(Sets.newTreeSet(jmsNames));
            conditionInitDatas.add(conditionInitData);
        }
        return conditionInitDatas;
    }
}
