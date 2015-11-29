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
amdApp.directive("headerAutoHide", ['$document', '$window', '$timeout',
    function(document, window, $timeout) {
        var linkFunction = function(scope, element, attrs) {
            var $window = $(window),
                prevTop = $window.scrollTop(),
                timerID,
                hideDuration = attrs.hideDuration ? parseInt(attrs.hideDuration) * 1000 : 2000;

            scope.onMouseOver = false;

            // do not hide the header when hovered
            element.bind('mouseenter', function() {
                scope.onMouseOver = true;
            });

            // hide the header on hover out if needed
            element.bind('mouseleave', function() {
                scope.onMouseOver = false;
                if ($window.scrollTop() > 40) {
                    addTimer(hideDuration);
                }
            });

            // handle the mouse scroll event
            window.addEventListener("scroll", function() {
                var newScrollTop = $window.scrollTop();
                // clear the timer if any
                if (timerID) {
                    $timeout.cancel(timerID);
                    //window.clearTimeout(timerID);
                }
                // scroll up, show the header
                if (prevTop > newScrollTop) {
                    console.log("scroll upside");
                    showHideHeader(true);
                    if (newScrollTop > 40) {
                        addTimer(hideDuration);
                    }
                }
                // scroll down, hide the header
                else if (newScrollTop > 40) {
                    console.log("scroll down");
                    showHideHeader(false);
                }
                prevTop = newScrollTop;
            });

            // add the timeout to hide
            var addTimer = function(duration) {
                return $timeout(function() {
                    showHideHeader(false);
                }, duration);
            }

            // shows or hides the header based on the parameter passed
            // true - show the header
            // false - hide the header
            var showHideHeader = function(isShow) {
                var scrollTop = $window.scrollTop();
                // show the header
                if (isShow) {
                    element.removeClass('hideHeader');
                    element.addClass('showHeader');
                }
                // hide the header 
                else if (!scope.onMouseOver && scrollTop > 40) {
                    element.removeClass('showHeader');
                    element.addClass('hideHeader');
                }
            };
        };

        return {
            restrict: "EA",
            scope: {},
            link: linkFunction
        };
    }
]);