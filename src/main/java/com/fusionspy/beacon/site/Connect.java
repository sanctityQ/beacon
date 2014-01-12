package com.fusionspy.beacon.site;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


@Component
public class Connect{

    private final static int BUFFER_LEN = 8192;

	private final static BiMap<Integer,String> operationCodeMap = HashBiMap.create();

	public final static Map<String,SiteThread> siteThreadMap = Maps.newConcurrentMap();

    private Logger logger = LoggerFactory.getLogger(Connect.class);

    static final int HAND_INIT_CODE = 91;

    private static final int HAND_ACK_CODE = 92;

    static final int GET_SVR_DATA_CODE = 93;

    static final int GET_INIT_DATA_CODE = 94;

    static final int CLOSE_CONNECT_CODE = 95;

    //!!no use
    //private static final int CLOSE_SVR_CODE = 96;
    //!!no use
    //private static final int DUMP = 97;

    enum Protocol{
        hand,init,runtime,close
    }

	static {
        operationCodeMap.put(HAND_INIT_CODE,"HANDINIT");
        operationCodeMap.put(HAND_ACK_CODE,"HANDACK");
        operationCodeMap.put(GET_SVR_DATA_CODE,"GETSVRDATA");
        operationCodeMap.put(GET_INIT_DATA_CODE,"GETINITDATA");
        operationCodeMap.put(CLOSE_CONNECT_CODE,"CLOSECONNECT");
//		coreMap.put("CLOSESVR", "96");
//		coreMap.put("DUMP", "97");
	}


	private final class SiteThread {

		private String siteID;

		private String ip;

		private int port;

		private Socket siteSocket;

		private PrintWriter out;

		private BufferedReader in;

        private String iniXml;

        private int connectCount;

        public SiteThread(String siteID, String ip,
				int port,String iniXml) {
            this.siteID = siteID;
            this.ip = ip;
            this.port = port;
            this.iniXml = iniXml;
            init();
            hand();
		}

        private void init(){
            try {
                this.siteSocket = new Socket(ip, port);
                this.out = new PrintWriter(siteSocket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(siteSocket.getInputStream()));
            }catch (UnknownHostException e1){
                throw new ConnectAgentException(MessageFormatter.arrayFormat(" siteId[{}], ip[{}],port[{}]:获取不到主机地址",
                        new Object[]{this.siteID,this.ip,this.port}).getMessage());
            }
            catch (IOException e) {
                throw new ConnectAgentException(MessageFormatter.arrayFormat(" siteId[{}], ip[{}],port[{}]:创建主机Connect失败",
                        new Object[]{this.siteID,this.ip,this.port}).getMessage());
            }
        }

        private void hand() {
            log("HANDINIT");

            //connect hand
            out.write((char) HAND_INIT_CODE);
            out.flush();
            char[] pubBuffer = new char[BUFFER_LEN];
            try {
                int len = in.read(pubBuffer, 0, 1);
                if (len <= 0) {
                    System.out.println("handleSiteSocket: Cannot read any bytes from InputStream, Agent doesn't response ...");
                } else {
                    int iniRCode = (int) pubBuffer[0];
                    if (iniRCode == HAND_ACK_CODE) {
                        System.out.println("handleSiteSocket: Handshake with siteAgent confirmed, put the socket into siteSocketMap...");
                    } else {
                        //need close socket
                        this.close();
                        throw new ConnectAgentException("握手返回协议码不正确", HAND_ACK_CODE);
                    }
                }
            } catch (IOException e) {
                if(e instanceof SocketException){
                    this.close();
                }
                throw new ConnectAgentException(e, HAND_INIT_CODE);
            }
        }



        private String getInitData() {
            log("GETINITDATA");
            // write 94 into stream to indicate this is to ask for
            // site's initial information
            out.write((char) GET_INIT_DATA_CODE);
            out.flush();
            request(this.iniXml,GET_INIT_DATA_CODE);
            return getResponse(GET_INIT_DATA_CODE);
        }

        private void request(String data,int code){
            data = data.trim();
            int dataLength = data.length();
            String sLen = String.valueOf(dataLength);
            if (sLen.length() < 8) {
                int dif = 8 - sLen.length();
                StringBuffer zeroStr = new StringBuffer();
                for (int m = 0; m < dif; m++) {
                    zeroStr = zeroStr.append("0");
                }
                sLen = zeroStr.toString() + sLen;
            }
            log(MessageFormatter.arrayFormat("Protocol[{}] request data: length is {}, xml is {}" ,new Object[]{operationCodeMap.get(code),sLen,data}).getMessage());
            char[] charLen = sLen.toCharArray();
            for (int i = 0; i < charLen.length; i++) {
                out.write(charLen[i]);
            }
            out.flush();
            // send parameter xml
            out.write(data.toCharArray());
            out.flush();
        }

        private  void checkResponse(String respLenStr,int code){
            log(MessageFormatter.arrayFormat("Protocol[{}] output respLen: {}",new Object[]{operationCodeMap.get(code),respLenStr}).getMessage());
            if (StringUtils.isBlank(respLenStr)) {
                throw new ConnectAgentException("返回数据长度为空",code);
            }
        }

        private String getResponse(int code){
            char[] buffer = new char[9];
            try {
                in.read(buffer, 0, 9);
                String respLenStr = new String(buffer).trim();
                checkResponse(respLenStr,code);
                int respLenInt = Integer.parseInt(respLenStr.trim());

                int count = 0;
                StringBuilder bufferWriter = new StringBuilder(respLenInt);
                while (count++ < respLenInt) {
                    bufferWriter.append((char) in.read());
                }
                String data =  bufferWriter.toString();
                log(MessageFormatter.format("Protocol[{}] response data: {}" ,operationCodeMap.get(code),data).getMessage());
                return data;
            } catch (IOException e) {
                if(e instanceof SocketException){
                   this.close();
                }
                throw new ConnectAgentException(e,code);
            }
        }

        private String getRuntimeData() {
            log("GETSVRDATA");
            out.write((char) GET_SVR_DATA_CODE);
            out.flush();
            return getResponse(GET_SVR_DATA_CODE);
        }


		private void close() {

            log("CLOSECONNECT");
            if (this.out != null) {
                this.out.write((char) CLOSE_CONNECT_CODE);
                this.out.flush();
                this.out.close();
            }
            try {
                if (this.in != null) {
                    this.in.close();
                }

                if (this.siteSocket != null) {
                    this.siteSocket.close();
                }
                siteThreadMap.remove(this.siteID);
            } catch (IOException ioe) {
                //关闭连接异常
                throw  new ConnectAgentException(ioe,CLOSE_CONNECT_CODE);
            }
        }


        private void log(String message){
            logger.debug("SITE ID:[{}]  "+ message,siteID);
        }

	}

	public String startSiteThread(String siteName,
			String agentIP, int sitePort,String initXml) {
        SiteThread task = siteThreadMap.get(siteName);
        if (task == null) {
            task = new SiteThread(siteName, agentIP,sitePort,initXml);
            siteThreadMap.put(siteName, task);
        }
        return task.getInitData();
    }

    public String getInTimeData(String siteName){
         SiteThread st = siteThreadMap.get(siteName);
         if(st==null){

         }
         return st.getRuntimeData();
    }

	public void stopSiteThreadByName(String siteName) {
        SiteThread st = siteThreadMap.get(siteName);
        st.close();
    }

}
