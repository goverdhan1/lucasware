'use strict';

/**
 * Services related to Widgets Pallette
 */
amdApp.factory('WidgetsPalletteService', ['$q', 'RestApiService',
    function ($q, RestApiService) {
        return {
            // gets widgets
            getWidgets: function () {
                return RestApiService.getOne('/widgetdefinitions', null, null);
            }
        };
    }]);