package com.fusionspy.beacon.report;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Function;
import com.google.common.collect.*;
import com.fusionspy.beacon.utils.Reflections;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.collect.Maps.newHashMap;


@Service
public abstract class StaticTopForwardReport implements StatisticTopReport{


    @Autowired
    private StatisticsTopRepository statisticsTopRepository;

    private int saveNum = TopFilter.ten.getTopNum();


    @Override
    public List<Map<String,String>> statisticByTop(String resourceId, DateSeries dateSeries, TopFilter top){

        ReportQuery reportQuery = dateSeries.getQuery();
        DateTime startTime = reportQuery.getStartDateTime();
        DateTime endTime = reportQuery.getEndDateTime();

        List<Map<String, String>> list = Lists.newArrayList();

        if(dateSeries != DateSeries.today) {

            StatisticsTop statisticsTop = statisticsTopRepository.findByResourceIdAndAttributeAndStartTimeAndEndTime(resourceId,
                    this.getAttribute().getAttribute(),
                    startTime.toDate(), endTime.toDate());

            if (statisticsTop == null) {
                if(dateSeries == DateSeries.yesterday){
                    list= statisticsOneDayAndSave(resourceId, startTime, endTime);
                }
                //last week or last month
                else  {

                    //查询每天的数据进行记录最终返回
                    for (TimePeriod timePeriod : reportQuery.getPeriods()) {
                        List s = statisticsOneDayAndSave(resourceId, timePeriod.getStartDateTime(), timePeriod.getEndDateTime());
                        list.addAll(s);
                    }

                    //对结果进行排序
                    Collections.sort(list, Ordering.from(getComparable()));
                    //每次固定按照前10条记录进行存储
                    list = Lists.newArrayList(Iterables.limit(list,saveNum));
                    saveStatisticsTop(resourceId,startTime,endTime,list);
                }


            } else {
                list = JSON.parseObject(statisticsTop.getTopValue(), new TypeReference<List<Map<String,String>>>(){});
            }
            return  Lists.newArrayList(Iterables.limit(list,top.getTopNum()));

        }
        //当天数据
        else {
            return  statisticByTop(resourceId, startTime, endTime, top.getTopNum());
        }
    }

    /**
     * 获取1天数据数据，会自带存储
     * @param resourceId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,String>> statisticsOneDayAndSave(String resourceId,DateTime startTime,DateTime endTime) {
        StatisticsTop statisticsTop = statisticsTopRepository.findByResourceIdAndAttributeAndStartTimeAndEndTime(resourceId,
                this.getAttribute().getAttribute(),
                startTime.toDate(), endTime.toDate());
        if(statisticsTop!=null){
            return  JSON.parseObject(statisticsTop.getTopValue(), new TypeReference<List<Map<String,String>>>(){});
        }
        List<Map<String,String>> s = statisticByTop(resourceId, startTime, endTime, saveNum);
        saveStatisticsTop(resourceId,startTime,endTime,s);
        return s;
    }

    void saveStatisticsTop(String resourceId,DateTime startTime,DateTime endTime,List s){
        StatisticsTop statisticsTop = new StatisticsTop();
        statisticsTop.setStartTime(startTime.toDate());
        statisticsTop.setEndTime(endTime.toDate());
        statisticsTop.setResourceType(getAttribute().getResourceType());
        statisticsTop.setAttribute(getAttribute().getAttribute());
        statisticsTop.setResourceId(resourceId);
        String value = JSON.toJSONString(s);
        statisticsTop.setTopValue(value);
        statisticsTopRepository.save(statisticsTop);
    }


    protected List<Map<String, String>> convert(List list) {

        return Lists.transform(list, new Function<Object, Map<String, String>>() {
            @Nullable
            @Override
            public Map<String, String> apply(@Nullable Object input) {
                Map<String, String> out = newHashMap();
                for (String attribute : reportAttribute().getAttributes()) {
                    out.put(attribute, String.valueOf(Reflections.getFieldValue(input, attribute)));
                }
                return out;
            }
        });
    }


    protected abstract List<Map<String,String>> statisticByTop(String resourceId,DateTime startTime,DateTime endTime,int top);

    protected abstract Comparator<Map<String,String>> getComparable();

}
