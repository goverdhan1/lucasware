/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucas.entity.BaseEntity;
import com.lucas.entity.reporting.Printable;
import com.lucas.entity.support.Identifiable;
import com.lucas.entity.support.MultiEditable;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "product")
public class Product extends BaseEntity implements  Identifiable<Long>, Printable, MultiEditable<Product> {

    private Long productId;
    private String productCode;
    private String description;
    private String productStatusCode;
    private String name;
    private Boolean performSecondValidation;
    private Long secondValidationEvery;
    private String secondValidationEveryPfLevel;
    private Long secondValidationCounter;
    private Boolean captureSerialNumber;
    private Boolean captureLotNumber;
    private Boolean isBaseItem;
    private Long baseItemThreshold;
    private String baseItemThresholdPfLevel;
    private Boolean captureCatchWeight;
    private Boolean enforceCatchWeightRange;
    private Double overrideItemWeight;
    private Double targetMaxFactor;
    private Double targetMinFactor;
    private Double absoluteMaxFactor;
    private Double absoluteMinFactor;
    private Boolean displayAsFactor;
    private Boolean captureExpirationDate;
    private Boolean enforceExpirationDateRange;
    private Integer expirationLeadTimeDays;
    private Boolean expirationUseInternationalDtFormat;
    private String customerSkuNbr;
    private String manufacturerItemNbr;
    private Boolean hazardousMaterial;
    private String imageUIrl;
    private Long importId;
    private Date importDate;
    private String customClassification1;
    private String customClassification2;
    private String customClassification3;
    private String customClassification4;
    private String customClassification5;
    private String customClassification6;
    private String customClassification7;
    private String customClassification8;
    private String customClassification9;
    private String customClassification10;
    private List<SpecialInstructions> specialInstructionsList = new ArrayList<SpecialInstructions>();


