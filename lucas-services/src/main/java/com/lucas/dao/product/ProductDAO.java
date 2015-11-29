/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.product;

import java.text.ParseException;
import java.util.List;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.SpecialInstructions;
import com.lucas.services.search.SearchAndSortCriteria;

public interface ProductDAO extends GenericDAO<Product> {

    public List<Product> getProductListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

	public List<SpecialInstructions> getSpecialInstructionsListForProduct(String productName);
	
}