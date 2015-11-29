"use strict";

describe("WidgetDetail Controller Unit Tests", function () {
    // Global variables
    var localWidgetDetailController = null;
    var rootScope;
    var localScope = null;
    var localLocalStoreService = null;
    var localWidgetGridService = null;
    var localWidgetService;

    beforeEach(module('amdApp'));

    beforeEach(inject(function ($controller, $rootScope, LocalStoreService, WidgetService) {
        rootScope = $rootScope;
        localScope = $rootScope.$new();
        localLocalStoreService = LocalStoreService;
        localLocalStoreService.addLSItem(localScope, 'currentWidgetInstance', {
            widgetDefinition: {
                type: "FORM"
            }
        });
        localWidgetGridService = {
            handleGridOrder: function () {
            },
            constructGrid: function (obj) {
                return [obj];
            },
            constructNewGridData: function () {
            }
        };
        localWidgetService = WidgetService;

        localLocalStoreService.addLSItem(null, 'ActiveWidgetId', 123);

        localWidgetDetailController = $controller('WidgetDetailController', {
            $scope: localScope,
            WidgetGridService: localWidgetGridService,
            WidgetService: localWidgetService
        });
    }));

    describe('Should handle Widget type "Grid"', function () {
        beforeEach(inject(function ($controller, $rootScope, LocalStoreService) {
            localScope = $rootScope.$new();
            localLocalStoreService = LocalStoreService;
            localLocalStoreService.addLSItem(localScope, "3|3|GridUpdatedItems", {});
            localLocalStoreService.addLSItem(localScope, 'currentWidgetInstance', {
                widgetDefinition: {
                    type: "FORM"
                }
            });
            localWidgetDetailController = $controller('WidgetDetailController', {
                $scope: localScope,
                WidgetGridService: localWidgetGridService
            });
            localWidgetDetailController = $controller('WidgetDetailController', {
                $scope: localScope,
                WidgetGridService: localWidgetGridService
            });
        }));
        /*it('Should handle Widget type "FORM"', function () {
            expect(localScope.formDetail).toBe(true);
        });*/
    });

    describe("Should handle ngGridEventColumns event", function () {
        beforeEach(inject(function ($controller, $rootScope, LocalStoreService) {
            localScope = $rootScope.$new();
            localLocalStoreService = LocalStoreService;
            localLocalStoreService.addLSItem(localScope, 'currentWidgetInstance', {
                widgetDefinition: {
                    type: "GRID"
                },
                data: {
                    grid: {
                    }
                }
            });
            localWidgetDetailController = $controller('WidgetDetailController', {
                $scope: localScope,
                WidgetGridService: localWidgetGridService
            });
        }));
        it("Should handle ngGridEventColumns event", function () {
            spyOn(localScope, '$broadcast').andCallThrough();
            localScope.$broadcast('ngGridEventColumns');
            expect(localScope.$broadcast).toHaveBeenCalledWith('ngGridEventColumns');
        });
    });

    describe('Should handle Widget type "Grid"', function () {
        beforeEach(inject(function ($controller, $rootScope, LocalStoreService) {
            localScope = $rootScope.$new();
            localLocalStoreService = LocalStoreService;
            localLocalStoreService.addLSItem(localScope, "3|3|GridUpdatedItems", {});
            localLocalStoreService.addLSItem(localScope, 'currentWidgetInstance', {
                data: {
                    grid: {},
                    chartType: "multi-bar-vertical",
                    chartValues: [
                        {
                            key: "Freezer",
                            values: [
                                {
                                    label: "Route505",
                                    series: 0,
                                    value: 10
                                },
                                {
                                    label: "Route506",
                                    series: 0,
                                    value: 20
                                },
                                {
                                    label: "Route507",
                                    series: 0,
                                    value: 30
                                }
                            ]
                        }
                    ]
                },
                id: 3,
                actualViewConfig: {
                    width: 200,
                    height: 200
                },
                widgetDefinition: {
                    broadCastMap: {},
                    broadcastMap: {},
                    id: 3,
                    listensForList: [],
                    name: "assignment-creation-widget",
                    shortName: "Alert",
                    subtype: "CHART_PIE",
                    type: "GRID",
                    widgetActionConfig: {
                        anchor: [0, 863],
                        height: 375,
                        orientation: {
                            option: null,
                            selected: null
                        },
                        width: 485,
                        zindex: 0
                    }
                }

            });
            localWidgetDetailController = $controller('WidgetDetailController', {
                $scope: localScope,
                WidgetGridService: localWidgetGridService
            });
        }));
        /*it('Should handle Widget type "Grid"', function () {
            expect(localScope.gridDetail).toBe(true);
        });*/
    });

    describe('Should handle Widget type "GRAPH_2D"', function () {
        beforeEach(inject(function ($controller, $rootScope, LocalStoreService) {
            localScope = $rootScope.$new();
            localLocalStoreService = LocalStoreService;
            localLocalStoreService.addLSItem(localScope, 'currentWidgetInstance', {
                data: {
                    chartType: "multi-bar-vertical",
                    chartValues: [
                        {
                            key: "Freezer",
                            values: [
                                {
                                    label: "Route505",
                                    series: 0,
                                    value: 10
                                },
                                {
                                    label: "Route506",
                                    series: 0,
                                    value: 20
                                },
                                {
                                    label: "Route507",
                                    series: 0,
                                    value: 30
                                }
                            ]
                        }
                    ]
                },
                id: 3,
                actualViewConfig: {
                    width: 200,
                    height: 200
                },
                widgetDefinition: {
                    broadCastMap: {},
                    broadcastMap: {},
                    id: 3,
                    listensForList: [],
                    name: "assignment-creation-widget",
                    shortName: "Alert",
                    subtype: "CHART_PIE",
                    type: "GRAPH_2D",
                    widgetActionConfig: {
                        anchor: [0, 863],
                        height: 375,
                        orientation: {
                            option: null,
                            selected: null
                        },
                        width: 485,
                        zindex: 0
                    }
                }

            });
            localWidgetDetailController = $controller('WidgetDetailController', {
                $scope: localScope,
                WidgetGridService: localWidgetGridService
            });
        }));
        /*it('Should handle Widget type "GRAPH_2D"', function () {
            expect(localScope.chartDetail).toBe(true);
        });*/
    });


    it("Should handle handleMinState method", function() {
        spyOn(localScope, 'handleMinState').andCallThrough();
        localScope.handleMinState();
        expect(localScope.handleMinState).toHaveBeenCalled();
    });

    it("Should handle handleCogWidget method", function() {
        // spy on methods
        spyOn(localScope, 'handleCogWidget').andCallThrough();
        spyOn(localWidgetService, 'loadCogMenuPopup').andReturn();
        // call handleCogWidget method
        localScope.handleCogWidget({clientId: 101});
        // test the results
        expect(localScope.handleCogWidget).toHaveBeenCalled();
        expect(localLocalStoreService.getLSItem('ActiveWidgetId')).toEqual(101);
        expect(localWidgetService.loadCogMenuPopup).toHaveBeenCalled();
    });

    it("Should handle handleRemoveWidget method", function() {
        // spy on methods
        spyOn(localScope, 'handleRemoveWidget').andCallThrough();
        spyOn(localWidgetService, 'loadDeleteWidgetPopup').andReturn();
        // call handleRemoveWidget method
        localScope.handleRemoveWidget({clientId: 101});
        // test the results
        expect(localScope.handleRemoveWidget).toHaveBeenCalled();
        expect(localLocalStoreService.getLSItem('ActiveWidgetId')).toEqual(101);
        expect(localWidgetService.loadDeleteWidgetPopup).toHaveBeenCalled();
    });

    it('Should handle RemoveWidget event', function() {
        rootScope.$emit('RemoveWidget', 0, 123);
        expect(localScope.widgetdetails).toEqual(null);
    });

});