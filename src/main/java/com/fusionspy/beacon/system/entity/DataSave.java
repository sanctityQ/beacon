package com.fusionspy.beacon.system.entity;

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
         DEFAULT.setSaveAllClient(Client.DISABLE);
         DEFAULT.setSaveQueueData(Queue.ALL);
         DEFAULT.setSaveServerData(Server.ALL);
    }

    public enum Client{ENABLE,DISABLE;}

    public enum Server{
        ALL,MEMORY,RQDONE,CPU;
    }

    public enum Queue{
        ALL,TOP10
    }

    //default
    private Client saveAllClient = Client.DISABLE;
    //default
    private Server saveServerData = Server.ALL;
    //default
    private Queue saveQueueData = Queue.ALL;

    @XmlElement(name="SaveAllClient")
    public Client getSaveAllClient() {
        return saveAllClient;
    }

    public void setSaveAllClient(Client saveAllClient) {
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


    public static void main(String[] args){


       System.out.println(Client.valueOf("ENABLE")) ;
    }


}
