package com.fusionspy.beacon.site;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;
import com.fusionspy.beacon.web.BeaconLocale;

import java.lang.reflect.Method;

/**
 * alert messgae
 * User: qc
 * Date: 11-9-22
 * Time: 下午4:49
 */
public class AlertMessage {

    public static final String MSG="_MSG";

    public String getMessage(AlarmMessageFormat alarmMessageFormat,Object... objects){
       IMessageConveyor mc = new MessageConveyor(BeaconLocale.getBeaconLocale());

       return mc.getMessage(alarmMessageFormat, objects);
    }

}
