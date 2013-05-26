package com.fusionspy.beacon.web.appServer;


import com.fusionspy.beacon.site.MonitorManage;
import com.fusionspy.beacon.system.entity.SiteListEntity;
import com.fusionspy.beacon.system.entity.SiteSettings;
import com.fusionspy.beacon.system.service.SystemService;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Delete;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import com.sinosoft.one.mvc.web.instruction.reply.Reply;
import com.sinosoft.one.mvc.web.instruction.reply.Replys;
import com.sinosoft.one.mvc.web.instruction.reply.transport.Text;
import com.sinosoft.one.uiutil.Gridable;
import com.sinosoft.one.uiutil.UIType;
import com.sinosoft.one.uiutil.UIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;

/**
 * appServer
 * User: qc
 * Date: 11-8-3
 * Time: 下午2:42
 */
@Path
public class AppServerController{

    private Logger logger = LoggerFactory.getLogger(AppServerController.class);

    @Autowired
    private SystemService systemService;


    @Autowired
    private MonitorManage monitorManage ;

    @Get("list/{type}")
    public String list(@Param("type")String type){
        if(type.equals("tuxedo"))
            return "tuxedoList";
        else
            return null;
    }


    @Get("view/{type}/{serverName}")
    public String detail(@Param("type")String type,@Param("serverName")String name,Invocation invocation){
        SiteListEntity siteListEntity = systemService.getSite(name);
        invocation.addModel("server",siteListEntity);
        return "/addmonitor/server/"+type;
    }

    @Get("list/{type}/data")
    public void list(@Param("type")String type,Invocation inv) throws Exception {

        List<SiteListEntity> siteList = systemService.getSites();


        List<Map<String,String>> l = new ArrayList<Map<String, String>>(siteList.size());
        for (Iterator<SiteListEntity> iter = siteList.iterator(); iter.hasNext();) {
            l.add(convertGrid(iter.next(),inv.getRequest().getContextPath(),type));
        }

        Page<Map<String,String>> page = new PageImpl<Map<String,String>>(l);
        Gridable<Map<String,String>> gridable = new Gridable<Map<String,String>> (page);
        String cellString = new String(
                "siteName,siteIp,sitePort,interval,operation");
        gridable.setIdField("id");
        gridable.setCellStringField(cellString);
        try {
            UIUtil.with(gridable).as(UIType.Json).render(inv.getResponse());
        } catch (Exception e) {
            throw new Exception("json数据转换出错!", e);
        }
    }


    private Map<String,String> convertGrid(SiteListEntity siteListEntity, String contextPath, String type) {

        Map<String,String> map = new HashMap<String, String>();
        map.put("id",siteListEntity.getSiteName());
        String siteName = "<a href='"+contextPath+"/appServer/"+type+"/view/"+siteListEntity.getSiteName()
                            +"' target='_blank'>" +siteListEntity.getSiteName()+"</a>";
        map.put("siteName",siteName);
        map.put("siteIp",siteListEntity.getSiteIp());
        map.put("sitePort",String.valueOf(siteListEntity.getSitePort()));
        map.put("interval",String.valueOf(siteListEntity.getInterval()));
        map.put("operation","<a  href='javascript:void(0)' onclick='updRow(this)' class='eid'>编辑</a> " +
                "<a href='javascript:void(0)' class='eid' onclick='setTuxMergency()'>数据保存设置</a>"+
                "<a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>");
        return map;
    }

    @Post("save/{type}")
    public String save(@Param("type")String type,SiteListEntity appServer){

        if(type.equals("tuxedo")){
            appServer.setSiteType("1");
        }
        systemService.addSite(appServer);

        logger.debug("add server type is {} ",type);
        return "/appServer/"+type+"/start/"+appServer.getSiteName();
        //return Replys.with("success").as(Text.class);
    }

    @Delete("delete/{type}/{serverName}")
    public Reply delete(@Param("type")String type,@Param("serverName")String serverName) {
        systemService.delSite(serverName);
        logger.debug("delete server type is {} ,name is",type,serverName);
        return Replys.with("success").as(Text.class);
    }

    /**
     * 支持使用Jquery.validate Ajax检验站点名称是否重复.
     */
    @Get("check/{name}/{siteName}")
    public Reply checkSite(@Param("name")String name,@Param("siteName")String siteName,Invocation inv) {
        logger.debug("sitename = {}", siteName);
        String result = String.valueOf(systemService.checkSiteExists(siteName));
        logger.debug("siteName validate is {}", result);
        return Replys.with(result).as(Text.class);
    }





    private SiteSettings settings;

    public SiteSettings getSettings() {
        return settings;
    }

    public void setSettings(SiteSettings settings) {
        this.settings = settings;
    }

//    public void prepareInputSetting(){
//      logger.debug("prepareInputSetting settings.siteName = {}" ,settings.getSiteName());
//      SiteSettings settings =  systemService.getSiteSetting(this.settings.getSiteName());
//      settings.setSiteType(this.settings.getSiteType());
//      this.settings= settings;
//    }

    public String inputSetting(){
      return "tuxSetting";
    }

    @Post("setting/add")
    public Reply saveSetting(){
         systemService.configSiteSetting(settings);
         //Struts2Utils.renderText("success");
         return Replys.with("success").as(Text.class);
    }

}
