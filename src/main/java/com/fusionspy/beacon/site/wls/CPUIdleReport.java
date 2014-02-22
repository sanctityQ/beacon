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
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-12-19
 * Time: 下午12:14
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CPUIdleReport extends StatisticForwardReport implements WlsReport {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Attribute attribute;

    @Autowired
    private WlsResourceDao wlsResourceDao;

    @Override
    public Map<String, Statistics> getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        logger.info("查询日期范围："+startDate.toString() + "--" + endDate.toString());
        Statistics statistics = wlsResourceDao.statisticHostCpuUsedByRectimeBetween(resourceId, new Timestamp(startDate.getMillis()), new Timestamp(endDate.getMillis()));
        if(statistics != null) {
            logger.info("查询结果："+resourceId+";min:"+statistics.getMin() + ";max:"+statistics.getMax()+";avg:"+statistics.getAvg());
        }
        Map<String, Statistics> statisticsMap = new HashMap<String, Statistics>();
        statisticsMap.put(resourceId, statistics);
        return statisticsMap;
    }

    @Override
    public Attribute getAttribute() {
        if (attribute == null) {
            attribute = new Attribute();
            attribute.setAttribute("CPU_IDLE");
            attribute.setAttributeCn("主机CPU使用率");
            attribute.setResourceType(ResourceType.WEBLOGIC);
            attribute.setUnits("%");
        }
        return attribute;
    }
}
