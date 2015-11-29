'use strict';
describe('ShowAvailableWidgets Controller Tests', function () {
    // Globals
    var localScope;
    var localLog;
    var localWidgetDefinitionService;
    var localWidgetDefinitionServiceException;
    var localLocalStoreService;
    var localModalInstance;
    var mockShowAvailableWidgetsServiceException;
    var localShowAvailableWidgetsController;
    var localState;
    var deferred;
    var $httpBackend;

    beforeEach(module('amdApp'));
    var availableWidgets = {
        'Productivity': [
            {
                'id': 7,
                'name': 'alert-widget',
                'shortName': 'alert',
                'type': 'GRAPH_2D',
                'subtype': 'CHART_LINE',
                'title': 'Alert'
            }
        ]
    };

    beforeEach(inject(function ($injector, $rootScope, $controller, $modal, $q, $templateCache, WidgetDefinitionService, $log, $state) {
        // Set up the mock http service responses
        $httpBackend = $injector.get('$httpBackend');
        // Handle all the GET requests
        $httpBackend.when('GET').respond({
            "status": "success",
            "code": "200",
            "message": "Request processed successfully",
            "level": null,
            "uniqueKey": null,
            "token": null,
            "explicxitDismissal": null,
            "data": {
                "name": "pickline-by-wave-barchart-widget",
                "id": 10,
                "shortName": "PicklineByWave",
                "txitle": "Pickline By Wave Bar chart",
                "widgetActionConfig": {},
                "defaultData": {},
                "defaultViewConfig": {
                    "height": 100,
                    "width": 120,
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": [
                            "mm-dd-yyyy",
                            "MMM dd, yyyy",
                            "dd-mm-yyyy"
                        ]
                    },
                    "anchor": [
                        1,
                        2
                    ],
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
                "listensForList": [],
                "broadCastMap": {
                    "waveId": "waveId"
                },
                "dataURL": {
                    "url": "/waves/picklines",
                    "searchCrxiteria": {
                        "searchMap": {},
                        "sortMap": {},
                        "pageSize": "10",
                        "pageNumber": "0"
                    }
                }
            }
        });
        $httpBackend.when('POST').respond({
            "status": "success",
            "code": "200",
            "message": "Request processed successfully",
            "level": null,
            "uniqueKey": null,
            "token": null,
            "explicxitDismissal": null,
            "data": {
                "name": "pickline-by-wave-barchart-widget",
                "id": 10,
                "shortName": "PicklineByWave",
                "txitle": "Pickline By Wave Bar chart",
                "widgetActionConfig": {},
                "defaultData": {},
                "defaultViewConfig": {
                    "height": 100,
                    "width": 120,
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": [
                            "mm-dd-yyyy",
                            "MMM dd, yyyy",
                            "dd-mm-yyyy"
                        ]
                    },
                    "anchor": [
                        1,
                        2
                    ],
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
                "listensForList": [],
                "broadCastMap": {
                    "waveId": "waveId"
                },
                "dataURL": {
                    "url": "/waves/picklines",
                    "searchCrxiteria": {
                        "searchMap": {},
                        "sortMap": {},
                        "pageSize": "10",
                        "pageNumber": "0"
                    }
                }
            }
        });

        localScope = $rootScope.$new();
        $templateCache.put('views/show-available-widgets.html', '');
        localModalInstance = $modal.open({
            templateUrl: 'views/show-available-widgets.html',
            controller: 'ShowAvailableWidgetsController',
            backdrop: 'static'
        });

        localLog = $log;
        localState = $state;

        localWidgetDefinitionService = WidgetDefinitionService;

        mockShowAvailableWidgetsServiceException = {
            getWidgetsByCategory: function () {
                deferred = $q.defer();
                deferred.reject( {
                    status: 404,
                    data: "",
                    config: {}
                });
                return deferred.promise;
            }
        };

        localWidgetDefinitionServiceException = {
            getWidgetDefinition: function () {
                deferred = $q.defer();
                deferred.reject('error');
                return deferred.promise;
            }
        };

        localLocalStoreService = {
            getLSItem: function () {
            }
        };

        localShowAvailableWidgetsController = $controller('ShowAvailableWidgetsController', {
            $scope: localScope,
            $log: localLog,
            $state: localState,
            $modalInstance: localModalInstance,
            WidgetDefinitionService: localWidgetDefinitionService,
            LocalStoreService: localLocalStoreService
        });
    }));


    // selectWidget method
    // Unit: Assert selectWidget method to check whether its calling WidgetDefinitionService or not.
    it('Should handle selectWidget method', function () {
        spyOn(localScope, 'selectWidget').andCallThrough();
        spyOn(localState, 'go').andReturn();
        spyOn(localModalInstance, 'close').andReturn();
        spyOn(localWidgetDefinitionService, 'getWidgetDefinition').andCallThrough();
        localScope.selectWidget(10, "pickline-by-wave-barchart-widget", "GRAPH_2D", "CHART_BAR");
        $httpBackend.flush();
        localScope.$digest();
        expect(localState.go).toHaveBeenCalled();
        expect(localScope.selectWidget).toHaveBeenCalled();
        expect(localWidgetDefinitionService.getWidgetDefinition).toHaveBeenCalled();
    });

    // selectWidget method
    describe('Should handle selectWidget method exception', function () {
        beforeEach(inject(function ($controller, $log) {
            localShowAvailableWidgetsController = $controller('ShowAvailableWidgetsController', {
                $scope: localScope,
                $state: localState,
                $log: localLog,
                $modalInstance: localModalInstance,
                WidgetDefinitionService: localWidgetDefinitionServiceException,
                LocalStoreService: localLocalStoreService
            });
        }));

        it('Should handle selectWidget method exception', function () {
            spyOn(localScope, 'selectWidget').andCallThrough();
            spyOn(localState, 'go').andReturn();
            spyOn(localModalInstance, 'close').andReturn();
            localScope.selectWidget();
            localScope.$digest();
            expect(localScope.selectWidget).toHaveBeenCalled();
        });

    });

    // cancel modal
    it('Should handle cancel method', function () {
        spyOn(localModalInstance, 'close').andReturn();
        spyOn(localScope, 'cancel').andCallThrough();
        localScope.cancel();
        expect(localScope.cancel).toHaveBeenCalled();
    });

    it("should handle getImagePath method", function() {
        expect(localScope.getImagePath({subtype: "FORM", type: "FORM"})).toBe("images/form.png");
        expect(localScope.getImagePath({subtype: "GRID"})).toBe("images/grid.png");
    });

    // for handling exception we mock the service
    describe('Should handle ShowAvailableWidgetsService exception', function () {
        beforeEach(inject(function ($controller) {
            // Need to mock as the actual log is giving error TypeError: Cannot read property 'push' of undefined
            // Need to do some research on this scenario
            localLog = {
                error: function () {
                }
            };
            localShowAvailableWidgetsController = $controller('ShowAvailableWidgetsController', {
                $scope: localScope,
                $state: localState,
                $log: localLog,
                $modalInstance: localModalInstance,
                WidgetDefinitionService: localWidgetDefinitionServiceException,
                LocalStoreService: localLocalStoreService,
                ShowAvailableWidgetsService: mockShowAvailableWidgetsServiceException
            });
        }));
        it('Should handle log error', function () {
            spyOn(localLog, 'error').andCallThrough();
            localScope.$digest();
            expect(localLog.error).toHaveBeenCalled();
        });
    });
});