/**
 * Copyright (c) Lucas Systems LLC
 */

package com.lucas.entity.product;

import com.lucas.entity.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "special_instructions")
public class SpecialInstructions  extends BaseEntity {

    private Long specialInstructionId;
    private String specialInstruction;
    private Product product;
    private AlertLocations alertLocations;
    private ResponseType  responseType;

    public SpecialInstructions() {
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "special_instruction_id")
    public Long getSpecialInstructionId() {
        return specialInstructionId;
    }
    public void setSpecialInstructionId(Long specialInstructionId) {
        this.specialInstructionId = specialInstructionId;
    }

    @Column(name = "special_instruction", unique = true )
    public String getSpecialInstruction() {
        return specialInstruction;
    }
    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_locations_id",referencedColumnName = "alert_locations_id")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    public AlertLocations getAlertLocations() {
        return alertLocations;
    }
    public void setAlertLocations(AlertLocations alertLocations) {
        this.alertLocations = alertLocations;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "response_type", length = 50)
    public ResponseType getResponseType() {
        return responseType;
    }
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("specialInstruction", specialInstruction).toString();
    }
}