    public Product() {
    }


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    @JsonProperty("id")
    @JsonIgnore
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Column(name = "product_code", length = 50)
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "description", length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "product_status_code", length = 50)
    public String getProductStatusCode() {
        return productStatusCode;
    }

    public void setProductStatusCode(String productStatusCode) {
        this.productStatusCode = productStatusCode;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "perform_2nd_validation", columnDefinition = "boolean default false" , nullable = false)
    public Boolean getPerformSecondValidation() {
        return performSecondValidation;
    }

    public void setPerformSecondValidation(Boolean performSecondValidation) {
        this.performSecondValidation = performSecondValidation;
    }

    @Column(name = "2nd_validate_every", length = 20)
    public Long getSecondValidationEvery() {
        return secondValidationEvery;
    }

    public void setSecondValidationEvery(Long secondValidationEvery) {
        this.secondValidationEvery = secondValidationEvery;
    }

    @Column(name = "2nd_validate_every_pf_level", length = 50)
    public String getSecondValidationEveryPfLevel() {
        return secondValidationEveryPfLevel;
    }

    public void setSecondValidationEveryPfLevel(String secondValidationEveryPfLevel) {
        this.secondValidationEveryPfLevel = secondValidationEveryPfLevel;
    }

    @Column(name = "2nd_validation_counter", length = 20)
    public Long getSecondValidationCounter() {
        return secondValidationCounter;
    }

    public void setSecondValidationCounter(Long secondValidationCounter) {
        this.secondValidationCounter = secondValidationCounter;
    }

    @Column(name = "capture_serial_number",columnDefinition = "boolean default false" , nullable = false)
    public Boolean getCaptureSerialNumber() {
        return captureSerialNumber;
    }

    public void setCaptureSerialNumber(Boolean captureSerialNumber) {
        this.captureSerialNumber = captureSerialNumber;
    }

    @Column(name = "capture_lot_number", columnDefinition = "boolean default false" ,nullable = false)
    public Boolean getCaptureLotNumber() {
        return captureLotNumber;
    }

    public void setCaptureLotNumber(Boolean captureLotNumber) {
        this.captureLotNumber = captureLotNumber;
    }

    @Column(name = "is_base_item",columnDefinition = "boolean default false" , nullable = false)
    public Boolean getIsBaseItem() {
        return isBaseItem;
    }

    public void setIsBaseItem(Boolean isBaseItem) {
        this.isBaseItem = isBaseItem;
    }

    @Column(name = "base_item_threshold", length = 20)
    public Long getBaseItemThreshold() {
        return baseItemThreshold;
    }

    public void setBaseItemThreshold(Long baseItemThreshold) {
        this.baseItemThreshold = baseItemThreshold;
    }

    @Column(name = "base_item_threshold_pf_level", length = 50)
    public String getBaseItemThresholdPfLevel() {
        return baseItemThresholdPfLevel;
    }

    public void setBaseItemThresholdPfLevel(String baseItemThresholdPfLevel) {
        this.baseItemThresholdPfLevel = baseItemThresholdPfLevel;
    }

    @Column(name = "capture_catch_weight",columnDefinition = "boolean default false" , nullable = false)
    public Boolean getCaptureCatchWeight() {
        return captureCatchWeight;
    }

    public void setCaptureCatchWeight(Boolean captureCatchWeight) {
        this.captureCatchWeight = captureCatchWeight;
    }

    @Column(name = "enforce_catch_weight_range",columnDefinition = "boolean default false" , nullable = false)
    public Boolean getEnforceCatchWeightRange() {
        return enforceCatchWeightRange;
    }

    public void setEnforceCatchWeightRange(Boolean enforceCatchWeightRange) {
        this.enforceCatchWeightRange = enforceCatchWeightRange;
    }

    @Column(name = "override_item_weight", length = 20, precision = 3)
    public Double getOverrideItemWeight() {
        return overrideItemWeight;
    }

    public void setOverrideItemWeight(Double overrideItemWeight) {
        this.overrideItemWeight = overrideItemWeight;
    }

    @Column(name = "target_max_factor")
    public Double getTargetMaxFactor() {
        return targetMaxFactor;
    }

    public void setTargetMaxFactor(Double targetMaxFactor) {
        this.targetMaxFactor = targetMaxFactor;
    }

    @Column(name = "target_min_factor")
    public Double getTargetMinFactor() {
        return targetMinFactor;
    }

    public void setTargetMinFactor(Double targetMinFactor) {
        this.targetMinFactor = targetMinFactor;
    }

    @Column(name = "absolute_max_factor")
    public Double getAbsoluteMaxFactor() {
        return absoluteMaxFactor;
    }

    public void setAbsoluteMaxFactor(Double absoluteMaxFactor) {
        this.absoluteMaxFactor = absoluteMaxFactor;
    }

    @Column(name = "absolute_min_factor")
    public Double getAbsoluteMinFactor() {
        return absoluteMinFactor;
    }

    public void setAbsoluteMinFactor(Double absoluteMinFactor) {
        this.absoluteMinFactor = absoluteMinFactor;
    }

    @Column(name = "display_as_factor", columnDefinition = "boolean default false" ,nullable = false)
    public Boolean getDisplayAsFactor() {
        return displayAsFactor;
    }

    public void setDisplayAsFactor(Boolean displayAsFactor) {
        this.displayAsFactor = displayAsFactor;
    }

    @Column(name = "capture_expiration_date", columnDefinition = "boolean default false" ,nullable = false)
    public Boolean getCaptureExpirationDate() {
        return captureExpirationDate;
    }

    public void setCaptureExpirationDate(Boolean captureExpirationDate) {
        this.captureExpirationDate = captureExpirationDate;
    }

    @Column(name = "enforce_expiration_date_range", columnDefinition = "boolean default false" ,nullable = false)
    public Boolean getEnforceExpirationDateRange() {
        return enforceExpirationDateRange;
    }

    public void setEnforceExpirationDateRange(Boolean enforceExpirationDateRange) {
        this.enforceExpirationDateRange = enforceExpirationDateRange;
    }

    @Column(name = "expiration_lead_time_days", length = 10)
    public Integer getExpirationLeadTimeDays() {
        return expirationLeadTimeDays;
    }

    public void setExpirationLeadTimeDays(Integer expirationLeadTimeDays) {
        this.expirationLeadTimeDays = expirationLeadTimeDays;
    }

    @Column(name = "expiration_use_international_dt_format", columnDefinition = "boolean default false" ,nullable = false)
    public Boolean getExpirationUseInternationalDtFormat() {
        return expirationUseInternationalDtFormat;
    }

    public void setExpirationUseInternationalDtFormat(Boolean expirationUseInternationalDtFormat) {
        this.expirationUseInternationalDtFormat = expirationUseInternationalDtFormat;
    }

    @Column(name = "customer_SKU_nbr", length = 50)
    public String getCustomerSkuNbr() {
        return customerSkuNbr;
    }

    public void setCustomerSkuNbr(String customerSkuNbr) {
        this.customerSkuNbr = customerSkuNbr;
    }

    @Column(name = "manufacturer_item_nbr", length = 50)
    public String getManufacturerItemNbr() {
        return manufacturerItemNbr;
    }

    public void setManufacturerItemNbr(String manufacturerItemNbr) {
        this.manufacturerItemNbr = manufacturerItemNbr;
    }

    @Column(name = "haz_material", columnDefinition = "boolean default false" ,nullable = false)
    public Boolean getHazardousMaterial() {
        return hazardousMaterial;
    }

    public void setHazardousMaterial(Boolean hazardousMaterial) {
        this.hazardousMaterial = hazardousMaterial;
    }

    @Column(name = "image_url",columnDefinition="LONGTEXT")
    public String getImageUIrl() {
        return imageUIrl;
    }

    public void setImageUIrl(String imageUIrl) {
        this.imageUIrl = imageUIrl;
    }

    @Column(name = "import_id", length = 20)
    public Long getImportId() {
        return importId;
    }

    public void setImportId(Long importId) {
        this.importId = importId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "import_date",columnDefinition = "TIMESTAMP")
    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    @Column(name = "custom_classification_1",length = 100)
    public String getCustomClassification1() {
        return customClassification1;
    }

    public void setCustomClassification1(String customClassification1) {
        this.customClassification1 = customClassification1;
    }

    @Column(name = "custom_classification_2",length = 100)
    public String getCustomClassification2() {
        return customClassification2;
    }

    public void setCustomClassification2(String customClassification2) {
        this.customClassification2 = customClassification2;
    }

    @Column(name = "custom_classification_3",length = 100)
    public String getCustomClassification3() {
        return customClassification3;
    }

    public void setCustomClassification3(String customClassification3) {
        this.customClassification3 = customClassification3;
    }

    @Column(name = "custom_classification_4",length = 100)
    public String getCustomClassification4() {
        return customClassification4;
    }

    public void setCustomClassification4(String customClassification4) {
        this.customClassification4 = customClassification4;
    }

    @Column(name = "custom_classification_5",length = 100)
    public String getCustomClassification5() {
        return customClassification5;
    }

    public void setCustomClassification5(String customClassification5) {
        this.customClassification5 = customClassification5;
    }

    @Column(name = "custom_classification_6",length = 100)
    public String getCustomClassification6() {
        return customClassification6;
    }

    public void setCustomClassification6(String customClassification6) {
        this.customClassification6 = customClassification6;
    }

    @Column(name = "custom_classification_7",length = 100)
    public String getCustomClassification7() {
        return customClassification7;
    }

    public void setCustomClassification7(String customClassification7) {
        this.customClassification7 = customClassification7;
    }

    @Column(name = "custom_classification_8",length = 100)
    public String getCustomClassification8() {
        return customClassification8;
    }

    public void setCustomClassification8(String customClassification8) {
        this.customClassification8 = customClassification8;
    }

    @Column(name = "custom_classification_9",length = 100)
    public String getCustomClassification9() {
        return customClassification9;
    }

    public void setCustomClassification9(String customClassification9) {
        this.customClassification9 = customClassification9;
    }

    @Column(name = "custom_classification_10",length = 100)
    public String getCustomClassification10() {
        return customClassification10;
    }

    public void setCustomClassification10(String customClassification10) {
        this.customClassification10 = customClassification10;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<SpecialInstructions> getSpecialInstructionsList() {
        return specialInstructionsList;
    }

    public void setSpecialInstructionsList(List<SpecialInstructions> specialInstructionsList) {
        this.specialInstructionsList = specialInstructionsList;
    }


    @JsonIgnore
    @Transient
    @Override
    public Long getId() {
        return this.productId;
    }


    @Override
    public void setId(Long id) {
        this.setProductId(id);

    }


    @Transient
    @Override
    public List<String> getHeaderElements() {
        final ArrayList<String> productHeaderList = new ArrayList<String>() {
            private static final long serialVersionUID = 1L;

            {
                add(getHeaderText("productCode"));
                add(getHeaderText("name"));
                add(getHeaderText("description"));
                add(getHeaderText("productStatusCode"));
                add(getHeaderText("performSecondValidation"));
                add(getHeaderText("captureLotNumber"));
                add(getHeaderText("captureSerialNumber"));
                add(getHeaderText("isBaseItem"));
                add(getHeaderText("captureCatchWeight"));
                add(getHeaderText("specialInstructionsList"));
                add(getHeaderText("hazardousMaterial"));
                add(getHeaderText("customerSkuNbr"));
                add(getHeaderText("manufacturerItemNbr"));
                add(getHeaderText("captureExpirationDate"));
            }

            ;
        };
        return productHeaderList;
    }


    @Transient
    @Override
    public List<String> getDataElements() {
        final ArrayList<String> productHeaderList = new ArrayList<String>() {
            private static final long serialVersionUID = 1L;

            {
                add(getHeaderText("productCode"));
                add(getHeaderText("name"));
                add(getHeaderText("description"));
                add(getHeaderText("productStatusCode"));
                add(getHeaderText("performSecondValidation"));
                add(getHeaderText("captureLotNumber"));
                add(getHeaderText("captureSerialNumber"));
                add(getHeaderText("isBaseItem"));
                add(getHeaderText("captureCatchWeight"));
                add(getHeaderText("specialInstructionsList"));
                add(getHeaderText("hazardousMaterial"));
                add(getHeaderText("customerSkuNbr"));
                add(getHeaderText("manufacturerItemNbr"));
                add(getHeaderText("captureExpirationDate"));
            }

            ;
        };
        return productHeaderList;
    }

    @Transient
    @Override
    public List<String> getMultiEditableFieldsList() {
        // TODO , to be taken up when the multi editable functionality is to be supported //TDM
        return null;
    }

    @Transient
    private String getHeaderText(String name) {
        final String heading[] = StringUtils.splitByCharacterTypeCamelCase(name);
        final StringBuilder colHeading = new StringBuilder();
        for (String word : heading) {
            colHeading.append(" " + (StringUtils.capitalize(word)));
        }
        return colHeading.toString();
    }
}
