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

(function() {
    'use strict';

    angular.module('amdApp').directive('widgetInteractions', widgetInteractions);

    //inject angular variables
    widgetInteractions.$inject = ['WidgetService', 'UtilsService'];

    //based on declared angular variables, pass your specific args in that order
    function widgetInteractions(WidgetService, UtilsService) {

        var linkFunction = function(scope, element, attrs, controller) {
            // get the instances
            scope.widgetInstances = controller.getWidgetInstancesList(parseInt(scope.widgetId), scope.listenToWidgets);

            if (scope.widgetInteractionConfig) {
                for (var i = scope.widgetInteractionConfig.length - 1; i >= 0; i--) {
                    scope.widgetInteractionConfig[i];
                    if (scope.widgetInteractionConfig[i].widgetInstance && UtilsService.findIndexByProperty(scope.widgetInstances, 'name', scope.widgetInteractionConfig[i].widgetInstance.name) == null) {
                        console.log("not found");
                        // remove as the element as the widget is deleted
                        scope.widgetInteractionConfig.splice(i, 1);
                    }
                }
            }

            // add the row
            scope.addRow = function() {
                scope.widgetInteractionConfig.push({});
            };

            // remove the row
            scope.removeRow = function(row, index) {
                console.log(row, index);
                scope.widgetInteractionConfig.splice(index, 1);
            };
        };

        return {
            restrict: 'EA',
            controller: 'WidgetInteractionsController',
            scope: {
                widgetInteractionConfig: "=",
                widgetId: "@",
                listenTo: "=",
                listenToWidgets: "="
            },
            link: linkFunction,
            templateUrl: 'views/widget-interactions.html'
        };

    };

})();