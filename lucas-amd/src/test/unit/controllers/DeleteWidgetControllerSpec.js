'use strict';

describe('Delete Widget Controller Specs', function() {

	//Global vars
	var localScope;
	var localDeleteWidgetController;
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
		localDeleteWidgetController = $controller("DeleteWidgetController", {
			$scope : localScope,
			$modalInstance : localModalInstance
		});
		localDeferred = $q;
	}));

	describe('Delete Controller Injection Specs', function() {
		it('should inject $scope', function() {
			expect(localScope).toBeDefined();
		});
		it('should inject $controller', function() {
			expect(localDeleteWidgetController).toBeDefined();
		});
		it('should inject LocalStoreService', function() {
			expect(localLocalStoreService).toBeDefined();
		});
		it('should inject WidgetService', function() {
			expect(localWidgetService).toBeDefined();
		});
	});

	describe('okbtn to delete widget', function() {
		beforeEach(function() {
			spyOn(localWidgetService, 'deleteWidgetInstance').andCallFake(function() {//replace with a fake function
				var deferred = localDeferred.defer();
				deferred.resolve({
					"clientId" : 100
				});
				return deferred.promise;
			});
			localScope.okBtnHandler(mockWidgetInstance);
		});
		it('should call localWidgetService.deleteWidgetInstance', function() {
			expect(localScope.okBtnHandler).toBeDefined();
			expect(localWidgetService.deleteWidgetInstance).toHaveBeenCalled();
		});

	});
	describe('cancelBtnHandler to cancel deleting a widget', function() {
		beforeEach(inject(function($modal, $controller) {
			localModalInstance = $modal.open({
				templateUrl : 'views/modals/delete-widget.html',
                                backdrop: 'static'
			});
			localDeleteWidgetController = $controller('DeleteWidgetController', {
				$scope : localScope,
				$modalInstance : localModalInstance
			});

			spyOn(localModalInstance, 'close').andReturn();
		}));
		it('should be defined in scope', function() {
			expect(localScope.cancelBtnHandler).toBeDefined();
		});

		it('should close the modal', function() {
			localScope.cancelBtnHandler();
			expect(localModalInstance.close).toHaveBeenCalled();
		});
	});
});
