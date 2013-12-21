package com.fusionspy.beacon.site.wls.entity;

import com.fusionspy.beacon.site.InTimeData;
import com.fusionspy.beacon.site.MonitorData;
import com.sinosoft.one.util.encode.JaxbBinder;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bao
 * Date: 13-9-21
 * Time: 上午12:12
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "MONITOR")
@XmlAccessorType(XmlAccessType.FIELD)
public class WlsInTimeData extends MonitorData implements InTimeData {

    public static WlsInTimeData EMPTY;

    static {
        try {
            InputStream in = WlsInTimeData.class.getClassLoader().getResourceAsStream("site/WlsInTimeEmpty.xml");
            SAXReader xmlReader = new SAXReader();
            Document xmlDocument = xmlReader.read(in);
            String resp = xmlDocument.asXML();
            JaxbBinder jaxbBinder2 = new JaxbBinder(WlsInTimeData.class);
            EMPTY = jaxbBinder2.fromXml(resp);
            EMPTY.getResource().setCpuIdle(100);
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

    public List<WlsSvr> getServerRuntimes() {
        return serverRuntimes;
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

    @XmlElement(name = "SYSTEM")
    private WlsError wlsError;

    public WlsError getWlsError() {
        return wlsError;
    }

    public void setWlsError(WlsError wlsError) {
        this.wlsError = wlsError;
    }
}
