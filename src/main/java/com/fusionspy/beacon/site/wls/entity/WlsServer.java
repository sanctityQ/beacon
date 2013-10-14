package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-10-14 0:49:30 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsServer.
* 
 */
@Entity
@Table(name="ge_monitor_wls_server")
public class WlsServer  implements java.io.Serializable {

    /**
    * PK，自动增长.
    */
    private Integer id;
    /**
        */
    private String version;
    /**
        */
    private String serverName;
    /**
        */
    private Date recTime;
    /**
        */
    private String listenAddress;
    /**
        */
    private String listenPort;
    /**
        */
    private Integer interval;
    /**
        */
    private String userName;
    /**
        */
    private String password;
    /**
        */
    private Integer isSsl;

    public WlsServer() {
    }

   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true)
    public Integer getId() {
    return this.id;
    }

    public void setId(Integer id) {
    this.id = id;
    }

    @Column(name="version", length=10)
    public String getVersion() {
    return this.version;
    }

    public void setVersion(String version) {
    this.version = version;
    }
    
    @Column(name="server_name")
    public String getServerName() {
    return this.serverName;
    }

    public void setServerName(String serverName) {
    this.serverName = serverName;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="rec_time", length=19)
    public Date getRecTime() {
    return this.recTime;
    }

    public void setRecTime(Date recTime) {
    this.recTime = recTime;
    }
    
    @Column(name="listen_address")
    public String getListenAddress() {
    return this.listenAddress;
    }

    public void setListenAddress(String listenAddress) {
    this.listenAddress = listenAddress;
    }
    
    @Column(name="listen_port", length=10)
    public String getListenPort() {
    return this.listenPort;
    }

    public void setListenPort(String listenPort) {
    this.listenPort = listenPort;
    }
    
    @Column(name="interval")
    public Integer getInterval() {
    return this.interval;
    }

    public void setInterval(Integer interval) {
    this.interval = interval;
    }
    
    @Column(name="user_name")
    public String getUserName() {
    return this.userName;
    }

    public void setUserName(String userName) {
    this.userName = userName;
    }
    
    @Column(name="password")
    public String getPassword() {
    return this.password;
    }

    public void setPassword(String password) {
    this.password = password;
    }
    
    @Column(name="is_SSL")
    public Integer getIsSsl() {
    return this.isSsl;
    }

    public void setIsSsl(Integer isSsl) {
    this.isSsl = isSsl;
    }


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}


