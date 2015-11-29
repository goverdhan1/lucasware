package com.lucas.alps.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.FavoriteCanvasesView;
import com.lucas.alps.viewtype.widget.*;
import com.lucas.entity.ui.viewconfig.ActualViewConfig;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.services.util.CollectionsUtilService;

@JsonInclude(Include.NON_EMPTY)
public class ActualViewConfigView {

	private ActualViewConfig actualViewConfig;

	public ActualViewConfigView() {
		this.actualViewConfig = new ActualViewConfig();
	}

	public ActualViewConfigView(ActualViewConfig actualViewConfig) {
		this.actualViewConfig = actualViewConfig;
	}

	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public List<Integer> getAnchor() {
		return actualViewConfig.getAnchor();
	}

	public void setAnchor(List<Integer> anchor) {

		actualViewConfig.setAnchor(anchor);
	}
	
	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public Long getMinimumWidth() {
		return actualViewConfig.getMinimumWidth();
	}

	public void setMinimumWidth(Long width) {
		actualViewConfig.setMinimumWidth(width);
	}

	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public Long getMinimumHeight() {
		return actualViewConfig.getMinimumHeight();
	}

	public void setMinimumHeight(Long height) {
		actualViewConfig.setMinimumHeight(height);
	}

	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public Long getZindex() {
		return actualViewConfig.getZindex();
	}

	public void setZindex(Long zindex) {
		actualViewConfig.setZindex(zindex);
	}

    @JsonView({CanvasDataView.class})
	public Boolean getIsMaximized() {
		return actualViewConfig.getIsMaximized();
	}

	public void setIsMaximized(Boolean isMaximized) {
		this.actualViewConfig.setIsMaximized(isMaximized);
	}

    @JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public Map<String, GridColumnView> getGridColumns() {
		Map<String, GridColumnView> gridColumns = new HashMap<String, GridColumnView>();
		Map<String, GridColumn> gridColumnsMap = actualViewConfig
				.getGridColumns();

		if (gridColumnsMap != null) {
			for (Entry<String, GridColumn> entry : CollectionsUtilService
					.nullGuard(gridColumnsMap.entrySet())) {
				gridColumns.put(entry.getKey(),
						new GridColumnView(entry.getValue()));
			}
		}
		return gridColumns;
	}

	public void setGridColumns(Map<String, GridColumnView> gridColums) {

		if (gridColums != null && gridColums.size() > 0) {
			Map<String, GridColumn> gridColumnsMap = new HashMap<String, GridColumn>();
			for (Entry<String, GridColumnView> entry : CollectionsUtilService
					.nullGuard(gridColums.entrySet())) {
				gridColumnsMap.put(entry.getKey(), entry.getValue()
						.getGridColumn());
			}
			actualViewConfig.setGridColumns(gridColumnsMap);
		}
	}

    @JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public WidgetOrientationView getOrientation() {
		return new WidgetOrientationView(actualViewConfig.getOrientation());
	}

	public void setOrientation(WidgetOrientationView orientation) {
		actualViewConfig.setOrientation(orientation.getWidgetOrientation());
	}

    @JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public DateFormatView getDateFormat() {
		return new DateFormatView(actualViewConfig.getDateFormat());
	}

	public void setDateFormat(DateFormatView dateFormat) {
		this.actualViewConfig.setDateFormat(dateFormat.getDateFormat());
	}

	@JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public List<String> getListensForList() {
		return actualViewConfig.getListensForList();
	}

	public void setListensForList(List<String> listensForList) {
		actualViewConfig.setListensForList(listensForList);
	}

    @JsonView({CanvasDataView.class,FavoriteCanvasesView.class})
	public Integer getPosition() {
		return actualViewConfig.getPosition();
	}

	public void setPosition(Integer position) {
		actualViewConfig.setPosition(position);
	}

    @JsonView({CanvasDataView.class})
    public Map<Integer, String> getDeviceWidths() {
        return actualViewConfig.getDeviceWidths();
    }

    public void setDeviceWidths(Map<Integer, String> deviceWidths) {
        this.actualViewConfig.setDeviceWidths(deviceWidths);
    }

    @JsonView({CanvasDataView.class})
    public AutoRefreshConfigView getAutoRefreshConfig() {
        return new AutoRefreshConfigView(actualViewConfig.getAutoRefreshConfig());
    }

    public void setAutoRefreshConfig(AutoRefreshConfigView autoRefreshConfigView) {
        this.actualViewConfig.setAutoRefreshConfig(autoRefreshConfigView.getAutoRefreshConfig());
    }


    public ActualViewConfig getActualViewConfig() {
		return actualViewConfig;
	}

	public void setActualViewConfig(ActualViewConfig actualViewConfig) {
		this.actualViewConfig = actualViewConfig;
	}

}
