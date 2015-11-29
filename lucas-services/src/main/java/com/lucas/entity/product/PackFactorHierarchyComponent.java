/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

*/
package com.lucas.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lucas.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Pack factor hierarchy component entity object
 * @author DiepLe
 *
 */

@JsonIgnoreProperties({"packFactorHierarchy", "packFactorComponent"})
@Entity
@Table(name = "pack_factor_hierarchy_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class PackFactorHierarchyComponent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long pfHierarchyId;
    private Long pfComponentId;
    private Long childPfComponentId;
    private Double factor;
    private Boolean bottomLevelFlag;
    private Long level;
    private Double defaultHeight;
    private Double defaultDepth;
    private Double defaultWeight;
    private Double defaultCube;
    private PackFactorHierarchy packFactorHierarchy;
    private PackFactorComponent packFactorComponent;

    public PackFactorHierarchyComponent() {
    }

    /* This is just to make spring/hibernate build successful */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * pack factor hierarchy id foreign key to pack_factor_hierarchy.
     */
    @NotNull
    @Column(name = "pf_hierarchy_id", length = 20)
    public Long getPfHierarchyId() {
        return pfHierarchyId;
    }

    public void setPfHierarchyId(Long pfHierarchyId) {
        this.pfHierarchyId = pfHierarchyId;
    }

    /**
     * pack factor component id foreign key to pack_factor_component.
     */
    @NotNull
    @Column(name = "pf_component_id", length = 20)
    public Long getPfComponentId() {
        return pfComponentId;
    }

    public void setPfComponentId(Long pfComponentId) {
        this.pfComponentId = pfComponentId;
    }

    /**
     * pack factor child component id foreign key to pack_factor_component.
     */
    @Column(name = "child_pf_component_id", length = 20, nullable = true)
    public Long getChildPfComponentId() {
        return childPfComponentId;
    }

    public void setChildPfComponentId(Long childPfComponentId) {
        this.childPfComponentId = childPfComponentId;
    }

    /**
     * factor - ratio between immediate parent and child pack configuration.
     */
    @NotEmpty
    @Column(name = "factor", columnDefinition = "Decimal(30,5)")
    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    /**
     * bottom_level_flag - Is lowest tracking level?.
     */
    @NotEmpty
    @Column(name = "bottom_level_flag", columnDefinition = "0")
    public Boolean getBottomLevelFlag() {
        return bottomLevelFlag;
    }

    public void setBottomLevelFlag(Boolean bottomLevelFlag) {
        this.bottomLevelFlag = bottomLevelFlag;
    }

    /**
     * level - tracking levels (how many levels has this pack configuration got?).
     */
    @NotEmpty
    @Column(name = "level", length =  10)
    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * default height for the tracking level.
     */
    @Column(name = "default_height", columnDefinition = "Decimal(30,5)", nullable = true)
    public Double getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(Double defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    /**
     * default depth for the tracking level.
     */
    @Column(name = "default_depth", columnDefinition = "Decimal(30,5)", nullable = true)
    public Double getDefaultDepth() {
        return defaultDepth;
    }

    public void setDefaultDepth(Double defaultDepth) {
        this.defaultDepth = defaultDepth;
    }

    /**
     * default weight for the tracking level.
     */
    @Column(name = "default_weight", columnDefinition = "Decimal(30,5)", nullable = true)
    public Double getDefaultWeight() {
        return defaultWeight;
    }

    public void setDefaultWeight(Double defaultWeight) {
        this.defaultWeight = defaultWeight;
    }

    /**
     * default volume for the tracking level.
     */
    @Column(name = "default_cube", columnDefinition = "Decimal(30,5)", nullable = true)
    public Double getDefaultCube() {
        return defaultCube;
    }

    public void setDefaultCube(Double defaultCube) {
        this.defaultCube = defaultCube;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_hierarchy_id",referencedColumnName = "pf_hierarchy_id", insertable = false, updatable = false)
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    public PackFactorHierarchy getPackFactorHierarchy() {
        return packFactorHierarchy;
    }

    public void setPackFactorHierarchy(PackFactorHierarchy packFactorHierarchy) {
        this.packFactorHierarchy = packFactorHierarchy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pf_component_id",referencedColumnName = "pf_component_id", insertable = false, updatable = false)
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    public PackFactorComponent getPackFactorComponent() {
        return packFactorComponent;
    }

    public void setPackFactorComponent(PackFactorComponent packFactorComponent) {
        this.packFactorComponent = packFactorComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackFactorHierarchyComponent that = (PackFactorHierarchyComponent) o;

        if (!bottomLevelFlag.equals(that.bottomLevelFlag)) return false;
        if (!childPfComponentId.equals(that.childPfComponentId)) return false;
        if (defaultCube != null ? !defaultCube.equals(that.defaultCube) : that.defaultCube != null) return false;
        if (defaultDepth != null ? !defaultDepth.equals(that.defaultDepth) : that.defaultDepth != null) return false;
        if (defaultHeight != null ? !defaultHeight.equals(that.defaultHeight) : that.defaultHeight != null)
            return false;
        if (defaultWeight != null ? !defaultWeight.equals(that.defaultWeight) : that.defaultWeight != null)
            return false;
        if (factor != null ? !factor.equals(that.factor) : that.factor != null) return false;
        if (!id.equals(that.id)) return false;
        if (!level.equals(that.level)) return false;
        if (packFactorComponent != null ? !packFactorComponent.equals(that.packFactorComponent) : that.packFactorComponent != null)
            return false;
        if (packFactorHierarchy != null ? !packFactorHierarchy.equals(that.packFactorHierarchy) : that.packFactorHierarchy != null)
            return false;
        if (!pfComponentId.equals(that.pfComponentId)) return false;
        if (!pfHierarchyId.equals(that.pfHierarchyId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + pfHierarchyId.hashCode();
        result = 31 * result + pfComponentId.hashCode();
        result = 31 * result + childPfComponentId.hashCode();
        result = 31 * result + (factor != null ? factor.hashCode() : 0);
        result = 31 * result + bottomLevelFlag.hashCode();
        result = 31 * result + level.hashCode();
        result = 31 * result + (defaultHeight != null ? defaultHeight.hashCode() : 0);
        result = 31 * result + (defaultDepth != null ? defaultDepth.hashCode() : 0);
        result = 31 * result + (defaultWeight != null ? defaultWeight.hashCode() : 0);
        result = 31 * result + (defaultCube != null ? defaultCube.hashCode() : 0);
        result = 31 * result + (packFactorHierarchy != null ? packFactorHierarchy.hashCode() : 0);
        result = 31 * result + (packFactorComponent != null ? packFactorComponent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PackFactorHierarchyComponent{" +
                "id=" + id +
                ", pfHierarchyId=" + pfHierarchyId +
                ", pfComponentId=" + pfComponentId +
                ", childPfComponentId=" + childPfComponentId +
                ", factor=" + factor +
                ", bottomLevelFlag=" + bottomLevelFlag +
                ", level=" + level +
                ", defaultHeight=" + defaultHeight +
                ", defaultDepth=" + defaultDepth +
                ", defaultWeight=" + defaultWeight +
                ", defaultCube=" + defaultCube +
                ", packFactorHierarchy=" + packFactorHierarchy +
                ", packFactorComponent=" + packFactorComponent +
                '}';
    }
}