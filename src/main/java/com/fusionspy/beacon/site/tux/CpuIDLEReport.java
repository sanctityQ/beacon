package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxResourceDao;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
class CpuIDLEReport extends StatisticForwardReport implements TuxReport {

    private Attribute attribute;

    @Autowired
    private TuxResourceDao resourceDao;

    @Override
    public Statistics getStatistic(String resourceId, DateTime startDate, DateTime endDate) {
        return resourceDao.statisticHostCpuUsedByRectimeBetween(resourceId,
                    new Timestamp(startDate.getMillis()),
                    new Timestamp(endDate.getMillis()));
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("CPU_IDLE");
            attribute.setAttributeCn("主机CPU使用率");
            attribute.setResourceType(ResourceType.Tuxedo);
            attribute.setUnits("%");
        }
        return attribute;
    }

}
