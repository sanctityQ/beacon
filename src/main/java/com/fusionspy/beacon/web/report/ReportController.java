package com.fusionspy.beacon.web.report;


import com.alibaba.fastjson.JSON;
import com.fusionspy.beacon.report.*;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.attribute.model.Attribute;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesCache;
import com.sinosoft.one.monitor.utils.Reflections;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path
public class ReportController {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private StatisticFactory factory;


    @Autowired
    private ResourcesCache resourcesCache;

    @Get
    public String view(Invocation inv){
        //TODO 先写死，后期需要调整为ajax动态加载属性值
        List<Attribute> attributes = factory.getAttributes(ResourceType.Tuxedo);
        inv.addModel("attributes",attributes);
        return "view";
    }


    @Get("/resourceType/{type}/attribute/{attribute}")
    public String serverResource(@Param("type")ResourceType type,@Param("attribute")String attribute,
                                 @Param("resourceId")String resourceId,@Param("dateSeries")DateSeries series,
                                 Invocation inv){

        StatisticReport statisticReport = factory.getInstance(type, attribute);



        ReportResult reportResult = statisticReport.getStatistic(resourceId,series);
        toGridJsonData(reportResult,series,inv);


        //view data
        inv.addModel("dateSeries",series);
        //TODO resourceType应该细化到tuxedo，现在仍然为APP_SERVER级别,当调整后可直接通过resource来获取到type
        inv.addModel("resourceType",type);
        inv.addModel("resource", StringUtils.isBlank(resourceId)?resourceId:resourcesCache.getResource(resourceId));
        inv.addModel("attribute",statisticReport.getAttribute());

        return "info";
    }

    @Get("/resourceType/{type}/attribute/{attribute}/top")
    public String top(@Param("type")ResourceType type,@Param("attribute")String attribute,
                      @Param("resourceId")String resourceId,@Param("dateSeries")DateSeries series,
                      @Param("top")TopFilter top,Invocation inv){
        StatisticTopReport statisticReport = (StatisticTopReport)factory.getInstance(type, attribute);
        //set top default value
        if(top ==null)top=TopFilter.five;
        List list = statisticReport.statisticByTop(resourceId,series,top);
        topDeal(list,statisticReport.reportAttribute(),inv);


        //view data
        inv.addModel("dateSeries",series);
        //TODO resourceType应该细化到tuxedo，现在仍然为APP_SERVER级别,当调整后可直接通过resource来获取到type
        inv.addModel("resourceType",type);
        inv.addModel("resource", StringUtils.isBlank(resourceId)?resourceId:resourcesCache.getResource(resourceId));
        inv.addModel("attribute",statisticReport.getAttribute());
        inv.addModel("top",top);

        return "topInfo";
    }

    void topDeal(List rows, final ReportAttribute reportAttribute, Invocation inv){

        //chart data
        final List<String> xAxis = Lists.newArrayList();
        final List<Double> chartData = Lists.newArrayList();
        List<Map<String,String>> grid = Lists.transform(rows,new Function<Object, Map<String,String>>() {
            @Nullable
            @Override
            public Map<String,String> apply(@Nullable Object input) {
                Map<String,String> m = Maps.newHashMap();
                for(String attribute:reportAttribute.getAttributes()){
                    m.put(attribute,String.valueOf(Reflections.invokeGetter(input, attribute)));
                };

                xAxis.add(m.get(reportAttribute.getCategories()));
                chartData.add(Double.parseDouble(m.get(reportAttribute.getChartData())));
                return m;
            }
        });

        String cellString = Joiner.on(",").join(reportAttribute.getAttributes());
        Page page = new PageImpl(grid);
        Gridable gridable = new Gridable(page);
        gridable.setIdField(reportAttribute.getCategories());
        gridable.setCellStringField(cellString);
        inv.addModel("gridData", UIUtil.with(gridable).as(UIType.Json).getConvertResult());
        inv.addModel("chartCategories", JSON.toJSONString(xAxis));
        inv.addModel("chartData", JSON.toJSONString(chartData) );
    }



    private void toGridJsonData(ReportResult reportResult,final DateSeries series, Invocation inv){
        if(reportResult.getStatistics().isEmpty())
            return;
        //chart data
        final List<String> xAxis = Lists.newArrayList();
        final List<Double> chartData = Lists.newArrayList();



        List<Map<String,String>> rows = Lists.transform(reportResult.getStatistics(),new Function<Statistics, Map<String,String>>() {
            @Nullable
            @Override
            public Map<String,String> apply(@Nullable Statistics input) {
                Map<String,String> m = Maps.newHashMap();
                m.put("date", input.getTimePeriod().getStartDateTime().toLocalDate().toString("yyyy-MM-dd"));
                if(series.equals(DateSeries.today)||series.equals(DateSeries.yesterday))
                    m.put("time",input.getTimePeriod().getStartDateTime().toLocalTime().toString("HH:mm:ss"));
                m.put("max",input.getMax()==null?"-":input.getMax().toString());
                m.put("min",input.getMin()==null?"-":input.getMin().toString());
                m.put("avg", input.getAvg() == null ? "-" : input.getAvg().toString());
                if(series.equals(DateSeries.lastWeek)||series.equals(DateSeries.lastMonth))
                    xAxis.add(input.getTimePeriod().getStartDateTime().toLocalDate().toString("yyyy-MM-dd"));
                else
                    xAxis.add(input.getTimePeriod().getStartDateTime().toLocalTime().toString("HH:mm:ss"));
                chartData.add(input.getAvg());
                return m;
            }
        });
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(rows);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);

        String cellString = new String("date,time,max,min,avg");
        if(series.equals(DateSeries.lastWeek)||series.equals(DateSeries.lastMonth))
            cellString = new String("date,max,min,avg");

        gridable.setIdField("date");
        gridable.setCellStringField(cellString);

        inv.addModel("gridData", UIUtil.with(gridable).as(UIType.Json).getConvertResult());
        inv.addModel("chartCategories", JSON.toJSONString(xAxis));
        inv.addModel("chartData", JSON.toJSONString(chartData) );
        inv.addModel("startTime",dateTimeFormatter.print(reportResult.getStartTime()));
        inv.addModel("endTime",dateTimeFormatter.print(reportResult.getEndTime()));
        inv.addModel("maxAvg",reportResult.getMaxAvg());
        inv.addModel("minAvg",reportResult.getMinAvg());
        inv.addModel("avg",reportResult.getAvg());
    }

    /**
     *
     * @param pageNo 请求页数
     * @param rowNum 代码行数
     * @param type
     * @param startDate
     * @param endDate
     * @param inv
     * @return
     */
    @Get("/serverResource/grid")
    public String serverResource( @Param("pageNo")int pageNo,@Param("rowNum")int rowNum,@Param("type")String type,
                   @Param("startDate")Date startDate,@Param("endDate")Date endDate,Invocation inv){

        PageRequest pageRequest = new PageRequest(pageNo-1,rowNum, Sort.Direction.DESC,"createTime");

//        Page<TuxresourceEntity> page = tuxResourceDao.findByRectimeBetween(
//                startDate,endDate, pageRequest);
        return "";
    }


}
