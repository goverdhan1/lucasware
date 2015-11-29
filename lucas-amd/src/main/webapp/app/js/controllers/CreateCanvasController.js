amdApp.controller('CreateCanvasController', ['$scope', '$modal', function (scope, modal) {

    // show the widgets pallette
    scope.loadWidget = function () {
        // display the popup
        var createCanvasModal = modal.open({
            templateUrl: 'views/modals/widgets-pallete.html',
            controller: 'WidgetsPalletteModalController',
       		backdrop: 'static' 
        });
    };

}]);