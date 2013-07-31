package com.fusionspy.beacon.report;


import org.joda.time.DateTime;

/**
 * 统计信息
 *
 */
public class Statistics {

    private Double max;

    private Double min;

    private Double avg;

    private TimePeriod timePeriod;


    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }
}
