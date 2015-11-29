'use strict';

describe('Logout Modal Controller Tests', function() {
	
	var localController = null;
	var localScope = null;
	var localModalInstance = null;
	
	beforeEach(module('amdApp'));
	
	beforeEach(module('amdApp', function($translateProvider) {
        
		$translateProvider.translations('en', {
            "language-code" : "EN",
        }, 'fr', {
            "language-code" : "fr",
        }, 'de', {
            "language-code" : "de",
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));
	
	// Inject Dependencies
	beforeEach(inject(function($controller, $rootScope, $modal, $templateCache) {		
		//mock opening modal, so we can test closing it
		$templateCache.put('views/modals/logout-modal.html','');
		localModalInstance = $modal.open({
			templateUrl : 'views/modals/logout-modal.html',
                        backdrop: 'static'
		});
		localScope = $rootScope.$new();
		
		localController = $controller('LogoutModalController', {
			$scope : localScope,
			$modalInstance : localModalInstance
		});
	}));
	
	// Dependency Injection Tests
	describe('Dependency Injection Specs', function() {	
		it('should inject scope dependency', function() {
			expect(localScope).toBeDefined();
		});
		it('should inject modal instance dependency', function() {
			expect(localModalInstance).toBeDefined();
		});
		it('should inject controller dependency', function() {
			expect(localController).toBeDefined();
		});
	});
	
	// Modal Tests
	describe('Logout Modal Specs', function() {
		beforeEach(function() {
			spyOn(localModalInstance, 'close').andCallThrough();
			localScope.$digest();
		});
		it('should close the logout modal', function() {
			localScope.closeModal();
			expect(localScope.closeModal).toBeDefined();
			expect(localModalInstance.close).toHaveBeenCalled();
		});
	});
});