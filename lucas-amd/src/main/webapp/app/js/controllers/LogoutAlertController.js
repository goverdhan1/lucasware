// TODO:TRIPOD: Not the ideal way of implementing i18n.  The strings shall be pulled from a resource file.
'use strict';

amdApp.controller('LogoutAlertController', ['$rootScope', '$scope', 'LogoutAlertService', '$timeout',
    function (rootScope, scope, LogoutAlertService, $timeout) {
        var timeoutPromise;

        rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            // set the message after logging into the application, the message will be used once the user logouts
            if (fromState.name == 'indexpage.home') {
                LogoutAlertService.setAlerts([
                    {
                        type: 'success',
                        msg: 'Logout Successful'
                    }
                ]);
            }
        });

        scope.alerts = LogoutAlertService.getAlerts();

        if (scope.alerts) {
            timeoutPromise = $timeout(function () {
               scope.alerts = [];
            }, 3000);
        }

        scope.closeAlert = function () {
            if (timeoutPromise) {
                $timeout.cancel(timeoutPromise);
            }
            scope.alerts = [];
        };

        scope.$on('$destroy', function () {
            if (timeoutPromise) {
                $timeout.cancel(timeoutPromise);
            }
        });

    }]);
