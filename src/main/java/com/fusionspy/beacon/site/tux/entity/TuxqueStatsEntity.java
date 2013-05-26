package com.fusionspy.beacon.site.tux.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Tuxque Stats
 * User: qc
 * Date: 11-9-16
 * Time: 上午11:20
 */
@Table(name = "GE_MONITOR_TUX_QUE_STATS")
@Entity
public class TuxqueStatsEntity {
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

    private int queuenum;

    @Column(name = "queueNum")
    @Basic
    public int getQueuenum() {
        return queuenum;
    }

    public void setQueuenum(int queuenum) {
        this.queuenum = queuenum;
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

        TuxqueStatsEntity that = (TuxqueStatsEntity) o;

        if (id != that.id) return false;
        if (queuenum != that.queuenum) return false;
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
        result = 31 * result + queuenum;
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}
