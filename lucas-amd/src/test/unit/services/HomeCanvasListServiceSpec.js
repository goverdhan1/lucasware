'use strict';

describe('HomeCanvasListService related tests', function () {
    // locals
    var localHomeCanvasListService;
    var localUtilsService
    var localScope;
    // initialize
    beforeEach(module('amdApp'));
    beforeEach(inject(function (HomeCanvasListService, $rootScope, $httpBackend, UtilsService) {
        $httpBackend.when('GET').respond({});
        $httpBackend.when('POST').respond({});
        localScope = $rootScope.$new();
        localHomeCanvasListService = HomeCanvasListService;
        localUtilsService = UtilsService;
    }));

    it('handle getSelectedCanvasData() method', function () {
        spyOn(localHomeCanvasListService, 'getSelectedCanvasData').andCallThrough();
        localHomeCanvasListService.getSelectedCanvasData();
        localScope.$digest();
        expect(localHomeCanvasListService.getSelectedCanvasData).toHaveBeenCalled();
    });

    it('handle deleteCanvas() method', function () {
        spyOn(localHomeCanvasListService, 'deleteCanvas').andCallThrough();
        localHomeCanvasListService.deleteCanvas();
        localScope.$digest();
        expect(localHomeCanvasListService.deleteCanvas).toHaveBeenCalled();
    });

    it('handle getUserConfig() method', function () {
        spyOn(localHomeCanvasListService, 'getUserConfig').andCallThrough();
        localHomeCanvasListService.getUserConfig();
        localScope.$digest();
        expect(localHomeCanvasListService.getUserConfig).toHaveBeenCalled();
    });

    it('handle saveActiveAndOpenCanvases() method', function () {
        var res;
        var user = {
            "userName": "jack123",
            "userId": 100,
            "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
            "homeCanvas": {},
            "activeCanvas": {"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []},
            "seeHomeCanvasIndicator": true,
            "openCanvases": [{"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []}],
            "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
        };
        localHomeCanvasListService.saveActiveAndOpenCanvases(user).then(function(response) {
            res = response;
        });
        localScope.$digest();
        expect(res).toEqual("success");
    });

    it('handle removeCanvasUnnecessaryProperties() method', function () {
        var user = {
            "userName": "jack123",
            "userId": 100,
            "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
            "homeCanvas": {},
            "activeCanvas": {"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []},
            "seeHomeCanvasIndicator": true,
            "openCanvases": [{"canvasId": 140, "canvasName": "Throughput", "widgetInstanceList": []}],
            "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
        };
        var propertiesToRemove = ['isDataFetched', 'widgetInstanceList'];
        spyOn(localHomeCanvasListService, 'removeCanvasUnnecessaryProperties').andCallThrough();
        spyOn(localUtilsService, 'removeProperties').andCallThrough();
        var resUser = localHomeCanvasListService.removeCanvasUnnecessaryProperties(user, propertiesToRemove);
        expect(resUser).toEqual({
            "userName": "jack123",
            "userId": 100,
            "permissionSet": ["manage-canvas", "clone-canvas", "delete-user", "disable-user"],
            "homeCanvas": {},
            "activeCanvas": {"canvasId": 140, "canvasName": "Throughput"},
            "seeHomeCanvasIndicator": true,
            "openCanvases": [{"canvasId": 140, "canvasName": "Throughput"}],
            "menuPermissions": {"manage-canvas": "Manage Canvas", "clone-canvas": "Clone Canvas"}
        });
        expect(localHomeCanvasListService.removeCanvasUnnecessaryProperties).toHaveBeenCalled();
    });
});