package com.fusionspy.beacon.account.model;
// Generated 2013-1-8 17:51:26 by One Data Tools 1.0.0


import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.google.common.base.Strings;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.annotations.*;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Account.

 */
@Entity
@Table(name="GE_MONITOR_ACCOUNT")
public class Account  implements java.io.Serializable {

    /**
    * 序号.
    */
    private String id;
    /**
    * 登陆名.
    */
    private String loginName;
    /**
    * 密码.
    */
    private String password;
    /**
        */
    private String email;
    /**
        */
    private String phone;
    /**
        */
    private String status;
    
    private String statusStr;
    /**
    * 用户名
.
    */
    private String name;
    /**
        */
    private Date createTime;

	private String createTimeStr;

    private String operation="<a  href='javascript:void(0)' onclick='updRow(this)' class='eid'>编辑</a> <a href='javascript:void(0)' class='del' onclick='delRow(this)'>删除</a>";
    public Account() {
    }

	@Transient
    public String getOperation() {
		return operation;
	}

	public Account(String id, String loginName, String status) {
        this.id = id;
        this.loginName = loginName;
        this.status = status;
    }
   
    @Id 
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID", unique=true, length=32)
    public String getId() {
    return this.id;
    }

    public void setId(String id) {
    this.id = id;
    }
    
    @Column(name="LOGIN_NAME", length=50)
    public String getLoginName() {
    return this.loginName;
    }

    public void setLoginName(String loginName) {
    this.loginName = loginName;
    }
    
    @Column(name="PASSWORD", length=50)
    public String getPassword() {
    return this.password;
    }

    public void setPassword(String password) {
    this.password = password;
    }
    
    @Column(name="EMAIL", length=100)
    public String getEmail() {
    return this.email;
    }

    public void setEmail(String email) {
    this.email = Strings.nullToEmpty(email);
    }
    
    @Column(name="PHONE", length=30)
    public String getPhone() {
    return this.phone;
    }

    public void setPhone(String phone) {
    this.phone = Strings.nullToEmpty(phone);
    }
    
    @Column(name="STATUS", length=1)
    public String getStatus() {
    return this.status;
    }

    public void setStatus(String status) {
	    this.status = status;
	    if("1".equals(status)) {
	    	this.statusStr =  "正常";
	    } else if("0".equals(status)) {
	    	this.statusStr = "锁定";
	    }
    }
    
    @Column(name="NAME", length=50)
    public String getName() {
    return this.name;
    }

    public void setName(String name) {
    this.name = Strings.nullToEmpty(name);
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
    return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
	    createTimeStr = DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
    }

	@Transient
	public String getCreateTimeStr() {
		return createTimeStr;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Transient
	public String getStatusStr() {
		return statusStr;
	}


    private List<Group> groupList = newArrayList();// 有序的关联对象集合

    // 多对多定义
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "GE_MONITOR_ACCOUNT_GROUP", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "group_id") })
    // Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    // 集合按id排序.
    @OrderBy("id")
    // 集合中对象id的缓存.
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

}


