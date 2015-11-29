'use strict';
describe('PieChart Controller Unit Tests', function () {

    // Global vars
    var localScope = null;
    var localPieChartController = null;
    var deferred = null;
    var mockWidgetChartService = null;
    var mockUtilsService = null;

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');

        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function ($controller, $rootScope, $q) {
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
                    broadcastMap: "TestBroadCastMap",
                    listensFor: "TestListener"
                }
            }
        };

        mockWidgetChartService = {
            getChartData: function () {
                deferred = $q.defer();
                deferred.resolve({
                    data: "dummy data"
                });
                return deferred.promise;
            },
            processChartData: function (data) {
                return data;
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

        localPieChartController = $controller("PieChartController", {
            $scope: localScope,
            WidgetChartService: mockWidgetChartService,
            UtilsService: mockUtilsService
        });
    }));

    describe("Should handle getChartData method", function () {
        var result;
        var data = {
            key: "TestKey",
            y: "TestY",
            x: "TestX"
        };
        var colorArrayPie = ['#000000', '#660000', '#CC0000', '#FF6666', '#FF3333', '#FF6666', '#FFE6E6'];

        beforeEach(function () {
            mockWidgetChartService.getChartData().then(function (response) {
                // This function is used if the promise is 'resolved'
                result = response;
            });
            // We need this here since promises are only
            // resolved/dispatched only on the next $digest cycle
            localScope.$digest();
        });

        it("Should call getChartData method correctly", function () {
            expect(result).toEqual({
                data: "dummy data"
            });
        });

        it("Should call xFunctionPie method correctly", function () {
            expect(localScope.xFunctionPie()(data)).toBe(data.key);
        });

        it("Should call yFunctionPie method correctly", function () {
            expect(localScope.yFunctionPie()(data)).toBe(data.y);
        });

        it("Should call colorFunctionPie method correctly", function () {
            expect(localScope.colorFunctionPie()(data, 1)).toBe(colorArrayPie[1]);
        });

        it("Should call descriptionFunctionPie method correctly", function () {
            expect(localScope.descriptionFunctionPie()(data)).toBe(data.key);
        });

        it("Should call toolTipContentFunctionPie method correctly", function () {
            expect(localScope.toolTipContentFunctionPie()(data.key, data.x, data.y)).toBe(
                    'Super New Tooltip' + '<h1>' + data.key + '</h1>' + '<p>' + ' at ' + data.x + '</p>'
            );
        });

    });

    describe("Should handle dimensionsUpdateFn", function () {
        it("Should handle updateChartData method", function () {
            var result;
            spyOn(localScope, "$broadcast").andCallThrough();
            spyOn(mockWidgetChartService, 'getChartData').andCallThrough();
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