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

package com.lucas.dao.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.ProductCustomClassification;
import com.lucas.services.util.CollectionsUtilService;

/**
 * DAO implementation class for Product Custom Classification entity object
 * 
 * @author Ajay Gabriel
 *
 */
@Repository
public class ProductCustomClassificationDAOImpl extends ResourceDAO<ProductCustomClassification> implements ProductCustomClassificationDAO {

  /**
   * This method fires the query to lucasdb to get all the Product Custom Classification data
   * @see com.lucas.dao.product.ProductCustomClassificationDAO#getAllProductCustomClassification()
   */
  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = true)
  public List<ProductCustomClassification> getAllProductCustomClassification() throws ParseException {

    // Start a hiberate session and return the all the
    // ProductCustomClassification from the database
    Session session = getSession();
    Criteria criteria = session.createCriteria(ProductCustomClassification.class);
    final List<ProductCustomClassification> productCustomClassificationList = (List<ProductCustomClassification>) criteria.list();
    return productCustomClassificationList;
  }

  
  /**
   * This method fires the query to lucasdb to get the Product Custom Classification based on custom classification field name.
   * @see com.lucas.dao.product.ProductCustomClassificationDAO#getProductCustomClassificationByCustomClassificationFieldName(java.lang.String)
   */
  @Override
  @Transactional(readOnly = true)
  public ProductCustomClassification getProductCustomClassificationByCustomClassificationFieldName(String customClassificationFieldName) {
    // Start a hiberate session and return the all the
    // ProductCustomClassification from the database
    Session session = getSession();
    Criteria criteria = session.createCriteria(ProductCustomClassification.class);
    criteria.add(Restrictions.eq("customClassificationFieldName", customClassificationFieldName));
    ProductCustomClassification productCustomClassification = (ProductCustomClassification) criteria.uniqueResult();
    
    return productCustomClassification;
  }
}
