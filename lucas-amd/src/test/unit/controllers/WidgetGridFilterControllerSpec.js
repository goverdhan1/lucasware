'use strict';

describe('Widget Grid Filter Controller Specs', function() {
	
	//Global Vars
	var localController = null;
	var localScope = null;
	
	//Mock translateProvide
	beforeEach(module('amdApp', function($translateProvider) {
	    $translateProvider.translations('en', {
	        "language-code": "EN"
	    }, 'fr', {
	        "language-code": "fr"
	    }, 'de', {
	        "language-code": "de"
	    }).preferredLanguage('en');
	
	    $translateProvider.useLoader('LocaleLoader');
	}));
	
	//Inject dependencies
	beforeEach(inject(function($rootScope, $controller) {
		//mock gridOptions object as this is set in parent scope
		localScope = $rootScope.$new();
		localScope.gridOptions = {}; 

		//inject dependencies into controller
		localController = $controller('WidgetGridFilterController', {
			$scope : localScope
		});

        localScope.$digest();
	}));
	
	//Dependency Injection Tests
	describe('Dependency injection tests', function() {

        it('should inject $scope dependency', function() {
			expect(localScope).toBeDefined();
		});
		
		it('should inject controller dependency', function() {
			expect(localController).toBeDefined();
		});
	});
	
	//Filter tests
	describe('Filter input and visibility tests', function() {

		it('should return the status of filter visibility', function() {
			var result = localScope.isFilterVisible();
			
			expect(result).toBeFalsy(); //filters hidden by default
		});
		
		it('should show the filter input text fields', function() {
			localScope.showFilters();
			var result = localScope.isFilterVisible();
			
			expect(result).toBeTruthy();
		});
		
		it('should hide the filter input text fields', function() {
			localScope.hideFilters();
			var result = localScope.isFilterVisible();
			
			expect(result).toBeFalsy();
		});
	});
});