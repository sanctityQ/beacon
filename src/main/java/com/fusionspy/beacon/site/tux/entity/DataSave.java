package com.fusionspy.beacon.site.tux.entity;

import javax.xml.bind.annotation.XmlElement;

/**
 * siteSetting dataSave config
 * User: qc
 * Date: 11-9-7
 * Time: 下午9:18
 */
public class DataSave {

    public static final DataSave DEFAULT = new DataSave();

    static {
         DEFAULT.setSaveAllClient(SaveFlag.DISABLE);
         DEFAULT.setSaveQueueData(Queue.ALL);
         DEFAULT.setSaveServerData(Server.ALL);
         DEFAULT.setSaveAll(SaveFlag.DISABLE);
    }



    public enum SaveFlag{ENABLE,DISABLE;}

    public enum Server{
        ALL,MEMORY,RQDONE,CPU;
    }

    public enum Queue{
        ALL,TOP10
    }

    //default
    private SaveFlag saveFlag = SaveFlag.DISABLE;

    //default
    private SaveFlag saveAllClient = SaveFlag.DISABLE;
    //default
    private Server saveServerData = Server.ALL;
    //default
    private Queue saveQueueData = Queue.ALL;

    @XmlElement(name="SaveAll")
    public SaveFlag getSaveAll(){
        return this.saveFlag;
    }

    public void setSaveAll(SaveFlag saveFlag) {
        this.saveFlag = saveFlag;
    }

    @XmlElement(name="SaveAllClient")
    public SaveFlag getSaveAllClient() {
        return saveAllClient;
    }

    public void setSaveAllClient(SaveFlag saveAllClient) {
       this.saveAllClient = saveAllClient;
    }

    @XmlElement(name="SaveServerData")
    public Server getSaveServerData() {
        return saveServerData;
    }

    public void setSaveServerData(Server saveServerData) {
        this.saveServerData = saveServerData;
    }

    public Queue getSaveQueueData() {
        return saveQueueData;
    }

    @XmlElement(name="SaveQueueData")
    public void setSaveQueueData(Queue saveQueueData) {
        this.saveQueueData = saveQueueData;
    }

}
