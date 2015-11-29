/**
 * Created by Shaik Basha on 10/11/2014.
 */
'use strict';
amdApp.directive('moveData', ['UtilsService', '$parse', function (UtilsService, $parse) {
    var link = function (scope, elem, attr) {

        /**
         * Maintains a scope level variable to control the template controls.
         * The $observe function gets read on every scope digest cycle so it
         * will keep the form controls consistently rendered in the correct state
         */
        attr.$observe('isDisabled', function() {
            scope.isDisabled = attr.isDisabled == "true";
        });

        /**
         * gets the onChange method defined in the parent scope
         */
        var invoker = $parse(scope.onChange);

        /**
         * calls parent onChange method if there is any change of data from left to right boxes and vice versa
         */
        function dispatchChangeEvent() {
            if (invoker) {
                invoker(scope);
            }
        }

        /**
         *  Moves the selected available records to existing records
         */
        scope.moveOneToExistingData = function () {
            var i, index;
            for (i = 0; i < scope.widgetdetails.selectedAvailableItems.length; i++) {
                scope.widgetdetails.existingItems.push(scope.widgetdetails.selectedAvailableItems[i]);
            }
            scope.widgetdetails.selectedAvailableItems = [];
            dispatchChangeEvent();
        };

        /**
         *  Moves the selected existing records to available records
         */
        scope.moveOneToAvailableData = function () {
            var i, index;
            for (i = 0; i < scope.widgetdetails.selectedExistingItems.length; i++) {
                index = scope.widgetdetails.existingItems.indexOf(scope.widgetdetails.selectedExistingItems[i]);
                if (index != -1) {
                    scope.widgetdetails.existingItems.splice(index, 1);
                }
            }
            scope.widgetdetails.selectedExistingItems = [];
            dispatchChangeEvent();
        };

        /**
         *  Moves all the existing records to available records irrespective of the user selection
         */
        scope.moveAllToExistingData = function () {
            var i, index;
            for (i = 0; i < scope.widgetdetails.availableItems.length; i++) {
                if (scope.widgetdetails.existingItems.indexOf(scope.widgetdetails.availableItems[i]) == -1) {
                    scope.widgetdetails.existingItems.push(scope.widgetdetails.availableItems[i]);
                }
            }
            scope.widgetdetails.selectedAvailableItems = [];
            dispatchChangeEvent();
        };


        /**
         *  Moves all the available records to existing records irrespective of the user selection
         */
        scope.moveAllToAvailableData = function () {
            // to empty all references
            scope.widgetdetails.existingItems.length = 0;
            scope.widgetdetails.selectedExistingItems = [];
            dispatchChangeEvent();
        };


        scope.removeExistingDataFilter = function (item) {
            return scope.widgetdetails.existingItems && scope.widgetdetails.existingItems.indexOf(item) == -1;
        };

        scope.removeExistingDataFilter();
    };

    return {
        restrict: 'E',
        templateUrl: "views/move-data.html",
        link: link
    };
}]);