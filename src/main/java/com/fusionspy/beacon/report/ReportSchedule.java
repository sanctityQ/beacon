package com.fusionspy.beacon.report;


import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 报表定时任务
 */
@Component("reportSchedule")
class ReportSchedule {

    @Autowired
    private ResourcesRepository resourcesRepository;


    @Autowired
    private List<StatisticReport> statisticReports;


    public void execute() {

        //清除缓存
        StatisticCacheReport.clear();
        //统计
        statistic();

    }

    private void statistic(){
        for (StatisticReport statisticReport : statisticReports) {
            statisticReport = new StatisticCacheReport(statisticReport);
            for (Resource resource : resourcesRepository.findByResourceType(statisticReport.getAttribute().getResourceType())) {
                for (DateSeries dateSeries : DateSeries.values()) {
                    if (dateSeries.equals(DateSeries.today))
                        continue;
                    statisticReport.getStatistic(resource.getResourceId(), dateSeries);
                }
            }
        }
    }





}
