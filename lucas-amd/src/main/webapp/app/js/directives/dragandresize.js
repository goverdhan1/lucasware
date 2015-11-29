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
amdApp.directive('dragandresize', ['$document', 'UtilsService', 'LocalStoreService', '$rootScope', 'WidgetService', 'SecurityService', '$timeout', 'HomeCanvasListService',
    function ($document, UtilsService, LocalStoreService, rootScope, WidgetService, SecurityService, $timeout, HomeCanvasListService) {
        return {
            restrict: 'A',
            link: function (scope, elem, attr) {
                var widgetsWidth=0,
                    rowCount=0,
                    columnCount=0,
                    resizingWidget=0,
                    start=0,
                    end=0,
                    space=0,
                    activeRow=0,
                    spaceAvailable=false,
                    widgetsInRow=[];

                // delay till next render as we are attaching the events to the parent element
                $timeout(function () {
                    var isCreateCanvas = SecurityService.hasPermission('create-canvas');

                    // Remove old instace data if any to reinitialize the jquery ui resizable properly
                    $.removeData( elem.parent()[0], 'ui-resizable' );

                    // attach the event to the parent element (ng-repeat is the parent we need to target)
                    elem.parent().resizable({
                        containment: '#detailCont',
                        handles: "n, e, w, s",
                        helper: "resizable-helper",
                        start: function(event, ui) {
                            console.log("in start");
                            // broadcast stopWatches event
                            rootScope.$broadcast("stopWatches");
                        },
                        
                        stop: function (event, ui) {
                        console.log("in stop");
                        var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId'),
                            favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated'),
                            activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasList, activeCanvasId),
                            containerWidth = $('.uiViewContentCanvas').width();

                            // broadcast resumeWatches event
                            rootScope.$broadcast("resumeWatches");

                            var gridSelector = "#" + attr.id + " .ngViewport";
                            var formSelector = "#" + attr.id + " form";

                            var minimumWidth;

                            if (containerWidth >= 540) {
                                minimumWidth = 540;
                            } else {
                                minimumWidth = 300;
                            }

                            if (!scope.widgetdetails.actualViewConfig.originalMinimumHeight) {
                                scope.widgetdetails.actualViewConfig.originalMinimumHeight = scope.widgetdetails.actualViewConfig.minimumHeight; 
                            }

                            scope.widgetdetails.actualViewConfig.originalResizedMinimumWidth = scope.widgetdetails.actualViewConfig.resizedMinimumWidth = Math.max(ui.size.width, 300);
                            
                            // store the axis (resized  direction)
                            var axis = $(this).data('uiResizable').axis;
                            // find when the height is changed 
                            if (axis == 'n' || axis == 's') {
                                if (ui.size.height != ui.originalSize.height) {
                                    scope.widgetdetails.actualViewConfig.resizedMinimumHeight = Math.max(ui.size.height, scope.widgetdetails.actualViewConfig.originalMinimumHeight);
                                }
                            }

                            // cannot resize below 300
                            if (ui.size.width < 300)
                                ui.size.width = 300;

                            var resizedWidth = ui.size.width - ui.originalSize.width;
                            
                            // when the width is reduced make the other widgets occupy the available space
                            if (resizedWidth < 0) {


                        // logic to find widgets in each row.
                                                       
                        for (var i = 0; i < favoriteCanvasList.length; i++) {
                            if (favoriteCanvasList[i].canvasId == activeCanvasId) {
                                    if (favoriteCanvasList[i].widgetInstanceList) {
                                        $("#detailCont .tile").each(function(j) {
                                            columnCount++;
                                            favoriteCanvasList[i].widgetInstanceList[j].actualViewConfig.row=rowCount;
                                            favoriteCanvasList[i].widgetInstanceList[j].actualViewConfig.column=columnCount;
                                            widgetsWidth+=$(this).width();
                                                if(containerWidth == widgetsWidth){
                                                    widgetsInRow[rowCount]=columnCount;
                                                    rowCount++;
                                                    widgetsWidth=0;
                                                    columnCount=0;
                                                }
                                        });


                                    // logic to find active row and end element.
                                      for(var k=0; k < favoriteCanvasList[i].widgetInstanceList.length; k++)
                                        {
                                            if(favoriteCanvasList[i].widgetInstanceList[k].clientId==scope.widgetdetails.clientId)
                                            {
                                                activeRow=favoriteCanvasList[i].widgetInstanceList[k].actualViewConfig.row;
                                                end=k+(widgetsInRow[favoriteCanvasList[i].widgetInstanceList[k].actualViewConfig.row]-favoriteCanvasList[i].widgetInstanceList[k].actualViewConfig.column)+1;
                                            }
                                        }

                                        // logic to calculate space available.

                                            for(var n=1; n<widgetsInRow[activeRow]; n++)
                                            {
                                                if(favoriteCanvasList[i].widgetInstanceList[n].actualViewConfig.isResize)
                                                {
                                                    space+=favoriteCanvasList[i].widgetInstanceList[n].resizedMinimumWidth;
                                                }
                                                else
                                                {
                                                    space+=300;
                                                }
                                            }
                                                space=containerWidth-space;
                                                if(favoriteCanvasList[i].widgetInstanceList[end+1])
                                                {
                                                    if(space>=favoriteCanvasList[i].widgetInstanceList[end+1].resizedMinimumWidth)
                                                    {
                                                    spaceAvailable=true;
                                                    scope.widgetdetails.actualViewConfig.resizedMinimumWidth=space-favoriteCanvasList[i].widgetInstanceList[end+1].resizedMinimumWidth
                                                    }
                                                    else
                                                    {
                                                        scope.widgetdetails.actualViewConfig.resizedMinimumWidth=space;
                                                    }
                                                }


                                        // logic to note widget resized.
                                        for(var k=0; k < favoriteCanvasList[i].widgetInstanceList.length; k++)
                                        {
                                            if(favoriteCanvasList[i].widgetInstanceList[k].clientId==scope.widgetdetails.clientId)
                                            {
                                                favoriteCanvasList[i].widgetInstanceList[k].actualViewConfig.isResize=true;
                                            }
                                        }

                                    }
                                }
                            }



                            } 

                            // need to update min-width and min-height css properties
                            ui.element.css({
                                "min-width": scope.widgetdetails.actualViewConfig.resizedMinimumWidth,
                                "width": "auto",
                                "height": "auto",
                                "left": "auto",
                                "right": "auto",
                                "top": "auto",
                                "bottom": "auto"
                            });

                            // find when the height is changed 
                            if (axis == 'n' || axis == 's') {
                                if (ui.size.height != ui.originalSize.height) {
                                    ui.element.css({
                                        "min-height": scope.widgetdetails.actualViewConfig.resizedMinimumHeight
                                    });
                                }
                            }

                            //here you need to update the modified values of viewConfig against widgetInstanceObj.
                            //you pass in widgetInstanceObj to the below generic fn.
                            
                            var newDimensions = ui.size;

                            // update widgetInteractionConfig of current widget
                            for (var j = 0, l = activeCanvas.widgetInstanceList.length; scope.widgetInstanceList[j] && j < l; j++) {
                                if (scope.widgetdetails.clientId == activeCanvas.widgetInstanceList[j].clientId) {
                                    scope.widgetInstanceList[j].widgetInteractionConfig = activeCanvas.widgetInstanceList[j].widgetInteractionConfig;
                                    break;
                                }
                            }

                            WidgetService.updateFavoriteCanvasListLocalStorage(scope.widgetdetails);

                            //this broadcast is needed for chart widget
                            rootScope.$broadcast(scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.name + "|" + scope.widgetdetails.widgetDefinition.id + "|ViewDimensions", newDimensions);
                            LocalStoreService.addLSItem(scope, 'zIndexAnchor', scope.widgetdetails.clientId + "|" + scope.widgetdetails.widgetDefinition.id);

                            if (isCreateCanvas) {
                                rootScope.$broadcast('UpdateWidget', scope.widgetdetails);
                            }
                        }
                    });
                });
            }
        };
    }]);