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

amdApp.directive('lcwidget', ['$rootScope', '$compile', '$http', 'LocalStoreService', 'WidgetService', '$window', '$timeout',
    function(rootScope, compile, http, LocalStoreService, WidgetService, $window, $timeout) {
        var linkFunction = function(scope, element, attrs) {

            // returns the width and height of the DOM Element
            scope.getElementDimensions = function() {
                var dim = {
                    'width': $(element).outerWidth(),
                    'height': $(element).outerHeight()
                };

                return dim;
            };

            /* 
                sets the device specific class based on width passed
                if the width is greater than 540 then the 'device-desktop' class is applied
                if the width is greater than 320 and less than or equals 540 then the 'device-tablet' class is applied
                otherwise the 'device-mobile' class is applied
            */
            scope.setDeviceSpecificClass = function(width) {
                console.log("In setDeviceSpecificClass");
                // remove the device classes
                element.removeClass('device-mobile');
                element.removeClass('device-tablet');
                element.removeClass('device-desktop');
                element.removeClass('full-width');

                // add the device specific class
                if (width > 540) {
                    element.addClass('device-desktop');
                    console.log('device-desktop');
                    if(width > 900)
                    {
                        element.addClass("full-width");
                    }
                } else if (width > 320 && width <= 540) {
                    element.addClass('device-tablet');
                    console.log('device-tablet');
                } else {
                    element.addClass('device-mobile');
                    console.log('device-mobile');
                }
            };

            // dynamically listen for width changes and set the appropriate device specific class
            function watchDimensions() {
                return scope.$watch(scope.getElementDimensions, function(newValue, oldValue) {
                    console.log(newValue);
                    if (newValue.width > 0) {
                        scope.setDeviceSpecificClass(newValue.width);
                    }
                }, true);
            }

            // add the watcher and stores it in scope.watcher so it can be unwatched at a later point for performance
            function addWatches() {
                scope.watcher = watchDimensions();
            }

            // add the watches
            addWatches();

            // need to update the DOM for originalResizedMinimumWidth
            scope.$on('refreshOriginalResizedMinimumWidth', function() {
                var minimumWidth;
                if (scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth) {
                    $(element).css('min-width', scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth);
                }
            });

            // unwatch the watches for improving performance
            scope.$on('stopWatches', function() {
                // remove the watches
                console.log("in stopWatches");
                scope.watcher();
            });

            // update the width to accomodate the reduce the width of another widget (sibling)
            scope.$on('updateWidth', function(event, obj) {
                if (obj.element.attr("id") == element.attr("id")) {
                    console.log("in updateWidth", obj.resizeInitiatorWidth, obj.width);

                    var containerWidth = $('.uiViewContentCanvas').width(),
                        minimumWidth;
                    if (scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth) {
                        minimumWidth = scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth;
                    }
                    else if (scope.widgetdetails.actualViewConfig.resizedMinimumWidth) {
                        minimumWidth = scope.widgetdetails.actualViewConfig.resizedMinimumWidth
                    } else {
                        minimumWidth = scope.widgetdetails.actualViewConfig.minimumWidth;
                    }

                    // increase
                    if ( containerWidth - obj.resizeInitiatorWidth  > minimumWidth) {
                        // acoomodate scrol bar too - 20px difference 
                        minimumWidth = containerWidth - obj.resizeInitiatorWidth - 20;
                    } else {
                        minimumWidth -= obj.width;
                    }
                    
                    if (minimumWidth < 300) {
                        minimumWidth = 300;
                    }
                    else if (minimumWidth > containerWidth)  {
                        minimumWidth = containerWidth;
                    }

                    if (!scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth) {
                        if (scope.widgetdetails.actualViewConfig.resizedMinimumWidth) {
                            scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth = scope.widgetdetails.actualViewConfig.resizedMinimumWidth;
                        }
                        if (scope.widgetdetails.actualViewConfig.minimumWidth) {
                            scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth = scope.widgetdetails.actualViewConfig.minimumWidth;
                        }
                    }

                    scope.widgetdetails.actualViewConfig.resizedMinimumWidth = minimumWidth;
            
                    // set the correct min width
                    $(element).css('min-width', minimumWidth);

                    element[0].offsetWidth;

                    scope.setDeviceSpecificClass(minimumWidth);

                    WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);
                }
            });
            
            // registers the unwatched watches
            scope.$on('resumeWatches', function() {
                console.log("in resumeWatches");
                addWatches();
            });

            // when ever the window is resized we need to accomodate the widgets to the newly available space
            angular.element($window).on('resize', function(e) {
            	if (e.target !== window || scope.widgetdetails.isMaximized) 
                    return;

                var containerWidth = $('.uiViewContentCanvas').width(),
                    minimumWidth = 0;

            	// need to calculate at next render
            	$timeout(function() {
                    if (scope.widgetdetails.actualViewConfig.resizedMinimumWidth) {
                        minimumWidth = scope.widgetdetails.actualViewConfig.resizedMinimumWidth;
                    } else {
                        minimumWidth = scope.widgetdetails.actualViewConfig.minimumWidth;
                    }

                    if (minimumWidth > containerWidth) {
                        if (containerWidth < 540) {
                            minimumWidth = 300;
                        } else {
                            minimumWidth = containerWidth;
                        }
                    }

                    // set the correct min width
                    $(element).css('min-width', minimumWidth);
                    // $(element).css('flex-basis', minimumWidth);

                    var dim = scope.getElementDimensions();
                    if (dim.width > 0) {
                        if (dim.width > containerWidth) {
                            scope.setDeviceSpecificClass(minimumWidth);
                        } else {
                            scope.setDeviceSpecificClass(dim.width);
                        }
                    }

                }, 0);
            });

            
            // load the template
            var loadTemplate = function() {
                if (scope.widgetdetails) {
                    var templateLocation = 'views/widgets/widget-wrapper.html';
                    http.get(templateLocation).then(function(response) {
                        element.html(compile(response.data)(scope));
                    });
                }
            };

            // listens for updateWidgetInstanceList
            scope.$on('updateWidgetInstanceList', function(event, args) {
                if (args.indexOf(scope.$parent.$index) != -1) {
                    console.log("in updateWidgetInstanceList");
                    loadTemplate();
                }
            });

            // re renders the widget
            var reRenderWiget = rootScope.$on('reRenderWiget', function(event) {
                // load the template, so as the widget is rendered properly
                loadTemplate();
            });

            scope.$on('$destroy', function() {
                // un register the event handler
                reRenderWiget();
            });

            // load the template
            loadTemplate();

        };
        return {
            restrict: 'A',
            scope: {
                widgetdetails: "=",
                widgetInstanceList: '='
            },
            replace: true,
            link: linkFunction,
            controller: 'WidgetController'
        };
    }
]);