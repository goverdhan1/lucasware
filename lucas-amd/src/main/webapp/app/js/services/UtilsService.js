/*
 Lucas Systems Inc
 11279 Perry Highway
 Wexford
 PA 15090
 United States


 The information in this file contains trade secrets and confidential
 information which is the property of Lucas Systems Inc.

 All trademarks, trade names, copyrights and other intellectual property
 rights created, developed, embodied in or arising in connection with
 this software shall remain the sole property of Lucas Systems Inc.

 Copyright (c) Lucas Systems, 2014
 ALL RIGHTS RESERVED

 */

'use strict';

amdApp.factory('UtilsService', ['$log', '$rootScope', '$window', '$filter', '$injector',
    function(log, rootScope, ngwindow, filter, $injector) {

        var clientWidgetInstanceId = 99;
        var persistedData = {};

        var utilsService = {

            //
            // Function responsible for raising notifications
            //
            raiseNotification: function(notification) {
                rootScope.$emit('raiseNotification', notification);
            },

            //find an item in an array
            findItemInArray: function (arr, item) {
                return (arr.indexOf(item) != -1);
            },
            //removing an item in array
            removeItemInArray: function (arr, itemArr) {
                var incomingArr = arr;
                for (var i = 0; i < itemArr.length; i++) {
                    for (var index in incomingArr) {
                        if (incomingArr[index] == itemArr[i]) {
                            incomingArr.splice(index, 1);
                            break;
                        }
                    }
                }
                return incomingArr;
            },
            addItemInArray: function (arr, itemArr) {
                for (var i = 0; i < itemArr.length; i++) {
                    var isItemPresent = this.findItemInArray(arr, itemArr[i]);
                    if (!isItemPresent) {
                        arr.push(itemArr[i]);
                    }
                }
                return arr;
            },
            // Util for finding an object by its 'id' property among an array
            findById: function (a, id) {
                for (var i = 0; i < a.length; i++) {
                    //if (a[i].canvasId == id) {
                    if (a[i].id == id) {
                        return a[i];
                    }
                }
                return null;
            },
            //util for finding an index of an object inside an array
            getArrayIndexOfId: function (a, id) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i].id == id) {
                        return i;
                    }
                }
                return null;
            },
            constructPattern: function () {
                var str = "";
                if (arguments[0] && arguments[1]) {//if there is no parameters, return safe exit
                    // using this because join doesn't work for arguments, since arguments isn't an array.
                    //more info http://stackoverflow.com/questions/2091138/why-doesnt-join-work-with-function-arguments
                    var args = Array.prototype.slice.call(arguments);
                    str = args.join("|");
                    return str;
                } else {
                    log.warn("construct pattern requires atleast 2 parameters");
                }
            },
            getObjectByMatchingId: function (a, id) {
                //rename to getObjectByMatchingId
                var arr = [];
                if (a !== null) {
                    for (var i = 0; i < a.length; i++) {
                        if (a[i].id == id) {
                            arr.push(a[i]);
                        }
                    }
                } else {
                    log.warn("array is null");
                }
                return arr[0];
            },
            getObjectProperties: function (obj) {
                var propsObj = {};
                var size = 0;
                for (var property in obj) {
                    propsObj[property] = obj[property];
                    size++;
                }
                ;
                propsObj.length = size;
                return propsObj;
            },
            putItemsInArray: function (key, value, obj) {
                if (obj.hasOwnProperty(key)) {
                    var aValueArray = [];
                    aValueArray = obj[key];
                    if (!this.checkIfSourceArrayContainsAnElementInTargetArray(aValueArray, [value])) {
                        //Add to the array
                        aValueArray.push(value);
                    }
                } else {
                    obj[key] = [value];
                }
                return obj;
            },
            checkIfSourceArrayContainsAnElementInTargetArray: function (sourceArray, targetArray) {
                var found = false;
                for (var i = 0; i < sourceArray.length; i++) {
                    for (var j = 0; j < targetArray.length; j++) {
                        if (sourceArray[i] === targetArray[j]) {
                            found = true;
                            break;
                        }
                    }
                }
                return found;
            },
            showListenItems: function (listenObj, listenConfig) {
                var aMap = {};
                var property, currentProperty;
                for (var j = 0; j < listenConfig.length; j++) {
                    var currentObj = listenObj;
                    var currentConfigValue = listenConfig[j];
                    for (property in currentObj) {
                        if (property == currentConfigValue) {
                            currentProperty = property;
                            aMap[currentProperty] = currentObj[currentProperty];
                        }
                    }
                }
                return aMap;
            },

            //
            // Central function used for widget interactions. This function
            // publishes data on Angular's rootScope object which other widgets
            // listen on. When the event is detected, the other widgets will
            // react to the event (if configured to do so).
            //
            // This function expects a broadcastObject as input
            //
            broadcast: function(broadcastObject) {

                // ensure we have everything we need, otherwise exit
                if (!angular.isDefined(broadcastObject)
                    || broadcastObject.getWidgetId() === null
                    || broadcastObject.getData() === null)
                    return;

                //Make the broadcast the broadcast
                console.log("*** broadcasting from widget " + broadcastObject.getWidgetId());
                rootScope.$emit('WidgetBroadcast', broadcastObject);
            },

            processSelectedItemsAsArray: function (selectedObj, broadcastObj) {
                if (selectedObj) {
                    var aMap = {};
                    for (var i = 0; i < selectedObj.length; i++) {
                        var currentObj = selectedObj[i];
                        for (var j = 0; j < broadcastObj.length; j++) {
                            var property = broadcastObj[j];
                            aMap = this.putItemsInArray(property, currentObj[property], aMap);
                        }
                    }
                    return aMap;
                }
            },
            processBroadcastMap: function (obj, broadcastArr) {
                var objMap = {};
                var objVal = "";
                var objArr = [];
                for (var property in broadcastArr) {
                    objVal = eval("obj." + broadcastArr[property]);
                    objMap[property] = objVal;
                    objArr.push(objMap);
                }
                return objArr;
            },

            formatDate: function (dateString) {
                var dateArr = dateString.split('-');
                //ensure date value has year, month and day in it.
                if (dateArr.length === 3) {
                    var sd = new Date(dateArr[2], parseInt(dateArr[0] - 1, 10), dateArr[1]);
                    var formattedDate = filter("date")(sd, 'yyyy-MM-dd');
                    return formattedDate;
                }
            },
            randomStrings: function (numberOfRows) {
                //var arr = [];
                var data = {
                    "grid": {
                        "values": []
                    }
                };

                for (var i = 0; i < numberOfRows; i++) {
                    //arr.push(Math.random().toString(36).substring(2, 7));
                    data.grid.values.push(Math.random().toString(36).substring(2, 7));
                }
                //return arr;
                return data;
            },
            randomStringsMod: function (numberOfRows) {
                //var arr = [];
                var data = [];

                for (var i = 0; i < numberOfRows; i++) {
                    //arr.push(Math.random().toString(36).substring(2, 7));
                    data.push(Math.random().toString(36).substring(2, 7));
                }
                //return arr;
                return data;
            },
            getClientWidgetInstanceId: function () {
                clientWidgetInstanceId++;
                return clientWidgetInstanceId;
            },
            getRandomHexColor: function () {
                var colorCode = '#' + (Math.random() * Math.PI).toString(16).slice(-6);
                return colorCode;
            },
            //compare two objects; if equals return true; else false
            compareTwoObjects: function (obj1, obj2) {
                var sameObjects = true;
                for (var property in obj1) {
                    if (obj1[property] !== obj2[property]) {
                        sameObjects = false;
                        break;
                    }
                }
                return sameObjects;
            },
            // Util for finding an object by its 'name' property among an array
            findIndexByName: function (a, name) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i].name == name) {
                        return i;
                    }
                }
                return -1;
            },
            // creates a new array by extracting the names
            parseNames: function (source) {
                var names = []
                var name;
                for (name in source) {
                    names.push({name: name});
                }
                return names;
            },
            // creates a new array by extracting the names without sending keys
            parseNamesWithoutKey: function (source) {
                var names = []
                var name;
                for (name in source) {
                    names.push(name);
                }
                return names;
            },

            removeProperty: function (object, propertyName) {
                if (object) {
                    delete object[propertyName];
                }
            },

            removeProperties: function (object, propertyNames) {
                var i, len = propertyNames.length;
                for (i = 0; i < len; i++) {
                    utilsService.removeProperty(object, propertyNames[i]);
                }
            },

            // Util for finding an object by its property among an array
            findIndexByProperty: function (a, propertyName, propertyValue) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i][propertyName] == propertyValue) {
                        return i;;
                    }
                }
                return null;
            },

            // swap the items in an array based on the indexes passed
            swapArrayItems: function(index1, index2, array) {
                var temp = array[index1];
                array[index1] = array[index2];
                array[index2] = temp;
                return array;
            },

            // Capitalize the first letter of all the words present in the string
            ucFirstAllWords: function( str ) {
                var pieces = str.split(" ");
                for ( var i = 0; i < pieces.length; i++ ) {
                    pieces[i] = pieces[i].toLowerCase();
                    var j = pieces[i].charAt(0).toUpperCase();
                    pieces[i] = j + pieces[i].substr(1);
                }
                return pieces.join(" ");
            },

            /**
             * Returns a random integer between min (inclusive) and max (inclusive)
             * Using Math.round() will give you a non-uniform distribution!
             */
            getRandomInt: function(min, max) {
                return Math.floor(Math.random() * (max - min + 1)) + min;
            },

            // helper for generating WidgetInstanceList in saving the active canvas
            getCanvasWithWidgetInstanceList: function(activeCanvas, canvasToSave) {
                var i;
                if (activeCanvas && activeCanvas.widgetInstanceList) {
                    for (i = 0; i < activeCanvas.widgetInstanceList.length; i++) {
                        // set to empty object
                        canvasToSave.widgetInstanceList[i] = {};
                       
                        // widgetInteractionConfig
                        canvasToSave.widgetInstanceList[i].widgetInteractionConfig = activeCanvas.widgetInstanceList[i].widgetInteractionConfig || [];

                        // set the id
                        canvasToSave.widgetInstanceList[i]["id"] = activeCanvas.widgetInstanceList[i].id;

                        // set the actualViewConfig
                        if (activeCanvas.widgetInstanceList[i].actualViewConfig) {
                            canvasToSave.widgetInstanceList[i]["actualViewConfig"] = activeCanvas.widgetInstanceList[i].actualViewConfig;
                        } else {
                            canvasToSave.widgetInstanceList[i]["actualViewConfig"] = {};
                        }

                        // set the position of the widget
                        canvasToSave.widgetInstanceList[i]["actualViewConfig"].position = (i+1);

                        // set minimumWidth
                        if (activeCanvas.widgetInstanceList[i].actualViewConfig.resizedMinimumWidth) {
                            canvasToSave.widgetInstanceList[i]["actualViewConfig"].minimumWidth = activeCanvas.widgetInstanceList[i].actualViewConfig.resizedMinimumWidth;
                        } else {
                            canvasToSave.widgetInstanceList[i]["actualViewConfig"].minimumWidth = activeCanvas.widgetInstanceList[i].actualViewConfig.minimumWidth;
                        }
                        // set minimumHeight
                        if (activeCanvas.widgetInstanceList[i].actualViewConfig.resizedMinimumHeight) {
                            canvasToSave.widgetInstanceList[i]["actualViewConfig"].minimumHeight = activeCanvas.widgetInstanceList[i].actualViewConfig.resizedMinimumHeight;
                        } else {
                            canvasToSave.widgetInstanceList[i]["actualViewConfig"].minimumHeight = activeCanvas.widgetInstanceList[i].actualViewConfig.minimumHeight;
                        }

                        // delete UI only variables, no need to send these to the BE
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].resizedMinimumWidth;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].originalMinimumWidth;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].originalResizedMinimumWidth;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].resizedMinimumHeight;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].originalMinimumHeight;

                        // delete the unncessary properties: reactToList, deviceWidths, sortCriteria, columnDefinitions and gridConfiguration
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].reactToList;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].deviceWidths;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].sortCriteria;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].columnDefinitions;
                        delete canvasToSave.widgetInstanceList[i]["actualViewConfig"].gridConfiguration;

                        // set the widgetDefinition
                        canvasToSave.widgetInstanceList[i]["widgetDefinition"] = {
                            name: activeCanvas.widgetInstanceList[i].widgetDefinition.name,
                            id: activeCanvas.widgetInstanceList[i].widgetDefinition.id
                        };

                        // set the gridColumns
                        canvasToSave.widgetInstanceList[i]["actualViewConfig"].gridColumns = activeCanvas.widgetInstanceList[i].actualViewConfig.gridColumns;

                        // set dataURL if any
                        if (activeCanvas.widgetInstanceList[i].widgetDefinition.dataURL) {
                            canvasToSave.widgetInstanceList[i]["dataURL"] = activeCanvas.widgetInstanceList[i].widgetDefinition.dataURL;
                        }

                        // set the delete to null, will be implemented in the future stories
                        canvasToSave.widgetInstanceList[i]["delete"] = null;

                        // remove autoRefreshConfig as the BE save is not ready
                        // delete canvasToSave.widgetInstanceList[i].actualViewConfig.autoRefreshConfig;
                    }
                }
                return canvasToSave;
            },

            // helper for saving the active canvas - generates the JSON and calls the CanvasService.saveCanvas
            saveActiveCanvas: function(updatedDateTime) {
                // local variables
                var activeCanvasId, activeCanvasIndex, favoriteCanvasListUpdated, activeCanvas, canvasToSave;

                // To avoid circular dependecies use $injector
                var LocalStoreService = $injector.get('LocalStoreService');
                var HomeCanvasListService = $injector.get('HomeCanvasListService');
                var CanvasService = $injector.get('CanvasService');

                // Read from the LocalStorage 
                activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
                favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);
                activeCanvasIndex = HomeCanvasListService.getArrayIndexOfCanvasId(favoriteCanvasListUpdated, activeCanvasId);

                // check if the canvas is active and canvasType is only private
                if (!activeCanvas || (activeCanvas.canvasType && activeCanvas.canvasType.toLowerCase() != 'private')) {
                    return;
                }

                // Save the changes

                /* 
                    Pass: 
                        "canvasId": null - to create new canvas
                        "canvasId": canvasId- to update new canvas

                    NOTE: This covers only updation of the canvas, as the BE for creation of canvas is not implemented yet
                */

                canvasToSave = {
                    "createdByUserName": LocalStoreService.getLSItem("UserName"),
                    "createdDateTime": null,
                    "updatedByUserName": null,
                    "updatedDateTime": updatedDateTime, // this formats to something like "2015-04-06T10:30:30.816Z",
                    "canvasId": activeCanvas.canvasId || null, // in future needs to send null for the newly created canvas case
                    "canvasName": activeCanvas.canvasName,
                    "shortName": activeCanvas.canvasName,
                    "canvasType": activeCanvas.canvasType,
                    "canvasLayout": null,
                    "userSet": null,
                    "widgetInstanceList": []
                };

                // get the widgetInstanceList from the active canvas
                canvasToSave = utilsService.getCanvasWithWidgetInstanceList(activeCanvas, canvasToSave);

                // save the canvas
                return CanvasService.saveCanvas(canvasToSave).then(function(response) {
                    console.log("Canvas saved");
                    if (response && response.widgets && response.widgets.length > 0) {
                        var i;
                        for (i = 0; i < response.widgets.length; i++) {
                            // for the newly created widgets, update the ids
                            if (response.widgets[i] && response.widgets[i].widgetInstanceAction == "create") {
                                if (favoriteCanvasListUpdated[activeCanvasIndex]["widgetInstanceList"]) {
                                    favoriteCanvasListUpdated[activeCanvasIndex]["widgetInstanceList"][i]["id"] = response.widgets[i].widgetInstanceId;
                                    // emit 
                                    rootScope.$emit('updateWidgetId',
                                        favoriteCanvasListUpdated[activeCanvasIndex]["widgetInstanceList"][i]["id"],
                                        favoriteCanvasListUpdated[activeCanvasIndex]["widgetInstanceList"][i]["clientId"]);

                                }
                            }
                        }
                        LocalStoreService.addLSItem(null, 'FavoriteCanvasListUpdated', favoriteCanvasListUpdated);
                    }
                }, function(error) {
                    console.log("error: " + error);
                });
                
            },

            isTouchDevice:function(){
                if( navigator.userAgent.match(/Android/i)
                    || navigator.userAgent.match(/webOS/i)
                    || navigator.userAgent.match(/iPhone/i)
                    || navigator.userAgent.match(/iPad/i)
                    || navigator.userAgent.match(/iPod/i)
                    || navigator.userAgent.match(/BlackBerry/i)
                    || navigator.userAgent.match(/Windows Phone/i)
                    ){
                        return true;
                    }
                    else {
                        return false;
                    }
            },
            // helper for triggering window resize
            // used for automatically sizing the DOM with out reloading the widgets
            triggerWindowResize: function() {
                $(ngwindow).trigger('resize');
            },

             // creates a new array by extracting the specific property
            getProperty: function(source, property) {
                var properties = []
                source.forEach(function(obj, ind) {
                    properties.push(obj[property]);
                });
                return properties;
            },

            //
            // common util function that caches any non-permanent widget data.. for example,
            // when we flip between canvases, we may wish to persist the current state of a form.
            //
            persistWidgetData : function(widgetId, data) {
                persistedData[widgetId] = data;
            },

            //
            // return any temporary cached widget data for the given widget. Returns NULL if no
            // data has been cached.
            //
            getPersistedWidgetData : function(widgetId) {
                if(persistedData.hasOwnProperty(widgetId))
                    return persistedData[widgetId];
                else
                    return null;
            },

            //
            // clears all temporary widget data help within the app memory. Called when a user logs out
            //
            clearAllWidgetData : function() {
                persistedData = {};
            },

            // sets the child properties with the given value
            setAllChildProperties: function(source, value) {
                angular.forEach(source, function(val, key) {
                    console.log(key, val);
                    source[key] = value;
                });
                return source;
            },

             /*
             *
             * Returns the widget details( id and name) for the widgets based on the active canvas
             * All the widgets other than the current widgetId are returned
             *
             */
            getWidgetDetailsOfActiveCanvas: function(widgetId, widgetNames) {
                // local variables
                var activeCanvasId,
                    favoriteCanvasListUpdated,
                    activeCanvas,
                    // To avoid circular dependecies use $injector
                    LocalStoreService = $injector.get('LocalStoreService'),
                    HomeCanvasListService = $injector.get('HomeCanvasListService'),
                    CanvasService = $injector.get('CanvasService'),
                    i,
                    widgetDetails = [],
                    id;

                // Read from the LocalStorage 
                activeCanvasId = LocalStoreService.getLSItem("ActiveCanvasId");
                favoriteCanvasListUpdated = LocalStoreService.getLSItem('FavoriteCanvasListUpdated');
                activeCanvas = HomeCanvasListService.findByCanvasId(favoriteCanvasListUpdated, activeCanvasId);

                // proceed only if there is an active canvas
                if (activeCanvas && activeCanvas.widgetInstanceList) {
                    for (i = 0; i < activeCanvas.widgetInstanceList.length; i++) {
                        // if the widgets present on the current canvas does not match the list of widget names,
                        // then we dont have to add them
                        
                        id = activeCanvas.widgetInstanceList[i].id;
                        if (id != widgetId &&
                            widgetNames.length &&
                            widgetNames.indexOf(activeCanvas.widgetInstanceList[i].widgetDefinition.shortName) != -1) {
                            // add the object to widgetDetails
                            widgetDetails.push({
                                id: "" + activeCanvas.widgetInstanceList[i].id,
                                name: activeCanvas.widgetInstanceList[i].widgetDefinition.shortName + ", " + id
                            });
                        }
                    }

                }
                return widgetDetails;
            }
        };
        return utilsService;
    }]);
