/**
 * Created by Shaik Basha on 11/13/2014.
 */

'use strict';
amdApp.directive('groupMaintenanceNewGroup', ['$document', '$rootScope', '$filter', 'UtilsService', function ($document, rootScope, $filter, UtilsService) {

    var link = function (scope, elem, attr) {
        scope.onBlur = function () {
            scope.onSelect(scope.newGroupName);
        };

        scope.onSelect = function (groupName) {
            if (UtilsService.findIndexByName(scope.widgetdetails.groupNames, groupName) != -1) {
                var msg = {
                    "status": "success",
                    "code": 200,
                    "level": "danger",
                    "explicitDismissal": true,
                    "message": $filter('translate')('groupAssignment.groupExists')
                };
                rootScope.$broadcast('showNotification', msg);
            }
        };
    };

    return {
        restrict: 'E',
        template: '<input ng-model="newGroupName" data-ng-blur="onBlur()">',
        link: link
    };
}]);
