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
package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MappingDetailView;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.mapping.MappingPath;
import com.lucas.entity.eai.transformation.Transformation;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class MappingPathView {

    private MappingPath mappingPath;

    public MappingPathView() {
        this.mappingPath = new MappingPath();
    }

    public MappingPathView(MappingPath mappingPath) {
        this.mappingPath = mappingPath;
    }

    @JsonView({MappingDetailView.class})
    public Long getMappingPathId() {
        if (this.mappingPath != null) {
            return this.mappingPath.getMappingPathId();
        }
        return null;
    }

    public void setMappingPathId(Long mappingPathId) {
        if (this.mappingPath != null) {
            this.mappingPath.setMappingPathId(mappingPathId);
        }
    }

    @JsonIgnore
    public MappingView getMapping() {
        if (this.mappingPath != null) {
            return  new MappingView(this.mappingPath.getMapping());
        }
        return null;
    }

    public void setMapping(Mapping eaiMapping) {
        if (this.mappingPath != null) {
            this.mappingPath.setMapping(eaiMapping);
        }
    }

    @JsonView({MappingDetailView.class})
    public TransformationView getFromTransformation() {
        if (this.mappingPath != null && this.mappingPath.getFromTransformation()!=null) {
            return new TransformationView(this.mappingPath.getFromTransformation());
        }
        return null;
    }

    public void setFromTransformation(TransformationView transformationView) {
        if (this.mappingPath != null) {
            this.mappingPath.setFromTransformation(transformationView.getTransformation());
        }
    }

    @JsonView({MappingDetailView.class})
    public Integer getMappingPathSeq() {
        if (this.mappingPath != null) {
            return this.mappingPath.getMappingPathSeq();
        }
        return null;
    }

    public void setMappingPathSeq(Integer mappingPathSeq) {
        if (this.mappingPath != null) {
            this.mappingPath.setMappingPathSeq(mappingPathSeq);
        }
    }

    @JsonView({MappingDetailView.class})
    public TransformationView getToTransformation() {
        if (this.mappingPath != null && this.mappingPath.getToTransformation() !=null) {
            return new TransformationView(this.mappingPath.getToTransformation());
        }
        return null;
    }

    public void setToTransformation(TransformationView transformationView) {
        if (this.mappingPath != null) {
            this.mappingPath.setToTransformation(transformationView.getTransformation());
        }
    }

    @JsonIgnore
    public MappingPath getMappingPath() {
        return mappingPath;
    }

    public void setMappingPath(MappingPath mappingPath) {
        this.mappingPath = mappingPath;
    }
}
