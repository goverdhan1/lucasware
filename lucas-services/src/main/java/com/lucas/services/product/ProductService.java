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

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.ProductCustomClassification;
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;

public interface ProductService {

	// Other crud operations TDM
    List<Product> getFullyHydratedProductList(final List<Product> productList) throws JsonParseException, JsonMappingException, IOException;
	List<Product> getProductListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws JsonParseException, JsonMappingException, IOException, ParseException;

	/**
	 * This Service method return all the Product Custom Classification from DAO.
	 * @return List of ProductCustomClassification
	 * @throws ParseException
	 */
	List<ProductCustomClassification> getAllProductCustomClassification() throws ParseException;

	/**
	 * This method returns data for ProductCustomClassification with CustoClassificationFieldName.
	 * @param customClassificationFieldName
	 * @return ProductCustomClassification
	 */
	ProductCustomClassification getProductCustomClassificationByCustomClassificationFieldName(String customClassificationFieldName);
}
