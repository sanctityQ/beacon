package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxResourceDao;
import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class HostMemFreeReport extends TuxStatisticReport implements TuxReport {

    private Attribute attribute;

    @Autowired
    private TuxResourceDao resourceDao;

    @Override
    public Statistics statistic(String resourceId, DateTime startDate, DateTime endDate) {
        return resourceDao.statisticHostMemFreeByRectimeBetween(resourceId,
                new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()));
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("MEM_FREE");
            attribute.setAttributeCn("主机空闲内存");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("M");
        }
        return attribute;
    }
}
