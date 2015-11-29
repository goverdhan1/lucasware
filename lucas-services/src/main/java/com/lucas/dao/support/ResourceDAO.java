/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.support;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;

/**
 * This class is for all objects that persist in the resourceDomain
 * Placeholder in case there are more than one domains in the app
 * @author Pankaj Tandon
 *
 * @param <DOMAINTYPE>
 */
public class ResourceDAO<DOMAINTYPE> extends DefaultGenericDAO<DOMAINTYPE> {
	
	@PersistenceContext (unitName = "resourceDomain")
	@Transient
	private transient EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
