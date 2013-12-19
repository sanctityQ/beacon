package com.fusionspy.beacon.site.wls.entity;
// Generated 2013-9-20 23:23:18 by One Data Tools 1.0.0


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * WlsJms.
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "ge_monitor_wls_jms")
public class WlsJms implements java.io.Serializable {

    /**
     */
    @XmlTransient
    private Integer id;
    /**
     * 站点名称
     */
    @XmlTransient
    private String siteName;
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
     * 名称.
     */
    @XmlAttribute(name = "Name")
    private String name;
    /**
     * 当前字节数.
     */
    @XmlAttribute(name = "BytesCurrentCount")
    private Integer bytesCurrentCount;
    /**
     * 最高字节数.
     */
    @XmlAttribute(name = "BytesHighCount")
    private Integer bytesHighCount;
    /**
     * 挂起字节数.
     */
    @XmlAttribute(name = "BytesPendingCount")
    private Integer bytesPendingCount;
    /**
     * 接收字节数.
     */
    @XmlAttribute(name = "BytesReceivedCount")
    private Integer bytesReceivedCount;
    /**
     * 当前消息数.
     */
    @XmlAttribute(name = "MessagesCurrentCount")
    private Integer messagesCurrentCount;
    /**
     * 最高消息数.
     */
    @XmlAttribute(name = "MessagesHighCount")
    private Integer messagesHighCount;
    /**
     * 挂起消息数.
     */
    @XmlAttribute(name = "MessagesPendingCount")
    private Integer messagesPendingCount;
    /**
     * 接收消息数.
     */
    @XmlAttribute(name = "MessagesReceivedCount")
    private Integer messagesReceivedCount;

    public WlsJms() {
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "site_name")
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rec_time", length = 19)
    public Date getRecTime() {
        return this.recTime;
    }

    public void setRecTime(Date recTime) {
        this.recTime = recTime;
    }

    @Column(name = "server_name")
    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "bytes_current_count")
    public Integer getBytesCurrentCount() {
        return this.bytesCurrentCount;
    }

    public void setBytesCurrentCount(Integer bytesCurrentCount) {
        this.bytesCurrentCount = bytesCurrentCount;
    }

    @Column(name = "bytes_high_count")
    public Integer getBytesHighCount() {
        return this.bytesHighCount;
    }

    public void setBytesHighCount(Integer bytesHighCount) {
        this.bytesHighCount = bytesHighCount;
    }

    @Column(name = "bytes_pending_count")
    public Integer getBytesPendingCount() {
        return this.bytesPendingCount;
    }

    public void setBytesPendingCount(Integer bytesPendingCount) {
        this.bytesPendingCount = bytesPendingCount;
    }

    @Column(name = "bytes_received_count")
    public Integer getBytesReceivedCount() {
        return this.bytesReceivedCount;
    }

    public void setBytesReceivedCount(Integer bytesReceivedCount) {
        this.bytesReceivedCount = bytesReceivedCount;
    }

    @Column(name = "messages_current_count")
    public Integer getMessagesCurrentCount() {
        return this.messagesCurrentCount;
    }

    public void setMessagesCurrentCount(Integer messagesCurrentCount) {
        this.messagesCurrentCount = messagesCurrentCount;
    }

    @Column(name = "messages_high_count")
    public Integer getMessagesHighCount() {
        return this.messagesHighCount;
    }

    public void setMessagesHighCount(Integer messagesHighCount) {
        this.messagesHighCount = messagesHighCount;
    }

    @Column(name = "messages_pending_count")
    public Integer getMessagesPendingCount() {
        return this.messagesPendingCount;
    }

    public void setMessagesPendingCount(Integer messagesPendingCount) {
        this.messagesPendingCount = messagesPendingCount;
    }

    @Column(name = "messages_received_count")
    public Integer getMessagesReceivedCount() {
        return this.messagesReceivedCount;
    }

    public void setMessagesReceivedCount(Integer messagesReceivedCount) {
        this.messagesReceivedCount = messagesReceivedCount;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}


