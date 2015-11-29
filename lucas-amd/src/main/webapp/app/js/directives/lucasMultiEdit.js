/**
 * Created by lester on 26/10/2014.
 */
'use strict';

amdApp.directive('lucasMultiEdit', ['$compile',
function(compile) {

    function link(scope, element, attrs) {

        //triggered when entering/exiting multi-edit mode
        scope.$watch('multiEditFields', function() {
            console.log('Multi-edit Mode: ' + scope.isEnabled);

            if (scope.isEnabled) {
                // for each field, check the field properties and manipulate the dom appropriately
                for (var defaultField in scope.defaultFields) {
                    console.log('-----------------------------');
                    console.log('Processing field: ' + defaultField);

                    var editField = scope.multiEditFields[defaultField];
                    console.log('properties: ' + JSON.stringify(editField));

                    //if the field is not present - disable any inputs/buttons/dropdowns
                    if (!editField) {
                        console.log('Disabling field');

                        //clear custom 'display-grey' css class
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input, select')
                            .removeClass('display-grey');

                        //disable any inputs
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input:enabled, button:enabled, select:enabled')
                            .attr('disabled', true);

                        //remove 'force-update' checkbox (if exists)
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input[type="checkbox"][name="force_update"]')
                            .parent('span').remove();

                        //clear text in model binding
                        scope.defaultFields[defaultField].value = '';

                        continue;
                    }

                    //display field in grey
                    if (editField.mutablePair.partial === 'true') {
                        console.log('Displaying field in GREY, with value [' + editField.mutablePair.value + ']');

                        //apply 'display-grey' css class to inputs
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input, select')
                            .addClass('display-grey');

                        //enable and disabled inputs/buttons/dropdowns
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input:disabled, button:disabled, select:disabled')
                            .removeAttr('disabled');

                        //set text in model binding
                        scope.defaultFields[defaultField].value = editField.mutablePair.value;

                    }
                    //display the field normally
                    else if (editField.mutablePair.partial === 'false') {
                        console.log('Displaying field NORMALLY, with value [' + editField.mutablePair.value + ']');

                        //clear custom 'display-grey' css class
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input, select')
                            .removeClass('display-grey');

                        //enable any disabled inputs/buttons/dropdowns
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input:disabled, button:disabled, select:disabled')
                            .removeAttr('disabled');

                        //set text in model binding
                        scope.defaultFields[defaultField].value = editField.mutablePair.value;
                    }

                    //logic to append 'force-update' checkbox to input field
                    // 1. First, make sure 'force-update' checkbox does not already exist from previous broadcast
                    // 2. Then append the checkbox to the input field
                    var checker = element.find('#frmGrp_createEditUser_' + defaultField)
                        .find('input[type="checkbox"][name="force_update"]');

                    //if no checkbox found - add one!
                    if (!checker || checker.length === 0) {
                        //define checkbox element to append when required
                        var checkbox = angular.element('<span><input type="checkbox"' +
                            ' name="force_update"' +
                            ' ng-model="defaultFields.' + defaultField + '.forceUpdate"' +
                            ' ng-true-value="true"' +
                            ' ng-false-value="false">' +
                            // have to use filter here as translate directive does not work
                            ' {{ \'core.forceUpdate\' | translate}}' +
                            ' </span>');

                        //append checkbox to inputs and dropdowns
                        element.find('#frmGrp_createEditUser_' + defaultField)
                            .find('input[type="text"], input[type="email"], select')
                            .after(checkbox);

                        compile(checkbox)(scope);
                    }
                }
            }
            else {
                //reset the form
                // 1. make sure none of the inputs/buttons are disabled
                element.find('input:disabled, button:disabled, select:disabled')
                    .removeAttr('disabled');
                // 2. remove the 'display-grey' css class from inputs
                element.find('input, select')
                    .removeClass('display-grey');
                // 3. remove all Force Update text boxes
                element.find('input[type="checkbox"][name="force_update"]')
                    .parent('span').remove();
            }
        });
    }

    return {
        restrict: 'E',
        link: link,
        scope : {
            isEnabled : "=",
            multiEditFields : "=",
            defaultFields : "="
        }
    };
}]);