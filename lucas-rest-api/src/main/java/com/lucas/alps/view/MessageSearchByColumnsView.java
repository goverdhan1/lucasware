/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED
 */
package com.lucas.alps.view;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.BaseView;
import com.lucas.alps.viewtype.MessageSearchByColumnsDetailsView;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageFormat;
import com.lucas.entity.equipment.EquipmentStyle;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.ui.viewconfig.GridColumn;
import com.lucas.services.util.CollectionsUtilService;

public class MessageSearchByColumnsView implements Serializable{

    private List<Message> messageList;
    private Long totalRecords;

    public MessageSearchByColumnsView(){
        
    }
    
    public MessageSearchByColumnsView(List<Message> messageList,Long totalRecords){
        this.messageList = messageList;
        this.totalRecords = totalRecords;
        
    }
    
    @JsonView(MessageSearchByColumnsDetailsView.class)
    public Map<String, GridColumnView> getGrid() throws IOException {
        LinkedHashMap<String, GridColumnView> gridView = new LinkedHashMap<String, GridColumnView>();
        for (int i = 1; i <= BaseView.TOTAL_NO_OF_MESSAGE_GRID_COLUMNS; i++) {
            GridColumn gridColumn = new GridColumn();
            gridView.put(i + "", new GridColumnView(gridColumn));
        }

        for (Message message : CollectionsUtilService.nullGuard(messageList)) {
            gridView.get("1").getValues().add((message != null && message.getMessageName() != null && !message.getMessageName().isEmpty()) ? message.getMessageName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("2").getValues().add((message.getMessageDescription() != null && !message.getMessageDescription().isEmpty()) ? message.getMessageDescription() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("3").getValues().add((message.getMessageFormat().getName() != null && !message.getMessageFormat().getName().isEmpty()) ? message.getMessageFormat().getName() : BaseView.ZERO_LENGTH_STRING);
            gridView.get("4").getValues().add(message.isLucasPredefined() ? "Yes" : "No");
            gridView.get("5").getValues().add((message.getLucasPredefinedService() != null && !message.getLucasPredefinedService().isEmpty()) ? message.getLucasPredefinedService() : BaseView.ZERO_LENGTH_STRING);
        }
        return gridView;
    }
    
    public void setGrid(final Map<String, GridColumnView> gridView)
            throws ParseException {
        int pageSize = gridView.get("1").getValues().size();
        messageList = new ArrayList<Message>();
        for (int index = 0; index < pageSize; index++) {
            final int i = index;
            messageList.add(new Message());
            messageList.get(i).setMessageName(gridView.get("1").getValues().get(i));
            messageList.get(i).setMessageDescription(gridView.get("2").getValues().get(i));
            messageList.get(i).setMessageFormat(MessageFormat.valueOf(gridView.get("3").getValues().get(i)));
            messageList.get(i).setLucasPredefined(gridView.get("4").getValues().get(i).equalsIgnoreCase("YES"));
            messageList.get(i).setLucasPredefinedService(gridView.get("5").getValues().get(i));
        }
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @JsonView(MessageSearchByColumnsDetailsView.class)
    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }


}
