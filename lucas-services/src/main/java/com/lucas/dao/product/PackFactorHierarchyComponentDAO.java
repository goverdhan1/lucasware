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
package com.lucas.dao.product;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.product.PackFactorComponent;
import com.lucas.entity.product.PackFactorHierarchyComponent;
import com.lucas.services.search.SearchAndSortCriteria;

import java.text.ParseException;
import java.util.List;

/**
 * DAO class for pack factor hierarchy component entity object
 * @author DiepLe
 *
 */


public interface PackFactorHierarchyComponentDAO extends GenericDAO<PackFactorHierarchyComponent> {

    public List<PackFactorHierarchyComponent> getPackFactorHierarchyComponentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;


}