/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.product;

import java.text.ParseException;
import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.SpecialInstructions;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.user.User;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.util.CollectionsUtilService;




@Repository
public class ProductDAOImpl extends ResourceDAO<Product> implements ProductDAO {

	@Override
    @Transactional(readOnly = true)
	public List<Product> getProductListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        final List<Product> productList = this.getBySearchAndSortCriteria(searchAndSortCriteria);
        return productList;
	}

	@Override
    @Transactional(readOnly = true)
	public List<SpecialInstructions> getSpecialInstructionsListForProduct(String productName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
        criteria.add(Restrictions.eq("name", productName));
		Product product =  (Product) criteria.uniqueResult();
		List<SpecialInstructions> specialInstructionsList = CollectionsUtilService.nullGuard(product.getSpecialInstructionsList());
		return specialInstructionsList;
	}


}
