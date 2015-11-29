/**
 * 
 */
package com.lucas.entity.ui.viewconfig;

import java.util.ArrayList;
import java.util.List;


public class GridColumn {
    
    private String name;
    private String fieldName;
    private Boolean visible;
    private Boolean allowFilter;
    private String order;
    private String sortOrder;
    private List<String> values;
    private int width;
    private String filterType;
    
    public GridColumn(){
        values = new ArrayList<String>();
    }
    
	public GridColumn(String name, String fieldName, Boolean visible,
			Boolean allowFilter, String order, String sortOrder,
			List<String> values, int width, String filterType) {
    
        this.name = name;
        this.fieldName = fieldName;
        this.visible = visible;
        this.allowFilter = allowFilter;
        this.order = order;
        this.sortOrder = sortOrder;
        this.width = width;
        this.values = values;
        this.filterType = filterType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * allowFilter widget definition for grids
     * @return the allowFilter
     */
    public Boolean getAllowFilter() {
        return allowFilter;
    }

    /**
     * @param allowFilter the allowFilter to set
     */
    public void setAllowFilter(Boolean allowFilter) {
        this.allowFilter = allowFilter;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

	/**
	 * @return the filterType
	 */
	public String getFilterType() {
		return filterType;
	}

	/**
	 * @param filterType the filterType to set
	 */
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
}
