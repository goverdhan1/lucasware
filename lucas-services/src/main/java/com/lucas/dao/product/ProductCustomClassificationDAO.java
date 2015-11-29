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
import java.util.List;

import com.lucas.entity.product.ProductCustomClassification;

/**
 * DAO class for Product Custom Classification entity object
 * 
 * @author Ajay Gabriel
 *
 */
public interface ProductCustomClassificationDAO {

  /**
   * This method return all the data for Product Custom Classification table from lucasdb.
   * 
   * @return List of ProductCustomClassification
   * @throws ParseException
   */
  public List<ProductCustomClassification> getAllProductCustomClassification() throws ParseException;

  /**
   * This method return ProductCustomClassification data for Product Custom Classification table
   * from lucasdb.
   * 
   * @param customClassificationFieldName
   * @return ProductCustomClassification
   */
  public ProductCustomClassification getProductCustomClassificationByCustomClassificationFieldName(String customClassificationFieldName);
}
