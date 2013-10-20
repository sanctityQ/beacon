package com.fusionspy.beacon.site.tux.entity;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * siteSettings alertType
 * User: qc
 * Date: 11-9-5
 * Time: 上午11:00
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AlertType {

    static final AlertType DEFAULT  = new  AlertType();

    static{
         DEFAULT.setEmailReceiver(EMailReceiver.DEFAULT);
         Alert alert = new Alert();
         alert.setAlert(Alert.ENABLE);
         DEFAULT.setMicroBlog(alert);
         DEFAULT.setPlaySound(PlaySound.DEFAULT);
         DEFAULT.setSmsReceiver(SMSReceiver.DEFAULT);
    }

    private Alert microBlog;

    private PlaySound playSound;

    private SMSReceiver smsReceiver;

    private EMailReceiver emailReceiver;




    @XmlElement(name="EMailReceiver")
    public EMailReceiver getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(EMailReceiver emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    @XmlElement(name="MicroBlog")
    public Alert getMicroBlog() {
        return microBlog;
    }

    public void setMicroBlog(Alert microBlog) {
        this.microBlog = microBlog;
    }

    @XmlElement(name="PlaySound")
    public PlaySound getPlaySound() {
        return playSound;
    }

    public void setPlaySound(PlaySound playSound) {
        this.playSound = playSound;
    }

    @XmlElement(name="SmsReceiver")
    public SMSReceiver getSmsReceiver() {
        return smsReceiver;
    }

    public void setSmsReceiver(SMSReceiver smsReceiver) {
        this.smsReceiver = smsReceiver;
    }

    public List<String> getReceivers(AlarmType alarmType) {
        if(alarmType.equals(emailReceiver.showAlarmType()))
            return  Arrays.asList(StringUtils.split(emailReceiver.getEmailReceivers(), ";"));
        else
            return new ArrayList<String>();
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class PlaySound extends AlarmAlert{

        private String playSoundFile;

        public static final PlaySound DEFAULT;

        static{
             DEFAULT = new PlaySound();
             DEFAULT.setPlaySoundFile(StringUtils.EMPTY);
             DEFAULT.setAlert(DISABLE);
        }

        @XmlAttribute(name="PlaySoundFile")
        public String getPlaySoundFile() {
            return playSoundFile;
        }

        public void setPlaySoundFile(String playSoundFile) {
            this.playSoundFile = playSoundFile;
        }

        public AlarmType showAlarmType(){
            return  AlarmType.SOUND;
        }
    }

    public static class SMSReceiver extends AlarmAlert{

        static final SMSReceiver DEFAULT = new SMSReceiver();

        static{
            DEFAULT.setSmsReceivers(StringUtils.EMPTY);
            DEFAULT.setAlert(ENABLE);
        }

        private String  smsReceivers;


        @XmlAttribute(name="SMSReceivers")
        public String getSmsReceivers() {
            return smsReceivers;
        }

        public void setSmsReceivers(String smsReceivers) {
            this.smsReceivers = smsReceivers;
        }

        public AlarmType showAlarmType(){
            return  AlarmType.SMS;
        }
    }

    public static class EMailReceiver extends AlarmAlert{

        static final EMailReceiver DEFAULT = new EMailReceiver();

        static{
         DEFAULT.setEmailReceivers(StringUtils.EMPTY);
         DEFAULT.setAlert(ENABLE);
        }

        private String emailReceivers;

        @XmlAttribute(name="EMailReceivers")
        public String getEmailReceivers() {
            return emailReceivers;
        }

        public void setEmailReceivers(String emailReceivers) {
            this.emailReceivers = emailReceivers;
        }

          public AlarmType showAlarmType(){
            return  AlarmType.MAIL;
        }
    }

    public Iterator<AlarmType> alarmTypeList(){
        ArrayList<AlarmType>  alarmTypes = new ArrayList<AlarmType>();
//        if(playSound.enable()){
//             alarmTypes.add(playSound.showAlarmType());
//        }
        if(smsReceiver.enable()){
            alarmTypes.add(smsReceiver.showAlarmType());
        }
        if(emailReceiver.enable()){
            alarmTypes.add(emailReceiver.showAlarmType());
        }
        return alarmTypes.iterator();
    }

}
