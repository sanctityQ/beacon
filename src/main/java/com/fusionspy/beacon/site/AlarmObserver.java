package com.fusionspy.beacon.site;

import com.fusionspy.beacon.site.tux.entity.AlarmType;
import com.fusionspy.beacon.site.tux.entity.AlertType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * AlarmObserver
 * User: qc
 * Date: 11-10-17
 * Time: 下午3:30
 */
public class AlarmObserver {

    private static AlarmObserver self = new  AlarmObserver();

    private  AlarmObserver(){}

    public static AlarmObserver getInstance(){
        return self;
    }

    private Map<AlarmType, MonitorAlarm> monitorAlarms = new HashMap<AlarmType, MonitorAlarm>();

    public void register(AlarmType alarmType, MonitorAlarm monitorAlarm) {
        monitorAlarms.put(alarmType, monitorAlarm);
    }

    public void alarm(AlertType alertType,String message){
        for(Iterator<AlarmType> iter = alertType.alarmTypeList();iter.hasNext();){
           AlarmType alarmType = iter.next();
           MonitorAlarm monitorAlarm = monitorAlarms.get(alarmType) ;
           List<String> receivers = alertType.getReceivers(alarmType);
           if(monitorAlarm!=null)
              monitorAlarm.alarm(receivers,message);
        }


    }

}
