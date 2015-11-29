'use strict';

describe('BarChart Controller Unit Tests', function () {

    // Global vars
    var localScope = null;
    var localBarChartController = null;
    var deferred = null;
    var mockWidgetChartService = null;
    var mockUtilsService = null;
    var mockLocalStoreService = null;
    var localStoreService = null;

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

    beforeEach(inject(function ($controller, $rootScope, $q) {
        mockWidgetChartService = {
            getChartData: function () {
                deferred = $q.defer();
                deferred.resolve({
                    data: "dummy data",
                    xValues: function () {
                        return 'label123'
                    },
                    yValues: function () {
                        return 'values123'
                    }
                });
                return deferred.promise;
            },
            processChartData: function (data) {
                return data;
            }
        };

        spyOn(mockWidgetChartService, 'getChartData').andCallThrough();

        localScope = $rootScope.$new();
        localScope.currentWidgetInstance = {
            id: "TestID",
            canvasId: "TestCanvasId",
            actualViewConfig: {
                width: "200",
                height: "200"
            },
            widget: {
                widgetActionConfig: {
                    broadcastMap: {
                        "Area" : "series.key",
                        "Route" : "point.label"
                    },
                    listensFor: ["Area", "Route", "Score"]
                }
            },
            widgetDefinition: {
                id: "TestID123"
                }
        };

        mockUtilsService = {
            processBroadcastMap: function (event, broadcastMap) {
                deferred = $q.defer();
                deferred.resolve();
                return deferred.promise;
            },
            processSelectedItemsAsArray: function (getAreaArr, listensFor) {
                deferred = $q.defer();
                deferred.resolve([
                    "TestItem1", "TestItem2"
                ]);
                return deferred.promise;
            }
        };

        mockLocalStoreService = {
            getLSItem : function () {
                return ['Morning Shift Canvas'];
            }
        };

        spyOn(mockLocalStoreService, 'getLSItem').andCallThrough();

        localBarChartController = $controller("BarChartController", {
            $scope: localScope,
            WidgetChartService: mockWidgetChartService,
            UtilsService: mockUtilsService,
            LocalStoreService: mockLocalStoreService
        });
    }));

    describe("Should handle getChartData method", function () {
        var data = {
            y: "TestY",
            x: "TestX"
        };

        it("Should call xValues method correctly", function () {
            var result = null;
            mockWidgetChartService.getChartData().then(function(response) {
               result = response.xValues();
            });
            localScope.$digest();
            expect(localScope.xValues).toBeDefined();
            expect(localScope.xValues()(data)).toBe(data.xValues);
        });

        it("Should call yValues method correctly", function () {
            var result = null;
            mockWidgetChartService.getChartData().then(function(response) {
                result = response.yValues();
            });
            localScope.$digest();
            expect(localScope.yValues).toBeDefined();
            expect(localScope.yValues()(data)).toBe(data.yValues);
        });
    });

    describe("Should handle dimensionsUpdateFn", function () {
        it("Should handle updateChartData method", function () {
            spyOn(localScope, "$broadcast").andCallThrough();
            localScope.$broadcast(localScope.currentWidgetInstance.id + "|" + localScope.currentWidgetInstance.canvasId + "|ViewDimensions", {width: 200, height: 200});
            localScope.$digest();
            expect(localScope.$broadcast).toHaveBeenCalledWith(localScope.currentWidgetInstance.id + "|" + localScope.currentWidgetInstance.canvasId + "|ViewDimensions", { width: 200, height: 200 });
            expect(mockWidgetChartService.getChartData).toHaveBeenCalled();
        });
    });


    describe("Should handle elementClick.directive event", function () {
        beforeEach(inject(function ($templateCache) {
            // Load the .html files into the templateCache,
            $templateCache.put('views/indexpage/index-tmpl.html', '');
            $templateCache.put('views/indexpage/logo.html', '');
            $templateCache.put('views/indexpage/login.html', '');
            $templateCache.put('views/indexpage/locale.html', '');
            $templateCache.put('views/indexpage/content1.html', '');
            $templateCache.put('views/indexpage/content2.html', '');
            $templateCache.put('views/indexpage/footer.html', '');

        }));

        it("Should handle elementClick.directive event", function () {
            spyOn(localScope, "$broadcast").andCallThrough();
            localScope.$broadcast('elementClick.directive');
            expect(localScope.$broadcast).toHaveBeenCalledWith('elementClick.directive');
        });
    });

    describe("Should handle destroy method", function () {
        it("Should handle destroy method", function () {
            spyOn(localScope, "$broadcast").andCallThrough();
            localScope.$broadcast("$destroy");
            expect(localScope.$broadcast).toHaveBeenCalledWith("$destroy");
        });
    });

});