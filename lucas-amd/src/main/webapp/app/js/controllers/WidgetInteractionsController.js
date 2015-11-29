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

    angular.module('amdApp').controller('WidgetInteractionsController', WidgetInteractionsController);

    // inject angular variables
    WidgetInteractionsController.$inject = ['$scope', '$element', 'UtilsService'];

    // based on declared angular variables, pass your specific args in that order
    function WidgetInteractionsController(scope, element, UtilsService) {

        // get the widget instances list using UtilService.getWidgetDetailsOfActiveCanvas
        this.getWidgetInstancesList = function(widgetId, widgetNames) {
            return UtilsService.getWidgetDetailsOfActiveCanvas(widgetId, widgetNames);
        };

    };

})();