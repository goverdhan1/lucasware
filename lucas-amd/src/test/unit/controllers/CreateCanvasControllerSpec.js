/**
 * Created by Shaik Basha on 1/23/2015.
 */

'use strict';

describe('CreateCanvasController Unit Tests', function () {

    // Global vars
    var def = null;
    var localCreateCanvasController;
    var localScope;
    var localModalInstance;

    // Global test setup
    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN",
        }, 'fr', {
            "language-code": "fr",
        }, 'de', {
            "language-code": "de",
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    beforeEach(inject(function ($rootScope, $controller, $modal, $templateCache) {
        //mock opening modal, so we can test closing it
        $templateCache.put('views/modals/widgets-pallete.html', '');
        localModalInstance = $modal;
        localScope = $rootScope.$new();

        localCreateCanvasController = $controller('CreateCanvasController', {
            $scope: localScope,
            $modalInstance: localModalInstance
        });

        spyOn(localScope, 'loadWidget').andCallThrough();

    }));

    // Inject Specs
    describe('CreateCanvasController Injection Specs', function () {
        it('should inject CreateCanvasController', function () {
            expect(localCreateCanvasController).toBeDefined();
        });

        it('should inject ModalInstance', function () {
            expect(localModalInstance).toBeDefined();
        });

        it('should inject $scope', function () {
            expect(localScope).toBeDefined();
        });
    });

    // loadWidget method
    describe('handle loadWidget method', function () {
        beforeEach(function () {
            spyOn(localModalInstance, 'open').andCallThrough();
            localScope.$digest();
        });
        it('should show CanvasModal Popup', function () {
            localScope.loadWidget();
            expect(localModalInstance.open).toHaveBeenCalled();
            expect(localScope.loadWidget).toBeDefined();
        });
    });

});