'use strict';

describe('Reset Widget Controller Specs', function() {

	//Global vars
	var localScope;
	var localResetWidgetController;
	var localModalInstance;
	var localLocalStoreService;
	var localWidgetService;
	var mockWidgetInstance = {
		"updateWidget" : true,
		"clientId" : 100,
		"isMaximized" : false
	};
	var localDeferred;

	//Mock translateProvide
	beforeEach(module('amdApp', function($translateProvider) {
		$translateProvider.translations('en', {
			"language-code" : "EN"
		}, 'fr', {
			"language-code" : "fr"
		}, 'de', {
			"language-code" : "de"
		}).preferredLanguage('en');
		$translateProvider.useLoader('LocaleLoader');
	}));

	beforeEach(inject(function($controller, $rootScope, LocalStoreService, WidgetService, $q, $modal) {
		localScope = $rootScope.$new();
		localLocalStoreService = LocalStoreService;
		localWidgetService = WidgetService;
		localModalInstance = $modal;
		localResetWidgetController = $controller("ResetWidgetController", {
			$scope : localScope,
			$modalInstance : localModalInstance
		});
		localDeferred = $q;
		spyOn(localWidgetService, 'getWidgetInstanceObj').andReturn(mockWidgetInstance);
	}));

	describe('Reset Controller Injection Specs', function() {
		it('should inject $scope', function() {
			expect(localScope).toBeDefined();
		});
		it('should inject $controller', function() {
			expect(localResetWidgetController).toBeDefined();
		});
		it('should inject LocalStoreService', function() {
			expect(localLocalStoreService).toBeDefined();
		});
		it('should inject WidgetService', function() {
			expect(localWidgetService).toBeDefined();
		});
	});

	describe('okbtn to reset widget', function() {
		beforeEach(inject(function($modal, $controller) {
			localModalInstance = $modal.open({
				templateUrl : 'views/modals/reset-widget.html',
                                backdrop: 'static'
			});
			localResetWidgetController = $controller('ResetWidgetController', {
				$scope : localScope,
				$modalInstance : localModalInstance
			});

			spyOn(localModalInstance, 'close').andReturn();
			localScope.okBtnHandler(mockWidgetInstance);
		}));
		it('should call okbtnhandler fn', function() {
			expect(localScope.okBtnHandler).toBeDefined();
			expect(localModalInstance.close).toHaveBeenCalled();
		});
	});

	describe('cancel button to reset widget', function() {
		beforeEach(inject(function($modal, $controller) {
			localModalInstance = $modal.open({
				templateUrl : 'views/modals/reset-widget.html',
                backdrop: 'static'
			});
			localResetWidgetController = $controller('ResetWidgetController', {
				$scope : localScope,
				$modalInstance : localModalInstance
			});

			spyOn(localModalInstance, 'close').andReturn();
			localScope.cancelBtnHandler();
		}));
		it('should call cancelBtnHandler fn', function() {
			expect(localScope.cancelBtnHandler).toBeDefined();
			expect(localModalInstance.close).toHaveBeenCalled();
		});
	});
}); 