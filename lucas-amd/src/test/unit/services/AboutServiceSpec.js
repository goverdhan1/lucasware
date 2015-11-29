'use strict';

describe('About Lucasware Service related Tests', function() {

	beforeEach(module('amdApp'));

	var localRestangular;
	var localAboutService;
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

	var injectAboutService = inject(function(AboutService) {
		localAboutService = AboutService;
	});

	describe('dependancy injection tests', function() {

		beforeEach(function() {
			injectRestangular();
			injectAboutService();
		});

		it('should inject dependencies', function() {
			expect(localRestangular).toBeDefined();
			expect(localAboutService).toBeDefined();
		});

	});

	describe('fetch about details from api in fn "getAboutDetails"', function() {
		beforeEach(function() {
			injectRestangular();
			injectAboutService();
		});

		it('should list "about information"', inject(function($rootScope) {
			var aPromise = localAboutService.getAboutDetails();
			expect(localRestangular.all(jasmine.any(Object)).getList()).toBe(deferred.promise);
		}));

	});

});
