(function () {
    'use strict';
    angular.module('amdApp').directive('widgetsPallete', widgetsPallete);

    //inject angular variables
    widgetsPallete.$inject = ['WidgetsPalletteService', 'UtilsService'];

    //based on declared angular variables, pass your specific args in that order
    function widgetsPallete(WidgetsPalletteService, UtilsService) {
        var linkFunction = function (scope, elem, attrs) {
            // selected vairables
            scope.selectedCategory = null;
            scope.selectedWidget = null;

            // select the widget
            scope.selectWidget = function (widget) {
                console.log(widget);
                scope.selectedWidget = widget;
                scope.onSelect({selectedWidget: widget})
            };

            // select the category
            scope.selectCategory = function (categoryName) {
                scope.selectedCategory = categoryName;
            };

            // process the names when the data is set
            scope.$watch('widgets', function (oldValue, newValue) {
                scope.names = UtilsService.parseNames(scope.widgets);
                if (scope.names.length > 0) {
                    scope.selectCategory(scope.names[0].name)
                }
            });
        };

        return {
            restrict: "EA",
            templateUrl: "views/widgets/widgets-pallette-widget.html",
            scope: {
                widgets: "=",
                onSelect: "&"
            },
            link: linkFunction
        };
    }
})();