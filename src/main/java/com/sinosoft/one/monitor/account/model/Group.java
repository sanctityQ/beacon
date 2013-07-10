package com.sinosoft.one.monitor.account.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 权限组.
 */
@Entity
@Table(name = "GE_MONITOR_GROUP")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group{

    protected Long id;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(generator = "system-uuid")
//	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	private String name;

	private List<String> permissionList = Lists.newArrayList();

	public Group() {
	}

	public Group(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
//	@ElementCollection(fetch = FetchType.EAGER)
//	@CollectionTable(name = "acct_group_permission", joinColumns = { @JoinColumn(name = "group_id") })
//	@Column(name = "permission")
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	public List<String> getPermissionList() {
//		return permissionList;
//	}
//
//	public void setPermissionList(List<String> permissionList) {
//		this.permissionList = permissionList;
//	}

//	@Transient
//	public String getPermissionNames() {
//		List<String> permissionNameList = Lists.newArrayList();
//		for (String permission : permissionList) {
//			permissionNameList.add(Permission.parse(permission).displayName);
//		}
//		return StringUtils.join(permissionNameList, ",");
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
