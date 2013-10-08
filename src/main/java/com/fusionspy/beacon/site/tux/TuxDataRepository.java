package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.Connect;
import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorDataRepository;
import com.fusionspy.beacon.site.tux.entity.TuxInTimeData;
import com.fusionspy.beacon.site.tux.entity.TuxIniData;
import com.fusionspy.beacon.site.tux.entity.TuxsvrsEntity;
import com.fusionspy.beacon.site.tux.entity.SysrecsEntity;
import com.sinosoft.one.util.encode.JaxbBinder;
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

    @Override
    public InitData getInitData(String siteName, String ip, int port) {
        String initXml = connect.startSiteThread("", siteName, ip, port, 0);
        logger.debug("get initXml Xml: {}", initXml);
        return iniBinder.fromXml(initXml);
    }

    @Override
    public InTimeData getInTimeData(String siteName) {
        String inTimeXml = connect.getInTimeData(siteName);
        logger.debug("get inTime Xml: {}", inTimeXml);
        return inTimeBinder.fromXml(inTimeXml);
    }

    @Override
    public void stopSite(String siteName) {
        connect.stopSiteThreadByName(siteName);
    }
}
