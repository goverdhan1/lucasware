/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States
 
 
The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.
 
All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.
 
Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED
 
*/

'use strict';
amdApp.directive('widgetloader', ['$compile', '$http', 'LocalStoreService', '$timeout', '$window',
function(compile, http, LocalStoreService, timeout, $window) {
	var template = '';
	var ctrlName = '';

	var linkFunction = function(scope, element, attrs) {
		
		element.addClass('widgetloader');
		element.addClass('display-flex');
		element.addClass('display-flex-column');
		element.addClass('flex1');


		// TODO: Need to take advice on reusing the setDeviceSpecificClass method as its present in both lcwidget and widgetloader directives
		/* 
			sets the device specific class based on width passed
            if the width is greater than 540 then the 'device-desktop' class is applied
            if the width is greater than 320 and less than or equals 540 then the 'device-tablet' class is applied
            otherwise the 'device-mobile' class is applied
		*/
		scope.setDeviceSpecificClass = function(width) {
		    console.log("In setDeviceSpecificClass");
		    // remove the device classes
		    element.removeClass('device-mobile');
		    element.removeClass('device-tablet');
		    element.removeClass('device-desktop');

		    // add the device specific class
		    if (width > 540) {
		        element.addClass('device-desktop');
		    } else if (width > 320 && width <= 540) {
		        element.addClass('device-tablet');
		    } else {
		        element.addClass('device-mobile');
		    }
		};

		angular.element($window).on('resize', function(e) {
		    adjustWidth();
		});

		// set the correct width when the widget is maximized
		var adjustWidth = function() {
			var containerWidth = $('.uiViewContentCanvas').width();
		    if (scope.widgetdetails.isMaximized) {
		        scope.setDeviceSpecificClass(containerWidth);
		        return;
		    }
		};

		adjustWidth();

        if (scope.widgetdetails) {
			template = 'views/widgets/' + scope.widgetdetails.widgetDefinition.name + '.html';
			http.get(template).then(function(response) {
				element.html(response.data);
				compile(element.contents())(scope);
			});
		}
	};
	return {
		restrict : 'E',
		scope : {
			widgetdetails : "="
		},
		replace : true,
		link : linkFunction
	};
}]);
