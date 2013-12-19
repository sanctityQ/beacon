package com.fusionspy.beacon.report;

import org.joda.time.DateTime;



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

        @Override
        public String getDateTimeFormatter() {
            return "HH:mm:ss";
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
            reportQuery.setEndDateTime(end.minusSeconds(1));
            return reportQuery;
        }

        private DateTime step(DateTime time){
            return time.plusHours(1);
        }

        @Override
        public String getDescription() {
            return "昨天";
        }

        @Override
        public String getDateTimeFormatter() {
            return "HH:mm:ss";
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
                reportQuery.addTimePeriod(new TimePeriod(start,stepTime.minusSeconds(1)));
                start = stepTime;
                stepTime = step(start);
            }
            reportQuery.setEndDateTime(end.minusSeconds(1));
            return reportQuery;
        }

        private DateTime step(DateTime time){
            return time.plusDays(1);
        }

        @Override
        public String getDescription() {
            return "最近一周";
        }

        @Override
        public String getDateTimeFormatter() {
            return "yyyy-MM-dd";
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
                reportQuery.addTimePeriod(new TimePeriod(start,stepTime.minusSeconds(1)));
                start = stepTime;
                stepTime = step(start);
            }
            reportQuery.setEndDateTime(end.minusSeconds(1));
            return reportQuery;
        }

        private DateTime step(DateTime time){
            return time.plusDays(1);
        }

        @Override
        public String getDescription() {
            return "最近一月";
        }

        @Override
        public String getDateTimeFormatter() {
            return "yyyy-MM-dd";
        }
    };

    public abstract ReportQuery getQuery();

    public abstract String getDescription();

    public abstract String getDateTimeFormatter();


    public static void main(String[] args){
        DateSeries today1 = DateSeries.lastMonth;
        for(TimePeriod timePeriod:today1.getQuery().getPeriods()){
            System.out.println(timePeriod.getStartDateTime()+"||"+timePeriod.getEndDateTime());

        }
    }
}
