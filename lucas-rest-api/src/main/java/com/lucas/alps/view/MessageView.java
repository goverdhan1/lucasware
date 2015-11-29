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

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MappingDetailView;
import com.lucas.alps.viewtype.MessageDetailsView;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.MessageFormat;
import com.lucas.services.util.CollectionsUtilService;

/**
 * @Author Pedda
 * @Product Lucas. Created By: Pedda Reddy Created On Date: 7/20/15 Time: 04:00 PM This Class
 *          provide the implementation for the functionality of..
 */

public class MessageView {

    private Message message;

    public MessageView() {
        message = new Message();
    }

    public MessageView(Message message) {
        if (message != null) {
            this.message = message;
        }
    }

    @JsonView({MappingDetailView.class,MessageDetailsView.class})
    public Long getMessageId() {
        if (message != null) {
            return message.getMessageId();
        }
        return null;
    }

    public void setMessageId(Long messageId) {
        if (message != null) {
            this.message.setMessageId(messageId);
        }
    }

    @JsonView({MappingDetailView.class,MessageDetailsView.class})
    public String getMessageName() {
        if (message != null) {
            return message.getMessageName();
        }
        return null;
    }

    public void setMessageName(String messageName) {
        if (message != null) {
            this.message.setMessageName(messageName);
        }
    }

    @JsonView({MessageDetailsView.class})
    public String getMessageDescription() {
        if (message != null) {
            return message.getMessageDescription();
        }
        return null;
    }

    public void setMessageDescription(String messageDescription) {
        if (message != null) {
            this.message.setMessageDescription(messageDescription);
        }
    }

    @JsonView({MessageDetailsView.class})
    public MessageFormat getMessageFormat() {
        if (message != null) {
            return message.getMessageFormat();
        }
        return null;
    }

    public void setMessageFormat(MessageFormat messageFormat) {
        if (message != null) {
            this.message.setMessageFormat(messageFormat);
        }
    }

    @JsonView({MessageDetailsView.class})
    public int getUsageInEvents() {
        if (message != null) {
            return message.getUsageInEvents();
        }
        return (Integer) null;
    }

    public void setUsageInEvents(int usageInEvents) {
        if (message != null) {
            this.message.setUsageInEvents(usageInEvents);
        }
    }

    @JsonView({MessageDetailsView.class})
    public boolean isLucasPredefined() {
        if (message != null) {
            return message.isLucasPredefined();
        }
        return false;
    }

    public void setLucasPredefined(boolean lucasPredefined) {
        if (message != null) {
            this.message.setLucasPredefined(isLucasPredefined());
        }
    }

    @JsonView({MessageDetailsView.class})
    public Set<MessageDefinitionView> getEaiMessageDefinitions() {
        Set<MessageDefinitionView> messageDefinitionViewSet = new HashSet<MessageDefinitionView>();
        if (message != null) {
            for (MessageDefinition messageDef : CollectionsUtilService.nullGuard(message.getEaiMessageDefinitions())) {
                MessageDefinitionView msgDefView = new MessageDefinitionView(messageDef);
                messageDefinitionViewSet.add(msgDefView);
            }
        }
        return messageDefinitionViewSet;
    }

    public void setMessageDefinitions(Set<MessageDefinitionView> messageDefinitions) {
        Set<MessageDefinition> messageDefinitionSet = new HashSet<MessageDefinition>();
        for (MessageDefinitionView messageDefView : CollectionsUtilService.nullGuard(messageDefinitions)) {
            messageDefinitionSet.add(messageDefView.getMessageDefinition());
        }
        if (message != null) {
            this.message.setEaiMessageDefinitions(messageDefinitionSet);
        }

    }

    @JsonIgnore
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
