/*
 Lucas Systems Inc
 11279 Perry Highway
 Wexford
 PA 15090
 United States


 The information in this file contains trade secrets and confidential
 information which is the property of Lucas Systems Inc.

 All trademarks, trade names, copyrights and other intellectual property
 rights created, developed, embodied in or arising in connection with
 this software shall remain the sole property of Lucas Systems Inc.

 Copyright (c) Lucas Systems, 2014
 ALL RIGHTS RESERVED

 */

"use strict";

var gridTestUtils = require( '../../resources/gridTestUtils.js');

describe('PHX-1139, PHX-1140 and PHX-1200 search user grid widget, listen for Delete, Disable, Enable, Retrain Voice Models, Manual Refresh e2e tests', function() {
	//Test setup
	beforeEach(function() {
		browser.get('');
		element(by.model('username')).sendKeys('jack123');
		element(by.model('password')).sendKeys('secret');
		element(by.css('.loginBtn')).click();
		element(by.css('#load-widget a')).click();
		element(by.cssContainingText('.menu li', 'Administration (4)')).click();
		element(by.cssContainingText('.widget .ng-binding', 'Search User')).click();

	});

describe("Action buttons", function(){

	it("Should select a record from grid and test for delete functionality and cancel the deletion", function(){
			
			//no rows selected - actions button disabled
            expect(element(by.css('#wid100 #deleteUser')).isEnabled()).toBeFalsy();

            //select a row - actions button enabled
            gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
            expect(element(by.css('#wid100 #deleteUser')).isEnabled()).toBeTruthy();

			element(by.css('#wid100 #deleteUser')).click();
			element(by.css('#btn-delete-close-confirmation')).click();
		});

		it("Should select a record from grid and test for disable functionality", function(){

			//no rows selected - actions button disabled
            expect(element(by.css('#wid100 #disableUser')).isEnabled()).toBeFalsy();

            //select a row - actions button enabled
            gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
            expect(element(by.css('#wid100 #disableUser')).isEnabled()).toBeTruthy();

			element(by.css('#wid100 #disableUser')).click();
			element(by.css('#btn-disable-close-confirmation')).click();
		});

		it("Should select a record from grid and test for enable functionality", function(){
			//no rows selected - actions button disabled
            expect(element(by.css('#wid100 #enableUser')).isEnabled()).toBeFalsy();

            //select a row - actions button enabled
            gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
            expect(element(by.css('#wid100 #enableUser')).isEnabled()).toBeTruthy();

			element(by.css('#wid100 #enableUser')).click();
			element(by.css('#btn-enable-close-confirmation')).click();
		});

		it("Should select a record from grid and test for retrain voice model functionality and cancel", function(){

			//no rows selected - actions button disabled
            expect(element(by.css('#wid100 #retrainVoiceModel')).isEnabled()).toBeFalsy();

            //select a row - actions button enabled
            gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
        	//expect(element(by.css('#wid100 #retrainVoiceModel')).isEnabled()).toBeTruthy();

			element(by.css('#wid100 #retrainVoiceModel')).click();
			element(by.css('#btn-retrain-voice-close-confirmation')).click();
		});

		it("Should select a record from grid and refresh the grid when click on Manual Refresh button", function(){

            gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
            expect(element(by.css('#wid100 #searchUserGridManualRefresh')).isPresent()).toBeTruthy();
			element(by.css('#wid100 #searchUserGridManualRefresh')).click();
            
            //assert gird loaded exists
            expect(element(by.css('#wid100 .ui-grid-canvas')).isPresent()).toBeTruthy();
		});

		it("Should clear the search filter fields", function(){

            expect(element(by.css('#wid100 #unfilterSearchUsers')).isPresent()).toBeTruthy();
			element(by.css('#wid100 #unfilterSearchUsers')).click();
            
            //assert gird loaded exists

	        var filterFields = element( by.id("#wid100") ).element( by.css('.ui-grid-filter-input'));
        	for(var i=0; i<filterFields.length; i++){
        		expect(filterFields.value).toEqual(null)
        	}
        });

		it("Should check total count in footer", function(){
	  	    gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
			expect( element(by.css('#wid100 .total-count') ).isPresent()).toBeTruthy();
		});

		it("Should check the auto refresh is paused ", function(){
	  	    gridTestUtils.clickGridRowHeader('searchUserGrid_100', 1);
			expect( element(by.css('#wid100 .pause-auto-refresh') ).isPresent()).toBeTruthy();
		});
	})
});
