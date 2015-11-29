/**
 * Created by Shaik Basha on 11/14/2014.
 */

'use strict';

amdApp.controller('UserGroupFormController', ['$scope', 'UtilsService', 'LocalStoreService',
    function (scope, UtilsService, LocalStoreService) {

        var widgetInstanceObj = scope.widgetdetails;
        scope.widgetdetails.userGroups = widgetInstanceObj.data.userGroups;
        scope.widgetdetails.userNames = [];

        //controls MoveData directive (enable/disabled)
        scope.allowEdit = true;
        scope.isSaving = false;

        var dimensionsforWidgets = LocalStoreService.getLSItem(scope.widgetdetails.id + "|" + scope.widgetdetails.widgetDefinition.id + "|ViewDimensions");
        var formHeight;
        if (dimensionsforWidgets !== null) {
            formHeight = dimensionsforWidgets.height - 70;
        } else {
            formHeight = scope.widgetdetails.actualViewConfig.height - 70;
        }
        if (scope.widgetdetails.isMaximized) {
            formHeight = $(".panel-body").height() - 560;
            $(".form-horizontal").css({
                "height": formHeight + "px"
            });
        } else {
            $(".form-horizontal").css({
                "height": formHeight + "px"
            });
        }

        scope.widgetdetails.userNames = UtilsService.parseNames(scope.widgetdetails.userGroups);

        scope.allGroups = UtilsService.parseNamesWithoutKey(scope.widgetdetails.data.availableGroups);

        var oldSelectedUser;

        // Used ng change instead of $watch for performance
        scope.updateSelectedUser = function (selectedUser) {
            if (selectedUser) {
                scope.widgetdetails.existingItems = scope.widgetdetails.userGroups[selectedUser];
                scope.widgetdetails.availableItems = scope.allGroups;
                if (oldSelectedUser != selectedUser) {
                    scope.widgetdetails.resultantPermissions = [];
                }
                scope.onChange();
                oldSelectedUser = selectedUser;
            }
        };

        /**
         *  onChange method handles when the existing groups have been changes,
         *  based on these all of the unique permissions of the groups should be added
         *
         */
        scope.onChange = function () {
            var items = [];
            angular.forEach(scope.widgetdetails.existingItems, function (value, key) {
                angular.forEach(scope.widgetdetails.data.availableGroups[value], function (value, key) {
                    if (items.indexOf(value) == -1)
                        items.push(value);
                });
            });
            scope.widgetdetails.resultantPermissions = items;
        };

        // resource Strings for availablePermissions and existingPermissions
        scope.widgetdetails.strAvailableItems = "userGroup.availableGroups";
        scope.widgetdetails.strExistingItems = "userGroup.existingGroups";
        scope.widgetdetails.resultantPermissions = [];

        // selected
        scope.widgetdetails.selectedItemName = null;
        scope.widgetdetails.selectedAvailableItems = [];
        scope.widgetdetails.selectedExistingItems = [];

    }]);
