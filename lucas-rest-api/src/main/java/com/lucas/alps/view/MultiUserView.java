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

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MultiUserDetailsView;

public class MultiUserView implements java.io.Serializable {
	private static final long serialVersionUID = -4066877668889252531L;

	private Map<String, String> userEditableFieldsMap;
	
	public MultiUserView() {
	}

	public MultiUserView(Map<String, String> userEditableFields) {
     this.userEditableFieldsMap  = userEditableFields;
	}

	
	@JsonView({MultiUserDetailsView.class})
	public Map<String, String> getUserEditableFields() {
		Map<String, String> userEditableFieldsView = new HashMap<String, String>();
		userEditableFieldsView = userEditableFieldsMap;
		return userEditableFieldsView;
	}

}
