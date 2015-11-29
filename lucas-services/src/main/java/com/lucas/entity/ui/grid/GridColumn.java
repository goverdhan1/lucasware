package com.lucas.entity.ui.grid;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

public class GridColumn {

	private Long columnId;
	private String name;
	private boolean visible;
	private int order;
	private boolean sorted;
	private List<String> valueList;
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public boolean isSorted() {
		return sorted;
	}
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public List<String> getValueList() {
		return valueList;
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
	
}
