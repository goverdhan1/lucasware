'use strict';

describe('Logout Confirmation Modal Controller Tests', function() {

	var localController = null;
	var localScope = null;
	var localModalInstance = null;
	var localWidgetService = null;
	var localLocalStoreService = null;
	var dirtyCanvas = [{
		"canvasName" : "Pick Monitoring Canvas",
		"widgetInstanceList" : [{
			"data" : [{
				"chart" : [{
					"key" : "Completed",
					"color" : "#d62728",
					"values" : [{
						"label" : "Wave1",
						"value" : "300"
					}, {
						"label" : "Wave2",
						"value" : "455"
					}, {
						"label" : "Wave3",
						"value" : "367"
					}, {
						"label" : "Wave4",
						"value" : "407"
					}]
				}, {
					"key" : "Total",
					"color" : "#1f77b4",
					"values" : [{
						"label" : "Wave1",
						"value" : "350"
					}, {
						"label" : "Wave2",
						"value" : "470"
					}, {
						"label" : "Wave3",
						"value" : "390"
					}, {
						"label" : "Wave4",
						"value" : "459"
					}]
				}]
			}],
			"id" : null,
			"widgetDefinition" : {
				"id" : 10,
				"name" : "pickline-by-wave-barchart-widget",
				"shortName" : "PicklineByWave",
				"type" : "GRAPH_2D",
				"subtype" : "CHART_BAR",
				"broadCastMap" : {
					"Completed" : "series.key",
					"Wave" : "point.label"
				},
				"dataURL" : {
					"url" : "/waves/picklines",
					"searchcriteria" : {
						"searchmap" : {}
					}
				},
				"actualViewConfig" : {
					"anchor" : [0, 0],
					"height" : 500,
					"width" : 600,
					"zindex" : 0
				},
				"listensForList" : ["Area", "Wave", "Score"]
			},
			"actualViewConfig" : {
				"anchor" : [0, 0],
				"height" : 500,
				"width" : 600,
				"zindex" : 0
			},
			"updateWidget" : true,
			"clientId" : 104,
			"isMaximized" : false
		}],
		"canvasId" : 5
	}, {
		"canvasName" : "User Management Canvas",
		"widgetInstanceList" : [{
			"data" : [{
				"chart" : [{
					"key" : "Completed",
					"color" : "#d62728",
					"values" : [{
						"label" : "Wave1",
						"value" : "300"
					}, {
						"label" : "Wave2",
						"value" : "455"
					}, {
						"label" : "Wave3",
						"value" : "367"
					}, {
						"label" : "Wave4",
						"value" : "407"
					}]
				}, {
					"key" : "Total",
					"color" : "#1f77b4",
					"values" : [{
						"label" : "Wave1",
						"value" : "350"
					}, {
						"label" : "Wave2",
						"value" : "470"
					}, {
						"label" : "Wave3",
						"value" : "390"
					}, {
						"label" : "Wave4",
						"value" : "459"
					}]
				}]
			}],
			"id" : null,
			"widgetDefinition" : {
				"id" : 10,
				"name" : "pickline-by-wave-barchart-widget",
				"shortName" : "PicklineByWave",
				"type" : "GRAPH_2D",
				"subtype" : "CHART_BAR",
				"broadCastMap" : {
					"Completed" : "series.key",
					"Wave" : "point.label"
				},
				"dataURL" : {
					"url" : "/waves/picklines",
					"searchcriteria" : {
						"searchmap" : {}
					}
				},
				"actualViewConfig" : {
					"anchor" : [0, 0],
					"height" : 500,
					"width" : 600,
					"zindex" : 0
				},
				"listensForList" : ["Area", "Wave", "Score"]
			},
			"actualViewConfig" : {
				"anchor" : [0, 0],
				"height" : 500,
				"width" : 600,
				"zindex" : 0
			},
			"updateWidget" : true,
			"clientId" : 100,
			"isMaximized" : false
		}, {
			"data" : [{
				"chart" : [{
					"key" : "Completed",
					"color" : "#d62728",
					"values" : [{
						"label" : "Wave1",
						"value" : "300"
					}, {
						"label" : "Wave2",
						"value" : "455"
					}, {
						"label" : "Wave3",
						"value" : "367"
					}, {
						"label" : "Wave4",
						"value" : "407"
					}]
				}, {
					"key" : "Total",
					"color" : "#1f77b4",
					"values" : [{
						"label" : "Wave1",
						"value" : "350"
					}, {
						"label" : "Wave2",
						"value" : "470"
					}, {
						"label" : "Wave3",
						"value" : "390"
					}, {
						"label" : "Wave4",
						"value" : "459"
					}]
				}]
			}],
			"id" : null,
			"widgetDefinition" : {
				"id" : 11,
				"name" : "assignment-management-piechart-widget",
				"shortName" : "PicklineByWave",
				"type" : "GRAPH_2D",
				"subtype" : "CHART_BAR",
				"broadCastMap" : {
					"Completed" : "series.key",
					"Wave" : "point.label"
				},
				"dataURL" : {
					"url" : "/waves/picklines",
					"searchcriteria" : {
						"searchmap" : {}
					}
				},
				"actualViewConfig" : {
					"anchor" : [0, 0],
					"height" : 500,
					"width" : 600,
					"zindex" : 0
				},
				"listensForList" : ["Area", "Wave", "Score"]
			},
			"actualViewConfig" : {
				"anchor" : [30, 384],
				"height" : 500,
				"width" : 600,
				"zindex" : 1
			},
			"updateWidget" : true,
			"clientId" : 101,
			"isMaximized" : false
		}, {
			"data" : [],
			"id" : null,
			"widgetDefinition" : {
				"id" : 8,
				"name" : "create-or-edit-user-form-widget",
				"shortName" : "CreateUser",
				"widgetActionConfig" : {
					"create-assignment" : false,
					"view-report-productivity" : false,
					"configure-location" : false,
					"delete-canvas" : false,
					"create-canvas" : false
				},
				"broadCastMap" : {
					"Area" : "Series.key",
					"Route" : "point.label"
				},
				"listensForList" : ["Area", "Shift", "Picker"],
				"definitionData" : {
					"User" : [{
						"startDate" : "2014-09-30T16:40:02.362Z"
					}],
					"handheldScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"amdScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"jenniferToUserLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"userToJenniferLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"]
				},
				"actualViewConfig" : {
					"height" : 375,
					"width" : 485,
					"anchor" : [0, 863],
					"zindex" : 0
				}
			},
			"actualViewConfig" : {
				"height" : 375,
				"width" : 485,
				"anchor" : [0, 863],
				"zindex" : 0
			},
			"updateWidget" : true,
			"clientId" : 102,
			"isMaximized" : false
		}, {
			"data" : {
				"sortInfo" : {
					"fields" : ["birthDate", "emailAddress", "employeeId", "firstName", "lastName", "mobileNumber", "needsAuthentication", "preferredLanguage", "startDate", "title", "userId", "userName"],
					"directions" : ["asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc"]
				},
				"colDefs" : [{
					"field" : "emailAddress",
					"displayName" : "Email Address",
					"visible" : true
				}, {
					"field" : "firstName",
					"displayName" : "First Name",
					"visible" : true
				}, {
					"field" : "lastName",
					"displayName" : "Last Name",
					"visible" : true
				}, {
					"field" : "startDate",
					"displayName" : "Start Date",
					"visible" : true
				}, {
					"field" : "userName",
					"displayName" : "User Name",
					"visible" : true
				}],
				"data" : [{
					"birthDate" : "a3zza",
					"emailAddress" : "f4p0e",
					"employeeId" : "hkehl",
					"firstName" : "jxz7i",
					"lastName" : "05bti",
					"mobileNumber" : "nwoov",
					"needsAuthentication" : "eeyel",
					"preferredLanguage" : "c5oxs",
					"startDate" : "0wy5t",
					"title" : "sazq0",
					"userId" : "wo1gl",
					"userName" : "p3ogx"
				}, {
					"birthDate" : "ayb2f",
					"emailAddress" : "0o0wn",
					"employeeId" : "n1gbz",
					"firstName" : "0k69f",
					"lastName" : "7yf7w",
					"mobileNumber" : "inukx",
					"needsAuthentication" : "60f7y",
					"preferredLanguage" : "84htz",
					"startDate" : "j0052",
					"title" : "c8cvz",
					"userId" : "dwaht",
					"userName" : "o0ctx"
				}, {
					"birthDate" : "jp25e",
					"emailAddress" : "lxq4v",
					"employeeId" : "uezvb",
					"firstName" : "jz0zr",
					"lastName" : "7g9vo",
					"mobileNumber" : "y2ysk",
					"needsAuthentication" : "k5ml9",
					"preferredLanguage" : "wd1an",
					"startDate" : "yskvc",
					"title" : "sfrsw",
					"userId" : "xsqca",
					"userName" : "0zh1a"
				}, {
					"birthDate" : "bb0va",
					"emailAddress" : "k1qnm",
					"employeeId" : "xmfwh",
					"firstName" : "rdzvs",
					"lastName" : "l3gry",
					"mobileNumber" : "rqits",
					"needsAuthentication" : "xhisw",
					"preferredLanguage" : "eunm6",
					"startDate" : "ulkeh",
					"title" : "t00ws",
					"userId" : "awfib",
					"userName" : "47sub"
				}, {
					"birthDate" : "wkztp",
					"emailAddress" : "4iak3",
					"employeeId" : "rj4ue",
					"firstName" : "16yr2",
					"lastName" : "0blai",
					"mobileNumber" : "x044s",
					"needsAuthentication" : "9aktr",
					"preferredLanguage" : "j5vz8",
					"startDate" : "leito",
					"title" : "g2yoy",
					"userId" : "8q0yk",
					"userName" : "9ccfy"
				}, {
					"birthDate" : "ivl5v",
					"emailAddress" : "6m7yb",
					"employeeId" : "msgn8",
					"firstName" : "tfjfp",
					"lastName" : "jy7kj",
					"mobileNumber" : "y9mmk",
					"needsAuthentication" : "eg2o1",
					"preferredLanguage" : "5wgtt",
					"startDate" : "1ts0m",
					"title" : "2kye4",
					"userId" : "j0sq8",
					"userName" : "9oaby"
				}, {
					"birthDate" : "184d2",
					"emailAddress" : "qw6fn",
					"employeeId" : "c3ion",
					"firstName" : "u0coo",
					"lastName" : "bs0j7",
					"mobileNumber" : "ufcez",
					"needsAuthentication" : "uz6eu",
					"preferredLanguage" : "ty4xw",
					"startDate" : "j7yea",
					"title" : "lha32",
					"userId" : "6h6dm",
					"userName" : "pulpp"
				}, {
					"birthDate" : "7a3gg",
					"emailAddress" : "pjec4",
					"employeeId" : "ppn5a",
					"firstName" : "njd8e",
					"lastName" : "f03mr",
					"mobileNumber" : "1fjjz",
					"needsAuthentication" : "jod77",
					"preferredLanguage" : "8rc7l",
					"startDate" : "26q4s",
					"title" : "vacho",
					"userId" : "rc5pd",
					"userName" : "yu8g2"
				}, {
					"birthDate" : "9fkrm",
					"emailAddress" : "abqta",
					"employeeId" : "02a8q",
					"firstName" : "k05v0",
					"lastName" : "1s3t3",
					"mobileNumber" : "umejk",
					"needsAuthentication" : "pkkyb",
					"preferredLanguage" : "cutic",
					"startDate" : "5a0sa",
					"title" : "4wy0g",
					"userId" : "zxf01",
					"userName" : "bo76y"
				}, {
					"birthDate" : "qo14a",
					"emailAddress" : "j11l7",
					"employeeId" : "9i07t",
					"firstName" : "3z9s6",
					"lastName" : "s8glt",
					"mobileNumber" : "bpxf5",
					"needsAuthentication" : "ojaf4",
					"preferredLanguage" : "dro4l",
					"startDate" : "8h0iz",
					"title" : "vnmn9",
					"userId" : "oospw",
					"userName" : "pvwcy"
				}, {
					"birthDate" : "ut4eg",
					"emailAddress" : "tewk8",
					"employeeId" : "zac67",
					"firstName" : "ydljq",
					"lastName" : "k5ies",
					"mobileNumber" : "80vfk",
					"needsAuthentication" : "56xru",
					"preferredLanguage" : "thznc",
					"startDate" : "ca50i",
					"title" : "3kcfs",
					"userId" : "qjsei",
					"userName" : "dvrwi"
				}, {
					"birthDate" : "1itoy",
					"emailAddress" : "1s064",
					"employeeId" : "5rn0e",
					"firstName" : "dv5sh",
					"lastName" : "fggio",
					"mobileNumber" : "vck43",
					"needsAuthentication" : "wtuav",
					"preferredLanguage" : "i6p59",
					"startDate" : "0c2ry",
					"title" : "t8c9s",
					"userId" : "b0523",
					"userName" : "mxenn"
				}, {
					"birthDate" : "4zaf3",
					"emailAddress" : "i2c86",
					"employeeId" : "n7tsy",
					"firstName" : "24lz7",
					"lastName" : "p9cj0",
					"mobileNumber" : "zccyr",
					"needsAuthentication" : "e1l9w",
					"preferredLanguage" : "yybus",
					"startDate" : "6e9vs",
					"title" : "cyol3",
					"userId" : "bqsfq",
					"userName" : "xpg7h"
				}, {
					"birthDate" : "tu71k",
					"emailAddress" : "extoi",
					"employeeId" : "tag8x",
					"firstName" : "9r87a",
					"lastName" : "9yw7z",
					"mobileNumber" : "rp7ri",
					"needsAuthentication" : "nxog6",
					"preferredLanguage" : "zbwun",
					"startDate" : "mla77",
					"title" : "6uvsu",
					"userId" : "268lz",
					"userName" : "361d6"
				}, {
					"birthDate" : "gyygt",
					"emailAddress" : "17ayu",
					"employeeId" : "ch0om",
					"firstName" : "xetxk",
					"lastName" : "ah0o2",
					"mobileNumber" : "ivahx",
					"needsAuthentication" : "atfqe",
					"preferredLanguage" : "du8g6",
					"startDate" : "yj8sm",
					"title" : "xqult",
					"userId" : "4934f",
					"userName" : "yjdut"
				}, {
					"birthDate" : "7qwie",
					"emailAddress" : "glmqw",
					"employeeId" : "x7hc1",
					"firstName" : "99sjn",
					"lastName" : "uqqxq",
					"mobileNumber" : "tas75",
					"needsAuthentication" : "2abhq",
					"preferredLanguage" : "ymh06",
					"startDate" : "9tple",
					"title" : "rczfj",
					"userId" : "e4e03",
					"userName" : "2o8ms"
				}, {
					"birthDate" : "dtzae",
					"emailAddress" : "2dpjq",
					"employeeId" : "k0qwr",
					"firstName" : "iygq6",
					"lastName" : "ghl3p",
					"mobileNumber" : "1wetx",
					"needsAuthentication" : "jr2i7",
					"preferredLanguage" : "niuot",
					"startDate" : "b6xfo",
					"title" : "x42k3",
					"userId" : "1bcsm",
					"userName" : "0vl9f"
				}, {
					"birthDate" : "4fza0",
					"emailAddress" : "juan0",
					"employeeId" : "9rmqw",
					"firstName" : "a6q8p",
					"lastName" : "exno2",
					"mobileNumber" : "ofiii",
					"needsAuthentication" : "oqbmx",
					"preferredLanguage" : "cb88s",
					"startDate" : "419v7",
					"title" : "pozzj",
					"userId" : "m3bsl",
					"userName" : "avx4l"
				}, {
					"birthDate" : "ih81j",
					"emailAddress" : "4w8xc",
					"employeeId" : "x6wj1",
					"firstName" : "nmmll",
					"lastName" : "sgmo7",
					"mobileNumber" : "peejy",
					"needsAuthentication" : "2xt4y",
					"preferredLanguage" : "glpob",
					"startDate" : "brezg",
					"title" : "8pu3o",
					"userId" : "qx9mz",
					"userName" : "qkbsm"
				}, {
					"birthDate" : "402th",
					"emailAddress" : "15af3",
					"employeeId" : "g6a29",
					"firstName" : "eovm6",
					"lastName" : "tdgvm",
					"mobileNumber" : "q410f",
					"needsAuthentication" : "wdayx",
					"preferredLanguage" : "jo0i3",
					"startDate" : "xfofx",
					"title" : "4t2y9",
					"userId" : "5xay3",
					"userName" : "q66sy"
				}, {
					"birthDate" : "dhuce",
					"emailAddress" : "xyim5",
					"employeeId" : "y2saa",
					"firstName" : "1qd4a",
					"lastName" : "rnq8a",
					"mobileNumber" : "36rka",
					"needsAuthentication" : "9xlbs",
					"preferredLanguage" : "kvvw6",
					"startDate" : "9g873",
					"title" : "c0pia",
					"userId" : "loyws",
					"userName" : "hm8dq"
				}, {
					"birthDate" : "6bc2q",
					"emailAddress" : "wdnx7",
					"employeeId" : "yo05l",
					"firstName" : "8u124",
					"lastName" : "accii",
					"mobileNumber" : "7tbk8",
					"needsAuthentication" : "xywyj",
					"preferredLanguage" : "baymf",
					"startDate" : "57b4a",
					"title" : "zcmb2",
					"userId" : "cva5y",
					"userName" : "d1s0u"
				}, {
					"birthDate" : "6nduq",
					"emailAddress" : "j9cmn",
					"employeeId" : "a2d7r",
					"firstName" : "23z3l",
					"lastName" : "wr06u",
					"mobileNumber" : "al0ur",
					"needsAuthentication" : "jkzeq",
					"preferredLanguage" : "2svgk",
					"startDate" : "tatta",
					"title" : "8su6z",
					"userId" : "6a1sl",
					"userName" : "lht61"
				}, {
					"birthDate" : "7mas1",
					"emailAddress" : "3do4z",
					"employeeId" : "fq25m",
					"firstName" : "13z5q",
					"lastName" : "a21wz",
					"mobileNumber" : "5qyru",
					"needsAuthentication" : "1ys9z",
					"preferredLanguage" : "37mj7",
					"startDate" : "i5y8n",
					"title" : "b6pi3",
					"userId" : "20z1w",
					"userName" : "76o1q"
				}, {
					"birthDate" : "c6hqf",
					"emailAddress" : "h1odc",
					"employeeId" : "4qykz",
					"firstName" : "ip35c",
					"lastName" : "yl4x1",
					"mobileNumber" : "z1s8j",
					"needsAuthentication" : "28o8f",
					"preferredLanguage" : "0xmxv",
					"startDate" : "dgtvv",
					"title" : "1c2x2",
					"userId" : "wpm3j",
					"userName" : "6khry"
				}]
			},
			"id" : null,
			"widgetDefinition" : {
				"name" : "search-user-grid-widget",
				"id" : 9,
				"shortName" : "SearchUser",
				"widgetActionConfig" : {},
				"defaultData" : {},
				"title" : "Search User",
				"actualViewConfig" : {
					"height" : 375,
					"width" : 485,
					"dateFormat" : {
						"selectedFormat" : "mm-dd-yyyy",
						"availableFormats" : ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
					},
					"anchor" : [1, 363],
					"zindex" : 0,
					"gridColumns" : {
						"1" : {
							"name" : "Birth Date",
							"fieldName" : "birthDate",
							"visible" : false,
							"order" : "1",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["a3zza", "ayb2f", "jp25e", "bb0va", "wkztp", "ivl5v", "184d2", "7a3gg", "9fkrm", "qo14a", "ut4eg", "1itoy", "4zaf3", "tu71k", "gyygt", "7qwie", "dtzae", "4fza0", "ih81j", "402th", "dhuce", "6bc2q", "6nduq", "7mas1", "c6hqf"]
								}
							}
						},
						"2" : {
							"name" : "Email Address",
							"fieldName" : "emailAddress",
							"visible" : true,
							"order" : 1,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["f4p0e", "0o0wn", "lxq4v", "k1qnm", "4iak3", "6m7yb", "qw6fn", "pjec4", "abqta", "j11l7", "tewk8", "1s064", "i2c86", "extoi", "17ayu", "glmqw", "2dpjq", "juan0", "4w8xc", "15af3", "xyim5", "wdnx7", "j9cmn", "3do4z", "h1odc"]
								}
							}
						},
						"3" : {
							"name" : "Employee Id",
							"fieldName" : "employeeId",
							"visible" : false,
							"order" : "3",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["hkehl", "n1gbz", "uezvb", "xmfwh", "rj4ue", "msgn8", "c3ion", "ppn5a", "02a8q", "9i07t", "zac67", "5rn0e", "n7tsy", "tag8x", "ch0om", "x7hc1", "k0qwr", "9rmqw", "x6wj1", "g6a29", "y2saa", "yo05l", "a2d7r", "fq25m", "4qykz"]
								}
							}
						},
						"4" : {
							"name" : "First Name",
							"fieldName" : "firstName",
							"visible" : true,
							"order" : 2,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["jxz7i", "0k69f", "jz0zr", "rdzvs", "16yr2", "tfjfp", "u0coo", "njd8e", "k05v0", "3z9s6", "ydljq", "dv5sh", "24lz7", "9r87a", "xetxk", "99sjn", "iygq6", "a6q8p", "nmmll", "eovm6", "1qd4a", "8u124", "23z3l", "13z5q", "ip35c"]
								}
							}
						},
						"5" : {
							"name" : "Last Name",
							"fieldName" : "lastName",
							"visible" : true,
							"order" : 3,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["05bti", "7yf7w", "7g9vo", "l3gry", "0blai", "jy7kj", "bs0j7", "f03mr", "1s3t3", "s8glt", "k5ies", "fggio", "p9cj0", "9yw7z", "ah0o2", "uqqxq", "ghl3p", "exno2", "sgmo7", "tdgvm", "rnq8a", "accii", "wr06u", "a21wz", "yl4x1"]
								}
							}
						},
						"6" : {
							"name" : "Mobile Number",
							"fieldName" : "mobileNumber",
							"visible" : false,
							"order" : "7",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["nwoov", "inukx", "y2ysk", "rqits", "x044s", "y9mmk", "ufcez", "1fjjz", "umejk", "bpxf5", "80vfk", "vck43", "zccyr", "rp7ri", "ivahx", "tas75", "1wetx", "ofiii", "peejy", "q410f", "36rka", "7tbk8", "al0ur", "5qyru", "z1s8j"]
								}
							}
						},
						"7" : {
							"name" : "Needs Authentication",
							"fieldName" : "needsAuthentication",
							"visible" : false,
							"order" : "8",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["eeyel", "60f7y", "k5ml9", "xhisw", "9aktr", "eg2o1", "uz6eu", "jod77", "pkkyb", "ojaf4", "56xru", "wtuav", "e1l9w", "nxog6", "atfqe", "2abhq", "jr2i7", "oqbmx", "2xt4y", "wdayx", "9xlbs", "xywyj", "jkzeq", "1ys9z", "28o8f"]
								}
							}
						},
						"8" : {
							"name" : "Preferred Language",
							"fieldName" : "preferredLanguage",
							"visible" : false,
							"order" : "9",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["c5oxs", "84htz", "wd1an", "eunm6", "j5vz8", "5wgtt", "ty4xw", "8rc7l", "cutic", "dro4l", "thznc", "i6p59", "yybus", "zbwun", "du8g6", "ymh06", "niuot", "cb88s", "glpob", "jo0i3", "kvvw6", "baymf", "2svgk", "37mj7", "0xmxv"]
								}
							}
						},
						"9" : {
							"name" : "Start Date",
							"fieldName" : "startDate",
							"visible" : true,
							"order" : 4,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["0wy5t", "j0052", "yskvc", "ulkeh", "leito", "1ts0m", "j7yea", "26q4s", "5a0sa", "8h0iz", "ca50i", "0c2ry", "6e9vs", "mla77", "yj8sm", "9tple", "b6xfo", "419v7", "brezg", "xfofx", "9g873", "57b4a", "tatta", "i5y8n", "dgtvv"]
								}
							}
						},
						"10" : {
							"name" : "Title",
							"fieldName" : "title",
							"visible" : false,
							"order" : "11",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["sazq0", "c8cvz", "sfrsw", "t00ws", "g2yoy", "2kye4", "lha32", "vacho", "4wy0g", "vnmn9", "3kcfs", "t8c9s", "cyol3", "6uvsu", "xqult", "rczfj", "x42k3", "pozzj", "8pu3o", "4t2y9", "c0pia", "zcmb2", "8su6z", "b6pi3", "1c2x2"]
								}
							}
						},
						"11" : {
							"name" : "User Id",
							"fieldName" : "userId",
							"visible" : false,
							"order" : "12",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["wo1gl", "dwaht", "xsqca", "awfib", "8q0yk", "j0sq8", "6h6dm", "rc5pd", "zxf01", "oospw", "qjsei", "b0523", "bqsfq", "268lz", "4934f", "e4e03", "1bcsm", "m3bsl", "qx9mz", "5xay3", "loyws", "cva5y", "6a1sl", "20z1w", "wpm3j"]
								}
							}
						},
						"12" : {
							"name" : "User Name",
							"fieldName" : "userName",
							"visible" : true,
							"order" : 5,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["p3ogx", "o0ctx", "0zh1a", "47sub", "9ccfy", "9oaby", "pulpp", "yu8g2", "bo76y", "pvwcy", "dvrwi", "mxenn", "xpg7h", "361d6", "yjdut", "2o8ms", "0vl9f", "avx4l", "qkbsm", "q66sy", "hm8dq", "d1s0u", "lht61", "76o1q", "6khry"]
								}
							}
						}
					}
				},
				"listensForList" : ["regionId", "warehouseId"],
				"broadCastMap" : {
					"username" : "username"
				},
				"dataURL" : {
					"url" : "/users/userlist/search",
					"searchCriteria" : {
						"searchMap" : {},
						"sortMap" : {},
						"pageSize" : "10",
						"pageNumber" : "0"
					},
					"searchcriteria" : {
						"pagesize" : "100",
						"pagenumber" : "0",
						"searchmap" : {}
					}
				}
			},
			"actualViewConfig" : {
				"height" : 375,
				"width" : 485,
				"dateFormat" : {
					"selectedFormat" : "mm-dd-yyyy",
					"availableFormats" : ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
				},
				"anchor" : [1, 363],
				"zindex" : 0,
				"gridColumns" : {
					"1" : {
						"name" : "Birth Date",
						"fieldName" : "birthDate",
						"visible" : false,
						"order" : "1",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["a3zza", "ayb2f", "jp25e", "bb0va", "wkztp", "ivl5v", "184d2", "7a3gg", "9fkrm", "qo14a", "ut4eg", "1itoy", "4zaf3", "tu71k", "gyygt", "7qwie", "dtzae", "4fza0", "ih81j", "402th", "dhuce", "6bc2q", "6nduq", "7mas1", "c6hqf"]
							}
						}
					},
					"2" : {
						"name" : "Email Address",
						"fieldName" : "emailAddress",
						"visible" : true,
						"order" : 1,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["f4p0e", "0o0wn", "lxq4v", "k1qnm", "4iak3", "6m7yb", "qw6fn", "pjec4", "abqta", "j11l7", "tewk8", "1s064", "i2c86", "extoi", "17ayu", "glmqw", "2dpjq", "juan0", "4w8xc", "15af3", "xyim5", "wdnx7", "j9cmn", "3do4z", "h1odc"]
							}
						}
					},
					"3" : {
						"name" : "Employee Id",
						"fieldName" : "employeeId",
						"visible" : false,
						"order" : "3",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["hkehl", "n1gbz", "uezvb", "xmfwh", "rj4ue", "msgn8", "c3ion", "ppn5a", "02a8q", "9i07t", "zac67", "5rn0e", "n7tsy", "tag8x", "ch0om", "x7hc1", "k0qwr", "9rmqw", "x6wj1", "g6a29", "y2saa", "yo05l", "a2d7r", "fq25m", "4qykz"]
							}
						}
					},
					"4" : {
						"name" : "First Name",
						"fieldName" : "firstName",
						"visible" : true,
						"order" : 2,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["jxz7i", "0k69f", "jz0zr", "rdzvs", "16yr2", "tfjfp", "u0coo", "njd8e", "k05v0", "3z9s6", "ydljq", "dv5sh", "24lz7", "9r87a", "xetxk", "99sjn", "iygq6", "a6q8p", "nmmll", "eovm6", "1qd4a", "8u124", "23z3l", "13z5q", "ip35c"]
							}
						}
					},
					"5" : {
						"name" : "Last Name",
						"fieldName" : "lastName",
						"visible" : true,
						"order" : 3,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["05bti", "7yf7w", "7g9vo", "l3gry", "0blai", "jy7kj", "bs0j7", "f03mr", "1s3t3", "s8glt", "k5ies", "fggio", "p9cj0", "9yw7z", "ah0o2", "uqqxq", "ghl3p", "exno2", "sgmo7", "tdgvm", "rnq8a", "accii", "wr06u", "a21wz", "yl4x1"]
							}
						}
					},
					"6" : {
						"name" : "Mobile Number",
						"fieldName" : "mobileNumber",
						"visible" : false,
						"order" : "7",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["nwoov", "inukx", "y2ysk", "rqits", "x044s", "y9mmk", "ufcez", "1fjjz", "umejk", "bpxf5", "80vfk", "vck43", "zccyr", "rp7ri", "ivahx", "tas75", "1wetx", "ofiii", "peejy", "q410f", "36rka", "7tbk8", "al0ur", "5qyru", "z1s8j"]
							}
						}
					},
					"7" : {
						"name" : "Needs Authentication",
						"fieldName" : "needsAuthentication",
						"visible" : false,
						"order" : "8",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["eeyel", "60f7y", "k5ml9", "xhisw", "9aktr", "eg2o1", "uz6eu", "jod77", "pkkyb", "ojaf4", "56xru", "wtuav", "e1l9w", "nxog6", "atfqe", "2abhq", "jr2i7", "oqbmx", "2xt4y", "wdayx", "9xlbs", "xywyj", "jkzeq", "1ys9z", "28o8f"]
							}
						}
					},
					"8" : {
						"name" : "Preferred Language",
						"fieldName" : "preferredLanguage",
						"visible" : false,
						"order" : "9",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["c5oxs", "84htz", "wd1an", "eunm6", "j5vz8", "5wgtt", "ty4xw", "8rc7l", "cutic", "dro4l", "thznc", "i6p59", "yybus", "zbwun", "du8g6", "ymh06", "niuot", "cb88s", "glpob", "jo0i3", "kvvw6", "baymf", "2svgk", "37mj7", "0xmxv"]
							}
						}
					},
					"9" : {
						"name" : "Start Date",
						"fieldName" : "startDate",
						"visible" : true,
						"order" : 4,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["0wy5t", "j0052", "yskvc", "ulkeh", "leito", "1ts0m", "j7yea", "26q4s", "5a0sa", "8h0iz", "ca50i", "0c2ry", "6e9vs", "mla77", "yj8sm", "9tple", "b6xfo", "419v7", "brezg", "xfofx", "9g873", "57b4a", "tatta", "i5y8n", "dgtvv"]
							}
						}
					},
					"10" : {
						"name" : "Title",
						"fieldName" : "title",
						"visible" : false,
						"order" : "11",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["sazq0", "c8cvz", "sfrsw", "t00ws", "g2yoy", "2kye4", "lha32", "vacho", "4wy0g", "vnmn9", "3kcfs", "t8c9s", "cyol3", "6uvsu", "xqult", "rczfj", "x42k3", "pozzj", "8pu3o", "4t2y9", "c0pia", "zcmb2", "8su6z", "b6pi3", "1c2x2"]
							}
						}
					},
					"11" : {
						"name" : "User Id",
						"fieldName" : "userId",
						"visible" : false,
						"order" : "12",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["wo1gl", "dwaht", "xsqca", "awfib", "8q0yk", "j0sq8", "6h6dm", "rc5pd", "zxf01", "oospw", "qjsei", "b0523", "bqsfq", "268lz", "4934f", "e4e03", "1bcsm", "m3bsl", "qx9mz", "5xay3", "loyws", "cva5y", "6a1sl", "20z1w", "wpm3j"]
							}
						}
					},
					"12" : {
						"name" : "User Name",
						"fieldName" : "userName",
						"visible" : true,
						"order" : 5,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["p3ogx", "o0ctx", "0zh1a", "47sub", "9ccfy", "9oaby", "pulpp", "yu8g2", "bo76y", "pvwcy", "dvrwi", "mxenn", "xpg7h", "361d6", "yjdut", "2o8ms", "0vl9f", "avx4l", "qkbsm", "q66sy", "hm8dq", "d1s0u", "lht61", "76o1q", "6khry"]
							}
						}
					}
				}
			},
			"updateWidget" : true,
			"clientId" : 103,
			"isMaximized" : false
		}],
		"canvasId" : 4
	}];
	var favCanvas = [{
		"canvasName" : "Pick Monitoring Canvas",
		"widgetInstanceList" : [{
			"data" : [{
				"chart" : [{
					"key" : "Completed",
					"color" : "#d62728",
					"values" : [{
						"label" : "Wave1",
						"value" : "300"
					}, {
						"label" : "Wave2",
						"value" : "455"
					}, {
						"label" : "Wave3",
						"value" : "367"
					}, {
						"label" : "Wave4",
						"value" : "407"
					}]
				}, {
					"key" : "Total",
					"color" : "#1f77b4",
					"values" : [{
						"label" : "Wave1",
						"value" : "350"
					}, {
						"label" : "Wave2",
						"value" : "470"
					}, {
						"label" : "Wave3",
						"value" : "390"
					}, {
						"label" : "Wave4",
						"value" : "459"
					}]
				}]
			}],
			"id" : null,
			"widgetDefinition" : {
				"id" : 10,
				"name" : "pickline-by-wave-barchart-widget",
				"shortName" : "PicklineByWave",
				"type" : "GRAPH_2D",
				"subtype" : "CHART_BAR",
				"broadCastMap" : {
					"Completed" : "series.key",
					"Wave" : "point.label"
				},
				"dataURL" : {
					"url" : "/waves/picklines",
					"searchcriteria" : {
						"searchmap" : {}
					}
				},
				"actualViewConfig" : {
					"anchor" : [0, 0],
					"height" : 500,
					"width" : 600,
					"zindex" : 0
				},
				"listensForList" : ["Area", "Wave", "Score"]
			},
			"actualViewConfig" : {
				"anchor" : [0, 0],
				"height" : 500,
				"width" : 600,
				"zindex" : 0
			},
			"updateWidget" : true,
			"clientId" : 104,
			"isMaximized" : false
		}],
		"canvasId" : 5
	}, {
		"canvasName" : "User Management Canvas",
		"widgetInstanceList" : [{
			"data" : [{
				"chart" : [{
					"key" : "Completed",
					"color" : "#d62728",
					"values" : [{
						"label" : "Wave1",
						"value" : "300"
					}, {
						"label" : "Wave2",
						"value" : "455"
					}, {
						"label" : "Wave3",
						"value" : "367"
					}, {
						"label" : "Wave4",
						"value" : "407"
					}]
				}, {
					"key" : "Total",
					"color" : "#1f77b4",
					"values" : [{
						"label" : "Wave1",
						"value" : "350"
					}, {
						"label" : "Wave2",
						"value" : "470"
					}, {
						"label" : "Wave3",
						"value" : "390"
					}, {
						"label" : "Wave4",
						"value" : "459"
					}]
				}]
			}],
			"id" : null,
			"widgetDefinition" : {
				"id" : 10,
				"name" : "pickline-by-wave-barchart-widget",
				"shortName" : "PicklineByWave",
				"type" : "GRAPH_2D",
				"subtype" : "CHART_BAR",
				"broadCastMap" : {
					"Completed" : "series.key",
					"Wave" : "point.label"
				},
				"dataURL" : {
					"url" : "/waves/picklines",
					"searchcriteria" : {
						"searchmap" : {}
					}
				},
				"actualViewConfig" : {
					"anchor" : [0, 0],
					"height" : 500,
					"width" : 600,
					"zindex" : 0
				},
				"listensForList" : ["Area", "Wave", "Score"]
			},
			"actualViewConfig" : {
				"anchor" : [0, 0],
				"height" : 500,
				"width" : 600,
				"zindex" : 0
			},
			"updateWidget" : true,
			"clientId" : 100,
			"isMaximized" : false
		}, {
			"data" : [{
				"chart" : [{
					"key" : "Completed",
					"color" : "#d62728",
					"values" : [{
						"label" : "Wave1",
						"value" : "300"
					}, {
						"label" : "Wave2",
						"value" : "455"
					}, {
						"label" : "Wave3",
						"value" : "367"
					}, {
						"label" : "Wave4",
						"value" : "407"
					}]
				}, {
					"key" : "Total",
					"color" : "#1f77b4",
					"values" : [{
						"label" : "Wave1",
						"value" : "350"
					}, {
						"label" : "Wave2",
						"value" : "470"
					}, {
						"label" : "Wave3",
						"value" : "390"
					}, {
						"label" : "Wave4",
						"value" : "459"
					}]
				}]
			}],
			"id" : null,
			"widgetDefinition" : {
				"id" : 11,
				"name" : "assignment-management-piechart-widget",
				"shortName" : "PicklineByWave",
				"type" : "GRAPH_2D",
				"subtype" : "CHART_BAR",
				"broadCastMap" : {
					"Completed" : "series.key",
					"Wave" : "point.label"
				},
				"dataURL" : {
					"url" : "/waves/picklines",
					"searchcriteria" : {
						"searchmap" : {}
					}
				},
				"actualViewConfig" : {
					"anchor" : [0, 0],
					"height" : 500,
					"width" : 600,
					"zindex" : 0
				},
				"listensForList" : ["Area", "Wave", "Score"]
			},
			"actualViewConfig" : {
				"anchor" : [30, 384],
				"height" : 500,
				"width" : 600,
				"zindex" : 1
			},
			"updateWidget" : true,
			"clientId" : 101,
			"isMaximized" : false
		}, {
			"data" : [],
			"id" : null,
			"widgetDefinition" : {
				"id" : 8,
				"name" : "create-or-edit-user-form-widget",
				"shortName" : "CreateUser",
				"widgetActionConfig" : {
					"create-assignment" : false,
					"view-report-productivity" : false,
					"configure-location" : false,
					"delete-canvas" : false,
					"create-canvas" : false
				},
				"broadCastMap" : {
					"Area" : "Series.key",
					"Route" : "point.label"
				},
				"listensForList" : ["Area", "Shift", "Picker"],
				"definitionData" : {
					"User" : [{
						"startDate" : "2014-09-30T16:40:02.362Z"
					}],
					"handheldScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"amdScreenLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"jenniferToUserLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"],
					"userToJenniferLanguageList" : ["ENGLISH", "FRENCH", "GERMAN"]
				},
				"actualViewConfig" : {
					"height" : 375,
					"width" : 485,
					"anchor" : [0, 863],
					"zindex" : 0
				}
			},
			"actualViewConfig" : {
				"height" : 375,
				"width" : 485,
				"anchor" : [0, 863],
				"zindex" : 0
			},
			"updateWidget" : true,
			"clientId" : 102,
			"isMaximized" : false
		}, {
			"data" : {
				"sortInfo" : {
					"fields" : ["birthDate", "emailAddress", "employeeId", "firstName", "lastName", "mobileNumber", "needsAuthentication", "preferredLanguage", "startDate", "title", "userId", "userName"],
					"directions" : ["asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc", "asc"]
				},
				"colDefs" : [{
					"field" : "emailAddress",
					"displayName" : "Email Address",
					"visible" : true
				}, {
					"field" : "firstName",
					"displayName" : "First Name",
					"visible" : true
				}, {
					"field" : "lastName",
					"displayName" : "Last Name",
					"visible" : true
				}, {
					"field" : "startDate",
					"displayName" : "Start Date",
					"visible" : true
				}, {
					"field" : "userName",
					"displayName" : "User Name",
					"visible" : true
				}],
				"data" : [{
					"birthDate" : "a3zza",
					"emailAddress" : "f4p0e",
					"employeeId" : "hkehl",
					"firstName" : "jxz7i",
					"lastName" : "05bti",
					"mobileNumber" : "nwoov",
					"needsAuthentication" : "eeyel",
					"preferredLanguage" : "c5oxs",
					"startDate" : "0wy5t",
					"title" : "sazq0",
					"userId" : "wo1gl",
					"userName" : "p3ogx"
				}, {
					"birthDate" : "ayb2f",
					"emailAddress" : "0o0wn",
					"employeeId" : "n1gbz",
					"firstName" : "0k69f",
					"lastName" : "7yf7w",
					"mobileNumber" : "inukx",
					"needsAuthentication" : "60f7y",
					"preferredLanguage" : "84htz",
					"startDate" : "j0052",
					"title" : "c8cvz",
					"userId" : "dwaht",
					"userName" : "o0ctx"
				}, {
					"birthDate" : "jp25e",
					"emailAddress" : "lxq4v",
					"employeeId" : "uezvb",
					"firstName" : "jz0zr",
					"lastName" : "7g9vo",
					"mobileNumber" : "y2ysk",
					"needsAuthentication" : "k5ml9",
					"preferredLanguage" : "wd1an",
					"startDate" : "yskvc",
					"title" : "sfrsw",
					"userId" : "xsqca",
					"userName" : "0zh1a"
				}, {
					"birthDate" : "bb0va",
					"emailAddress" : "k1qnm",
					"employeeId" : "xmfwh",
					"firstName" : "rdzvs",
					"lastName" : "l3gry",
					"mobileNumber" : "rqits",
					"needsAuthentication" : "xhisw",
					"preferredLanguage" : "eunm6",
					"startDate" : "ulkeh",
					"title" : "t00ws",
					"userId" : "awfib",
					"userName" : "47sub"
				}, {
					"birthDate" : "wkztp",
					"emailAddress" : "4iak3",
					"employeeId" : "rj4ue",
					"firstName" : "16yr2",
					"lastName" : "0blai",
					"mobileNumber" : "x044s",
					"needsAuthentication" : "9aktr",
					"preferredLanguage" : "j5vz8",
					"startDate" : "leito",
					"title" : "g2yoy",
					"userId" : "8q0yk",
					"userName" : "9ccfy"
				}, {
					"birthDate" : "ivl5v",
					"emailAddress" : "6m7yb",
					"employeeId" : "msgn8",
					"firstName" : "tfjfp",
					"lastName" : "jy7kj",
					"mobileNumber" : "y9mmk",
					"needsAuthentication" : "eg2o1",
					"preferredLanguage" : "5wgtt",
					"startDate" : "1ts0m",
					"title" : "2kye4",
					"userId" : "j0sq8",
					"userName" : "9oaby"
				}, {
					"birthDate" : "184d2",
					"emailAddress" : "qw6fn",
					"employeeId" : "c3ion",
					"firstName" : "u0coo",
					"lastName" : "bs0j7",
					"mobileNumber" : "ufcez",
					"needsAuthentication" : "uz6eu",
					"preferredLanguage" : "ty4xw",
					"startDate" : "j7yea",
					"title" : "lha32",
					"userId" : "6h6dm",
					"userName" : "pulpp"
				}, {
					"birthDate" : "7a3gg",
					"emailAddress" : "pjec4",
					"employeeId" : "ppn5a",
					"firstName" : "njd8e",
					"lastName" : "f03mr",
					"mobileNumber" : "1fjjz",
					"needsAuthentication" : "jod77",
					"preferredLanguage" : "8rc7l",
					"startDate" : "26q4s",
					"title" : "vacho",
					"userId" : "rc5pd",
					"userName" : "yu8g2"
				}, {
					"birthDate" : "9fkrm",
					"emailAddress" : "abqta",
					"employeeId" : "02a8q",
					"firstName" : "k05v0",
					"lastName" : "1s3t3",
					"mobileNumber" : "umejk",
					"needsAuthentication" : "pkkyb",
					"preferredLanguage" : "cutic",
					"startDate" : "5a0sa",
					"title" : "4wy0g",
					"userId" : "zxf01",
					"userName" : "bo76y"
				}, {
					"birthDate" : "qo14a",
					"emailAddress" : "j11l7",
					"employeeId" : "9i07t",
					"firstName" : "3z9s6",
					"lastName" : "s8glt",
					"mobileNumber" : "bpxf5",
					"needsAuthentication" : "ojaf4",
					"preferredLanguage" : "dro4l",
					"startDate" : "8h0iz",
					"title" : "vnmn9",
					"userId" : "oospw",
					"userName" : "pvwcy"
				}, {
					"birthDate" : "ut4eg",
					"emailAddress" : "tewk8",
					"employeeId" : "zac67",
					"firstName" : "ydljq",
					"lastName" : "k5ies",
					"mobileNumber" : "80vfk",
					"needsAuthentication" : "56xru",
					"preferredLanguage" : "thznc",
					"startDate" : "ca50i",
					"title" : "3kcfs",
					"userId" : "qjsei",
					"userName" : "dvrwi"
				}, {
					"birthDate" : "1itoy",
					"emailAddress" : "1s064",
					"employeeId" : "5rn0e",
					"firstName" : "dv5sh",
					"lastName" : "fggio",
					"mobileNumber" : "vck43",
					"needsAuthentication" : "wtuav",
					"preferredLanguage" : "i6p59",
					"startDate" : "0c2ry",
					"title" : "t8c9s",
					"userId" : "b0523",
					"userName" : "mxenn"
				}, {
					"birthDate" : "4zaf3",
					"emailAddress" : "i2c86",
					"employeeId" : "n7tsy",
					"firstName" : "24lz7",
					"lastName" : "p9cj0",
					"mobileNumber" : "zccyr",
					"needsAuthentication" : "e1l9w",
					"preferredLanguage" : "yybus",
					"startDate" : "6e9vs",
					"title" : "cyol3",
					"userId" : "bqsfq",
					"userName" : "xpg7h"
				}, {
					"birthDate" : "tu71k",
					"emailAddress" : "extoi",
					"employeeId" : "tag8x",
					"firstName" : "9r87a",
					"lastName" : "9yw7z",
					"mobileNumber" : "rp7ri",
					"needsAuthentication" : "nxog6",
					"preferredLanguage" : "zbwun",
					"startDate" : "mla77",
					"title" : "6uvsu",
					"userId" : "268lz",
					"userName" : "361d6"
				}, {
					"birthDate" : "gyygt",
					"emailAddress" : "17ayu",
					"employeeId" : "ch0om",
					"firstName" : "xetxk",
					"lastName" : "ah0o2",
					"mobileNumber" : "ivahx",
					"needsAuthentication" : "atfqe",
					"preferredLanguage" : "du8g6",
					"startDate" : "yj8sm",
					"title" : "xqult",
					"userId" : "4934f",
					"userName" : "yjdut"
				}, {
					"birthDate" : "7qwie",
					"emailAddress" : "glmqw",
					"employeeId" : "x7hc1",
					"firstName" : "99sjn",
					"lastName" : "uqqxq",
					"mobileNumber" : "tas75",
					"needsAuthentication" : "2abhq",
					"preferredLanguage" : "ymh06",
					"startDate" : "9tple",
					"title" : "rczfj",
					"userId" : "e4e03",
					"userName" : "2o8ms"
				}, {
					"birthDate" : "dtzae",
					"emailAddress" : "2dpjq",
					"employeeId" : "k0qwr",
					"firstName" : "iygq6",
					"lastName" : "ghl3p",
					"mobileNumber" : "1wetx",
					"needsAuthentication" : "jr2i7",
					"preferredLanguage" : "niuot",
					"startDate" : "b6xfo",
					"title" : "x42k3",
					"userId" : "1bcsm",
					"userName" : "0vl9f"
				}, {
					"birthDate" : "4fza0",
					"emailAddress" : "juan0",
					"employeeId" : "9rmqw",
					"firstName" : "a6q8p",
					"lastName" : "exno2",
					"mobileNumber" : "ofiii",
					"needsAuthentication" : "oqbmx",
					"preferredLanguage" : "cb88s",
					"startDate" : "419v7",
					"title" : "pozzj",
					"userId" : "m3bsl",
					"userName" : "avx4l"
				}, {
					"birthDate" : "ih81j",
					"emailAddress" : "4w8xc",
					"employeeId" : "x6wj1",
					"firstName" : "nmmll",
					"lastName" : "sgmo7",
					"mobileNumber" : "peejy",
					"needsAuthentication" : "2xt4y",
					"preferredLanguage" : "glpob",
					"startDate" : "brezg",
					"title" : "8pu3o",
					"userId" : "qx9mz",
					"userName" : "qkbsm"
				}, {
					"birthDate" : "402th",
					"emailAddress" : "15af3",
					"employeeId" : "g6a29",
					"firstName" : "eovm6",
					"lastName" : "tdgvm",
					"mobileNumber" : "q410f",
					"needsAuthentication" : "wdayx",
					"preferredLanguage" : "jo0i3",
					"startDate" : "xfofx",
					"title" : "4t2y9",
					"userId" : "5xay3",
					"userName" : "q66sy"
				}, {
					"birthDate" : "dhuce",
					"emailAddress" : "xyim5",
					"employeeId" : "y2saa",
					"firstName" : "1qd4a",
					"lastName" : "rnq8a",
					"mobileNumber" : "36rka",
					"needsAuthentication" : "9xlbs",
					"preferredLanguage" : "kvvw6",
					"startDate" : "9g873",
					"title" : "c0pia",
					"userId" : "loyws",
					"userName" : "hm8dq"
				}, {
					"birthDate" : "6bc2q",
					"emailAddress" : "wdnx7",
					"employeeId" : "yo05l",
					"firstName" : "8u124",
					"lastName" : "accii",
					"mobileNumber" : "7tbk8",
					"needsAuthentication" : "xywyj",
					"preferredLanguage" : "baymf",
					"startDate" : "57b4a",
					"title" : "zcmb2",
					"userId" : "cva5y",
					"userName" : "d1s0u"
				}, {
					"birthDate" : "6nduq",
					"emailAddress" : "j9cmn",
					"employeeId" : "a2d7r",
					"firstName" : "23z3l",
					"lastName" : "wr06u",
					"mobileNumber" : "al0ur",
					"needsAuthentication" : "jkzeq",
					"preferredLanguage" : "2svgk",
					"startDate" : "tatta",
					"title" : "8su6z",
					"userId" : "6a1sl",
					"userName" : "lht61"
				}, {
					"birthDate" : "7mas1",
					"emailAddress" : "3do4z",
					"employeeId" : "fq25m",
					"firstName" : "13z5q",
					"lastName" : "a21wz",
					"mobileNumber" : "5qyru",
					"needsAuthentication" : "1ys9z",
					"preferredLanguage" : "37mj7",
					"startDate" : "i5y8n",
					"title" : "b6pi3",
					"userId" : "20z1w",
					"userName" : "76o1q"
				}, {
					"birthDate" : "c6hqf",
					"emailAddress" : "h1odc",
					"employeeId" : "4qykz",
					"firstName" : "ip35c",
					"lastName" : "yl4x1",
					"mobileNumber" : "z1s8j",
					"needsAuthentication" : "28o8f",
					"preferredLanguage" : "0xmxv",
					"startDate" : "dgtvv",
					"title" : "1c2x2",
					"userId" : "wpm3j",
					"userName" : "6khry"
				}]
			},
			"id" : null,
			"widgetDefinition" : {
				"name" : "search-user-grid-widget",
				"id" : 9,
				"shortName" : "SearchUser",
				"widgetActionConfig" : {},
				"defaultData" : {},
				"title" : "Search User",
				"actualViewConfig" : {
					"height" : 375,
					"width" : 485,
					"dateFormat" : {
						"selectedFormat" : "mm-dd-yyyy",
						"availableFormats" : ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
					},
					"anchor" : [1, 363],
					"zindex" : 0,
					"gridColumns" : {
						"1" : {
							"name" : "Birth Date",
							"fieldName" : "birthDate",
							"visible" : false,
							"order" : "1",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["a3zza", "ayb2f", "jp25e", "bb0va", "wkztp", "ivl5v", "184d2", "7a3gg", "9fkrm", "qo14a", "ut4eg", "1itoy", "4zaf3", "tu71k", "gyygt", "7qwie", "dtzae", "4fza0", "ih81j", "402th", "dhuce", "6bc2q", "6nduq", "7mas1", "c6hqf"]
								}
							}
						},
						"2" : {
							"name" : "Email Address",
							"fieldName" : "emailAddress",
							"visible" : true,
							"order" : 1,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["f4p0e", "0o0wn", "lxq4v", "k1qnm", "4iak3", "6m7yb", "qw6fn", "pjec4", "abqta", "j11l7", "tewk8", "1s064", "i2c86", "extoi", "17ayu", "glmqw", "2dpjq", "juan0", "4w8xc", "15af3", "xyim5", "wdnx7", "j9cmn", "3do4z", "h1odc"]
								}
							}
						},
						"3" : {
							"name" : "Employee Id",
							"fieldName" : "employeeId",
							"visible" : false,
							"order" : "3",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["hkehl", "n1gbz", "uezvb", "xmfwh", "rj4ue", "msgn8", "c3ion", "ppn5a", "02a8q", "9i07t", "zac67", "5rn0e", "n7tsy", "tag8x", "ch0om", "x7hc1", "k0qwr", "9rmqw", "x6wj1", "g6a29", "y2saa", "yo05l", "a2d7r", "fq25m", "4qykz"]
								}
							}
						},
						"4" : {
							"name" : "First Name",
							"fieldName" : "firstName",
							"visible" : true,
							"order" : 2,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["jxz7i", "0k69f", "jz0zr", "rdzvs", "16yr2", "tfjfp", "u0coo", "njd8e", "k05v0", "3z9s6", "ydljq", "dv5sh", "24lz7", "9r87a", "xetxk", "99sjn", "iygq6", "a6q8p", "nmmll", "eovm6", "1qd4a", "8u124", "23z3l", "13z5q", "ip35c"]
								}
							}
						},
						"5" : {
							"name" : "Last Name",
							"fieldName" : "lastName",
							"visible" : true,
							"order" : 3,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["05bti", "7yf7w", "7g9vo", "l3gry", "0blai", "jy7kj", "bs0j7", "f03mr", "1s3t3", "s8glt", "k5ies", "fggio", "p9cj0", "9yw7z", "ah0o2", "uqqxq", "ghl3p", "exno2", "sgmo7", "tdgvm", "rnq8a", "accii", "wr06u", "a21wz", "yl4x1"]
								}
							}
						},
						"6" : {
							"name" : "Mobile Number",
							"fieldName" : "mobileNumber",
							"visible" : false,
							"order" : "7",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["nwoov", "inukx", "y2ysk", "rqits", "x044s", "y9mmk", "ufcez", "1fjjz", "umejk", "bpxf5", "80vfk", "vck43", "zccyr", "rp7ri", "ivahx", "tas75", "1wetx", "ofiii", "peejy", "q410f", "36rka", "7tbk8", "al0ur", "5qyru", "z1s8j"]
								}
							}
						},
						"7" : {
							"name" : "Needs Authentication",
							"fieldName" : "needsAuthentication",
							"visible" : false,
							"order" : "8",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["eeyel", "60f7y", "k5ml9", "xhisw", "9aktr", "eg2o1", "uz6eu", "jod77", "pkkyb", "ojaf4", "56xru", "wtuav", "e1l9w", "nxog6", "atfqe", "2abhq", "jr2i7", "oqbmx", "2xt4y", "wdayx", "9xlbs", "xywyj", "jkzeq", "1ys9z", "28o8f"]
								}
							}
						},
						"8" : {
							"name" : "Preferred Language",
							"fieldName" : "preferredLanguage",
							"visible" : false,
							"order" : "9",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["c5oxs", "84htz", "wd1an", "eunm6", "j5vz8", "5wgtt", "ty4xw", "8rc7l", "cutic", "dro4l", "thznc", "i6p59", "yybus", "zbwun", "du8g6", "ymh06", "niuot", "cb88s", "glpob", "jo0i3", "kvvw6", "baymf", "2svgk", "37mj7", "0xmxv"]
								}
							}
						},
						"9" : {
							"name" : "Start Date",
							"fieldName" : "startDate",
							"visible" : true,
							"order" : 4,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["0wy5t", "j0052", "yskvc", "ulkeh", "leito", "1ts0m", "j7yea", "26q4s", "5a0sa", "8h0iz", "ca50i", "0c2ry", "6e9vs", "mla77", "yj8sm", "9tple", "b6xfo", "419v7", "brezg", "xfofx", "9g873", "57b4a", "tatta", "i5y8n", "dgtvv"]
								}
							}
						},
						"10" : {
							"name" : "Title",
							"fieldName" : "title",
							"visible" : false,
							"order" : "11",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["sazq0", "c8cvz", "sfrsw", "t00ws", "g2yoy", "2kye4", "lha32", "vacho", "4wy0g", "vnmn9", "3kcfs", "t8c9s", "cyol3", "6uvsu", "xqult", "rczfj", "x42k3", "pozzj", "8pu3o", "4t2y9", "c0pia", "zcmb2", "8su6z", "b6pi3", "1c2x2"]
								}
							}
						},
						"11" : {
							"name" : "User Id",
							"fieldName" : "userId",
							"visible" : false,
							"order" : "12",
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["wo1gl", "dwaht", "xsqca", "awfib", "8q0yk", "j0sq8", "6h6dm", "rc5pd", "zxf01", "oospw", "qjsei", "b0523", "bqsfq", "268lz", "4934f", "e4e03", "1bcsm", "m3bsl", "qx9mz", "5xay3", "loyws", "cva5y", "6a1sl", "20z1w", "wpm3j"]
								}
							}
						},
						"12" : {
							"name" : "User Name",
							"fieldName" : "userName",
							"visible" : true,
							"order" : 5,
							"sortOrder" : "1",
							"data" : {
								"grid" : {
									"values" : ["p3ogx", "o0ctx", "0zh1a", "47sub", "9ccfy", "9oaby", "pulpp", "yu8g2", "bo76y", "pvwcy", "dvrwi", "mxenn", "xpg7h", "361d6", "yjdut", "2o8ms", "0vl9f", "avx4l", "qkbsm", "q66sy", "hm8dq", "d1s0u", "lht61", "76o1q", "6khry"]
								}
							}
						}
					}
				},
				"listensForList" : ["regionId", "warehouseId"],
				"broadCastMap" : {
					"username" : "username"
				},
				"dataURL" : {
					"url" : "/users/userlist/search",
					"searchCriteria" : {
						"searchMap" : {},
						"sortMap" : {},
						"pageSize" : "10",
						"pageNumber" : "0"
					},
					"searchcriteria" : {
						"pagesize" : "100",
						"pagenumber" : "0",
						"searchmap" : {}
					}
				}
			},
			"actualViewConfig" : {
				"height" : 375,
				"width" : 485,
				"dateFormat" : {
					"selectedFormat" : "mm-dd-yyyy",
					"availableFormats" : ["mm-dd-yyyy", "MMM dd, yyyy", "dd-mm-yyyy"]
				},
				"anchor" : [1, 363],
				"zindex" : 0,
				"gridColumns" : {
					"1" : {
						"name" : "Birth Date",
						"fieldName" : "birthDate",
						"visible" : false,
						"order" : "1",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["a3zza", "ayb2f", "jp25e", "bb0va", "wkztp", "ivl5v", "184d2", "7a3gg", "9fkrm", "qo14a", "ut4eg", "1itoy", "4zaf3", "tu71k", "gyygt", "7qwie", "dtzae", "4fza0", "ih81j", "402th", "dhuce", "6bc2q", "6nduq", "7mas1", "c6hqf"]
							}
						}
					},
					"2" : {
						"name" : "Email Address",
						"fieldName" : "emailAddress",
						"visible" : true,
						"order" : 1,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["f4p0e", "0o0wn", "lxq4v", "k1qnm", "4iak3", "6m7yb", "qw6fn", "pjec4", "abqta", "j11l7", "tewk8", "1s064", "i2c86", "extoi", "17ayu", "glmqw", "2dpjq", "juan0", "4w8xc", "15af3", "xyim5", "wdnx7", "j9cmn", "3do4z", "h1odc"]
							}
						}
					},
					"3" : {
						"name" : "Employee Id",
						"fieldName" : "employeeId",
						"visible" : false,
						"order" : "3",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["hkehl", "n1gbz", "uezvb", "xmfwh", "rj4ue", "msgn8", "c3ion", "ppn5a", "02a8q", "9i07t", "zac67", "5rn0e", "n7tsy", "tag8x", "ch0om", "x7hc1", "k0qwr", "9rmqw", "x6wj1", "g6a29", "y2saa", "yo05l", "a2d7r", "fq25m", "4qykz"]
							}
						}
					},
					"4" : {
						"name" : "First Name",
						"fieldName" : "firstName",
						"visible" : true,
						"order" : 2,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["jxz7i", "0k69f", "jz0zr", "rdzvs", "16yr2", "tfjfp", "u0coo", "njd8e", "k05v0", "3z9s6", "ydljq", "dv5sh", "24lz7", "9r87a", "xetxk", "99sjn", "iygq6", "a6q8p", "nmmll", "eovm6", "1qd4a", "8u124", "23z3l", "13z5q", "ip35c"]
							}
						}
					},
					"5" : {
						"name" : "Last Name",
						"fieldName" : "lastName",
						"visible" : true,
						"order" : 3,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["05bti", "7yf7w", "7g9vo", "l3gry", "0blai", "jy7kj", "bs0j7", "f03mr", "1s3t3", "s8glt", "k5ies", "fggio", "p9cj0", "9yw7z", "ah0o2", "uqqxq", "ghl3p", "exno2", "sgmo7", "tdgvm", "rnq8a", "accii", "wr06u", "a21wz", "yl4x1"]
							}
						}
					},
					"6" : {
						"name" : "Mobile Number",
						"fieldName" : "mobileNumber",
						"visible" : false,
						"order" : "7",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["nwoov", "inukx", "y2ysk", "rqits", "x044s", "y9mmk", "ufcez", "1fjjz", "umejk", "bpxf5", "80vfk", "vck43", "zccyr", "rp7ri", "ivahx", "tas75", "1wetx", "ofiii", "peejy", "q410f", "36rka", "7tbk8", "al0ur", "5qyru", "z1s8j"]
							}
						}
					},
					"7" : {
						"name" : "Needs Authentication",
						"fieldName" : "needsAuthentication",
						"visible" : false,
						"order" : "8",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["eeyel", "60f7y", "k5ml9", "xhisw", "9aktr", "eg2o1", "uz6eu", "jod77", "pkkyb", "ojaf4", "56xru", "wtuav", "e1l9w", "nxog6", "atfqe", "2abhq", "jr2i7", "oqbmx", "2xt4y", "wdayx", "9xlbs", "xywyj", "jkzeq", "1ys9z", "28o8f"]
							}
						}
					},
					"8" : {
						"name" : "Preferred Language",
						"fieldName" : "preferredLanguage",
						"visible" : false,
						"order" : "9",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["c5oxs", "84htz", "wd1an", "eunm6", "j5vz8", "5wgtt", "ty4xw", "8rc7l", "cutic", "dro4l", "thznc", "i6p59", "yybus", "zbwun", "du8g6", "ymh06", "niuot", "cb88s", "glpob", "jo0i3", "kvvw6", "baymf", "2svgk", "37mj7", "0xmxv"]
							}
						}
					},
					"9" : {
						"name" : "Start Date",
						"fieldName" : "startDate",
						"visible" : true,
						"order" : 4,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["0wy5t", "j0052", "yskvc", "ulkeh", "leito", "1ts0m", "j7yea", "26q4s", "5a0sa", "8h0iz", "ca50i", "0c2ry", "6e9vs", "mla77", "yj8sm", "9tple", "b6xfo", "419v7", "brezg", "xfofx", "9g873", "57b4a", "tatta", "i5y8n", "dgtvv"]
							}
						}
					},
					"10" : {
						"name" : "Title",
						"fieldName" : "title",
						"visible" : false,
						"order" : "11",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["sazq0", "c8cvz", "sfrsw", "t00ws", "g2yoy", "2kye4", "lha32", "vacho", "4wy0g", "vnmn9", "3kcfs", "t8c9s", "cyol3", "6uvsu", "xqult", "rczfj", "x42k3", "pozzj", "8pu3o", "4t2y9", "c0pia", "zcmb2", "8su6z", "b6pi3", "1c2x2"]
							}
						}
					},
					"11" : {
						"name" : "User Id",
						"fieldName" : "userId",
						"visible" : false,
						"order" : "12",
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["wo1gl", "dwaht", "xsqca", "awfib", "8q0yk", "j0sq8", "6h6dm", "rc5pd", "zxf01", "oospw", "qjsei", "b0523", "bqsfq", "268lz", "4934f", "e4e03", "1bcsm", "m3bsl", "qx9mz", "5xay3", "loyws", "cva5y", "6a1sl", "20z1w", "wpm3j"]
							}
						}
					},
					"12" : {
						"name" : "User Name",
						"fieldName" : "userName",
						"visible" : true,
						"order" : 5,
						"sortOrder" : "1",
						"data" : {
							"grid" : {
								"values" : ["p3ogx", "o0ctx", "0zh1a", "47sub", "9ccfy", "9oaby", "pulpp", "yu8g2", "bo76y", "pvwcy", "dvrwi", "mxenn", "xpg7h", "361d6", "yjdut", "2o8ms", "0vl9f", "avx4l", "qkbsm", "q66sy", "hm8dq", "d1s0u", "lht61", "76o1q", "6khry"]
							}
						}
					}
				}
			},
			"updateWidget" : true,
			"clientId" : 103,
			"isMaximized" : false
		}],
		"canvasId" : 4
	}];
	var localEvent = {
		stopPropagation : function() {
			return true;
		}
	};
	var userInfo = "Jack";
	var localUserService = null;
	var localState = null;

	beforeEach(module('amdApp', function($translateProvider) {

		$translateProvider.translations('en', {
			"language-code" : "EN",
		}, 'fr', {
			"language-code" : "fr",
		}, 'de', {
			"language-code" : "de",
		}).preferredLanguage('en');
		$translateProvider.useLoader('LocaleLoader');
	}));

	// Inject Dependencies
	beforeEach(inject(function($controller, $rootScope, $modal, $state, $templateCache, LocalStoreService, WidgetService, UserService) {
		//mock opening modal, so we can test closing it
		$templateCache.put('views/modals/logout-confirmation.html', '');
		localModalInstance = $modal.open({
			templateUrl : 'views/modals/logout-confirmation.html',
                        backdrop: 'static'
		});
		localScope = $rootScope.$new();
		localLocalStoreService = LocalStoreService;
		localWidgetService = WidgetService;
		localUserService = UserService;
		localLocalStoreService.addLSItem(null, 'FavoriteCanvasList', favCanvas);
		localLocalStoreService.addLSItem(null, 'UserInfo', userInfo);

		localController = $controller('LogoutConfirmationModalController', {
			$scope : localScope,
			$state : localState,
			$modalInstance : localModalInstance
		});
	}));

	// Dependency Injection Tests
	describe('Dependency Injection Specs', function() {
		it('should inject scope dependency', function() {
			expect(localScope).toBeDefined();
		});
		it('should inject modal instance dependency', function() {
			expect(localModalInstance).toBeDefined();
		});
		it('should inject controller dependency', function() {
			expect(localController).toBeDefined();
		});
	});

	// Modal Tests
	describe('Logout Confirmation Modal Specs', function() {
		it('should close the logout modal', function() {
			spyOn(localModalInstance, 'close').andCallThrough();
			localScope.$digest();
			localScope.closeModal();

			expect(localScope.closeModal).toBeDefined();
			expect(localModalInstance.close).toHaveBeenCalled();
		});

		//handleLogout
		it('should call handleLogout fn', function() {
			spyOn(localUserService, 'logout').andCallThrough();
			localScope.$digest();
			localScope.handleLogout();

			expect(localScope.handleLogout).toBeDefined();
			expect(localUserService.logout).toHaveBeenCalled();
		});
		//SaveCanvas
		/*it('should call SaveCanvas fn', function() {
			localScope.SaveCanvas(dirtyCanvas[0], localEvent);
			expect(localScope.SaveCanvas).toBeDefined();
		});
		
		it('should call SaveWidget fn', function() {
			localScope.SaveWidget(dirtyCanvas[0].widgetInstanceList[0]);
			expect(localScope.SaveWidget).toBeDefined();
		});*/
	});
});
