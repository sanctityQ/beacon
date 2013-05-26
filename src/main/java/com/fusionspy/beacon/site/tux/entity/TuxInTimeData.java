package com.fusionspy.beacon.site.tux.entity;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.MonitorData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * tux monidtor data
 * User: qc
 * Date: 11-9-8
 * Time: 下午6:18
 */
@XmlRootElement(name = "MONITOR")
public class TuxInTimeData extends MonitorData implements InTimeData {

    private List<TuxsvrsEntity> servers;

    public final static TuxInTimeData EMPTY;

    static {
       EMPTY = new TuxInTimeData();
       List list = new ArrayList();
       EMPTY.setClients(list);
       EMPTY.setQueues(list);
       EMPTY.setServers(list);
    }

    @XmlElement(name="SERVER")
    public List<TuxsvrsEntity> getServers() {
        return servers;
    }

    public void setServers(List<TuxsvrsEntity> servers) {
        this.servers = servers;
    }

    private List<TuxcltsEntity> clients;

    @XmlElement(name="CLIENT")
    public List<TuxcltsEntity> getClients() {
        return clients;
    }

    public void setClients(List<TuxcltsEntity> clients) {
        this.clients = clients;
    }

    private List<TuxquesEntity>  queues;

    @XmlElement(name="QUEUE")
    public List<TuxquesEntity> getQueues() {
        return queues;
    }

    public void setQueues(List<TuxquesEntity> queues) {
        this.queues = queues;
    }

    private TuxSysData tuxSysData;

    @XmlElement(name="SYSTEM")
    public TuxSysData getTuxSysData() {
        return tuxSysData;
    }

    public void setTuxSysData(TuxSysData tuxSysData) {
        this.tuxSysData = tuxSysData;
    }


    private TuxError tuxError;

    @XmlElement(name="ERROR")
    public TuxError getTuxError() {
        return tuxError;
    }

    public void setTuxError(TuxError tuxError) {
        this.tuxError = tuxError;
    }
}
