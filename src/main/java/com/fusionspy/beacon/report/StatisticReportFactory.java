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

    private static Map<String,StatisticCacheReport> attributeReportMap = newHashMap();



    @PostConstruct
    public void init(){
        initChild();
        for(StatisticReport statisticReport : statisticReports){
            attributeReportMap.put(statisticReport.getAttribute().getAttribute(),
                    new StatisticCacheReport(statisticReport));
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
        return this.attributeReportMap.get(attribute);
    }

    @Override
    public void clean(){
        for(StatisticCacheReport statisticCacheReport : attributeReportMap.values()){
            statisticCacheReport.clean();
        }
    }


}
