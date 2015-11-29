package com.lucas.alps.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.*;
import com.lucas.alps.viewtype.widget.EquipmentManagerGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.EquipmentTypeGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.EventHandlerGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.EventGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageMappingGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.MessageSegmentGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchGroupGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchProductGridWidgetDetailsView;
import com.lucas.alps.viewtype.widget.SearchUserGridWidgetDetailsView;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.services.filter.FilterType;

public class GridColumnView {
	
	private GridColumn gridColumn;

	public GridColumnView() {
		this.gridColumn = new  GridColumn();
	}

	public GridColumnView(GridColumn gridColumn) {
		this.gridColumn = gridColumn;
	}

	public GridColumn getGridColumn() {
		return gridColumn;
	}

	public void setGridColumn(GridColumn gridColumn) {
		this.gridColumn = gridColumn;
	}



	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class,  
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public String getName() {
		
		if (gridColumn != null) {
			return gridColumn.getName();
		}
		
		return null;
	}

	public void setName(String name) {
		if (gridColumn != null) {
			gridColumn.setName(name);
		}
	}


	@JsonView({CanvasDataView.class, EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class, 
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public String getFieldName() {
		
		if (gridColumn != null) {
			return gridColumn.getFieldName();
		}
		
		return null;
	}

	public void setFieldName(String fieldName) {
		if (gridColumn != null) {
			gridColumn.setFieldName(fieldName);
		}
	}

	

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, 
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Boolean getVisible() {
		if (gridColumn != null) {
			return gridColumn.getVisible();
		}
		return null;
	}

	public void setVisible(Boolean visible) {
		if (gridColumn != null) {
			gridColumn.setVisible(visible);
		}
	}
	
	/**
	 * allowFilter widget definition required for the grids.
	 */

	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, 
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public Boolean getAllowFilter() {
        if (gridColumn != null) {
            return gridColumn.getAllowFilter();
        }
        return null;
    }

    public void setAllowFilter(Boolean allowFilter) {
        if (gridColumn != null) {
            gridColumn.setAllowFilter(allowFilter);
        }
    }
	
	@JsonView({CanvasDataView.class, EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class, 
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public String getOrder() {
		if (gridColumn != null) {
			return gridColumn.getOrder();
		}
		return null;
	}

	public void setOrder(String order) {
		if (gridColumn != null) {
			gridColumn.setOrder(order);
		}
	}
	@JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class, 
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public String getSortOrder() {
		if (gridColumn != null) {
			return gridColumn.getSortOrder();
		}
		return null;
	}

	public void setSortOrder(String sortOrder) {
		if (gridColumn != null) {
			gridColumn.setSortOrder(sortOrder);
		}
	}
	
	@JsonView({CanvasDataView.class, EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class, 
	    SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
	    ,EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
	public Integer getWidth() {
		if (gridColumn != null) {
			return gridColumn.getWidth();
		}
		return null;
	}

	public void setWidth(Integer width) {
		if (gridColumn != null) {
			gridColumn.setWidth(width);
		}
	}
	
/*	*//**
	 * filterType widget definition required for the grids.
	 */
	@JsonView({CanvasDataView.class, EquipmentTypeGridWidgetDetailsView.class,SearchUserGridWidgetDetailsView.class, SearchProductGridWidgetDetailsView.class, SearchGroupGridWidgetDetailsView.class,
		EventGridWidgetDetailsView.class})
	public String getFilterType() {
		if (gridColumn != null) {
			if (gridColumn.getFilterType() != null) {
				return gridColumn.getFilterType();
			} else {
				return FilterType.NONE.toString();
			}
		}
		return null;
	}

	public void setFilterType(String filterType) {
		if (gridColumn != null) {
			gridColumn.setFilterType(filterType);
		}
	}
	
	@JsonView({CanvasDataView.class,EquipmentSearchByColumnsDetailsView.class,EquipmentTypeSearchByColumnsDetailsView.class, UserSearchByColumnsDetailsView.class, ProductSearchByColumnsDetailsView.class
 	    , SegmentSearchByColumnsDetailsView.class,MessageSearchByColumnsDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class, EventSearchByColumnsDetailsView.class, MappingSearchByColumnsDetailsView.class})
	public List<String> getValues(){
		if(gridColumn != null){
			return gridColumn.getValues();
		}
		return null;
	}
	public void setValues(List<String> values){
		if (gridColumn != null) {
			gridColumn.setValues(values);
		}
	}
}
