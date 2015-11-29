/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.user.Permission;

@Repository
public class PermissionDAOImpl extends ResourceDAO<Permission> implements
		PermissionDAO {
	
	@Override
	public List<Permission> getAllPermissions() {
		
		// Start a hiberate session and return the all the permissions from the database
		Session session = getSession();
		Criteria criteria = session.createCriteria(Permission.class);
	    return criteria.list();
	}

	@Override
	public List<Permission> getPermission(List<Long> permissionId) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Permission.class);
		criteria.add(Restrictions.in("permissionId", permissionId.toArray()));
		return criteria.list();
	}

	@Override
	public List<Permission> getUserPermissionsByPermissionGroupName(String permissionGroupName) {

		Session session = getSession();

		Criteria criteria = session.createCriteria(Permission.class);

		criteria.createAlias("permissionGroupSet", "pg");
		criteria.add(Restrictions.eq("pg.permissionGroupName",
				permissionGroupName));
		return criteria.list();
	}

	@Override
	public Permission getPermissionByName(String permissionName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Permission.class);

		criteria.add(Restrictions.eq("permissionName", permissionName));

		Permission permission = (Permission) criteria.uniqueResult();

		return permission;
	}

}
