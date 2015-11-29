'use strict';


describe('SecurityService related Tests', function () {
    var localSecurityService;
    var localStoreService;
    var mockProfileData = {
        "username": "jack123",
        "userId": 3,
        "userPermissions": [
            "create-canvas",
            "authenticated-user",
            "user-management-canvas-view",
            "create-assignment",
            "user-management-canvas-edit",
            "user-list-download-excel",
            "user-list-download-pdf",
            "pick-monitoring-canvas-edit",
            "pick-monitoring-canvas-view"
        ]
    };

    beforeEach(module('amdApp'));

    beforeEach(inject(function (SecurityService, LocalStoreService) {
        localSecurityService = SecurityService;
        localStoreService = LocalStoreService;
    }));

    it('should get the permissions using getPermissions() method from localstorage', inject(function () {
        localStoreService.addLSItem(null, 'ProfileData', mockProfileData);
        expect(localSecurityService.getPermissions()).toEqual(mockProfileData.userPermissions);
    }));

    it('should get the permissions using getPermissions() method', inject(function () {
        localStoreService.removeLSItem('ProfileData');
        expect(localSecurityService.getPermissions()).toEqual([]);
    }));

    it('should check for permission using hasPermission() method', inject(function () {
        localStoreService.addLSItem(null, 'ProfileData', mockProfileData);
        expect(localSecurityService.hasPermission('create-canvas')).toBe(true);
    }));

});