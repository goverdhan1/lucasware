package com.lucas.entity.ui.widget;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class ProductivityByZoneWidget extends AbstractLicensableWidget {

}
