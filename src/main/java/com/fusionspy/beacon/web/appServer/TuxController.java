package com.fusionspy.beacon.web.appServer;

import com.alibaba.fastjson.JSONArray;
import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.site.tux.TuxHisData;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.site.tux.entity.SiteListEntity;
import com.fusionspy.beacon.site.tux.entity.SiteSettings;
import com.fusionspy.beacon.system.service.SystemService;
import com.fusionspy.beacon.web.BeaconLocale;
import com.fusionspy.beacon.web.Chart;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sinosoft.one.monitor.utils.MessageUtils;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.DefValue;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Delete;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.annotation.rest.Put;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Text;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import com.sinosoft.one.util.encode.JsonBinder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * tuxedo action
 * User: qc
 * Date: 11-8-30
 * Time: 下午8:26
 */
@Path("/tuxedo")
public class TuxController {


    private Logger logger = LoggerFactory.getLogger(getClass());

    JsonBinder jsonBinder =  JsonBinder.buildNonNullBinder();

    private static final String TOP = "Top";

    private static final String TIME_FORMAT="HH:mm:ss";

    private static final String DATE_FORMAT =  "yyyy-MM-dd HH:mm:ss";

    private DateFormat df = new SimpleDateFormat(TIME_FORMAT);

    private static String[] SORT_CSS_CLASS ={"one","two","three","four","five"};

    @Autowired
    private MonitorManage monitorManage ;

    @Autowired
    private SystemService systemService;

    @Put("/save")
    public Reply update(SiteListEntity appServer,Invocation invocation){
        systemService.addSite(appServer);
        monitorManage.cancel(appServer.getSiteName());
        monitorManage.monitor(appServer.getSiteName());
        return Replys.with("success").as(Text.class);
    }

    @Delete("/delete/{serverName}")
    public Reply delete(@Param("serverName")String serverName) {
        monitorManage.cancel(serverName);
        systemService.delSite(serverName);

        return Replys.with("success").as(Text.class);
    }


    @Post("/save")
    public Reply save(SiteListEntity appServer,@DefValue("zh_CN")Locale locale){
        systemService.addSite(appServer);
        BeaconLocale.setLocale(locale);
        monitorManage.monitor(appServer.getSiteName());
        return Replys.with("success").as(Text.class);
    }

    @Get("manager")
    public String list(){
        return "tuxedoList";
    }


    @Get("manager/add")
    public String add(){
        return "tuxedoAdd";
    }


