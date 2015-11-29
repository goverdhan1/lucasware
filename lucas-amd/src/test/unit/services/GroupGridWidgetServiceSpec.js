'use strict';

describe('Group Grid Service Specs', function () {

    var service, def, RestApiService;

    beforeEach(module('amdApp', function ($translateProvider) {
        $translateProvider.translations('en', {
            "language-code": "EN"
        }, 'fr', {
            "language-code": "fr"
        }, 'de', {
            "language-code": "de"
        }).preferredLanguage('en');
        $translateProvider.useLoader('LocaleLoader');
    }));

    //dependency injection tests
    describe('Dependency Injection Tests', function () {

        beforeEach(inject(function (GroupGridWidgetService, _RestApiService_, _$q_) {
            service = GroupGridWidgetService;
            RestApiService=_RestApiService_;
            //setup dummy deferred exec.
            def = _$q_.defer();
  
        }));

        it('GroupGridWidgetService to be defined', function () {
            expect(service).toBeDefined();
        });

        it('deleteGroup() to be defined', function () {

          //resolve the promise
          def.resolve({
              "status":"success"
          });

            spyOn(RestApiService, "post").andReturn(def.promise)
            expect(service.deleteGroup).toBeDefined();
            
            // invoke the deleteGroup function
            service.deleteGroup();

            //assert that a call was made to the restApi to delete a group.
            expect(RestApiService.post).toHaveBeenCalled();

        });

    });
});
