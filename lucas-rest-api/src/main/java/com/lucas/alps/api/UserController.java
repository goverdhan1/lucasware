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
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiRequest;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.*;
import com.lucas.alps.viewtype.*;
import com.lucas.dao.user.UserDAO;
import com.lucas.dto.user.MultiUserDTO;
import com.lucas.dto.user.UserFormFieldsDTO;
import com.lucas.dto.user.UsersAssignedGroupsDTO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.entity.user.UserPreferences;
import com.lucas.exception.Level;
import com.lucas.exception.LucasBusinessException;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.exception.UserDoesNotExistException;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.ui.UIService;
import com.lucas.services.user.*;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.support.DefaultGridable;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/users")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;
	private final UserDAO userDAO;
    private final MessageSource messageSource;
    private final ShiftService shiftService;
    private final WmsUserService wmsUserService;
    private final SupportedLanguageService languageService;
    private final GroupService groupService;
 	private final UIService uiService;
 	private final UserPreferencesService userPreferencesService;

    @Inject
	public UserController(UserService userService, MessageSource messageSource,
                          ShiftService shiftService, WmsUserService wmsUserService,
                          SupportedLanguageService languageService, GroupService groupService,
                          UIService uiService, UserPreferencesService userPreferencesService,
                          UserDAO userDAO) {

		this.userService = userService;
		this.messageSource = messageSource;
		this.shiftService = shiftService;
		this.wmsUserService = wmsUserService;
		this.languageService = languageService;
		this.groupService = groupService;
        this.uiService=uiService;
        this.userPreferencesService = userPreferencesService;
        this.userDAO = userDAO;

	}


    @RequestMapping(method = RequestMethod.GET, value = "/usernames")
    @ResponseBody
    public ApiResponse <List<String>> getUsernamesList() {

        final ApiResponse <List <String>> apiResponse = new ApiResponse <List<String>>();

        List<String> userList = null;

        try {
            userList = userService.getActiveUsernameList();

            apiResponse.setData(userList);
            LOG.debug("Size{}", userList.size());

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;
    }

	/*
	This is called when the user first login to get the user permissions
	 */
	@RequestMapping(method = RequestMethod.GET, value="/{username}")
	@ResponseView(UserDetailsView.class)
	public @ResponseBody ApiResponse<UserView> getUser(@PathVariable String username){

		User user = userService.getUser(username);
		ApiResponse<UserView> apiResponse = new ApiResponse<UserView>();
		apiResponse.setData(new UserView(user));
		return apiResponse;
	}
	

    /*
    This is call to get all the data for the user widget form - note that this is returning different data than the above
     */
	@ResponseView(UserFormView.class)
	@RequestMapping(method = RequestMethod.POST, value="/details")
	public @ResponseBody ApiResponse<List<UserView>> getUserDetails(@RequestBody List<String> userNameList){

        List<User> userList = new ArrayList<>();
        ApiResponse<List<UserView>> apiResponse = new ApiResponse<List<UserView>>();
        List<UserView> userViewList = new ArrayList<>();

        if ((userNameList != null) && (userNameList.size() != 0)) {
            try {
                userList = userService.getUserList(userNameList);
            } catch (UserDoesNotExistException e) {
                apiResponse.setStatus("failure");
                apiResponse.setData(null);
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1026");
                apiResponse.setMessage(messageSource.getMessage("1026", new Object[] {userList}, LocaleContextHolder.getLocale()));
                return apiResponse;
            }
            catch (Exception e) {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        } else {
            apiResponse.setStatus("failure");
            apiResponse.setData(null);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1026");
            apiResponse.setMessage(messageSource.getMessage("1026", new Object[] {userList}, LocaleContextHolder.getLocale()));
            return apiResponse;
        }


        for (final User user :  userList) {
            userViewList.add(new UserView(user));
        }

        apiResponse.setData(userViewList);
        LOG.debug("Size{}", userList.size());
        return apiResponse;

	}

	@RequestMapping(method = RequestMethod.GET, value="/userscount")
	public @ResponseBody ApiResponse<String> getUsersCount(){
		ApiResponse<String> apiResponse = new ApiResponse<String>();
		Long userCount = userService.getUserCount();
		apiResponse.setData(String.valueOf(userCount));
		return  apiResponse;
	}


  	@ResponseView(UserFormView.class)
	@RequestMapping(method = RequestMethod.POST, value="/save")
	public @ResponseBody ApiResponse<Object> saveUser(@RequestBody @Valid UserView userView){
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        Boolean isCreated = null;
        final User user = userView.getUser();
        LOG.debug("**** UserController.saveUser() **** Deserialise user object: ******* = " + ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));

       try {
            isCreated = userService.createUser(user);
        } catch (ConstraintViolationException | LucasBusinessException ex) {
            LOG.debug("Exception thrown...", ex.getMessage());
            apiResponse.setExplicitDismissal(Boolean.FALSE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1021");
            apiResponse.setStatus("failure");
            apiResponse.setData("");
            apiResponse.setMessage(messageSource.getMessage("1021", new Object[] {user.getUserName()}, LocaleContextHolder.getLocale()));
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
       } catch (Exception ex) {
            LOG.debug("Exception thrown...", ex.getMessage());
            if (ex.getMessage().equals("Password is required") == true)
            {
               apiResponse.setExplicitDismissal(Boolean.TRUE);
               apiResponse.setLevel(Level.ERROR);
               apiResponse.setCode("1053");
               apiResponse.setStatus("failure");
               apiResponse.setData("");
               apiResponse.setMessage(messageSource.getMessage("1053", new Object[]{user.getUserName()}, LocaleContextHolder.getLocale()));
            }
            else {
               apiResponse.setExplicitDismissal(Boolean.FALSE);
               apiResponse.setLevel(Level.ERROR);
               apiResponse.setCode("1021");
               apiResponse.setStatus("failure");
               apiResponse.setData("");
               apiResponse.setMessage(messageSource.getMessage("1021", new Object[]{user.getUserName()}, LocaleContextHolder.getLocale()));
            }
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
        }

        apiResponse.setExplicitDismissal(Boolean.FALSE);
		apiResponse.setLevel(Level.INFO);
		if(isCreated){
			apiResponse.setCode("1012");		
			apiResponse.setMessage(messageSource.getMessage("1012", new Object[] {user.getUserName()}, LocaleContextHolder.getLocale()));
		} else {
			apiResponse.setCode("1017");		
			apiResponse.setMessage(messageSource.getMessage("1017", new Object[] {user.getUserName()}, LocaleContextHolder.getLocale()));

		}
		LOG.debug(apiResponse.getMessage());

		return apiResponse;
	}



    /**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/userlist/search
     * sample JSON expected as input to this method
           {
  "pageSize": 3,
  "pageNumber": 0,
  "searchMap": {
    "userName": [
      "jack123",
      "jill123",
      "admin123"
    ]
  },
  "sortMap": {
    "userName": "ASC"
  }
}

returns the result

{
    "status": "success",
    "code": "200",
    "message": "Request processed successfully",
    "uniqueKey": null,
    "token": null,
    "reason": null,
    "data": {
        "grid": {
            "1": {
                "values": [
                    "",
                    "",
                    ""
                ]
            },
            "2": {
                "values": [
                    "admin@user.com",
                    "user2@normal.com",
                    "user1@normal.com"
                ]
            },
            "3": {
                "values": [
                    "",
                    "",
                    ""
                ]
            },
            "4": {
                "values": [
                    "Admin",
                    "Jack",
                    "Jill"
                ]
            },
            "5": {
                "values": [
                    "",
                    "",
                    ""
                ]
            },
            "6": {
                "values": [
                    "User",
                    "User2",
                    "User1"
                ]
            },
            "7": {
                "values": [
                    "",
                    "",
                    ""
                ]
            },
            "8": {
                "values": [
                    "false",
                    "false",
                    "false"
                ]
            },
            "9": {
                "values": [
                    "English",
                    "English",
                    "English"
                ]
            },
            "10": {
                "values": [
                    "2014-01-01T00:00:00.000Z",
                    "2014-01-01T00:00:00.000Z",
                    "2014-01-01T00:00:00.000Z"
                ]
            },
            "11": {
                "values": [
                    "",
                    "",
                    ""
                ]
            },
            "12": {
                "values": [
                    "1",
                    "3",
                    "2"
                ]
            },
            "13": {
                "values": [
                    "admin123",
                    "jack123",
                    "jill123"
                ]
            }
        }
    }
}
     */
	@ResponseView(UserSearchByColumnsDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/userlist/search")
    public
    @ResponseBody
    ApiResponse<UserSearchByColumnsView> getUserListBySearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

        final ApiResponse<UserSearchByColumnsView> apiResponse = new ApiResponse<UserSearchByColumnsView>();
        List<User> userList = null;
        Long totalRecords = null;
        UserSearchByColumnsView userSearchBycolumnsView = null;
        try {
            userList = userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
            totalRecords =userService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
            userSearchBycolumnsView = new UserSearchByColumnsView(userList);
            userSearchBycolumnsView.setTotalRecords(totalRecords);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            apiResponse.setStatus("failure");
            apiResponse.setData(null);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1069");
            apiResponse.setMessage(messageSource.getMessage("1069", new Object[] {e.getMessage()}, LocaleContextHolder.getLocale()));
            return apiResponse;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            apiResponse.setStatus("failure");
            apiResponse.setData(null);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1070");
            apiResponse.setMessage(messageSource.getMessage("1070", new Object[]{e.getMessage()}, LocaleContextHolder.getLocale()));
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        apiResponse.setData(userSearchBycolumnsView);

		LOG.debug("Size{}",userList.size());
        return apiResponse;
    }

    /**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/userlist/excel
     * sample JSON expected as input to this method
           {
   "pageSize":"20",
   "pageNumber":"1",
   "sortMap":{
      "userName":"ASC"
   },
   "searchMap":{
      "startDate":{
         "startDate":[
            2014,
            1,
            1
         ],
         "endDate":[
            2015,
            8,
            5
         ]
      },
      "userId":{
         "startOfRange":0,
         "endOfRange":300
      },
      "userName":[
         "%dummy%",
         "jack123"
      ]
   }
}
     */
    @RequestMapping(value = "/userlist/excel", method = RequestMethod.POST)
    public ModelAndView getUserListInExcel(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {
        List userList = new ArrayList<User>();
        try {
            userList = userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        // return a view which will be resolved by an excel view resolver

        final DefaultGridable defaultGridable = new DefaultGridable(userList);
        defaultGridable.setTitle("User Details");
        final ModelAndView modelAndView= new ModelAndView("excelView", "defaultGridable", defaultGridable);
        return  modelAndView;
    }


    /**
     * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/userlist/pdf
     * sample JSON expected as input to this method
     {
   "pageSize":"20",
   "pageNumber":"1",
   "sortMap":{
      "userName":"ASC"
   },
   "searchMap":{
      "startDate":{
         startDate":[
            2014,
            1,
            1
         ],
         "endDate":[
            2015,
            8,
            5
         ]
      },
      "userId":{
         "startOfRange":0,
         "endOfRange":300
      },
      "userName":[
         "%dummy%",
         "jack123"
      ]
   }
}
     */
    @RequestMapping(value = "/userlist/pdf", method = RequestMethod.POST)
    public ModelAndView getUserListInPdf(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {
        List userList = new ArrayList<User>();
        try {
            userList = userService.getUserListBySearchAndSortCriteria(searchAndSortCriteria);
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        // return a view which will be resolved by an excel view resolver

        final DefaultGridable defaultGridable = new DefaultGridable(userList);
        defaultGridable.setTitle("User Details");
        final ModelAndView modelAndView= new ModelAndView("pdfView", "defaultGridable", defaultGridable);
        return  modelAndView;
    }
    
	/*
	 * url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/multiedit/details
     * sample JSON expected as output of this method
        "userMutablePairMap": {
              "skill": "TestSkill",
              "hhLanguage": "DE_DE",
              "amdLanguage": "EN_US",
              "j2uLanguage": "EN_US",
              "u2jLanguage": "FR_FR"
        }	 
        Refer: 1353
	 */
	
    @ResponseView(MultiUserDetailsView.class)
    @RequestMapping(value = "/multiedit/details", method = RequestMethod.POST)
    public @ResponseBody ApiResponse<MultiUserView> userDetails(@RequestBody List<String> userNameList) throws IntrospectionException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, ParseException, LucasRuntimeException {
        final ApiResponse<MultiUserView> apiResponse = new ApiResponse<MultiUserView>();
        List<String> userList = new ArrayList<>();
        if (!CollectionsUtilService.isNullOrEmpty(userNameList) && userNameList.size() > 1) {
            
            try {

                userList = userService.getUserNameListVerified(userNameList);
                Map<String, String> userEditableFields = userService.getUserMutablePairMap(userList);
                MultiUserView multiUserView = new MultiUserView(userEditableFields);
                apiResponse.setData(multiUserView);

            } catch (UserDoesNotExistException e) {
                apiResponse.setStatus("failure");
                apiResponse.setData(null);
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1026");
                apiResponse.setMessage(messageSource.getMessage("1042", new Object[] {e.getMessage()}, LocaleContextHolder.getLocale()));
                return apiResponse;
            }
            catch (Exception e) {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
            
        } else {
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.INFO);
            apiResponse.setCode("1041");
            apiResponse.setMessage(messageSource.getMessage("1041", new Object[] {userNameList.toString()}, LocaleContextHolder.getLocale()));
        }

        return apiResponse;
    }
	

    /*
   url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/enable
      [
        "jack",
        "jill"
      ]
  */
    @RequestMapping(method = RequestMethod.POST, value = "/enable")
    public
    @ResponseBody
    ApiResponse<Map> enableUser(@RequestBody List<String> userList) {
        final ApiResponse<Map> apiResponse = new ApiResponse<Map>();
        Map<String,String> resultMap=new HashMap<String,String>();
        if (userList != null) {
            resultMap=userService.enableUser(userList);
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        apiResponse.setData(resultMap);
        apiResponse.setCode("1015");
        apiResponse.setMessage(messageSource.getMessage("1015", userList.toArray(), LocaleContextHolder.getLocale()));
        LOG.debug(apiResponse.getMessage());
        return apiResponse;
    }

    /*
  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/disable
     [
       "jack",
       "jill"
     ]
 */
    @RequestMapping(method = RequestMethod.POST, value = "/disable")
    public
    @ResponseBody
    ApiResponse<Map> disableUser(@RequestBody List<String> userList) {
        final ApiResponse<Map> apiResponse = new ApiResponse<Map>();
        Map<String,String> resultMap=new HashMap<String,String>();
        if (userList != null) {
            resultMap=userService.disableUser(userList);
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        apiResponse.setData(resultMap);
        apiResponse.setCode("1014");
        apiResponse.setMessage(messageSource.getMessage("1014", userList.toArray(), LocaleContextHolder.getLocale()));
        LOG.debug(apiResponse.getMessage());
        return apiResponse;
    }


    /*
  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/delete
   [
     "jack123",
     "jill123"
   ]
*/
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    ApiResponse<Map> deleteUser(@RequestBody List<String> userNameList) {
        final ApiResponse<Map> apiResponse = new ApiResponse<Map>();
        Map<String,String> resultMap=new HashMap<String,String>();




        resultMap = userService.deleteUser(userNameList);

        for (String value: resultMap.values()) {
            // using ArrayList#contains
            if (value.contains("BLANK_USERNAME_ERROR_CODE")) {
                apiResponse.setCode("1019");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1019", userNameList.toArray(), LocaleContextHolder.getLocale()));
            } else if (value.contains("INVALID_USERNAME_ERROR_CODE")) {
                apiResponse.setCode("1020");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1020", userNameList.toArray(), LocaleContextHolder.getLocale()));
            } else {
                apiResponse.setCode("1016");
                apiResponse.setMessage(messageSource.getMessage("1016", userNameList.toArray(), LocaleContextHolder.getLocale()));
                apiResponse.setStatus("success");
            }
        }

        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        apiResponse.setData(resultMap);
        LOG.debug(apiResponse.getMessage());
        return apiResponse;
    }

    /**Sample input to the method should be {
	    "usernameList": [
	      "jack123",
	      "jill123"
	    ],
	    "multiEditFields": {
            "skill": {
            "value": "Advanced",
            "forceUpdate": "true"
          },
	      "j2uLanguage": {
	        "value": "FR_FR",
	        "forceUpdate": "true"
	      },
	      "u2jLanguage": {
	        "value": "FR_FR",
	        "forceUpdate": "true"
	      },
	      "hhLanguage": {
	        "value": "FR_FR",
	        "forceUpdate": "true"
	      },
	      "amdLanguage": {
	        "value": "FR_FR",
	        "forceUpdate": "true"
	      }
	    }
	}
     * 
     * @param multiUserDTO
     * @return
     * @throws LucasRuntimeException
     * @throws Exception
     */
	
    @RequestMapping(value = "/multiedit/save", method = RequestMethod.POST)
    public @ResponseBody ApiResponse<Object> saveMultiUser(@RequestBody MultiUserDTO multiUserDTO) throws LucasRuntimeException, Exception {

        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        final List<String> userNameList = multiUserDTO.getUserNameList();

        if (multiUserDTO != null) {
            UserFormFieldsDTO multiEditFieldsDto = multiUserDTO.getMultiEditFields();
            if (!CollectionsUtilService.isNullOrEmpty(userNameList) && multiEditFieldsDto != null) {

                List<String> userList = new ArrayList<>();
                if (!CollectionsUtilService.isNullOrEmpty(userNameList) && userNameList.size() > 1) {

                    try {

                        userList = userService.getUserNameListVerified(userNameList);
                        userService.saveMultiUser(userList, multiEditFieldsDto);
                        apiResponse.setExplicitDismissal(Boolean.TRUE);
                        apiResponse.setLevel(Level.INFO);
                        apiResponse.setCode("1018");
                        apiResponse.setMessage(messageSource.getMessage("1018", new Object[] {userNameList.toString()}, LocaleContextHolder.getLocale()));

                    } catch (UserDoesNotExistException e) {
                        apiResponse.setStatus("failure");
                        apiResponse.setData(null);
                        apiResponse.setLevel(Level.ERROR);
                        apiResponse.setCode("1026");
                        apiResponse.setMessage(messageSource.getMessage("1042", new Object[] {e.getMessage()}, LocaleContextHolder.getLocale()));
                        return apiResponse;
                    } catch (Exception e) {
                        throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
                    }

                } else {
                    apiResponse.setExplicitDismissal(Boolean.TRUE);
                    apiResponse.setLevel(Level.INFO);
                    apiResponse.setCode("1041");
                    apiResponse.setMessage(messageSource.getMessage("1041", new Object[] {userNameList.toString()}, LocaleContextHolder.getLocale()));
                }

                LOG.debug(apiResponse.getMessage());
            }

        }

        return apiResponse;
    }

	/**
	 * 
	 * @param searchAndSortCriteria {
				  "pageSize": 2147483647,
				  "pageNumber": 0,
				  "searchMap": {},
				  "sortMap": {
				    "permissionGroupName": "ASC"
				  }
				}
	 * @return {
				  "status": "success",
				  "code": "200",
				  "message": "Request processed successfully",
				  "level": null,
				  "uniqueKey": null,
				  "token": null,
				  "explicitDismissal": null,
				  "data": {
				    "groupPermissions": {
				      "warehouse-manager": [
				        "create-assignment",
				        "view-report-productivity",
				        "configure-location",
				        "create-canvas",
				        "delete-canvas"
				      ],
				      "basic-authenticated-user": [
				        "authenticated-user"
				      ],
				      "supervisor": [
				        "user-management-canvas-view",
				        "user-management-canvas-edit",
				        "pick-monitoring-canvas-view",
				        "pick-monitoring-canvas-edit",
				        "user-list-download-excel",
				        "user-list-download-pdf",
				        "edit-canvas",
				        "delete-user",
				        "disable-user",
				        "disable-user",
				        "enable-user",
				        "edit-multi-user"
				      ],
				      "admin": [
				        "edit-canvas",
				        "disable-user",
				        "user-management-canvas-view",
				        "pick-monitoring-canvas-view",
				        "authenticated-user"
				      ],
				      "picker": [
				        "create-assignment",
				        "authenticated-user",
				        "user-management-canvas-view",
				        "pick-monitoring-canvas-view",
				        "disable-user"
				      ]
				    },
				    "availablePermissions": [
				      "edit-canvas",
				      "disable-user",
				      "user-management-canvas-view",
				      "pick-monitoring-canvas-view",
				      "authenticated-user",
				      "create-assignment",
				      "user-management-canvas-edit",
				      "pick-monitoring-canvas-edit",
				      "user-list-download-excel",
				      "user-list-download-pdf",
				      "delete-user",
				      "disable-user",
				      "enable-user",
				      "edit-multi-user",
				      "view-report-productivity",
				      "configure-location",
				      "create-canvas",
				      "delete-canvas"
				    ]
				  }
				}
	 */
	@ResponseView(UserGroupsPermissionsDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/groups/permissions")
    public
    @ResponseBody
    ApiResponse<UserGroupsPermissionsView> getUserGroupsPermissionsBySearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

        final ApiResponse<UserGroupsPermissionsView> apiResponse = new ApiResponse<UserGroupsPermissionsView>();
        List<PermissionGroup> permissionGroupList = null;
        UserGroupsPermissionsView userGroupsPermissionsView = null;
        try {
        	permissionGroupList = userService.getPermissionGroupsBySearchAndSortCriteria(searchAndSortCriteria);
            userGroupsPermissionsView = new UserGroupsPermissionsView(permissionGroupList);
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        apiResponse.setData(userGroupsPermissionsView);

		LOG.debug("Size{}",permissionGroupList.size());
        return apiResponse;
    }
	
	
	public List<Canvas> getCanvasListFromCanvasViewList(
			List<CanvasView> canvasViewList) {
		List<Canvas> canvasList = new ArrayList<Canvas>();
		for(CanvasView canvasView : canvasViewList){
			Canvas canvas = canvasView.getCanvas();
			canvasList.add(canvas);
		}
		return canvasList;
	}	
	


    /**
     *  url = http://[HostName]:[PortNumber]/[Context/lucas-api]/users/groups/permissionslist
      following is the input json for the service
     [
         "jack123",
         "jill123",
         "joe123"
     ]
     *  sample JSON expected as input to this method

    {
        "status": "success",
            "code": "200",
            "message": "Request processed successfully",
            "level": null,
            "uniqueKey": null,
            "token": null,
            "explicitDismissal": null,
            "data": [
        {
            "username": "jack123",
                "groups": [
            {
                "groupName": "picker",
                    "permission": [
                "create-assignment",
                        "authenticated-user",
                        "user-management-canvas-view",
                        "pick-monitoring-canvas-view",
                        "disable-user"
                ]
            },
            {
                "groupName": "supervisor",
                    "permission": [
                "user-management-canvas-view",
                        "user-management-canvas-edit",
                        "pick-monitoring-canvas-view",
                        "pick-monitoring-canvas-edit",
                        "user-list-download-excel",
                        "user-list-download-pdf",
                        "edit-canvas",
                        "delete-user",
                        "disable-user",
                        "disable-user",
                        "enable-user",
                        "edit-multi-user",
                        "group-maintenance-create",
                        "group-maintenance-edit",
                        "create-product",
                        "view-product",
                        "edit-product",
                        "delete-product",
                        "view-users-createeditform-widget",
                        "view-users-searchusergrid-widget",
                        "view-users-picklinebywave-widget",
                        "view-users-groupmaintenance-widget",
                        "view-users-searchproductgrid-widget"
                ]
            }
            ]
        },
        {
            "username": "jill123",
                "groups": [
            {
                "groupName": "picker",
                    "permission": [
                "create-assignment",
                        "authenticated-user",
                        "user-management-canvas-view",
                        "pick-monitoring-canvas-view",
                        "disable-user"
                ]
            }
            ]
        },
        {
            "username": "admin123",
                "groups": [
            {
                "groupName": "warehouse-manager",
                    "permission": [
                "create-assignment",
                        "view-report-productivity",
                        "configure-location",
                        "create-canvas",
                        "delete-canvas"
                ]
            },
            {
                "groupName": "basic-authenticated-user",
                    "permission": [
                "authenticated-user"
                ]
            }
            ]
        }
        ]
    }
    */

    @ResponseView(UserGroupPermissionDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/groups/permissionslist")
    public @ResponseBody ApiResponse<List<UserPermissionView>> getUsersGroupsWithPermission(@RequestBody final List<String> userNameList){
        final ApiResponse<List<UserPermissionView>> apiResponse = new ApiResponse<List<UserPermissionView>>();
        try {
            if (userNameList != null && userNameList.size() > 0) {
                final List<UserPermissionView> userViewList = new ArrayList();
                final List<User> userList = this.userService.getUserListFromUserNameList(userNameList);
                for (User user : userList) {
                    userViewList.add(new UserPermissionView(user));
                }
                apiResponse.setData(userViewList);
                return apiResponse;
            }
        }catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        return apiResponse;
    }

    /**
     * Controller method to take the post data and assigned the groups to the user
     * 
     * @param usersAssignedGroupsDTO
     * @return
     */
    @ResponseView(UserFormView.class)
    @RequestMapping(method = RequestMethod.POST, value="/groups/assign")
    public @ResponseBody ApiResponse<Object> saveUsersAssignedGroups(@RequestBody UsersAssignedGroupsDTO usersAssignedGroupsDTO){

        ApiResponse<Object> apiResponse = new ApiResponse<Object>();

        Boolean isCreated = null;

        LOG.debug("**** UserController.saveUser() **** user = " + usersAssignedGroupsDTO.getUserName());
        LOG.debug("**** UserController.saveUser() **** newAssignedGroups = " + usersAssignedGroupsDTO.getAssignedGroups());

        if(usersAssignedGroupsDTO != null) {
            isCreated = userService.saveUsersAssignedGroups(usersAssignedGroupsDTO);
        }

        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        if(isCreated){
            apiResponse.setCode("1012");
            apiResponse.setMessage(messageSource.getMessage("1012", new Object[] {usersAssignedGroupsDTO.getUserName()}, LocaleContextHolder.getLocale()));
        } else {
            apiResponse.setCode("1017");
            apiResponse.setMessage(messageSource.getMessage("1017", new Object[] {usersAssignedGroupsDTO.getUserName()}, LocaleContextHolder.getLocale()));

        }
        LOG.debug(apiResponse.getMessage());

        return apiResponse;
    }
    
    /**
     * Controller method to take the post data of selected users and set the retrain voice model for
     * the selected users
     * 
     * @param userList
     * @return ApiResponse
     */
    @RequestMapping(method = RequestMethod.POST, value = "/retrainvoice")
    public @ResponseBody ApiResponse<Map> retrainVoiceModelForSelectedUsers(@RequestBody List<String> userList) {
        final ApiResponse<Map> apiResponse = new ApiResponse<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        if (userList != null) {
            resultMap = userService.retrainVoiceModelForSelectedUsers(userList);
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        apiResponse.setData(resultMap);
        apiResponse.setCode("1025");
        apiResponse.setMessage(messageSource.getMessage("1025", userList.toArray(), LocaleContextHolder.getLocale()));
        LOG.debug(apiResponse.getMessage());
        return apiResponse;
    }


    /**
     * GET  -> get all the permission_group records for a user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/groups")
    @ResponseBody
    public ApiResponse<Map> getUserAssignedGroups(@RequestBody final List<String> userNameList) {
        LOG.debug("REST request to get all Groups for users");
        final ApiResponse <Map> apiResponse = new ApiResponse <Map>();
        Map <String, Object> resultMap = new HashMap<String, Object>();

        if (userNameList != null && userNameList.size() != 0) {
            try {
                resultMap = userService.getAssignedUserGroups(userNameList);
            } catch (Exception e) {
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1023");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1023", userNameList.toArray(), LocaleContextHolder.getLocale()));
                return apiResponse;
            }
        }
        else {
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1023");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1023", userNameList.toArray(), LocaleContextHolder.getLocale()));
            return apiResponse;
        }

        apiResponse.setLevel(Level.INFO);
        apiResponse.setCode("1022");
        apiResponse.setStatus("success");
        apiResponse.setMessage(messageSource.getMessage("1022", userNameList.toArray(), LocaleContextHolder.getLocale()));
        apiResponse.setData(resultMap);

        LOG.debug("Size{}", resultMap.size());
        return apiResponse;
    }


    /**
     * @param username accept the instance of the java.lang.String containing
     *                 the persistence state user name for fetching operation.
     * @return the Canvas data for the user and wrapped into the Canvas view.
     * @Author Adarsh kumar
     * <p/>
     * getOpenCanvasesForUser() provide the functionality for getting the open canvases
     * from the database based on the persistence state userId send form client.
     * url for service is = /users/{username}/canvases/open
     * it accept the http get request.
     */
    @ResponseView(OpenCanvasView.class)
    @RequestMapping(value = "/{username}/canvases/open", method = RequestMethod.GET)
    public
    @ResponseBody
    ApiResponse<List<OpenUserCanvasesView>> getOpenCanvasesForUser(@PathVariable String username) throws Exception {
        final ApiResponse<List<OpenUserCanvasesView>> apiResponse = new ApiResponse<List<OpenUserCanvasesView>>();
        try {
            if (username != null && username.length() > 0) {
                final User user = this.userService.getUser(username);
                final List<OpenUserCanvas> openUserCanvases = this.uiService.getAllOpenCanvasForUser(user.getUserId());
                if (openUserCanvases != null && !openUserCanvases.isEmpty()) {
                    final List<OpenUserCanvasesView> openUserCanvasesViews = new ArrayList<>(openUserCanvases.size());
                    for (OpenUserCanvas openUserCanvas : openUserCanvases) {
                        openUserCanvasesViews.add(new OpenUserCanvasesView(openUserCanvas));
                    }
                    apiResponse.setData(openUserCanvasesViews);
                } else {
                    apiResponse.setMessage("Open Canvas Not Found for User "+ username);
                    apiResponse.setStatus("success");
                }
            } else {
                apiResponse.setMessage("User Name is Null Or Empty");
                apiResponse.setStatus("failure");
            }
        } catch (Exception e) {
            apiResponse.setMessage("Open Canvas Not Found for User  " + username);
            apiResponse.setStatus("success");
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        return apiResponse;
    }

    /**
     * The controller method gives an end point to save the active canvases for a user
     * @param userView
     * @return
     * @throws LucasRuntimeException
     * @throws Exception modified date: 5/13/2015
     */

    @RequestMapping(value = "/canvases/active", method = RequestMethod.POST)
    public @ResponseBody ApiResponse<Object> saveActiveCanvasesForUser(@RequestBody UserView userView) throws LucasRuntimeException, Exception {

        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        Boolean isSaved = null;

        if (userView != null && userView.getUser() != null && userView.getUser().getActiveCanvas() != null) {
            try {
                isSaved = this.userService.updateUserActiveCanvas(userView.getUser());
                apiResponse.setExplicitDismissal(Boolean.FALSE);
                apiResponse.setLevel(Level.INFO);
                apiResponse.setCode("1017");
                if (isSaved) {
                    apiResponse.setMessage(messageSource.getMessage("1017"
                            , new Object[] {userView.getUser().getId()}
                            , LocaleContextHolder.getLocale()));
                } else {
                    apiResponse.setMessage(messageSource.getMessage("1029"
                            , new Object[] {userView.getUser().getId()}
                            , LocaleContextHolder.getLocale()));
                }
            } catch (Exception e) {
                apiResponse.setMessage(messageSource.getMessage("1029"
                        , new Object[] {userView.getUser().getId()}
                        , LocaleContextHolder.getLocale()));
            }
        } else {
            apiResponse.setMessage(messageSource.getMessage("1029"
                    , new Object[] {userView.getUser().getId()}
                    , LocaleContextHolder.getLocale()));
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);

        return apiResponse;
    }


    /**
     * The controller method gives an end point to save the open canvases for a user
     * @param userView
     * @return
     * @throws LucasRuntimeException
     * @throws Exception
     */
    @RequestMapping(value = "/canvases/open", method = RequestMethod.POST)
    public @ResponseBody ApiResponse<Object> saveOpenCanvasesForUser(@RequestBody UserView userView) throws LucasRuntimeException, Exception {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        if (userView != null && userView.getUser() != null && userView.getUser().getOpenUserCanvases() != null) {
            try {
                final List<String> responseData=this.userService.updateUserOpenCanvas(userView.getUser());
                apiResponse.setExplicitDismissal(Boolean.FALSE);
                apiResponse.setLevel(Level.INFO);
                apiResponse.setCode("1017");
                if(responseData.size()==0){
                apiResponse.setMessage(messageSource.getMessage("1017"
                        , new Object[]{userView.getUser().getId()}
                        , LocaleContextHolder.getLocale()));
                }else{
                    apiResponse.setMessage(messageSource.getMessage("1029"
                            , new Object[]{userView.getUser().getId()}
                            , LocaleContextHolder.getLocale())+""+responseData.toString());
                }
            } catch (Exception e) {
                apiResponse.setMessage(messageSource.getMessage("1029"
                        , new Object[]{userView.getUser().getId()}
                        , LocaleContextHolder.getLocale()));
            }
        } else {
            apiResponse.setMessage(messageSource.getMessage("1029"
                    , new Object[]{userView.getUser().getId()}
                    , LocaleContextHolder.getLocale()));
        }
        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        return apiResponse;
    }
    
    /**
     * The controller method gives an end point to display the active canvases for a user
     * @param username
     * @return
     * @throws LucasRuntimeException
     * @throws Exception
     */

    @ResponseView(ActiveCanvasView.class)
    @RequestMapping(value = "{username}/canvases/active", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<CanvasView> getActiveCanvasForUser(@PathVariable String username) throws LucasRuntimeException, Exception {

        final ApiResponse<CanvasView> apiResponse = new ApiResponse<CanvasView>();
        try {
            if (username != null && username.length() > 0) {
                Canvas canvas = this.userService.getUserActiveCanvas(username);
                if (canvas != null) {
                    canvas = userService.getUserActiveCanvas(username);
                    apiResponse.setData(new CanvasView(canvas));

                } else {
                    apiResponse.setMessage(messageSource.getMessage("1034"
                            , new Object[]{username}
                            , LocaleContextHolder.getLocale()));
                }
            } else {
                apiResponse.setMessage(messageSource.getMessage("1035"
                        , new Object[]{username}
                        , LocaleContextHolder.getLocale()));
            }
        } catch (Exception e) {
            apiResponse.setMessage(messageSource.getMessage("1034"
                    , new Object[]{username}
                    , LocaleContextHolder.getLocale()));
        }

        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        apiResponse.setCode("1017");
        return apiResponse;
    }

    /**
     * This method save the user preferences data
     *
     * @param map Hashmap of user preferences data to be saved
     * @return
     * @throws LucasRuntimeException
     * @throws Exception

     */
    @RequestMapping(method = RequestMethod.POST, value = "/preferences/save")
    @ResponseBody
    public ApiResponse<Map<String, String>> saveUserPreferencesData(@RequestBody final Map<String, String> map) {
        LOG.debug("REST request to save user preferences data");
        final ApiResponse <Map<String, String>> apiResponse = new ApiResponse <Map<String, String>>();
        Map <String, String> resultMap = new HashMap<String, String>();
        Object[] objectArray = new Object[1];
        boolean created = false;

        try {
            resultMap = userService.saveUserPreferencesData(map);
            LOG.debug("UserController.saveUserPreferencesData: return value userService.saveUserPreferencesData: code {}", resultMap.get("statusCode"));
            LOG.debug("UserController.saveUserPreferencesData: return value userService.saveUserPreferencesData: message {}", resultMap.get("message"));
            if (resultMap.get("statusCode").equals("1033")) {
                // success status
                created = true;
            }
        } catch (Exception e) {
            LOG.debug("UserController.saveUserPreferencesData: Exception caught: {}", e);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1032");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1032", objectArray, LocaleContextHolder.getLocale()));
            return apiResponse;
        }

        if (created) {
            LOG.debug("UserController.saveUserPreferencesData: successful response 1033 returned");
            apiResponse.setLevel(Level.INFO);
            apiResponse.setCode("1033");
            apiResponse.setStatus("success");
            apiResponse.setMessage(messageSource.getMessage("1033", objectArray, LocaleContextHolder.getLocale()));
        } else {
            LOG.debug("UserController.saveUserPreferencesData: error response 1032 returned");
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1032");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1032", objectArray, LocaleContextHolder.getLocale()));
            return apiResponse;
        }

        LOG.debug("Size{}", resultMap.size());
        return apiResponse;
    }

    /**
     * GET  -> get the user_preferences data for a user
     *
     * @param userName
     * @return
     * @throws LucasRuntimeException
     * @throws Exception

     */
    @RequestMapping(method = RequestMethod.GET, value = "/preferences/{userName}")
    @ResponseBody
    public ApiResponse<Map<String, String>> getUserPreferencesData(@PathVariable String userName) {
        LOG.debug("REST request to get user preferences data");
        final ApiResponse <Map<String, String>> apiResponse = new ApiResponse <Map<String, String>>();
        Map <String, String> resultMap = new HashMap<String, String>();
        Object[] objectArray = new Object[1];

        if (userName != null && !userName.isEmpty()) {
            try {
                resultMap = userPreferencesService.findOneUserPreferenceByUserName(userName);
            } catch (Exception e) {
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1032");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1032", objectArray, LocaleContextHolder.getLocale()));
                return apiResponse;
            }
        }
        else {
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1032");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1032", objectArray, LocaleContextHolder.getLocale()));
            return apiResponse;
        }

        apiResponse.setLevel(Level.INFO);
        apiResponse.setCode("1031");
        apiResponse.setStatus("success");
        apiResponse.setMessage(messageSource.getMessage("1031", objectArray, LocaleContextHolder.getLocale()));
        apiResponse.setData(resultMap);

        LOG.debug("Size{}", resultMap.size());
        return apiResponse;
    }

    /*
     * This is called to retrieve selected properties of all users by passing in a list of properties
     * names ie. firstName, lastName, skill
     *
     * @param propertyList
     * sample JSON expected as input to this method
     *
     *    ["firstName", "lastName", "skill", "emailAddress"]
     *
     * @return ApiResponse list of column names and values for all users
     */
    @RequestMapping(value="/userproperties", method = RequestMethod.POST)
    public @ResponseBody ApiResponse<List<Map<String, String>>> getAllUsersProperties(@RequestBody List<String> propertyList)
            throws LucasRuntimeException, Exception {
        LOG.debug("UserController.getAllUsersProperties: Start ");
        LOG.debug("UserController.getAllUsersProperties: propertyList - " + propertyList);

        ApiResponse<List<Map<String, String>>> apiResponse = new ApiResponse<List<Map<String, String>>>();
        List<Map<String, String>> userObjectList = null;

        if (propertyList == null || propertyList.size() == 0)
        {
            apiResponse.setCode("1037");
            apiResponse.setMessage(messageSource.getMessage("1037", new Object[] {propertyList}, LocaleContextHolder.getLocale()));
            apiResponse.setStatus("failure");
            return apiResponse;
        }

        try {
            LOG.debug("UserController.getAllUsersProperties: calling userService ");
            userObjectList = userService.getAllUsersProperties(propertyList);
            LOG.debug("UserController.getAllUsersProperties: return from userService ");
        }
        catch (Exception e) {
            LOG.debug("LucasBusinessException thrown...");
            apiResponse.setExplicitDismissal(Boolean.FALSE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1036");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1036", new Object[] {propertyList}, LocaleContextHolder.getLocale()));
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
        }

        if (userObjectList == null)
        {
            apiResponse.setCode("1037");
            apiResponse.setMessage(messageSource.getMessage("1037", new Object[] {propertyList}, LocaleContextHolder.getLocale()));
            apiResponse.setStatus("failure");
        }
        else {
            apiResponse.setData(userObjectList);
            LOG.debug("userObjectList.size{}", userObjectList.size());
        }
        return apiResponse;
    }

}
