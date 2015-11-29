'use strict';
// directive extension
amdApp.directive('datepickerPopup', function ($timeout, $parse) {
    return {
        restrict: 'AE',
        require: [
            '?datepickerPopup'
        ],
        link: function (scope, elem, attrs) {
            // retrieve the isolateScope
            var iScope = angular.element(elem).isolateScope();
            elem.on("change", function () {
                // call the bootstrap date selection method
                iScope.dateSelection();
            });
        }
    };
});