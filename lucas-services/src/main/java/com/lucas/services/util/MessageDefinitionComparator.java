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
package com.lucas.services.util;

import java.util.Comparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lucas.entity.eai.message.MessageDefinition;


/**
 * @author Pedda Reddy
 * OpenUserCanvasComparator provide the functionality for comparing the MessageDefinition
 * based on the MessageFieldSeq of the MessageDefinition which is used in the
 * Collection.sort() before rendering the data to the controller
 */

public class MessageDefinitionComparator implements Comparator<MessageDefinition> {

    private static final Logger LOG = LoggerFactory.getLogger(WidgetInstanceComparator.class);
    
    @Override
    public int compare(MessageDefinition messageDefinition1, MessageDefinition messageDefinition2) {
        if(messageDefinition1 != null && messageDefinition1.getMessageFieldSeq() != 0l
                && messageDefinition2 != null && messageDefinition2.getMessageFieldSeq() != 0l){
            try{
                if(messageDefinition1.getMessageFieldSeq() > messageDefinition2.getMessageFieldSeq())
                    return 1;
                else
                    return -1;
            }catch(Exception e){
                LOG.error(e.getLocalizedMessage());
            }            
        }
        return 0;
    }

}
