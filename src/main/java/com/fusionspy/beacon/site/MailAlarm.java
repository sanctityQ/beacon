package com.fusionspy.beacon.site;

import com.fusionspy.beacon.system.entity.AlarmType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

/**
 * mail alarm
 * User: qc
 * Date: 11-9-23
 * Time: 上午10:49
 */
public class MailAlarm implements MonitorAlarm {

    private JavaMailSender mailSender;

    private static Logger logger = LoggerFactory.getLogger(MailAlarm.class);

    private static AlarmObserver alarmObserver = AlarmObserver.getInstance();


    private String from;

    private String subject;

    private String textTemplate;

    public void init(){
         alarmObserver.register(AlarmType.MAIL,this);
    }

    public void setTextTemplate(String textTemplate) {
        this.textTemplate = textTemplate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void alarm(List<String> to,String message) {
        if(to.isEmpty())
            return;
        SimpleMailMessage templateMessage = new SimpleMailMessage();
        templateMessage.setFrom(this.from);
        templateMessage.setTo(to.toArray(new String[to.size()]));
        templateMessage.setSubject(this.subject);
        String content = String.format(textTemplate, message);
        templateMessage.setText(content);
        try{
            mailSender.send(templateMessage);
            logger.debug("mail has send to {}",to);
        }catch (Exception e){
            logger.error("mail send failed",e);
        }
    }
}
