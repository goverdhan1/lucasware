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

import com.lucas.entity.product.PackFactorComponent;
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
 * Functionality test for pack factor components
 * @author DiepLe
 */
public class PackFactorComponentServiceFunctionalTests extends AbstractSpringFunctionalTests {

    private static final String COMPONENT_NAME_1 = "each";
    private static final String COMPONENT_NAME_2 = "case";
    private static final String COMPONENT_NAME_3 = "pallet";

    @Inject
    private PackFactorComponentService packFactorComponentService;

    @Test
    public void testPackFactorComponentService() throws IOException, ParseException {
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("pfComponentName", new ArrayList<String>() {
            {
                add(COMPONENT_NAME_1);
                add(COMPONENT_NAME_2);
                add(COMPONENT_NAME_3);
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("pfComponentName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);
        final List<PackFactorComponent> componentList = packFactorComponentService.getPackFactorComponentListBySearchAndSortCriteria(searchAndSortCriteria);
        Assert.notNull(componentList,"Component List is Null");
    }
}
