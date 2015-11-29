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
<<<<<<< HEAD
 */
package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MappingDetailView;
import com.lucas.entity.eai.transformation.Transformation;
import com.lucas.entity.eai.transformation.TransformationDefinition;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class TransformationView {

    private Transformation transformation;

    public TransformationView() {
        this.transformation = new Transformation();
    }

    public TransformationView(Transformation transformation) {
        this.transformation = transformation;
    }

    @JsonView({MappingDetailView.class})
    public Long getTransformationId() {
        if(this.transformation!=null){
            return this.transformation.getTransformationId();
        }
        return null;
    }

    public void setTransformationId(Long transformationId) {
        if(this.transformation!=null){
            this.transformation.setTransformationId(transformationId);
        }
    }

    public String getTransformationChannel() {
        if(this.transformation!=null){
            return this.transformation.getTransformationChannel();
        }
        return null;
    }

    public void setTransformationChannel(String transformationChannel) {
        if(this.transformation!=null){
            this.transformation.setTransformationChannel(transformationChannel);
        }
    }

    @JsonView({MappingDetailView.class})
    public String getTransformationName() {
        if(this.transformation!=null){
            return this.transformation.getTransformationName();
        }
        return null;
    }

    public void setTransformationName(String transformationName) {
        if(this.transformation!=null){
            this.transformation.setTransformationName(transformationName);
        }
    }

    public String getTransformationType() {
        if(this.transformation!=null){
            return this.transformation.getTransformationType();
        }
        return null;
    }

    public void setTransformationType(String transformationType) {
        if(this.transformation!=null){
            this.transformation.setTransformationType(transformationType);
        }
    }


    @JsonIgnore
    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }
}
