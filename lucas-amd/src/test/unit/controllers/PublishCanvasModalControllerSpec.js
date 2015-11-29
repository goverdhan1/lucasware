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

describe('PublishCanvasModalController Tests', function() {
    // Global vars
    var localScope;
    var localModalInstance;
    var localCanvasService;
    var localUtilsService;
    var localPublishCanvasModalController;
    var httpBackend;
    var localStoreService;

    // Global test setup
    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');

        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function($controller, $rootScope, $q, $modal, $templateCache, CanvasService, LocalStoreService, $httpBackend, UtilsService) {
        localScope = $rootScope.$new();
        localCanvasService = CanvasService;
        localStoreService = LocalStoreService;
        localUtilsService = UtilsService;

        httpBackend = $httpBackend;
        httpBackend.whenGET().respond({});

        localStoreService.addLSItem(null, "UserName", "jack123");
        localStoreService.addLSItem(null, "ActiveCanvasId", 201);
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', [{
            "name": "ProductManagement",
            "canvasId": "201",
            "canvasType": "PRIVATE",
            "displayOrder": "1"
        }, {
            "name": "Work Execution",
            "canvasId": "221",
            "isActive": "1",
            "canvasType": "COMPANY",
            "displayOrder": "2"
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
        }, {
            "canvasId": 244,
            "canvasType": "COMPANY",
            "name": "test12345"
        }]);

        $templateCache.put('views/modals/publish-canvas.html', '');
        localModalInstance = $modal.open({
            templateUrl: 'views/modals/publish-canvas.html',
            backdrop: 'static'
        });

        localPublishCanvasModalController = $controller("PublishCanvasModalController", {
            $scope: localScope,
            CanvasService: localCanvasService,
            $modalInstance: localModalInstance,
            canvas: {
                'canvasName': 'ProductManagement',
                'canvasId': 201
            }
        });
    }));

    it('publish canvas success', inject(function($q) {

        var def = $q.defer();
        def.resolve({
            canvasId: 201,
            canvasName: "ProductManagement"
        });

        // spy on publishCanvas method
        spyOn(localCanvasService, 'publishCanvas').andReturn(def.promise);

        // invoke controller function to publish the canvas
        localScope.publishCanvas();

        // check canvasService publishCanvas has been called
        expect(localCanvasService.publishCanvas).toHaveBeenCalled();
    }));
});