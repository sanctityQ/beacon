package com.fusionspy.beacon.report;


import java.util.List;

public class ReportAttribute {

    private List<String> attributes;

    private String categories;

    private String chartData;

    public ReportAttribute(List<String> attributes, String categories, String chartData) {
        this.attributes = attributes;
        this.categories = categories;
        this.chartData = chartData;
    }

    public List<String> getAttributes() {
        return attributes;
    }


    public String getChartData() {
        return chartData;
    }

    public String getCategories() {
        return categories;
    }
}
