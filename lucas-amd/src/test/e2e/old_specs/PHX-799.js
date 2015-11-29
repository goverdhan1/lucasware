// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('E2E Tests for PHX-754 FE: GroupMaintenance Widget', function() {
	//Test setup
	beforeEach(function() {
		browser.get('');
		element(by.model('username')).sendKeys('jack123');
		element(by.model('password')).sendKeys('secret');
		element(by.css('.loginBtn')).click();
		element(by.css('#load-widget a')).click();
	});

	describe('GroupMaintenance Widget', function() {
		beforeEach(function() {
			element(by.linkText('Group Maintenance')).click();
		});
		it('ensure user can select existing group which shows available, existing permissions; ensure user can add a new group; ensure user cannnot add the same group; ensure you can move items from LHS to RHS and vice versa', function() {
			expect(element(by.css('#wid100 .panel-heading')).getText()).toBe("group-maintenance-widget, 100");
			//check for existing group.
			var selectedItemNameModel = element(by.model('widgetdetails.selectedItemName'));
			selectedItemNameModel.sendKeys('s');
			var typeaheadDropDown = element(by.css('#wid100 .group-maintenance-widget ul[matches="matches"]'));

			//ensure typeahead options are displayed
			expect(typeaheadDropDown.isDisplayed()).toBe(true);
			expect(typeaheadDropDown.getText()).toContain("supervisor");

			//click on first option in typeahead - admin; ensure permissions are populated in lhs and rhs
			element(by.css('#wid100 .group-maintenance-widget ul[matches="matches"] li:first-child')).click();
			//var availableItems = element.all(by.options("availableItem for availableItem in widgetdetails.availableItems | filter: removeExistingDataFilter | orderBy:'toString()'"));
			//var availableItems = element.all(by.options("availableItem for availableItem in widgetdetails.availableItems"));
			//ensure values are populated in availablePermissions dropdown
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("user");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("canvas");

			//test for moveAllItems from lhs to rhs
			element(by.css("button[data-ng-click='moveAllToExistingData()']")).click();
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("user");

			//test for moveAllItems from rhs to lhs
			element(by.css("button[data-ng-click='moveAllToAvailableData()']")).click();
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("user");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("");

			//test for moving one or more item from lhs to rhs
			var selectedOption = element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems'] option:nth-child(1)"));
			var selectedOptionText = selectedOption.getText();
			selectedOption.click();
			//ensure it is bounded to the model
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain(selectedOptionText);
			element(by.css("button[data-ng-click='moveOneToExistingData()']")).click();
			//ensure the selected option is added to RHS existing permissions dropdown.
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toEqual(selectedOptionText);

			//test for moving one or more item from rhs to lhs
			var selectedOption = element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems'] option:nth-child(1)"));
			var selectedOptionText = selectedOption.getText();
			selectedOption.click();
			//ensure it is bounded to the model
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain(selectedOptionText);
			element(by.css("button[data-ng-click='moveOneToAvailableData()']")).click();
			//ensure the selected option is added to LHS available permissions dropdown.
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain(selectedOptionText);

			//ensure you can add a new group; ensure you can't add the same group
			var newGroupModel = element(by.model('newGroupName'));
			newGroupModel.sendKeys('admin');
			element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).click();
			expect(element(by.css('.notification-panel')).getText()).toContain("Group already exists. Please enter new name");

		});
	});

});
