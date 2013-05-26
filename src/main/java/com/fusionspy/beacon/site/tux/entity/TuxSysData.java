package com.fusionspy.beacon.site.tux.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * tux intime data SYSTEM node
 * User: qc
 * Date: 11-9-19
 * Time: 下午4:05
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class TuxSysData {

    private String coreFind;

    private String errorFind;

    private String warnFind;

    private String largeFile;

    private String freeMem;

    private int idleCPU;

    private String svrCnt;

    private String queCnt;

    private String cltCnt;

    @XmlAttribute(name="CoreFind")
    public String getCoreFind() {
        return coreFind;
    }

    public void setCoreFind(String coreFind) {
        this.coreFind = coreFind;
    }

    @XmlAttribute(name="ErrorFind")
    public String getErrorFind() {
        return errorFind;
    }

    public void setErrorFind(String errorFind) {
        this.errorFind = errorFind;
    }

    @XmlAttribute(name="WarnFind")
    public String getWarnFind() {
        return warnFind;
    }

    public void setWarnFind(String warnFind) {
        this.warnFind = warnFind;
    }

    @XmlAttribute(name="LargeFile")
    public String getLargeFile() {
        return largeFile;
    }

    public void setLargeFile(String largeFile) {
        this.largeFile = largeFile;
    }

    @XmlAttribute(name="FreeMem")
    public String getFreeMem() {
        return freeMem;
    }

    public void setFreeMem(String freeMem) {
        this.freeMem = freeMem;
    }

    @XmlAttribute(name="IdleCPU")
    public int getIdleCPU() {
        return idleCPU;
    }

    public void setIdleCPU(int idleCPU) {
        this.idleCPU = idleCPU;
    }

    @XmlAttribute(name="SvrCnt")
    public String getSvrCnt() {
        return svrCnt;
    }

    public void setSvrCnt(String svrCnt) {
        this.svrCnt = svrCnt;
    }

    @XmlAttribute(name="QueCnt")
    public String getQueCnt() {
        return queCnt;
    }

    public void setQueCnt(String queCnt) {
        this.queCnt = queCnt;
    }

    @XmlAttribute(name="CltCnt")
    public String getCltCnt() {
        return cltCnt;
    }

    public void setCltCnt(String cltCnt) {
        this.cltCnt = cltCnt;
    }
}
