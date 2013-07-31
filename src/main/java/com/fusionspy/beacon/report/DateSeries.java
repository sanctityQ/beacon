package com.fusionspy.beacon.report;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

/**
 * 统计日期类型
 *
 */
public enum DateSeries {

    today {

        @Override
        public ReportQuery getQuery() {

            ReportQuery reportQuery = new ReportQuery();
            DateTime end = DateTime.now();
            DateTime start = end.withTimeAtStartOfDay();
            reportQuery.setEndDateTime(end);
            reportQuery.setStartDateTime(new DateTime(start));
            DateTime step = step(start);
            while(step.isBefore(end)){
                reportQuery.addTimePeriod(new TimePeriod(start,step));
                start = step;
                step = step(step);
            }
            if(start.isBefore(end)&&!step.isBefore(end))
                reportQuery.addTimePeriod(new TimePeriod(start,end));
            return reportQuery;
        }


        @Override
        public String getDescription() {
            return "今天";
        }


        private DateTime step(DateTime time){
            return time.plusHours(1);
        }

    },

    yesterday {
        @Override
        public ReportQuery getQuery() {
            ReportQuery reportQuery = new ReportQuery();
            DateTime end = DateTime.now().withTimeAtStartOfDay();
            DateTime start = end.minusDays(1);
            reportQuery.setStartDateTime(new DateTime(start));

            DateTime stepTime = step(start);
            while(start.isBefore(end)){
                reportQuery.addTimePeriod(new TimePeriod(start,stepTime));
                start = stepTime;
                stepTime = step(start);
            }
            reportQuery.setEndDateTime(stepTime.minusSeconds(1));
            return reportQuery;
        }

        private DateTime step(DateTime time){
            return time.plusHours(1);
        }

        @Override
        public String getDescription() {
            return "昨天";
        }
    },
    lastWeek {
        @Override
        public ReportQuery getQuery() {
            ReportQuery reportQuery = new ReportQuery();
            DateTime end = DateTime.now().withTimeAtStartOfDay();
            DateTime start = end.minusWeeks(1);
            reportQuery.setStartDateTime(new DateTime(start));
            DateTime stepTime = step(start);
            while(start.isBefore(end)){
                reportQuery.addTimePeriod(new TimePeriod(start,stepTime));
                start = stepTime;
                stepTime = step(start);
            }
            reportQuery.setEndDateTime(stepTime);
            return reportQuery;
        }

        private DateTime step(DateTime time){
            return time.plusDays(1);
        }

        @Override
        public String getDescription() {
            return "最近一周";
        }
    },

    lastMonth {

        @Override
        public ReportQuery getQuery() {
            ReportQuery reportQuery = new ReportQuery();
            DateTime end = DateTime.now().withTimeAtStartOfDay();
            DateTime start = end.minusMonths(1);
            reportQuery.setStartDateTime(new DateTime(start));
            DateTime stepTime = step(start);
            while(start.isBefore(end)){
                reportQuery.addTimePeriod(new TimePeriod(start,stepTime));
                start = stepTime;
                stepTime = step(start);
            }
            reportQuery.setEndDateTime(stepTime);
            return reportQuery;
        }

        private DateTime step(DateTime time){
            return time.plusDays(1);
        }

        @Override
        public String getDescription() {
            return "最近一月";
        }
    };

    public abstract ReportQuery getQuery();

    public abstract String getDescription();


    public static void main(String[] args){
        DateSeries today1 = DateSeries.today;
    }
}
