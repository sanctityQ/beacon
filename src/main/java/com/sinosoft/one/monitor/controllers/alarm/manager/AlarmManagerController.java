package com.sinosoft.one.monitor.controllers.alarm.manager;

import com.sinosoft.one.monitor.alarm.domain.AlarmService;
import com.sinosoft.one.monitor.alarm.model.Alarm;
import com.sinosoft.one.monitor.alarm.repository.AlarmRepository;
import com.sinosoft.one.monitor.application.model.UrlTraceLog;
import com.sinosoft.one.monitor.application.repository.ApplicationRepository;
import com.sinosoft.one.monitor.application.repository.UrlTraceLogRepository;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.db.oracle.repository.InfoRepository;
import com.sinosoft.one.monitor.os.linux.repository.OsRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 告警列表和告警详细信息处理Controller.
 * User: zfb
 * Date: 13-3-8
 * Time: 下午3:11
 */
@Path("alarmmanager")
public class AlarmManagerController {

    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AlarmService alarmService;
    @Autowired
    OsRepository osRepository;
    @Autowired
    InfoRepository infoRepository;
    @Autowired
    UrlTraceLogRepository urlTraceLogRepository;

    @Get("list")
    public String getAlarmList(Invocation inv){
        return "alarmList";
    }

    //向页面返回json数据
    private void getJsonDataOfAlarms(Page<Alarm> alarms,Invocation inv) throws Exception {
        Gridable<Alarm> gridable=new Gridable<Alarm>(alarms);
        gridable.setIdField("id");
        gridable.setCellStringField("status,message,appName,monitorType,recordTime");
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new Exception("JSON数据转化出错！",e);
        }
    }

    //没有选择时间和类型的ajax请求
    @Post("alarm/")
    public void getAlarmListWithNoChoice(@Param("pageNo")int pageNo,@Param("rowNum")int rowNum,Invocation inv) throws Exception {
        PageRequest pageRequest = new PageRequest(pageNo,rowNum);
        Page<Alarm> allAlarms= alarmService.queryAlarmsByPage(pageRequest);
        getAlarmListWithGivenCondition(allAlarms, inv);
    }

    //只选择时间,或者只选择类型的ajax请求
    @Post("alarm/timeType/{givenTimeOrType}")
    public void getAlarmListWithGivenTimeOrType(@Param("givenTimeOrType")String givenTimeOrType,Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        Page<Alarm> timeOrTypeAlarms = null;
        String givenTime="";
        String givenType="";
        if(!StringUtils.isBlank(givenTimeOrType)){
            if("twentyFourHours".equals(givenTimeOrType)){
                givenTime=String.valueOf(24);
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenTime(givenTime,pageRequest);
            }else if("thirtyDays".equals(givenTimeOrType)){
                givenTime=String.valueOf(30);
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenTime(givenTime,pageRequest);
            }else if(ResourceType.APPLICATION.name().equals(givenTimeOrType)){
                givenType=ResourceType.APPLICATION.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenType(givenType,pageRequest);
            }else if(ResourceType.OS.name().equals(givenTimeOrType)){
                givenType=ResourceType.OS.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenType(givenType,pageRequest);
            }else if(ResourceType.DB.name().equals(givenTimeOrType)){
                givenType=ResourceType.DB.name();
                timeOrTypeAlarms=alarmRepository.findAlarmsWithGivenType(givenType,pageRequest);
            }

            getAlarmListWithGivenCondition(timeOrTypeAlarms, inv);
        }else {
            timeOrTypeAlarms= alarmService.queryAlarmsByPage(pageRequest);
            getAlarmListWithGivenCondition(timeOrTypeAlarms, inv);
        }
    }

    //时间和类型都选择的ajax请求
    @Post("alarm/timeType/{givenTimeOrType}/resourceType/{givenType}")
    public void getAlarmListWithGivenTimeAndType(@Param("givenTime")String givenTime, @Param("givenType") ResourceType givenType,Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        if("twentyFourHours".equals(givenTime)){
            givenTime=String.valueOf(24);
        }else if("thirtyDays".equals(givenTime)){
            givenTime=String.valueOf(30);
        }
        Page<Alarm> allAlarmsWithGivenTimeAndType=alarmRepository.findAlarmsWithGivenTimeAndType(givenTime,givenType.name(),pageRequest);
        getAlarmListWithGivenCondition(allAlarmsWithGivenTimeAndType,inv);
    }

    //各种条件组个统一调用这个方法，获得告警列表
    private void getAlarmListWithGivenCondition(Page<Alarm> pageAlarms,Invocation inv) throws Exception {
        List<Alarm> newAlarms=new ArrayList<Alarm>();
        if(pageAlarms!=null&&pageAlarms.getContent()!=null&&pageAlarms.getContent().size()>0){
            List<Alarm> dbAlarms=pageAlarms.getContent();
                String statusStart="<div class='";
                /*String statusEnd="' onclick='viewRelevance()'></div>";*/
                String statusEnd="'</div>";
                String messageNameStart="<a href='javascript:void(0)' onclick='alarmDetailInfo(this)'>";
                String messageNameEnd="</a>";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i=0;i<pageAlarms.getContent().size();i++){
                Alarm tempAlarm=pageAlarms.getContent().get(i);
                String statusMiddle="";
                String tempStatusMiddle=getStatusOfAlarm(tempAlarm.getSeverity());
                if(!StringUtils.isBlank(tempStatusMiddle)){
                    //设置状态
                    statusMiddle=tempStatusMiddle;
                }
                //拼接状态字符串
                tempAlarm.setStatus(statusStart+statusMiddle+statusEnd);
                //得到告警标题
                String allMessage=tempAlarm.getMessage();
                if(!StringUtils.isBlank(allMessage)){
                    String[] messageArray=allMessage.split("<br>");
                    tempAlarm.setMessage(messageNameStart+messageArray[0]+messageNameEnd);
                }else {
                    tempAlarm.setMessage(messageNameStart+messageNameEnd);
                }
                //拼接应用中文名
                if(ResourceType.APPLICATION.name().equals(tempAlarm.getMonitorType())){
                    tempAlarm.setAppName(applicationRepository.findOne(tempAlarm.getMonitorId()).getCnName());
                }else if(ResourceType.OS.name().equals(tempAlarm.getMonitorType())){
                    tempAlarm.setAppName(osRepository.findOne(tempAlarm.getMonitorId()).getName());
                }else if(ResourceType.DB.name().equals(tempAlarm.getMonitorType())){
                    tempAlarm.setAppName(infoRepository.findOne(tempAlarm.getMonitorId()).getName());
                }
                //获得类型对应的中文名
                tempAlarm.setMonitorType(ResourceType.valueOf(tempAlarm.getMonitorType()).cnName());
                //格式化时间，供页面显示
                tempAlarm.setRecordTime(formatter.format(tempAlarm.getCreateTime()));
            }
                getJsonDataOfAlarms(pageAlarms,inv);
                return;
            /*gridable.setCellStringField("status,message,appName,monitorType,recordTime,ownerName");*/
        }else {
            getJsonDataOfAlarms(pageAlarms,inv);
            return;
        }
    }

    //获得与前台页面相对应的状态
    private  String getStatusOfAlarm(SeverityLevel severityLevel){
        if(severityLevel!=null){
            if(severityLevel.name().equals("CRITICAL")){
                return "poor";
            }else if(severityLevel.name().equals("WARNING")){
                return "y_poor";
            }else if(severityLevel.name().equals("INFO")){
                return "fine";
            }
        }
        return "";
    }

    //获得与前台页面相对应的图片
    private String getImageOfAlarm(SeverityLevel severityLevel){
        if(severityLevel!=null){
            if(severityLevel.name().equals("CRITICAL")){
                return "bussinessY2.gif";
            }else if(severityLevel.name().equals("WARNING")){
                return "bussinessY3.gif";
            }else if(severityLevel.name().equals("INFO")){
                return "bussinessY.gif";
            }
        }
        return "";
    }

    //告警详细信息页面
    @Get("detail/{alarmId}")
    public String getAlarmDetail(@Param("alarmId") String alarmId,Invocation inv){
        Alarm dbAlarm=alarmRepository.findOne(alarmId);
        if(ResourceType.APPLICATION.name().equals(dbAlarm.getMonitorType())){
            inv.addModel("monitorName",applicationRepository.findOne(dbAlarm.getMonitorId()).getCnName());
            inv.addModel("monitorType",ResourceType.APPLICATION.name());
            UrlTraceLog urlTraceLog=urlTraceLogRepository.findByAlarmId(dbAlarm.getId());
            if(null==urlTraceLog){
                inv.addModel("urlTraceLogUrlId","-1");
                inv.addModel("urlTraceLogId","notExist");
            }else{
                inv.addModel("urlTraceLogUrlId",urlTraceLog.getUrlId());
                inv.addModel("urlTraceLogId",urlTraceLog.getId());
            }
        }else if(ResourceType.OS.name().equals(dbAlarm.getMonitorType())){
            inv.addModel("monitorName",osRepository.findOne(dbAlarm.getMonitorId()).getName());
        }else if(ResourceType.DB.name().equals(dbAlarm.getMonitorType())){
            inv.addModel("monitorName",infoRepository.findOne(dbAlarm.getMonitorId()).getName());
        }
        inv.addModel("alarm",dbAlarm);
        inv.addModel("_cnName",dbAlarm.getSeverity().cnName());
        inv.addModel("alarmImage",getImageOfAlarm(dbAlarm.getSeverity()));
        //用以发送ajax，获得当前监视器的历史告警信息
        inv.addModel("monitorId",dbAlarm.getMonitorId());
        return "alarmDetail";
    }

    //当前监视器的历史告警信息
    @Post("history/{monitorId}")
    public void getPageOfHistoryAlarm(@Param("monitorId") String monitorId,Invocation inv) throws Exception {
        int currentPageNumber=Integer.valueOf(inv.getRequest().getParameter("pageNo"))-1;
        PageRequest pageRequest = new PageRequest(currentPageNumber,10);
        Page<Alarm> dbAlarms=alarmRepository.findByMonitorId(monitorId,pageRequest);
        if(null!=dbAlarms&&dbAlarms.getContent()!=null&&dbAlarms.getContent().size()>0){
            String statusStart="<div class='";
            String statusEnd="'></div>";
            String messageStart="<p class=\"magess\">";
            String messageEnd="</p>";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Alarm> tempAlarms=new ArrayList<Alarm>();
            for(int i=0;i<dbAlarms.getContent().size();i++){
                Alarm tempAlarm=dbAlarms.getContent().get(i);
                tempAlarm.setStatus(statusStart+getStatusOfAlarm(tempAlarm.getSeverity())+statusEnd);
                tempAlarm.setRecordTime(formatter.format(tempAlarm.getCreateTime()));
                tempAlarm.setMessage(messageStart+tempAlarm.getMessage()+messageEnd);
            }

            /*for(Alarm alarm:dbAlarms){
                //拼接页面显示的状态
                alarm.setStatus(statusStart+getStatusOfAlarm(alarm.getSeverity())+statusEnd);
                alarm.setRecordTime(formatter.format(alarm.getCreateTime()));
                alarm.setMessage(messageStart+alarm.getMessage()+messageEnd);
                tempAlarms.add(alarm);
            }
            Page historyAlarmPage=new PageImpl(tempAlarms);*/
            Gridable<Alarm> gridable=new Gridable<Alarm>(dbAlarms);
            gridable.setIdField("id");
            gridable.setCellStringField("status,recordTime,message");
            try {
                UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
            } catch (Exception e) {
                throw new Exception("JSON数据转换出错！",e);
            }
        }
    }

    //批量删除告警详细信息
    @Post("batchdelete")
    public Reply batchDeleteAlarms(Invocation inv){
        String[] alarmIds=inv.getRequest().getParameterValues("alarmIds[]");
        //执行批量删除告警的SQL
        if(null!=alarmIds&&alarmIds.length>0){
            alarmRepository.batchDeleteAlarms(alarmIds);
            return Replys.with("successDeleted");
        }
        return Replys.with("failDeleted");
    }
}
