/**
 * Created by TLester on 06/11/2014.
 */
describe('Lucas Multi Edit Directive Unit tests', function() {
    var $compile = null;
    var $scope = null;
    var element = null;

    // Load the amdApp module, which contains the directive
    beforeEach(module('amdApp', function (_$translateProvider_) {
        _$translateProvider_.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        _$translateProvider_.useLoader('LocaleLoader');
    }));

    describe('Dependency Injection specs', function() {
        // The injector unwraps the underscores (_) from around the parameter names when matching
        beforeEach(inject(function(_$compile_, _$rootScope_){
            $compile = _$compile_;
            $scope = _$rootScope_.$new();

            //create the directive
            element = $compile("<lucas-multi-edit></lucas-multi-edit>")($scope);
            $scope.$digest();
        }));

        it('should inject test dependencies', function() {
            expect($scope).toBeDefined();
            expect($compile).toBeDefined();
            expect(element).toBeDefined();
        });
    });

    describe('Exiting multi-edit mode specs', function() {
        beforeEach(inject(function(_$compile_, _$rootScope_){
            $compile = _$compile_;
            $scope = _$rootScope_.$new();
            $scope.isEnabled = false;
        }));

        it('should remove disabled attribute from form controls', function() {
            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled">' +
                       '    <input disabled="disabled" />' +
                       '    <button disabled="disabled" />' +
                       '    <select disabled="disabled" />' +
                       '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should not remove the elements, only the disabled attribute
            expect(element.find('input:disabled').length).toBe(0);
            expect(element.find('input:enabled').length).toBe(1);

            expect(element.find('button:disabled').length).toBe(0);
            expect(element.find('button:enabled').length).toBe(1);

            expect(element.find('select:disabled').length).toBe(0);
            expect(element.find('select:enabled').length).toBe(1);
        });

        it('should remove display-grey CSS class from form controls', function() {
            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled">' +
                       '    <input class="display-grey" />' +
                       '    <select class="display-grey" />' +
                       '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should not remove the elements, only the display-grey CSS class
            expect(element.find('input').length).toBe(1);
            expect(element.find('input').hasClass('display-grey')).toBeFalsy();

            expect(element.find('select').length).toBe(1);
            expect(element.find('select').hasClass('display-grey')).toBeFalsy();
        });

        it('should remove any \'force update\' checkboxes', function() {
            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled">' +
                       '    <span>' +
                       '        <input type="checkbox" name="force_update" />' +
                       '    </span>' +
                       '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should remove the checkbox, and parent span element
            expect(element.find('span').length).toBe(0);
            expect(element.find('input[type="checkbox"][name="force_update"]').length).toBe(0);
        });
    });

    describe('Entering multi-edit mode specs', function() {
        beforeEach(inject(function(_$compile_, _$rootScope_){
            $compile = _$compile_;
            $scope = _$rootScope_.$new();
        }));

        it('should disable form controls and clear ng-model when field is not sent with multi edit', function() {
            //setup scope assignments
            $scope.isEnabled = true;
            $scope.defaultFields = {
                "plainTextPassword" : {
                    "value" : "secret"
                }
            };
            $scope.multiEditFields = {};

            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled" default-fields="defaultFields" multi-edit-fields="multiEditFields">' +
                       '    <div id="frmGrp_createEditUser_plainTextPassword">' +
                       '        <input type="text" />' +
                       '        <button type="button" />' +
                       '        <select />' +
                       '    </div>' +
                       '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should disable controls for plainTextPassword
            expect(element.find('#frmGrp_createEditUser_plainTextPassword')
                .find('input:disabled, button:disabled, select:disabled').length)
                .toEqual(3);

            //should clear the ng-model
            expect($scope.defaultFields.plainTextPassword.value).toEqual('');
        });

        it('should apply \'display-grey\' CSS class when partial flag is true', function() {
            //setup scope assignments
            $scope.isEnabled = true;
            $scope.defaultFields = {
                "firstName" : {
                    "value" : ""
                }
            };
            $scope.multiEditFields = {
                "firstName" : {
                    "mutablePair" : {
                        "value": "Jack",
                        "partial": "true"
                    }
                }
            };

            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled" default-fields="defaultFields" multi-edit-fields="multiEditFields">' +
                       '    <div id="frmGrp_createEditUser_firstName">' +
                       '        <input type="text" />' +
                       '        <select />' +
                       '    </div>' +
                       '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should apply display-grey CSS class to form controls
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input, select').hasClass('display-grey')).toBeTruthy();

            //form controls should NOT be disabled
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input:disabled, select:disabled').length).toEqual(0);

            //should set the ng-model with value form multiEditField
            expect($scope.defaultFields.firstName.value).toEqual('Jack');
        });

        it('should display the form controls normally when partial flag is false', function() {
            //setup scope assignments
            $scope.isEnabled = true;
            $scope.defaultFields = {
                "firstName" : {
                    "value" : ""
                }
            };
            $scope.multiEditFields = {
                "firstName" : {
                    "mutablePair" : {
                        "value": "Jack",
                        "partial": "false"
                    }
                }
            };

            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled" default-fields="defaultFields" multi-edit-fields="multiEditFields">' +
                '    <div id="frmGrp_createEditUser_firstName">' +
                '        <input type="text" />' +
                '        <select />' +
                '    </div>' +
                '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should NOT apply display-grey CSS class to form controls
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input, select').hasClass('display-grey')).toBeFalsy();

            //form controls should NOT be disabled
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input:disabled, select:disabled').length).toEqual(0);

            //should set the ng-model with value form multiEditField
            expect($scope.defaultFields.firstName.value).toEqual('Jack');
        });

        it('should append a \'force update\' checkbox to form control', function() {
            //setup scope assignments
            $scope.isEnabled = true;
            $scope.defaultFields = {
                "firstName" : {
                    "value" : ""
                }
            };
            $scope.multiEditFields = {
                "firstName" : {
                    "mutablePair" : {
                        "value": "Jack",
                        "partial": "false"
                    }
                }
            };

            //HTML template
            var html = '<lucas-multi-edit is-enabled="isEnabled" default-fields="defaultFields" multi-edit-fields="multiEditFields">' +
                       '    <div id="frmGrp_createEditUser_firstName">' +
                       '        <input type="text" />' +
                       '    </div>' +
                       '</lucas-multi-edit>';

            //compile the directive
            element = $compile(html)($scope);
            $scope.$digest();

            //should NOT apply display-grey CSS class to form controls
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input').hasClass('display-grey')).toBeFalsy();

            //form controls should NOT be disabled
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input:disabled').length).toEqual(0);

            //should have appended a checkbox inside a parent span element
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('span').length).toEqual(1);
            expect(element.find('#frmGrp_createEditUser_firstName')
                .find('input[type="checkbox"][name="force_update"]').length).toEqual(1);
        });
    });
});