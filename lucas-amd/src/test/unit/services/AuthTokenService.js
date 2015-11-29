'use strict';

describe('AuthTokenService related Tests', function () {
    var localAuthTokenService;
    var token = {sessionToken: 'amFjayEhITE0MDU2NjA5MzI0ODAhISGuTTHMZnmpVYqWVJ/0Ru2G', userName: 'jack'};

    beforeEach(module('amdApp'));
    beforeEach(inject(function (AuthTokenService) {
        localAuthTokenService = AuthTokenService;
    }));

    describe('dependency injection tests', function () {
        it('should inject dependencies', function () {
            expect(localAuthTokenService).toBeDefined();
        });
    });

    describe('should be able to set and get AuthToken', function () {
        it('should handle getAuthenticationOrAuthorizationFailed method', inject(function ($rootScope) {
            localAuthTokenService.setAuthToken(token);
            expect(localAuthTokenService.getAuthToken()).toEqual(token);
        }));
    });
});