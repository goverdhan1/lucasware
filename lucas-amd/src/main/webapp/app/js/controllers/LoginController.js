// TODO:TRIPOD: Not the ideal way of implementing i18n.  The strings shall be pulled from a resource file.
'use strict';

amdApp.controller('LoginController', ['$log', '$location', '$rootScope', '$scope', '$state', 'SessionService', 'HomeCanvasListService', 'UserService', 'LocalStoreService', '$modal', '$timeout', 'LoginService',
    function (log, location, rootScope, scope, state, SessionService, HomeCanvasListService, UserService, LocalStoreService, modal, $timeout, LoginService) {

        scope.loginSubmit = function () {
            var credentials = {
                username: scope.username,
                password: scope.password
            };
           LoginService.login(credentials);
        };
        var handle1001 = scope.$on('1001:loginRequired', function (msg, data) {
            scope.username = '';
            scope.password = '';
            scope.errorPanel = {
                //"message": data.message
                // just return the coded string for localisation @ frontend
                "message": "login.invalidUserNameOrPassword"
            };
        });

        scope.$on('$destroy', function () {
            handle1001();
        });
    }]);