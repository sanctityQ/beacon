package com.fusionspy.beacon.system.service;

import com.fusionspy.beacon.system.entity.SiteListEntity;
import com.fusionspy.beacon.system.entity.SiteSettings;
import com.fusionspy.beacon.system.entity.SysrecsEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * system service obj
 * User: qc
 * Date: 11-8-5
 * Time: 下午1:52
 */
@Transactional
public interface SystemService {


    void addSite(SiteListEntity siteListEntity);

    void delSite(String siteName);

    SiteListEntity getSite(String siteName);

    boolean checkSiteExists(String siteName);

    List<SiteListEntity> getSites();

    void configSiteSetting(SiteSettings siteSettings);

    SiteSettings getSiteSetting(String siteName);

    /**
     * save Sysrecs
     * @param sysrecsEntity
     * @return siteMonitor count
     */
    int saveSysRec(SysrecsEntity sysrecsEntity);
}
