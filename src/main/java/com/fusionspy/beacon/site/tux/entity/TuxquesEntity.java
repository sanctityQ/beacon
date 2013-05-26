package com.fusionspy.beacon.site.tux.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * tux queue
 * User: qc
 * Date: 11-9-16
 * Time: 上午11:20
 */
@Table(name = "GE_MONITOR_TUX_QUE")
@Entity
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TuxquesEntity {
    @XmlTransient
    private Integer id;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    private String sitename;

    @Column(name = "siteName")
    @Basic
    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    @XmlTransient
    private Date rectime;

    @Column(name = "recTime")
    @Basic
    public Date getRectime() {
        return rectime;
    }

    public void setRectime(Date rectime) {
        this.rectime = rectime;
    }

    private String progname;

    @Column(name = "progName")
    @Basic
    @XmlAttribute(name="Server")
    public String getProgname() {
        return progname;
    }

    public void setProgname(String progname) {
        this.progname = progname;
    }

    private int queued;

    @Column(name = "queued")
    @Basic
    @XmlAttribute(name="Queued")
    public int getQueued() {
        return queued;
    }

    public void setQueued(int queued) {
        this.queued = queued;
    }

    private int svrcnt;

    @Column(name = "svrCnt")
    @Basic
    @XmlAttribute(name="SrvCnt")
    public int getSvrcnt() {
        return svrcnt;
    }

    public void setSvrcnt(int svrcnt) {
        this.svrcnt = svrcnt;
    }

    private String ipcsid;

    @Column(name = "ipcsId")
    @Basic
    @XmlAttribute(name="Queueid")
    public String getIpcsid() {
        return ipcsid;
    }

    public void setIpcsid(String ipcsid) {
        this.ipcsid = ipcsid;
    }

    @XmlTransient
    private String createAt;

    @Column(name = "create_at")
    @Basic
    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @XmlTransient
    private String updateAt;

    @Column(name = "update_at")
    @Basic
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

        TuxquesEntity that = (TuxquesEntity) o;

        if (id != that.id) return false;
        if (queued != that.queued) return false;
        if (svrcnt != that.svrcnt) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
        if (ipcsid != null ? !ipcsid.equals(that.ipcsid) : that.ipcsid != null) return false;
        if (progname != null ? !progname.equals(that.progname) : that.progname != null) return false;
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
        result = 31 * result + (progname != null ? progname.hashCode() : 0);
        result = 31 * result + queued;
        result = 31 * result + svrcnt;
        result = 31 * result + (ipcsid != null ? ipcsid.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
