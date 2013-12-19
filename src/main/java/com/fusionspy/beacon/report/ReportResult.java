package com.fusionspy.beacon.report;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ReportResult {

    private DateTime startTime, endTime;

    private Double maxAvg,minAvg,avg;

   // private Ordering ordering = Ordering.natural();

    private List<Double> avgs = newArrayList();

    private List<Statistics> statisticsList = newArrayList();

    public DateTime getStartTime() {
        return startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    void setMaxAvg(Statistics statistics) {
        if(statistics.getAvg() == null)
            return;
        if(getMaxAvg() == null){
            maxAvg = statistics.getAvg();
        }else{
            maxAvg = getMaxAvg()>statistics.getAvg()?maxAvg:statistics.getAvg();
        }
    }

    public Double getMinAvg() {
        return minAvg;
    }

    void setMinAvg(Statistics statistics) {
        if(statistics.getAvg() == null)
            return;
        if(getMinAvg() == null){
            minAvg = statistics.getAvg();
        }else{
            minAvg = getMinAvg()>statistics.getAvg()?statistics.getAvg():minAvg;
        }
    }

    public Double getAvg() {
        return avg;
    }

    public void setStartTime(DateTime dateTime) {
        this.startTime = dateTime;
    }

    public void setEndTime(DateTime dateTime) {
        this.endTime =  dateTime;
    }

    public Double getMaxAvg() {
        return maxAvg;
    }

    public void addStatistics(Statistics statistics){

        setMinAvg(statistics);
        setMaxAvg(statistics);
        setAvg(statistics);
        this.statisticsList.add(statistics);
    }

    void setAvg(Statistics statistics) {
        avgs.add(statistics.getAvg());
        if(statistics.getAvg() == null)
            return;
        Double temp = 0d;
        for(Double avg:avgs){
            if(avg ==null)
                continue;
            temp+=avg;
        }
        avg = BigDecimal.valueOf(temp/avgs.size()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public List<Statistics> getStatistics() {

       Collections.sort(this.statisticsList, Ordering.from(StatisticsOrder.instance));
       return this.statisticsList;
    }

    public ListMultimap<String,Statistics> getStatisticsMap(){
        return Multimaps.index(this.getStatistics(),new MultimapFunction());
    }

    static class MultimapFunction implements Function<com.fusionspy.beacon.report.Statistics, java.lang.String>{

        @Nullable
        @Override
        public java.lang.String apply(@Nullable com.fusionspy.beacon.report.Statistics input) {
            return StringUtils.isBlank(input.getName())?input.getResourceId():input.getName();
        }
    }

    static class StatisticsOrder implements Comparator<Statistics> {

        static StatisticsOrder instance = new StatisticsOrder();

        @Override
        public int compare(Statistics o1, Statistics o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    }

}
