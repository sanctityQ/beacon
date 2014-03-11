package com.fusionspy.beacon.report;


import java.util.Set;
import java.util.TreeSet;

public class ConditionInitData {

     private String name;


     private TreeSet<String> values;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<String> getValues() {
        return values;
    }

    public void setValues(TreeSet<String> values) {
        this.values = values;
    }
}
