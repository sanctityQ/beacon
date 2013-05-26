package com.fusionspy.beacon.system.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * SysRec
 * User: qc
 * Date: 11-9-9
 * Time: 下午3:21
 */
@Table(name = "GE_MONITOR_TUX_SYSRECS")
@Entity
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SysrecsEntity {
    private Integer id;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String siteName;

    @Column(name = "siteName")
    @Basic
    @XmlTransient
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    private Date rectime;

    @Column(name = "recTime")
    @Basic
    @XmlTransient
    public Date getRectime() {
        return rectime;
    }

    public void setRectime(Date rectime) {
        this.rectime = rectime;
    }

    private String productver;

    @Column(name = "productVer")
    @Basic
    @XmlAttribute(name="TuxVersion")
    public String getProductver() {
        return productver;
    }

    public void setProductver(String productver) {
        this.productver = productver;
    }

    private String systemboot;

    @Column(name = "systemBoot")
    @Basic
    @XmlAttribute(name="TuxBootAt")
    public String getSystemboot() {
        return systemboot;
    }

    public void setSystemboot(String systemboot) {
        this.systemboot = systemboot;
    }

    private String ostype;

    @Column(name = "osType")
    @Basic
    @XmlAttribute(name="OSVersion")
    public String getOstype() {
        return ostype;
    }

    public void setOstype(String ostype) {
        this.ostype = ostype;
    }

    private String agentver;

    @Column(name = "agentVer")
    @Basic
    @XmlAttribute(name="AgentVersion")
    public String getAgentver() {
        return agentver;
    }

    public void setAgentver(String agentver) {
        this.agentver = agentver;
    }

    private String createAt;

    @Column(name = "create_at")
    @Basic
    @XmlTransient
    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    private String updateAt;

    @Column(name = "update_at")
    @Basic
    @XmlTransient
    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysrecsEntity that = (SysrecsEntity) o;

        if (id != that.id) return false;
        if (agentver != null ? !agentver.equals(that.agentver) : that.agentver != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
        if (ostype != null ? !ostype.equals(that.ostype) : that.ostype != null) return false;
        if (productver != null ? !productver.equals(that.productver) : that.productver != null) return false;
        if (rectime != null ? !rectime.equals(that.rectime) : that.rectime != null) return false;
        if (siteName != null ? !siteName.equals(that.siteName) : that.siteName != null) return false;
        if (systemboot != null ? !systemboot.equals(that.systemboot) : that.systemboot != null) return false;
        if (updateAt != null ? !updateAt.equals(that.updateAt) : that.updateAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (siteName != null ? siteName.hashCode() : 0);
        result = 31 * result + (rectime != null ? rectime.hashCode() : 0);
        result = 31 * result + (productver != null ? productver.hashCode() : 0);
        result = 31 * result + (systemboot != null ? systemboot.hashCode() : 0);
        result = 31 * result + (ostype != null ? ostype.hashCode() : 0);
        result = 31 * result + (agentver != null ? agentver.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
