/**
 * Created by TLester on 01/12/2014.
 */
'use strict';

var gridTestUtils = require( '../../resources/gridTestUtils.js');

// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('AMD Specs', function() {

    /*
     * Common helper functions containing boilerplate code
     */

    //clears the browser LocalStorage
    var clearLS = function() {
        browser.executeScript('window.localStorage.clear();');
    };

    //Navigate to entry page (user login)
    var goHome = function() {
        browser.get('');
        clearLS();
    };

    //log into the application
    var authenticate = function(user, password) {
        goHome();
        element(by.model('username')).sendKeys(user);
        element(by.model('password')).sendKeys(password);
        element(by.css('.loginBtn')).click();
    };

    //logout the user
    var logout = function() {
        element(by.id('btn-logout')).click();
        element(by.id('btn-logout-confirmation')).click();
    };

    //Load a widget
    var loadWidgetById = function(widgetId) {
        element(by.id('load-widget')).element(by.css('a')).click();
        element(by.css('a[id="' + widgetId + '"]')).click();
    };


    /*
     * Application localization tests
     */
    describe('Localization Tests', function() {

        beforeEach(function() {
            //navigate to root context
            goHome();
        });

        afterEach(function() {
            //clear LocalStorage
            clearLS();
        });

        it('should show available languages when Change Language dropdown in opened', function() {
            //ensure dropdown is not already expanded
            expect(element(by.css('.localeContainer button[aria-expanded="false"]')).isPresent()).toBeTruthy();

            //Click the dropdown to expand it
            element.all(by.css('.localeContainer button[type="button"]')).get(1).click();
            expect(element(by.css('.localeContainer button[aria-expanded="true"]')).isPresent()).toBeTruthy();
        });

        it('should show English content', function() {
            element.all(by.css('.localeContainer button[type="button"]')).get(1).click();
            element.all(by.css('.localeContainer .dropdown-menu li a')).get(1).click();

            expect(element(by.css('.loginBtn')).getText()).toEqual('Sign in');
        });

        it('should show German content', function() {
            element.all(by.css('.localeContainer button[type="button"]')).get(1).click();
            element.all(by.css('.localeContainer .dropdown-menu li a')).get(0).click();

            expect(element(by.css('.loginBtn')).getText()).toEqual('Anmelden');
        });

        it('should show French content', function() {
            element.all(by.css('.localeContainer button[type="button"]')).get(1).click();
            element.all(by.css('.localeContainer .dropdown-menu li a')).get(2).click();

            expect(element(by.css('.loginBtn')).getText()).toEqual('Connectez-vous');
        });
    });


    /*
     * Authentication login test
     */
    describe('Authentication tests', function() {
        beforeEach(function() {
            //navigate to root context
            goHome();
        });

        it('should allow login for valid users', function() {
            authenticate('jack123', 'secret');
            expect(element(by.css('.welcomeText')).getText()).toEqual('Welcome Jack');
        });

        it('should not allow login for invalid users', function() {
            authenticate('joebloggs', 'password');
            expect(element(by.css('.notification-panel')).getText()).toContain('Invalid Username or Password');
        });

        it('should force login when accessing a protected resource', function() {
            browser.get('/amd/app/index.html#/canvases/5');
            expect(element(by.css('.loginModal')).isPresent()).toBeTruthy();
        });

        it('should allow login to access a protected resource', function() {
            //navigate to protected resource
            browser.get('/amd/app/index.html#/canvases/5');
            expect(element(by.css('.loginModal')).isPresent()).toBeTruthy();

            //sign in
            element(by.model('form.username')).sendKeys('jack123');
            element(by.model('form.password')).sendKeys('secret');
            element(by.buttonText('Sign in')).click();

            //expect canvas page to be displayed
            expect(element(by.css('.welcomeText')).getText()).toEqual('Welcome Jack');
        });
    });


    /*
     * Logout interaction tests
     */
    describe('Logout interation tests', function() {

        var setupDone = false;
        beforeEach(function() {
            if(!setupDone) {
                //Navigate to root context and authenticate user
                goHome();
                authenticate('jack123', 'secret');
                setupDone = true;
            }
        });

        it('should not logout the user when pressing Cancel to logout confirmation', function() {
            element(by.id('btn-logout')).click();
            element(by.buttonText('Cancel')).click();

            //Jack should still be logged in
            expect(element(by.css('.welcomeText')).getText()).toEqual('Welcome Jack');
        });

        it('should logout the user when pressing OK to logout confirmation', function() {
            element(by.id('btn-logout')).click();
            element(by.buttonText('OK')).click();

            //Jack should be logged out
            expect(element(by.model('username')).isPresent()).toBeTruthy();
            expect(element(by.model('password')).isPresent()).toBeTruthy();
            expect(element(by.css('.loginBtn')).isPresent()).toBeTruthy();
        });
    });


    /*
     * General Canvas Interactions tests
     */
    describe('Canvas Page tests', function() {

        var setupDone = false;
        beforeEach(function() {
            if(!setupDone) {
                //Navigate to root context and authenticate user
                goHome();
                authenticate('jack123', 'secret');
                setupDone = true;
            }
        });

        it('should load favourite canvases', function() {
            var canvasList = element.all(by.repeater('canvasBarItem in canvasBar'));

            expect(canvasList.count()).toEqual(2);
            expect(canvasList.get(0).getText()).toContain('Pick Monitoring');
            expect(canvasList.get(1).getText()).toContain('User Management');
        });

        it('should be possible to switch between canvases', function() {
            //click on second canvas in Canvas Bar
            element.all(by.repeater('canvasBarItem in canvasBar')).get(1).element(by.css('a')).click();

            //The active canvas should now be the second element
            expect(element.all(by.repeater('canvasBarItem in canvasBar')).get(0).getAttribute('class')).not.toContain('active');
            expect(element.all(by.repeater('canvasBarItem in canvasBar')).get(1).getAttribute('class')).toContain('active');
        });

        it('should be possible to cancel adding new canvas', function() {
            //Click the 'add canvas' button in canvas footer to display canvas modal
            element(by.id('add-canvas-btn')).click();
            expect(element(by.id('select-canvas-modal')).isPresent()).toBeTruthy();

            //Press the Close button
            element(by.buttonText('Close')).click();
            expect(element(by.id('select-canvas-modal')).isPresent()).toBeFalsy();
        });

        it('should add a new canvas', function() {
            //Click the 'add canvas' button in canvas footer to display canvas modal
            element(by.id('add-canvas-btn')).click();
            expect(element(by.id('select-canvas-modal')).isPresent()).toBeTruthy();

            //Select the Assignment Management canvas
            element.all(by.repeater('canvas in prop.canvasList')).get(1).element(by.css('a')).click();

            var canvasList = element.all(by.repeater('canvasBarItem in canvasBar'));

            //Modal should have closed and canvas added to canvas bar
            expect(element(by.id('select-canvas-modal')).isPresent()).toBeFalsy();

            expect(canvasList.count()).toEqual(3);
            expect(canvasList.get(0).getText()).toContain('Pick Monitoring');
            expect(canvasList.get(1).getText()).toContain('User Management');
            expect(canvasList.get(2).getText()).toContain('Assignment Management');
        });

        it('should not close canvas when pressing Cancel in confirmation dialog', function() {
            //click 'Close' button on Canvas and expect confirmation
            element(by.id('close-canvas')).element(by.css('a')).click();
            expect(element(by.css('.closeCanvasModal')).isPresent()).toBeTruthy();

            //press Cancel button
            element(by.buttonText('Cancel')).click();

            var canvasList = element.all(by.repeater('canvasBarItem in canvasBar'));

            //Confirmation modal should close and Canvas should not be removed
            expect(element(by.css('.closeCanvasModal')).isPresent()).toBeFalsy();

            expect(canvasList.count()).toEqual(3);
            expect(canvasList.get(0).getText()).toContain('Pick Monitoring');
            expect(canvasList.get(1).getText()).toContain('User Management');
            expect(canvasList.get(2).getText()).toContain('Assignment Management');
        });

        it('should close canvas when pressing Yes in confirmation dialog', function() {
            //click 'Close' button on Canvas and expect confirmation
            element(by.id('closeCanvas_'+canvasBar.canvasId)).click();
            expect(element(by.css('.closeCanvasModal')).isPresent()).toBeTruthy();

            //press Cancel button
            element(by.buttonText('Yes')).click();

            var canvasList = element.all(by.repeater('canvasBarItem in canvasBar'));

            //Confirmation modal should close and Canvas should not be removed
            expect(element(by.css('.closeCanvasModal')).isPresent()).toBeFalsy();

            expect(canvasList.count()).toEqual(2);
            expect(canvasList.get(0).getText()).toContain('Pick Monitoring');
            expect(canvasList.get(1).getText()).toContain('User Management');
        });
    });


    /*
     * General Widget Interaction tests
     */
    describe('General Widget Interaction tests', function() {

        var setupDone = false;
        beforeEach(function() {
            if(!setupDone) {
                //Navigate to root context and authenticate user
                goHome();
                authenticate('jack123', 'secret');
                setupDone = true;
            }
        });

        it('should show the widget pallet', function() {
            element(by.id('load-widget')).element(by.css('a')).click();
            expect(element(by.css('.showAvailableWidgetsModal')).isPresent()).toBeTruthy();
        });

        it('should close the widget pallet when Cancel button is pressed', function() {
            element(by.buttonText('Cancel')).click();
            expect(element(by.css('.showAvailableWidgetsModal')).isPresent()).toBeFalsy();
        });

        it('should load a widget', function() {
            //Open widget pallet and load widget
            element(by.id('load-widget')).element(by.css('a')).click();
            element.all(by.repeater('widget in prop')).get(1).element(by.css('a')).click();

            //expect widget to be present
            expect(element(by.id('wid100')).isPresent()).toBeTruthy();
        });

        it('should not delete the widget when pressing Cancel on confirmation', function() {
            //click 'X' (delete) button, and confirm OK to deleting widget
            element(by.id('wid100-remove-widget-btn')).click();
            element(by.buttonText('Cancel')).click();

            //expect widget to still be on canvas
            expect(element(by.id('wid100')).element(by.css('.widget-panel')).isPresent()).toBeTruthy();
        });


        it('should delete widget when pressing OK on confirmation', function() {
            //click 'X' (delete) button, and confirm OK to deleting widget
            element(by.id('wid100-remove-widget-btn')).click();
            element(by.buttonText('OK')).click();

            //expect widget to no longer be present
            expect(element(by.id('wid100')).element(by.css('.widget-panel')).isPresent()).toBeFalsy();
        });
    });


    /*
     * User Permission tests for loading a widget
     */
    describe('User Permission specific tests', function() {
        beforeEach(function() {
            //Navigate to root context
            goHome();
        });

        afterEach(function() {
           logout();
        });

        it('should not be possible for Jill to load a widget', function() {
            authenticate('jill123', 'secret');
            expect(element(by.id('load-widget')).isPresent()).toBeFalsy();
        });

        it('should be possible for Jack to load a widget', function() {
            authenticate('jack123', 'secret');

            //Load Widget button should be present
            expect(element(by.id('load-widget')).isPresent()).toBeTruthy();

            //now load a widget
            loadWidgetById(10);
            expect(element(by.id('wid100')).element(by.css('.widget-panel')).isPresent()).toBeTruthy();
        });
    });


    /*
     * Pick Line By Wave Bar Chart Widget tests
     */
    describe('Pick Line by Wave Bar Chart widget tests', function() {

        var setupDone = false;
        beforeEach(function() {
            if(!setupDone) {
                //Navigate to root context, authenticate user, and load up the
                //Pick Line by Wave Bar Chart widget
                goHome();
                authenticate('jack123', 'secret');
                loadWidgetById(10);
                setupDone = true;
            }
        });

        it('should load the Pick Line by Wave bar chart widget', function() {
            expect(element(by.id('wid100')).element(by.css('.widget-panel')).isPresent()).toBeTruthy();
        });


        it('should load widget with default horizontal chart orientation', function() {
            expect(element(by.id('horizontalChart100')).isPresent()).toBeTruthy();
            expect(element(by.id('verticalChart100')).isPresent()).toBeFalsy();
        });

        it('should display orientation options in cog menu', function() {
            //open cog menu modal
            element(by.css('.panel-heading .glyphicon-cog')).click();
            expect(element(by.id('modal-pickByWaveChart')).isPresent()).toBeTruthy();

            //check chart orientations
            var orientations = element.all(by.repeater('orientation in cogWidgetInstance.actualViewConfig.orientation.option'));
            expect(orientations.count()).toEqual(2);
            expect(orientations.get(0).getText()).toEqual('Horizontal');
            expect(orientations.get(1).getText()).toEqual('Vertical');
        });

        it('should render vertical chart orientation when changed in cog menu', function() {
            //select Vertical radio button
            element.all(by.model('cogWidgetInstance.actualViewConfig.orientation.selected')).get(1).click();

            //now save the changes and asset chart orientation has changed
            element(by.buttonText('Save')).click();
            expect(element(by.id('verticalChart100')).isPresent()).toBeTruthy();
            expect(element(by.id('horizontalChart100')).isPresent()).toBeFalsy();
        });
    });


    /*
     * Create or Edit User Form Widget tests
     */
    describe('Create or Edit User Form widget tests', function() {

        var setupDone = false;
        beforeEach(function() {
            if(!setupDone) {
                //Navigate to root context, authenticate user, and load up the
                //Create or Edit User Form widget
                goHome();
                authenticate('jack123', 'secret');
                loadWidgetById(8);
                setupDone = true;
            }
        });

        it('should load the Create or Edit User form widget', function() {
            expect(element(by.id('wid100')).element(by.css('.widget-panel')).isPresent()).toBeTruthy();
        });

        it('should have two Date Picker fields', function() {
            expect(element.all(by.css('button i.glyphicon-calendar')).count()).toEqual(2);
        });

        it('should popup Date Picker for Start Date and Birth Date fields', function() {
            //date pickers not displayed
            expect(element(by.id('frmGrp_createEditUser_startDate')).element(by.css('ul')).getAttribute('style')).toContain('display: none');
            expect(element(by.id('frmGrp_createEditUser_birthDate')).element(by.css('ul')).getAttribute('style')).toContain('display: none');

            //click Start Date picker, assert it is displayed
            element.all(by.css('button i.glyphicon-calendar')).get(0).click();
            expect(element(by.id('frmGrp_createEditUser_startDate')).element(by.css('ul')).getAttribute('style')).toContain('display: block');

            //click Birth Date picker, assert it is displayed
            element.all(by.css('button i.glyphicon-calendar')).get(1).click();
            expect(element(by.id('frmGrp_createEditUser_birthDate')).element(by.css('ul')).getAttribute('style')).toContain('display: block');
        });

        it('should be possible to create a new user', function() {

            //click Create New User button
            element(by.buttonText('Create New User')).click();

            //fill out form fields
            element(by.model('defaultFields.userName.value')).sendKeys('donald123');                  //USERNAME
            element(by.model('defaultFields.plainTextPassword.value')).sendKeys('secret');            //PASSWORD
            element(by.model('defaultFields.verifyPassword.value')).sendKeys('secret');               //CONFIRM PASSWORD
            element(by.model('defaultFields.emailAddress.value')).sendKeys('donald.duck@domain.com'); //EMAIL ADDRESS
            element(by.model('defaultFields.title.value')).sendKeys('Mr');                            //TITLE
            element(by.model('defaultFields.mobileNumber.value')).sendKeys('0123456789');             //MOBILE NUMBER
            element(by.model('defaultFields.firstName.value')).sendKeys('Donald');                    //FIRST NAME
            element(by.model('defaultFields.lastName.value')).sendKeys('Duck');                       //LAST NAME
            element(by.model('defaultFields.employeeNumber.value')).sendKeys('DDUCK001');             //EMPLOYEE NUMBER
            element(by.model('defaultFields.startDate.value')).sendKeys('05-02-2000');                //START DATE (MM-DD-YYYY)
            element(by.model('defaultFields.birthDate.value')).sendKeys('11-26-1986');                //BIRTH DATE (MM-DD-YYYY)

            //click save button
            element(by.buttonText('Save')).click();

            //load the Search User widget to verify user was created
            loadWidgetById(9);

            //Filter for our user
            element(by.id('showFilterBtn')).click();
            element(by.id('filter_userName')).sendKeys('donald123');

            //fetch matching records
            element(by.id('queryBtn')).click();
            element(by.id('hideFilterBtn')).click();

            //assert user record exists
            gridTestUtils.expectRowCount('searchUserGrid_101', 1);
        });

        it('should pre-populate form with existing user details when entering username', function() {
            //first clear the form
            element(by.buttonText('Create New User')).click();

            //gain focus and enter username
            element(by.model('defaultFields.userName.value')).click(); //lose focus on Username field
            element(by.model('defaultFields.userName.value')).sendKeys('donald123');
            element(by.model('defaultFields.plainTextPassword.value')).click(); //lose focus on Username field

            //expect form to be populated with Donald's details
            expect(element(by.model('defaultFields.userName.value')).getAttribute('value')).toEqual('donald123');                  //USERNAME
            expect(element(by.model('defaultFields.plainTextPassword.value')).getAttribute('value')).toEqual('');                  //PASSWORD
            expect(element(by.model('defaultFields.verifyPassword.value')).getAttribute('value')).toEqual('');                     //CONFIRM PASSWORD
            expect(element(by.model('defaultFields.emailAddress.value')).getAttribute('value')).toEqual('donald.duck@domain.com'); //EMAIL ADDRESS
            expect(element(by.model('defaultFields.title.value')).getAttribute('value')).toEqual('Mr');                            //TITLE
            expect(element(by.model('defaultFields.mobileNumber.value')).getAttribute('value')).toEqual('0123456789');             //HOST LOGIN
            expect(element(by.model('defaultFields.firstName.value')).getAttribute('value')).toEqual('Donald');                    //FIRST NAME
            expect(element(by.model('defaultFields.lastName.value')).getAttribute('value')).toEqual('Duck');                       //LAST NAME
            expect(element(by.model('defaultFields.employeeNumber.value')).getAttribute('value')).toEqual('DDUCK001');             //EMPLOYEE NUMBER
            expect(element(by.model('defaultFields.startDate.value')).getAttribute('value')).toEqual('05-02-2000');                //START DATE (MM-DD-YYYY)
            expect(element(by.model('defaultFields.birthDate.value')).getAttribute('value')).toEqual('11-26-1986');                //BIRTH DATE (MM-DD-YYYY)*/
        });

        it('should be possible to edit existing user details', function() {
            //Change the email address value and save the user
            element(by.model('defaultFields.emailAddress.value')).clear();
            element(by.model('defaultFields.emailAddress.value')).sendKeys('donald_duck987@g-mail.com');

            // ---------------------------------------------------------------------------------------------
            //
            // NOTE: We currently have to send password with any update to get around validation constraints.
            //       This will be fixed by PHX-920. The following 2 lines will then be removed
            element(by.model('defaultFields.plainTextPassword.value')).sendKeys('secret');
            element(by.model('defaultFields.verifyPassword.value')).sendKeys('secret');
            //
            // ----------------------------------------------------------------------------------------------

            element(by.buttonText('Save')).click();

            //Expect user value to be changed in search user grid
            //Fetch new results from server
            element(by.id('showFilterBtn')).click();
            element(by.id('queryBtn')).click();
            element(by.id('hideFilterBtn')).click();

            //assert user record exists, with updated email address
            gridTestUtils.expectRowCount('searchUserGrid_101', 1); //should be 1 row
            gridTestUtils.expectCellValueMatch('searchUserGrid_101', 0, 0, 'donald_duck987@g-mail.com'); //email address should be updated
        });
    });


    /*
     * Search User grid widget Action Pallet tests
     */
    describe('Search User grid widget Action Pallet tests', function() {

        var setupDone = false;
        beforeEach(function() {
            if(!setupDone) {
                //Navigate to root context, authenticate user, and load up the
                //Create or Edit User Form widget
                goHome();
                setupDone = true;
            }
        });

        it('should enable/disable actions button on row selection', function() {
            authenticate('joe123', 'secret');
            loadWidgetById(9); //load search user grid widget

            //no rows selected - actions button disabled
            expect(element(by.buttonText('Actions')).isEnabled()).toBeFalsy();

            //select a row - actions button enabled
            gridTestUtils.clickGridRow('searchUserGrid_100', 0);
            expect(element(by.buttonText('Actions')).isEnabled()).toBeTruthy();
        });

        it('should only show \'Deactivate User\' action enabled for user Joe', function() {

            //open Actions Pallet
            element(by.buttonText('Actions')).click();

            //Action pallet opens, with both actions enabled
            expect(element(by.css('.modal-content')).isPresent()).toBeTruthy();

            var actions = element.all(by.repeater('(action, permission) in widgetdetails.widgetDefinition.widgetActionConfig'));
            expect(actions.count()).toEqual(2);

            var deactivateUser = actions.get(0);
            var deleteUser = actions.get(1);

            expect(deactivateUser.element(by.css('span.list-group-item-text')).getText()).toContain('Deactivate User(s)');
            expect(deactivateUser.element(by.css('a')).getAttribute('class')).not.toContain('link-disabled');

            expect(deleteUser.element(by.css('span.list-group-item-text')).getText()).toContain('Delete User(s)');
            expect(deleteUser.element(by.css('a')).getAttribute('class')).toContain('link-disabled');

            //logout as next test requires Jack to authenticate
            logout();
        });

        it('should show \'Delete User\' and \'Deactivate User\' actions enabled for user Jack', function() {
            authenticate('jack123', 'secret');
            loadWidgetById(9);

            //select a row, and open actions pallet
            //element.all(by.repeater('row in renderedRows')).get(0).click();
            gridTestUtils.clickGridRow('searchUserGrid_100', 0);

            element(by.buttonText('Actions')).click();

            //Action pallet opens, with both actions enabled
            expect(element(by.css('.modal-content')).isPresent()).toBeTruthy();

            var actions = element.all(by.repeater('(action, permission) in widgetdetails.widgetDefinition.widgetActionConfig'));
            expect(actions.count()).toEqual(2);

            var deactivateUser = actions.get(0);
            var deleteUser = actions.get(1);

            expect(deactivateUser.element(by.css('span.list-group-item-text')).getText()).toContain('Deactivate User(s)');
            expect(deactivateUser.element(by.css('a')).getAttribute('class')).not.toContain('link-disabled');

            expect(deleteUser.element(by.css('span.list-group-item-text')).getText()).toContain('Delete User(s)');
            expect(deleteUser.element(by.css('a')).getAttribute('class')).not.toContain('link-disabled');
        });

        it('should close the Actions modal when pressing the Close button', function() {
            element(by.buttonText('Close')).click();
            expect(element(by.css('.modal-content')).isPresent()).toBeFalsy();
        });

        it('should delete user donald', function() {
            //For this test we will delete user Donald, which is the user created
            //by the 'create user' e2e test earlier

            //First find for user Donald123
            element(by.id('showFilterBtn')).click();
            element(by.id('filter_userName')).sendKeys('donald123');
            element(by.id('queryBtn')).click();
            element(by.id('hideFilterBtn')).click();

            //Expect user to be present in grid
            gridTestUtils.expectRowCount('searchUserGrid_100', 1); //the query should return 1 user matching 'donald123'

            //select row, and open Actions Pallet
            gridTestUtils.clickGridRow('searchUserGrid_100', 0);
            element(by.buttonText('Actions')).click();

            //Click Delete User, and confirm action
            var actions = element.all(by.repeater('(action, permission) in widgetdetails.widgetDefinition.widgetActionConfig'));
            actions.get(1).element(by.css('a')).click();
            element(by.buttonText('OK')).click();

            //Assert user has been deleted - grid should be empty
            gridTestUtils.expectRowCount('searchUserGrid_100', 0);
        });
    });
});