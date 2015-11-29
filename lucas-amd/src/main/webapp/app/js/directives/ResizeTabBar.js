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

"use strict";

amdApp.directive('resizeTabBar', function($window, $timeout) {

    var linkFunction = function(scope, elem, attrs, resizeTabBarController) {
        resizeTabBarController.init(elem);
    };

    return {
        restrict: "EA",
        controller: 'ResizeTabBarController',
        transclude: true,
        template: 
            '<ul class="nav nav-pills tabs" ui-sortable="sortableOptions" ng-model="canvasBar" ng-transclude></ul>' +
                '<button type="button" class="btn btn-default btn-sm dropdown-toggle"><span class="caret"></span></button>' +
            '<ul class="dropdown-menu options" role="menu" ng-transclude></ul>',
        link: linkFunction
    };
});