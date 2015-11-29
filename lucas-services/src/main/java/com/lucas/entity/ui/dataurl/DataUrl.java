package com.lucas.entity.ui.dataurl;

import com.lucas.services.search.SearchAndSortCriteria;

public class DataUrl {

	private String url;
    private SearchAndSortCriteria searchCriteria;
	
	public DataUrl(){
		
	}
	
	public DataUrl(String url, SearchAndSortCriteria searchCriteria){
		this.url = url;
		this.searchCriteria = searchCriteria;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SearchAndSortCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchAndSortCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

}
