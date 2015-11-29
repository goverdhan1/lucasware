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

import com.lucas.entity.product.PackFactorHierarchy;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Functionality test for pack factor hierarchy objects
 * @author DiepLe
 */
public class PackFactorHierarchyServiceFunctionalTests extends AbstractSpringFunctionalTests {

    private static final String HIERARCHY_NAME_1 = "Frozen Foods 5 lbs";
    private static final String HIERARCHY_NAME_2 = "Ready to Eat Salads";

    @Inject
    private PackFactorHierarchyService packFactorHierarchyService;

    @Test
    public void testPackFactorHierarchyService() throws IOException, ParseException {
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("pfHierarchyName", new ArrayList<String>() {
            {
                add(HIERARCHY_NAME_1);
                add(HIERARCHY_NAME_2);
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("pfHierarchyName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        final List<PackFactorHierarchy> hierarchyNameList = packFactorHierarchyService.getPackFactorHierarchyListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(hierarchyNameList,"Hierarchy name list is Null");
    }
}
