package com.fusionspy.beacon.report;


import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@Component
public abstract class StatisticReportFactory implements StatisticClean{


    @Autowired
    List<StatisticReport> statisticReports;

    @Autowired
    List<StatisticTopReport> statisticTopReports;

    private static Map<String,StatisticCacheReport> staticReportMap = newHashMap();

    private static Map<String,StatisticsTopCacheReport> topReportMap = newHashMap();



    @PostConstruct
    public void init(){

        initChild();

        for(StatisticReport statisticReport : statisticReports){
            staticReportMap.put(statisticReport.getAttribute().getAttribute(),
                    new StatisticCacheReport(statisticReport));
        }

        for (StatisticTopReport statisticTopReport:statisticTopReports){
            topReportMap.put(statisticTopReport.getAttribute().getAttribute(),
                    new StatisticsTopCacheReport(statisticTopReport));
        }

    }

    protected abstract ResourceType getResourceType();

    protected abstract void initChild();

    /**
     * 返回报表属性
     * @return
     */
    public abstract List<Attribute> getAttributes();

    public StatisticReport getStatisticReport(String attribute){
        return this.staticReportMap.get(attribute);
    }

    @Override
    public void clean(){
        for(StatisticCacheReport statisticCache : staticReportMap.values()){
            statisticCache.clean();
        }

        for(StatisticsTopCacheReport statisticTopCache : topReportMap.values()){
            statisticTopCache.clean();
        }
    }


    public  StatisticTopReport getTopReport(String attribute){
        return this.topReportMap.get(attribute);

    }
}
