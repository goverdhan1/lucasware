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

package com.lucas.entity.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lucas.entity.BaseEntity;

/**
 * Product Custom Classification entity object
 * 
 * @author Ajay Gabriel
 *
 */

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "product_custom_classification", uniqueConstraints = @UniqueConstraint(columnNames = "custom_classification_name"))
public class ProductCustomClassification extends BaseEntity {

  private static final long serialVersionUID = 1L;
  
  private String customClassificationFieldName;
  private String customClassificationName;
  private String customClassificationDefinition;

  public ProductCustomClassification() {}

  @Id
  @NotNull
  @Column(name = "custom_classification_field_name", length = 50)
  public String getCustomClassificationFieldName() {
    return this.customClassificationFieldName;
  }

  public void setCustomClassificationFieldName(String customClassificationFieldName) {
    this.customClassificationFieldName = customClassificationFieldName;
  }

  @Column(name = "custom_classification_name", unique = true, length = 50)
  public String getCustomClassificationName() {
    return this.customClassificationName;
  }

  public void setCustomClassificationName(String customClassificationName) {
    this.customClassificationName = customClassificationName;
  }

  @Column(name = "custom_classification_definition")
  public String getCustomClassificationDefinition() {
    return this.customClassificationDefinition;
  }

  public void setCustomClassificationDefinition(String customClassificationDefinition) {
    this.customClassificationDefinition = customClassificationDefinition;
  }

  /*
   * This method return the hashcode representation of Product Custom Classification entity attributes.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((customClassificationDefinition == null) ? 0 : customClassificationDefinition.hashCode());
    result = prime * result + ((customClassificationFieldName == null) ? 0 : customClassificationFieldName.hashCode());
    result = prime * result + ((customClassificationName == null) ? 0 : customClassificationName.hashCode());
    return result;
  }

  /*
   * This method return the equal representation of Product Custom Classification entity attributes.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof ProductCustomClassification)) {
      return false;
    }
    ProductCustomClassification other = (ProductCustomClassification) obj;
    if (customClassificationDefinition == null) {
      if (other.customClassificationDefinition != null) {
        return false;
      }
    } else if (!customClassificationDefinition.equals(other.customClassificationDefinition)) {
      return false;
    }
    if (customClassificationFieldName == null) {
      if (other.customClassificationFieldName != null) {
        return false;
      }
    } else if (!customClassificationFieldName.equals(other.customClassificationFieldName)) {
      return false;
    }
    if (customClassificationName == null) {
      if (other.customClassificationName != null) {
        return false;
      }
    } else if (!customClassificationName.equals(other.customClassificationName)) {
      return false;
    }
    return true;
  }

  /*
   * This method return the string representation of Product Custom Classification entity
   */
  @Override
  public String toString() {
    return String.format("ProductCustomClassification [customClassificationFieldName=%s, customClassificationName=%s, customClassificationDefinition=%s]",
        customClassificationFieldName, customClassificationName, customClassificationDefinition);
  }

}
