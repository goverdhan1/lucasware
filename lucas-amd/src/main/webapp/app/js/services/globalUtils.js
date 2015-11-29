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

var globalUtils = {

    createLinkTag: function(url) {
        var ss = document.createElement("link");
        ss.rel = "stylesheet";
        ss.href = url;
        document.getElementsByTagName("head")[0].appendChild(ss);
    },
    loadCDNFallback: function(CDN, fallbackUrl) {
        $.ajax({
            url: CDN,
            error: function() {
                globalUtils.createLinkTag(fallbackUrl);
            }
        });
    }
}