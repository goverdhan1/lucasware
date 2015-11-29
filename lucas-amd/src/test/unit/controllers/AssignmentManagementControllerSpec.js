'use strict';

describe('AssignmentManagement Controller Unit Tests', function () {

    // Global vars
    var localScope = null;
    var deferred = null;
    var localAssignmentManagementController = null;
    var mockWidgetChartService = null;
    var mockUtilsService = null;
    var mockLocalStoreService = null;

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


    beforeEach(inject(function ($injector, $controller, $rootScope, $httpBackend) {
            // Set up the mock http service responses
            $httpBackend = $injector.get('$httpBackend');
            // Handle all the POST requests
            $httpBackend.when('GET').respond({});

            localScope = $rootScope.$new();

            localScope.widgetdetails = {
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
                    broadcastList: {
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

            mockLocalStoreService = {
                getLSItem: function () {
                    return [0, 0];
                }
            };

            mockUtilsService = {
                processBroadcastMap: function (event, broadcastMap) {
                    return [broadcastMap];
                },
                processSelectedItemsAsArray: function (getAreaArr, listensForList) {
                    return [getAreaArr];
                }
            };

            localAssignmentManagementController = $controller('AssignmentManagementController', {
                $scope: localScope,
                LocalStoreService: mockLocalStoreService,
                UtilsService: mockUtilsService
            });
            spyOn(localScope, "$broadcast").andCallThrough();
        })
    );


    it('Should call the event 81|pickline-by-wave-barchart-widget|10|ViewDimensions', function () {
        localScope.$broadcast(localScope.widgetdetails.clientId + "|" + localScope.widgetdetails.widgetDefinition.name + "|" + localScope.widgetdetails.widgetDefinition.id + "|ViewDimensions", {width: 100, height: 100});
        expect(localScope.$broadcast).toHaveBeenCalledWith(localScope.widgetdetails.clientId + "|" + localScope.widgetdetails.widgetDefinition.name + "|" + localScope.widgetdetails.widgetDefinition.id + "|ViewDimensions", {width: 100, height: 100});
    });

    it('Should call the xFunctionPie method', function () {
        expect(localScope.xFunctionPie()({key: "test"})).toBe("test");
    });

    it('Should call the yFunctionPie  method', function () {
        expect(localScope.yFunctionPie()({y: "test"})).toBe("test");
    });

    // Handling destroy method
    it('Should call the destroy method', function () {
        localScope.$broadcast("$destroy");
        expect(localScope.$broadcast).toHaveBeenCalledWith("$destroy");
    });

    // Handling elementClick.directive
    it('Should handle elementClick.directive', function () {
        localScope.$broadcast("elementClick.directive");
        expect(localScope.$broadcast).toHaveBeenCalledWith("elementClick.directive");
    });

    describe("Should handle isMaximized ", function () {
        beforeEach(inject(function ($controller) {
            localScope.widgetdetails.isMaximized = true;
            spyOn($.fn, "height").andReturn(300);
            localAssignmentManagementController = $controller('AssignmentManagementController', {
                $scope: localScope,
                LocalStoreService: mockLocalStoreService,
                UtilsService: mockUtilsService
            });

        }));

        it("Should check chartHeight", function () {
            expect(localScope.chartHeight).toBe(300);
        });
    });

});