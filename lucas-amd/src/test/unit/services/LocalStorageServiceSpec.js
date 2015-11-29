/**
 *
 *  Copyright (c)  Lucas Systems Inc.
 *
 **/
'use strict';

describe('Local Storage Service related Unit Tests', function() {

	beforeEach(module('amdApp'));

	var localLSService = null;

	var injectLSService = inject(function(LocalStoreService) {
		localLSService = LocalStoreService;
	});
	
	// Injection tests
	describe('dependancy injection tests', function() {

		beforeEach(function() {
			injectLSService();
		});

		it('should inject dependencies', function() {
			expect(localLSService).toBeDefined();
		});		
	});
	
	// Add items to local storage
	describe('adding items to local storage tests', function() {
		
		beforeEach(function() {
			injectLSService();
		});
		
		it('should add item to local storage', function() {
			localLSService.addLSItem(null, 'UserInfo', 'Bob');
			expect(localLSService.getLSItem('UserInfo')).toEqual('Bob');
		});
	});
	
	// Get items from local storage
	describe('getting items from local storage tests', function() {
		
		beforeEach(function() {
			injectLSService();
			localLSService.addLSItem(null, 'UserInfo', 'Bob');
		});
		
		it('should ', function() {
			var result = localLSService.getLSItem('UserInfo');
			expect(result).toEqual('Bob');
		});
		
	});
	
	// Removing items form local storage
	describe('removing items from local storage tests', function() {
	
		beforeEach(function() {
			injectLSService();
			// Create dummy LocalStore object to clear
			localLSService.addLSItem(null, 'UserInfo', 'Bob');
		});
		
		it('should remove specific item from local storage', function() {
			localLSService.removeLSItem('UserInfo');
			expect(localLSService.getLSItem('UserInfo')).toEqual(null);
		});
		
		it('should clear user specific local storage items', function() {			
			localLSService.clearUserItems('Bob');
			expect(localLSService.getLSItem('UserInfo')).toEqual(null);
		});
		
		it('should clear all items in local storage', function() {
			localLSService.clearAll();
			expect(localLSService.getLSItem('UserInfo')).toEqual(null);
		});
		
	});
	
});