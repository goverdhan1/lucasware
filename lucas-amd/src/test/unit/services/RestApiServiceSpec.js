'use strict';

describe('RestApiService Tests', function() {
	
	beforeEach(module('amdApp'));

	var localRestangular;
	var localRestApiService;

	var injectRestangular = inject(function(Restangular) {
		localRestangular = Restangular;
		//Set mocks
        spyOn(Restangular, "setBaseUrl").andReturn();
        spyOn(Restangular, "setRequestSuffix").andReturn();
    });
	
	var injectRestApiService = inject(function(RestApiService) {
		localRestApiService = RestApiService;
	});
	
	describe('local restangular access', function() {
		beforeEach(function() {
			module(function ($provide) {
				$provide.value('DomainPath', "aLocalDomainPath");
				$provide.value('ClientPath', "aLocalClientPath");
				$provide.value('ApiPath', "anApiPath");
				$provide.value('JsonPath', "aJsonPath");
				$provide.value('UseLocalJsons', true);
			});
			
			injectRestangular();
			injectRestApiService();
		});
		
		it('should inject deps', function() {
			expect(localRestApiService).toBeDefined();
			expect(localRestangular).toBeDefined();
		});
		
		it('should make call to restangular to access local Json with good path', function() {
			localRestApiService.getAll('navigation');
			expect(localRestangular.setRequestSuffix).toHaveBeenCalledWith("");
			expect(localRestangular.setBaseUrl).toHaveBeenCalledWith("aLocalDomainPathanApiPath");
		});
	});
	
	describe('remote restangular access', function() {
		beforeEach(function() {
			module(function ($provide) {
				$provide.value('DomainPath', "aLocalDomainPath");
				$provide.value('ClientPath', "aLocalClientPath");
				$provide.value('ApiPath', "anApiPath");
				$provide.value('JsonPath', "aJsonPath");
				$provide.value('UseLocalJsons', false);
			});
			
			injectRestangular();
			injectRestApiService();
		});
		
		it('should make call to restangular to access remote Json with good path that is true', function() {
			localRestApiService.getAll('authenticate');
			localRestangular.setRequestSuffix('');
			expect(localRestangular.setRequestSuffix).toHaveBeenCalledWith("");
			expect(localRestangular.setBaseUrl).toHaveBeenCalledWith("aLocalDomainPathanApiPath");
		});
		
		it('should make call to restangular to access local Json with bad path', function() {
			localRestApiService.getAll('doesntExist');
			localRestangular.setRequestSuffix('');
			expect(localRestangular.setRequestSuffix).toHaveBeenCalledWith("");
			expect(localRestangular.setBaseUrl).toHaveBeenCalledWith("aLocalDomainPathanApiPath");
		});
	});
	
	afterEach(function() {
		jasmine.getEnv().addReporter(new jasmine.ConsoleReporter(console.log));
	});
});