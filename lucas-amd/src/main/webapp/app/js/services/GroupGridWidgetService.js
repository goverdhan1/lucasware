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

amdApp.factory('GroupGridWidgetService', ['RestApiService', '$rootScope',
    function (RestApiService, rootScope) {

        var factory = {};

        // Make rest call to delete selected group.
        factory.deleteGroup=function(group){

			var groups={
			  	groupName: group
			};

            return RestApiService.post("groups/delete", null, groups).then(
                function(response){
                    return response;
                },
                function(error){
                    throw new LucasBusinessException(error);
                }
            );

        };

        return factory;
    }
]);