'use strict';
describe('WidgetDefinition Controller Tests', function() {

	var widgetDefinitionObj = {
		"id" : 1,
		"name" : 'user-create-or-edit-widget',
		"shortName" : 'user create form',
		"type" : 'FORM',
		"subtype" : 'FORM',
		"widgetActionConfig" : {
			"create-assignment" : false,
			"view-report-productivity" : false,
			"configure-location" : false,
			"delete-canvas" : false,
			"create-canvas" : false
		},
		"broadCastMap" : {
			"Area" : "series.key",
			"Route" : "point.label"
		},
		"listensForList" : ["Area", "Shift", "Picker"],
		"definitionData" : {
			"User" : [{
				"startDate" : "08-12-2014"
			}],
			"handheldScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
			"amdScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
			"jenniferToUserLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
			"userToJenniferLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"]
		},
		"actualViewConfig" : {
			"anchor" : [400, 200],
			"height" : 400,
			"width" : 400,
			"zindex" : 1
		}
	};
	var actualViewConfig = widgetDefinitionObj.actualViewConfig;
	var widgetInstanceObject = {
		"data" : null,
		"id" : 10,
		"widgetDefinition" : widgetDefinitionObj,
		"actualViewConfig" : actualViewConfig
	};

	beforeEach(module('amdApp'));
	var localScope, localUtilsService, localWidgetDefinitionController, localLocalStoreService;
	
	beforeEach(inject(function(LocalStoreService) {
		localLocalStoreService = LocalStoreService;
		localLocalStoreService.addLSItem(null, 'addedWidgetInstance', widgetInstanceObject);
	}));

	beforeEach(inject(function($rootScope, $controller, UtilsService) {
		localScope = $rootScope.$new();
		localUtilsService = UtilsService;		
		localWidgetDefinitionController = $controller('WidgetDefinitionController', {
			$scope : localScope,
			UtilsService : localUtilsService
		});
	}));

	/*describe('WidgetDefinitionController injection tests', function() {		
		it('should inject "WidgetDefinitionController" controller', function() {			
			expect(localWidgetDefinitionController).toBeDefined();
		});
		it('should do scope assignments', function() {			
			expect(localScope.formData).toEqual(widgetInstanceObject.widgetDefinition);
		});
		it('should format start data', function() {
			expect(localScope.startdate).toEqual('2014-08-12');			
		});
	});*/
});
