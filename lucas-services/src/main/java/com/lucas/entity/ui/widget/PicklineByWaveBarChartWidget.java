package com.lucas.entity.ui.widget;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class PicklineByWaveBarChartWidget extends AbstractLicensableWidget {

public PicklineByWaveBarChartWidget(){
		
	}
	
	public PicklineByWaveBarChartWidget(String name, String shortName,
			WidgetType widgetType, String defaultViewConfig,
			String title) {

		super();
		this.name = name;
		this.shortName = shortName;
		this.widgetType = widgetType;
		this.defaultViewConfig = defaultViewConfig;
		this.title = title;
	}
}
