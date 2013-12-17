package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-9-20 23:23:18 by One Data Tools 1.0.0


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsJdbc.
* 
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="ge_monitor_wls_jdbc")
public class WlsJdbc  implements java.io.Serializable {

    /**
        */
    @XmlTransient
    private Integer id;
    /**
    * 记录时间.
    */
    @XmlTransient
    private Date recTime;
    /**
    * Server名称.
    */
    @XmlAttribute(name = "serverName")
    private String serverName;
    /**
    * 连接池名称.
    */
    @XmlAttribute(name = "Name")
    private String name;
    /**
    * 活动链接数.
    */
    @XmlAttribute(name = "ActiveConnectionsCurrentCount")
    private Integer activeCount;
    /**
    * 连接最高值.
    */
    @XmlAttribute(name = "ActiveConnectionsHighCount")
    private Integer activeHigh;
    /**
    * 连接池大小.
    */
    @XmlAttribute(name = "CurrCapacity")
    private Integer currCapacity;
    /**
    * 连接泄露数.
    */
    @XmlAttribute(name = "LeakedConnectionCount")
    private Integer leakCount;
    /**
    * 状态.
    */
    @XmlAttribute(name = "State")
    private String state;

    public WlsJdbc() {
    }

   
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true)
    public Integer getId() {
    return this.id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="rec_time", length=19)
    public Date getRecTime() {
    return this.recTime;
    }

    public void setRecTime(Date recTime) {
    this.recTime = recTime;
    }
    
    @Column(name="server_name")
    public String getServerName() {
    return this.serverName;
    }

    public void setServerName(String serverName) {
    this.serverName = serverName;
    }
    
    @Column(name="name")
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
    this.name = name;
    }
    
    @Column(name="active_count")
    public Integer getActiveCount() {
    return this.activeCount;
    }

    public void setActiveCount(Integer activeCount) {
    this.activeCount = activeCount;
    }
    
    @Column(name="active_high")
    public Integer getActiveHigh() {
    return this.activeHigh;
    }

    public void setActiveHigh(Integer activeHigh) {
    this.activeHigh = activeHigh;
    }
    
    @Column(name="curr_capacity")
    public Integer getCurrCapacity() {
    return this.currCapacity;
    }

    public void setCurrCapacity(Integer currCapacity) {
    this.currCapacity = currCapacity;
    }
    
    @Column(name="leak_count")
    public Integer getLeakCount() {
    return this.leakCount;
    }

    public void setLeakCount(Integer leakCount) {
    this.leakCount = leakCount;
    }
    
    @Column(name="state")
    public String getState() {
    return this.state;
    }

    public void setState(String state) {
    this.state = state;
    }

    @Transient
    public BigDecimal getJdbcusage() {
        BigDecimal jdbcUsage = new BigDecimal(activeCount+leakCount+"").divide(new BigDecimal(currCapacity+""), 2, RoundingMode.HALF_UP);
        return jdbcUsage.multiply(new BigDecimal("100"));
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


