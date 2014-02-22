package com.fusionspy.beacon.system.service;

import com.fusionspy.beacon.site.tux.dao.SiteListDao;
import com.fusionspy.beacon.site.tux.dao.SitesSettingsDao;
import com.fusionspy.beacon.site.tux.dao.SysrecsDao;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.common.ResourceType;
import com.fusionspy.beacon.resources.model.Resource;
import com.fusionspy.beacon.resources.repository.ResourcesRepository;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * User: qc
 * Date: 11-8-14
 * Time: 下午10:45
 */
@Component(value="systemService")
public class SystemServiceImpl implements SystemService{

    private Logger logger = LoggerFactory.getLogger(SystemService.class);

    private static final String UTF8_ENCODE = "utf-8";

    private JaxbBinder jaxbBinder = new JaxbBinder(SiteSettings.class,Conditions.class,Alert.class,
            Conditions.OSCpu.class,Conditions.ProcessMemory.class,Conditions.Queued.class,AlertAndName.class,Conditions.Percentage.class);

    private static final String CACHE = "systemInfoCache";

//    @Autowired
//	private CacheManager ehcacheManager;

    @Autowired
    private SiteListDao siteListDao;

    @Autowired
    private SitesSettingsDao sitesSettingsDao;

    @Autowired
    private SysrecsDao sysrecsDao;

    @Autowired
    private ResourcesRepository resourcesRepository;


    @Override
    public void addSite(SiteListEntity siteListEntity) {
        siteListDao.save(siteListEntity);

        Resource resource = resourcesRepository.findOne(siteListEntity.getSiteName());
        if (resource == null) {
            // resource
            resource = new Resource();
            resource.setResourceId(siteListEntity.getSiteName());
            resource.setResourceName(siteListEntity.getSiteName());
            resource.setResourceType(ResourceType.Tuxedo);
            resourcesRepository.save(resource);
        }

        //增加缺省值
        SiteSettings siteSettings = getSiteSetting(siteListEntity.getSiteName());
        if (siteSettings == null) {
            siteSettings = SiteSettings.DEFAULT;
            siteSettings.setSiteName(siteListEntity.getSiteName());
            configSiteSetting(siteSettings);
        }
        //siteSettings.setSiteType(siteListEntity.getSiteType());
    }

    @Override
    @Transactional(readOnly = false)
    public void delSite(String siteName) {
         siteListDao.delete(siteName);
         sitesSettingsDao.delete(siteName);
         sysrecsDao.deleteBySiteName(siteName);
    }

    @Override
    public void configSiteSetting(SiteSettings siteSettings) {

       SiteSettingsEntity siteSettingsEntity =  sitesSettingsDao.findOne(siteSettings.getSiteName());
       logger.debug("siteSetting = {}",siteSettingsEntity);
       if(siteSettingsEntity == null){
           siteSettingsEntity = new SiteSettingsEntity();
           siteSettingsEntity.setSiteName(siteSettings.getSiteName());
       }
       siteSettingsEntity.setSiteType(siteSettings.getSiteType());
       siteSettingsEntity.setSiteSetting(jaxbBinder.toXml(siteSettings,UTF8_ENCODE));
       sitesSettingsDao.save(siteSettingsEntity);


//       Cache cache = ehcacheManager.getCache(CACHE);
//       cache.put(new Element(siteSettings.getSiteName(),siteSettings));
    }


//    private void cacheSiteSetting(){
//        Cache cache = ehcacheManager.getCache(CACHE);
//    }

    @Override
    public SiteSettings getSiteSetting(String siteName) {
        Assert.hasText(siteName);
        //TODO 由于阈值与站点设置是两回事情，所以暂时去除从缓存中读取
//        Cache cache = ehcacheManager.getCache(CACHE);
//        Element element = cache.get(siteName);
//        if (element != null) {
//            return (SiteSettings)element.getObjectValue();
//        }


        //todo 将此调整为读取站点数据设置信息
        SiteSettingsEntity siteSettingsEntity = sitesSettingsDao.findOne(siteName);
        if(siteSettingsEntity == null)
            return null;

        SiteSettings siteSettings = jaxbBinder.fromXml(siteSettingsEntity.getSiteSetting());
        siteSettings.setSiteName(siteName);

//        Resource resource = resourcesCache.getResource(siteName);
//        //获取属性
//        Attribute attribute = attributeCache.getAttribute(resource.getResourceType(), AttributeName.ServerDied.name());
//
//        AlertAndName alertAndName = new AlertAndName();
//
//        if(attribute == Attribute.EMPTY)
//            alertAndName.setAlert("0");
//        else
//            alertAndName.setAlert("1");
//
//        //获取阈值
//        Threshold threshold = thresholdService.queryThreshold(siteName, attribute.getId());
//        alertAndName.setName(threshold.getCriticalThresholdCondition());
//
//        siteSettings.getConditions().setServerDied();


//        siteSettings.setSiteType(siteSettingsEntity.getSiteType());
//        element = new Element(siteName,siteSettings);
//        cache.put(element);
        return siteSettings;
    }

    @Override
    public boolean checkSiteExists(String siteName) {
        Assert.hasText(siteName);
        logger.debug("siteName is {}" ,siteName);
        SiteListEntity siteListEntity = siteListDao.findOne(siteName);
        logger.debug("entry is {}",siteListEntity);
        return siteListEntity==null?true:false;
    }

    @Override
    public List<SiteListEntity> getSites() {
        return (List<SiteListEntity>)siteListDao.findAll();
    }

    public int saveSysRec(SysrecsEntity sysrecsEntity){
       sysrecsDao.save(sysrecsEntity);
       return sysrecsDao.findBySiteName(sysrecsEntity.getSiteName()).size();
    }

    @Override
    public SiteListEntity getSite(String siteName) {
        Assert.hasText(siteName);
        return siteListDao.findOne(siteName);
    }


}
