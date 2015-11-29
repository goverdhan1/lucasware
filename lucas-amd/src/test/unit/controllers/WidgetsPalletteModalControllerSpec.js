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

'use strict';

describe('WidgetsPalletteModalController Unit Tests', function () {

    // Global vars
    var def = null;
    var localWidgetsPalletteModalController;
    var localScope;
    var rootScope;
    var localState;
    var localWidgetDefinitionService;
    var localWidgetsPalletteService;
    var localModalInstance;
    var httpBackend;
    var mockResponse = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "Administration": [
                {
                    "id": 8,
                    "name": "create-or-edit-user-form-widget",
                    "shortName": "CreateUser",
                    "title": "Create Or Edit User",
                    "description": "ABC",
                    "type": "FORM"
                },
                {
                    "id": 12,
                    "name": "group-maintenance-widget",
                    "shortName": "GroupMaintenance",
                    "title": "Group Maintenance",
                    "description": "DEF",
                    "type": "FORM"
                },
                {
                    "id": 9,
                    "name": "search-user-grid-widget",
                    "shortName": "SearchUser",
                    "title": "Search User",
                    "description": "JKL",
                    "type": "GRID"
                }
            ],
            "Work Execution": [
                {
                    "id": 14,
                    "name": "search-product-grid-widget",
                    "shortName": "SearchProduct",
                    "title": "Search Product",
                    "description": "GHI",
                    "type": "GRID"
                }
            ],
            "Reporting": [
                {
                    "id": 10,
                    "name": "pickline-by-wave-barchart-widget",
                    "shortName": "PicklineByWave",
                    "title": "Pickline By Wave Bar chart",
                    "description": "MNO",
                    "type": "GRAPH_2D"
                }
            ]
        }
    };

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de",
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function ($rootScope, $controller, $modal, $templateCache, WidgetsPalletteService, $httpBackend, $state, WidgetDefinitionService) {
        //mock opening modal, so we can test closing it
        $templateCache.put('views/modals/widgets-pallete.html', '');
        localModalInstance = $modal.open({
            templateUrl: 'views/modals/widgets-pallete.html',
            backdrop: 'static'
        });
        localScope = $rootScope.$new();
        rootScope = $rootScope;
        localState = $state;
        localWidgetDefinitionService = WidgetDefinitionService;
        localWidgetsPalletteService = WidgetsPalletteService;
        httpBackend = $httpBackend;


        $templateCache.put('views/indexpage/index-tmpl.html', '');
        $templateCache.put('views/indexpage/logo.html', '');
        $templateCache.put('views/indexpage/login.html', '');
        $templateCache.put('views/indexpage/locale.html', '');
        $templateCache.put('views/indexpage/content1.html', '');
        $templateCache.put('views/indexpage/content2.html', '');
        $templateCache.put('views/indexpage/footer.html', '');
        $templateCache.put('views/canvaspage/canvas-container.html', '');
        $templateCache.put('views/canvaspage/topleft-tmpl.html', '');
        $templateCache.put('views/canvaspage/topcenter-tmpl.html', '');
        $templateCache.put('views/canvaspage/topright-tmpl.html', '');
        $templateCache.put('views/canvaspage/canvas-detail.html', '');
        $templateCache.put('views/canvaspage/bottomleft-tmpl.html', '');
        $templateCache.put('views/canvaspage/bottomright-tmpl.html', '');

        httpBackend.whenGET('${RestEndpoint}${ApiPath}//widgetdefinitions').respond(mockResponse);
        httpBackend.whenGET("${RestEndpoint}${ApiPath}/widgets/pickline-by-wave-barchart-widget/definition").respond({
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

        spyOn(localWidgetsPalletteService, 'getWidgets').andCallThrough();

        localWidgetsPalletteModalController = $controller('WidgetsPalletteModalController', {
            $scope: localScope,
            $state: localState,
            $modalInstance: localModalInstance,
            localWidgetsPalletteService: WidgetsPalletteService
        });

        spyOn(localScope, 'closeModal').andCallThrough();
    }));

    // Inject Specs
    describe('WidgetsPalletteModalController Injection Specs', function () {
        it('should inject WidgetsPalletteModalController', function () {
            expect(localWidgetsPalletteModalController).toBeDefined();
        });

        it('should inject ModalInstance', function () {
            expect(localModalInstance).toBeDefined();
        });

        it('should inject $scope', function () {
            expect(localScope).toBeDefined();
        });
    });

    // getWidgets method
    describe('handle WidgetsPalletteService.getWidgets() method', function () {
        it('handle WidgetsPalletteService.getWidgets() method', function () {
            expect(localWidgetsPalletteService.getWidgets).toHaveBeenCalled();
            localWidgetsPalletteService.getWidgets().then(function (response) {
                expect(response).toEqual(mockResponse);
            });
        });
    });

    // Modal Tests
    describe('Logout Modal Specs', function () {
        beforeEach(function () {
            spyOn(localModalInstance, 'close').andCallThrough();
            localScope.$digest();
        });
        it('should close the logout modal', function () {
            localScope.closeModal();
            expect(localScope.closeModal).toBeDefined();
            expect(localModalInstance.close).toHaveBeenCalled();
        });
    });

    // selectWidget method
    // Unit: Assert selectWidget method to check whether its calling WidgetDefinitionService or not.
    it('Should handle selectWidget method', function () {
        spyOn(localScope, 'selectWidget').andCallThrough();
        spyOn(localState, 'go').andReturn();
        spyOn(localModalInstance, 'close').andReturn();
        spyOn(localWidgetDefinitionService, 'getWidgetDefinition').andCallThrough();
        spyOn(rootScope, '$broadcast').andCallThrough();
        localScope.selectWidget({
            id: 10, name:"pickline-by-wave-barchart-widget", type:"GRAPH_2D"
        });
        httpBackend.flush();
        localScope.$digest();
        expect(localState.go).toHaveBeenCalled();
        expect(localScope.selectWidget).toHaveBeenCalled();
        expect(localWidgetDefinitionService.getWidgetDefinition).toHaveBeenCalled();
        expect(rootScope.$broadcast).toHaveBeenCalledWith("resumeWatches");
        expect(rootScope.$broadcast).toHaveBeenCalledWith("refreshOriginalResizedMinWidth");
    });

});