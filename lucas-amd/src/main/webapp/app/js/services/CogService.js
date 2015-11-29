'use strict';

/**
 * Services related to cog menu.
 */
amdApp.factory('CogService', ['$rootScope', '$q', 'LocalStoreService', 'WidgetService',
    function (rootScope, q, LocalStoreService, WidgetService) {
        return {
            refreshWidgetWithUpdatedValues: function (widgetInstanceObj) {
            	//update widgetInstance with changes made in cog menu
                WidgetService.updateWidgetInstance(widgetInstanceObj);
            }
        };
    }
]);

