'use strict';

amdApp.directive('listener', ['$timeout', '$rootScope', 'UtilsService',
function($timeout, rootScope, UtilsService) {
	var linkFunction = function(scope, element, attrs) {
		scope.receivedInfo = null;
		scope.$on('broadcastedItems', function(angularEvent, args) {
			if (scope.listenInfo) {
				var showIncomingItems = UtilsService.showListenItems(args.items, scope.listenInfo);
				if (!($.isEmptyObject(showIncomingItems))) {
					scope.receivedInfo = {
						"from" : args.from,
						"receivedInfo" : showIncomingItems
					};
				} else {
					scope.receivedInfo = null;
				}
			}
		});
	};
	return {
		restrict : 'E',
        scope : {
            listenInfo : "=",
            receivedInfo : "="
        },
        replace : true,
		link : linkFunction,
		templateUrl : 'views/listener.html'
	};
}]);
