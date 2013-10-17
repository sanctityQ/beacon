package com.fusionspy.beacon.web.appServer;

<<<<<<< HEAD
<<<<<<< HEAD
import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.web.BeaconLocale;
import com.fusionspy.beacon.web.JsonGrid;
=======
import com.alibaba.fastjson.JSONArray;
=======
>>>>>>> a068eb6... none
import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.web.BeaconLocale;
<<<<<<< HEAD
import com.fusionspy.beacon.web.Chart;
import com.fusionspy.beacon.web.Grid;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
>>>>>>> f9e9463... wls
=======
import com.fusionspy.beacon.web.JsonGrid;
>>>>>>> a068eb6... none
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
<<<<<<< HEAD
<<<<<<< HEAD
import com.sinosoft.one.util.encode.JsonBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
=======
import com.sinosoft.one.mvc.web.instruction.reply.transport.Text;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
=======
>>>>>>> a068eb6... none
import com.sinosoft.one.util.encode.JsonBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
<<<<<<< HEAD
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
>>>>>>> f9e9463... wls
=======
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
>>>>>>> a068eb6... none

/**
 * tuxedo action
 * User: qc
 * Date: 11-8-30
 * Time: 下午8:26
 */
@Path("/weblogic")
public class WlsController {

    private Logger logger = LoggerFactory.getLogger(getClass());
<<<<<<< HEAD
<<<<<<< HEAD
    JsonBinder jsonBinder =  JsonBinder.buildNonNullBinder();
=======
>>>>>>> f9e9463... wls
=======
    JsonBinder jsonBinder =  JsonBinder.buildNonNullBinder();
>>>>>>> a068eb6... none

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
<<<<<<< HEAD
<<<<<<< HEAD
        final String viewUrl = inv.getServletContext().getContextPath()+"/appServer/weblogic/view/";
        final String eidUrl = inv.getServletContext().getContextPath()+"/appServer/weblogic/editUI/";
        JsonGrid grid = JsonGrid.buildGrid(wlsServerList, new JsonGrid.JsonRowHandler<WlsServer>() {
            @Override
            public JsonGrid.JsonRow buildRow(WlsServer wlsServer) {
                JsonGrid.JsonRow row = new JsonGrid.JsonRow();
                row.addCell(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, viewUrl + wlsServer.getServerName(), wlsServer.getServerName()));
                row.addCell(wlsServer.getListenAddress());
                row.addCell(wlsServer.getListenPort());
                row.addCell(wlsServer.getInterval()+"");
                row.addCell(MessageUtils.formateMessage(MessageUtils.HANDLER_FORMAT, eidUrl+wlsServer.getServerName()));
=======
        final String preUrl = inv.getServletContext().getContextPath()+"/appServer/tuxedo/view/";
        Grid grid = Grid.buildGrid(wlsServerList, new Grid.RowHandler<WlsServer>() {
            @Override
            public Grid.Row buildRow(WlsServer wlsServer) {
                Grid.Row row = new Grid.Row();
                String url = preUrl + wlsServer.getServerName();
                row.addCell(new Grid.Cell(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, url, wlsServer.getServerName())));
                row.addCell(new Grid.Cell(wlsServer.getListenAddress()));
                row.addCell(new Grid.Cell(wlsServer.getListenPort()));
                row.addCell(new Grid.Cell(wlsServer.getInterval()+""));
                row.addCell(new Grid.Cell("bbb"));
>>>>>>> f9e9463... wls
=======
        final String viewUrl = inv.getServletContext().getContextPath()+"/appServer/weblogic/view/";
        final String eidUrl = inv.getServletContext().getContextPath()+"/appServer/weblogic/editUI/";
        JsonGrid grid = JsonGrid.buildGrid(wlsServerList, new JsonGrid.JsonRowHandler<WlsServer>() {
            @Override
            public JsonGrid.JsonRow buildRow(WlsServer wlsServer) {
                JsonGrid.JsonRow row = new JsonGrid.JsonRow();
                row.addCell(MessageUtils.formateMessage(MessageUtils.MESSAGE_FORMAT_A, viewUrl + wlsServer.getServerName(), wlsServer.getServerName()));
                row.addCell(wlsServer.getListenAddress());
                row.addCell(wlsServer.getListenPort());
                row.addCell(wlsServer.getInterval()+"");
                row.addCell(MessageUtils.formateMessage(MessageUtils.HANDLER_FORMAT, eidUrl+wlsServer.getServerName()));
>>>>>>> a068eb6... none
                return row;
            }
        });
        return Replys.with(grid).as(Json.class);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a068eb6... none
    @Get("view/{serverName}")
    public String view(@Param("serverName")String serverName,Invocation invocation) {
        return null;
    }

    @Get("editUI/{serverName}")
    public String editUI(@Param("serverName")String serverName,Invocation invocation) {
        //TODO 修改为从MonitorManage获取
        WlsServer wlsServer = wlsService.getSite(serverName);
        invocation.addModel("server", wlsServer);
        return "/views/addWeblogic.jsp";
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

<<<<<<< HEAD
=======
>>>>>>> f9e9463... wls
=======
>>>>>>> a068eb6... none
    @Post("start/{serverName}")
    public Reply startMonitor(@Param("serverName")String serverName, @DefValue("zh_CN")Locale locale){
        BeaconLocale.setLocale(locale);
        //monitorManage.monitor(serverName);
        logger.debug("start server name is {} ", serverName);
        message.put("type", "success");
        return Replys.with(message).as(Json.class);
    }

}
