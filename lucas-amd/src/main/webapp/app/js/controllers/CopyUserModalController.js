'use strict';

amdApp.controller('CopyUserModalController', ['$log', '$scope', '$modalInstance', 'userList',
function(log, scope, modalInstance, userList) {

    //initialise model for capturing New Lucas Login
    scope.newLucasLogin = "";

    //initialise indicators used to control modal presentation
    scope.isValidLogin = false;
    scope.userIsEmpty = false;
    scope.userAlreadyExists = false;


    //watcher function invoked by ng-change directive on
    //New Lucas Login field
    scope.validateNewLucasLogin = function() {
        //Make sure something has been entered
        if (angular.isUndefined(scope.newLucasLogin) || scope.newLucasLogin.trim().length === 0) {
            scope.isValidLogin = false;
            scope.userIsEmpty = true;
            return;
        }

        //Ensure username doesn't already exist - trim off any leading/trailing spaces
        if (userList.indexOf(scope.newLucasLogin.trim()) > -1) {
            scope.isValidLogin = false;
            scope.userAlreadyExists = true;
            return;
        }

        //The login entered is valid - enable Save button and hide messages
        scope.isValidLogin = true;
        scope.userIsEmpty = false;
        scope.userAlreadyExists = false;
    };
}]);
