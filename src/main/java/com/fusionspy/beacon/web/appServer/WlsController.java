package com.fusionspy.beacon.web.appServer;

import com.alibaba.fastjson.JSONArray;
import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.site.tux.TuxHisData;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.site.wls.WlsService;
import com.fusionspy.beacon.site.wls.entity.WlsServer;
import com.fusionspy.beacon.system.entity.SiteListEntity;
import com.fusionspy.beacon.system.entity.SiteSettings;
import com.fusionspy.beacon.system.service.SystemService;
import com.fusionspy.beacon.web.BeaconLocale;
import com.fusionspy.beacon.web.Chart;
import com.fusionspy.beacon.web.Grid;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * tuxedo action
 * User: qc
 * Date: 11-8-30
 * Time: 下午8:26
 */
@Path("/weblogic")
public class WlsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

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
                return row;
            }
        });
        return Replys.with(grid).as(Json.class);
    }

    @Post("start/{serverName}")
    public Reply startMonitor(@Param("serverName")String serverName, @DefValue("zh_CN")Locale locale){
        BeaconLocale.setLocale(locale);
        //monitorManage.monitor(serverName);
        logger.debug("start server name is {} ", serverName);
        message.put("type", "success");
        return Replys.with(message).as(Json.class);
    }

}
