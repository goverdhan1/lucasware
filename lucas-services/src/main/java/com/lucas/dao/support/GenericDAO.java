/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

 */
package com.lucas.dao.support;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

public interface GenericDAO<DOMAINTYPE> {
	Session getSession();
	DOMAINTYPE save(Object data);
	DOMAINTYPE load(Object id);
	List<DOMAINTYPE> loadAll();
	void delete(DOMAINTYPE entity) ;
	void flush() ;
	void clear() ;
    List<DOMAINTYPE> findByCriteria(Criterion... criterion) ;
    public List<DOMAINTYPE> getBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)throws ParseException;
    public List<DOMAINTYPE> getNewBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, IllegalArgumentException, LucasRuntimeException;
	public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

    /**
     * getTotalCountForSearchAndSortCriteria() provide the specification for getting the total count of the
     * records fetch by the provided Criteria
     * @param criteria is accepted as the formal arguments for this method which contains the selection baseic
     *                 and sorting basics for records
     * @return the total number of records fetch by the criteria.
     * @throws ParseException
     */
    public Long getTotalCountForSearchAndSortCriteria(final Criteria criteria)throws ParseException;
    /**
     * Method will return the class type of given string key from the class 
     * @param aKey of type String
     * @return Type of the class
     */
    public Type getFieldType(String aKey);
}
