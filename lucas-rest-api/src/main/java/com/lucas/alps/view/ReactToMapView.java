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
import com.lucas.entity.ui.reacttomap.ReactToMap;

/**
 * @author Prafull
 *
 */
public class ReactToMapView {
	
	private ReactToMap reactToMap;
	
	public ReactToMapView(){
		reactToMap = new ReactToMap();
	}
	
	public ReactToMapView(ReactToMap reactToMap){
		this.reactToMap = reactToMap;
	}

	public ReactToMap getReactToMap() {
		return reactToMap;
	}

	public void setReactToMap(ReactToMap reactToMap) {
		this.reactToMap = reactToMap;
	}
	@JsonView({CanvasDataView.class,UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class})
	public ReactToMapDataUrlView getUserName() {
		if(reactToMap != null && reactToMap.getUserName() != null) {
			return new ReactToMapDataUrlView(reactToMap.getUserName());
		}
		return null;
	}

	public void setUserName(ReactToMapDataUrlView reactToMapDataUrlView) {
		this.reactToMap.setUserName(reactToMapDataUrlView.getDataURL());
	}
	/*@JsonView({SearchUserGridWidgetDetailsView.class})
	public ReactToMapDataUrlView getHostLogin() {

		if (reactToMap != null ) {
			return new ReactToMapDataUrlView();
		}
		return null;
	}

	public void setHostLogin(ReactToMapDataUrlView reactToMapDataUrlView) {
		this.reactToMap.setUserName(reactToMapDataUrlView.getDataURL());
	}
*/
    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
    public ReactToMapDataUrlView getGroupName() {
        if (reactToMap != null && reactToMap.getGroupName() != null) {
            return new ReactToMapDataUrlView(reactToMap.getGroupName());
        }
        return null;
    }

    public void setGroupName(ReactToMapDataUrlView reactToMapDataUrlView)  {
        this.reactToMap.setGroupName(reactToMapDataUrlView.getDataURL());
    }
    
	@JsonView({CanvasDataView.class,SearchProductGridWidgetDetailsView.class})
	public ReactToMapDataUrlView getProductName() {
		if(reactToMap != null) {
			return new ReactToMapDataUrlView(reactToMap.getProductName());
		}return null;
	}

	public void setProductName(ReactToMapDataUrlView reactToMapDataUrlView) {
		this.reactToMap.setProductName(reactToMapDataUrlView.getDataURL());
	}    
}
