package com.fusionspy.beacon.site;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * site connect
 * Date: 11-9-11
 * Time: 下午1:25
 */
@Component
public class Connect{

    private final static int BUFFER_LEN = 8192;

	private final static Map coreMap = new HashMap(6);

	public final static Map<String,SiteThread> siteThreadMap = new HashMap<String,SiteThread>(100);

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final int HAND_INIT_CODE = 91;

    private static final int HAND_ACK_CODE = 92;

    private static final int GET_SVR_DATA_CODE = 93;

    private static final int GET_INIT_DATA_CODE = 94;

    private static final int CLOSE_CONNECT_CODE = 95;

    private static final int CLOSE_SVR_CODE = 96;

    private static final int DUMP = 97;


	static {
		coreMap.put("HANDINIT", "91");
		coreMap.put("HANDACK", "92");
		coreMap.put("GETSVRDATA", "93");
		coreMap.put("GETINITDATA", "94");
		coreMap.put("CLOSECONNECT", "95");
		coreMap.put("CLOSESVR", "96");
		coreMap.put("DUMP", "97");
	}

    private String loadXML(String iniXmlNameP) {
		try {
            if(StringUtils.isEmpty(iniXmlNameP))
                return StringUtils.EMPTY;
            URL initXml =Connect.class.getClassLoader().getResource(iniXmlNameP);
			SAXReader xmlReader = new SAXReader();
			Document xmlDocument = (Document) xmlReader.read(initXml);
			return xmlDocument.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private final class SiteThread {

	//	private volatile boolean signal;

		private String iniXmlName;

		private String siteID;

		private String agentIP;

		private int agentPort;

		private int sampleInterval=0;

		private Socket siteSocket;

		private PrintWriter out;

		private BufferedReader in;

		char[] buffer;

		int respLenCharCount;

		// get response-xml
		String respLenStr;

		int respLenInt;

		int readInLen;

		String respXmlStr;

		String iniRespXmlStr;

		String errXml;

        private String iniXml;

        public SiteThread(String iniXmlNameP, String siteIDP, String agentIPP,
				int sitePortP, int sampleIntervalP) {
            iniXml = loadXML(iniXmlNameP);
			siteID = siteIDP;
			agentIP = agentIPP;
			agentPort = sitePortP;
			sampleInterval = sampleIntervalP;
			buffer = "".toCharArray();
			//signal = true;
			errXml = "<Err/>";
		}

        public String getMonitorDataByResetConnect(){
            stop();
            init();
            return getMonitorData();
        }

		public void stop() {
			try {
                getData("CLOSECONNECT",null);
				if (this.out != null) {
					this.out.close();
				}
				if (this.in != null) {
					this.in.close();
				}
				if (this.siteSocket != null) {
					this.siteSocket.close();
				}
	//			this.signal = false;
			} catch (IOException ioe) {
                throw  new RuntimeException(ioe);
			}

		}

		public String getRespXmlStr() {
			return this.respXmlStr;
		}

		public String getIniRespXmlStr() {
			return this.iniRespXmlStr;
		}

		private String getData(String action, String para) {
			try {

				if (action != null) {

					int actionInt = Integer.parseInt((String) coreMap.get(action));

					switch (actionInt) {

					case 91:
                        logger.debug("HANDINIT");
						siteSocket = new Socket(agentIP, agentPort);
						out = new PrintWriter(siteSocket.getOutputStream(),true);
						in = new BufferedReader(new InputStreamReader(siteSocket.getInputStream()));

						int iniRCode;

						out.write((char) Integer.parseInt((String) coreMap
								.get("HANDINIT")));
						out.flush();

						char[] pubBuffer = new char[BUFFER_LEN];
						int len = in.read(pubBuffer, 0, 1);

						if (len <= 0) {
							System.out.println("handleSiteSocket: Cannot read any bytes from InputStream, Agent doesn't response ...");
						} else {
							iniRCode = (int) pubBuffer[0];
							if (iniRCode == Integer.valueOf((String) coreMap.get("HANDACK")).intValue()) {
								System.out.println("handleSiteSocket: Handshake with siteAgent confirmed, put the socket into siteSocketMap...");
							}
						}
						break;
					case 93:
                        logger.debug("GETSVRDATA");
						out.write((char) Integer.parseInt((String) coreMap
								.get("GETSVRDATA")));
						out.flush();
                        buffer = new char[9];
						// get length of response-xml
						respLenCharCount = in.read(buffer, 0, 9);
                        respLenStr = new String(buffer).trim();
						// get response-xml
                        logger.debug("respLenStr  = {}",respLenStr);
                        if(StringUtils.isEmpty(respLenStr)){
                            return getMonitorDataByResetConnect();
                        }
						int respLenInt = Integer.parseInt(respLenStr.trim());
                        int count = 0;
                        StringBuilder bufferWriter = new  StringBuilder(respLenInt);
                        while (count++<respLenInt) {
                             bufferWriter.append((char)in.read());
                        }
						respXmlStr = bufferWriter.toString();

						break;

					case 94:
                        logger.debug("GETINITDATA");
						// write 94 into stream to indicate this is to ask for
						// site's initial information
						out.write((char) 94);
						out.flush();
                        logger.debug("para =  {}",para);
						// construct and send the length of parameter xml
						int inXmlLen = para.length();
						String sLen = String.valueOf(inXmlLen);
						if (sLen.length() < 8) {
							int dif = 8 - sLen.length();
							StringBuffer zeroStr = new StringBuffer();
							for (int m = 0; m < dif; m++) {
								zeroStr = zeroStr.append("0");
							}
							sLen = zeroStr.toString() + sLen;
						}
						char[] charLen = sLen.toCharArray();
						for (int i = 0; i < charLen.length; i++) {
							out.write(charLen[i]);
						}
						out.flush();


						// send parameter xml
						buffer =  new String(para.getBytes()).toCharArray();

						out.write(buffer);
						out.flush();

//                        if(buffer.length == 0)
                            buffer = new char[9];
						// get length of response-xml
						respLenCharCount = in.read(buffer, 0, 9);

                        String  respLenStr = new String(buffer).trim();
                        logger.debug("GETINITDATA:{} -- respLenCharCount:{}" , buffer,respLenStr);
						// get response-xml
						//respLenStr = new String(buffer, 0, respLenCharCount);
						respLenInt = Integer.parseInt(respLenStr.trim());
//                        buffer = new char[respLenInt];
//						readInLen = in.read(buffer, 0, respLenInt);
//                        logger.debug("readInLen = {} , all line = {}" ,readInLen,respLenInt);
//						respXmlStr = new String(buffer, 0, readInLen);
                        count = 0;
                        bufferWriter = new  StringBuilder(respLenInt);
                        while (count++<respLenInt) {
                            bufferWriter.append((char)in.read());
                        }
                        respXmlStr = bufferWriter.toString();
                        logger.debug("--GETINITDATA:receive data {}",respXmlStr);
						break;

					case 95:
                        logger.debug("CLOSECONNECT");
						out.write((char) Integer.parseInt((String) coreMap
								.get("CLOSECONNECT")));
						out.flush();
						// get length of incoming-xml
						// len = in.read(buffer, 0, 8);
						break;
					default:
                        logger.debug("NO PROPER CODE");
						in.close();
						out.close();
						break;
					}

				} else {
                    logger.error("getData: no action specified, need to point out which action to do...");
				}
				return respXmlStr;
			}
//            catch (UnknownHostException uhe) {
//				uhe.printStackTrace();
//			} catch (ConnectException connectException){
//                throw new RuntimeException(connectException);
//            }
//            catch (IOException ioe) {
//				ioe.printStackTrace();
//			}
            catch (Throwable e) {
//				System.out.println("getData: got RuntimeException while getting data from agent...");
				logger.error("getData: got RuntimeException while getting data from agent,exception info is {}",e);
                throw new ConnectAgentException(e);
			}
//            return  StringUtils.EMPTY;
		}

        public String init(){
            if (siteSocket == null || siteSocket.isClosed()) {
					getData("HANDINIT", null);
		    }
            return getData("GETINITDATA", iniXml);
        }

        public String getMonitorData(){
            return this.getData("GETSVRDATA", iniXml);
        }

//		public void run() {
//			System.out.println("Site Thread running now...");
//			try {
//
//				String iniXml = loadXML(iniXmlName);
//				// System.out.println(iniXml);
//
//				if (siteSocket == null || siteSocket.isClosed()) {
//					getData("HANDINIT", null);
//				}
//
//				getData("GETINITDATA", iniXml);
//				while (this.signal == true) {
//					getData("GETSVRDATA", iniXml);
//					Thread.sleep(sampleInterval);
//				}
//			} catch (InterruptedException ie) {
//				ie.printStackTrace();
//			}
//		}

	}

	public  String startSiteThread(String iniXmlNameP, String siteName,
			String agentIPP, int sitePortP, int sampleIntervalP){
//		try {
            SiteThread task = siteThreadMap.get(siteName);
            if(task==null){
               task = new SiteThread(iniXmlNameP, siteName, agentIPP,
					sitePortP, sampleIntervalP);
                siteThreadMap.put(siteName, task);
            }
            return task.init();

//		} catch (Exception ioe) {
//			System.out.println("Got IOException while creating site thread");
//			ioe.printStackTrace();
//		}
        //return null;
	}

    public String getInTimeData(String siteName){
         SiteThread st = siteThreadMap.get(siteName);
         return st.getMonitorData();
    }

	public void stopSiteThreadByName(String siteName) {
		SiteThread st = siteThreadMap.get(siteName);
		st.stop();
        siteThreadMap.remove(siteName);
	}

    public static void main(String[] args){
         System.out.println(SiteThread.class.getClassLoader().getResource("ini9.xml"));
    }
}
