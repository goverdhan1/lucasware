'use strict';

describe('Action Confirmation Controller Specs', function() {

	//Global vars
	var scope;
	var controller;
    var modalInstance;
	
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
	
	//Global test setup
	beforeEach(inject(function(_$rootScope_, _$modal_) {
        //assign locals
        scope = _$rootScope_.$new();
        modalInstance = _$modal_.open({
            templateUrl: 'views/modals/action-confirmation.html'
        });
    }));


	//Dependency Injection tests
	describe('Dependency Injection specs', function() {
		beforeEach(inject(function(_$controller_) {

            //inject controller
			controller = _$controller_('ActionConfirmationController', {
				$scope : scope,
				$modalInstance : modalInstance
			});
		}));

        it('should inject $controller', function() {
            expect(controller).toBeDefined();
        });

        it('should inject $scope', function() {
			expect(scope).toBeDefined();
		});

        it('should inject $modalInstance', function() {
            expect(modalInstance).toBeDefined();
        });
	});

    //Process selected pallet action
    describe('Process the selected action confirmation specs', function() {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('ActionConfirmationController', {
                $scope : scope,
                $modalInstance : modalInstance
            });
        }));

        it('should $emit the selected action to the calling widget', function() {
            spyOn(scope, '$emit').andReturn();
            spyOn(modalInstance, 'close').andReturn();

            //invoke the function and assert the event was emitted
            scope.handleActionSelected('delete-user');
            scope.close();

            expect(scope.$emit).toHaveBeenCalledWith('ActionConfirmed', 'delete-user');
        });
    });

    //closing the pallet action modal
    describe('Closing the action confirmation modal spec', function() {
        beforeEach(inject(function(_$controller_) {
            //inject controller
            controller = _$controller_('ActionConfirmationController', {
                $scope : scope,
                $modalInstance : modalInstance
            });
        }));

        it('should close the action confirmation modal', function() {
            spyOn(modalInstance, 'close').andReturn();
            //invoke the function and assert the result
            scope.close();
            expect(modalInstance.close).toHaveBeenCalled();
        })
    });

});