package com.fusionspy.beacon.site.tux.entity;

import com.fusionspy.beacon.site.tux.Conditions;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Tuxclt
 * User: qc
 * Date: 11-9-9
 * Time: 下午2:45
 */
@Table(name = "GE_MONITOR_TUX_CLT")
@Entity
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TuxcltsEntity {
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

    private String sitename;

    @Column(name = "siteName")
    @Basic
    @XmlTransient
    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
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

    private String conntime;

    @Column(name = "connTime")
    @Basic
    @XmlAttribute(name="ConTime")
    public String getConntime() {
        return conntime;
    }

    public void setConntime(String conntime) {
        this.conntime = conntime;
    }

    private Conditions.Status clientstatus;

    @Column(name = "clientStatus")
    @Basic
    @XmlAttribute(name="Status")
    @Enumerated(EnumType.STRING)
    public Conditions.Status getClientstatus() {
        return clientstatus;
    }

    public void setClientstatus(Conditions.Status clientstatus) {
        this.clientstatus = clientstatus;
    }

    private String clientaddr;

    @Column(name = "clientAddr")
    @Basic
    @XmlAttribute(name="ClientAddr")
    public String getClientaddr() {
        return clientaddr;
    }

    public void setClientaddr(String clientaddr) {
        this.clientaddr = clientaddr;
    }

    private String clientpid;

    @Column(name = "clientPid")
    @Basic
    @XmlAttribute(name = "ClientPID")
    public String getClientpid() {
        return clientpid;
    }

    public void setClientpid(String clientpid) {
        this.clientpid = clientpid;
    }

    private String clientname;

    @Column(name = "clientName")
    @Basic
    @XmlAttribute(name="Name")
    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
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

        TuxcltsEntity that = (TuxcltsEntity) o;

        if (id != that.id) return false;
        if (clientaddr != null ? !clientaddr.equals(that.clientaddr) : that.clientaddr != null) return false;
        if (clientname != null ? !clientname.equals(that.clientname) : that.clientname != null) return false;
        if (clientpid != null ? !clientpid.equals(that.clientpid) : that.clientpid != null) return false;
        if (clientstatus != null ? !clientstatus.equals(that.clientstatus) : that.clientstatus != null) return false;
        if (conntime != null ? !conntime.equals(that.conntime) : that.conntime != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
        if (rectime != null ? !rectime.equals(that.rectime) : that.rectime != null) return false;
        if (sitename != null ? !sitename.equals(that.sitename) : that.sitename != null) return false;
        if (updateAt != null ? !updateAt.equals(that.updateAt) : that.updateAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sitename != null ? sitename.hashCode() : 0);
        result = 31 * result + (rectime != null ? rectime.hashCode() : 0);
        result = 31 * result + (conntime != null ? conntime.hashCode() : 0);
        result = 31 * result + (clientstatus != null ? clientstatus.hashCode() : 0);
        result = 31 * result + (clientaddr != null ? clientaddr.hashCode() : 0);
        result = 31 * result + (clientpid != null ? clientpid.hashCode() : 0);
        result = 31 * result + (clientname != null ? clientname.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
