'use strict';

describe('WidgetChartService related Tests', function() {
	var $rootScope, $state, $injector, localtranslate;

	beforeEach(function() {
		module('amdApp', function($provide, $translateProvider) {
			$translateProvider.translations('en', {
				"language-code" : "EN",
			}, 'fr', {
				"language-code" : "fr",
			}, 'de', {
				"language-code" : "de",
			}).preferredLanguage('en');
			$translateProvider.useLoader('LocaleLoader');
		});
		inject(function(_$rootScope_, _$state_, _$injector_, $templateCache, $translate) {
			$rootScope = _$rootScope_;
			$state = _$state_;
			$injector = _$injector_;
			localtranslate = $translate;

			$templateCache.put('views/indexpage/index-tmpl.html', '');
			$templateCache.put('views/indexpage/locale.html', '');
			$templateCache.put('views/indexpage/logo.html', '');
			$templateCache.put('views/indexpage/login.html', '');
			$templateCache.put('views/indexpage/content1.html', '');
			$templateCache.put('views/indexpage/content2.html', '');
			$templateCache.put('views/indexpage/footer.html', '');
		});
	});

	var localWidgetChartService;
	var deferred = null;

	var injectWidgetChartService = inject(function(WidgetChartService) {
		localWidgetChartService = WidgetChartService;
	});

	describe('dependancy injection tests', function() {

		beforeEach(function() {
			injectWidgetChartService();
		});

		it('should inject dependencies', function() {
			expect(localWidgetChartService).toBeDefined();
		});

	});

	describe('function executions', function() {
		beforeEach(function() {
			injectWidgetChartService();
		});
		it('should call getChartData fn', inject(function($rootScope) {
			expect(localWidgetChartService.getChartData).toBeDefined();
		}));
		it('should call processChartData fn', inject(function($rootScope) {
			expect(localWidgetChartService.processLineChartData).toBeDefined();
		}));
	});

	describe('Testing nvd3 Chart with Data || Chart Type: multi bar horizontal', function() {
		var domUtils, localScope, localCompile, scope, localElement;

		beforeEach(inject(function($rootScope, $templateCache, $compile) {
			localScope = $rootScope.$new();
			localCompile = $compile;
			localElement = angular.element('<div nvd3-multi-bar-horizontal-chart id="horizontalChart10" data="chartData" showXAxis="true" showYAxis="true" tooltips="true" interactive="true"></div>');
			scope = $rootScope;
			scope.chartData = [{
				"key" : "Series 1",
				"values" : [[1025409600000, 0], [1028088000000, -6.3382185140371], [1030766400000, -5.9507873460847], [1033358400000, -11.569146943813], [1036040400000, -5.4767332317425], [1038632400000, 0.50794682203014], [1041310800000, -5.5310285460542], [1043989200000, -5.7838296963382], [1046408400000, -7.3249341615649], [1049086800000, -6.7078630712489], [1051675200000, 0.44227126150934], [1054353600000, 7.2481659343222], [1056945600000, 9.2512381306992]]
			}, {
				"key" : "Series 2",
				"values" : [[1025409600000, -7.0674410638835], [1028088000000, -14.663359292964], [1030766400000, -14.104393060540], [1033358400000, -23.114477037218], [1036040400000, -16.774256687841], [1038632400000, -11.902028464000], [1041310800000, -16.883038668422], [1043989200000, -19.104223676831], [1046408400000, -20.420523282736], [1049086800000, -19.660555051587], [1051675200000, -13.106911231646], [1054353600000, -8.2448460302143], [1056945600000, -7.0313058730976]]
			}];
			//angular.element('body').append(localElement);
			localCompile(localElement)(scope);
			localScope.$digest();
		}));
		it('should display multi bar horizontal chart based on json response', function() {
			//console.log(localElement.length);			
			expect(localElement.length).toBe(1);
		});
	});
	
	describe('Testing nvd3 Chart with Data || Chart Type: multi bar vertical', function() {
		var domUtils, localScope, localCompile, scope, localElement;

		beforeEach(inject(function($rootScope, $templateCache, $compile) {
			localScope = $rootScope.$new();
			localCompile = $compile;
			localElement = angular.element('<div nvd3-multi-bar-chart id="verticalChart10" data="chartData" showXAxis="true" showYAxis="true" tooltips="true" interactive="true"></div>');
			scope = $rootScope;
			scope.chartData = [{
				"key" : "Series 1",
				"values" : [[1025409600000, 0], [1028088000000, -6.3382185140371], [1030766400000, -5.9507873460847], [1033358400000, -11.569146943813], [1036040400000, -5.4767332317425], [1038632400000, 0.50794682203014], [1041310800000, -5.5310285460542], [1043989200000, -5.7838296963382], [1046408400000, -7.3249341615649], [1049086800000, -6.7078630712489], [1051675200000, 0.44227126150934], [1054353600000, 7.2481659343222], [1056945600000, 9.2512381306992]]
			}, {
				"key" : "Series 2",
				"values" : [[1025409600000, -7.0674410638835], [1028088000000, -14.663359292964], [1030766400000, -14.104393060540], [1033358400000, -23.114477037218], [1036040400000, -16.774256687841], [1038632400000, -11.902028464000], [1041310800000, -16.883038668422], [1043989200000, -19.104223676831], [1046408400000, -20.420523282736], [1049086800000, -19.660555051587], [1051675200000, -13.106911231646], [1054353600000, -8.2448460302143], [1056945600000, -7.0313058730976]]
			}];
			localCompile(localElement)(scope);
			localScope.$digest();
		}));
		it('should display multi bar vertical chart based on json response', function() {			
			expect(localElement.length).toBe(1);
		});
	});
	
	describe('Testing nvd3 Chart with Data || Chart Type: Pie', function() {
		var domUtils, localScope, localCompile, scope, localElement;

		beforeEach(inject(function($rootScope, $templateCache, $compile) {
			localScope = $rootScope.$new();
			localCompile = $compile;
			localElement = angular.element('<div nvd3-pie-chart id="pieChart10" data="chartData" showXAxis="true" showYAxis="true" tooltips="true" interactive="true"></div>');
			scope = $rootScope;
			scope.chartData = [{
				"key" : "Series 1",
				"values" : 10
			}, {
				"key" : "Series 2",
				"values" : 20
			}];
			localCompile(localElement)(scope);
			localScope.$digest();
		}));
		it('should display Pie chart based on json response', function() {			
			expect(localElement.length).toBe(1);
		});
	});
	
	describe('Testing nvd3 Chart with Data || Chart Type: Pie Donut', function() {
		var domUtils, localScope, localCompile, scope, localElement;

		beforeEach(inject(function($rootScope, $templateCache, $compile) {
			localScope = $rootScope.$new();
			localCompile = $compile;
			localElement = angular.element('<div nvd3-pie-chart id="pieChart11" donut="true" data="chartData" showXAxis="true" showYAxis="true" tooltips="true" interactive="true"></div>');
			scope = $rootScope;
			scope.chartData = [{
				"key" : "Series 1",
				"values" : 10
			}, {
				"key" : "Series 2",
				"values" : 20
			}];
			localCompile(localElement)(scope);
			localScope.$digest();
		}));
		it('should display Pie Donut chart based on json response', function() {			
			expect(localElement.length).toBe(1);
		});
	});

});
