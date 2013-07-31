package com.fusionspy.beacon.report;

import org.joda.time.DateTime;


public class TimePeriod {

    private DateTime startDateTime;

    private DateTime endDateTime;

    public TimePeriod(DateTime startDateTime, DateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }
}
