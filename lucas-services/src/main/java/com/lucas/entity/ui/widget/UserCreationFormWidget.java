package com.lucas.entity.ui.widget;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.lucas.services.ui.Languages;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "all")
public class UserCreationFormWidget extends AbstractLicensableWidget {

	private	List<Languages> jenniferToUserLanguageList;
	private	List<Languages> usertoJenniferLanguageList;
	private	List<Languages> handheldScreenLanguageList;
	private	List<Languages> amdScreenLanguageList;

	public UserCreationFormWidget() {
	}	
	
	public UserCreationFormWidget(String name, String shortName,
			WidgetType widgetType, String defaultViewConfig
			 ) {

		super();
		this.name = name;
		this.shortName = shortName;
		this.widgetType = widgetType;
		this.defaultViewConfig = defaultViewConfig;
	}

	@Override
	@Transient
	public String getDefinitionData() {
		return definitionData;
	}
	public  void setDefinitionData(String data) {
		 this.definitionData = data;
	}



	@Transient
	public List<Languages> getJenniferToUserLanguageList() {
		return jenniferToUserLanguageList;
	}

	public void setJenniferToUserLanguageList(
			List<Languages> jenniferToUserLanguageList) {
		this.jenniferToUserLanguageList = jenniferToUserLanguageList;
	}

	@Transient
	public List<Languages> getUsertoJenniferLanguageList() {
		return usertoJenniferLanguageList;
	}


	public void setUsertoJenniferLanguageList(
			List<Languages> usertoJenniferLanguageList) {
		this.usertoJenniferLanguageList = usertoJenniferLanguageList;
	}

	@Transient
	public List<Languages> getHandheldScreenLanguageList() {
		return handheldScreenLanguageList;
	}

	public void setHandheldScreenLanguageList(
			List<Languages> handheldScreenLanguageList) {
		this.handheldScreenLanguageList = handheldScreenLanguageList;
	}

	@Transient
	public List<Languages> getamdScreenLanguageList() {
		return amdScreenLanguageList;
	}

	public void setamdScreenLanguageList(List<Languages> aMdScreenLanguageList) {
		this.amdScreenLanguageList = aMdScreenLanguageList;
	}

	
	

}
