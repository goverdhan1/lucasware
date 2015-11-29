package com.lucas.entity.ui.widget;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("rawtypes")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class AreaRouteChartWidget  extends AbstractLicensableWidget  {
	private String chartData;
	
	@SuppressWarnings("unused")
	public AreaRouteChartWidget() {
	}

	@SuppressWarnings("unchecked")
	public AreaRouteChartWidget(String name, String shortName, WidgetType widgetType, String defaultViewConfig) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.widgetType = widgetType;
		this.defaultViewConfig = defaultViewConfig;
	}	
	

	@Override
	@Transient
	public String getDefinitionData() {
		return chartData;
	}
	public  void setData(String chartData) {
		 this.chartData = chartData;
	}

}
