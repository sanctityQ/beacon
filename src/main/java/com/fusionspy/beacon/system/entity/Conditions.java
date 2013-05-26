package com.fusionspy.beacon.system.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * siteConditions
 * User: qc
 * Date: 11-9-3
 * Time: 上午10:47
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Conditions {

    final static Conditions DEFAULT = new Conditions();

    static{
           DEFAULT.setOsCpu(OSCpu.DEFAULT);
           DEFAULT.setProcessMemory(ProcessMemory.DEFAULT);
           DEFAULT.setQueued(Queued.DEFAULT);
           DEFAULT.setServerBusy(AlertAndName.DEFAULT);
           DEFAULT.setServerDied(AlertAndName.DEFAULT);
           DEFAULT.setServerNoTrans(AlertAndName.DEFAULT);
           Alert al = new Alert();
           al.setAlert(Alert.DISABLE);
           DEFAULT.setSystemStop(al);
    }

    @XmlElement(name="ServerDied")
    public AlertAndName getServerDied() {
        return serverDied;
    }

    public void setServerDied(AlertAndName serverDied) {
        this.serverDied = serverDied;
    }

    @XmlElement(name="ServerNoTrans")
    public AlertAndName getServerNoTrans() {
        return serverNoTrans;
    }

    public void setServerNoTrans(AlertAndName serverNoTrans) {
        this.serverNoTrans = serverNoTrans;
    }

    @XmlElement(name="ServerBusy")
    public AlertAndName getServerBusy() {
        return serverBusy;
    }

    public void setServerBusy(AlertAndName serverBusy) {
        this.serverBusy = serverBusy;
    }

    @XmlElement(name="Queued")
    public Queued getQueued() {
        return queued;
    }

    public void setQueued(Queued queued) {
        this.queued = queued;
    }

    @XmlElement(name="ProcessMemory")
    public ProcessMemory getProcessMemory() {
        return processMemory;
    }

    public void setProcessMemory(ProcessMemory processMemory) {
        this.processMemory = processMemory;
    }

    @XmlElement(name="OSCPU")
    public OSCpu getOsCpu() {
        return osCpu;
    }

    public void setOsCpu(OSCpu osCpu) {
        this.osCpu = osCpu;
    }

    @XmlElement(name="SystemStop")
    public Alert getSystemStop() {
        return systemStop;
    }

    public void setSystemStop(Alert systemStop) {
        this.systemStop = systemStop;
    }


    private AlertAndName serverDied;

    private AlertAndName serverNoTrans;

    private AlertAndName serverBusy;


    private Queued queued;


    private ProcessMemory processMemory;


    private OSCpu osCpu;


    private Alert systemStop;


    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class Queued extends Alert{

        static final Queued DEFAULT = new Queued();

        static{
           DEFAULT.setQueueNumber(0);
           DEFAULT.setAlert(Alert.DISABLE);
        }

        @XmlAttribute(name="QueuedNumber")
        public int getQueueNumber() {
            return queueNumber;
        }

        public void setQueueNumber(int queueNumber) {
            this.queueNumber = queueNumber;
        }

        private int queueNumber;
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class ProcessMemory extends Alert{

       static final ProcessMemory DEFAULT = new ProcessMemory();

        static{
           DEFAULT.setUsedMemory(0);
           DEFAULT.setAlert(Alert.DISABLE);
        }

        @XmlAttribute(name="UsedMemory")
        public int getUsedMemory() {
            return usedMemory;
        }

        public void setUsedMemory(int usedMemory) {
            this.usedMemory = usedMemory;
        }
        private int usedMemory;
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class OSCpu extends Alert{

        static final OSCpu DEFAULT = new OSCpu();

         static {
            DEFAULT.setAlert(Alert.DISABLE);
            DEFAULT.setUsed(0);
         }

        @XmlAttribute(name="Used")
        public float getUsed() {
            return used;
        }

        public void setUsed(float used) {
            this.used = used;
        }

        private float used;
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class Percentage extends Alert{

        private String percentage;

        @XmlAttribute(name="Percentage")
        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }


}
