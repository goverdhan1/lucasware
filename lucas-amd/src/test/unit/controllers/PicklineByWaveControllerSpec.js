'use strict';

describe('PicklineByWave Controller Unit Tests', function () {

    // Global vars
    var controller = null;
    var scope = null;
    var WidgetChartService = null;
    var UtilsService = null;
    var LocalStoreService = null;
    var mockWidgetInstance = null;

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function(_WidgetChartService_, _UtilsService_, _LocalStoreService_) {
        //setup dependencies
        WidgetChartService = _WidgetChartService_;
        UtilsService = _UtilsService_;
        LocalStoreService = _LocalStoreService_;

        //setup spies
        spyOn(WidgetChartService, 'processLineChartData').andReturn();
        spyOn(LocalStoreService, 'getLSItem').andReturn();
        spyOn(UtilsService, 'processBroadcastMap').andReturn();
        spyOn(UtilsService, 'processSelectedItemsAsArray').andReturn();

        mockWidgetInstance = {
            "data": {
                "data": {
                    "chart": {
                        "Complete": [
                            {
                                "value": "26",
                                "label": "Wave1"
                            },
                            {
                                "value": "12",
                                "label": "Wave2"
                            },
                            {
                                "value": "24",
                                "label": "Wave3"
                            },
                            {
                                "value": "13",
                                "label": "Wave4"
                            }
                        ],
                        "Total": [
                            {
                                "value": "26",
                                "label": "Wave1"
                            },
                            {
                                "value": "24",
                                "label": "Wave2"
                            },
                            {
                                "value": "25",
                                "label": "Wave3"
                            },
                            {
                                "value": "25",
                                "label": "Wave4"
                            }
                        ]
                    }
                }

            },
            "widgetDefinition": {
                "id": 10,
                "name": "pickline-by-wave-barchart-widget",
                "shortName": "PicklineByWave",
                "type": "GRAPH_2D",
                "subtype": "CHART_BAR",
                "broadCastMap": {
                    "Completed": "series.key",
                    "Wave": "point.label"
                },
                "dataURL": {
                    "url": "/waves/picklines",
                    "searchcriteria": {
                        "searchmap": {}
                    }
                },
                "defaultViewConfig": {
                    "anchor": [
                        0,
                        0
                    ],
                    "height": 500,
                    "width": 600,
                    "zindex": 1,
                    "orientation": {
                        "option": [
                            {
                                "legend": "horizontal",
                                "value": "h"
                            },
                            {
                                "legend": "vertical",
                                "value": "v"
                            }
                        ],
                        "selected": "h"
                    }
                },
                "listensForList": [
                    "Area",
                    "Wave",
                    "Score"
                ]
            },
            "actualViewConfig": {
                "anchor": [
                    0,
                    0
                ],
                "height": 500,
                "width": 600,
                "zindex": 1,
                "orientation": {
                    "option": [
                        {
                            "legend": "horizontal",
                            "value": "h"
                        },
                        {
                            "legend": "vertical",
                            "value": "v"
                        }
                    ],
                    "selected": "h"
                }
            },
            "updateWidget": true,
            "clientId": 100,
            "isMaximized": false
        };
    }));

    //Controller instantiation test
    describe('PickLineByWaveController instantiation spec', function() {
        beforeEach(inject(function(_$controller_, _$rootScope_) {

            //assign widgetInstance to scope
            scope = _$rootScope_.$new();
            scope.widgetdetails = mockWidgetInstance;

            //inject controller with dependencies
            controller = _$controller_('PicklineByWaveController', {
                $scope : scope,
                WidgetChartService : WidgetChartService,
                UtilsService : UtilsService,
                LocalStoreService : LocalStoreService
            });
        }));

        it('should store client id from widgetInstance', function(){
            expect(controller.staticClientId).toEqual(mockWidgetInstance.clientId);
        });

        it('should set orientation from widgetInstance', function() {
            expect(scope.orientation).toEqual(mockWidgetInstance.widgetDefinition.defaultViewConfig.orientation.selected);
        });
    });

    //set default chart orientation when missing property orientation.selected
    describe('default chart orientation when orientation.selected property missing', function() {
        beforeEach(inject(function(_$controller_, _$rootScope_) {

            //assign widgetInstance to scope
            scope = _$rootScope_.$new();
            scope.widgetdetails = mockWidgetInstance;

            //null out orientation attribute to simulate missing property
            scope.widgetdetails.actualViewConfig.orientation.selected = null;

            //inject controller with dependencies
            controller = _$controller_('PicklineByWaveController', {
                $scope : scope,
                WidgetChartService : WidgetChartService,
                UtilsService : UtilsService,
                LocalStoreService : LocalStoreService
            });
        }));

        it('should default orientation to Vertical when missing from widgetInstance', function() {
            expect(scope.orientation).toEqual('v');
        });
    });

    //updating the orientation based on cog menu selection
    describe('reacting to cog menu orientation changes', function() {

        beforeEach(inject(function(_$controller_, _$rootScope_) {

            //assign widgetInstance to scope
            scope = _$rootScope_.$new();
            scope.widgetdetails = mockWidgetInstance;

            //watch $scope.$on() to make sure it was called
            spyOn(scope, '$on').andCallThrough();

            scope.widgetdetails = mockWidgetInstance;

            //inject controller with dependencies
            controller = _$controller_('PicklineByWaveController', {
                $scope : scope,
                WidgetChartService : WidgetChartService,
                UtilsService : UtilsService,
                LocalStoreService : LocalStoreService
            });
        }));

        it('should react to orientation change on cog menu', function() {
            //simulate change in orientation; from Horizontal to Vertical
            var broadcastInstance = mockWidgetInstance;
            broadcastInstance.actualViewConfig.orientation.selected = 'v';
            
            scope.$broadcast('updateExistingWidget', mockWidgetInstance);
            expect(scope.$on).toHaveBeenCalled();
            expect(scope.widgetdetails.actualViewConfig.orientation.selected).toEqual('v');
        });

        it('should not react to broadcasts made by other widgets', function() {
            //simulate broadcast from another widget by changing the clientId
            var broadcastInstance = mockWidgetInstance;

            broadcastInstance.clientId = 987;
            broadcastInstance.actualViewConfig.orientation.selected = 'v';

            scope.$broadcast('updateExistingWidget', broadcastInstance);
            expect(scope.$on).toHaveBeenCalled();
            expect(scope.orientation).toEqual('h'); //orientation should not have changed to 'v'
        });
    });
});