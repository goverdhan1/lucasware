"use strict";

describe('PHX-1015 FE: Canvases: When a AMD user logs in, load the canvases that were open the last time the user logged off', function () {

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

	it('Test loading of open canvases', function () {
        //Get canvas bar items
        var canvasList = element.all(by.repeater('canvasBarItem in canvasBar'));

		// test Hazmat is in open canvases
        expect(canvasList.get(0).getText()).toContain('JackCanvas405');

		// test Work Execution is in open canvases
        expect(canvasList.get(1).getText()).toContain('JackCanvas404');

		// test abc is in open canvases
        expect(canvasList.get(2).getText()).toContain('JackCanvas403');

		// test xyz is in open canvases
        expect(canvasList.get(3).getText()).toContain('JackCanvas402');
	});

	it('Test the active canvas is selected', function () {
		// test Work Execution is active canvas
        var activeCanvas = element.all(by.repeater('canvasBarItem in canvasBar')).get(0).element(by.css('a'));
        expect(activeCanvas.getAttribute('class')).toContain('btn-primary');
	});
});