// commented out as this depends on load widget button, and due to the new UI changes now there is no load widget button
// Need to update the test case once the future part of PHX-927 is completed
xdescribe('E2E Tests for PHX-756 FE:UserGroupWidget', function() {
	//Test setup

	describe('User Group Widget', function() {

		it('login and load user group widget', function() {
			browser.get('');
			element(by.model('username')).sendKeys('jack123');
			element(by.model('password')).sendKeys('secret');
			element(by.css('.loginBtn')).click();
			element(by.css('#load-widget a')).click();
			element(by.linkText('User Group Maintenance')).click();
		});
		it('ensure user group widget is loaded with expected fields', function() {
			expect(element(by.css('#wid100 .panel-heading')).getText()).toBe("user-group-widget, 100");
			expect(element(by.css('h4[translate="userGroup.existingGroups"]')).getText()).toBe("Existing Groups");
			expect(element(by.css('h4[translate="userGroup.availableGroups"]')).getText()).toBe("Available Groups");
			expect(element(by.css('h3[translate="userGroup.userName"]')).getText()).toBe("User Name");
			expect(element(by.css('h3[translate="userGroup.resultantPermissions"]')).getText()).toBe("Resultant Permissions");
			expect(element(by.css('.modal-footer .btn-warning')).getText()).toBe("Cancel");
			expect(element(by.css('.modal-footer .btn-success')).getText()).toBe("Save");
		});
		it('ensure typeahead values are shown when username is entered', function() {
			var selectedItemNameModel = element(by.model('widgetdetails.selectedItemName'));
			selectedItemNameModel.sendKeys('j');
			var typeaheadDropDown = element(by.css('#wid100 .user-group-widget ul[matches="matches"]'));
			//ensure typeahead options are displayed
			expect(typeaheadDropDown.isDisplayed()).toBe(true);
			expect(typeaheadDropDown.getText()).toContain("jack");
			expect(typeaheadDropDown.getText()).toContain("jill");
			expect(typeaheadDropDown.getText()).toContain("john");
		});
		it('ensure selecting a typeahead value populates lhs and rhs dropdonw', function() {
			//click on first option in typeahead - jack; ensure groups are populated in lhs and rhs
			element(by.css('#wid100 .user-group-widget ul[matches="matches"] li:first-child')).click();
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("Picker");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("Admin");
		});

		it('test for moveAllItems from lhs to rhs', function() {
			element(by.css("button[data-ng-click='moveAllToExistingData()']")).click();
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("Picker");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("Admin");
		});

		it('test for moveAllItems from rhs to lhs', function() {
			element(by.css("button[data-ng-click='moveAllToAvailableData()']")).click();
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("Picker");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain("Admin");
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain("");
		});

		it('test for moving one or more item from lhs to rhs', function() {
			var selectedOption = element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems'] option:nth-child(1)"));
			var selectedOptionText = selectedOption.getText();
			selectedOption.click();
			//ensure it is bounded to the model
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain(selectedOptionText);
			element(by.css("button[data-ng-click='moveOneToExistingData()']")).click();
			//ensure the selected option is added to RHS existing permissions dropdown.
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toEqual(selectedOptionText);
		});

		it('test for moving one or more item from rhs to lhs', function() {
			var selectedOption = element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems'] option:nth-child(1)"));
			var selectedOptionText = selectedOption.getText();
			selectedOption.click();
			//ensure it is bounded to the model
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems']")).getText()).toContain(selectedOptionText);
			element(by.css("button[data-ng-click='moveOneToAvailableData()']")).click();
			//ensure the selected option is added to LHS available permissions dropdown.
			expect(element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems']")).getText()).toContain(selectedOptionText);
		});
		it('should show empty resultant permissions', function() {
			var resultPermissionList = element(by.css("div[data-ng-repeat='permission in widgetdetails.resultantPermissions']"));
			expect(resultPermissionList.isPresent()).toBe(false);
		});
		it('test when moving all items resultant permissions should show all permissions', function() {
			var resultPermissionList = element(by.css("div[data-ng-repeat='permission in widgetdetails.resultantPermissions']"));
			element(by.css("button[data-ng-click='moveAllToExistingData()']")).click();
			expect(resultPermissionList.isPresent()).toBe(true);
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toContain('user');
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toContain('canvas');
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toContain('widget');
		});
		it('test when moving one or more items resultant permissions should show available permissions', function() {
			element(by.css("button[data-ng-click='moveAllToAvailableData()']")).click();
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toBe('');
			var selectedOption = element(by.css("select[data-ng-model='widgetdetails.selectedAvailableItems'] option:nth-child(1)"));
			selectedOption.click();
			element(by.css("button[data-ng-click='moveOneToExistingData()']")).click();
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toContain('create');
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toContain('edit');
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toContain('delete');
			element(by.css("select[data-ng-model='widgetdetails.selectedExistingItems'] option:nth-child(1)")).click();
			element(by.css("button[data-ng-click='moveOneToAvailableData()']")).click();
			expect(element(by.css("#wid100 .user-group-widget .list-group")).getText()).toBe('');
		});
	});

});
