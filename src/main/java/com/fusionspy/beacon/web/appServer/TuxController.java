package com.fusionspy.beacon.web.appServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.site.tux.TuxHisData;
import com.fusionspy.beacon.site.tux.TuxSite;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.system.entity.DataSave;
import com.fusionspy.beacon.system.entity.SiteListEntity;
import com.fusionspy.beacon.system.entity.SiteSettings;
import com.fusionspy.beacon.system.service.SystemService;
import com.fusionspy.beacon.web.BeaconLocale;
import com.fusionspy.beacon.web.Chart;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.DefValue;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Text;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Xml;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import com.sinosoft.one.util.encode.JsonBinder;
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

    @Post("start/{serverName}")
    public Reply startMonitor(@Param("serverName")String serverName,@DefValue("zh_CN")Locale locale){
        BeaconLocale.setLocale(locale);
        monitorManage.monitor(serverName);
        logger.debug("start server name is {} ", serverName);
        return Replys.with("success").as(Text.class);
    }

    @Get("view/{serverName}")
    public String showMonitor(@Param("serverName")String serverName,Invocation invocation){
        TuxHisData hisData = monitorManage.getMonitorInf(serverName);
        invocation.addModel("serverName",serverName);
        invocation.addModel("serverType","tuxedo");
        invocation.addModel("tuxVersion",hisData.getTuxIniData().getSysrecsEntity().getProductver());
        invocation.addModel("systemboot",hisData.getTuxIniData().getSysrecsEntity().getSystemboot());
        invocation.addModel("rectime", hisData.getTuxIniData().getSysrecsEntity().getRectime());
        invocation.addModel("tuxRunSvr", hisData.getProcessResult().getTuxRes().getTuxrunsvr());
        invocation.addModel("tuxRunQueue", hisData.getProcessResult().getTuxRes().getTuxrunqueue());
        invocation.addModel("tuxRunClt", hisData.getProcessResult().getTuxRes().getTuxrunclt());
        invocation.addModel("osVersion", hisData.getTuxIniData().getSysrecsEntity().getOstype());
        invocation.addModel("cpuIdle", hisData.getProcessResult().getTuxRes().getCpuidle());
        invocation.addModel("memFree", hisData.getProcessResult().getTuxRes().getMemfree());
        invocation.addModel("agentVer", hisData.getTuxIniData().getSysrecsEntity().getAgentver());
        invocation.addModel("count", hisData.getMonitorCount());

        return "tuxedoInfo";
    }

     public String stopMonitor(String siteName){
        monitorManage.cancel(siteName);
        //return showMonitor("tuxedo");
         return null;
     }

    public Reply changePeriod(String siteName,int period){
        monitorManage.changePeriod(siteName, period);
        return Replys.with("success").as(Text.class);
    }

    public Reply switchSave(String siteName){
        String msg = monitorManage.switchSave(siteName)?"yes":"not";
        return Replys.with(msg).as(Text.class);
    }

    public Reply runState(String siteName) {
        boolean isRunning = monitorManage.siteRunning(siteName);
        return  Replys.with(isRunning?"1":"0").as(Text.class);
    }

    public Reply saveState(String siteName){
        boolean state = monitorManage.isSave(siteName);
        return  Replys.with(state?"1":"0").as(Text.class);
    }

    public String showData(){
        return "data";
    }
    
    
    public String showStatistics(){
        return "statistics";
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

//    public Reply getInTimeData(String siteName) {
//        TuxHisData hisData = monitorManage.getMonitorInf(siteName);
//        jsonBinder.setDateFormat(TIME_FORMAT);
//        TuxInTimeData tuxInTimeData = hisData.getTuxInTimeData();
//        String data = jsonBinder.toJson(tuxInTimeData);
//        return Replys.with(data).as(Json.class);
//    }

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
        try {
            UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
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
        try {
            UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
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
        try {
            UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
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
        try {
            UIUtil.with(gridable).as(UIType.Json).render(invocation.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
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
        map.put("queued",String.valueOf(que.getSvrcnt()));
        return map;
    }


    private String renderXml(Chart chart){
       return chart.getJaxbBinder().toXml(chart, "UTF-8");
    }

    private String addColumnSeries(int index, Chart chart){
         Chart.Value sv = new Chart.Value();
         String title =   TOP + String.valueOf(index);
         sv.setValue(title);
         chart.addSeries(sv);
         return title;
    }


    void checkColumnChart(Chart chart) {
        if (chart.getSeries().isEmpty()) {
            for (int i = 1; i < 6; i++) {
               addColumnSeries(i,chart);
            }
        }
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
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
    }

    private Map<String, String> convertMemGrid(TuxsvrsEntity svr, int index) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("sort","<div class='"+SORT_CSS_CLASS[index]+"'></div>");
        map.put("pid",svr.getProcessid());
        map.put("used",String.valueOf(svr.getMemoryuse()));
        return map;
    }

    private void addColumnValue(Chart.ColumnGraph graph,String value,String des){
        Chart.ColumnValue gv = new Chart.ColumnValue();
        gv.setValue(value);
        gv.setDescription(des);
        graph.addValue(gv);
    }

    private String newMemTranMessage(String serverName,String pid){
        return  serverName + "(pid:"+pid+")";
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
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new RuntimeException("json数据转换出错!", e);
        }
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
                       result.put("y",resource.getCpuidle());
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
                   jsonArray.add(resource.getCpuidle());
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


    public Reply logInf(String siteName){
        String log = jsonBinder.toJson(monitorManage.getLogger(siteName));
        return  Replys.with(log).as(Json.class);
    }

    private void addLineSeries(Chart.LineChart chart,Date time) {
        Chart.Value ser = new Chart.Value();
        ser.setValue(df.format(time));
        chart.addSeries(ser);
    }

    private void addLineValue(Chart.Graph graph,String value) {
        Chart.Value tv = new Chart.Value();
        tv.setValue(value);
        graph.addValue(tv);
    }

    private void lineChartCheck(Chart.LineChart chart ){
        if(chart.getSeries().isEmpty()){
          addLineSeries(chart,new Date());
        }
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
