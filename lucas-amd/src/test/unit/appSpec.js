describe('app.js unit tests', function() {
	describe('indexPage routing', function() {
		var $rootScope, 
			$state, 
			$injector, 
			SessionServiceMock, 
			state = 'indexpage.home', 
			localtranslate,
			localLoginService, 
			httpBackend, 
			localModalInstance, 
			localUtilsService,
			interval;

		beforeEach(function() {
			localAmdApp = module('amdApp', function($provide, $translateProvider) {
				$provide.value('SessionService', SessionServiceMock = {});
				$translateProvider.translations('en', {
					"language-code" : "EN",
				}, 'fr', {
					"language-code" : "fr",
				}, 'de', {
					"language-code" : "de",
				}).preferredLanguage('en');
				$translateProvider.useLoader('LocaleLoader');
			});
			inject(function(_$rootScope_, _$state_, _$injector_, $templateCache, $translate, LoginService, $httpBackend, $modal, UtilsService, $interval) {
				$rootScope = _$rootScope_;
				$state = _$state_;
				$injector = _$injector_;
				localtranslate = $translate;
                localLoginService = LoginService;
                localModalInstance = $modal;
                localUtilsService = UtilsService;
                interval = $interval;
				$templateCache.put('views/indexpage/index-tmpl.html', '');
				$templateCache.put('views/indexpage/locale.html', '');
				$templateCache.put('views/indexpage/logo.html', '');
				$templateCache.put('views/indexpage/login.html', '');
				$templateCache.put('views/indexpage/content1.html', '');
				$templateCache.put('views/indexpage/content2.html', '');
				$templateCache.put('views/indexpage/footer.html', '');

                spyOn($rootScope, '$on').andCallThrough();
                spyOn($rootScope, '$broadcast').andCallThrough();
                spyOn(localModalInstance, 'open').andCallThrough();

                $httpBackend.when("GET").respond({});
                httpBackend = $httpBackend;
			});

		});

		it('should respond to URL', function() {
			expect($state.href(state, {
				id : 1
			})).toEqual('#/');
		});

		it('should show the current state with default Locale which is English Language', function() {
			$state.go(state);
			localtranslate.use("en");
			$rootScope.$digest();
			expect($state.current.name).toBe(state);
			expect(localtranslate.preferredLanguage()).toBe('en');
		});

		it('should render German language when selected', function() {
			$.getJSON('../../main/webapp/app/localjsons/i18n/de.json', function(response) {
				if (response.data) {
					var langCode = response.data["locale.languageCode"];
					localtranslate.use(langCode);
					$rootScope.$digest();	
					expect(localtranslate.proposedLanguage()).toBe(langCode);				
				}
			});
		});

		it('should render French language when selected', function() {
			$.getJSON('../../main/webapp/app/localjsons/i18n/fr.json', function(response) {
				if (response.data) {
					var langCode = response.data["locale.languageCode"];
					localtranslate.use(langCode);
					$rootScope.$digest();	
					expect(localtranslate.proposedLanguage()).toBe(langCode);				
				}
			});
		});


        // Login Failure events
        it("Should handle 401:loginRequired", function () {
            var data = {
                code: "401",
                data: null,
                message: "Invalid Username or Password",
                reason: null,
                status: "failure",
                token: null,
                uniqueKey: null
            };
            $rootScope.$broadcast("401:loginRequired", data);
            httpBackend.flush();
            $rootScope.$digest();
            expect($rootScope.$broadcast).toHaveBeenCalledWith("401:loginRequired", data);
            expect($rootScope.$on).toHaveBeenCalled();
            expect(localLoginService.getAuthenticationOrAuthorizationFailed()).toEqual(true);
            expect(localModalInstance.open).toHaveBeenCalled();
        });

        it("Should handle 403:accessDenied", function () {
            var data = {
                code: "403",
                data: null,
                message: "Invalid Username or Password",
                reason: null,
                status: "failure",
                token: null,
                uniqueKey: null
            };
            $rootScope.$broadcast("403:accessDenied", data);
            httpBackend.flush();
            $rootScope.$digest();
            expect($rootScope.$broadcast).toHaveBeenCalledWith("403:accessDenied", data);
            expect($rootScope.$on).toHaveBeenCalled();
            expect(localLoginService.getAuthenticationOrAuthorizationFailed()).toEqual(true);
            expect(localModalInstance.open).toHaveBeenCalled();
        });

        // handle stateChangeStart event
        it('Should handle stateChangeStart', function() {
        	spyOn(localLoginService, 'setStateParameters').andCallThrough();
        	$rootScope.$broadcast("stateChangeStart");
            $rootScope.$digest();
            expect($rootScope.$broadcast).toHaveBeenCalledWith('saveActiveCanvas');
            expect(localLoginService.setStateParameters).toHaveBeenCalled();
        });

        // handle saveActiveCanvas event
        it('Should handle saveActiveCanvas', function() {
        	window.localStorage.FavoriteCanvasListUpdated = '{"newobject":""}';
        	$rootScope.$digest();
        	window.localStorage.FavoriteCanvasListUpdated = '{"newobject2":""}';
        	$rootScope.$digest();
        	spyOn(localUtilsService, 'saveActiveCanvas').andCallThrough();
        	$rootScope.$broadcast("saveActiveCanvas");
            $rootScope.$digest();
            // called due to LS changes
            expect(localUtilsService.saveActiveCanvas).toHaveBeenCalled();
            window.localStorage.FavoriteCanvasListUpdated = '{"newobject3":""}';
        	$rootScope.$digest();
        	interval.flush();
        	// called by $interval
        	expect(localUtilsService.saveActiveCanvas).toHaveBeenCalled();
        });
	});
});
