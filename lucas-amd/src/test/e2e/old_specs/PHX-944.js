"use strict";

describe('PHX-944 FE: Canvases: View available canvases when adding a new canvas', function () {

	describe('tests for jill', function() {

		//Test setup
		beforeEach(function () {
			browser.get('');
			element(by.model('username')).sendKeys('jill123');
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

		it('widget pallet tests', function() {
			// click add canvas button
			element(by.id('add-canvas-btn')).click();
					
			// test presence of create new canvas button
			expect( element.all(by.id('btn-create-new-canvas')).count() ).toBe(0);
			
			// test presence of Private Canvases
			expect( element.all(by.cssContainingText('h4', 'My Private Canvases')).count() ).toBe(1);
			
			// test presence of Company Canvases
			expect( element.all(by.cssContainingText('h4', 'Company Canvases')).count() ).toBe(1);
			
			// test presence of Lucas Canvases
			expect( element.all(by.cssContainingText('h4', 'Lucas Canvases')).count() ).toBe(1);
			
			// close the modal
			element(by.id('btn-close-create-new-canvas')).click();
		});
	
	});
	
	describe('tests for jack', function() {

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

		it('canvas tests', function() {
			// click add canvas button
			element(by.id('add-canvas-btn')).click();
					
			// test presence of create new canvas button
			expect( element.all(by.id('btn-create-new-canvas')).count() ).toBe(1);
			
			// test presence of Private Canvases
			expect( element.all(by.cssContainingText('h4', 'My Private Canvases')).count() ).toBe(1);
			
			// test presence of Company Canvases
			expect( element.all(by.cssContainingText('h4', 'Company Canvases')).count() ).toBe(1);
			
			// test presence of Lucas Canvases
			expect( element.all(by.cssContainingText('h4', 'Lucas Canvases')).count() ).toBe(1);
			
			// close the modal
			element(by.id('btn-close-create-new-canvas')).click();
		});
	
	});
});