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

'use strict';

function LucasRestApiException(details) {

    this.status = details.status;
    this.code = details.code;
    this.message = details.message;
    this.level = details.level;
    this.explicitDismissal = details.explicitDismissal;
    this.uniqueKey = details.uniqueKey;
}
