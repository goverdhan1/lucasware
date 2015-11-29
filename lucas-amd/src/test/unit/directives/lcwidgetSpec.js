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

describe('Unit testing lcwidget directive', function () {
    var $compile;
    var $rootScope;
    var localElement;
    var $httpBackend;
    var $interval;
    var mockWidgetController;
    var timeout;
    var localWidgetService;

    beforeEach(module("amdApp", function ($provide) {
        mockWidgetController = {};
        $provide.value("WidgetController", mockWidgetController);
    }));

    beforeEach(inject(function (_$compile_, _$rootScope_, _$interval_, _$httpBackend_, $timeout, WidgetService) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;
        $interval = _$interval_;
        timeout = $timeout;
        localWidgetService = WidgetService;

        // Set up the mock http service responses
        $httpBackend = _$httpBackend_;
        // Handle all the POST requests
        $httpBackend.when('GET').respond({data: {}});

        $rootScope.widgetdetails = {
            clientId: 61,
            isMaximized: false,
            updateWidget: true,
            data: [
                {
                    chart: [
                        {
                            color: "#d62728",
                            key: "Completed",
                            values: [
                                {
                                    label: "Wave1", value: "300"
                                },
                                {
                                    label: "Wave2", value: "455"
                                },
                                {
                                    label: "Wave3", value: "367"
                                },
                                {
                                    label: "Wave4", value: "407"
                                }
                            ]
                        },
                        {
                            color: "#1f77b4",
                            key: "Total",
                            values: [
                                {
                                    label: "Wave1", value: "350"
                                },
                                {
                                    label: "Wave2", value: "470"
                                },
                                {
                                    label: "Wave3", value: "390"
                                },
                                {
                                    label: "Wave4", value: "459"
                                }
                            ]
                        }
                    ]
                }
            ],
            actualViewConfig: {
                anchor: [0, 0],
                height: 500,
                width: 600,
                zindex: 0
            },
            widgetDefinition: {
                id: 11,
                broadcastMap: {
                    Completed: "series.key",
                    Wave: "point.label"
                },
                dataURL: {

                },
                name: "assignment-management-piechart-widget",
                shortName: "PicklineByWave",
                subtype: "CHART_BAR",
                type: "GRAPH_2D",
                actualViewConfig: {
                    anchor: [0, 0],
                    height: 500,
                    width: 600,
                    zindex: 0
                }
            }
        };
        localElement = $compile("<div lcwidget widgetdetails='widgetdetails'></div>")($rootScope);
        $httpBackend.flush();
        $rootScope.$digest();
    }));

    // Verifies that all of the requests defined via the expect api were made.
    // If any of the requests were not made, verifyNoOutstandingExpectation throws an exception.
    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('Should create dragandresize directive', function () {
        expect(localElement.length).toBeGreaterThan(0);
    });

    it('Should create handle click event', function () {
        spyOn(localElement, 'trigger').andCallThrough();
        localElement.trigger('click');
        expect(localElement.trigger).toHaveBeenCalled();
    });

    it('should handle getElementDimensions() method', function() {
        $(localElement).outerWidth(400);
        $(localElement).outerHeight(600);
        var isolatedScope = localElement.isolateScope();
        expect(isolatedScope.getElementDimensions()).toEqual({
            width: 400,
            height: 600
        });
    });

    it('should handle setDeviceSpecificClass() method', function() {
        var isolatedScope = localElement.isolateScope();
        isolatedScope.setDeviceSpecificClass(600);
        expect(localElement).toHaveClass('device-desktop');
        isolatedScope.setDeviceSpecificClass(360);
        expect(localElement).toHaveClass('device-tablet');
        isolatedScope.setDeviceSpecificClass(240);
        expect(localElement).toHaveClass('device-mobile');
    });

    it('should handle stopWatches event ', function() {
        var isolatedScope = localElement.isolateScope();
        spyOn(isolatedScope, 'watcher').andCallThrough();
        $rootScope.$broadcast('stopWatches');
        expect(isolatedScope.watcher).toHaveBeenCalled();
    });

    it('should handle refreshOriginalResizedMinimumWidth event ', function() {
        var isolatedScope = localElement.isolateScope();
        isolatedScope.widgetdetails.actualViewConfig.originalResizedMinimumWidth = 300;
        $rootScope.$broadcast('refreshOriginalResizedMinimumWidth');
        expect($(localElement).css('min-width')).toEqual('300px');
    });

    it('should handle resize event', function() {
        var isolatedScope = localElement.isolateScope();
        isolatedScope.widgetdetails.actualViewConfig.resizedMinimumWidth = 400;
        $('<div class="uiViewContentCanvas" />').appendTo("body").width(500);
        $(window).trigger('resize');
        timeout.flush();
        expect($(localElement).css('min-width')).toEqual('400px');
    });

    it('should handle updateWidth event', function() {
        var isolatedScope = localElement.isolateScope();
        isolatedScope.widgetdetails.actualViewConfig.originalResizedMinimumWidth = 400;
        spyOn(localWidgetService, 'updateFavoriteCanvasListLocalStorage').andCallThrough();
        $('<div class="uiViewContentCanvas" />').appendTo("body").width(500);
        $rootScope.$broadcast('updateWidth', {width: 400, element: localElement});
        expect(localWidgetService.updateFavoriteCanvasListLocalStorage).toHaveBeenCalled();
        expect($(localElement).css('min-width')).toEqual('300px');
        expect(localElement).toHaveClass('device-mobile');
    });

     it('should handle reRenderWiget event', function() {
        // spy on $emit
        spyOn($rootScope, '$emit').andCallThrough();
        $rootScope.$emit('reRenderWiget');
        // flush the requests 
        $httpBackend.flush();
        // asser $emit event is called
        expect($rootScope.$emit).toHaveBeenCalled();
     });
});