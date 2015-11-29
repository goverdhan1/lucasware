'use strict';

var gridTestUtils = require( '../../resources/gridTestUtils.js');

// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('develop protractor specs for phx-661, phx-662 - widgets broadcast and listening', function() {

	describe('communication between pickline chart, create user form, search user grid', function() {
		it('should login', function() {
			browser.get('');
			element(by.model('username')).sendKeys('jack123');
			element(by.model('password')).sendKeys('secret');
			element(by.css('.loginBtn')).click();
		});
		it('load pickline by wave chart widget and ensure listensforList and broadcastMap is present', function() {
			//in pickline chart widget
			element(by.css('#load-widget a')).click();
			element(by.linkText('Pickline By Wave Bar chart')).click();
			//ensure listensForlist and broadcast is present in pickline
			expect(element(by.css('#wid100 .panel-heading')).getText()).toBe("pickline-by-wave-barchart-widget, 100");

			//take username as a value in listensForList and test against that.
			expect(element(by.css('#wid100 .panel-body p[listen-info="widgetdetails.widgetViewConfig.listensForList"] .label-success')).getText()).toContain("userName");
			//take wave as a value in broadcastmap and test against that.
			expect(element(by.css('#wid100 .panel-body p[sendinfo="widgetdetails.widgetDefinition"] .label-info')).getText()).toContain("Wave");
		});
		it('load create user form widget and ensure listensforList and broadcastMap is present', function() {
			//in create user form widget
			element(by.css('#load-widget a')).click();
			element(by.linkText('Create Or Edit User')).click();
			//ensure listensForlist and broadcast is present in create edit user form
			expect(element(by.css('#wid101 .panel-heading')).getText()).toBe("create-or-edit-user-form-widget, 101");
			//take username as a value in listensForList and test against that.
			expect(element(by.css('#wid101 .panel-body .label-success')).getText()).toContain("Wave");
			//take wave as a value in broadcastmap and test against that.
			expect(element(by.css('#wid101 .panel-body .label-info')).getText()).toContain("userName");
		});
		it('should click on issue broadcast in create user form and ensure it is broadcasted to grid and chart widget', function() {
			element(by.css('#wid101 button[data-ng-click="handleBroadcast($event)"]')).click();
			//ensure broadcasted info from create user form is received in pickline chart
			expect(element(by.css('#wid100 .label-danger')).getText()).toContain("userName");
			expect(element(by.css('#wid100 .label-danger')).getText()).toContain("create-or-edit-user-form-widget");
			expect(element(by.css('#wid101 .label-danger')).getText()).toContain("create-or-edit-user-form-widget");
		});
		it('load search user grid widget and ensure listensforList and broadcastMap is present', function() {
			element(by.css('#load-widget a')).click();
			element(by.linkText('Search User')).click();
			//ensure listensForlist and broadcast is present in create edit user form
			expect(element(by.css('#wid102 .panel-heading')).getText()).toBe("search-user-grid-widget, 102");
			//take score as a value in listensForList and test against that.
			expect(element(by.css('#wid102 .panel-body .label-success')).getText()).toContain("Score");
			//take userName as a value in broadcastmap and test against that.
			expect(element(by.css('#wid102 .panel-body .label-info')).getText()).toContain("userName");
		});
		it('should click an area in pickline chart and ensure it is broadcasted to form and grid widget.', function() {
			element.all(by.css('.nv-bar.positive')).get(0).click();
			//ensure broadcasted info from pickline chart is received in create user form
			expect(element(by.css('#wid101 .label-danger')).getText()).toContain("Wave");
			expect(element(by.css('#wid101 .label-danger')).getText()).toContain("pickline-by-wave-barchart-widget");
			//ensure broadcasted info from pickline chart is received in search user grid
			expect(element(by.css('#wid102 .label-danger')).getText()).toContain("Score");
			expect(element(by.css('#wid101 .label-danger')).getText()).toContain("pickline-by-wave-barchart-widget");
		});
		it('should click one or more rows in search user grid widget and ensure it is broadcast to chart and form widget', function() {
			//click grid row
            gridTestUtils.clickGridRow('searchUserGrid_102', 0);

			//ensure broadcasted info from search user grid is received in pickline chart
			expect(element(by.css('#wid100 .label-danger')).getText()).toContain("userName");
			expect(element(by.css('#wid100 .label-danger')).getText()).toContain("search-user-grid-widget");

			//ensure broadcasted info from search user grid is received in create user form
			expect(element(by.css('#wid101 .label-danger')).getText()).toContain("userName");
			expect(element(by.css('#wid101 .label-danger')).getText()).toContain("search-user-grid-widget");
		});
	});
	//phx-732, phx-733 - saving and deleting widget, canvas with widgets on clicking logout/close
	describe('save create edit user form widget and ensure it shows up when a user clicks close or logout', function() {
		it('should login', function() {
			browser.get('');
			element(by.model('username')).sendKeys('jack123');
			element(by.model('password')).sendKeys('secret');
			element(by.css('.loginBtn')).click();
		});
		it('able to click close canvas and ensure widget is available to save', function() {
			element(by.css('#load-widget a')).click();
			element(by.linkText('Create Or Edit User')).click();
			element(by.css('#close-canvas a')).click();
			//ensure one widget is available to save
			expect(element(by.css('.closeCanvasModal .panel-heading')).getText()).toContain("1 Save Canvas");
			//click on the accordion panel heading
			element(by.css('.closeCanvasModal .panel-heading a')).click();
			//ensure accordion panel body is opened
			expect(element(by.css('.closeCanvasModal .panel-collapse')).getAttribute('class')).toContain("in");
		});
		it('should check the modified widget is under updated widgets category', function() {
			expect(element(by.css('.closeCanvasModal .panel-collapse .panel-body h4')).getText()).toBe("Updated widgets");
			expect(element(by.css('.closeCanvasModal .panel-collapse .panel-body p button')).getText()).toBe("Save Widget");
			expect(element(by.css('.closeCanvasModal .panel-collapse .panel-body p')).getText()).toContain("create-or-edit-user-form-widget, 100");
		});
		it('should ensure clicking save widget gives message widget is saved successfully.', function() {
			element(by.css('.closeCanvasModal .panel-collapse .panel-body p button')).click();
			expect(element(by.css('.notification-panel p strong')).getText()).toContain("Widget is saved successfully.");
			element(by.css('.notification-panel .close')).click();
		});
		it('should ensure clicking save canvas gives message canvas is saved successfully', function() {
			element(by.css('.closeCanvasModal .panel-heading button.pull-right')).click();
			expect(element(by.css('.notification-panel p strong')).getText()).toContain("Canvas is saved successfully.");
			element(by.css('.notification-panel .close')).click();
		});
		it('when you go to other canvas, closing that canvas should not show this widget to save', function() {
			//close the canvas modal
			element(by.css('.closeCanvasModal .modal-footer .btn-warning')).click();
			//go to other canvas
			element(by.css('.footerNav li:last-child a')).click();
			element(by.css('#close-canvas a')).click();
			//ensure no widget is available to save
			expect(element(by.css('.closeCanvasModal accordion .panel-heading')).isPresent()).toBe(false);
			element(by.css('.closeCanvasModal .modal-footer .btn-warning')).click();
		});
		it('should click on logout and ensure widget is available to save.', function() {
			element(by.css('#btn-logout')).click();
			//ensure one widget is available to save
			expect(element(by.css('#logoutConfirmation .panel-heading')).getText()).toContain("1 Save Canvas");
			//click on the accordion panel heading
			element(by.css('#logoutConfirmation .panel-heading a')).click();
			//ensure accordion panel body is opened
			expect(element(by.css('#logoutConfirmation .panel-collapse')).getAttribute('class')).toContain("in");
			//check the modified widget is under 'updated widgets' category
			expect(element(by.css('#logoutConfirmation .panel-collapse .panel-body h4')).getText()).toBe("Updated widgets");
			expect(element(by.css('#logoutConfirmation .panel-collapse .panel-body p button')).getText()).toBe("Save Widget");
			expect(element(by.css('#logoutConfirmation .panel-collapse .panel-body p')).getText()).toContain("create-or-edit-user-form-widget, 100");
		});
	});
});
