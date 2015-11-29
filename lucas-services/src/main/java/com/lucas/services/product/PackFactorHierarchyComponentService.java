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
package com.lucas.services.product;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.product.PackFactorHierarchyComponent;
import com.lucas.services.search.SearchAndSortCriteria;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * Service class for pack factor hierarchy component entity object
 * @author DiepLe
 *
 */


public interface PackFactorHierarchyComponentService {

    List<PackFactorHierarchyComponent> getPackFactorHierarchyComponentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws JsonParseException, JsonMappingException, IOException, ParseException;


}
