package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxSvrsStatsDao;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
class TpsReport extends TuxStatisticReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxSvrsStatsDao svrsStatsDao;

    @Override
    public Statistics statistic(String resourceId, DateTime startDate, DateTime endDate) {
        return svrsStatsDao.statisticTpsByRectimeBetween(resourceId,
                new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()));
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("TPS_DONE");
            attribute.setAttributeCn("TPS");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("");
        }
        return attribute;
    }
}