    @Get("manager/list")
    public void list(@Param("type")String type,Invocation inv) throws Exception {

        List<SiteListEntity> siteList = systemService.getSites();
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(siteList.size());
        for (Iterator<SiteListEntity> iter = siteList.iterator(); iter.hasNext();) {
            l.add(convertGrid(iter.next(),inv.getRequest().getContextPath(),type));
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("siteName,siteIp,sitePort,interval,operation");
        gridable.setIdField("id");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
    }

    @Get("manager/view/{serverName}")
    public String detail(@Param("serverName")String name,Invocation invocation){
        SiteListEntity siteListEntity = systemService.getSite(name);
        invocation.addModel("server",siteListEntity);
        return "/appServer/tuxedo/manager/add";
    }


    private Map<String,String> convertGrid(SiteListEntity siteListEntity, String contextPath, String type) {

        Map<String,String> map = new HashMap<String, String>();
        map.put("id",siteListEntity.getSiteName());
        String siteName = "<a href='"+contextPath+"/appServer/tuxedo/view/"+siteListEntity.getSiteName()
                +"' target='_blank'>" +siteListEntity.getSiteName()+"</a>";
        map.put("siteName",siteName);
        map.put("siteIp",siteListEntity.getSiteIp());
        map.put("sitePort",String.valueOf(siteListEntity.getSitePort()));
        map.put("interval",String.valueOf(siteListEntity.getInterval()));
        map.put("operation","<a  href='javascript:void(0)' onclick='updRow(this)' class='eid'>编辑</a> " +
                "<a href='javascript:void(0)' class='eid' onclick='setTuxMergency(this)'>数据保存设置</a>"+
                "<a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>");
        return map;
    }



    @Delete("delete/{serverName}")
    public Reply delete(@Param("type")String type,@Param("serverName")String serverName) {
        monitorManage.cancel(serverName);
        systemService.delSite(serverName);
        logger.debug("delete server type is {} ,name is",type,serverName);

        return Replys.with("success").as(Text.class);
    }


    @Get("list")
    public void indexList(Invocation inv){
        List<Map<String,String>> datas = Lists.newArrayList();
        for(SiteListEntity siteListEntity:systemService.getSites()){

            TuxHisData monitorInf = monitorManage.getMonitorInf(siteListEntity.getSiteName());
            Map<String,String> map = Maps.newHashMap();
            String url = inv.getServletContext().getContextPath()+"/appServer/tuxedo/view/"+siteListEntity.getSiteName();
            map.put("siteName", MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, url, siteListEntity.getSiteName()));
            map.put("availability",MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_DIV,
                    MessageUtils.available2CssClass(!monitorInf.getProcessResult().isStopAlarmSignal())));
            map.put("rqDone", String.valueOf(monitorInf.getRqDoneCount()));
            datas.add(map);
        }

        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(datas);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("siteName,availability,rqDone");
        gridable.setIdField("siteName");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
    }

    @Post("start/{serverName}")
    public Reply startMonitor(@Param("serverName")String serverName,@DefValue("zh_CN")Locale locale){
        BeaconLocale.setLocale(locale);
        monitorManage.monitor(serverName);
        logger.debug("start server name is {} ", serverName);
        return Replys.with("success").as(Text.class);
    }

    @Get("view/{serverName}")
    public String showMonitor(@Param("serverName")String serverName,Invocation invocation){
        SiteListEntity siteListEntity = systemService.getSite(serverName);
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);
        invocation.addModel("serverName",serverName);
        invocation.addModel("serverType","tuxedo");
        invocation.addModel("tuxVersion",hisData.getTuxIniData().getSysrecsEntity().getProductver());
        invocation.addModel("systemboot",hisData.getTuxIniData().getSysrecsEntity().getSystemboot());
        invocation.addModel("rectime", DateTimeFormat.forPattern(DATE_FORMAT).print(new DateTime(hisData.getTuxIniData().getSysrecsEntity().getRectime())) );
        invocation.addModel("tuxRunSvr", hisData.getProcessResult().getTuxRes().getTuxrunsvr());
        invocation.addModel("tuxRunQueue", hisData.getProcessResult().getTuxRes().getTuxrunqueue());
        invocation.addModel("tuxRunClt", hisData.getProcessResult().getTuxRes().getTuxrunclt());
        invocation.addModel("osVersion", hisData.getTuxIniData().getSysrecsEntity().getOstype());
        invocation.addModel("cpuIdle", hisData.getProcessResult().getTuxRes().getCpuidle());
        invocation.addModel("memFree", hisData.getProcessResult().getTuxRes().getMemfree());
        invocation.addModel("agentVer", hisData.getTuxIniData().getSysrecsEntity().getAgentver());
        invocation.addModel("count", hisData.getMonitorCount());
        invocation.addModel("ip", siteListEntity.getSiteIp());
        invocation.addModel("port", siteListEntity.getSitePort());
        return "tuxedoInfo";
    }

    @Get("view/{serverName}/latest")
    public Reply showMonitorLatest(@Param("serverName")String serverName){
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);
        Map<String,String> reply = Maps.newHashMap();
        reply.put("cpuIdle",String.valueOf(hisData.getProcessResult().getTuxRes().getCpuidle()));
        reply.put("tuxRunQueue",String.valueOf(hisData.getProcessResult().getTuxRes().getTuxrunqueue()));
        reply.put("tuxRunClt", String.valueOf(hisData.getProcessResult().getTuxRes().getTuxrunclt()));
        reply.put("memFree",String.valueOf(hisData.getProcessResult().getTuxRes().getMemfree()));
        reply.put("count", String.valueOf(hisData.getMonitorCount()));
        return Replys.with(reply).as(Json.class);
    }


    public Reply getMonitorInf(String siteName){
      TuxHisData hisData = monitorManage.getMonitorInf(siteName);
      jsonBinder.setDateFormat(DATE_FORMAT);
      HashMap<String,Object> map = new HashMap<String,Object>();
      map.put("tuxVersion",hisData.getTuxIniData().getSysrecsEntity().getProductver());
      map.put("systemboot",hisData.getTuxIniData().getSysrecsEntity().getSystemboot());
      map.put("rectime",hisData.getTuxIniData().getSysrecsEntity().getRectime());
      map.put("tuxRunSvr",hisData.getProcessResult().getTuxRes().getTuxrunsvr());
      map.put("tuxRunQueue",hisData.getProcessResult().getTuxRes().getTuxrunqueue());
      map.put("tuxRunClt",hisData.getProcessResult().getTuxRes().getTuxrunclt());
      map.put("osVersion",hisData.getTuxIniData().getSysrecsEntity().getOstype());
      map.put("cpuIdle",hisData.getProcessResult().getTuxRes().getCpuidle());
      map.put("memFree",hisData.getProcessResult().getTuxRes().getMemfree());
      map.put("agentVer",hisData.getTuxIniData().getSysrecsEntity().getAgentver());
      map.put("count",hisData.getMonitorCount());
      String data = jsonBinder.toJson(map);
      return Replys.with(data).as(Json.class);
    }

    @Get("data/{type}/{serverName}")
    public void getInTimeData(@Param("type")String type,@Param("serverName")String serverName,Invocation invocation){
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);
        TuxInTimeData tuxInTimeData = hisData.getTuxInTimeData();
        if(type.equals("server")){
            getServerDate(tuxInTimeData.getServers(),invocation);
        }
        else if(type.equals("queue")){
            getQueDate(tuxInTimeData.getQueues(),invocation);
        }
        else if(type.equals("client")){
            getClientDate(tuxInTimeData.getClients(),invocation);
        }
        else if(type.equals("system")){
            getSystemDate(tuxInTimeData.getTuxSysData(),invocation);
        }

    }

    private void getServerDate(List<TuxsvrsEntity> servers, Invocation invocation){
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(servers.size());
        for(Iterator<TuxsvrsEntity> iterator = servers.iterator();iterator.hasNext();){
            TuxsvrsEntity svr = iterator.next();
            Map<String,String> map = new HashMap<String, String>();
            map.put("server",svr.getProgname());
            map.put("queueId",svr.getQueuename());
            map.put("processId",svr.getProcessid());
            map.put("rqDone",String.valueOf(svr.getRqdone()));
            map.put("currentSvc",svr.getCurrenctsvc());
            map.put("svrMin",String.valueOf(svr.getSvrmin()));
            map.put("svrMax",String.valueOf(svr.getSvrmax()));
            map.put("memUsed",String.valueOf(svr.getMemoryuse()));
            map.put("cpuUsed",String.valueOf(svr.getCpuuse()));
            l.add(map);
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("server,queueId,processId,rqDone,currentSvc,svrMin,svrMax,memUsed,cpuUsed");
        gridable.setIdField("server");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());

    }


    private void getQueDate(List<TuxquesEntity> servers, Invocation invocation){
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(servers.size());
        for(Iterator<TuxquesEntity> iterator = servers.iterator();iterator.hasNext();){
            TuxquesEntity que = iterator.next();
            Map<String,String> map = new HashMap<String, String>();
            map.put("server",que.getProgname());
            map.put("queueId",que.getIpcsid());
            map.put("srvCnt",String.valueOf(que.getSvrcnt()));
            map.put("queued",String.valueOf(que.getQueued()));
            l.add(map);
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("server,queueId,srvCnt,queued");
        gridable.setIdField("server");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
    }

    private void getClientDate(List<TuxcltsEntity> servers, Invocation invocation){
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(servers.size());
        for(Iterator<TuxcltsEntity> iterator = servers.iterator();iterator.hasNext();){
            TuxcltsEntity clt = iterator.next();
            Map<String,String> map = new HashMap<String, String>();
            map.put("name",clt.getClientname());
            map.put("pid",clt.getClientpid());
            map.put("addr",String.valueOf(clt.getClientaddr()));
            map.put("status",String.valueOf(clt.getClientstatus()));
            map.put("conTime",String.valueOf(clt.getConntime()));
            l.add(map);
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("name,pid,addr,status,conTime");
        gridable.setIdField("name");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
    }

    private void getSystemDate(TuxSysData sys, Invocation invocation) {
        List<Map<String, String>> l = new ArrayList<Map<String, String>>(1);
        Map<String, String> map = new HashMap<String, String>();
        map.put("coreFind", sys.getCoreFind());
        map.put("errorFind", sys.getErrorFind());
        map.put("warnFind", sys.getWarnFind());
        map.put("largueFile", sys.getLargeFile());
        map.put("freeMem", sys.getFreeMem());
        map.put("idleCPU", String.valueOf(sys.getIdleCPU()));
        map.put("svrCnt", sys.getSvrCnt());
        map.put("queCnt", sys.getQueCnt());
        map.put("cltCnt", sys.getCltCnt());
        l.add(map);
        Page<Map<String, String>> page = new PageImpl<Map<String, String>>(l);
        Gridable<Map<String, String>> gridable = new Gridable<Map<String, String>>(page);
        String cellString = new String("coreFind,errorFind,warnFind,largueFile,freeMem,idleCPU,svrCnt,queCnt,cltCnt");
        gridable.setIdField("coreFind");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
    }


    @Get("queue/top/{serverName}")
    public void chartQue(@Param("serverName")String siteName,Invocation inv){

        TuxHisData hisData = monitorManage.getMonitorInf(siteName);
        int index = 0;
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(hisData.getProcessResult().getTop5Que().size());
        for (Iterator<TuxquesEntity> iter = hisData.getProcessResult().getTop5Que().iterator(); iter.hasNext();) {
            l.add(convertQueueGrid(iter.next(), index));
            index++;
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("sort,name,queued");
        gridable.setIdField("name");
        gridable.setCellStringField(cellString);
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
    }

    private Map<String, String> convertQueueGrid(TuxquesEntity que, int index) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("sort","<div class='"+SORT_CSS_CLASS[index]+"'></div>");
        map.put("name",que.getProgname());
        map.put("queued",String.valueOf(que.getQueued()));
        return map;
    }



    @Get("memory/top/{serverName}")
    public void chartMem(@Param("serverName")String serverName,Invocation inv){
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);
        int index = 0;
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(hisData.getProcessResult().getTop5Mem().size());
        for (Iterator<TuxsvrsEntity> iter = hisData.getProcessResult().getTop5Mem().iterator(); iter.hasNext();) {
            l.add(convertMemGrid(iter.next(), index));
            index++;
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("sort,pid,used");
        gridable.setIdField("pid");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
    }

    private Map<String, String> convertMemGrid(TuxsvrsEntity svr, int index) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("sort","<div class='"+SORT_CSS_CLASS[index]+"'></div>");
        map.put("pid",svr.getProcessid());
        map.put("used",String.valueOf(svr.getMemoryuse()));
        return map;
    }

    @Get("transcation/top/{serverName}")
    public void chartTrans(@Param("serverName")String siteName,Invocation inv){
        TuxHisData hisData = monitorManage.getMonitorInf(siteName);

        int index = 0;
        List<Map<String,String>> l = new ArrayList<Map<String, String>>(hisData.getProcessResult().getTop5Tran().size());
        for (Iterator<TuxsvrsEntity> iter = hisData.getProcessResult().getTop5Tran().iterator(); iter.hasNext();) {
            l.add(convertTransGrid(iter.next(), index));
            index++;
        }
        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String("sort,rqdon,progname,pid");
        gridable.setIdField("pid");
        gridable.setCellStringField(cellString);
        UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
    }

    private Map<String, String> convertTransGrid(TuxsvrsEntity svr, int index) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("sort","<div class='"+SORT_CSS_CLASS[index]+"'></div>");
        map.put("rqdon",String.valueOf(svr.getRqdone()));
        map.put("progname",String.valueOf(svr.getProgname()));
        map.put("pid",String.valueOf(svr.getProcessid()));
        return map;
    }

    @Get("/chart/client/{serverName}/{operation}/{type}")
    public Reply chartClt(@Param("serverName")String serverName,@Param("operation")String operation,@Param("type")String type) {
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);
        JSONArray jsonArray = new JSONArray();
        if(operation.equals("latest")){
            TuxcltsStatsEntity tuxcltsStats = hisData.getProcessResult().getTuxcltsStats();
            jsonArray.add(tuxcltsStats.getRectime().getTime());
            if(type.equals("all"))
                jsonArray.add(Integer.parseInt(tuxcltsStats.getTotalcount()));
            else
                jsonArray.add(Integer.parseInt(tuxcltsStats.getBusycount()));
        }else{
            for (Iterator<TuxcltsStatsEntity> iterator = hisData.getProcessResult().getCltsStatsQue(); iterator.hasNext();) {
                TuxcltsStatsEntity tuxcltsStats = iterator.next();

                Map<String,Object> result = new HashMap<String,Object>();
                result.put("x",tuxcltsStats.getRectime().getTime());
                if(type.equals("all"))
                    result.put("y",Integer.parseInt(tuxcltsStats.getTotalcount()));
                else
                    result.put("y",Integer.parseInt(tuxcltsStats.getBusycount()));
                jsonArray.add(result);
            }
        }

        String jsonBack = jsonArray.toString();
        logger.debug("json data:{}",jsonBack);

        return Replys.with(jsonBack).as(Json.class);
    }

    @Get("/chart/{type}/{serverName}/{operation}")
    public Reply chartFree(@Param("type")String type,@Param("serverName")String serverName,
                           @Param("operation")String operation) {
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);

        JSONArray jsonArray = new JSONArray();
        if(type.equals("cpu")||type.equals("memory")){
           if(operation.equals("latest")){
               jsonArray = chartCpuTheLatest(hisData,type);
           }else{
               for (Iterator<TuxresourceEntity> iterator = hisData.getProcessResult().getResQue(); iterator.hasNext();) {
                   TuxresourceEntity resource =  iterator.next();
                   Map<String,Object> result = new HashMap<String,Object>();
                   result.put("x",resource.getRectime().getTime());
                   if(type.equals("cpu"))
                       result.put("y",100-resource.getCpuidle());
                   else
                       result.put("y",resource.getMemfree());
                   jsonArray.add(result);
               }
           }
        }
        else if(type.equals("thoughtPut")){
            jsonArray = this.chartThought(hisData,operation);
        }


        String jsonBack = jsonArray.toString();
        logger.debug("json data:{}",jsonBack);

        return Replys.with(jsonBack).as(Json.class);
    }

    private JSONArray chartCpuTheLatest(TuxHisData tuxHisData,String type){
        JSONArray jsonArray = new JSONArray();
        for (Iterator<TuxresourceEntity> iterator = tuxHisData.getProcessResult().getResQue(); iterator.hasNext();) {
            TuxresourceEntity resource =  iterator.next();
            if(iterator.hasNext()){
               continue;
           }
           else{
               jsonArray.add(resource.getRectime().getTime());
               if(type.equals("cpu"))
                   jsonArray.add(100-resource.getCpuidle());
               else
                   jsonArray.add(resource.getMemfree());
           }
        }
        return jsonArray;
    }



    /**
     * 吞吐量
     * @param hisData
     * @return
     */
    private JSONArray chartThought(TuxHisData hisData,String operation) {
        JSONArray jsonArray = new JSONArray();
        if (operation.equals("latest")) {
            TuxsvrStatsEntity svrStats = hisData.getProcessResult().getTuxSvrStats();
            jsonArray.add(svrStats.getRectime().getTime());
            jsonArray.add(svrStats.getTpsdone());
        } else {
            for (Iterator<TuxsvrStatsEntity> iterator = hisData.getProcessResult().getSvrStatsQue(); iterator.hasNext(); ) {
                TuxsvrStatsEntity svrStats = iterator.next();
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("x", svrStats.getRectime().getTime());
                result.put("y", svrStats.getTpsdone());
                jsonArray.add(result);
            }
        }
        return jsonArray;
    }

    /**
     * 支持使用Jquery.validate Ajax检验站点名称是否重复.
     */
    @Get("check/{siteName}")
    public Reply checkSite(@Param("siteName")String siteName,Invocation inv) {
        logger.debug("sitename = {}", siteName);
        String result = String.valueOf(systemService.checkSiteExists(siteName));
        logger.debug("siteName validate is {}", result);
        return Replys.with(result).as(Text.class);
    }



    @Post("setting/save")
    public Reply saveSetting(SiteSettings settings){
        systemService.configSiteSetting(settings);
        return Replys.with("success").as(Text.class);
    }

    @Get("setting/{serverName}")
    public String getSetting(@Param("serverName")String serverName,Invocation invocation){
        invocation.addModel(systemService.getSiteSetting(serverName).getDataSave());
        invocation.addModel("serverName",serverName);
        return "tuxedoSetting";
    }

}
