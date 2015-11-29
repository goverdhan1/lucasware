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

amdApp.controller('WidgetsPalletteModalController', ['$rootScope', '$scope', '$log', '$modalInstance', 'WidgetsPalletteService', 'WidgetDefinitionService', 'LocalStoreService', 'UtilsService',
    function (rootScope, scope, log, modalInstance, WidgetsPalletteService, WidgetDefinitionService, LocalStoreService, UtilsService) {
        // close the modal
        scope.closeModal = function () {
            modalInstance.close();
        };

        scope.selectWidget = function (selectedWidget) {
            // broadcast resumeWatches event
            rootScope.$broadcast("resumeWatches");
            console.log(selectedWidget);
            scope.closeModal();
            
            WidgetDefinitionService.getWidgetDefinition(selectedWidget.id, selectedWidget.name, selectedWidget.type).then(function (response) {
                modalInstance.close();
                var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                $state.go('canvases.detail', {
                    canvasId: activeCanvasId
                }, {
                    location: true
                });
                // refresh original resized min width
                rootScope.$broadcast("refreshOriginalResizedMinWidth");
            }, function (error) {
                log.error("error: " + error);
            });

        };

        scope.widgetFactory = function (widgetName) {
            var factoryWidget = {};
            factoryWidget.name = widgetName + "-grid-widget";
            widgetName = UtilsService.ucFirstAllWords(widgetName.split('-').join(' '));
            widgetName = widgetName.split(' ').join('');
            factoryWidget.id = UtilsService.getRandomInt(150, 200);
            factoryWidget.description = "GRID-" + widgetName + "Widget";
            factoryWidget.shortName = widgetName;
            factoryWidget.title = widgetName;
            factoryWidget.type = "GRID";
            return factoryWidget;
        }

        // get the widgets
        WidgetsPalletteService.getWidgets().then(function (response) {
            scope.availableWidgets = response;
            //Mock data for Pack Factor Hierarchy widget starts here
            scope.availableWidgets["Work Execution"].push(scope.widgetFactory('pack-factor-hierarchy'));

            scope.availableWidgets["EAI"] = [];

            // PHX-1204 Mocking the data till we have a endpoint available
            scope.availableWidgets.EAI.push(scope.widgetFactory('eai-message-segments'));

            // PHX-1207 Mocking the data for EAI Messages till we have a endpoint available
            scope.availableWidgets.EAI.push(scope.widgetFactory('eai-messages'));

            // PHX-1208 Mocking the data for EAI Message Mappings till we have a endpoint available
            scope.availableWidgets.EAI.push(scope.widgetFactory('eai-message-mappings'));

            // PHX-1210 Mocking the data for EAI Event Handlers till we have a endpoint available
            scope.availableWidgets.EAI.push(scope.widgetFactory('eai-event-handlers'));

            // PHX-1211 Mocking the data for EAI Events till we have a endpoint available
            scope.availableWidgets.EAI.push(scope.widgetFactory('eai-events'));

            // Equipment type mock data
            scope.availableWidgets["Work Execution"].push(scope.widgetFactory('equipment-type'));
        });
    }]);