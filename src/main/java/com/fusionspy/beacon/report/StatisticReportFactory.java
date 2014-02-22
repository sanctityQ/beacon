package com.fusionspy.beacon.report;


import com.fusionspy.beacon.attribute.model.Attribute;
import com.fusionspy.beacon.common.ResourceType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@Component
public abstract class StatisticReportFactory implements StatisticClean{


    protected List<StatisticReport> statisticReports = newArrayList();

    protected List<StatisticTopReport> statisticTopReports = newArrayList();

    private Map<String,StatisticCacheReport> staticReportMap = newHashMap();

    private Map<String,StatisticsTopCacheReport> topReportMap = newHashMap();


    protected List<Attribute> attributes = newArrayList();

    protected List<Attribute> staticAttributes =  newArrayList();

    protected List<Attribute> staticTopAttributes =  newArrayList();

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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Attribute> getStatisticAttributes() {
        return staticAttributes;
    }

    public List<Attribute> getStatisticTopAttributes() {
        return staticTopAttributes;
    }
}
