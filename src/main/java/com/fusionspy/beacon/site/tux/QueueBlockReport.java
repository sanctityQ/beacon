package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxQueueStatsDao;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QueueBlockReport extends TuxStatisticReport implements TuxReport {

    private Attribute attribute;

    @Autowired
    private TuxQueueStatsDao queueStatsDao;

    @Override
    public Statistics statistic(String resourceId, DateTime startDate, DateTime endDate) {
        return queueStatsDao.statisticQueueBlockCountByRectimeBetween(resourceId,
                new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()));
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("QUE_BLOCK_COUNT");
            attribute.setAttributeCn("队列阻塞消息数量");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("");
        }
        return attribute;
    }
}
