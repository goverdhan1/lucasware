package com.lucas.entity.ui.license;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class WidgetLicense {
	
	private String license;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

}
