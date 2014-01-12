package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.*;
import com.fusionspy.beacon.site.tux.dao.SysrecsDao;
import com.fusionspy.beacon.site.tux.entity.TuxInTimeData;
import com.fusionspy.beacon.site.tux.entity.TuxIniData;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.fusionspy.beacon.site.tux.entity.SysrecsEntity;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * tux monitor data repository
 * User: qc
 * Date: 12-3-10
 * Time: 下午1:34
 */
@Component(value="tuxDataConnectRepo")
public class TuxDataRepository implements MonitorDataRepository {

    private JaxbBinder inTimeBinder = new JaxbBinder(TuxInTimeData.class, TuxsvrsEntity.class);

    private JaxbBinder iniBinder = new JaxbBinder(SysrecsEntity.class, TuxIniData.class, TuxsvrsEntity.class);

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Connect connect;

    @Autowired
    private SysrecsDao sysrecsDao;

    @Override
    public TuxIniData getInitData(MonitorSite monitorSite) {
        String initXml = connect.startSiteThread( monitorSite.getSiteName(), monitorSite.getSiteIp(),
                monitorSite.getSitePort(), StringUtils.EMPTY);
        //logger.debug("siteName is {}, ip is {}, get initXml Xml: {}",new Object[]{ siteName,ip,initXml});
        TuxIniData tuxIniData =  iniBinder.fromXml(initXml);
        tuxIniData.setSiteName(monitorSite.getSiteName());
        tuxIniData.setSysrecsDao(sysrecsDao);
        return tuxIniData;
    }

    @Override
    public InTimeData getInTimeData(String siteName) {
        String inTimeXml = connect.getInTimeData(siteName);
       // logger.debug("get inTime Xml: {}", inTimeXml);
        return inTimeBinder.fromXml(inTimeXml);
    }

    @Override
    public void stopSite(String siteName) {
        connect.stopSiteThreadByName(siteName);
    }
}
