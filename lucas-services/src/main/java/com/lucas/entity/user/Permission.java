/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, include = "all")
@Table(name = "permission")
public class Permission implements java.io.Serializable, Comparable<Permission> {

	private static final long serialVersionUID = -6090715515641436653L;

	private Long permissionId;

	private String permissionName;

	private String permissionCategory;
	
	private Long displayOrder;

	private Set<PermissionGroup> permissionGroupSet = new HashSet<PermissionGroup>();

	
	public Permission() {
	}

	public Permission(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public Permission(String permissionName, Long displayOrder) {
		this.permissionName = permissionName;
		this.displayOrder = displayOrder;
	}
	

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "permission_id")
	@JsonIgnore
	public Long getPermissionId() {
		return permissionId;
	}
	
	
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
		
	@NotNull
	@Column(name = "permission_name", unique = true)
	@JsonProperty("permissionName")
	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Column(name = "permission_category")
	@JsonIgnore
	public String getPermissionCategory() {
		return permissionCategory;
	}

	public void setPermissionCategory(String permissionCategory) {
		this.permissionCategory = permissionCategory;
	}

	@ManyToMany(mappedBy = "permissionGroupPermissionList", targetEntity = com.lucas.entity.user.PermissionGroup.class, cascade = {
		CascadeType.PERSIST, CascadeType.MERGE })
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public Set<PermissionGroup> getPermissionGroupSet() {
		return permissionGroupSet;
	}

	public void setPermissionGroupSet(Set<PermissionGroup> permissionGroupSet) {
		this.permissionGroupSet = permissionGroupSet;
	}
	
	@NotNull
	@Column(name = "display_order", length = 20)
	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public int compareTo(Permission p) {

		
		
		return (new CompareToBuilder()).append(this.permissionCategory,
				p.permissionCategory).append(this.displayOrder,
				p.displayOrder).toComparison();
	}
	
	public boolean equals(Object other) {
		if (this == other){
			return true;
		}
		if (!(other instanceof Permission)){
			return false;
		}
		Permission castOther = (Permission) other;
		return new EqualsBuilder().append(permissionName,
				castOther.permissionName).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(permissionName).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("permissionName", permissionName).toString();
	}

}
