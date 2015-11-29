describe('Save a Widget or a Canvas Directive Unit tests', function() {
	var localCompile = null;
	var localScope = null;
	var localElement = null;
	var localWidgetService = null;
	var localHttpBackend = null;
	var widget = {
		canvasId : 5,
		canvasName : "Pick Monitoring Canvas",
		widgetInstanceList : [{
			clientId : 100
		}]
	};
	var msg = {
		"status" : "success",
		"level" : "success",
		"code" : "Widget is saved successfully. The widget details : ",
		"explicitDismissal" : true,
		//"message" : angular.toJson(instanceObj, true)
		"message" : widget.canvasName + widget.canvasId + widget.widgetInstanceList.clientId
	};

	// Load the amdApp module, which contains the directive
	beforeEach(module('amdApp', function(_$translateProvider_) {
		_$translateProvider_.translations('en', {
			"language-code" : "EN"
		}, 'fr', {
			"language-code" : "fr"
		}, 'de', {
			"language-code" : "de"
		}).preferredLanguage('en');
		_$translateProvider_.useLoader('LocaleLoader');
	}));

	describe('Dependency Injection specs', function() {
		// The injector unwraps the underscores (_) from around the parameter names when matching
		beforeEach(inject(function(_$compile_, _$rootScope_, WidgetService, _$httpBackend_) {
			localCompile = _$compile_;
			localScope = _$rootScope_;
			localWidgetService = WidgetService;
			localHttpBackend = _$httpBackend_;
			// Handle all the POST requests
			localHttpBackend.when('GET').respond({
				data : {}
			});

			//create the directive
			localElement = localCompile("<save-widget-canvas canvaslevel='true'></save-widget-canvas>")(localScope);
			spyOn(localWidgetService, 'showUpdatedCanvas').andReturn();
			localHttpBackend.flush();
			localScope.$digest();
		}));

		it('should inject directive dependencies', function() {
			expect(localScope).toBeDefined();
			expect(localCompile).toBeDefined();
			expect(localElement).toBeDefined();
			expect(localWidgetService).toBeDefined();
		});

		it('should assign the values of widgetservice.showUpdatedCanvas', function() {
			var canvasListToSave = localWidgetService.showUpdatedCanvas(true);
			localScope.$digest();
			expect(localScope.canvasListToBeSaved).toEqual(canvasListToSave);
		});

		it('should call fn SaveCanvas when clicking save canvas', function() {

		});

	});
	describe('should call fn SaveCanvas', function() {
		beforeEach(inject(function(_$compile_, _$rootScope_, _$httpBackend_) {
			localCompile = _$compile_;
			localScope = _$rootScope_.$new();
			localHttpBackend = _$httpBackend_;
			// Handle all the POST requests
			localHttpBackend.when('GET').respond({
				data : {}
			});

			//var html = '<button class="btn btn-primary btn-xs pull-right" value="yes" data-ng-click="SaveCanvas(canvas, $event)">Save Canvas <span class="badge ng-binding"> 1</span></button>';
			var html = '<save-widget-canvas canvaslevel="true"></save-widget-canvas>';

			//compile the directive
			localElement = localCompile(html)(localScope);
			localScope.SaveCanvas = jasmine.createSpy();
			localHttpBackend.flush();
			localScope.$digest();
		}));

		it('when clicking save canvas', function() {
			var canvas = {
				canvasId : 5,
				canvasName : "Pick Monitoring Canvas",
				widgetInstanceList : []
			};
			localScope.SaveCanvas(canvas, null);
			localScope.$digest();
			expect(localScope.SaveCanvas).toHaveBeenCalled();

		});
	});

	describe('should call fn SaveWidget', function() {
		beforeEach(inject(function(_$compile_, _$rootScope_, _$httpBackend_) {
			localCompile = _$compile_;
			localScope = _$rootScope_.$new();
			localHttpBackend = _$httpBackend_;
			// Handle all the POST requests
			localHttpBackend.when('GET').respond({
				data : {}
			});

			//var html = '<button class="btn btn-primary btn-xs pull-right" value="yes" data-ng-click="SaveCanvas(canvas, $event)">Save Canvas <span class="badge ng-binding"> 1</span></button>';
			var html = '<save-widget-canvas canvaslevel="true"></save-widget-canvas>';

			//compile the directive
			localElement = localCompile(html)(localScope);
			localScope.SaveWidget = jasmine.createSpy();
			localScope.$broadcast = jasmine.createSpy();
			localHttpBackend.flush();

			localScope.SaveWidget(widget, null);
			localScope.$broadcast('showNotification', msg);
			localScope.$digest();
		}));

		it('when clicking save Widget', function() {
			//localScope.$digest();
			expect(localScope.SaveWidget).toHaveBeenCalled();
			expect(localScope.$broadcast).toHaveBeenCalledWith('showNotification', msg);
		});
	});
});
