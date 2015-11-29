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
package com.lucas.services.application;

import java.util.List;
import java.util.Map;


/**
 * Interface class for the Code lookup functionality
 * @author DiepLe
 *
 */
public interface CodeLookupService {

    Map<String, Object> getCodeLookupData(List<String> codeLookupList);
    public List<String> getCodeLookupValueBySingleKey(String lookupKey);
}
