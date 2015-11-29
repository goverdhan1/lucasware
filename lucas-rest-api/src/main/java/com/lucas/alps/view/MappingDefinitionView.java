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
import com.lucas.entity.eai.mapping.MappingDefinition;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class MappingDefinitionView {

    private MappingDefinition mappingDefinition;

    public MappingDefinitionView() {
        this.mappingDefinition = new MappingDefinition();
    }

    public MappingDefinitionView(MappingDefinition mappingDefinition) {
        this.mappingDefinition = mappingDefinition;
    }

    @JsonView({MappingDetailView.class})
    public Long getMappingDefinitionId() {
        if(this.mappingDefinition!=null){
            return this.mappingDefinition.getMappingDefinitionId();

        }
        return null;
    }

    public void setMappingDefinitionId(Long mappingDefinitionId) {
        if(this.mappingDefinition!=null){
            this.mappingDefinition.setMappingDefinitionId(mappingDefinitionId);
        }
    }

    @JsonIgnore
    public MappingView getEaiMapping() {
        if(this.mappingDefinition!=null){
           return new MappingView(this.mappingDefinition.getEaiMapping());
        }
        return null;
    }

    public void setEaiMapping(MappingView mappingView) {
        if(this.mappingDefinition!=null){
            this.mappingDefinition.setEaiMapping(mappingView.getMapping());
        }
    }

    @JsonView({MappingDetailView.class})
    public String getTransformationEntityName() {
        if(this.mappingDefinition!=null){
            return this.mappingDefinition.getTransformationEntityName();
        }
        return null;
    }

    public void setTransformationEntityName(String transformationEntityName) {
        if(this.mappingDefinition!=null){
            this.mappingDefinition.setTransformationEntityName(transformationEntityName);
        }
    }

    @JsonView({MappingDetailView.class})
    public Long getTransformationId() {
        if(this.mappingDefinition!=null){
            return this.mappingDefinition.getTransformationId();
        }
        return null;
    }

    public void setTransformationId(Long transformationId) {
        if(this.mappingDefinition!=null){
            this.mappingDefinition.setTransformationId(transformationId);
        }
    }

    @JsonIgnore
    public MappingDefinition getMappingDefinition() {
        return mappingDefinition;
    }

    public void setMappingDefinition(MappingDefinition mappingDefinition) {
        this.mappingDefinition = mappingDefinition;
    }
}
