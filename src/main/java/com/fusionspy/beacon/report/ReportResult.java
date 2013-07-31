package com.fusionspy.beacon.report;


import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;
import org.apache.commons.codec.language.bm.Languages;
import org.joda.time.DateTime;

import java.math.BigDecimal;
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
        };
        avg = BigDecimal.valueOf(temp/avgs.size()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public List<Statistics> getStatistics() {
        return this.statisticsList;
    }
}
