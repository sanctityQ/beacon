package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.dao.TuxSvrsDao;
import com.google.common.collect.Lists;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProgressCpuUsedReport<TuxsvrsEntity> implements TuxReport, StatisticTopReport{

    private Attribute attribute;

    @Autowired
    private TuxSvrsDao svrsDao;

    @Override
    public List<TuxsvrsEntity> statisticByTop(String resourceId, DateSeries dateSeries, TopFilter top) {
        PageRequest pageRequest = new PageRequest(0,top.getTopNum(), Sort.Direction.DESC,"cpuuse");
        ReportQuery query = dateSeries.getQuery();
        Page page =svrsDao.findByRectimeBetweenAndSitename(query.getStartDateTime().toDate(),
                query.getEndDateTime().toDate(),resourceId,pageRequest);
        return page.getContent();
    }

    @Override
    public ReportAttribute reportAttribute() {
        return new ReportAttribute(Lists.newArrayList("progname", "cpuuse", "processid"),"progname","cpuuse");
    }

    @Override
    public ReportResult getStatistic(String resourceId, DateSeries dateSeries) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Attribute getAttribute() {
        if(attribute == null){
            attribute = new Attribute();
            attribute.setAttribute("PROGRESS_CPU_USED");
            attribute.setAttributeCn("进程CPU使用率");
            attribute.setUnits("%");
        }
        return attribute;
    }
}
