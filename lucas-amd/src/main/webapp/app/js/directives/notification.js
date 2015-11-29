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

amdApp.directive('notification', ['$timeout', '$rootScope', 'EXCEPTION_LEVELS',
function(timeout, rootScope, EXCEPTION_LEVELS) {

    return {
        restrict : 'E',
        scope : {},
        replace : true,
        templateUrl : 'views/notification.html',
        link : function(scope, element, attrs) {

            scope.showNotification = false;
            scope.notificationDetails = {};

            //watch for notification messages
            rootScope.$on('raiseNotification', function(event, args) {

                console.log(JSON.stringify(args));

                //compile the notification details
                scope.notificationDetails.type = (function getNotificationType() {
                    //map the levels so the correct notification CSS class is used
                    //when rendering the notification
                    if(args.level) {
                        if(angular.equals(args.level, EXCEPTION_LEVELS.INFORMATION)) {
                        //if(angular.equals(args.level, "INFO")) {
                            return "success";
                        }
                        else if(angular.equals(args.level, EXCEPTION_LEVELS.WARNING)) {
                            return "warning";
                        }
                        else if(angular.equals(args.level, EXCEPTION_LEVELS.ERROR)) {
                            return "danger";
                        }
                        else {
                            return "success";
                        }
                    }

                    //default notification to INFO if no level is explicitly set
                    return "success";
                })();

                //if this is a error notification, then display additional information
                //about the error, such as an error code and unique-reference for support
                //purposes. Else, just show the message
                if (angular.equals(args.level, EXCEPTION_LEVELS.ERROR)) {

                    scope.notificationDetails.errorCode = args.code;
                    scope.notificationDetails.uniqueKey = args.uniqueKey;
                }

                //set the message
                scope.notificationDetails.message = args.message;

                //finished compiling the notification - display it!
                scope.showNotification = true;

                //auto dismiss the message after 3 seconds
                if (!args.hasOwnProperty("explicitDismissal")
                    || angular.equals(args.explicitDismissal, false)) {

                    timeout(function() {
                        scope.dismissNotification();
                    }, 3000);
                }

                //dismiss the notification
                scope.dismissNotification = function() {
                    scope.notificationDetails = {};
                    scope.showNotification = false;
                };
            });
        }
    };
}]);
