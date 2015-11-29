/**
 * Created by Shaik Basha on 1/23/2015.
 */

'use strict';

describe('WidgetsPalletteService related Tests', function () {

    // Global variables
    var localScope;
    var localWidgetsPalletteService;
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
    beforeEach(module('amdApp'));
    beforeEach(inject(function ($rootScope, $httpBackend, WidgetsPalletteService) {
        localScope = $rootScope.$new();
        localWidgetsPalletteService = WidgetsPalletteService;
        $httpBackend.when('GET').respond(mockResponse);
    }));

    // test injections
    it('should inject dependencies', function () {
        expect(localWidgetsPalletteService).toBeDefined();
    });

    // test getWidgets() method response
    it('handle getWidgets method', function () {
        localScope.$digest();
        localWidgetsPalletteService.getWidgets().then(function (response) {
            // test the response
            expect(response).toEqual(mockResponse);
        })
    });

});