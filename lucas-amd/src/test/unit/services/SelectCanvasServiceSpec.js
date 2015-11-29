'use strict';

describe('SelectCanvas Service related Tests', function() {
	var localStoreService;
	var localSelectCanvasService;
	var localScope;
	var jackMockData = {
		"status": "success",
		"code": "200",
		"message": "Request processed successfully",
		"level": null,
		"uniqueKey": null,
		"token": null,
		"explicitDismissal": null,
		"data": {
			"canvasByCategory": {
				"private": [{
					"canvasId": 100,
					"name": "Overview Warehouse1"
				}, {
					"canvasId": 101,
					"name": "bcd"
				}],
				"company": [{
					"canvasId": 110,
					"name": "cde"
				}, {
					"canvasId": 111,
					"name": "def"
				}],
				"lucas": [{
					"canvasId": 201,
					"name": "efg"
				}, {
					"canvasId": 202,
					"name": "fgh"
				}]
			},
			"user": {
				"userId": 3,
				"userName": "jack123",
				"permissionSet": ["create-canvas"]
			}
		}
	};
	var jillMockData = {
		"status": "success",
		"code": "200",
		"message": "Request processed successfully",
		"level": null,
		"uniqueKey": null,
		"token": null,
		"explicitDismissal": null,
		"data": {
			"canvasByCategory": {
				"private": [{
					"canvasId": 100,
					"name": "Overview Warehouse1"
				}],
				"company": [{
					"canvasId": 110,
					"name": "cde"
				}]
			},
			"user": {
				"userId": 3,
				"userName": "jill123",
				"permissionSet": []
			}
		}
	};

	beforeEach(module('amdApp'));

	beforeEach(inject(function(SelectCanvasService, LocalStoreService, $rootScope, $httpBackend) {
		localScope = $rootScope.$new();
		$httpBackend.when('GET').respond({});
		$httpBackend.when('POST').respond({});
		localSelectCanvasService = SelectCanvasService;
		localStoreService = LocalStoreService;
	}));

	it('should list available screen list for Jack', inject(function($rootScope) {
		localStoreService.addLSItem(null, 'UserInfo', "Jack");
		spyOn(localSelectCanvasService, 'getAllScreens').andReturn(jackMockData);
		expect(localSelectCanvasService.getAllScreens()).toEqual(jackMockData);
	}));

	it('should list available screen list for Jill', inject(function($rootScope) {
		localStoreService.addLSItem(null, 'UserInfo', "Jill");
		spyOn(localSelectCanvasService, 'getAllScreens').andReturn(jillMockData);
		expect(localSelectCanvasService.getAllScreens()).toEqual(jillMockData);
	}));

});
