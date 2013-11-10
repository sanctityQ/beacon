package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxResourceDao;
import com.fusionspy.beacon.site.tux.dao.TuxcltsStatsDao;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
class ClientBusyCountReport implements TuxReport{

    private Attribute attribute;

    @Autowired
    private TuxcltsStatsDao cltsStatsDao;


    @Override
    public ReportResult getStatistic(String resourceId, DateSeries dateSeries) {
        ReportQuery query = dateSeries.getQuery();
        List<TimePeriod> timePeriods = query.getPeriods();
        ReportResult reportResult = new ReportResult();

        for(TimePeriod timePeriod:timePeriods){
            Statistics statistics =  cltsStatsDao.statisticBusyCountByRectimeBetween(resourceId,
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
            attribute.setAttribute("CLT_BUSY_COUNT");
            attribute.setAttributeCn("繁忙客户端数量");
            attribute.setUnits("");
        }
        return attribute;
    }


}
