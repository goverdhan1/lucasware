"use strict";

describe('PHX-953 - FE: AMD Canvas layout and saving of widget instance configurations', function () {

	//Test setup
	beforeEach(function () {
		browser.get('');
		element(by.model('username')).sendKeys('jack123');
		element(by.model('password')).sendKeys('secret');
		element(by.css('.loginBtn')).click();
	});

	//Clean up after each tests has finished
	afterEach(function () {
		// implicitly tests the logout functionality
		element(by.id('btn-user-menu')).click();
		element(by.id('btn-logout')).click();
		element(by.id('btn-logout-confirmation')).click();
	});

	// commented out the tests, as the loading from the actual endpoint will be addressed in the future tickets
	xit('Test loading of active canvas', function () {
		// select xyz canvas
		element(by.linkText('xyz')).click();

		// test presence of create or edit user form widget
		expect( element.all(by.cssContainingText('.panel-heading', 'create-or-edit-user-form-widget')).count() ).toBe(1);

		// test presence of search user grid widget
		expect( element.all(by.cssContainingText('.panel-heading', 'search-user-grid-widget')).count() ).toBe(1);

		// select  Work Execution canvas
		element(by.linkText('Work Execution')).click();

		// test presence of user group widget
		expect( element.all(by.cssContainingText('.panel-heading', 'user-group-widget')).count() ).toBe(1);

	});

	it('Test loading of amd logo', function () {
		// test presence of amd logo
		expect(element.all(by.id('amdLogo')).count()).toEqual(1);
	});

	it('Test loading of customer logo', function () {
		// test presence of customer logo
		expect(element.all(by.id('customerLogo')).count()).toEqual(1);
	});

	it('Test loading of user preferences', function () {
		// test presence of user menu
		expect(element.all(by.id('btn-user-menu')).count()).toEqual(1);
		// open the dropdown
		element(by.id('btn-user-menu')).click();
		// test presence of preferences button
		expect(element.all(by.id('btn-my-preferences')).count()).toEqual(1);
		// close the dropdown
		element(by.id('btn-user-menu')).click();
	});

	// Make home canvas is removed now
	xit('Test Make Home Canvas', function () {
		// test presence of modal dialog popup
		expect(element.all(by.css('.modal-dialog')).count()).toEqual(0);
		// open the dropdown
		element(by.id('btn-user-menu')).click();
		// select the Make Home Canvas
		element(by.id('btn-make-home-canvas')).click();
		// test presence of modal dialog popup
		expect(element.all(by.css('.modal-dialog')).count()).toEqual(1);
		// press ok button
		element(by.id('btn-make-home-canvas-ok-confirmation')).click();
		// test presence of modal dialog popup
		expect(element.all(by.css('.modal-dialog')).count()).toEqual(0);
		// open the dropdown
		element(by.id('btn-user-menu')).click();
		// select the Make Home Canvas
		element(by.id('btn-make-home-canvas')).click();
		// test presence of modal dialog popup
		expect(element.all(by.css('.modal-dialog')).count()).toEqual(1);
		// press cancel button
		element(by.id('btn-make-home-canvas-close-confirmation')).click();
		// test presence of modal dialog popup
		expect(element.all(by.css('.modal-dialog')).count()).toEqual(0);
	});
});