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

amdApp.controller('ResizeTabBarController', ['$scope', '$rootScope', '$window', '$interval', function($scope, $rootScope, $window, $interval) {
    var self = this,
        window = angular.element($window);

    var refreshFavoriteCanvasListUpdated = $rootScope.$on('refreshFavoriteCanvasListUpdated', function(event, favoriteCanvasList) {
        $interval(function() {
            self.adjustDisplay(true);
        }, 0, 1);
    });

    $scope.$on('$destroy', function() {
        refreshFavoriteCanvasListUpdated();
    });

    this.init = function(element) {
        self.elem = element;
        element.addClass('resizeTabBar');

        window.on('resize', function(e) {
            self.adjustDisplay();
        });

        $interval(function() {
            self.adjustDisplay(true);
        }, 50, 1);
    };

    this.returnDigit = function(val) {
        var re = /\d+/;
        var digit;
        if (val == "") {
            digit = "0";
        } else {
            digit = val.match(re)[0];
        }
        return digit;
    };

    this.getWidth = function(element) {
        var w = self.returnDigit(element.css('width')) -
            self.returnDigit(element.css('padding-left')) -
            self.returnDigit(element.css('padding-right'));
        // console.log(w);
        return w;
    };


    this.adjustDisplay = function(firstTime) {
        var childs = angular.element(self.elem.children()[0]).children(),
            dropupChilds = angular.element(self.elem.children()[2]).children(),
            len = childs.length,
            elemWidth = self.getWidth(self.elem),
            buttonWidth = parseInt(self.returnDigit(angular.element(self.elem.children()[1]).css('width'))),
            totalWidth = 0,
            childElem,
            hideButton = true;

        // console.log("in adjustDisplay");

        // initial render
        if (firstTime && elemWidth == 0) {
            // console.log("in adjustDisplay initial render");
            $interval(function() {
                self.adjustDisplay(true);
            }, 50, 1);
            return;
        }

        for (var i = 0; i < len; i++) {
            childElem = angular.element(childs[i]);
            totalWidth += self.getWidth(childElem);
            // add 10 pixels, to avoid float issue
            if (totalWidth + buttonWidth + 10 > elemWidth) {
                childElem.css('display', 'none');
                angular.element(dropupChilds[i]).css('display', 'block');
                hideButton = false;
            } else {
                childElem.css('display', 'inline');
                angular.element(dropupChilds[i]).css('display', 'none');
            }
            // console.log(totalWidth, elemWidth);
        }

        if (hideButton) {
            angular.element(self.elem.children()[1]).css('visibility', 'hidden');
        } else {
            angular.element(self.elem.children()[1]).css('visibility', 'visible');
        }
    }

}]);