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

package com.lucas.entity.ui.widget;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.entity.support.LucasObjectMapper;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.reacttomap.ReactToMap;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
@Inheritance
@Table(name = "widget")
public class AbstractLicensableWidget implements LicenseableWidget<Map<String, Map<Permission, Boolean>>> /*,  DataDriven<T>*/  {

	protected Long Id;
	protected String name;
	protected String shortName;
	protected WidgetType widgetType;
	protected String title;
	protected String description;
	protected String defaultViewConfig;
	protected WidgetLicense widgetLicense;
	protected String definitionData; 
	protected List<String> broadcastList;
	private String definitionMethod;
	protected String dataURL;
	private Boolean active;
	private String category;
	protected String jsonActionConfig;
	private ObjectMapper jacksonObjectMapper;
    protected List<String> reactToList;
	
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "widget_id")	
	public Long getId() {
		return Id;
	}

	@Override	
	public void setId(Long Id) {
		this.Id = Id;
	}

	@Override
	@Column(name = "name", unique = true)
	@NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	@Enumerated(EnumType.STRING)
	@Column(name="widget_type")
	public WidgetType getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(WidgetType widgetType) {
		this.widgetType = widgetType;
	}
	
		
	@Override
	@Column(name = "title")	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override 
	//TODO
	@Column(name="license")
	@Embedded
	public WidgetLicense getLicense()  {
		return widgetLicense;
	}

	@Override
	public void setLicense(WidgetLicense widgetLicense) {
		this.widgetLicense = widgetLicense;
	}



	@Lob
	@Column(name="default_widget_view_config")	
	public String getDefaultViewConfig() {
		return defaultViewConfig;
	}

	public void setDefaultViewConfig(String defaultViewConfig) {
		this.defaultViewConfig = defaultViewConfig;
	}
	
	@Lob
	@Column(name="action_config")
	@NotNull
	public String getJsonActionConfig() {
		return jsonActionConfig;
	}
	
	public void setJsonActionConfig(String actionConfig) {
		this.jsonActionConfig = actionConfig;
	}
	
	@JsonProperty("actionConfig")
	@Transient
	public MappedActionConfigurable<String, Map<Permission, Boolean>> getActionConfig() throws JsonParseException, JsonMappingException, IOException {
		if (jsonActionConfig != null) {
			jacksonObjectMapper = new LucasObjectMapper();
			MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig = jacksonObjectMapper
					.readValue(
							jsonActionConfig,
							new TypeReference<MappedActionConfigurable<String, Map<Permission, Boolean>>>() {
							});
			return mappedActionConfig;
		}
		return null;
	}


	public void setActionConfig(MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig) throws JsonProcessingException   {
		jacksonObjectMapper =  new LucasObjectMapper();
		this.jsonActionConfig =  jacksonObjectMapper.writeValueAsString(mappedActionConfig);
	}

	@Transient
	public List<String> getBroadcastList() {
		return broadcastList;
	}

	public void setBroadcastList(List<String> broadcastList) {
		this.broadcastList = broadcastList;
	}

    @Transient
    public List<String> getReactToList() {
        return reactToList;
    }

    public void setReactToList(List<String> reactToList) {
        this.reactToList = reactToList;
    }



    //@Transient
	@Lob
	@Column(name="definition_data")
	public String getDefinitionData() {
		return definitionData;
	}

	public void setDefinitionData(String data) {
		this.definitionData = data;
	}
	
	public boolean equals(Object other) {
		Boolean isEqual = false;
		if (this == other){
			isEqual = true;
		}
		if (!(other instanceof AreaRouteChartWidget)){
			isEqual = false;
		}
		AreaRouteChartWidget castOther = (AreaRouteChartWidget) other;
		isEqual = new EqualsBuilder().append(name, castOther.getName()).isEquals();

		return isEqual;
	}
	
	
	
	@JsonIgnore
	@Lob
	@Column(name="method_to_invoke_definition")
	public String getDefinitionMethod() {
		return definitionMethod;
	}

	public void setDefinitionMethod(String definitionMethod) {
		this.definitionMethod = definitionMethod;
	}
	
	
	@Override
	@Lob
	@Column(name = "data_url")	
	public String getDataURL() {
		return dataURL;
	}

	public void setDataURL(String dataURL) {
		this.dataURL = dataURL;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(name).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("name", name).toString();
	}
	
	@Column(name="active")
	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@Column(name = "category", unique = true, length = 50)
	@NotNull
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
