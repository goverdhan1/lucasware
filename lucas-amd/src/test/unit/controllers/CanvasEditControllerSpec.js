'use strict';

describe('CanvasEdit Controller Tests', function() {

    var localScope = null;
    var localCanvasEditController = null;
    var localState = null;
    var localStateParams = null;


    beforeEach(module('amdApp'));

    beforeEach(inject(function($rootScope, $controller, $state, $stateParams) {
        localScope = $rootScope.$new();
        localState = $state;

        //spyOn(localState, 'go').andCallThrough();

        localCanvasEditController = $controller('CanvasEditController', {
            $scope : localScope,
            $state : localState
        });

        spyOn(localState, 'go').andCallThrough();

    }));

    describe('CanvasEditController injection tests', function() {
        it('should have CanvasEdit controller defined', function() {
            expect(localCanvasEditController).toBeDefined();
            expect(localState).toBeDefined();
            //spyOn(localState, 'go').andCallThrough();
            //expect(localState.go).toHaveBeenCalled();

        });
    });


});