package com.fusionspy.beacon.controllers;

import com.fusionspy.beacon.resources.domain.ResourcesService;
import com.sinosoft.one.mvc.web.Invocation;
import com.sinosoft.one.mvc.web.annotation.Param;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Get;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


@Path("addmonitor")
public class AddMonitorController {

    @Autowired
    ResourcesService resourcesService;

    @Get("list")
    public String getAddMonitorList(Invocation inv){
        return "addMonitorList";
    }

    @Get("addapp")
    @Post("errorcreate")
    public String addApplication(Invocation inv){
        return "addSystem";
    }

    @Get("addoracle")
    @Post("errorcreate")
    public String addOracle(Invocation inv){
        return "addOracle";
    }

    @Get("addos")
    @Post("errorcreate")
    public String addOs(Invocation inv){
        return "addLinux";
    }

    @Get("server/{type}")
    public String addServer(@Param("type")String type,Invocation inv){
        String firstLetter = StringUtils.substring(type,0,1);
        type = firstLetter.toUpperCase() + StringUtils.substring(type,1);
        return "add"+type;
    }


}
