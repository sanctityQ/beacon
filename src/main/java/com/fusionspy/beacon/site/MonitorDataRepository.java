package com.fusionspy.beacon.site;

/**
 * monitor data repository
 * User: qc
 * Date: 12-3-10
 * Time: 下午12:59
 */
public interface MonitorDataRepository {

    /**
     * when monitor start get init data
     * @return
     */
    <T> InitData getInitData(String siteName, String ip, int port);

    /**
     * every interval get server side in-time data
     * @param siteName
     * @return
     */
    InTimeData getInTimeData(String siteName);


    void stopSite(String siteName);
}