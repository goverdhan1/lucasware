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

amdApp.factory('SearchUserGridService', ['WidgetGridService', 'RestApiService', '$q',
    function(WidgetGridService, RestApiService, q) {

        var factory = {};

        // common code for active/deactivate users
        factory.setupDataParams = function(selectedRows) {

            console.log('***[SearchUserGridService.setupDataParams]***');
            var data = [];

            for (var i = 0; i < selectedRows.length; i++) {

                /* THIS CHECK WILL BE DONE IN ANOTHER TICKET
                if (selectedRows[i].userName === LocalStoreService.getLSItem('UserInfo'))
                {
                    // prevent user from perform update on its own userName
                }*/

                data.push(selectedRows[i].userName);
            }

            console.log('***[SearchUserGridService.setupDataParams]*** data = ' + data);
            return data;
        };

        factory.getColumnFilterValues = function(userColumn) {
            return RestApiService.post("application/codes", null, userColumn).then(function(response) {
                    return response;
                }, function(error) {
                    return error;
                });
        };

        factory.columnFilterOptions = function(columnName) {
            var userColumn = [];
            var filterOptions={};
            var def = q.defer();

            userColumn.push("USER_" + columnName.toUpperCase());
            if (columnName == "skill") {
                 //  skill dropdown options

                    return this.getColumnFilterValues(userColumn).then(function(r){
                    return r.USER_SKILL;
                    })
               
                } else if (columnName == "j2uLanguage") {
                 //  j2uLanguage dropdown options

                return WidgetGridService.getSupportedLanguages().then(function(r){
                    var USER_J2ULANGUAGE=[];
                    for(var i=0; i< r.j2u.length; i++){
                        USER_J2ULANGUAGE.push({"key": r.j2u[i], "value":r.j2u[i] });
                    }
                    return USER_J2ULANGUAGE; 
                });
                } else if (columnName == "u2jLanguage") {
                 //  u2jLanguage dropdown options

                return WidgetGridService.getSupportedLanguages().then(function(r){
                    var USER_U2JLANGUAGE=[];
                    for(var i=0; i< r.j2u.length; i++){
                        USER_U2JLANGUAGE.push({"key": r.j2u[i], "value":r.j2u[i] });
                    }
                    return USER_U2JLANGUAGE; 
                });
                } else if (columnName == "hhLanguage") {
                 //  hhLanguage dropdown options

                return WidgetGridService.getSupportedLanguages().then(function(r){
                    var USER_HHLANGUAGE=[];
                    for(var i=0; i< r.j2u.length; i++){
                        USER_HHLANGUAGE.push({"key": r.j2u[i], "value":r.j2u[i] });
                    }
                    return USER_HHLANGUAGE; 
                });
                } else if (columnName == "amdLanguage") {
                 //  amdLanguage dropdown options

                return WidgetGridService.getSupportedLanguages().then(function(r){
                    var USER_AMDLANGUAGE=[];
                    for(var i=0; i< r.j2u.length; i++){
                        USER_AMDLANGUAGE.push({"key": r.j2u[i], "value":r.j2u[i] });
                    }
                    return USER_AMDLANGUAGE; 
                });
                } else if (columnName == "shift") {
                 //  shift dropdown options

                return WidgetGridService.getAvailableShifts().then(function(r) {
                    var USER_SHIFT=[];
                    for(var i=0; i< r.length; i++){
                        USER_SHIFT.push({"key": r[i], "value":r[i] });
                    }
                    return USER_SHIFT; 
                });
                } else if (columnName == "enable") {
                 //  enable dropdown options

                    userColumn=["USER_STATUS"];
                    return this.getColumnFilterValues(userColumn).then(function (r) {
                        return r.USER_STATUS;
                    });
                } else if (columnName == "startDate") {
                 //  startDate dropdown options

                    userColumn=["FILTER_DATE"];
                    return this.getColumnFilterValues(userColumn).then(function (r) {
                        return r.FILTER_DATE;
                    });
                } else{
                 //  other dropdown options

                       def.resolve(filterOptions);
                    return def.promise;
                }  

        };

        // Update user record(s) based on selected action
        factory.updateUserRecords = function(action, selectedRows) {
            console.log('***[SearchUserGridService.updateUserRecords]*** with action = ' + action);
            var targetData = this.setupDataParams(selectedRows);
            var restUrl;

            if (action === 'delete-user') {
                restUrl = 'users/delete';
            } else if (action === 'disable-user' || action === 'activate-user') {
                restUrl = (action === 'disable-user') ? 'users/disable' : 'users/enable';
            }

            console.log('***[SearchUserGridService.updateUserRecords]*** restUrl = ' + restUrl);
            console.log('***[SearchUserGridService.updateUserRecords]*** targetData = ' + JSON.stringify(targetData));

            // now call the server code to do its job
            if (restUrl) {
                return RestApiService.post(restUrl, null, targetData)
                    .then(function(response) {
                        return response;
                    }, function(error) {
                        console.log('Failed to update user - ' + error);
                        return error;
                    });
            }
        };

        return factory;

    }
]);