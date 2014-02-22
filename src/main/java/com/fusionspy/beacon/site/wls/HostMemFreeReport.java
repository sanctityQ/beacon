package com.fusionspy.beacon.site.wls;


import com.fusionspy.beacon.report.StatisticForwardReport;
import com.fusionspy.beacon.report.Statistics;
import com.fusionspy.beacon.site.wls.dao.WlsResourceDao;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Service("wlsHostMemFreeReport")
public class HostMemFreeReport extends StatisticForwardReport implements WlsReport {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Attribute attribute;

    @Autowired
    private WlsResourceDao resourceDao;

    @Override
    public Attribute getAttribute() {
        if (attribute == null) {
            attribute = new Attribute();
            attribute.setAttribute("MEM_FREE");
            attribute.setAttributeCn("主机空闲内存");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("M");
        }
        return attribute;
    }

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        logger.info("查询日期范围："+startDate.toString() + "--" + endDate.toString());
        Map<String, Statistics> map = newHashMap();
        Statistics statistics = resourceDao.statisticHostMemFreeByRectimeBetween(resourceId,
                new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()));
        map.put(resourceId, statistics);
        if(statistics != null) {
            logger.info("查询结果："+resourceId+";min:"+statistics.getMin() + ";max:"+statistics.getMax()+";avg:"+statistics.getAvg());
        }
        return map;
    }
}
