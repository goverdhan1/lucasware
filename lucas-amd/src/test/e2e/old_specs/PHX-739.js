// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('phx-739 pickline by wave barchart widget reacttomap, listensforlist e2e tests', function() {
	//Test setup
	beforeEach(function() {
		browser.get('');
		element(by.model('username')).sendKeys('jack123');
		element(by.model('password')).sendKeys('secret');
		element(by.css('.loginBtn')).click();
		element(by.css('#load-widget a')).click();
	});

	describe('pikcline by wave barchart widget ', function() {
		beforeEach(function() {
			element(by.linkText('Pickline By Wave Bar chart')).click();
			element(by.css('#wid100 .cogMenuBtn')).click();
		});
		it('ensure listensForList is populated in dropdown, allows user to delete an item, shows save button, and clicks save button', function() {
			//ensure intended cog menu is loaded, with expected controls.
			expect(element(by.css('.modal-header h4')).getText()).toBe("pickline-by-wave-barchart-widget");
			//ensure reset widget and save button is not present
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-danger')).isDisplayed()).toBeFalsy();
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-success')).isDisplayed()).toBeFalsy();
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-warning')).isDisplayed()).toBeTruthy();

			//get the values of listensForList, ReactToMapList from dom dropdowns
			var listensForListValues = element.all(by.options('listensForValue as listensForValue for listensForValue in widgetinstance.widgetViewConfig.listensForList'));
			var reactToListValues = element.all(by.options('reactToValue as reactToValue for reactToValue in widgetinstance.widgetViewConfig.reactToList'));
			var updateReactToList = element(by.css('#moveItemsToReactToList'));
			var updateListenForList = element(by.css('#moveItemsToListensForList'));
			var updateAllListenForItem = element(by.css('#moveAllItemsToListensForList'));
			var updateAllReactToList = element(by.css('#moveAllItemsToReactToList'));

			var selectedListenForValue = element(by.model('selectedListenForValue'));
			var selectedReactToValue = element(by.model('selectedReactToValue'));
			var currentListensForListCount = listensForListValues.count();
			expect(currentListensForListCount).toBeGreaterThan(0);

			//spec to test move all items from listens for list to react to list
			expect(updateAllReactToList.isPresent()).toBe(true);
			updateAllReactToList.click();
			expect(listensForListValues.length).not.toBeDefined();

			//spec to test move all items from react to list to listens for list
			expect(updateAllListenForItem.isPresent()).toBe(true);
			updateAllListenForItem.click();
			expect(reactToListValues.length).not.toBeDefined();

			//spec to test move one or more items from listens for list to react to list
			var optionElement = element(by.css('select#listensForList option:nth-child(1)'));
			optionElement.click();
			expect(selectedListenForValue.getText()).toEqual(jasmine.any(String));
			updateReactToList.click();
			
			//spec to test move one or more items from react to list to listens for list
			var optionElement = element(by.css('select#reactToList option:nth-child(1)'));
			optionElement.click();
			expect(selectedReactToValue.getText()).toEqual(jasmine.any(String));
			updateListenForList.click();
			expect(selectedReactToValue.getText()).toEqual('');

			//save the cog menu modal popup with the changes
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-success')).isDisplayed()).toBeTruthy();
			element(by.css('.showCogmenuWidgetModal .modal-footer .btn-success')).click();
			expect(element(by.css('#wid100 .panel-heading')).getText()).toBe("pickline-by-wave-barchart-widget, 100");

			//reset widget to factory settings; do some changes to ensure reset is working fine
			element(by.css('#wid100 .cogMenuBtn')).click();
			updateAllReactToList.click();
			element(by.css('.showCogmenuWidgetModal .modal-footer .btn-success')).click();
			element(by.css('#wid100 .cogMenuBtn')).click();
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-danger')).isPresent()).toBe(true);
			element(by.css('.showCogmenuWidgetModal .modal-footer .btn-danger')).click();
			expect(element(by.css('#resetConfirmation .modal-body h4')).getText()).toContain("you want to reset");
			element(by.css('#resetConfirmation .modal-footer #btn-reset-confirmation')).click();
			element(by.css('#wid100 .cogMenuBtn')).click();

			//ensure reset widget and save button is not present
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-danger')).isDisplayed()).toBeFalsy();
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-success')).isDisplayed()).toBeFalsy();
			expect(element(by.css('.showCogmenuWidgetModal .modal-footer .btn-warning')).isDisplayed()).toBeTruthy();
		});
	});

});
