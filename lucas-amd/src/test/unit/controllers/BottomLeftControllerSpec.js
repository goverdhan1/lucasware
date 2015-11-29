'use strict';

describe('BottomLeftController Unit Tests', function() {

    // Global vars
    var localScope = null;
    var rootScope;
    var mockLocalStoreService = null;
    var localStoreService = null;
    var localController = null;
    var modal;
    var $state;
    var localHomeCanvasListService;

    beforeEach(module('amdApp'));

    beforeEach(inject(function($controller, $rootScope, _$state_, $templateCache, $httpBackend, $modal, HomeCanvasListService) {
        // Load the .html files into the templateCache,
        // these will be used after the user is created by the code in LoginController.js
        $templateCache.put('views/canvaspage/canvas-container.html', '');
        $templateCache.put('views/indexpage/locale.html', '');
        $templateCache.put('views/canvaspage/topleft-tmpl.html', '');
        $templateCache.put('views/canvaspage/topcenter-tmpl.html', '');
        $templateCache.put('views/canvaspage/topright-tmpl.html', '');
        $templateCache.put('views/canvaspage/canvas-detail.html', '');
        $templateCache.put('views/canvaspage/bottomleft-tmpl.html', '');
        $templateCache.put('views/canvaspage/bottomright-tmpl.html', '');

        $httpBackend.when('GET').respond({});
        $state = _$state_;
        modal = $modal;

        mockLocalStoreService = {
            getLSItem : function () {
                // return a mock favourite canvas
                return ['Morning Shift Canvas'];
            }
        };

        localStoreService = mockLocalStoreService;
        localHomeCanvasListService = HomeCanvasListService;
        rootScope = $rootScope;
        localScope = $rootScope.$new();

        localController = $controller('BottomLeftController', {
            $scope : localScope,
            LocalStoreService : localStoreService,
            HomeCanvasListService: localHomeCanvasListService
        });
    }));


    // Dependency Injection Specs
    describe('dependency injection tests ', function() {

        it('should inject BottomLeftController', function() {
            expect(localController).toBeDefined();
        });

        it('should inject localScope', function() {
            expect(localScope).toBeDefined();
        });

        it('should inject LocalStoreService', function() {
            expect(localStoreService).toBeDefined();
        });
    });


    //Unit Specs
    describe('local store display user canvas spec ', function() {

        it('should show user canvas', function() {
            var a = localStoreService.getLSItem();

            expect(a[0]).toEqual('Morning Shift Canvas');

        });
    });

    it('should test getActiveClass() method', function() {
        // set the canvas id, to the state
        $state.params.canvasId = 221;
        // check primary for active canvas
        expect(localScope.getActiveClass(221)).toEqual('btn-primary');
        // check primary for non active canvas
        expect(localScope.getActiveClass(224)).toEqual();
    });

    it('should show user canvas', function() {
        spyOn(rootScope, '$broadcast').andCallThrough();
        spyOn(modal, 'open').andCallThrough();
        spyOn(localHomeCanvasListService, 'findByCanvasId').andCallThrough();
        localScope.CloseCanvas(235);
        rootScope.$digest();
        expect(rootScope.$broadcast).toHaveBeenCalledWith('saveActiveCanvas');
        expect(modal.open).toHaveBeenCalled();
        expect(localHomeCanvasListService.findByCanvasId).toHaveBeenCalled();
    });

    it('should handle refreshFavoriteCanvasListUpdated event', function() {
        var favoriteCanvasList =  [{
            "name": "ProductManagement",
            "canvasId": "221",
            "canvasType": "COMPANY",
            "displayOrder": "2",
            "widgetInstanceList": [{
                "data": [],
                "id": 8,
                "widgetDefinition": {
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "widgetActionConfig": {
                        "widget-access": {
                            "create-edit-user-widget-access": true
                        },
                        "widget-actions": {
                            "edit-user": true,
                            "create-user": true
                        }
                    },
                    "definitionData": {
                        "User": [null]
                    },
                    "broadcastList": ["userName"],
                    "reactToMap": {
                        "lastName": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "lastName": ["jack123", "jill123", "admin123"]
                                }
                            }
                        },
                        "Wave": {
                            "url": "/users",
                            "searchCriteria": {
                                "pageSize": "10",
                                "pageNumber": "0",
                                "searchMap": {
                                    "Wave": ["Wave 1", "Wave 2", "Wave 3"]
                                }
                            }
                        }
                    },
                    "defaultViewConfig": {
                        "deviceWidths": {
                            "320": "mobile",
                            "540": "tablet",
                            "800": "desktop",
                            "1200": "wideScreen"
                        },
                        "dateFormat": {
                            "selectedFormat": "mm-dd-yyyy",
                            "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                        },
                        "minimumWidth": 300,
                        "minimumHeight": 480,
                        "anchor": [275, 295],
                        "zindex": 0,
                        "isMaximized": false,
                        "listensForList": ["userName", "Wave", "Score"],
                        "reactToList": [],
                        "originalMinimumWidth": 300,
                        "originalMinimumHeight": 480
                    },
                    "broadcastMap": ["userName", "firstName"]
                },
                "actualViewConfig": {
                    "deviceWidths": {
                        "320": "mobile",
                        "540": "tablet",
                        "800": "desktop",
                        "1200": "wideScreen"
                    },
                    "dateFormat": {
                        "selectedFormat": "mm-dd-yyyy",
                        "availableFormats": ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
                    },
                    "minimumWidth": 300,
                    "minimumHeight": 480,
                    "anchor": [275, 295],
                    "zindex": 0,
                    "isMaximized": false,
                    "listensForList": ["userName", "Wave", "Score"],
                    "reactToList": [],
                    "originalMinimumWidth": 300,
                    "originalMinimumHeight": 480
                },
                "updateWidget": true,
                "clientId": 100,
                "isMaximized": false
            }]
        }, {
            "name": "AssignmentManagement",
            "canvasId": "231",
            "canvasType": "COMPANY",
            "displayOrder": "3"
        }, {
            "name": "GroupManagement",
            "canvasId": "235",
            "canvasType": "COMPANY",
            "displayOrder": "4"
        }];
        rootScope.$emit('refreshFavoriteCanvasListUpdated', favoriteCanvasList);
        rootScope.$digest();
        expect(localScope.canvasBar).toEqual(favoriteCanvasList);
    });

});