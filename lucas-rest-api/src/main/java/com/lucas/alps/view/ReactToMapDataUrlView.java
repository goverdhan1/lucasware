/**
 * 
 */
package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.GroupMaintenanceWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchGroupGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchProductGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.UserCreationFormWidgetDetailsView;
import com.lucas.entity.ui.dataurl.DataUrl;

/**
 * @author Prafull
 *
 */
public class ReactToMapDataUrlView {
	
	private DataUrl dataURL;

	public ReactToMapDataUrlView() {
		dataURL =  new DataUrl();
	}

	public ReactToMapDataUrlView(DataUrl dataURL) {
		this.dataURL = dataURL;
	}

	public DataUrl getDataURL() {
		return dataURL;
	}

	public void setDataURL(DataUrl dataURL) {
		this.dataURL = dataURL;
	}
	@JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class
			, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class })
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
	@JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class
			, SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class})
	public ReactToMapSearchAndSortCriteriaView getSearchCriteria() {
		if(dataURL != null){
			return new ReactToMapSearchAndSortCriteriaView(dataURL.getSearchCriteria());
		}
		return null;
	}

    public void setSearchCriteria(ReactToMapSearchAndSortCriteriaView searchAndSortCriteria) {
        if(dataURL != null){
            this.dataURL.setSearchCriteria(searchAndSortCriteria.getSearchAndSortCriteria());
        }
    }

	
}
