package com.fusionspy.beacon.report;


import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 报表定时任务
 */
@Component("reportSchedule")
class ReportSchedule {

    private static final Logger logger = LoggerFactory.getLogger(ReportSchedule.class);

    @Autowired
    private ResourcesRepository resourcesRepository;


    @Autowired
    private List<StatisticReport> statisticReports;


    @Autowired
    private List<StatisticClean> cleans;

    public void execute() {

        //统计
        statistic();
        //统计后清除处理
        clear();

    }

    void clear() {
        logger.debug("report Schedule task clean start");
        for(StatisticClean clean:cleans){
            clean.clean();
        }
    }

    void statistic(){
        DateTime now =  DateTime.now();
        logger.debug("report Schedule task statistic start,time is:{}",now);
        for (StatisticReport statisticReport : statisticReports) {
            for (Resource resource : resourcesRepository.findByResourceType(statisticReport.getAttribute().getResourceType())) {
                for (DateSeries dateSeries : DateSeries.values()) {
                    if (dateSeries.equals(DateSeries.today))
                        continue;
                    statisticReport.getStatistic(resource.getResourceId(), dateSeries);
                }
            }
        }
        logger.debug("report Schedule task statistic end,consumeTime is:{}",System.currentTimeMillis()-now.getMillis());
    }





}
