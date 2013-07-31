package com.fusionspy.beacon.site.tux;


import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxResourceDao;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class HostMemFreeReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxResourceDao resourceDao;

    @Override
    public ReportResult getStatistic(String resourceId, DateSeries dateSeries) {
        ReportQuery query = dateSeries.getQuery();
        ReportResult reportResult = new ReportResult();
        for(TimePeriod timePeriod:query.getPeriods()){
            Statistics statistics =  resourceDao.statisticHostMemFreeByRectimeBetween(resourceId,
                    new Timestamp(timePeriod.getStartDateTime().getMillis()),
                    new Timestamp(timePeriod.getEndDateTime().getMillis()));
            statistics.setTimePeriod(timePeriod);
            reportResult.addStatistics(statistics);
        }
        reportResult.setStartTime(query.getStartDateTime());
        reportResult.setEndTime(query.getEndDateTime());
        return reportResult;
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("MEM_FREE");
            attribute.setAttributeCn("主机空闲内存");
            attribute.setUnits("M");
        }
        return attribute;
    }
}
