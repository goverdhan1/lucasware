/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.services.product;

import com.lucas.entity.product.Product;
import com.lucas.entity.product.ProductCustomClassification;
import com.lucas.services.product.ProductService;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import com.lucas.testsupport.AbstractSpringFunctionalTests;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Functionality test for Product and Product Custom Classification Created by Adarsh on 2/27/15.
 */
public class ProductServiceFunctionalTests extends AbstractSpringFunctionalTests {

  private static final String PRODUCTNAME_1 = "99-4937";
  private static final String PRODUCTNAME_2 = "99-4938";
  private static final String PRODUCTNAME_3 = "99-4939";

  @Inject
  private ProductService productService;

  @Test
  public void testProductService() throws IOException, ParseException {
    final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
    final Map<String, Object> searchMap = new HashMap<String, Object>();

    searchMap.put("name", new ArrayList<String>() {
      {
        add(PRODUCTNAME_1);
        add(PRODUCTNAME_2);
        add(PRODUCTNAME_3);
      }
    });


    searchAndSortCriteria.setSearchMap(searchMap);
    searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
      {
        put("name", SortType.ASC);
      }
    });
    searchAndSortCriteria.setPageNumber(0);
    searchAndSortCriteria.setPageSize(3);
    final List<Product> productList = productService.getProductListBySearchAndSortCriteria(searchAndSortCriteria);
    Assert.notNull(productList, "Product List is Null");
  }

  @Transactional(readOnly = true)
  @Test
  public void testGetAllProductCustomClassification() throws ParseException {

    final List<ProductCustomClassification> productCustomClassificationList = this.productService.getAllProductCustomClassification();

    Assert.notNull(productCustomClassificationList);
    Assert.notEmpty(productCustomClassificationList, "Product Custom Classification List is Empty");
  }

  @Transactional(readOnly = true)
  @Test
  public void testGetProductCustomClassificationByCustomClassificationFieldName() {

    final String CUSTOM_CLASSIFICATION_1 = "custom_classification_1";

    final ProductCustomClassification productCustomClassification = this.productService.getProductCustomClassificationByCustomClassificationFieldName(CUSTOM_CLASSIFICATION_1);

    Assert.notNull(productCustomClassification, "Product Custom Classification List is Null");
    Assert.isTrue(productCustomClassification.getCustomClassificationFieldName().equals(CUSTOM_CLASSIFICATION_1), "Product Custom Classification is not same as given custom classification field name");
  }
}
