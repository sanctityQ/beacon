package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxResourceDao;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
class ServiceCpuUsedReport extends StatisticForwardReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxResourceDao resourceDao;

    @Override
    public Statistics getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
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
