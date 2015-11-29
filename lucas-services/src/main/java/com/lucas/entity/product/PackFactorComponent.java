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

import com.lucas.entity.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Pack factor component entity object
 * @author DiepLe
 *
 */


@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "pack_factor_component")
public class PackFactorComponent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long pfComponentId;
    private String pfComponentName;
    private String pfComponentDefinition;
    private String pfComponentVoicing;
    private List<PackFactorHierarchyComponent> pfHierarchyComponent = new ArrayList<>();

    public PackFactorComponent() {
    }

    /**
     * pack factor component id primary key.
     */
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pf_component_id", length = 20)
    public Long getPfComponentId() {
        return pfComponentId;
    }

    public void setPfComponentId(Long pfComponentId) {
        this.pfComponentId = pfComponentId;
    }

    /**
     * pack factor component name.
     */
    @NotEmpty
    @Column(name = "pf_component_name", unique = true, length = 50)
    public String getPfComponentName() {
        return pfComponentName;
    }

    public void setPfComponentName(String pfComponentName) {
        this.pfComponentName = pfComponentName;
    }

    /**
     * pack factor component definition in json format.
     */
    @Column(name = "pf_component_definition", nullable = true)
    public String getPfComponentDefinition() {
        return pfComponentDefinition;
    }

    public void setPfComponentDefinition(String pfComponentDefinition) {
        this.pfComponentDefinition = pfComponentDefinition;
    }

    /**
     * use by Jennifer to speak to voice pickers.
     */
    @Column(name = "pf_component_voicing", nullable = true)
    public String getPfComponentVoicing() {
        return pfComponentVoicing;
    }

    public void setPfComponentVoicing(String pfComponentVoicing) {
        this.pfComponentVoicing = pfComponentVoicing;
    }

    /**
     * Gets list of <code>Pack_Factor_Hierarchy_Component</code>s.
     */
    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "packFactorHierarchy")
    public List<PackFactorHierarchyComponent> getPfHierarchyComponent() {
        return pfHierarchyComponent;
    }

    public void setPfHierarchyComponent(List<PackFactorHierarchyComponent> pfHierarchyComponent) {
        this.pfHierarchyComponent = pfHierarchyComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackFactorComponent that = (PackFactorComponent) o;

        if (pfComponentDefinition != null ? !pfComponentDefinition.equals(that.pfComponentDefinition) : that.pfComponentDefinition != null)
            return false;
        if (!pfComponentId.equals(that.pfComponentId)) return false;
        if (!pfComponentName.equals(that.pfComponentName)) return false;
        if (pfComponentVoicing != null ? !pfComponentVoicing.equals(that.pfComponentVoicing) : that.pfComponentVoicing != null)
            return false;
        if (pfHierarchyComponent != null ? !pfHierarchyComponent.equals(that.pfHierarchyComponent) : that.pfHierarchyComponent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pfComponentId.hashCode();
        result = 31 * result + pfComponentName.hashCode();
        result = 31 * result + (pfComponentDefinition != null ? pfComponentDefinition.hashCode() : 0);
        result = 31 * result + (pfComponentVoicing != null ? pfComponentVoicing.hashCode() : 0);
        result = 31 * result + (pfHierarchyComponent != null ? pfHierarchyComponent.hashCode() : 0);
        return result;
    }
}