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

describe('Unit testing widgetloader directive', function () {
    var $compile;
    var $rootScope;
    var deferred;
    var localElement;
    var httpMock;

    beforeEach(module("amdApp"));

    beforeEach(inject(function (_$compile_, _$rootScope_, _$httpBackend_) {
        $compile = _$compile_;
        $rootScope = _$rootScope_;

        // Set up the mock http service responses
        httpMock = _$httpBackend_;
        // Handle all the POST requests
        httpMock.when('GET').respond({data: {}});

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

        localElement = $compile('<widgetloader widgetdetails="widgetdetails"></widgetloader>')($rootScope);
        $rootScope.$digest();
        httpMock.flush();
    }));

    // Verifies that all of the requests defined via the expect api were made.
    // If any of the requests were not made, verifyNoOutstandingExpectation throws an exception.
    afterEach(function() {
        httpMock.verifyNoOutstandingExpectation();
        httpMock.verifyNoOutstandingRequest();
    });

    it('Should create widgetloader directive', function () {
        expect(localElement.length).toBeGreaterThan(0);
    });

    it('test all of the required flex box classes are added', function() {
        // test the presence of css classes
        expect(localElement).toHaveClass('display-flex');
        expect(localElement).toHaveClass('display-flex-column');
        expect(localElement).toHaveClass('flex1');
    });

    it('should handle setDeviceSpecificClass() method', function() {
        var isolatedScope = localElement.isolateScope();
        // test desktop
        isolatedScope.setDeviceSpecificClass(600);
        expect(localElement).toHaveClass('device-desktop');
        // test tablet
        isolatedScope.setDeviceSpecificClass(360);
        expect(localElement).toHaveClass('device-tablet');
        // test mobile
        isolatedScope.setDeviceSpecificClass(240);
        expect(localElement).toHaveClass('device-mobile');
    });

    it('should handle resize event', function() {
        var isolatedScope = localElement.isolateScope();
        isolatedScope.widgetdetails.isMaximized = true;
        $('<div class="uiViewContentCanvas" />').appendTo("body");
        // test tablet
        $('.uiViewContentCanvas').width(500);
        $(window).trigger('resize');
        expect(localElement).toHaveClass('device-tablet');
        // test mobile
        $('.uiViewContentCanvas').width(300);
        $(window).trigger('resize');
        expect(localElement).toHaveClass('device-mobile');
        // test desktop
        $('.uiViewContentCanvas').width(700);
        $(window).trigger('resize');
        expect(localElement).toHaveClass('device-desktop');
    });
});