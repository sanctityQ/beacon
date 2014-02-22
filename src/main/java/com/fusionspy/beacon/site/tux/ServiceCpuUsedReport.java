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
class ServiceCpuUsedReport extends TuxStatisticReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxResourceDao resourceDao;

    @Override
    public Statistics statistic(String resourceId, DateTime startDate, DateTime endDate) {
        return resourceDao.statisticServiceCpuUsedByRectimeBetween(resourceId,
                new Timestamp(startDate.getMillis()),
                new Timestamp(endDate.getMillis()));
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("SVR_CPU_USED");
            attribute.setAttributeCn("服务资源CPU使用率");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("%");
        }
        return attribute;
    }

}
