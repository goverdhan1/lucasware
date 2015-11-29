package com.lucas.alps.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.alps.viewtype.FavoriteCanvasesView;
import com.lucas.alps.viewtype.WidgetPalleteView;
import com.lucas.entity.ui.actionconfig.MappedActionConfigurable;
import com.lucas.entity.ui.license.WidgetLicense;
import com.lucas.entity.ui.widget.AbstractLicensableWidget;
import com.lucas.entity.ui.widget.WidgetSubType;
import com.lucas.entity.ui.widget.WidgetType;
import com.lucas.entity.user.Permission;

@SuppressWarnings({ "serial" })
@JsonPropertyOrder({  "id", "name", "shortName", "widgetType",  "widgetActionConfig",   "broadCastMap", "listensForList",  })
public class AbstractLicensableWidgetView<T> implements java.io.Serializable {

	private AbstractLicensableWidget abstractLicensableWidget;

	public AbstractLicensableWidgetView() {
		this.abstractLicensableWidget = new AbstractLicensableWidget();
	}

	public AbstractLicensableWidgetView(
			AbstractLicensableWidget abstractLicensableWidget) {
		this.abstractLicensableWidget = abstractLicensableWidget;
	}

	@JsonIgnore
	public AbstractLicensableWidget getAbstractLicensableWidget() {
		return abstractLicensableWidget;
	}

	public void setAbstractLicensableWidget(
			AbstractLicensableWidget abstractLicensableWidget) {
		this.abstractLicensableWidget = abstractLicensableWidget;
	}

	@JsonView({FavoriteCanvasesView.class, WidgetPalleteView.class})
	public Long getId() {
		return abstractLicensableWidget.getId();
	}

	public void setId(Long Id) {
		this.abstractLicensableWidget.setId(Id);
	}

	@JsonView({FavoriteCanvasesView.class, WidgetPalleteView.class})
	public String getName() {
		return abstractLicensableWidget.getName();
	}

	public void setName(String name) {
		this.abstractLicensableWidget.setName(name);
	}
	
	@JsonView(WidgetPalleteView.class)
	public String getTitle() {
		return abstractLicensableWidget.getTitle();
	}

	public void setTitle(String title) {
		this.abstractLicensableWidget.setTitle(title);
	}

	@JsonView({FavoriteCanvasesView.class, WidgetPalleteView.class})
	public String getShortName() {
		return abstractLicensableWidget.getShortName();
	}

	public void setShortName(String shortName) {
		this.abstractLicensableWidget.setShortName(shortName);
	}

	@JsonView({FavoriteCanvasesView.class, WidgetPalleteView.class})
	@JsonProperty("type")
    public WidgetType getWidgetType() {
		return abstractLicensableWidget.getWidgetType();
	}

	public void setWidgetType(WidgetType widgetType) {
		this.abstractLicensableWidget.setWidgetType(widgetType);
	}
	
	@JsonView({WidgetPalleteView.class})
	public String getDescription() {
		return abstractLicensableWidget.getDescription();
	}

	public void setDescription(String description) {
		this.abstractLicensableWidget.setDescription(description);
	}

	@JsonView(FavoriteCanvasesView.class)
	@JsonProperty("widgetActionConfig")
	public Map<String, Map<Permission, Boolean>> getActionConfig() throws JsonParseException,
			JsonMappingException, IOException {

		if (abstractLicensableWidget.getActionConfig() != null) {
			Map<String, Map<Permission, Boolean>> actionConfigMap = abstractLicensableWidget
					.getActionConfig().getActionConfig();
			Map<String, Map<Permission, Boolean>> permissionsMap = new HashMap<String, Map<Permission, Boolean>>();
			for (Map.Entry<String, Map<Permission, Boolean>> entry : actionConfigMap
					.entrySet()) {

				permissionsMap.put(entry.getKey(), entry
						.getValue());
			}
			return permissionsMap;
		}
		
		return null;
	}

	public void setActionConfig(
			MappedActionConfigurable<String, Map<Permission, Boolean>> mappedActionConfig)
			throws JsonProcessingException {

		abstractLicensableWidget.setActionConfig(mappedActionConfig);
	}

	// @JsonIgnore
	@JsonView(FavoriteCanvasesView.class)
	public List<String> getBroadcastList() throws JsonParseException,
			JsonMappingException, IOException {
		
		return this.abstractLicensableWidget.getBroadcastList();
	}

	public void setBroadcastList(List<String> broadcastList)
			throws JsonProcessingException {
		
			this.abstractLicensableWidget.setBroadcastList(broadcastList);
	}
	
	// @JsonIgnore
	public DefaultViewConfigView getDefaultViewConfig() throws JsonParseException,
			JsonMappingException, IOException {
		if (abstractLicensableWidget.getDefaultViewConfig() != null) {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(
					this.abstractLicensableWidget.getDefaultViewConfig(),
					DefaultViewConfigView.class);
		}
		return null;
	}

	public void setDefaultViewConfig(DefaultViewConfigView defaultViewConfig)
			throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		this.abstractLicensableWidget.setDefaultViewConfig(om
				.writeValueAsString(defaultViewConfig));
	}

	// @JsonIgnore
	public WidgetLicense getWidgetLicense() {
		return abstractLicensableWidget.getLicense();
	}

	public void setWidgetLicense(WidgetLicense widgetLicense) {
		this.abstractLicensableWidget.setLicense(widgetLicense);
	}

	public String getData() {
		return abstractLicensableWidget.getDefinitionData();
	}

	
	public void setData(String data) {
		this.abstractLicensableWidget.setDefinitionData(data);
	
	}
	
	
	
}
