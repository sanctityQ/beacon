package com.fusionspy.beacon.report;

import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StatisticFacade {

    private  Map<ResourceType,StatisticReportFactory> map = Maps.newHashMap();

    @Inject
    private Set<StatisticReportFactory> statisticReportFactorySet;


    @PostConstruct
    void init(){
        for(StatisticReportFactory statisticReportFactory:statisticReportFactorySet){
            addStatisticReportFactory(statisticReportFactory.getResourceType(),statisticReportFactory);
        }
    }

    private void addStatisticReportFactory(ResourceType resourceType,StatisticReportFactory statisticReportFactory){
        map.put(resourceType,statisticReportFactory);
    }


    public List<Attribute> getAttributes(ResourceType resourceType){
        return map.get(resourceType).getAttributes();
    }

    public  List<Attribute> getStatisticAttributes(ResourceType resourceType){
        return map.get(resourceType).getStatisticAttributes();
    }


    public  List<Attribute> getStatisticTopAttributes(ResourceType resourceType){
        return map.get(resourceType).getStatisticTopAttributes();
    }

    public StatisticReport getStatisticReport(ResourceType resourceType, String attributeId){
        return map.get(resourceType).getStatisticReport(attributeId);
    }

    public StatisticTopReport getTopReport(ResourceType resourceType,String attributeId){
        return map.get(resourceType).getTopReport(attributeId);
    }






}
