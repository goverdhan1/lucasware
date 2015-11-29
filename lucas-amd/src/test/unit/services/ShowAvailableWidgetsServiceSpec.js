'use strict';

describe('Show Available Widgets Service related Tests', function() {

	beforeEach(module('amdApp'));

	var localRestangular;
	var localShowAvailableWidgetsService;
	var deferred = null;

	var injectRestangular = inject(function(Restangular, $q) {
		localRestangular = Restangular;

		//set mocks
		deferred = $q.defer();
		spyOn(Restangular, 'all').andReturn({
			getList : function() {
				return deferred.promise;
			}
		});
	});

	var injectShowAvailableWidgetsService = inject(function(ShowAvailableWidgetsService) {
		localShowAvailableWidgetsService = ShowAvailableWidgetsService;
	});

	describe('dependancy injection tests', function() {

		beforeEach(function() {
			injectRestangular();
			injectShowAvailableWidgetsService();
		});

		it('should inject dependencies', function() {
			expect(localRestangular).toBeDefined();
			expect(localShowAvailableWidgetsService).toBeDefined();
		});

	});

	describe('should fetch widget list for widget palette from fn "getWidgetsByCategory"', function() {
		beforeEach(function() {
			injectRestangular();
			injectShowAvailableWidgetsService();
		});

		it('fn call for "getWidgetsByCategory"', inject(function($rootScope) {
			var aPromise = localShowAvailableWidgetsService.getWidgetsByCategory();
			expect(localRestangular.all(jasmine.any(Object)).getList()).toBe(deferred.promise);
		}));

	});

}); 