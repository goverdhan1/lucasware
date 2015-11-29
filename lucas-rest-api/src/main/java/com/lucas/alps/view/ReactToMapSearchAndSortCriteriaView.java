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
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;

import java.util.Map;

/**
 * @author Prafull
 *
 */
public class ReactToMapSearchAndSortCriteriaView {
	
	 private SearchAndSortCriteria searchAndSortCriteria;

		public ReactToMapSearchAndSortCriteriaView() {
	        searchAndSortCriteria =  new SearchAndSortCriteria();
		}

	    public ReactToMapSearchAndSortCriteriaView(SearchAndSortCriteria searchAndSortCriteria) {
	        this.searchAndSortCriteria = searchAndSortCriteria;
	    }

	    public SearchAndSortCriteria getSearchAndSortCriteria() {
	        return searchAndSortCriteria;
	    }

	    public void setSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) {
	        this.searchAndSortCriteria = searchAndSortCriteria;
	    }



	    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class,SearchProductGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class})
	    public Map<String, Object> getSearchMap() {
	        if(searchAndSortCriteria != null){
	            return searchAndSortCriteria.getSearchMap();
	        }
	        return null;
	    }

	    public void setSearchMap(Map<String, Object> searchMap) {
	        if(searchAndSortCriteria != null){
	            this.searchAndSortCriteria.setSearchMap(searchMap);
	        }
	    }

        @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class})
	    public Map<String, SortType> getSortMap() {
	        if(searchAndSortCriteria != null){
	            return searchAndSortCriteria.getSortMap();
	        }
	        return null;
	    }
	    
	    public void setSortMap(Map<String, SortType> sortMap) {
	        if(searchAndSortCriteria != null){
	            this.searchAndSortCriteria.setSortMap(sortMap);
	        }
	    }
	    
	    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class,SearchProductGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class})
	    public String getPageSize() {
	        if(searchAndSortCriteria != null){
	            return searchAndSortCriteria.getPageSize().toString();
	        }
	        return null;
	    }

	    public void setPageSize(String pageSize) {
	        if(searchAndSortCriteria != null){
	            this.searchAndSortCriteria.setPageSize(Integer.parseInt(pageSize));
	        }
	    }
	    @JsonView({CanvasDataView.class,GroupMaintenanceWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class,SearchProductGridWidgetDetailsView.class,SearchGroupGridWidgetDetailsView.class})
	    public String getPageNumber() {
	        if(searchAndSortCriteria != null){
	            return searchAndSortCriteria.getPageNumber().toString();
	        }
	        return null;
	    }

	    public void setPageNumber(String pageNumber) {
	        if(searchAndSortCriteria != null){
	            this.searchAndSortCriteria.setPageNumber(Integer.parseInt(pageNumber));
	        }
	    }

}
