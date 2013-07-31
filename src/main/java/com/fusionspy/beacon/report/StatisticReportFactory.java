package com.fusionspy.beacon.report;


import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public abstract class StatisticReportFactory {

    @Autowired
    private StatisticFactory statisticFactory;

    @PostConstruct
    public void init(){
        statisticFactory.addStatisticReportFactory(getResourceType(),this);
        initChild();

    }

    protected abstract ResourceType getResourceType();

    protected abstract void initChild();

    /**
     * 返回报表属性
     * @return
     */
    public abstract List<Attribute> getAttribute();

    public abstract StatisticReport create(String attribute);


}
