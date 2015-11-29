'use strict';

/**
 * Services related to User object.
 */
amdApp.factory('WidgetService', ['$state', '$rootScope', '$q', 'RestApiService', 'LocalStoreService', '$log', '$modal',
    function(state, rootScope, q, restApiService, LocalStoreService, log, modal) {

        return {
            putWidgetInstance : function(instanceObj) {
                var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                if (favoriteCanvasList !== null) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        if (favoriteCanvasList[i].canvasId == activeCanvasId) {
                            if (favoriteCanvasList[i].widgetInstanceList) {
                                favoriteCanvasList[i].widgetInstanceList.push(instanceObj);
                                for (var j = 0; j < favoriteCanvasList[i].widgetInstanceList.length; j++) {
                                    if (favoriteCanvasList[i].widgetInstanceList[j]) {
                                       	var zIndexWidgetInstance = LocalStoreService.getLSItem('zIndexAnchor');
                                        if (zIndexWidgetInstance === favoriteCanvasList[i].widgetInstanceList[j].clientId + '|' + favoriteCanvasList[i].widgetInstanceList[j].widgetDefinition.id) {
                                            favoriteCanvasList[i].widgetInstanceList[j].actualViewConfig.zindex = 1;
                                        } else {
                                            favoriteCanvasList[i].widgetInstanceList[j].actualViewConfig.zindex = 0;
                                        }
                                    }
                                }
                            } else {
                                var activeCanvasDetails = favoriteCanvasList[i];
                                var widgetInstanceList = [];
                                widgetInstanceList.push(instanceObj);
                                activeCanvasDetails['widgetInstanceList'] = widgetInstanceList;
                                favoriteCanvasList[i] = activeCanvasDetails;
                            }
                        }
                    }
                } else {
                    log.warn("widgetArray is null");
                }
                LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);                
                state.transitionTo('canvases.detail', {
	                canvasId: activeCanvasId
	            });
	            rootScope.$broadcast('addNewWidget', instanceObj);
            },
            widgetInstancePost : function(widgetInstance) {
                return restApiService.post('widgetinstances', null, widgetInstance).then(function(response) {
                    if (response.id) {
                        console.log(response.id);
                        return response.id;
                    }
                });
            },
            getWidgetData : function(widgetInstanceObj) {
                console.log('***[WidgetService.getWidgetData]***');

                var promise = null;
                if (widgetInstanceObj.widgetDefinition.dataURL) {
                	//presence of both dataurl.url and dataurl.searchcriteria requires post to fetch data;
                	//example widgets are grid, chart
                	if(widgetInstanceObj.widgetDefinition.dataURL.url && widgetInstanceObj.widgetDefinition.dataURL.searchCriteria){
                		promise = restApiService.post(widgetInstanceObj.widgetDefinition.dataURL.url, null, widgetInstanceObj.widgetDefinition.dataURL.searchCriteria).then(function(widgetInstanceData) {
	                    	return widgetInstanceData;
	                	});
                	}                	
                }
                //if we don't have a promise, create and return
                //a dummy one
                if (!promise) {
                    var def = q.defer();
                    def.resolve(null);
                    return def.promise;
                }

                return promise;
            },
            deleteProperty : function(prop, id) {
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                if (favoriteCanvasList) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        for (var j = 0; j < favoriteCanvasList[i].widgetInstanceList.length; j++) {
                            if (favoriteCanvasList[i].widgetInstanceList[j].clientId === id) {
                                delete favoriteCanvasList[i].widgetInstanceList[j][prop];
                            }
                        }
                    }
                    LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);
                }
            },
            /*addProperty : function(prop, id) {
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                if (favoriteCanvasList) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        for (var j = 0; j < favoriteCanvasList[i].widgetInstanceList.length; j++) {
                            if (favoriteCanvasList[i].widgetInstanceList[j]) {
                                if (favoriteCanvasList[i].widgetInstanceList[j].clientId === id) {
                                    favoriteCanvasList[i].widgetInstanceList[j][prop] = true;
                                }
                            }

                        }
                    }
                    LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);
                }
            },*/
            constructWidgetInstanceObj : function(instanceObj) {
                var zIndexValue;
                var getLocalZindex = LocalStoreService.getLSItem('zIndexAnchor');
                if (getLocalZindex) {
                    if (getLocalZindex === instanceObj.clientId + '|' + instanceObj.widgetDefinition.id) {
                        zIndexValue = 1;
                    } else {
                        zIndexValue = 0;
                    }
                } else {
                    zIndexValue = 0;
                }

                var widgetInstanceObj = {
                	//include here the updated actualViewConfig and append the zIndexValue with that.                    
                    "widgetDefinition" : {
                        "name" : instanceObj.widgetDefinition.name,
                        "id" : instanceObj.widgetDefinition.id
                    },
                    "id" : instanceObj.id,
                    "canvas" : {
                        "canvasName" : LocalStoreService.getLSItem('ActiveCanvasName'),
                        "canvasId" : LocalStoreService.getLSItem('ActiveCanvasId')
                    }
                };
                return widgetInstanceObj;
            },
            updateWidgetInstance : function(widgetInstanceObj) {
               	this.updateFavoriteCanvasListLocalStorage(widgetInstanceObj);
                rootScope.$broadcast('updateExistingWidget', widgetInstanceObj);
            },
            //for deleting a widget instance
            deleteWidgetInstance : function(widgetInstanceObj) {
                var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                var deletedWidgetInstances = [];
                if (favoriteCanvasList !== null) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        if (favoriteCanvasList[i].widgetInstanceList) {
                            for (var j = 0; j < favoriteCanvasList[i].widgetInstanceList.length; j++) {                         
                                if (favoriteCanvasList[i].widgetInstanceList[j].clientId === widgetInstanceObj.clientId) {
                                    return restApiService.post('canvas/widgetinstance/delete', null, {
                                        "canvasId": activeCanvasId,
                                        "widgetInstanceListId": widgetInstanceObj.id
                                    }).then(function(response) {
                                        favoriteCanvasList[i].widgetInstanceList.splice(j, 1);
                                        LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);
                                        rootScope.$emit('RemoveWidget', j, widgetInstanceObj.clientId);
                                        return response;
                                    });
                                }
                            }
                        }
                    }                    
                }
            },
            loadCogMenuPopup : function(widgetInstance) {
                //LocalStoreService.addLSItem(null, 'CogWidgetInstance', widgetInstance);
                var modalInstance = modal.open({
                    templateUrl : 'views/cog/' + widgetInstance.widgetDefinition.name + '.html',
                    controller : 'ShowCogmenuWidgetController',
                    windowClass : 'showCogmenuWidgetModal',
                    backdrop: 'static'
                });
            },
            loadDeleteWidgetPopup: function(widgetInstance) {
                var modalInstance = modal.open({
                    templateUrl : 'views/modals/delete-widget.html',
                    controller : 'DeleteWidgetController',
                    windowClass : 'deleteWidgetModal',
                    backdrop: 'static'
                });
            },
            loadResetWidgetPopup: function(widgetInstance) {
                var modalInstance = modal.open({
                    templateUrl : 'views/modals/reset-widget.html',
                    controller : 'ResetWidgetController',
                    windowClass : 'resetWidgetModal',
                    backdrop: 'static'
                });
            },
           	showUpdatedCanvas : function(isCanvasLevel) {
				var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');				
				var canvasObj = {
					"widgetInstanceList": [],
					"widgetInstanceListDeleted": [],
					"canvasId": 0,
					"canvasName": ""
				};
				if(isCanvasLevel==="true"){
					var activeCanvasarr= [];
					var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
					for (var i = 0; i < favoriteCanvasList.length; i++) {
                        if (favoriteCanvasList[i].canvasId == activeCanvasId) {                        	
                        	
                        	//look for widgetInstanceList with updateWidget set to true; coz those are widgetinstances that are modified
                        	if (favoriteCanvasList[i].widgetInstanceList) {
                                for (var j = 0; j < favoriteCanvasList[i].widgetInstanceList.length; j++) {
                            		if (favoriteCanvasList[i].widgetInstanceList[j].updateWidget) {
                            			canvasObj.widgetInstanceList.push(favoriteCanvasList[i].widgetInstanceList[j]);
                            			canvasObj.canvasName = favoriteCanvasList[i].canvasName;
                            			canvasObj.canvasId = favoriteCanvasList[i].canvasId;
                            		}
                            	}
                            }

                        	//for widgetInstanceListDeleted, always updatewidget is true
                        	if(favoriteCanvasList[i].widgetInstanceListDeleted){
	                        	for (var k = 0; k < favoriteCanvasList[i].widgetInstanceListDeleted.length; k++) {
	                        		canvasObj.widgetInstanceListDeleted = favoriteCanvasList[i].widgetInstanceListDeleted;
	                        		canvasObj.canvasName = favoriteCanvasList[i].canvasName;
	                        		canvasObj.canvasId = favoriteCanvasList[i].canvasId;
	                        	}
                        	}
                        	if(canvasObj.canvasId !==0){
                        		activeCanvasarr.push(canvasObj);
                        		return activeCanvasarr;
                        	}                        	
                        }
                   }                 
				} else if(isCanvasLevel==="false"){
					var canvasArr = [];
					var index = 0;
					for (var i = 0; i < favoriteCanvasList.length; i++) {
	                	canvasObj = favoriteCanvasList[i];
	                	if(!(canvasObj.widgetInstanceListDeleted)){
	                		canvasObj['widgetInstanceListDeleted'] = [];
	                	}
	                	if(!(canvasObj.widgetInstanceList)){
	                		canvasObj['widgetInstanceList'] = [];
	                	}	                	
	                	if(canvasObj.widgetInstanceList.length>0){
	                		for (var j = 0; j < canvasObj.widgetInstanceList.length; j++) {
                        		if (canvasObj.widgetInstanceList[j].updateWidget) {	                               
	                              if(canvasArr[canvasArr.length-1] && canvasArr[canvasArr.length-1].canvasId === canvasObj.canvasId){	                				                			
			                		} else {
			                			canvasArr.push(canvasObj);
			                		}	                               
                        		}
                        	}
	                	}
	                	if(canvasObj.widgetInstanceListDeleted.length>0){	                		
	                		if(canvasArr[canvasArr.length-1] && canvasArr[canvasArr.length-1].canvasId === canvasObj.canvasId){	                				                			
	                		} else {
	                			canvasArr.push(canvasObj);
	                		}
	                	}	                	
                   	}                   	
                   	return canvasArr;               
				}				
           	},
            
            //this function is generic for updating FavoriteCanvasListUpdated with new changes in widgetInstanceObj.
            //it gets and does manipulation and sets it again in LS.
            updateFavoriteCanvasListLocalStorage : function(widgetInstanceObj) {
                var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                if (favoriteCanvasList !== null) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        if (favoriteCanvasList[i].canvasId == activeCanvasId) {
                            for (var widgetInstance in favoriteCanvasList[i].widgetInstanceList) {
                                if (favoriteCanvasList[i].widgetInstanceList[widgetInstance]) {
                                    if (favoriteCanvasList[i].widgetInstanceList[widgetInstance].clientId && widgetInstanceObj.clientId && favoriteCanvasList[i].widgetInstanceList[widgetInstance].clientId == widgetInstanceObj.clientId) {
                                        delete favoriteCanvasList[i].widgetInstanceList[widgetInstance];
                                        favoriteCanvasList[i].widgetInstanceList[widgetInstance] = widgetInstanceObj;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    log.warn("widgetArray is null");
                }
                LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);                
            },
            
            getWidgetInstanceObj: function(ActiveWidgetId){
            	var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                var instanceObj = {};
                if (favoriteCanvasList !== null) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        if (favoriteCanvasList[i].canvasId == activeCanvasId) {
                            for (var widgetInstance in favoriteCanvasList[i].widgetInstanceList) {
                                if (favoriteCanvasList[i].widgetInstanceList[widgetInstance]) {
                                    if (favoriteCanvasList[i].widgetInstanceList[widgetInstance].clientId == ActiveWidgetId) {
                                        instanceObj = favoriteCanvasList[i].widgetInstanceList[widgetInstance];
                                    }
                                }
                            }
                        }
                    }
                } else {
                    log.warn("widgetArray is null");
                }
                return instanceObj;            	
            },
            
            getReactToArrayFromMap: function(reactToMap, listensForList){
            	var arr = [];            	
            	for(var reactKey in reactToMap){            		
            		if((listensForList.indexOf(reactKey) === -1)){
            			arr.push(reactKey);
            		}
            	}
            	return arr;            	
            },

            // updates FavoriteCanvasListUpdated with new changes in widgetInstanceList
            updateFavoriteCanvasListLocalStorageWithWidgetInstanceList : function(widgetInstanceList) {
                var activeCanvasId = LocalStoreService.getLSItem('ActiveCanvasId');
                var favoriteCanvasList = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                if (favoriteCanvasList !== null) {
                    for (var i = 0; i < favoriteCanvasList.length; i++) {
                        if (favoriteCanvasList[i].canvasId == activeCanvasId) {
                            // update the widgetInstanceList based on the activeCanvasId
                            favoriteCanvasList[i].widgetInstanceList = widgetInstanceList;
                        }
                    }
                } else {
                    log.warn("widgetInstanceList is null");
                }
                // Update the LocalStorage with the updated widgetInstanceList
                LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasList);                
            }
        };
    }]);