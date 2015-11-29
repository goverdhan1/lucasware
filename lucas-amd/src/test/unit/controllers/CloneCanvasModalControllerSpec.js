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

describe('CloneCanvasModalController Tests', function() {
    // Global vars
    var localScope;
    var localModalInstance;
    var localCanvasService;
    var localUtilsService;
    var localCloneCanvasModalController;
    var httpBackend;
    var $q;
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

    beforeEach(inject(function($controller, $rootScope, _$q_, $modal, $templateCache, CanvasService, LocalStoreService, $httpBackend, UtilsService) {
        localScope = $rootScope.$new();
        localCanvasService = CanvasService;
        localStoreService = LocalStoreService;
        localUtilsService = UtilsService;
        $q = _$q_;

        httpBackend = $httpBackend;
        httpBackend.whenGET().respond({});

        localStoreService.addLSItem(null, "UserName", "jack123");
        localStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', []);
        localStoreService.addLSItem(null, 'UserConfig', {
            'openCanvases': []
        });

        $templateCache.put('views/modals/create-new-canvas.html', '');
        localModalInstance = $modal.open({
            templateUrl: 'views/modals/create-new-canvas.html',
            backdrop: 'static'
        });

        localScope.userForm = {
            $error: {},
            canvasname: {
                $setValidity: function(key, isValid) {
                    localScope.userForm.$error[key] = isValid;
                }
            }
        };
        localCloneCanvasModalController = $controller("CloneCanvasModalController", {
            $scope: localScope,
            CanvasService: localCanvasService,
            $modalInstance: localModalInstance
        });
    }));

    it('clone canvas name empty validation', function() {
        // spy on saveCanvas method
        spyOn(localScope, 'saveCanvas').andCallThrough();
        // create the new canvas
        localScope.saveCanvas();
        expect(localScope.saveCanvas).toHaveBeenCalled();
        expect(localScope.userForm.$error['canvasNameAlreadyExists']).toBeTruthy();
        expect(localScope.userForm.$error['canvasNameIsRequired']).toBeFalsy();
    });

    it('clone canvas name duplicate validation', inject(function(_$q_) {
        var def = _$q_.defer();
        def.reject();

        //setup spies
        spyOn(localCanvasService, 'saveCanvas').andReturn(def.promise);

        // set the name of the canvas
        localScope.canvasName = "ProductCanvas";

        // invoke the controller function
        localScope.saveCanvas();
        localScope.$digest();

        // check canvasService saveCanvas has been called
        expect(localCanvasService.saveCanvas).toHaveBeenCalled();
        expect(localScope.userForm.$error['canvasNameAlreadyExists']).toBeFalsy();
        expect(localScope.userForm.$error['canvasNameIsRequired']).toBeTruthy();
    }));
});