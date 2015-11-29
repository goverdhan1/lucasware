/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.entity.BaseEntity;
import com.lucas.exception.LucasRuntimeException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "permission_group")
public class PermissionGroup extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3255284247058910779L;

	private Long permissionGroupId;

	private String permissionGroupName;

    private String permissionDescription;

	private List<Permission> permissionGroupPermissionList = new ArrayList<Permission>();

	Set<Permission> permissionsSet = new HashSet<Permission>();

	private Set<User> userSet = new HashSet<User>();

	// ctr's
	public PermissionGroup() {
	}

	public PermissionGroup(String permissionGroupName) {
		super();
		this.permissionGroupName = permissionGroupName;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "permission_group_id")
	@JsonProperty("id")
	@JsonIgnore
	public Long getPermissionGroupId() {
		return permissionGroupId;
	}

	public void setPermissionGroupId(Long permissionGroupId) {
		this.permissionGroupId = permissionGroupId;
	}

	@NotNull
	@Column(name = "permission_group_name", unique = true)
	public String getPermissionGroupName() {
		return permissionGroupName;
	}

	public void setPermissionGroupName(String permissionGroupName) {
		this.permissionGroupName = permissionGroupName;
	}

    @Column(name = "description", length = 100)
    public String getPermissionDescription() { return permissionDescription; }

    public void setPermissionDescription(String permissionDescription) { this.permissionDescription = permissionDescription; }

    // FK
	@ManyToMany(targetEntity = com.lucas.entity.user.User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "lucas_user_permission_group", joinColumns = { @JoinColumn(name = "permission_group_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	@JsonIgnore
	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	@Transient
	@JsonIgnore
	public Set<Permission> getPermissionsSet() throws LucasRuntimeException {

		permissionsSet.addAll(permissionGroupPermissionList);
		return permissionsSet;
	}

	public void setPermissionsSet(Set<Permission> permissionsSet) {
		this.permissionsSet = permissionsSet;
	}

	@ManyToMany(targetEntity = com.lucas.entity.user.Permission.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "permission_group_permission", joinColumns = { @JoinColumn(name = "permission_group_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<Permission> getPermissionGroupPermissionList() {
		return permissionGroupPermissionList;
	}

	public void setPermissionGroupPermissionList(
			List<Permission> permissionGroupPermissionList) {
		this.permissionGroupPermissionList = permissionGroupPermissionList;
	}

	public boolean equals(Object other) {
		if (this == other){
			return true;
		}
		if (!(other instanceof PermissionGroup)){
			return false;
		}
		PermissionGroup castOther = (PermissionGroup) other;
		return new EqualsBuilder().append(permissionGroupName,
				castOther.permissionGroupName).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(permissionGroupName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("permissionGroupName", permissionGroupName).toString();
	}

}
