package com.fusionspy.beacon.site.wls.entity;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.wls.WlsMonitorData;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@XmlRootElement(name = "MONITOR")
@XmlAccessorType(XmlAccessType.FIELD)
public class WlsInTimeData extends WlsMonitorData implements InTimeData {

    public static WlsInTimeData EMPTY;


    static {
        //初始化数据需要去除Dom4j
        try {
            InputStream in = WlsInTimeData.class.getClassLoader().getResourceAsStream("site/WlsInTimeEmpty.xml");
            //InputStreamReader
            SAXReader xmlReader = new SAXReader();
            Document xmlDocument = xmlReader.read(in);
            String resp = xmlDocument.asXML();
            JaxbBinder jaxbBinder2 = new JaxbBinder(WlsInTimeData.class);
            EMPTY = jaxbBinder2.fromXml(resp);
            EMPTY.getResource().setCpuIdle(0);
            EMPTY.getResource().setMemFree("0");
            EMPTY.defaultData();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @XmlElement(name = "ServerRuntime")
    private List<WlsSvr> serverRuntimes = new ArrayList<WlsSvr>();
    @XmlElement(name = "JVMRuntime")
    private List<WlsJvm> jvmRuntimes = new ArrayList<WlsJvm>();
    @XmlElement(name = "ThreadPoolRuntime")
    private List<WlsThread> threadPoolRuntimes = new ArrayList<WlsThread>();
    @XmlElement(name = "JDBCServiceRuntime.JDBCDataSourceRuntimeMBeans")
    private List<WlsJdbc> jdbcDataSourceRuntimes = new ArrayList<WlsJdbc>();
    @XmlElement(name = "ApplicationRuntimes.ComponentRuntimes")
    private List<WlsWebapp> componentRuntimes = new ArrayList<WlsWebapp>();
    @XmlElement(name = "JMSRuntime.JMSServers")
    private List<WlsJms> jmsServers = new ArrayList<WlsJms>();
    @XmlElement(name = "ApplicationRuntimes.ComponentRuntimes.EJBRuntimes.PoolRuntime")
    private List<WlsEjbpool> poolRuntimes = new ArrayList<WlsEjbpool>();
    @XmlElement(name = "ApplicationRuntimes.ComponentRuntimes.EJBRuntimes.CacheRuntime")
    private List<WlsEjbcache> cacheRuntime = new ArrayList<WlsEjbcache>();
    @XmlElement(name = "OSResource")
    private WlsResource resource;

    private Integer runningServerAmount;
    private Integer stopServerAmount;
    private Integer throughputCount = 0;

    public List<WlsSvr> getServerRuntimes() {
        return serverRuntimes;
    }


    public Integer getRunningServerAmount(){
        if(runningServerAmount==null){
            calculateServerAmount();
        }
        return runningServerAmount;
    }

    void calculateServerAmount(){
        if(this == EMPTY)
            return;
        for(WlsSvr serverRuntime : serverRuntimes) {
            if(serverRuntime.isRunning()){
                runningServerAmount++;
            }else{
                stopServerAmount++;
            }
        }
    }

    public Integer getStopServerAmount(){
        if(stopServerAmount==null){
            calculateServerAmount();
        }
        return stopServerAmount;
    }


    public void setServerRuntimes(List<WlsSvr> serverRuntimes) {
        this.serverRuntimes = serverRuntimes;
    }

    public List<WlsJvm> getJvmRuntimes() {
        return jvmRuntimes;
    }

    public void setJvmRuntimes(List<WlsJvm> jvmRuntimes) {
        this.jvmRuntimes = jvmRuntimes;
    }

    public List<WlsThread> getThreadPoolRuntimes() {
        return threadPoolRuntimes;
    }

    public void setThreadPoolRuntimes(List<WlsThread> threadPoolRuntimes) {
        this.threadPoolRuntimes = threadPoolRuntimes;
    }

    public List<WlsJdbc> getJdbcDataSourceRuntimes() {
        return jdbcDataSourceRuntimes;
    }

    public void setJdbcDataSourceRuntimes(List<WlsJdbc> jdbcDataSourceRuntimes) {
        this.jdbcDataSourceRuntimes = jdbcDataSourceRuntimes;
    }

    public List<WlsWebapp> getComponentRuntimes() {
        return componentRuntimes;
    }

    public void setComponentRuntimes(List<WlsWebapp> componentRuntimes) {
        this.componentRuntimes = componentRuntimes;
    }

    public List<WlsJms> getJmsServers() {
        return jmsServers;
    }

    public void setJmsServers(List<WlsJms> jmsServers) {
        this.jmsServers = jmsServers;
    }

    public List<WlsEjbpool> getPoolRuntimes() {
        return poolRuntimes;
    }

    public void setPoolRuntimes(List<WlsEjbpool> poolRuntimes) {
        this.poolRuntimes = poolRuntimes;
    }

    public List<WlsEjbcache> getCacheRuntime() {
        return cacheRuntime;
    }

    public void setCacheRuntime(List<WlsEjbcache> cacheRuntime) {
        this.cacheRuntime = cacheRuntime;
    }

    public WlsResource getResource() {
        return resource;
    }

    public void setResource(WlsResource resource) {
        this.resource = resource;
    }

    public WlsInTimeData defaultData() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("error".equals(field.getName())) continue;
                if (field.isAnnotationPresent(XmlElement.class)) {
                    field.setAccessible(true);
                    Object fieldVal = field.get(this);
                    if (fieldVal == null) continue;
                    if (field.getType() == List.class) {
                        List<?> fieldList = (List<?>) fieldVal;
                        for (Object obj : fieldList) {
                            for (Field subField : obj.getClass().getDeclaredFields()) {
                                subField.setAccessible(true);
                                if (subField.isAnnotationPresent(XmlAttribute.class) && subField.getType() == String.class) {
                                    Object subFieldVal = subField.get(obj);
                                    if (subFieldVal == null || StringUtils.isBlank(subFieldVal.toString())) {
                                        subField.set(obj, "N/A");
                                    }
                                }
                                if (subField.isAnnotationPresent(XmlAttribute.class) && subField.getType() == Integer.class) {
                                    Object subFieldVal = subField.get(obj);
                                    if (subFieldVal == null) {
                                        subField.set(obj, 0);
                                    }
                                }
                                if ("recTime".equals(subField.getName()) && subField.getType() == Date.class) {
                                    subField.set(obj, new Date());
                                }
                            }
                        }
                    } else {
                        for (Field subField : fieldVal.getClass().getDeclaredFields()) {
                            subField.setAccessible(true);
                            if (subField.isAnnotationPresent(XmlAttribute.class) && subField.getType() == String.class) {
                                Object subFieldVal = subField.get(fieldVal);
                                if (subFieldVal == null || StringUtils.isBlank(subFieldVal.toString())) {
                                    subField.set(fieldVal, "N/A");
                                }
                            }
                            if (subField.isAnnotationPresent(XmlAttribute.class) && subField.getType() == Integer.class) {
                                Object subFieldVal = subField.get(fieldVal);
                                if (subFieldVal == null) {
                                    subField.set(fieldVal, 0);
                                }
                            }
                            if ("recTime".equals(subField.getName()) && subField.getType() == Date.class) {
                                subField.set(fieldVal, new Date());
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Integer getThroughputCount() {
        for(WlsThread wlsThread:threadPoolRuntimes){
            throughputCount = wlsThread.getThoughput()+throughputCount;
        }
        return throughputCount;
    }

    public Iterable<WlsWebapp> getWlsWebappsByServerName(final String serverName){
       return Iterables.filter(this.getComponentRuntimes(), new Predicate<WlsWebapp>() {
           @Override
           public boolean apply(@Nullable WlsWebapp input) {
               return input.getServerName().equals(serverName);
           }
       });
    }
}
