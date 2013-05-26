package com.fusionspy.beacon.web;

import com.fusionspy.beacon.system.entity.SiteSettings;
import com.sinosoft.one.mvc.web.annotation.Path;
import com.sinosoft.one.mvc.web.annotation.rest.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * siteSetting action
 * User: qc
 * Date: 11-9-5
 * Time: 下午11:43
 */
@Path("/appServer")
public class SiteSettingController {

    private static Logger logger = LoggerFactory.getLogger(SiteSettingController.class);


//    @Post("add")
//    public String save(SiteSettings siteSettings) throws Exception {
//        logger.debug("siteSettings {}",siteSettings.getConditions().getServerDied().getAlert());
//        return "SUCESS";
//    }


}
