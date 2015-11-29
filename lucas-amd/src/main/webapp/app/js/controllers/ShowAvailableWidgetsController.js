'use strict';

amdApp.controller('ShowAvailableWidgetsController', ['$scope', '$state', 'WidgetDefinitionService', '$modalInstance', 'ShowAvailableWidgetsService', 'LocalStoreService', '$log',
    function (scope, state, WidgetDefinitionService, modalInstance, ShowAvailableWidgetsService, LocalStoreService, log) {
        ShowAvailableWidgetsService.getWidgetsByCategory().then(function (response) {
            scope.availableWidgets = response;
            var mockchartArr = {
                "Pie Charts": [
                    {
                        "id": 11,
                        "name": "assignment-management-piechart-widget",
                        "shortName": "AssignmentManagement",
                        "type": "GRAPH_2D",
                        "subtype": "CHART_PIE",
                        "title": "Assignment Management"
                    }
                ]
            };
            scope.availableWidgets["Pie Chart"] = mockchartArr["Pie Charts"];

            if (!scope.availableWidgets['User Management']) {
                scope.availableWidgets['User Management'] = [];
            }
            scope.availableWidgets['User Management'].push({
                id: 13,
                name: "user-group-widget",
                shortName: "UserGroup",
                subtype: "FORM",
                title: "User Group Maintenance",
                type: "FORM"
            });

        }, function (error) {
            log.error("error: " + error);
        });

        scope.selectWidget = function (widgetId, widgetName, widgetType, widgetSubType) {
            //console.log(widgetType);
            widgetType = 'GRAPH_2D';

            WidgetDefinitionService.getWidgetDefinition(widgetId, widgetName, widgetType, widgetSubType).then(function (response) {
                modalInstance.close();
                var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                state.go('canvases.detail', {
                    canvasId: activeCanvasId
                }, {
                    location: true
                });
            }, function (error) {
                log.error("error: " + error);
            });
        };
        scope.cancel = function () {
            modalInstance.close();
        };
        scope.getImagePath = function (widget) {
            // Remove subtype or type, based on which is not used in other places
            if (widget.subtype) {
                return "images/" + widget.subtype.toLowerCase() + ".png";
            } else if (widget.type) {
                return "images/" + widget.type.toLowerCase() + ".png";
            }
        };
    }]);