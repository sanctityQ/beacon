package com.fusionspy.beacon.site;

/**
 * monitor data repository
 * User: qc
 * Date: 12-3-10
 * Time: 下午12:59
 */
public interface MonitorDataRepository<E extends MonitorSite> {

    /**
     * when monitor start get init data
     * @return
     */
    <T  extends InitData> T getInitData(E e);

    /**
     * every interval get server side in-time data
     * @param siteName
     * @return
     */
    InTimeData getInTimeData(String siteName);


    void stopSite(String siteName);
}