package com.fusionspy.beacon.site.tux.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;

/**
 * Tuxsvrs Entity
 * User: qc
 * Date: 11-9-9
 * Time: 下午2:45
 */
@Table(name = "GE_MONITOR_TUX_SVR")
@Entity
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TuxsvrsEntity {

    private Integer id = new Integer(0);

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

    private int memoryuse;

    @Column(name = "memoryUse")
    @Basic
    @XmlAttribute(name="UseMem")
    public int getMemoryuse() {
        return memoryuse;
    }

    public void setMemoryuse(int memoryuse) {
        this.memoryuse = memoryuse;
    }

    private float cpuuse;

    @Column(name = "cpuUse")
    @Basic
    @XmlAttribute(name="UseCPU")
    public float getCpuuse() {
        return cpuuse;
    }

    public void setCpuuse(float cpuuse) {
        this.cpuuse = cpuuse;
    }

    private String currenctsvc;

    @Column(name = "currenctSvc")
    @Basic
    @XmlAttribute(name="CurrSvc")
    public String getCurrenctsvc() {
        return currenctsvc;
    }

    public void setCurrenctsvc(String currenctsvc) {
        this.currenctsvc = currenctsvc;
    }

    private int svrmin;

    @Column(name = "svrMin")
    @Basic
    @XmlAttribute(name="SvrMin")
    public int getSvrmin() {
        return svrmin;
    }

    public void setSvrmin(int svrmin) {
        this.svrmin = svrmin;
    }

    private int svrmax;

    @Column(name = "svrMax")
    @Basic
    @XmlAttribute(name="SvrMax")
    public int getSvrmax() {
        return svrmax;
    }

    public void setSvrmax(int svrmax) {
        this.svrmax = svrmax;
    }

    private int rqdone;

    @Column(name = "rqDone")
    @Basic
    @XmlAttribute(name="RqDone")
    public int getRqdone() {
        return rqdone;
    }

    public void setRqdone(int rqdone) {
        this.rqdone = rqdone;
    }

    private String processid;

    @Column(name = "processId")
    @Basic
    @XmlAttribute(name="ProcessID")
    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    private String queuename;

    @Column(name = "queueName")
    @Basic
    @XmlAttribute(name="Queueid")
    public String getQueuename() {
        return queuename;
    }

    public void setQueuename(String queuename) {
        this.queuename = queuename;
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

        TuxsvrsEntity that = (TuxsvrsEntity) o;

        if (Float.compare(that.cpuuse, cpuuse) != 0) return false;
        if (id != that.id) return false;
        if (memoryuse != that.memoryuse) return false;
        if (rqdone != that.rqdone) return false;
        if (svrmax != that.svrmax) return false;
        if (svrmin != that.svrmin) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
        if (currenctsvc != null ? !currenctsvc.equals(that.currenctsvc) : that.currenctsvc != null) return false;
        if (processid != null ? !processid.equals(that.processid) : that.processid != null) return false;
        if (progname != null ? !progname.equals(that.progname) : that.progname != null) return false;
        if (queuename != null ? !queuename.equals(that.queuename) : that.queuename != null) return false;
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
        result = 31 * result + memoryuse;
        result = 31 * result + (cpuuse != +0.0f ? Float.floatToIntBits(cpuuse) : 0);
        result = 31 * result + (currenctsvc != null ? currenctsvc.hashCode() : 0);
        result = 31 * result + svrmin;
        result = 31 * result + svrmax;
        result = 31 * result + rqdone;
        result = 31 * result + (processid != null ? processid.hashCode() : 0);
        result = 31 * result + (queuename != null ? queuename.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
