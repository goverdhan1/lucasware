/**
 * Copyright (c) Lucas Systems LLC
 */

package com.lucas.entity.product;


import com.lucas.entity.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Table(name = "alert_locations")
public class AlertLocations extends BaseEntity {


    private Long alertLocationId;
    private String alertLocationName;
    private String alertLocationDescription;
    private List<SpecialInstructions> specialInstructions;

    public AlertLocations() {
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_locations_id")
    @Id
    public Long getAlertLocationId() {
        return alertLocationId;
    }

    public void setAlertLocationId(Long alertLocationId) {
        this.alertLocationId = alertLocationId;
    }

    @Column(name = "alert_location_name", nullable = false, unique = true, length = 50)
    public String getAlertLocationName() {
        return alertLocationName;
    }

    public void setAlertLocationName(String alertLocationName) {
        this.alertLocationName = alertLocationName;
    }

    @Column(name = "alert_location_description", nullable = false, length = 255)
    public String getAlertLocationDescription() {
        return alertLocationDescription;
    }

    public void setAlertLocationDescription(String alertLocationDescription) {
        this.alertLocationDescription = alertLocationDescription;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "alertLocations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<SpecialInstructions> getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(List<SpecialInstructions> specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
