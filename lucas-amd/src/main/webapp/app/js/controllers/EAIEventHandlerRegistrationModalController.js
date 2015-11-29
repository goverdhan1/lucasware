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

amdApp.controller("EAIEventHandlerRegistrationModalController", ['$scope', '$modalInstance', 'EAIEventsService', 'eventHandlerName',
    function(scope, modalInstance, EAIEventsService, eventHandlerName) {

        //
        // Array to maintain the currently active tab in the widget
        //
        scope.tabs = [
            false,  // Details tab
            false  // Transport tab
        ];

        scope.closeModal = function() {
            modalInstance.close();
        };

        scope.save = function(save) {
            //add logic to save the data to database
            EAIEventsService.saveEAIEventHandlerRegistration(scope.details);
            scope.closeModal();
        };

        var fetchEventHandlerRegistration = function(id) {
            scope.details = EAIEventsService.getEAIEventHandlerRegistration(id).then(function(response) {
                scope.details = response;
            });
        }

        scope.changeDataFormat = function() {
            fetchEventHandlerRegistration();
            scope.$apply();
        };

        //mocking data starts here
        if(eventHandlerName.length > 0) {
            fetchEventHandlerRegistration();
        }

        fetchEventHandlerRegistration();

        var payload = ['EAI_EVENT_TYPES', 'EAI_EVENT_SOURCE_TYPES', 'EAI_EVENT_EXECUTION_ORDER', 'EAI_TRANSPORT_TYPES', 'EAI_EVENT_HANDLER_TYPES'];

        scope.staticData = EAIEventsService.getEAIEventStaticData(payload).then(function(response) {
            scope.staticData = response;
        });

        //mocking data ends here

    }
]);
