package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.*;
import com.lucas.entity.ui.dataurl.DataUrl;

public class DataUrlView {
	
	private DataUrl dataURL;

	public DataUrlView() {
		dataURL =  new DataUrl();
	}

	public DataUrlView(DataUrl dataURL) {
		this.dataURL = dataURL;
	}

	public DataUrl getDataURL() {
		return dataURL;
	}

	public void setDataURL(DataUrl dataURL) {
		this.dataURL = dataURL;
	}
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class
			, SearchProductGridWidgetDetailsView.class, PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public String getUrl() {
		if(dataURL != null){
			return dataURL.getUrl();
		}
		return null;
	}

	public void setUrl(String url) {
		if(dataURL != null){
			this.dataURL.setUrl(url);
		}
	}
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,GroupMaintenanceWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class
			,  SearchProductGridWidgetDetailsView.class, PicklineByWaveBarChartWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
			, EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public SearchCriteriaView getSearchCriteria() {
		if(dataURL != null){
			return new SearchCriteriaView(dataURL.getSearchCriteria());
		}
		return null;
	}

    public void setSearchCriteria(SearchCriteriaView searchCriteria) {
        if(dataURL != null){
            this.dataURL.setSearchCriteria(searchCriteria.getSearchAndSortCriteria());
        }
    }

	

}
