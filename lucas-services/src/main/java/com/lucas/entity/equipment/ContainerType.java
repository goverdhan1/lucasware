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
package com.lucas.entity.equipment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of
 * referencing the container type into the database.
 */
@Entity
@Table(name = "container_type")
public class ContainerType  implements Serializable{

    private String containerCode;
    private String containerDescription;
    private Long length;
    private Long width;
    private Long height;
    private String color;
    private String material;

    @Id
    @Column(name = "container_code", nullable = false, insertable = true, updatable = true, length = 25)
    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    @Column(name = "container_description", nullable = true, insertable = true, updatable = true, length = 255)
    public String getContainerDescription() {
        return containerDescription;
    }

    public void setContainerDescription(String containerDescription) {
        this.containerDescription = containerDescription;
    }

    @Column(name = "length", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Column(name = "width", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Column(name = "height", nullable = true, insertable = true, updatable = true, length = 20)
    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    @Column(name = "color", nullable = true, insertable = true, updatable = true, length = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "material", nullable = true, insertable = true, updatable = true, length = 50)
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
