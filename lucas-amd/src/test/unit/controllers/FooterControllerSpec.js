'use strict';
describe('Footer Controller Tests', function() {

    // Global vars
    var localFooterController = null;
    var localScope = null;
    var localModal = null;

    beforeEach(module('amdApp'));

    beforeEach(inject(function($rootScope, $controller, $modal) {
        localScope = $rootScope.$new();
        localModal = $modal;
        localFooterController = $controller('FooterController', {
            $scope: localScope
        });
    }));

    // Modal Specs
    describe('About Footer Modal Specs', function() {
        it("should open the modal", function() {
            spyOn(localModal, 'open').andCallThrough();
            localScope.showVersion();
            expect(localModal.open).toHaveBeenCalled();
        });
    });

});