amdApp.directive("createCanvas", ['$compile', '$window', '$http', 'LocalStoreService',
    function (compile, window, http, LocalStoreService) {
        var linkFunction = function (scope, element, attrs) {
            var renderHTML = function () {
                console.log("In renderHTML");
                var templateLocation = 'views/createCanvas/' + scope.canvasLayoutId + '.html';
                http.get(templateLocation).then(function (response) {
                    element.html(compile(response.data)(scope));
                });
            };

            scope.$watch('canvasLayoutId', function (oldValue, newValue) {
                if (oldValue != newValue) {
                    renderHTML();
                }
            });

            // initial renderHTML
            renderHTML();
        };
        return {
            restrict: "EA",
            replace: true,
            link: linkFunction,
            controller: 'CreateCanvasController'
        };
    }]);