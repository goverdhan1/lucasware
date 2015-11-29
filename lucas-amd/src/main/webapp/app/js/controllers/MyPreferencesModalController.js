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

amdApp.controller('MyPreferencesModalController', ['$rootScope', '$scope', '$modalInstance', 'UserService', 'PreferencesService',
function (rootScope, scope, modalInstance, UserService, PreferencesService) {

    var currentUser = UserService.getCurrentUser();

    //initialise scope variables
    scope.dateFormats = [];
    scope.timeFormats = [];
    scope.selectedDateFormat = null;
    scope.selectedTimeFormat = null;
    scope.refreshInterval = 0;

    //Grab the data required to render the User Preferences UI
    PreferencesService.getPreferenceData(currentUser).then(function(response) {
        scope.dateFormats = response.options.DATE_FORMAT;
        scope.timeFormats = response.options.TIME_FORMAT;

        scope.selectedDateFormat = response.preferences.dateFormat;
        scope.selectedTimeFormat = response.preferences.timeFormat;
        scope.refreshInterval = response.preferences.dataRefreshFrequency;
    });

    //Handle modal actions
    modalInstance.result.then(function(response) {
        var preferences = {};
        preferences.userName = currentUser;
        preferences.dateFormat = scope.selectedDateFormat;
        preferences.timeFormat = scope.selectedTimeFormat;
        preferences.dataRefreshFrequency = scope.refreshInterval;

        PreferencesService.saveUserPreferences(preferences).then(function() {
            rootScope.$emit('UserPreferencesSaved', preferences);
        });

    }, function() {
        console.log("Discarding User Preference changes");
    });
}]);