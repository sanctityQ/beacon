package com.fusionspy.beacon.site.tux;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.InitData;
import com.fusionspy.beacon.site.MonitorDataRepository;
import com.fusionspy.beacon.site.tux.entity.*;
import com.fusionspy.beacon.site.tux.entity.DataSave;
import com.fusionspy.beacon.site.tux.entity.SysrecsEntity;
import com.google.common.collect.MapMaker;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * tux data simulation data
 * User: qc
 * Date: 12-3-10
 * Time: 下午5:53
 */
@Component(value="tuxDataSimulationRep")
public class TuxDataSimulationRepository implements MonitorDataRepository {

    private static Logger logger = LoggerFactory.getLogger(TuxDataSimulationRepository.class);

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    private String[] siteNames = {"TMUSREVT", "simpserv", "EventAgent", "WSL", "BBL", "TMSYSEVT", "WSH", "TuxAgent"};

    // private HashMap
    private ConcurrentMap<String, SimulationData> last = new MapMaker().concurrencyLevel(32).makeMap();

    @Override
    public <T> InitData getInitData(String siteName, String ip, int port) {
        Assert.hasText(siteName);
        TuxIniData initData = new TuxIniData();
        SysrecsEntity sysrecs = new SysrecsEntity();
        initData.setSysrecsEntity(sysrecs);

        initData.setSiteName(siteName);
        initData.setDate(df.format(new Date()));
        sysrecs.setProductver("VVer:8.1,Patch Level");
        sysrecs.setSystemboot("2011-10-15 03:52:51");
        sysrecs.setOstype("localhost.localdomain - 2.4.20-8");
        sysrecs.setAgentver("1.0.4 - FORMAL LICENSE");

        return initData;
    }

    @Override
    public InTimeData getInTimeData(String siteName) {
        // server need 100; because every  rpsdone when busy need 54000-60000, idel need 12000-15000, so server at least 100
        return new SimulationData(siteName, 11, randomRange(70, 80), 100).Simulation();
    }

    int randomRange(int min, int max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }

    @Override
    public void stopSite(String siteName) {
        last.remove(siteName);
    }


    /**
     * queueid relation  server map
     */
    public class SimulationData {

        private boolean busy;

        private int queueCount = 11;

        private int clientCount = 70;

        private int serverCount = 11;

        private List<TuxsvrsEntity> svrs;

        private String siteName;

        public SimulationData(String siteName, int queueCount, int clientCount, int serverCount) {
            this.siteName = siteName;
            busy = isBusy();
            logger.debug("is busy = {}",busy );
            this.queueCount = queueCount;
            this.clientCount = clientCount;
            this.serverCount = serverCount;
        }

        boolean isBusy() {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            if (9 < hour && hour < 12) {
                return true;
            } else if (13 < hour && hour < 17) {
                return true;
            } else
                return false;
        }

        TuxquesEntity createQueue() {
            TuxquesEntity que = new TuxquesEntity();
            int id = generateQueueId();
            String server = siteNames[RandomUtils.nextInt(7)];
            que.setProgname(server);
            que.setSvrcnt(0);
            que.setQueued(busy ? randomRange(20, 40) : randomRange(2, 3));
            que.setIpcsid(String.valueOf(id));
            return que;
        }


        int generateQueueId() {
            int t = new Random().nextInt(99999);
            if (t < 10000) t += 10000;
            return t;
        }

        public InTimeData Simulation() {
            TuxInTimeData data = new TuxInTimeData();
            data.setClients(createClientList());
            data.setQueues(createQueueList());
            data.setServers(createServerList());
            data.setTuxSysData(createTuxSysData());
            last.put(siteName, this);
            return data;
        }

        private List<TuxsvrsEntity> createServerList() {
            List<TuxsvrsEntity> list = null;
            if (last.containsKey(siteName)) {
                 SimulationData old =  last.get(siteName);
                 list = old.svrs;
                 for(int i=0,n = list.size(); i<n;i++){
                      TuxsvrsEntity svr = old.svrs.get(i);
                      svr.setRqdone(svr.getRqdone()+(busy?randomRange(540,600):randomRange(120,150)));
                      svr.setCpuuse((busy ? randomRange(30, 50) : randomRange(0, 9)) + (float) (Math.round(new Random().nextFloat() * 100)) / 100);
                      svr.setMemoryuse(randomRange(8000, 11000));
                 }
            } else {
                list = new ArrayList<TuxsvrsEntity>();
                for (int i = 0; i < serverCount; i++) {
                    list.add(createServer());
                }
            }
            Collections.sort(list, TuxDataComparatorFactory.getComparatorServer(DataSave.Server.RQDONE));
            svrs = list;
            return list;
        }

        private TuxsvrsEntity createServer() {
            TuxsvrsEntity svr = new TuxsvrsEntity();
            svr.setRqdone(randomRange(100, 200));
            svr.setCpuuse((busy ? randomRange(30, 50) : randomRange(0, 9)) + (float) (Math.round(new Random().nextFloat() * 100)) / 100);
            svr.setMemoryuse(randomRange(8000, 11000));
            return svr;
        }

        private List<TuxcltsEntity> createClientList() {
            List<TuxcltsEntity> list = new ArrayList<TuxcltsEntity>();
            int busyCount = busy ? randomRange(60, 80) : randomRange(20, 30);
            if (clientCount < busyCount) {
                busyCount = clientCount;
            }
            for (int i = 0; i < clientCount; i++) {
                list.add(createClient((i < busyCount) ? true : false));
            }
            return list;
        }

        private TuxcltsEntity createClient(boolean busyFlag) {
            TuxcltsEntity clt = new TuxcltsEntity();
            clt.setClientname(StringUtils.EMPTY);
            clt.setClientpid(String.valueOf(RandomUtils.nextInt()));
            clt.setClientstatus(busyFlag ? Conditions.Status.BUSY : Conditions.Status.IDLE);
            return clt;
        }

        private TuxSysData createTuxSysData() {
            TuxSysData tuxSysData = new TuxSysData();
            tuxSysData.setCltCnt(String.valueOf(this.clientCount));
            tuxSysData.setFreeMem(String.valueOf(randomRange(1536, 2048)) + "M");
            tuxSysData.setIdleCPU(busy ? randomRange(50, 70) : randomRange(90, 99));
            tuxSysData.setQueCnt(String.valueOf(queueCount));
            tuxSysData.setSvrCnt(String.valueOf(serverCount));
            return tuxSysData;
        }

        private List<TuxquesEntity> createQueueList() {
            List<TuxquesEntity> list = new ArrayList<TuxquesEntity>();
            for (int i = 0; i < queueCount; i++) {
                list.add(createQueue());
            }
            return list;
        }
    }


    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            System.out.println((float) (Math.round(new Random().nextFloat() * 100)) / 100);
        }

    }
}
