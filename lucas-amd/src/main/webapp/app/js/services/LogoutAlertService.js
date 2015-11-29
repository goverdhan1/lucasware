'use strict';

/**
 * Services related to Logout Alert
 */
amdApp.factory('LogoutAlertService', [function (rootScope) {
    var alerts = [];
    return {
        setAlerts: function (data) {
            alerts = data;
        },
        getAlerts: function () {
            return alerts;
        }
    };
}]);
