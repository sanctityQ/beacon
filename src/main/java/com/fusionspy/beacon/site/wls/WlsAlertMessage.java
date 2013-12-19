package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.AlarmMessageFormat;
import com.fusionspy.beacon.site.AlertMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-11-30
 * Time: 下午11:17
 * To change this template use File | Settings | File Templates.
 */
public class WlsAlertMessage extends AlertMessage {

    private Set<String> diedServerName = new HashSet<String>();
    private Set<String> heapAlarmServerName = new HashSet<String>();
    private Set<String> jdbcAlarmServerName = new HashSet<String>();
    private Set<String> threadAlarmServerName = new HashSet<String>();
    private Set<String> cpuAlarmServerName = new HashSet<String>();
    private String stopServerName;


    public String getMessageByAlarmMessageFormat(AlarmMessageFormat alarmMessageFormat){
        switch (alarmMessageFormat) {
            case WLS_DIED:
                return appendServerName(alarmMessageFormat, this.diedServerName);
            case WLS_HEAP:
                return appendServerName(alarmMessageFormat, this.heapAlarmServerName);
            case WLS_JDBC:
                return appendServerName(alarmMessageFormat, this.jdbcAlarmServerName);
            case WLS_THREAD:
                return appendServerName(alarmMessageFormat, this.threadAlarmServerName);
            case WLS_CPU:
                return appendServerName(alarmMessageFormat, this.cpuAlarmServerName);
            case WLS_STOP:
                return this.getMessage(alarmMessageFormat,this.stopServerName);
        }
        return null;
    }

    public String appendServerName(AlarmMessageFormat format,Collection<String> serverNames){
        if(serverNames.isEmpty())
            return StringUtils.EMPTY;
        //no current env data
        StringBuffer str = new StringBuffer(100);
        for(String serverName:serverNames){
            str.append("(").append(serverName).append(")");
        }
        return this.getMessage(format,str.toString());
    }

    public Set<String> getJdbcAlarmServerName() {
        return jdbcAlarmServerName;
    }

    public void addJdbcAlarm(String serverName) {
        this.jdbcAlarmServerName.add(serverName);
    }

    public Set<String> getDiedServerName() {
        return diedServerName;
    }

    public void addDiedServerName(String diedServerName) {
        this.diedServerName.add(diedServerName);
    }

    public Set<String> getHeapAlarmServerName() {
        return heapAlarmServerName;
    }

    public void addHeapAlarm(String heapAlarmServerName) {
        this.heapAlarmServerName.add(heapAlarmServerName);
    }

    public Set<String> getThreadAlarmServerName() {
        return threadAlarmServerName;
    }

    public void addThreadAlarm(String threadAlarmServerName) {
        this.threadAlarmServerName.add(threadAlarmServerName);
    }

    public Set<String> getCpuAlarmServerName() {
        return cpuAlarmServerName;
    }

    public void addCpuAlarm(String cpuAlarmServerName) {
        this.cpuAlarmServerName.add(cpuAlarmServerName);
    }

    public String getStopServerName() {
        return stopServerName;
    }

    public void setStopServerName(String stopServerName) {
        this.stopServerName = stopServerName;
    }
}
