package com.fusionspy.beacon.site.wls;

import com.fusionspy.beacon.site.wls.dao.*;
import com.fusionspy.beacon.site.wls.entity.*;
import com.sinosoft.one.monitor.common.ResourceType;
import com.sinosoft.one.monitor.resources.domain.ResourcesService;
import com.sinosoft.one.monitor.resources.model.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-20
 * Time: 下午11:39
 * To change this template use File | Settings | File Templates.
 */
@Service
public class WlsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private WlsSysrecDao wlsSysrecDao;
    @Autowired
    private WlsEjbCacheDao wlsEjbCacheDao;
    @Autowired
    private WlsEjbPoolDao wlsEjbPoolDao;
    @Autowired
    private WlsJdbcDao wlsJdbcDao;
    @Autowired
    private WlsJmsDao wlsJmsDao;
    @Autowired
    private WlsJvmDao wlsJvmDao;
    @Autowired
    private WlsResourceDao wlsResourceDao;
    @Autowired
    private WlsSvrDao wlsSvrDao;
    @Autowired
    private WlsThreadDao wlsThreadDao;
    @Autowired
    private WlsWebappDao wlsWebappDao;
    @Autowired
    private WlsServerDao wlsServerDao;

    /**
     * process init data
     * @param wlsIniData
     * @return
     */
    @Transactional
    public int processInitData(WlsIniData wlsIniData) {
        wlsIniData.defaultData();
        WlsSysrec wlsSysrec = wlsIniData.getWlsSysrec();
        wlsSysrec.setRecTime(new Date());
        wlsSysrecDao.save(wlsSysrec);
        return 1;
    }


    /**
     * process intime data
     * @param siteName
     * @param period
     * @param inTimeData
     * @param hisData
     */
    @Transactional
    public void processInTimeData(String siteName,int period,WlsInTimeData inTimeData,WlsHisData hisData) {
        inTimeData.defaultData();  //赋默认值
        WlsSysrec sysrec = hisData.getWlsIniData().getWlsSysrec();

        List<WlsSvr> serverRuntimes = inTimeData.getServerRuntimes();
        wlsSvrDao.save(serverRuntimes);
        List<WlsJvm> jvmRuntimes = inTimeData.getJvmRuntimes();
        wlsJvmDao.save(jvmRuntimes);
        List<WlsThread> threadPoolRuntimes = inTimeData.getThreadPoolRuntimes();
        wlsThreadDao.save(threadPoolRuntimes);
        List<WlsJdbc> jdbcDataSourceRuntimes = inTimeData.getJdbcDataSourceRuntimes();
        wlsJdbcDao.save(jdbcDataSourceRuntimes);
        List<WlsWebapp> componentRuntimes = inTimeData.getComponentRuntimes();
        for(WlsWebapp webapp : componentRuntimes) {
            webapp.setOpenSessionsHigh(parsetInt(webapp.getOpenSessionsHighCount()));
            webapp.setOpenSessionsCurrent(parsetInt(webapp.getOpenSessionsCurrentCount()));
            webapp.setSessionsOpenedTotal(parsetInt(webapp.getSessionsOpenedTotalCount()));
        }
        wlsWebappDao.save(componentRuntimes);
        List<WlsJms> jmsServers = inTimeData.getJmsServers();
        wlsJmsDao.save(jmsServers);
        List<WlsEjbpool> poolRuntimes = inTimeData.getPoolRuntimes();
        wlsEjbPoolDao.save(poolRuntimes);
        List<WlsEjbcache> cacheRuntime = inTimeData.getCacheRuntime();
        wlsEjbCacheDao.save(cacheRuntime);
        WlsError error = inTimeData.getError();
        //汇总信息处理
        WlsResource resource = inTimeData.getResource();
        String mem = resource.getMem();
        resource.setRunServerNumber(jvmRuntimes.size()); //服务器数量
        resource.setServerNumber(sysrec.getServerNum()); //运行服务器数量
        if(StringUtils.isNotBlank(resource.getCpu())) { //CPU空闲
            resource.setCpuIdle(Integer.parseInt(resource.getCpu().trim()));
        } else {
            resource.setCpuIdle(0);
        }
        resource.setOsType(sysrec.getOsType());
        if(StringUtils.isNotBlank(mem)) { //获取当前'M'之前的数值
            resource.setMemFree(mem.substring(0, mem.indexOf('M')));
        } else {
            resource.setMemFree("0");
        }
        hisData.setWlsInTimeData(inTimeData);
        hisData.addWlsIntimeData(siteName, inTimeData);
        wlsResourceDao.save(resource);

    }

    private Integer parsetInt(String str) {
        try{
           return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    @Transactional
    public void save(WlsServer wlsServer) {
        wlsServer.setRecTime(new Date());
        wlsServerDao.save(wlsServer);
        Resource resource = new Resource();
        resource.setResourceId(wlsServer.getServerName());
        resource.setResourceName(wlsServer.getServerName());
        resource.setResourceType(ResourceType.Weblogic.toString());
        resourcesService.saveResource(resource);

    }

    @Transactional
    public void delete(String serverName) {
        wlsServerDao.delete(serverName);
    }

    @Transactional(readOnly = true)
    public WlsServer getSite(String serverName) {
        return wlsServerDao.findOne(serverName);
    }

    public List<WlsServer> getSites() {
        return (List<WlsServer>) wlsServerDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<WlsServer> list() {
        return (List<WlsServer>) wlsServerDao.findAll();
    }
}
