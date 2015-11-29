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

import org.apache.commons.lang3.tuple.MutablePair;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.MultiUserDetailsView;

public class MutablePairView {
	
	@SuppressWarnings("rawtypes")
	MutablePair mutablePair;


	public MutablePairView(MutablePair mutablePair) {
		this.mutablePair = mutablePair;
	}

	@JsonView({MultiUserDetailsView.class})
	public Map<String, String> getMutablePair() {
		Map<String, String> mutablePairMap = new HashMap<String, String>();
		mutablePairMap.put("value", (mutablePair.left == null) ? null :mutablePair.left.toString());
		return mutablePairMap;
	}

	public void setMutablePair(MutablePair<String, Boolean> mutablePair) {
		this.mutablePair = mutablePair;
	}
	
	

}
