'use strict';

amdApp.factory('RestApiService', ['$log', 'DomainPath', 'ClientPath', 'ApiPath', 'JsonPath', 'UseLocalJsons', 'Restangular', 'UtilsService', 'LocalStoreService', '$http', 'AuthTokenService', '$q', '$translate', 'EXCEPTION_LEVELS',
    function(log, localDomainPath, localClientPath, localApiPath, localJsonPath, localUseLocalJsons, Restangular, UtilsService, LocalStoreService, http, AuthTokenService, $q, translate, EXCEPTION_LEVELS) {

        var remotePath = localDomainPath + localApiPath;
        var localPath = localClientPath + localJsonPath;

        var restApi = {

            //
            // for handling post requests
            //
            post: function(url, headerMapToBeAddedToExistingHeaders, payloadToBePosted) {

                //perform the post request
                return restApi.sendRequest(url, headerMapToBeAddedToExistingHeaders)
                    .post(payloadToBePosted)
                    .then(function(response) {

                        //if the response wasn't successful, raise a business exception
                        if (response.status !== "success") {
                            restApi.raiseException(response);
                        };

                        //if we are authenticating, we need to add the token to the
                        //response data before we sent it back
                        if(angular.equals(url, "authenticate")) {
                            response.data.token = response.token;
                        }

                        //extract and return the response data
                        console.log(JSON.stringify(response.data));
                        return response.data;

                    },
                    function(error) {
                        restApi.raiseException(error);
                    }
                );
            },


            //
            // for fetching single record
            //
            getOne: function(url, headerMapToBeAddedToExistingHeaders) {

                return restApi.sendRequest(url, headerMapToBeAddedToExistingHeaders).getList()
                    .then(function(response) {

                        //if the response wasn't successful, raise a business exception
                        if (response[0].status !== "success") {
                            restApi.raiseException(response);
                        };

                        //extract and return the response data - the rest of the response is not needed
                        return response[0].data;

                    },
                    function(error) {
                        restApi.raiseException(error);
                    }
                );
            },


            //
            // for fetching all records
            //
            getAll: function(url, headerMapToBeAddedToExistingHeaders) {
                return restApi.sendRequest(url, headerMapToBeAddedToExistingHeaders).getList()
                    .then(function(response) {

                        //if the response wasn't successful, raise a business exception
                        if (response[0].status !== "success") {
                            restApi.raiseException(response);
                        };

                        //extract and return the response data - the rest of the response is not needed
                        return response[0].data;

                    },
                    function(error) {
                        restApi.raiseException(error);
                    }
                );
            },


            //
            // share the common code in a common method
            //
            sendRequest: function(api, headerMapToBeAddedToExistingHeaders) {

                var token = AuthTokenService.getAuthToken().sessionToken;

                //set HTTP headers
                http.defaults.headers.common['Accept-Language'] = translate.use();

                if (token) {
                    http.defaults.headers.common['Authentication-token'] = token;
                }

                if (headerMapToBeAddedToExistingHeaders) {
                    for (var key in headerMapToBeAddedToExistingHeaders) {
                        if (headerMapToBeAddedToExistingHeaders.hasOwnProperty(key)) {
                            http.defaults.headers.common[key] = headerMapToBeAddedToExistingHeaders[key];
                        }
                    }
                }

                //do the API request
                Restangular.setBaseUrl(remotePath);
                Restangular.setRequestSuffix('');

                return Restangular.all(api);
            },

            //
            // Raise a new RestApiException to pass back to the calling function
            //
            raiseException: function(exceptionDetails) {

                //ensure we have the basic exception details. Mostly these will be
                //sent from the server, however in the event which they are not, we
                //should default them
                exceptionDetails.level = (angular.equals(exceptionDetails.level, null))
                    ? EXCEPTION_LEVELS.ERROR
                    : exceptionDetails.level;

                exceptionDetails.explicitDismissal = (angular.equals(exceptionDetails.explicitDismissal, null))
                    ? true
                    : false;

                throw new LucasRestApiException(exceptionDetails);
            }
        };

        return restApi;
    }
]);