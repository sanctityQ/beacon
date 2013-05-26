package com.fusionspy.beacon.site.tux.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Tux Resource Entity
 * User: qc
 * Date: 11-9-19
 * Time: 下午4:16
 */
@Table(name = "GE_MONITOR_TUX_RESOURCE")
@Entity
public class TuxresourceEntity {
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

    private String sitename;

    @Column(name = "siteName")
    @Basic
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

    private int tuxrunsvr;

    @Column(name = "tuxRunSvr")
    @Basic
    public int getTuxrunsvr() {
        return tuxrunsvr;
    }

    public void setTuxrunsvr(int tuxrunsvr) {
        this.tuxrunsvr = tuxrunsvr;
    }

    private int tuxrunqueue;

    @Column(name = "tuxRunQueue")
    @Basic
    public int getTuxrunqueue() {
        return tuxrunqueue;
    }

    public void setTuxrunqueue(int tuxrunqueue) {
        this.tuxrunqueue = tuxrunqueue;
    }

    private int tuxrunclt;

    @Column(name = "tuxRunClt")
    @Basic
    public int getTuxrunclt() {
        return tuxrunclt;
    }

    public void setTuxrunclt(int tuxrunclt) {
        this.tuxrunclt = tuxrunclt;
    }

    private int cpuidle;

    @Column(name = "cpuIdle")
    @Basic
    public int getCpuidle() {
        return cpuidle;
    }

    public void setCpuidle(int cpuidle) {
        this.cpuidle = cpuidle;
    }

    private float memfree;

    @Column(name = "memFree")
    @Basic
    public float getMemfree() {
        return memfree;
    }

    public void setMemfree(float memfree) {
        this.memfree = memfree;
    }

    private float allsvrcpuuse;

    @Column(name = "allSvrCpuUse")
    @Basic
    public float getAllsvrcpuuse() {
        return allsvrcpuuse;
    }

    public void setAllsvrcpuuse(float allsvrcpuuse) {
        this.allsvrcpuuse = allsvrcpuuse;
    }

    private int allsvrmemused;

    @Column(name = "allSvrMemUsed")
    @Basic
    public int getAllsvrmemused() {
        return allsvrmemused;
    }

    public void setAllsvrmemused(int allsvrmemused) {
        this.allsvrmemused = allsvrmemused;
    }

    private String createAt;

    @Column(name = "create_at")
    @Basic
    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

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

        TuxresourceEntity that = (TuxresourceEntity) o;

        if (Float.compare(that.allsvrcpuuse, allsvrcpuuse) != 0) return false;
        if (allsvrmemused != that.allsvrmemused) return false;
        if (cpuidle != that.cpuidle) return false;
        if (id != that.id) return false;
        if (Float.compare(that.memfree, memfree) != 0) return false;
        if (tuxrunclt != that.tuxrunclt) return false;
        if (tuxrunqueue != that.tuxrunqueue) return false;
        if (tuxrunsvr != that.tuxrunsvr) return false;
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
        result = 31 * result + tuxrunsvr;
        result = 31 * result + tuxrunqueue;
        result = 31 * result + tuxrunclt;
        result = 31 * result + cpuidle;
        result = 31 * result + (memfree != +0.0f ? Float.floatToIntBits(memfree) : 0);
        result = 31 * result + (allsvrcpuuse != +0.0f ? Float.floatToIntBits(allsvrcpuuse) : 0);
        result = 31 * result + allsvrmemused;
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
