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

 // Adds Mobile version of menu to the bootstrap tabset directive
 amdApp.directive('menuTabset', function($timeout, $translate) {
    var directive = {};
    directive.restrict = 'EA';
    directive.require = ['^tabset'];

    var templHTML = 
        '<div class="resizeTabSet">' +
                '<button type="button" ng-click="isOpen = !isOpen;" class="btn btn-default btn-sm dropdown-toggle">' +
                '   <span class="caret"></span>' +
                '   {{selectedHeading}}'+
                '</button>' +
                '<ul class="nav" role="menu" ng-click="isOpen = !isOpen;" ng-show=\"isOpen\">' +
                    '<li class=\"menuitem\" ng-repeat=\"tab in parentTabs\">' +
                        '<a ng-click="select(tab)"> {{tab.heading}} </a>' +
                    '</li>' +
                '</ul>' +
        '</div>'

    directive.compile = function(tabsetElem, attrs) {
        tabsetElem.addClass('resizeTabSetContainer');
        var parentTile = $(tabsetElem).closest(".tile");
        var tabsetElement = $(tabsetElem).prepend( angular.element(templHTML) );

        return function(scope, element, attr, ctrls) {
            var tabSetController = ctrls[0];
            scope.isOpen = false;
            scope.selectedHeading = '';
            scope.parentTabs = tabSetController.tabs;

            // returns true if the device is desktop, false otherwise
            scope.getDeviceSpecificClass = function() {
                return parentTile.hasClass('device-desktop');
            };

            // dynamically listen for width changes and set the appropriate device specific class
            function watchClass() {
                return scope.$watch(scope.getDeviceSpecificClass, function(newValue, oldValue) {
                    if (!newValue && attr.hidemenuheadingindex && scope.tabs[attr.hidemenuheadingindex]) {
                        // deselect the groups tab
                        scope.tabs[attr.hidemenuheadingindex] = false;
                        // select the first tab (login)
                        scope.tabs[0] = true;
                    }
                });
            }

            // add the watcher and stores it in scope.watcher so it can be unwatched at a later point for performance
            function addWatches() {
                scope.watcher = watchClass();
            }

            // add the watches
            addWatches();
            
            scope.$watch('tabs', function(newValue, oldValue) {
                if (newValue && newValue.indexOf(true) != -1) {
                    if (attr.hidemenuheadingindex && newValue.indexOf(true) == attr.hidemenuheadingindex)
                        return;
                    // maually translate where the translation has not yet happened
                    $translate(tabSetController.tabs[newValue.indexOf(true)].heading).then(function(translation) {
                        scope.selectedHeading = translation;
                    }, function(err) {
                        // use the existing translation
                        scope.selectedHeading = tabSetController.tabs[newValue.indexOf(true)].heading;
                    });
                }
            }, true);

            scope.select = function(selectedTab) {
                scope.selectedHeading = selectedTab.heading;
                tabSetController.select(selectedTab);
            };
        };
    };

    return directive;
});