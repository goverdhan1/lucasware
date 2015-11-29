'use strict';


describe('UserService related Tests', function() {

    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN",
        }, 'fr', {
            "language-code" : "fr",
        }, 'de', {
            "language-code" : "de",
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
        $translateProvider.useLoader('LocaleLoader');
    }));

    // globals
    var localScope = null;
    var localRestangular = null;
    var localRestApiService = null;
    var localUserService = null;
    var localUtilsService = null;
    var localStoreService = null;
    var deferred = null;
    var mockProfileData = {
        username: 'jack123',
        userId: 3,
        userPermissions: ['authenticated-user', 'user-management-canvas-view', 'create-assignment', 'user-management-canvas-edit', 'user-list-download-excel', 'user-list-download-pdf', 'pick-monitoring-canvas-edit', 'pick-monitoring-canvas-view'],
        firstName: 'Jack'
    };
    var jackMockData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "user": {
                "userId": 3,
                "userName": "jack123",
                "permissionSet": [
                    "create-canvas"
                ]
            }
        }
    };
    var jillMockData = {
        "status": "success",
        "code": "200",
        "message": "Request processed successfully",
        "level": null,
        "uniqueKey": null,
        "token": null,
        "explicitDismissal": null,
        "data": {
            "user": {
                "userId": 3,
                "userName": "jack123",
                "permissionSet": [
                ]
            }
        }
    };

    var injectRestangular = inject(function(Restangular, $q) {
        localRestangular = Restangular;

        //set mocks
        deferred = $q.defer();
        spyOn(Restangular, 'all').andReturn({
            getList: function () {
                deferred.resolve(mockProfileData);
                return deferred.promise;
            },
            post : function() {
                deferred.resolve({'status':'200', 'message':'Logout Successful'});
                return deferred.promise;
            }
        });
    });
    var injectUserService = inject(function(UserService) {
        localUserService = UserService;
    });
    var injectUtilsService = inject(function(UtilsService) {
        localUtilsService = UtilsService;
    });
    var injectLocalStoreService = inject(function(LocalStoreService) {
        localStoreService = LocalStoreService;

        spyOn(localStoreService, 'clearUserItems').andCallThrough();
    });
    var injectRestApiService = inject(function(_RestApiService_) {
       localRestApiService = _RestApiService_;
    });

    describe('dependancy injection tests', function() {

        beforeEach(function() {
            injectRestangular();
            injectUserService();
        });

        it('should inject dependencies', function() {
            expect(localRestangular).toBeDefined();
            expect(localUserService).toBeDefined();
        });

    });

    describe('Get the current active user', function() {

        beforeEach(function() {
            injectUserService();
            injectLocalStoreService();

            spyOn(localStoreService, 'getLSItem').andReturn('jack123');
        });

        it('should get the current logged in user', function() {
            //invoke the service function
            var result = localUserService.getCurrentUser();

            expect(localStoreService.getLSItem).toHaveBeenCalledWith('UserName');
            expect(result).toEqual('jack123');
        });
    });

    describe('fetch userlist from api in fn "getUserList"', function() {
        beforeEach(function() {
            injectUserService();
            injectRestApiService();
        });

        it('should list available user list', inject(function($q) {
            var def = $q.defer();
            spyOn(localRestApiService, 'getAll').andReturn(def.promise);

            //invoke service function
            localUserService.getUserList(20, 0);

            expect(localRestApiService.getAll).toHaveBeenCalled();
        }));

        it('should list available user count', inject(function($q) {
            var def = $q.defer();
            spyOn(localRestApiService, 'getAll').andReturn(def.promise);

            //invoke service function
            localUserService.getUserCount();

            expect(localRestApiService.getAll).toHaveBeenCalled();
        }));

    });


    describe('user logout tests', function() {

        beforeEach(function() {
            injectRestApiService();
            injectUserService();
            injectLocalStoreService();
            injectUtilsService();
        });

        it('should logout the user', inject(function($rootScope, $q) {
            var def = $q.defer();
            def.resolve();

            spyOn(localRestApiService, 'post').andReturn(def.promise);
            spyOn(localUtilsService, 'clearAllWidgetData').andReturn();
            spyOn(localUtilsService, 'raiseNotification').andReturn();

            //invoke service function
            localUserService.logout('Bob');

            localScope = $rootScope.$new();
            localScope.$digest();

            expect(localRestApiService.post).toHaveBeenCalled();
            expect(localStoreService.clearUserItems).toHaveBeenCalled();
            expect(localUtilsService.clearAllWidgetData).toHaveBeenCalled();
            expect(localUtilsService.raiseNotification).toHaveBeenCalled();
        }));
    });

    describe('user ProfileData tests', function() {
        var localStore;

        beforeEach(inject(function(LocalStoreService) {
            localStoreService = LocalStoreService;
            injectRestangular();
            injectRestApiService();
            injectUserService();
            injectLocalStoreService();
        }));

        it('should get the profile data using  getProfile() method', inject(function($rootScope) {
            spyOn(localUserService, 'getUserInfo').andReturn();

            //clean local storage
            localStoreService.removeLSItem('ProfileData');

            //invoke service function
            localUserService.getProfile();

            expect(localUserService.getUserInfo).toHaveBeenCalled();
        }));

        it('should get the profile data using  getProfile() method  from local storage', inject(function($rootScope) {
            var profileData;
            localUserService.saveProfileToLS(mockProfileData);
            localUserService.getProfile().then(function (response) {
                profileData = response;
            });
            localScope = $rootScope.$new();
            localScope.$digest();
            expect(profileData).toEqual(mockProfileData);
        }));

        it('should get the profile data using  getProfile() method with force refresh', inject(function($rootScope) {
            spyOn(localUserService, 'getUserInfo').andReturn();

            //clean local storage
            localStoreService.removeLSItem('ProfileData');

            //invoke service function
            localUserService.getProfile(true);

            expect(localUserService.getUserInfo).toHaveBeenCalled();
        }));

        it('should save the profile data to LocalStorage using saveProfileToLS() method', inject(function($rootScope) {
            localUserService.saveProfileToLS(mockProfileData);
            expect( localStoreService.getLSItem('ProfileData')).toEqual(mockProfileData);
        }));

        it('should handle getAmdLogo() method', function() {
            spyOn(localUserService, 'getAmdLogo').andCallThrough();
            localUserService.getAmdLogo();
            expect( localUserService.getAmdLogo).toHaveBeenCalled();
        });

        it('should handle getCustomerLogo() method', function() {
            spyOn(localUserService, 'getCustomerLogo').andCallThrough();
            localUserService.getCustomerLogo();
            expect( localUserService.getCustomerLogo).toHaveBeenCalled();
        })

    });

    describe('user permission tests', function () {
        beforeEach(inject(function (LocalStoreService) {
            localStoreService = LocalStoreService;
            injectUserService();
        }));

        it('should check user permission for Jack', inject(function ($rootScope) {
            var res;
            localStoreService.addLSItem(null, 'UserInfo', "Jack");
            localUserService.getUserPermissions().then(function (response) {
                res = response;
            });
            localScope = $rootScope.$new();
            localScope.$digest();
            expect(res).toEqual(jackMockData);
        }));

        it('should check user permission for Jill', inject(function ($rootScope) {
            var res;
            localStoreService.addLSItem(null, 'UserInfo', "Jill");
            localUserService.getUserPermissions().then(function (response) {
                res = response;
            });
            localScope = $rootScope.$new();
            localScope.$digest();
            expect(res).toEqual(jillMockData);
        }));

    });

});