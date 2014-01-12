package com.fusionspy.beacon.site;


class ConnectAgentException  extends  RuntimeException{

    private  int connectCode;

    ConnectAgentException(String message){
        super(message);
    }

    ConnectAgentException(String message,int connectCode){
        super(message);
        this.connectCode = connectCode;
    }


    ConnectAgentException(Throwable cause,int connectCode) {
        super(cause);
        this.connectCode = connectCode;
    }

    /**
     * 获取connect 通讯code
     * @return
     */
    public int getConnectCode(){
        return this.connectCode;
    }

}
