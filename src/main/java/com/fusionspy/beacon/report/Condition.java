package com.fusionspy.beacon.report;


import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;

import java.util.*;

public class Condition<String,V> extends ForwardingMap<String,V>{


    private final Map<String, V> map;

    public Condition(final ImmutableMap<String,V> map){
        this.map  = map;
    }

    @Override
    protected Map<String,V> delegate() {
        return this.map;
    }
}
