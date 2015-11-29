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
package com.lucas.alps.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.ProductCustomClassificationDetailsView;
import com.lucas.entity.product.ProductCustomClassification;

/**
 * This Class provide the implementation for the functionality of view for
 * ProductCustomClassification
 * 
 * @author Ajay Gabriel
 *
 */
public class ProductCustomClassificationView implements Serializable {

  private static final long serialVersionUID = 1L;

  private ProductCustomClassification productCustomClassification;


  public ProductCustomClassificationView() {
    productCustomClassification = new ProductCustomClassification();
  }

  /**
   * @param productCustomClassification
   */
  public ProductCustomClassificationView(ProductCustomClassification productCustomClassification) {
    this.productCustomClassification = productCustomClassification;
  }

  @JsonView({ProductCustomClassificationDetailsView.class})
  public String getCustomClassificationFieldName() {
    return this.productCustomClassification.getCustomClassificationFieldName();
  }

  public void setCustomClassificationFieldName(String customClassificationFieldName) {
    this.productCustomClassification.setCustomClassificationFieldName(customClassificationFieldName);
  }

  @JsonView({ProductCustomClassificationDetailsView.class})
  public String getCustomClassificationName() {
    return this.productCustomClassification.getCustomClassificationName();
  }

  public void setCustomClassificationName(String customClassificationName) {
    this.productCustomClassification.setCustomClassificationName(customClassificationName);
  }

  @SuppressWarnings("unchecked")
@JsonView({ProductCustomClassificationDetailsView.class})
  public Map<String, List<String>> getCustomClassificationDefinition() throws JsonParseException, JsonMappingException, IOException {
    if (this.productCustomClassification.getCustomClassificationDefinition() != null) {
      ObjectMapper om = new ObjectMapper();
      return om.readValue(this.productCustomClassification.getCustomClassificationDefinition(), HashMap.class);
    }
    return null;
  }

  public void setCustomClassificationDefinition(Map<String, List<String>> customClassificationDefinition) throws JsonProcessingException {
    if (this.productCustomClassification.getCustomClassificationDefinition() != null) {
      ObjectMapper om = new ObjectMapper();
      this.productCustomClassification.setCustomClassificationDefinition(om.writeValueAsString(customClassificationDefinition));
    }
  }

  public String getCreatedByUserName() {
    return this.productCustomClassification.getCreatedByUserName();
  }

  public void setCreatedByUserName(String createdByUserName) {
    this.productCustomClassification.setCreatedByUserName(createdByUserName);
  }

  public Date getCreatedDateTime() {
    return this.productCustomClassification.getCreatedDateTime();
  }

  public void setCreatedDateTime(Date createdDateTime) {
    this.productCustomClassification.setCreatedDateTime(createdDateTime);
  }

  public String getUpdatedByUserName() {
    return this.productCustomClassification.getUpdatedByUserName();
  }

  public void setUpdatedByUserName(String updatedByUserName) {
    this.productCustomClassification.setUpdatedByUserName(updatedByUserName);
  }

  public Date getUpdatedDateTime() {
    return this.productCustomClassification.getUpdatedDateTime();
  }

  public void setUpdatedDateTime(Date updatedDateTime) {
    this.productCustomClassification.setUpdatedDateTime(updatedDateTime);
  }
}
