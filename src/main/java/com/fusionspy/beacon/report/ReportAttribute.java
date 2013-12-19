package com.fusionspy.beacon.report;


import java.util.List;

public class ReportAttribute {

    private List<String> attributes;

    private String categories;

    private String orderAttribute;


    public ReportAttribute(List<String> attributes, String categories, String orderAttribute) {
        this.attributes = attributes;
        this.categories = categories;
        this.orderAttribute = orderAttribute;
    }

    public List<String> getAttributes() {
        return attributes;
    }


    public String getOrderAttributeName() {
        return orderAttribute;
    }


    public String getCategories() {
        return categories;
    }
}
