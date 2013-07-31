package com.fusionspy.beacon.report;

import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StatisticFactory {

    private  Map<ResourceType,StatisticReportFactory> map = Maps.newHashMap();



    @Inject//只是为了调用StatisticReportFactory初始化方法
    private Set<StatisticReportFactory> statisticReportFactorySet;


    void addStatisticReportFactory(ResourceType resourceType,StatisticReportFactory statisticReportFactory){
        map.put(resourceType,statisticReportFactory);
    }


    public List<Attribute> getAttributes(ResourceType resourceType){
        return map.get(resourceType).getAttribute();
    }

    public StatisticReport getInstance(ResourceType resourceType,String attributeId){
        return  map.get(resourceType).create(attributeId);
    }




}
