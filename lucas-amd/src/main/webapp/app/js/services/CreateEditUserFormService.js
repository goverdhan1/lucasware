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

/**
 * Services related to saving the CreateEditUserForm Widget.
 */
amdApp.factory('CreateEditUserFormService', ['RestApiService', '$log', '$q', '$timeout', '$filter', '$translate',
function(restApiService, log, q, timeout, filter, $translate) {

    //Class variables to cache static data
    var languages = undefined;
    var shifts = undefined;
    var groups = undefined;
    var skills = undefined;
    var usernames = undefined;

    var factory = {};

    //
    // Gets a list of supported LANGUAGES.
    // Makes an API call to "/languages" if data has not already been cached
    //
    factory.getSupportedLanguages = function() {
        //Data is cached, no need to fetch from server again!
        if (angular.isDefined(languages)) {

            //Send back promise containing languages
            var def = q.defer();
            def.resolve(languages);
            return def.promise;
        }
        else {
            //initialise object
            languages = {
                amd : [],
                hh : [],
                j2u : [],
                u2j : []
            };

            //Get languages from API
            return restApiService.getAll("languages", null, null).then(function(response) {
                //loop over languages and construct array of supported languages for
                //AMD, Device, Jen2User, User2Jen in cached object
                for (var i = 0; i < response.length; i++) {

                    var langObj = response[i];
                    var languageCode = langObj.languageCode.toUpperCase();

                    if (langObj.hasOwnProperty('amdLanguage')
                        && angular.equals(langObj.amdLanguage, true)) {
                        languages.amd.push(languageCode);
                    }

                    if (langObj.hasOwnProperty('hhLanguage')
                        && angular.equals(langObj.hhLanguage, true)) {
                        languages.hh.push(languageCode);
                    }

                    if (langObj.hasOwnProperty('j2uLanguage')
                        && angular.equals(langObj.j2uLanguage, true)) {
                        languages.j2u.push(languageCode);
                    }

                    if (langObj.hasOwnProperty('u2jLanguage')
                        && angular.equals(langObj.u2jLanguage, true)) {
                        languages.u2j.push(languageCode);
                    }
                }

                return languages;
            }, function(ex) {
            });
        }
    };


    //
    // Gets a list of available SHIFTS.
    // Makes an API call to "/shifts" if data has not already been cached
    //
    factory.getAvailableShifts = function() {
        //Data is cached, no need to fetch from server again!
        if (angular.isDefined(shifts)) {

            //Send back promise containing shifts
            var def = q.defer();
            def.resolve(shifts);

            return def.promise;
        }
        else {
            //Get shifts using API
            return restApiService.getAll("shifts", null, null).then(function(response) {
                //cache the server response for future performance
                shifts = response;
                return shifts;
            }, function(ex) {
            });
        }
    };


    //
    // Gets a list of available GROUPS.
    // Makes an API call to "/groups" if data has not already been cached
    //
    factory.getAvailableGroups = function() {
        //Data is cached, no need to fetch from server again!
        if (angular.isDefined(groups)) {

            //Send back promise containing groups
            var def = q.defer();
            def.resolve(groups);

            return def.promise;
        }
        else {
            //get groups from API
            return restApiService.getAll("groups", null, null).then(function(response) {
                 //cache the server response for future performance
                 groups = response;
                 return groups;
            }, function(ex) {
            });
        }
    };


    //
    // Gets a list of available SKILLS.
    // There is no proposed endpoint for this call, so for the
    // time being they are hardcoded. This may change in the
    // future
    //
    factory.getAvailableSkills = function() {
        //If data has already been cached, no need to process
        //it again. Just return it.
        if (angular.isDefined(skills)) {

            //Send back promise containing groups
            var def = q.defer();
            def.resolve(skills);

            return def.promise;
        }
        else {
            //Get skills
            return restApiService.post('/application/codes', null, ["USER_SKILL"]).then(
                function(userSkills) {
                    skills = userSkills.USER_SKILL;
                    return skills;
                },
                function(error) {
                    throw new LucasBusinessException(error);
                }
            );
        }
    };


    //
    // Gets a list of USERS
    // Makes an API call to "/users/usernames" if data has not already been cached
    //
    factory.getUsernames = function() {
        //Data is cached, no need to fetch from server again!
        if (angular.isDefined(usernames)) {

            //Send back promise containing users
            var def = q.defer();
            def.resolve(usernames);

            return def.promise;
        }
        else {
            //get list of user
            return restApiService.getAll("users/usernames", null, null).then(function(response) {
                 //cache the server response for future performance
                 usernames = response;
                 return usernames;
            }, function(ex) {
            });
        }
    };


    //
    // Check if user already exists
    // Returns boolean
    //
    factory.checkUserExists = function(user) {
        return (usernames.indexOf(user) > -1);
    };


    //
    // Reoloads the user cache - hits the /users/usernames endpoint
    // to get an up-to-date list of all current users. This function
    // is called when a user is created/deleted via AMD to ensure the cached
    // list of users is in sync with the DB
    //
    factory.reloadUsernameCache = function() {
        this.getUsernames().then(function(username_list) {
            usernames = username_list;
        }, function(ex) {
            //an error occurred
            console.log("error loading username cache [" + JSON.stringify(ex) + "]");
            usernames = (angular.isDefined(usernames) ? usernames : []);
        });
    };


    //
    // Makes an API call to endpoint '/users/details/{username}'
    // Returns user object containing requested users information if user
    // exists. returns NULL if the user doesn't exist
    //
    factory.getUserDetails = function(username) {
        //POST request to /users/details
        return restApiService.post('/users/details',null, [username]).then(
            function(response) {
                return response[0];
            },
            function(error) {
                //throw meaningful exception back to controller
                error.message = $translate.instant('constants.FETCH_USER_DETAILS_ERROR');
                throw new LucasBusinessException(error);
            }
        );
    };


    //
    // Makes an API call to endpoint '/groups/{username}'
    // Returns array object containing users assigned groups.
    // Empty array is returned if user is not assigned to any groups
    //
    factory.getGroupsForUser = function(username) {
        //POST request to /users/groups
        return restApiService.post('/users/groups', null, [username]).then(
            function(response) {
                return response[username];
            }, function(error) {
                throw new LucasBusinessException(error);
            }
        );
    };


    //
    // Used for creating a new user and editing an existing user.
    // Sends user object to endpoint '/users/save'. The end point will
    // evaluate if the user already exists (by username) and if so will
    // do an update, else it will do an insert
    //
    factory.saveUser = function(details, groups) {

        //construct the post bodies for each of the API calls
        var detailsPost = {};
        var groupsPost = {};

        //set the values
        if (details.hasOwnProperty('username')) {
            detailsPost.username = details.username[0];
            groupsPost.userName = details.username[0];
        }
        if (details.hasOwnProperty('firstName')) {
            detailsPost.firstName = details.firstName;
        }
        if (details.hasOwnProperty('lastName')) {
            detailsPost.lastName = details.lastName;
        }
        if (details.hasOwnProperty('employeeNumber')) {
            detailsPost.employeeNumber = details.employeeNumber;
        }
        if (details.hasOwnProperty('skill')) {
            detailsPost.skill = details.skill;
        }
        if (details.hasOwnProperty('mobileNumber')) {
            detailsPost.mobileNumber = details.mobileNumber;
        }
        if (details.hasOwnProperty('emailAddress')) {
            detailsPost.emailAddress = details.emailAddress;
        }
        if (details.hasOwnProperty('startDate')) {
            detailsPost.startDate = filter('date')(details.startDate, 'yyyy-MM-dd');
        }
        if (details.hasOwnProperty('birthDate')) {
            detailsPost.birthDate = filter('date')(details.birthDate, 'yyyy-MM-dd');
        }
        if (details.hasOwnProperty('shift')) {
            detailsPost.shift = {
                shiftId : details.shift
            };
        }
        if (details.hasOwnProperty('amdLanguage')) {
            detailsPost.amdLanguage = {
                languageCode : details.amdLanguage
            };
        }
        if (details.hasOwnProperty('hhLanguage')) {
            detailsPost.hhLanguage = {
                languageCode : details.hhLanguage
            };
        }
        if (details.hasOwnProperty('u2jLanguage')) {
            detailsPost.u2jLanguage = {
                languageCode : details.u2jLanguage
            };
        }
        if (details.hasOwnProperty('j2uLanguage')) {
            detailsPost.j2uLanguage = {
                languageCode : details.j2uLanguage
            };
        }
        if (details.hasOwnProperty('password') && details.password.length) {
            detailsPost.plainTextPassword = details.password;
        }
        if (details.hasOwnProperty('hostLogin')) {
            detailsPost.hostLogin = details.hostLogin;
        }
        if (details.hasOwnProperty('hostPassword')) {
            detailsPost.hostPassword = details.hostPassword;
        }

        groupsPost.assignedGroups = (groups.length) ? groups : [];

        console.log(JSON.stringify(detailsPost));
        console.log(groupsPost);

        //function to save the user
        function saveDetails(postBody) {
            return restApiService.post('/users/save', null, postBody).then(
                function() {
                return;
                }, function(error) {
                    throw new LucasBusinessException(error);
                }
            );
        }

        //function to assign user groups
        function assignGroups(postBody) {
            return restApiService.post('/users/groups/assign', null, postBody).then(
                function() {
                    return;
                }, function(error) {
                    throw new LucasBusinessException(error);
                }
            );
        }

        //Do the save operations
        return saveDetails(detailsPost).then(function() {
            //then assign the groups
            return assignGroups(groupsPost);
        }, function(error) {
            console.log(error);
        });
    };

    // Gets users details in multi-edit mode
    factory.getMultiUsersDetails = function(userNamelist) {
        return restApiService.post("users/multiedit/details", null, userNamelist).then(
            function(response) {
                return response;
            },
            function(error) {
                throw  new LucasBusinessException(error)
            }
        );
    };

    // save multi users details
    factory.saveMultiUsersDetails = function(updatedFields) {
        return restApiService.post('/users/multiedit/save', null, updatedFields).then(
            function(response) {
                return response;
            },
            function(error) {
                throw  new LucasBusinessException(error)
            }
        );
    };

    return factory;
}]);
