package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxcltsStatsDao;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ClientTotalCountReport extends TuxStatisticReport implements TuxReport
{
    private Attribute attribute;

    @Autowired
    private TuxcltsStatsDao cltsStatsDao;


    @Override
    public Statistics statistic(String resourceId, DateTime startDate, DateTime endDate) {
        return cltsStatsDao.statisticTotalCountByRectimeBetween(resourceId,
                new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()));
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("CLT_COUNT");
            attribute.setAttributeCn("客户端数量");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("");
        }
        return attribute;
    }
}
