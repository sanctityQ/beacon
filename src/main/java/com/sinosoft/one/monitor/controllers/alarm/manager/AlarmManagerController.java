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
import com.sinosoft.one.monitor.resources.model.Resource;
import com.sinosoft.one.monitor.resources.repository.ResourcesRepository;
import com.sinosoft.one.monitor.threshold.model.SeverityLevel;
import com.sinosoft.one.monitor.utils.MessageUtils;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 告警列表和告警详细信息处理Controller.
 * User: zfb
 * Date: 13-3-8
 * Time: 下午3:11
 */
@Path
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
    @Autowired
    ResourcesRepository resourcesRepository;

    @Get("list")
    public String getAlarmList(Invocation inv){
        String resourceId=inv.getParameter("resourceId");
        inv.setAttribute("resourceId",resourceId);
        return "alarmList";
    }

    //向页面返回json数据
    private void getJsonDataOfAlarms(Page<Alarm> alarms,Invocation inv) {
        Gridable<Alarm> gridable=new Gridable<Alarm>(alarms);
        gridable.setIdField("id");
        gridable.setCellStringField("status,message,appName,monitorType,recordTime");
        UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
    }

    @Get("/resource/{resourceId}")
    public Reply getAlarmByResourceId(@Param("resourceId") String resourceId, Invocation inv){
        String contextPath =  inv.getServletContext().getContextPath();
        List<Alarm> alarms = alarmService.queryLatestAlarms(resourceId,10);
        /* 封装表格行数据信息List->rows*/
        List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        /* 循环构建表格行数据*/
        for(Alarm alarm : alarms) {
            Map<String, Object> row = new HashMap<String, Object>();
            List<String> cell = new ArrayList<String>();

			/* 健康状况 1-健康(绿色=fine) ；其它状态均不健康(红色=poor)*/
            String healthyClass = MessageUtils.SeverityLevel2CssClass(alarm.getSeverity());
			/* 构建预警详细信息地址*/
            String url = contextPath + "/alarm/manager/detail/"+alarm.getId();
			/* 格式化表格数据信息*/
            cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_DIV, healthyClass));
            String title = alarm.getMessage();
            String subTitle = title.length()>70 ? title.substring(0, 69)+"...." : title;
            cell.add(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORM_A_SUBTITLE, url, title, subTitle));
          //  DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            cell.add(DateFormatUtils.format(alarm.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            row.put("cell", cell);
            rows.add(row);
        }
        Map<String, Object> grid = new HashMap<String, Object>();
        grid.put("rows", rows);
        return Replys.with(grid).as(Json.class);
    }

    //没有选择时间和类型的ajax请求
    @Get
    public void getAlarmListWithNoChoice(@Param("pageNo")int pageNo,@Param("rowNum")int rowNum,@Param("severityLevel")SeverityLevel severityLevel,
                                         Invocation inv) throws Exception {
        PageRequest pageRequest = new PageRequest(pageNo-1,rowNum, Sort.Direction.DESC,"createTime");
        Page<Alarm> allAlarms= severityLevel ==null?alarmService.queryAlarmsByPage(pageRequest):alarmService.queryAlarmsByPageAndSeverityLevel(pageRequest,severityLevel);
        getAlarmListWithGivenCondition(allAlarms, inv);
    }

    //只选择时间,或者只选择类型的ajax请求
    @Get("day/{givenTime}")
    public void getAlarmListWithGivenTimeOrType(@Param("givenTime")int givenTime,@Param("pageNo")int pageNo,@Param("rowNum")int rowNum,
                                                @Param("severityLevel")SeverityLevel severityLevel,Invocation inv) throws Exception {
        PageRequest pageRequest = new PageRequest(pageNo-1,rowNum,Sort.Direction.DESC,"createTime");
        DateTime now = DateTime.now();
        DateTime startTime = now.minusDays(givenTime);
        Page<Alarm> alarms = severityLevel == null?
                alarmRepository.findByCreateTimeBetween(startTime.toDate(),now.toDate(),pageRequest)
                :alarmRepository.findByCreateTimeBetweenAndSeverity(startTime.toDate(),now.toDate(),severityLevel,pageRequest);
        getAlarmListWithGivenCondition(alarms, inv);
    }

    @Get("resourceType/{givenType}")
    public void findAlarmByResourceType(@Param("givenType") ResourceType givenType,@Param("pageNo")int pageNo,@Param("rowNum")int rowNum,
                                        @Param("severityLevel")SeverityLevel severityLevel,Invocation inv)throws Exception{
        PageRequest pageRequest = new PageRequest(pageNo-1,rowNum,Sort.Direction.DESC,"createTime");

        Page<Alarm> alarms= severityLevel == null?alarmRepository.findByMonitorType(givenType.name(),pageRequest)
                :alarmRepository.findByMonitorTypeAndSeverity(givenType.name(),severityLevel,pageRequest);
        getAlarmListWithGivenCondition(alarms, inv);
    }



    //时间和类型都选择的ajax请求
    @Get("day/{givenTime}/resourceType/{givenType}")
    public void getAlarmListWithGivenTimeAndType(@Param("givenTime")int givenTime, @Param("givenType") ResourceType resourceType,
                                                 @Param("severityLevel")SeverityLevel severityLevel,
                                                 @Param("pageNo")int pageNo,@Param("rowNum")int rowNum,Invocation inv) throws Exception {
        PageRequest pageRequest = new PageRequest(pageNo-1,rowNum,Sort.Direction.DESC,"createTime");
        DateTime now = DateTime.now();
        DateTime startTime = now.minusDays(givenTime);
        Page<Alarm> allAlarmsWithGivenTimeAndType=severityLevel == null?alarmRepository.findByCreateTimeBetweenAndMonitorType(startTime.toDate(),now.toDate(),resourceType.name(),pageRequest)
                :alarmRepository.findByCreateTimeBetweenAndMonitorTypeAndSeverity(startTime.toDate(),now.toDate(),resourceType.name(),severityLevel,pageRequest);
        getAlarmListWithGivenCondition(allAlarmsWithGivenTimeAndType,inv);
    }



    //各种条件组个统一调用这个方法，获得告警列表
    private void getAlarmListWithGivenCondition(Page<Alarm> pageAlarms,Invocation inv) throws Exception {
        List<Alarm> newAlarms=new ArrayList<Alarm>();
        if(pageAlarms!=null&&pageAlarms.getContent()!=null&&pageAlarms.getContent().size()>0){
            List<Alarm> dbAlarms=pageAlarms.getContent();
                String statusStart="<div class='";
                /*String statusEnd="' onclick='viewRelevance()'></div>";*/
                String statusEnd="'></div>";
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
                }else {
                    tempAlarm.setAppName(tempAlarm.getMonitorId());
                }
                //获得类型对应的中文名
                tempAlarm.setMonitorType(ResourceType.valueOf(tempAlarm.getMonitorType()).getDescription());
                //格式化时间，供页面显示
                tempAlarm.setRecordTime(formatter.format(tempAlarm.getCreateTime()));
            }
                getJsonDataOfAlarms(pageAlarms,inv);
            /*gridable.setCellStringField("status,message,appName,monitorType,recordTime,ownerName");*/
        }else {
            getJsonDataOfAlarms(pageAlarms,inv);
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
        }else{
            inv.addModel("monitorName",dbAlarm.getMonitorId());
        }
        inv.addModel("alarm",dbAlarm);
        inv.addModel("_cnName",dbAlarm.getSeverity().cnName());
        inv.addModel("alarmImage",getImageOfAlarm(dbAlarm.getSeverity()));
        //用以发送ajax，获得当前监视器的历史告警信息
        inv.addModel("monitorId",dbAlarm.getMonitorId());
        return "alarmDetail";
    }

    @Get("resource/{resourceId}/history")
    public String getHistoryAlarm(@Param("resourceId")String resourceId,Invocation inv){
       Resource resource = resourcesRepository.findOne(resourceId);
       inv.addModel("monitorId",resourceId);
       inv.addModel("monitorName",resource.getResourceName());
       return "history";
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
