'use strict';

describe('Login Service related Tests', function () {
    var localLoginService;
    var httpBackend;
    var localSessionService;
    var localHomeCanvasListService;
    var localStoreService;
    var deferred;
    var state;
    var rootScope;
    var log;
    var credentials = {username: "jack", password: "secret"};
    var mockProfileData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "username": "jack123",
            "userId": 3,
            "userPermissions": ["authenticated-user", "user-management-canvas-view", "create-assignment", "user-management-canvas-edit", "user-list-download-excel", "user-list-download-pdf", "pick-monitoring-canvas-edit", "pick-monitoring-canvas-view"],
            "firstName": "Jack",
            canvasId: 2,
            canvasName: "Morning Shift Canvas",
            widgetInstanceList: [],
            canvasList: [
                {
                    "name": "ProductManagement",
                    "canvasId": "201",
                    "canvasType": "COMPANY",
                    "displayOrder": "1"
                },
                {
                    "name": "Work Execution",
                    "canvasId": "221",
                    "isActive": "1",
                    "canvasType": "COMPANY",
                    "displayOrder": "2"
                },
                {
                    "name": "AssignmentManagement",
                    "canvasId": "231",
                    "canvasType": "COMPANY",
                    "displayOrder": "3"
                },
                {
                    "name": "GroupManagement",
                    "canvasId": "235",
                    "canvasType": "COMPANY",
                    "displayOrder": "4"
                }
            ]
        }
    };

    beforeEach(module('amdApp'));
    beforeEach(inject(function ($q, $state, $rootScope, LoginService, LocalStoreService, SessionService, HomeCanvasListService, $httpBackend) {
        localLoginService = LoginService;
        localSessionService = SessionService;
        localHomeCanvasListService = HomeCanvasListService;
        localStoreService = LocalStoreService;
        deferred = $q.defer();
        rootScope = $rootScope;
        state = $state;
        httpBackend = $httpBackend;
        httpBackend.when('GET').respond(mockProfileData);
        httpBackend.when('POST').respond(mockProfileData);
    }));

    describe('dependency injection tests', function () {
        it('should inject dependencies', function () {
            expect(localLoginService).toBeDefined();
            expect(localSessionService).toBeDefined();
            expect(localHomeCanvasListService).toBeDefined();
        });
    });

    describe('Should verify all internal scenarios of login method', function () {
        it('should call login method', function () {
            spyOn(localLoginService, 'login').andCallThrough();
            localLoginService.login(credentials);
            expect(localLoginService.login).toHaveBeenCalled();
        });

        xit('should handle create method', function () {
            spyOn(localSessionService, 'create').andCallThrough();
            localLoginService.login(credentials);
            var localScope = rootScope.$new();
            httpBackend.flush();
            localScope.$digest();
            expect(localSessionService.create).toHaveBeenCalled();
        })

        xit('should handle getUserConfig method', function () {
            spyOn(localHomeCanvasListService, 'getUserConfig').andCallThrough();
            localLoginService.login(credentials);
            var localScope = rootScope.$new();
            httpBackend.flush();
            localScope.$digest();
            expect(localHomeCanvasListService.getUserConfig).toHaveBeenCalled();
        });

        xit('should handle state go method', function () {
            spyOn(state, 'go').andCallThrough();
            localLoginService.login(credentials);
            var localScope = rootScope.$new();
            httpBackend.flush();
            localScope.$digest();
            expect(state.go).toHaveBeenCalled();
        });

    });

    describe('should be able to set and get isAuthenticationOrAuthorizationFailed', function () {
        it('should handle setAuthenticationOrAuthorizationFailed method', inject(function ($rootScope) {
            spyOn(localLoginService, 'setAuthenticationOrAuthorizationFailed').andCallThrough();
            localLoginService.setAuthenticationOrAuthorizationFailed(true);
            expect(localLoginService.setAuthenticationOrAuthorizationFailed).toHaveBeenCalledWith(true);
        }));
        it('should handle getAuthenticationOrAuthorizationFailed method', inject(function ($rootScope) {
            localLoginService.setAuthenticationOrAuthorizationFailed(true);
            expect(localLoginService.getAuthenticationOrAuthorizationFailed()).toBe(true);
        }));
    });

    describe('should be able to set and get stateParameters', function () {
        it('should handle setStateParameters method', inject(function ($rootScope) {
            spyOn(localLoginService, 'setStateParameters').andCallThrough();
            localLoginService.setStateParameters({});
            expect(localLoginService.setStateParameters).toHaveBeenCalledWith({});
        }));
        it('should handle getStateParameters method', inject(function ($rootScope) {
            localLoginService.setStateParameters({});
            expect(localLoginService.getStateParameters()).toEqual({});
        }));
    });

    describe('should be login specific methods', function () {
        it('should handle login method', inject(function ($rootScope) {
            spyOn(localLoginService, 'login').andCallThrough();
            localLoginService.login(credentials);
            expect(localLoginService.login).toHaveBeenCalledWith(credentials);
        }));
    });

});