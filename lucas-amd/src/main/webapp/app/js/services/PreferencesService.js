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

amdApp.factory('PreferencesService', ['RestApiService', '$q',
function (RestApiService, q) {

    var factory = {};

    //
    // used to get user preferences from the server
    //
    factory.getUserPreferences = function (user) {
        return RestApiService.getOne('users/preferences/' + user).then(function(preferences) {
            return preferences;
        });
    };

    //
    // used to save/update user preferences
    //
    factory.saveUserPreferences = function(preferences) {
        return RestApiService.post('users/preferences/save', null, preferences);
    };

    //
    // fetch data required for rendering the preferences UI
    //
    factory.getPreferencesOptions = function(){
        var postBody = [
            "DATE_FORMAT",
            "TIME_FORMAT"
        ];
        return RestApiService.post('application/codes', null, postBody).then(function (options) {
            return options;
        });
    };

    //
    // returns the necessary information required for rendering the preferences UI
    //
    factory.getPreferenceData = function(user) {
        return q.all([
            this.getPreferencesOptions(),
            this.getUserPreferences(user)
        ]).then(function (response) {
            //construct a meaningful response object
            var data = {};
            data.options = response[0];
            data.preferences = response[1];

            //convert the refresh frequency to a int.. JSON converts it
            //to a string when sending over network
            data.preferences.dataRefreshFrequency = parseInt(data.preferences.dataRefreshFrequency);

            return data;
        });
    };

    return factory;
}]);