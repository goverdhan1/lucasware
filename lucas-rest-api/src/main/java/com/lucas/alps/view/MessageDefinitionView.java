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

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MessageDetailsView;
import com.lucas.alps.viewtype.SaveMessageSegmentDetailsView;
import com.lucas.entity.eai.message.Message;
import com.lucas.entity.eai.message.MessageDefinition;
import com.lucas.entity.eai.message.Segments;
import com.lucas.entity.eai.transformation.TransformationDefinition;

/**
 * @author Naveen
 * 
 */
public class MessageDefinitionView implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125384551121387394L;

    private MessageDefinition messageDefinition;

    public MessageDefinitionView() {
        if (messageDefinition != null) {
            messageDefinition = new MessageDefinition();
        }

    }

    public MessageDefinitionView(MessageDefinition messageDefinition) {
        this.messageDefinition = messageDefinition;
    }


	/**
	 * @return the messageDefinition
	 */
	public MessageDefinition getMessageDefinition() {
		return messageDefinition;
	}

    public void setMessageDefinition(MessageDefinition messageDefinition) {
        this.messageDefinition = messageDefinition;
    }

	/**
	 * @return the messageFieldSeq
	 */
    @JsonView({MessageDetailsView.class})
    public Integer getMessageFieldSeq() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldSeq();
        }
        return null;
    }

    public void setMessageFieldSeq(Integer messageFieldSeq) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldSeq(messageFieldSeq);
        }
    }

    @JsonView({MessageDetailsView.class})
    public String getMessageFieldType() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldType();
        }
        return null;
    }

    public void setMessageFieldType(String messageFieldType) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldType(messageFieldType);
        }
    }

    @JsonView({MessageDetailsView.class})
    public String getMessageFieldName() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldName();
        }
        return null;
    }

    public void setMessageFieldName(String messageFieldName) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldName(messageFieldName);
        }
    }

    @JsonView({MessageDetailsView.class})
    public Long getMessageSegmentId() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageSegmentId();
        }
        return null;
    }

    public void setMessageSegmentId(Long messageSegmentId) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageSegmentId(messageSegmentId);
        }
    }

    @JsonView({MessageDetailsView.class})
    public String getMessageFieldDescription() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldDescription();
        }
        return null;
    }

    public void setMessageFieldDescription(String messageFieldDescription) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldDescription(messageFieldDescription);
        }
    }

    @JsonView({MessageDetailsView.class})
    public Integer getMessageFieldLength() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldLength();
        }
        return null;
    }

    public void setMessageFieldLength(Integer messageFieldLength) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldLength(messageFieldLength);
        }
    }

    @JsonView({MessageDetailsView.class})
    public Integer getMessageFieldStarts() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldStarts();
        }
        return null;
    }

    public void setMessageFieldStarts(Integer messageFieldStarts) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldStarts(messageFieldStarts);
        }
    }

    @JsonView({MessageDetailsView.class})
    public Integer getMessageFieldEnds() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldEnds();
        }
        return null;
    }

    public void setMessageFieldEnds(Integer messageFieldEnds) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldEnds(messageFieldEnds);
        }
    }


    @JsonView({MessageDetailsView.class})
    public String getMessageFieldSeparator() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldSeparator();
        }
        return null;
    }

    public void setMessageFieldSeparator(String messageFieldSeparator) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldSeparator(messageFieldSeparator);
        }
    }

    @JsonView({MessageDetailsView.class})
    public Integer getMessageFieldRepeat() {
        if (messageDefinition != null) {
            return messageDefinition.getMessageFieldRepeat();
        }
        return null;
    }

    public void setMessageFieldRepeat(Integer messageFieldRepeat) {
        if (messageDefinition != null) {
            this.messageDefinition.setMessageFieldRepeat(messageFieldRepeat);
        }
    }


	/**
	 * @return the messageFieldId
	 */
	public Long getMessageFieldId() {
		return this.messageDefinition.getMessageFieldId();
	}

	/**
	 * @param messageFieldId
	 *            the messageFieldId to set
	 */
	public void setMessageFieldId(Long messageFieldId) {
		this.messageDefinition.setMessageFieldId(messageFieldId);
	}

	/**
	 * @return the eaiMessage
	 */
	public Message getEaiMessage() {
		return this.messageDefinition.getEaiMessage();
	}

	/**
	 * @param eaiMessage
	 *            the eaiMessage to set
	 */
	public void setEaiMessage(Message eaiMessage) {
		this.messageDefinition.setEaiMessage(eaiMessage);
	}

	/**
	 * @return the eaiSegments
	 */
	@JsonView({SaveMessageSegmentDetailsView.class})
	public Segments getEaiSegments() {
		return this.messageDefinition.getEaiSegments();
	}

	/**
	 * @param eaiSegments
	 *            the eaiSegments to set
	 */
	public void setEaiSegments(Segments eaiSegments) {
		this.messageDefinition.setEaiSegments(eaiSegments);
	}

	/**
	 * @return the transformationDefinition
	 */
	public Set<TransformationDefinition> getTransformationDefinition() {
		return this.messageDefinition.getTransformationDefinition();
	}

	/**
	 * @param transformationDefinition
	 *            the transformationDefinition to set
	 */
	public void setTransformationDefinition(
			Set<TransformationDefinition> transformationDefinition) {
		this.messageDefinition
				.setTransformationDefinition(transformationDefinition);
	}

}
