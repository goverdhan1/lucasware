amdApp.errorCheckInterceptor = ['$location', '$rootScope', '$q', '$log', 'LocalStoreService',
function(location, rootScope, q, log, LocalStoreService) {
	// When the status in header is 200, we can't assume everything is fine yet.
	// So, we'll handle the other status codes that AMD puts inside the response like 401 etc
	function success(response) {
		var code = response.data.code;

		if (code == 401) {
			log.warn('AMD-Response status: ' + code);
			rootScope.$broadcast('401:loginRequired', response.data);
		} else if (code == 403) {
			log.warn('AMD-Response status: ' + code + ', Message: ' + response.data.message);
			rootScope.$broadcast('403:accessDenied', response.data);
		} else if (code == 1001) {
			//log.warn('AMD-Response status: ' + code + ', Message: ' + response.data.message);
			//rootScope.$broadcast('1001:loginRequired', response.data);
			var msg = {
				"status" : "success",
				"code" : response.data.code,
				"level" : "danger",
				"explicitDismissal" : true,
				"message" : code + ': ' + response.data.message
			};
			rootScope.$broadcast('showNotification', msg);
		} else if (code == 500) {// Internal Server Error
			//log.error('AMD-Response status: ' + code + ', Message: ' + response.data.message);
		} else if (code == undefined) {// like in case of views
			// do-nothing;
		} else {// status may be 200!
			//$log.info('AMD-Response status: ' + code);
		}
		
		//logout notification is temporary; once server follows the structure for logout notification, this will be removed.
		if (response.data.message === "Logout Successfull") {
			var msg = {
				"status" : "success",
				"code" : response.data.code,
				"level" : "success",
				"explicitDismissal" : false,
				"message" : response.data.message
			};
			rootScope.$broadcast('showNotification', msg);
		}

		//catch all level responses - this is for showing notifications from server
		if (response.data.level === "WARN") {
			log.warn(response.data);
		} else if (response.data.level === "INFO") {
			log.info('showNotification', response.data);
		}
		
		return response;
	}

	// responses with 404 in the headers, use it for notification
	function error(response) {
		//catch 404 here		
		if (response.status === 404) {
			var notFound = {
				"status" : "failure",
				"code" : 1009,
				"level" : "danger",
				"explicitDismissal" : true,
				"message" : response.status + ": Page not found."
			};
			rootScope.$broadcast('showNotification', notFound);
		} else if (response.status === 500) {
			response.data.message = response.data.message + ". Contact server administrator with the following id: " + response.data.uniqueKey;
			var notFound = {
				"status" : "failure",
				"code" : 1008,
				"level" : "danger",
				"explicitDismissal" : true,
				"message" : response.data.message
			};
			rootScope.$broadcast('showNotification', notFound);
		}
		log.warn('Angular-Response status: ' + status);
		return q.reject(response);
		//similar to throw response;
	}

	return function(promise) {
		return promise.then(success, error);
	};
}];
