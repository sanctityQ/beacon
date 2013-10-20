package com.fusionspy.beacon.web.appServer;

import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.site.wls.WlsHisData;
import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.entity.WlsInTimeData;
import com.fusionspy.beacon.site.wls.entity.WlsIniData;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.web.BeaconLocale;
import com.fusionspy.beacon.web.JsonGrid;
import com.sinosoft.one.monitor.utils.MessageUtils;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.DefValue;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Json;
import com.sinosoft.one.util.encode.JsonBinder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * tuxedo action
 * User: qc
 * Date: 11-8-30
 * Time: 下午8:26
 */
@Path("/weblogic")
public class WlsController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    JsonBinder jsonBinder =  JsonBinder.buildNonNullBinder();

    private Map<String, Object> message = new HashMap<String, Object>();

    @Resource
    private MonitorManage monitorManage;

    @Resource
    private WlsService wlsService;

    @Post("save")
    public String save(WlsServer wlsServer) {
        wlsServer.setStatus(1);
        wlsService.save(wlsServer);
        return "/appServer/weblogic/start/"+wlsServer.getServerName();
    }

    @Get("listUI")
    public String listUI(Invocation inv){

        return "weblogicList";
    }

    @Post("list")
    public Reply list(Invocation inv) {
        List<WlsServer> wlsServerList = wlsService.list();
        final String viewUrl = inv.getServletContext().getContextPath()+"/appServer/weblogic/view/";
        final String eidUrl = inv.getServletContext().getContextPath()+"/appServer/weblogic/editUI/";
        JsonGrid grid = JsonGrid.buildGrid(wlsServerList, new JsonGrid.JsonRowHandler<WlsServer>() {
            @Override
            public JsonGrid.JsonRow buildRow(WlsServer wlsServer) {
                JsonGrid.JsonRow row = new JsonGrid.JsonRow();
                row.addCell(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, viewUrl + wlsServer.getServerName(), wlsServer.getServerName()));
                row.addCell(wlsServer.getListenAddress());
                row.addCell(wlsServer.getListenPort()+"");
                row.addCell(wlsServer.getInterval()+"");
                row.addCell(MessageUtils.formateMessage(MessageUtils.HANDLER_FORMAT, eidUrl+wlsServer.getServerName()));
                return row;
            }
        });
        return Replys.with(grid).as(Json.class);
    }

    @Get("view/{serverName}")
    public String view(@Param("serverName")String serverName,Invocation invocation) {
        WlsHisData hisData = monitorManage.getMonitorInf(serverName);
        WlsIniData iniData = hisData.getWlsIniData();
        WlsInTimeData inTimeData = hisData.getWlsInTimeData();
        invocation.addModel("serverName",serverName);
        invocation.addModel("serverType","weblogic");
        invocation.addModel("wlsVersion", iniData.getWlsSysrec().getDomainVersion());
        invocation.addModel("rectime", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(iniData.getWlsSysrec().getRecTime())));
        invocation.addModel("osVersion", iniData.getWlsSysrec().getOsVersion());
        invocation.addModel("cpuIdle", inTimeData.getResource().getCpuIdle());
        invocation.addModel("memFree", inTimeData.getResource().getMemFree());
        invocation.addModel("agentVer", iniData.getWlsSysrec().getAgentVersion());
        invocation.addModel("systemboot", iniData.getWlsSysrec().getSystemBoot());
        invocation.addModel("count", hisData.getMonitorCount());
        invocation.addModel("ip", inTimeData.getServerRuntimes().get(0).getListenAddress());
        invocation.addModel("port", inTimeData.getServerRuntimes().get(0).getListenPort());
        return "weblogicInfo";
    }

    @Get("editUI/{serverName}")
    public String editUI(@Param("serverName")String serverName,Invocation invocation) {
        //TODO 修改为从MonitorManage获取
        WlsServer wlsServer = wlsService.getSite(serverName);
        invocation.addModel("server", wlsServer);
        return "/views/addWeblogic";
    }

    /**
     * 删除操作(包含删除一个和批量删除操作)
     * @param serverNames
     * @param inv
     * @return
     */
    @Get("remove")
    public Reply remove(@Param("serverNames")List<String> serverNames, Invocation inv) {
        //TODO 确认都要做哪些操作
        message.put("result", true);
        return Replys.with(message).as(Json.class);
    }

    @Post("start/{serverName}")
    public Reply startMonitor(@Param("serverName")String serverName, @DefValue("zh_CN")Locale locale){
        BeaconLocale.setLocale(locale);
        monitorManage.monitor(serverName);
        logger.debug("start server name is {} ", serverName);
        message.put("type", "success");
        return Replys.with(message).as(Json.class);
    }

}
