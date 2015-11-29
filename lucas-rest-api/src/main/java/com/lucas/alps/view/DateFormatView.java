package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.CanvasDataView;
import com.lucas.alps.viewtype.widget.*;
import com.lucas.entity.ui.viewconfig.DateFormat;

import java.util.List;

public class DateFormatView {

    private DateFormat dateFormat;

    public DateFormatView() {

        dateFormat = new DateFormat();
    }

    public DateFormatView(DateFormat dateFormat) {

        this.dateFormat = dateFormat;
    }

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class
            ,  SearchProductGridWidgetDetailsView.class, PicklineByWaveBarChartWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
            , EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class, MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public String getSelectedFormat() {
        if (dateFormat != null) {

            return dateFormat.getSelectedFormat();
        }
        return null;
    }

    public void setSelectedFormat(String selectedFormat) {
        if (dateFormat != null) {
            this.dateFormat.setSelectedFormat(selectedFormat);
        }
    }

    @JsonView({CanvasDataView.class,EquipmentTypeGridWidgetDetailsView.class,UserCreationFormWidgetDetailsView.class, SearchUserGridWidgetDetailsView.class,
            SearchProductGridWidgetDetailsView.class, PicklineByWaveBarChartWidgetDetailsView.class,EquipmentManagerGridWidgetDetailsView.class,MessageSegmentGridWidgetDetailsView.class
            , EventGridWidgetDetailsView.class,MessageGridWidgetDetailsView.class,MessageMappingGridWidgetDetailsView.class,EventHandlerGridWidgetDetailsView.class})
    public List<String> getAvailableFormats() {
        if (dateFormat != null) {
            return dateFormat.getAvailableFormats();
        }
        return null;
    }

    public void setAvailableFormats(List<String> availableFormats) {
        if (dateFormat != null) {
            this.dateFormat.setAvailableFormats(availableFormats);
        }
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


}
