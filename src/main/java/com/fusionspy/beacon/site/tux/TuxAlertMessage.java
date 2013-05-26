package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.AlarmMessageFormat;
import com.fusionspy.beacon.site.AlertMessage;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * tux alert message
 * User: qc
 * Date: 11-10-17
 * Time: 下午5:17
 */
class TuxAlertMessage extends AlertMessage {

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    private Set<String> queueAlarmServerName = new HashSet<String>();

    private Set<String> memAlarmServerName = new HashSet<String>();

    private Set<String> cpuAlarmServerName = new HashSet<String>();

    private Set<String> diedServerName = new HashSet<String>();

    private Set<String> noTransServerName = new HashSet<String>();

    private Set<String> longBusyServerName = new HashSet<String>();

        //alarm stop
    private String stopServerName;

    private Date receiveDate;

    void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

     /**
     * add queue alarm server name
     * @param serverName
     */
    void addQueueAlarm(String serverName) {
        this.queueAlarmServerName.add(serverName);
    }

    void addMemAlarm(String serverName) {
       this.memAlarmServerName.add(serverName);
    }

    void addCpuAlarm(String serverName) {
        this.cpuAlarmServerName.add(serverName);
    }

    void addDiedServerName(String serverName) {
        this.diedServerName.add(serverName);
    }

    Set<String> getDiedServerName(){
        return this.diedServerName;
    }

    void addNoTrans(String serverName){
        this.noTransServerName.add(serverName);
    }

    void addLongBusy(String serverName) {
        this.longBusyServerName.add(serverName);
    }

    public void setStopServerName(String stopServerName) {
        this.stopServerName = stopServerName;
    }

    /**
     * get event messages
     * @return  List<String> messagse;
     */
     List<String> getMessages(){
        ArrayList<String> result = new ArrayList<String>();
        resultAdd(result, AlarmMessageFormat.TUX_DIED,this.diedServerName);
        resultAdd(result,AlarmMessageFormat.TUX_NOTRAN,this.noTransServerName);
        resultAdd(result,AlarmMessageFormat.TUX_BUSY,this.longBusyServerName);
        resultAdd(result,AlarmMessageFormat.TUX_QUE,this.queueAlarmServerName);
        resultAdd(result,AlarmMessageFormat.TUX_MEM,this.memAlarmServerName);
        resultAdd(result,AlarmMessageFormat.TUX_CPU,this.cpuAlarmServerName);
        if(StringUtils.isNotEmpty(stopServerName))
             result.add(this.getMessage(AlarmMessageFormat.TUX_STOP, receiveDate, this.stopServerName));
        return result;
    }


    String getAlarmMessage(){
       StringBuffer str = new StringBuffer(100);
       for(Iterator<String> iter = getMessages().iterator();iter.hasNext();){
          str.append(iter.next()).append("\r\n");
       }
       return str.toString();
    }

    void resultAdd(List result,AlarmMessageFormat format,Collection<String> serverNames){
        if(serverNames != null&&!serverNames.isEmpty()){
             if(format == AlarmMessageFormat.TUX_CPU) {
                result.add(noAppendServerName(format));
             }
             else{
                result.add(appendServerName(format,serverNames));
             }
        }
    }


    String getMessageByAlarmMessageFormat(AlarmMessageFormat alarmMessageFormat){
        switch (alarmMessageFormat) {
            case TUX_DIED:
                return appendServerName(alarmMessageFormat, this.diedServerName);
            case TUX_NOTRAN:
                return appendServerName(alarmMessageFormat, this.noTransServerName);
            case TUX_BUSY:
                break;
            case TUX_QUE:
                break;
            case TUX_MEM:
                break;
            case TUX_CPU:
                break;
            case TUX_STOP:
                break;
        }
        return null;
    }

    String appendServerName(AlarmMessageFormat format,Collection<String> serverNames){
       //no current env data
       StringBuffer str = new StringBuffer(100);
       for(String serverName:serverNames){
            str.append("(").append(serverName).append(")");
       }
       return this.getMessage(format, df.format(receiveDate), str.toString());
    }

    String noAppendServerName(AlarmMessageFormat format){
       return this.getMessage(format, df.format(receiveDate),StringUtils.EMPTY);
    }
}
