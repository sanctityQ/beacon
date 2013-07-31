package com.fusionspy.beacon.report;


import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;


public class ReportQuery {

    private DateTime startDateTime;

    private DateTime endDateTime;

    private List<TimePeriod> periods = Lists.newArrayList();

    public void addTimePeriod(TimePeriod timePeriod){
        periods.add(timePeriod);
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public List<TimePeriod> getPeriods(){
        return this.periods;
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
