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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Pack factor hierarchy entity object
 * @author DiepLe
 *
 */

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "pack_factor_hierarchy")
public class PackFactorHierarchy extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;
    private Long pfHierarchyId;
    private String pfHierarchyName;
    private List<PackFactorHierarchyComponent> pfHierarchyComponent = new ArrayList<>();

    public PackFactorHierarchy() {
    }

    /**
     * pack factor hierarchy id primary key.
     */
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pf_hierarchy_id", length = 20)
    public Long getPfHierarchyId() {
        return pfHierarchyId;
    }

    public void setPfHierarchyId(Long pfHierarchyId) {
        this.pfHierarchyId = pfHierarchyId;
    }

    /**
     * pack factor hierarchy name.
     */
    @NotEmpty
    @Column(name = "pf_hierarchy_name", length = 20, unique = true)
    public String getPfHierarchyName() {
        return pfHierarchyName;
    }

    public void setPfHierarchyName(String pfHierarchyName) {
        this.pfHierarchyName = pfHierarchyName;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "packFactorHierarchy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

        PackFactorHierarchy that = (PackFactorHierarchy) o;

        if (pfHierarchyComponent != null ? !pfHierarchyComponent.equals(that.pfHierarchyComponent) : that.pfHierarchyComponent != null)
            return false;
        if (!pfHierarchyId.equals(that.pfHierarchyId)) return false;
        if (!pfHierarchyName.equals(that.pfHierarchyName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pfHierarchyId.hashCode();
        result = 31 * result + pfHierarchyName.hashCode();
        result = 31 * result + (pfHierarchyComponent != null ? pfHierarchyComponent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PackFactorHierarchy{" +
                "pfHierarchyId=" + pfHierarchyId +
                ", pfHierarchyName='" + pfHierarchyName + '\'' +
                ", pfHierarchyComponent=" + pfHierarchyComponent +
                '}';
    }
}