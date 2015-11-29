'use strict';

describe('Search User Grid Service Specs', function() {

    var localService = null;
    var utilsService, q, def;

    beforeEach(module('amdApp', function($translateProvider) {
        $translateProvider.translations('en', {
            "language-code" : "EN"
        }, 'fr', {
            "language-code" : "fr"
        }, 'de', {
            "language-code" : "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));
		
	//dependency injection tests
	describe('Dependency Injection Tests', function() {
		beforeEach(inject(function(SearchUserGridService) {
			localService = SearchUserGridService;
		}));
		
		it('SearchUserGridService to be defined', function() {
			expect(localService).toBeDefined();
		});
	});

    //setupDataParams specs
    describe('Setting up the userName', function() {
       
        beforeEach(inject(function(SearchUserGridService, _RestApiService_, _$q_) {
            localService = SearchUserGridService;
            RestApiService = _RestApiService_;
            //resolve the promise
            def = _$q_.defer();
        }));

        it('Should construct usernames with mock values', function() {
            //setup mock searchSortCriteria
            var mockDataRecords =
                [
                    {"birthDate":"","emailAddress":"NULL","employeeId":"","firstName":"dummy-firstName","lastName":"dummy-LastName","mobileNumber":"","needsAuthentication":"false","preferredLanguage":"English","startDate":"","title":"","userId":"8","userName":"dummy-username10"},
                    {"birthDate":"","emailAddress":"NULL","employeeId":"","firstName":"dummy-firstName","lastName":"dummy-LastName","mobileNumber":"","needsAuthentication":"false","preferredLanguage":"English","startDate":"","title":"","userId":"98","userName":"dummy-username100"},
                    {"birthDate":"","emailAddress":"NULL","employeeId":"","firstName":"dummy-firstName","lastName":"dummy-LastName","mobileNumber":"","needsAuthentication":"false","preferredLanguage":"English","startDate":"","title":"","userId":"99","userName":"dummy-username101"}
                ];

            var expectedResult = [
                            "dummy-username10",
                            "dummy-username100",
                            "dummy-username101"
                            ];

            //invoke the function
            var actualResult = localService.setupDataParams(mockDataRecords);

            //test the JSON against the expected result
            expect(actualResult).toEqual(expectedResult);
        });

        it("should test 'getColumnFilterValues()' function", function(){

            def.resolve([{}]);
            spyOn(RestApiService, 'post').andReturn(def.promise);
            expect(localService.getColumnFilterValues).toBeDefined();
            localService.getColumnFilterValues("skill");
            expect(RestApiService.post).toHaveBeenCalledWith('application/codes', null, 'skill');

        });

    });
});