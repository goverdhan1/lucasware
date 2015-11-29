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
package com.lucas.services.user;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.ui.OpenUserCanvasDAO;
import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.dto.user.UserFormFieldsDTO;
import com.lucas.dto.user.UsersAssignedGroupsDTO;
import com.lucas.entity.support.MultiEditable;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.services.application.CodeLookupService;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import com.lucas.services.ui.UIService;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.services.util.DateUtils;
import com.lucas.services.util.MultiCompare;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

import org.activiti.engine.impl.util.json.JSONException;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.Shift;
import com.lucas.entity.user.SupportedLanguage;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.exception.PermissionGroupDoesNotExistException;
import com.lucas.exception.UserDoesNotExistException;


@Service("userService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;
    private final PermissionGroupDAO permissionGroupDAO;
    private final PermissionDAO permissionDAO;
    private final ShiftService shiftService;
    private final WmsUserService wmsUserService;
    private final SupportedLanguageService supportedLanguageService;
    private final UIService uiService;
    private final MessageSource messageSource;
    private final SecurityService securityService;
    private final GroupService groupService;
    private final OpenUserCanvasDAO openUserCanvas;
    private final CodeLookupService codeLookupService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Inject
    public UserServiceImpl(UserDAO userDAO,
                           PermissionGroupDAO permissionGroupDAO, PermissionDAO permissionDAO,
                           UIService uiService, MessageSource messageSource,
                           ShiftService shiftService,
                           WmsUserService wmsUserService,
                           SupportedLanguageService supportedLanguageService,
                           SecurityService securityService,
                           GroupService groupService,
                           OpenUserCanvasDAO openUserCanvas,
                           CodeLookupService codeLookupService, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.permissionGroupDAO = permissionGroupDAO;
        this.permissionDAO = permissionDAO;
        this.uiService = uiService;
        this.shiftService = shiftService;
        this.wmsUserService = wmsUserService;
        this.messageSource = messageSource;
        this.supportedLanguageService = supportedLanguageService;
        this.securityService = securityService;
        this.groupService = groupService;
        this.openUserCanvas = openUserCanvas;
        this.codeLookupService = codeLookupService;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return userDAO.findByUserName(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserID(Long userId) {
        return userDAO.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String username) {
        return getUser(username, true);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String username, boolean includePermission) {
        User user = userDAO.getUser(username);
        if (includePermission) {
            if (null != user) {
                setUserPermissionSet(user);
            }
        }
        return user;
    }

    /**
     * Access modifier was changed from private to default to enhance
     * testability
     *
     * @param user
     * @throws JSONException
     */
    void setUserPermissionSet(User user) {
        List<PermissionGroup> userPermissionGroupList = user
                .getUserPermissionGroupList();
        if (!CollectionUtils.isEmpty(userPermissionGroupList)) {
            LOG.debug(" User Permission Group List Size{} ",
                    userPermissionGroupList.size());
            for (PermissionGroup permissionGroup : CollectionsUtilService.nullGuard(userPermissionGroupList)) {
                Set<Permission> p = permissionGroup.getPermissionsSet();
                user.setPermissionsSet(p);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByPlainTextCredentials(String username, String password) {
        StopWatch clock = new StopWatch();
        clock.start();
        User returnedUser = null;

        User user = getUser(username, true);

        if (user != null) {
        	if (password != null && !password.isEmpty() && passwordEncoder.matches(password, user.getHashedPassword())) {
                returnedUser = user;
            }
        }
        clock.stop();
        LOG.debug("It takes " + clock.getTime() + " milliseconds");
        return returnedUser;
    }

    @Override
    public User findUserByHashedCredentials(String username,
                                            String encryptedPassword) {
        User returnedUser = null;
        User user = userDAO.findByUserName(username);
        if (user != null) {
            if (StringUtils.equals(encryptedPassword, user.getHashedPassword())) {
                returnedUser = user;
            }
        }
        return returnedUser;
    }

    /**
     * Determines if the username and passed in hashedPassword will result in
     * positive authentication
     *
     * @param userName
     * @param hashedPassword
     * @return
     */
    @Override
    public boolean isAuthenticateByHashedCredentials(String userName,
                                                     String hashedPassword) {
        boolean boo = false;
        User retrievedUser = userDAO.findByUserName(userName);
        if (retrievedUser != null) {

            if (StringUtils.equals(hashedPassword,
                    retrievedUser.getHashedPassword())) {
                boo = true;
            }
        }
        return boo;
    }

    /**
     * Determines if the username and passed in plainTextPassword will result in
     * positive authentication
     *
     * @param userName
     * @param plainTextPassword
     * @return
     */
    @Override
    public boolean isAuthenticateByPlainTextCredentials(String userName,
                                                        String plainTextPassword) {
        boolean boo = false;
        User retrievedUser = userDAO.findByUserName(userName);
        if (retrievedUser != null) {
            if (retrievedUser.getHashedPassword() != null) {
            	boo = passwordEncoder.matches(plainTextPassword, retrievedUser.getHashedPassword());
            }
        }
        return boo;
    }

    @Transactional
    @Override
    public void updateUser(User user) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {
        User retrievedUser = userDAO.findByUserId(user.getUserId());
        if (retrievedUser == null) {
            throw new UserDoesNotExistException(String.format(
                    "No user exists with username %s. Cannot update user!",
                    user.getUserName()));
        }

        Canvas activateCanvas = user.getActiveCanvas();
        if (activateCanvas != null) {
            activateCanvas = uiService.findByCanvasId(activateCanvas
                    .getCanvasId());
        }

        retrievedUser.setActiveCanvas(activateCanvas);

        List<OpenUserCanvas> openUserCanvases = user.getOpenUserCanvases();

        if (openUserCanvases != null && !openUserCanvases.isEmpty()) {
            for (OpenUserCanvas openCanvas : CollectionsUtilService
                    .nullGuard(openUserCanvases)) {
                Canvas canvas = uiService.findByCanvasId(openCanvas.getCanvas()
                        .getCanvasId());
                openCanvas.setCanvas(canvas);
            }
        }

        retrievedUser.setOpenUserCanvases(openUserCanvases);
        retrievedUser.setAuditUsername(securityService);
        userDAO.save(retrievedUser);
    }

    private void adjustUserGroupsForExistingGroups(User user, List<PermissionGroup> groups) {
        if (groups != null) {
            List<PermissionGroup> newGroups = new ArrayList<>();
            for (PermissionGroup group : groups) {
                if (group.getPermissionGroupId() == null) {
                    PermissionGroup existingGroup = this.permissionGroupDAO.getPermissionGroupByName(group.getPermissionGroupName());
                    if (existingGroup != null)
                        newGroups.add(existingGroup);
                    else
                        newGroups.add(group);
                }
                else
                    newGroups.add(group);
            }
            user.setUserPermissionGroupList(newGroups);
        }
    }


    @Transactional
    @Override
    public boolean createUser(User user) throws Exception {
        /*LOG.debug("Entering UserServiceImpl.createUser()...");
        LOG.debug("Created User  user object:" + ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));*/
        String plainTextPassword = user.getPlainTextPassword();
        User retrievedUser = userDAO.findByUserName(user.getUserName());

        if (retrievedUser != null && retrievedUser.getUserName().equals(user.getUserName())) {
            // saving existing user

            /* check if we need to save new password and re-hash the password
             * if plainTextPassword is null or blank, then don't update existing password and hashedPassword.
             * if plainTextPassword is the same as the existing password, then don't update hashedPassword.
             * if plainTextPassword is not null nor blank, not the same as existing password, then update hashedPassword
             */
            if ((plainTextPassword != null) && (plainTextPassword.length() > 0))
            {
                if (isAuthenticateByPlainTextCredentials(user.getUserName(), plainTextPassword) == false)
                {
                    retrievedUser.setPlainTextPassword(plainTextPassword);
                    retrievedUser.setHashedPassword(passwordEncoder.encode(plainTextPassword));
                }
            }

            //retrievedUser.setHostLogin(user.getHostLogin());
            //retrievedUser.setHostHashedPassword(stringEncryptor.encrypt(user.getHostPassword()));
            retrievedUser.setAmdLanguage(user.getAmdLanguage());
            retrievedUser.setJ2uLanguage(user.getJ2uLanguage());
            retrievedUser.setHhLanguage(user.getHhLanguage());
            retrievedUser.setU2jLanguage(user.getU2jLanguage());
            retrievedUser.setStartDate(user.getStartDate());
            retrievedUser.setBirthDate(user.getBirthDate());
            retrievedUser.setEmailAddress(user.getEmailAddress());
            retrievedUser.setEmployeeNumber(user.getEmployeeNumber());
            retrievedUser.setFirstName(user.getFirstName());
            retrievedUser.setLastName(user.getLastName());
            retrievedUser.setMobileNumber(user.getMobileNumber());
            retrievedUser.setTitle(user.getTitle());
            retrievedUser.setEnable(user.isEnable());
            retrievedUser.setSkill(user.getSkill());
            retrievedUser.setShift(user.getShift());
            retrievedUser.setRetrainVoiceModel(user.getRetrainVoiceModel());
            retrievedUser.setCurrentWorkType(user.getCurrentWorkType());
            retrievedUser.setSeeHomeCanvasIndicator(user.getSeeHomeCanvasIndicator());
            retrievedUser.setUpdatedDateTime(user.getUpdatedDateTime());
            //retrievedUser.setWmsUser(wmsUser);

            if(user.getActiveCanvas() != null) {
                final Canvas activeCanvas = uiService.getCanvasByName(user.getActiveCanvas().getName());
                retrievedUser.setActiveCanvas(activeCanvas);
            }

            if (user.getUserPreferences() != null) {
                // now update the user preferences by the passed in data

                if (user.getUserPreferences().getDataRefreshFrequency() != null) {
                    retrievedUser.getUserPreferences().setDataRefreshFrequency(user.getUserPreferences().getDataRefreshFrequency());
                }
                if (user.getUserPreferences().getDateFormat() != null) {
                    retrievedUser.getUserPreferences().setDateFormat(user.getUserPreferences().getDateFormat());
                }
                if (user.getUserPreferences().getTimeFormat() != null) {
                    retrievedUser.getUserPreferences().setTimeFormat(user.getUserPreferences().getTimeFormat());
                }
            }

            // update the user
            retrievedUser.setAuditUsername(securityService);
            adjustUserGroupsForExistingGroups(retrievedUser, user.getUserPermissionGroupList());
            userDAO.save(retrievedUser);
            LOG.debug("Updated User :" + ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));
        } else {
           
            // creating new user
           
            if (user.getHashedPassword() == null) {
            	 // plainTextPassword must be provided for new user
                if ((plainTextPassword == null) || (plainTextPassword.length() <= 0))
                {
                    throw new Exception("Password is required");
                }
                user.setHashedPassword(passwordEncoder.encode(plainTextPassword));
			}
			if(user.getActiveCanvas() != null) {
                final Canvas activeCanvas = uiService.getCanvasByName(user.getActiveCanvas().getName());
                user.setActiveCanvas(activeCanvas);
            }
            adjustUserGroupsForExistingGroups(user, user.getUserPermissionGroupList());

            user.setAuditUsername(securityService);
            userDAO.save(user);
        }

        return true;
    }


    @Transactional
    @Secured({"delete-user"})
    @Override
    public Map<String, String> deleteUser(Long userId) {
        final Map<String, String> resultMap = new HashMap<String, String>();

        // Checks here to see other dependencies, end emails etc..
        User retrievedUser = null;
        if (userId != null) {
            retrievedUser = userDAO.load(userId);
            if (retrievedUser != null) {
                // we do not delete user from the database - just mark the flag as deleted
                retrievedUser.setDeletedIndicator(true);
                retrievedUser.setAuditUsername(securityService);
                this.userDAO.save(retrievedUser);
                resultMap.put(retrievedUser.getUserName(), "Deleted Successfully");
            } else {
                LOG.debug(String
                        .format("Did not delete any user as no user with userId %s found.",
                                userId));
                resultMap.put("INVALID_USERNAME_ERROR_CODE", "INVALID_USERNAME_ERROR_CODE");
            }
        } else {
            LOG.debug("Did not delete any user as null userId was passed in.");
            resultMap.put("BLANK_USERNAME_ERROR_CODE", "BLANK_USERNAME_ERROR_CODE");
        }
        return resultMap;
    }

    @Transactional
    @Secured({"delete-user"})
    @Override
    public Map<String, String> deleteUser(String username) {
        final Map<String, String> resultMap = new HashMap<String, String>();

        // Checks here to see other dependencies, end emails etc..
        User retrievedUser = null;
        if (username != null) {
            retrievedUser = userDAO.findByUserName(username);
            if (retrievedUser != null) {
                // we do not delete user from the database - just mark the flag as deleted
                retrievedUser.setDeletedIndicator(true);
                retrievedUser.setAuditUsername(securityService);
                this.userDAO.save(retrievedUser);
                resultMap.put(retrievedUser.getUserName(), "Deleted Successfully");
            } else {
                LOG.debug(String
                        .format("Did not delete any user as no user with username %s found.",
                                username));
                resultMap.put("INVALID_USERNAME_ERROR_CODE", "INVALID_USERNAME_ERROR_CODE");
            }
        } else {
            LOG.debug("Did not delete any user as null username was passed in.");
            resultMap.put("BLANK_USERNAME_ERROR_CODE", "BLANK_USERNAME_ERROR_CODE");
        }
        return resultMap;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getUserList(List<String> userNameList) {

        final List<User> userList = new ArrayList<>();

        for (final String userName : userNameList) {

            final User retrievedUser = userDAO.findByUserName(userName);

            if (retrievedUser == null) {
                throw new UserDoesNotExistException(String.format(
                        "No user exists with username %s. Cannot retrieve user!",
                        userName));
            }

            setUserPermissionSet(retrievedUser);

            userList.add(retrievedUser);
        }

        return userList;
    }


    @Transactional
    @Secured({"delete-user"})
    @Override
    public Map<String, String> deleteUser(User user) {
        final Map<String, String> resultMap = new HashMap<String, String>();

        // Checks here to see other dependencies, end emails etc..
        if (user != null && user.getUserId() != null) {
            User retrievedUser = userDAO.load(user.getUserId());
            if (retrievedUser != null) {
                retrievedUser.setDeletedIndicator(true);
                retrievedUser.setAuditUsername(securityService);
                this.userDAO.save(retrievedUser);
                resultMap.put(user.getUserName(), "Deleted Successfully");
            } else {
                LOG.debug(String
                        .format("Did not delete any user as no user with username %s found.",
                                user.getUserName()));
                resultMap.put("INVALID_USERNAME_ERROR_CODE", "INVALID_USERNAME_ERROR_CODE");
            }
        } else {
            LOG.debug("Did not delete any user as null user was passed in.");
            resultMap.put("BLANK_USERNAME_ERROR_CODE", "BLANK_USERNAME_ERROR_CODE");
        }
        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('view-report-productivity')")
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUserList(String pageSize, String pageNumber) {
        return userDAO.getUserList(pageSize, pageNumber);
    }

    @Override
    public Long getUserCount() {
        return userDAO.getUserCount();
    }

    @Override
    @Secured({"disable-user", "enable-user"})
    public Map<String, String> enableOrDisableUser(List<String> userList, boolean enable) {
        final Map<String, String> resultMap = new HashMap<String, String>();

        for (final String userName : userList) {
            final User retrievedUser = userDAO.findByUserName(userName);
            if (retrievedUser == null) {
                throw new UserDoesNotExistException(String.format(
                        "No user exists with username %s. Cannot update user!",
                        userName));
            }
            //to bypass the NotNull Constraint on User
            retrievedUser.setPlainTextPassword("-----");
            retrievedUser.setAuditUsername(securityService);
            if (enable) {
                retrievedUser.setEnable(Boolean.TRUE);
                final User user = this.userDAO.save(retrievedUser);
                resultMap.put(user.getUserName(), "Enabled Successfully");
            } else {
                retrievedUser.setEnable(Boolean.FALSE);
                final User user = this.userDAO.save(retrievedUser);
                resultMap.put(user.getUserName(), "Disabled Successfully");
            }

        }
        return resultMap;
    }

    @Override
    @Secured({"disable-user"})
    @Transactional(readOnly = false)
    public Map<String, String> disableUser(List<String> userList) {
        return this.enableOrDisableUser(userList, false);
    }


    @Override
    @Secured({"enable-user"})
    @Transactional(readOnly = false)
    public Map<String, String> enableUser(List<String> userList) {
        return this.enableOrDisableUser(userList, true);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveUsers(List<User> userList) throws Exception {
        for (User user : userList) {
            try {
                this.createUser(user);
            }
            catch (Exception e)
            {
                throw (e);
            }
        }
    }


    /**
     * This method updated the deleted flag to true. If any errors then everything should roll back
     *
     * @param userNameList
     * @return
     */
    @Transactional
    @Override
    @Secured({"delete-user"})
    public Map<String, String> deleteUser(List<String> userNameList) {
        final Map<String, String> userNameAndStatusMap = new HashedMap();
        Map<String, String> preProcessUserNameMap = new HashedMap();

        preProcessUserNameMap = preProcessUsername(userNameList);

        if (preProcessUserNameMap.containsValue("BLANK_USERNAME_ERROR_CODE")) {
            LOG.debug("Cannot delete blank user");
            return preProcessUserNameMap;
        } else if (preProcessUserNameMap.containsValue("INVALID_USERNAME_ERROR_CODE")) {
            LOG.debug("Cannot delete invalid user");
            return preProcessUserNameMap;
        }

        for (final String userName : userNameList) {
            if (userName != null) {
                try {
                    // list of usernames are valid - go ahead and update
                    final User retrievedUser = userDAO.findByUserName(userName);

                    if (retrievedUser != null) {
                        retrievedUser.setDeletedIndicator(true);
                        retrievedUser.setAuditUsername(securityService);
                        this.userDAO.save(retrievedUser);
                        userNameAndStatusMap.put(userName, "Deleted Successfully");
                    } else {
                        LOG.debug(String
                                .format("Cannot delete any user as no user with username %s found.",
                                        userName));
                        userNameAndStatusMap.put(userName, "User not found to Delete");
                        return userNameAndStatusMap;
                    }
                } catch (Exception e) {
                    userNameAndStatusMap.put(userName, "Error: Cannot delete user");
                    return userNameAndStatusMap;
                }
            } else {
                userNameAndStatusMap.put(userName, "Error: Cannot delete user - supplied user name list is null or empty");
                LOG.debug("Supplied UserName List is Null Or Empty .");
            }
        }

        return userNameAndStatusMap;
    }


    @Override
    public User getFullyHydratedUser(String username, String password) throws JsonParseException, JsonMappingException, IOException {
        User user = findUserByPlainTextCredentials(username, password);

        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getFullyHydratedUserList(final List<User> userList)
            throws JsonParseException, JsonMappingException, IOException {
        for (User user : userList) {
            if (user != null) {
                final List<PermissionGroup> permissionGroupList = this.permissionGroupDAO.getUserPermissionGroupsByUsername(user.getUserName());
                if (permissionGroupList != null) {
                    LOG.debug("PermissionGroup list size is " + permissionGroupList.size());
                    for (PermissionGroup permissionGroup : CollectionsUtilService.nullGuard(permissionGroupList)) {
                        final List<Permission> permissionList = this.permissionDAO.getUserPermissionsByPermissionGroupName(permissionGroup.getPermissionGroupName());
                        permissionGroup.setPermissionsSet((permissionList != null ? new HashSet(permissionList) : null));
                    }
                }
                user.setUserPermissionGroupList(permissionGroupList);

                this.setUserPermissionSet(user);
            }
        }
        return userList;
    }

    @Transactional(readOnly = true)
    @Secured({"user-list-download-excel", "user-list-download-pdf"})
    @Override
    public List<User> getUserListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, IllegalArgumentException, IllegalStateException, Exception {

        List<String> dateEnumList = codeLookupService.getCodeLookupValueBySingleKey("FILTER_DATE");

        // if it's not valid the exception will propagate
        // all the way back to caller
        searchAndSortCriteria.checkSearchMapData(dateEnumList);

        return this.getFullyHydratedUserList(userDAO.getNewBySearchAndSortCriteria(searchAndSortCriteria));
    }


    /*
     * Service method implementation to get the total records from UserDAO given a search and sort criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, Exception {
        return userDAO.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
    }

    /**
     * getUserListFromUserNameList() provide the functionality for fetching the
     * persistence state user object from the data base.based on the user name list.
     *
     * @param usernameList accept the instance of java.util.List containing the
     *                     user name in the form of the java.lang.String object.
     * @return the instance of java.util.List containing the persistence state
     * com.lucas.entity.user.User instance containing the data.
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getUserListFromUserNameList(List<String> usernameList) {
        List<User> userList = new ArrayList<User>();
        for (String username : CollectionsUtilService.nullGuard(usernameList)) {
            User user = this.getUser(username);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public Map<String,String> getUserMutablePairMap(List<String> usernameList) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException,LucasRuntimeException {
        List<User> userList = getUserListFromUserNameList(usernameList);
        MultiEditable<User> multiEditable = new User();
        List<String> multiUserEditableFieldsList = multiEditable.getMultiEditableFieldsList();
        return MultiCompare.getMutablePairMap(userList, multiUserEditableFieldsList);
    }

    @Override
    @Transactional
    public void saveMultiUser(List<String> usernameList,
                              UserFormFieldsDTO userFormFieldsDTO) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException {

        Map<String, String> fieldValueMap = getFieldValueMap(userFormFieldsDTO);
        List<User> userList = getUserListFromUserNameList(usernameList);
        for (User user : CollectionsUtilService.nullGuard(userList)) {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(
                    user.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (fieldValueMap.containsKey(propertyDescriptor.getName())) {
                    if (propertyDescriptor.getPropertyType().getName().equals(String.class.getName())) {
                        propertyDescriptor.getWriteMethod().invoke(user, fieldValueMap.get(propertyDescriptor.getName().trim()));
                    }

                    if (propertyDescriptor.getPropertyType().getName().equals(Date.class.getName())) {
                        String dateString = fieldValueMap.get(propertyDescriptor.getName());
                        Date dateObject = DateUtils.parseDate(dateString);
                        propertyDescriptor.getWriteMethod().invoke(user, dateObject);
                    }
                    if (propertyDescriptor.getPropertyType().getName().equals(Boolean.class.getName()) || propertyDescriptor.getPropertyType().getName().equalsIgnoreCase("boolean")) {
                        //Need to send boolean or Boolean values 1 or 0 in the json
                        String booleanString = fieldValueMap.get(propertyDescriptor.getName());
                        propertyDescriptor.getWriteMethod().invoke(user, Boolean.valueOf(booleanString));
                    }
                    if (propertyDescriptor.getPropertyType().getName().equalsIgnoreCase("com.lucas.entity.user.SupportedLanguage")) {
                        String langString = fieldValueMap.get(propertyDescriptor.getName());
                        SupportedLanguage s = new SupportedLanguage();
                        s.setLanguageCode(langString);
                        propertyDescriptor.getWriteMethod().invoke(user, s);
                    }                   

                }
            }
        }
    }

    private Map<String, String> getFieldValueMap(
            UserFormFieldsDTO userFormFieldsDTO) throws IntrospectionException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Map<String, String> fieldValueMap = new HashMap<String, String>();
        Map<String, String> fieldMap = null;
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(
                userFormFieldsDTO.getClass()).getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Object fieldMapObject = propertyDescriptor.getReadMethod().invoke(
                    userFormFieldsDTO);

            if (fieldMapObject instanceof Map<?, ?>) {
                fieldMap = (Map<String, String>) fieldMapObject;
                String forceUpdateValue = null;
                if (fieldMap != null && !fieldMap.isEmpty()) {
                    forceUpdateValue = fieldMap.get("forceUpdate");
                }
                if (forceUpdateValue != null
                        && forceUpdateValue.equalsIgnoreCase("true")) {
                    String value = fieldMap.get("value");
                    fieldValueMap.put(propertyDescriptor.getName(), value);
                }
            }
        }
        if (fieldValueMap
                .get("plainTextPassword") == null) {
            fieldValueMap
                    .put("plainTextPassword", "dummyPlainTextPassword");
        }
        return fieldValueMap;
    }

    public List<PermissionGroup> getPermissionGroupsBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
        return this.getFullyHydratedPermissionGroupList(permissionGroupDAO.getPermissionGroupsBySearchAndSortCriteria(searchAndSortCriteria));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getActiveUsernameList()
            throws ParseException, Exception {

        return this.userDAO.getActiveUsernameList();
    }

    private List<PermissionGroup> getFullyHydratedPermissionGroupList(
            List<PermissionGroup> permissionGroupsList) {
        for (PermissionGroup permissionGroup : CollectionsUtilService.nullGuard(permissionGroupsList)) {
            List<Permission> permissionList = permissionDAO.getUserPermissionsByPermissionGroupName(permissionGroup.getPermissionGroupName());
            permissionGroup.setPermissionGroupPermissionList(permissionList);
        }
        return permissionGroupsList;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveUsersAssignedGroups(UsersAssignedGroupsDTO usersAssignedGroupsDTO) {

        List<PermissionGroup> groupList = new ArrayList<PermissionGroup>();

        List<String> groups = usersAssignedGroupsDTO.getAssignedGroups();

        for (int i = 0; i < groups.size(); i++) {
            String currentGroupName = groups.get(i);
            PermissionGroup curPermissionGroup = permissionGroupDAO.getPermissionGroupByName(currentGroupName);
            if (curPermissionGroup == null && currentGroupName != "") {
                // contains invalid groups
                //return false;
                throw new PermissionGroupDoesNotExistException(String.format(
                        "Permission group does not exist with group %s. Cannot add Groups to user!",
                        currentGroupName));
            }
            groupList.add(permissionGroupDAO.getPermissionGroupByName(currentGroupName));
        }

        securityService.replaceGroupListForUser(usersAssignedGroupsDTO.getUserName(), groupList);

        return true;
    }

    /**
     * Preprocess the list of usernames and check whether the username is null or invalid
     *
     * @param usernameList
     * @return
     */
    private Map<String, String> preProcessUsername(List<String> usernameList) {

        final Map<String, String> userNameAndStatusMap = new HashedMap();

        for (final String userName : usernameList) {
            if (userName != null) {
                // first check for any null names
                if (StringUtils.isBlank(userName)) {
                    userNameAndStatusMap.put(userName, "BLANK_USERNAME_ERROR_CODE");
                    return userNameAndStatusMap;
                }

                final User retrievedUser = userDAO.findByUserName(userName);

                if (retrievedUser == null) {
                    userNameAndStatusMap.put(userName, "INVALID_USERNAME_ERROR_CODE");
                    return userNameAndStatusMap;
                }

                userNameAndStatusMap.put(userName, "OK");

            } else {
                userNameAndStatusMap.put("BLANK_USERNAME_ERROR_CODE", "BLANK_USERNAME_ERROR_CODE");
                return userNameAndStatusMap;
            }
        }

        return userNameAndStatusMap;
    }

    /*
     * Implementation method to set the retrain voice model for the selected Users and persist in
     * the database.
     */
    @Transactional(readOnly = false)
    @Override
    @Secured({"retrain-voice-model"})
    public Map<String, String> retrainVoiceModelForSelectedUsers(List<String> userList) {
        final Map<String, String> resultMap = new HashMap<String, String>();

        // throw exception if no users were passed
        if (userList == null || userList.size() == 0) {
            throw new UserDoesNotExistException("No users requested for voice model reset.");
        }

        for (final String userName : userList) {
            final User retrievedUser = userDAO.findByUserName(userName);
            if (retrievedUser == null) {
                throw new UserDoesNotExistException(String.format("No user exists with username %s.", userName));
            }
            // to bypass the NotNull Constraint on User
            retrievedUser.setPlainTextPassword("-----");
            retrievedUser.setRetrainVoiceModel(Boolean.TRUE);
            retrievedUser.setAuditUsername(securityService);
            final User user = this.userDAO.save(retrievedUser);
            resultMap.put(user.getUserName(), "Retrain Voice Reset Successfully");
        }

        return resultMap;
    }

    /**
     * Get the users assigned group the the passed in list of usernames
     *
     * @param userList
     * @return resultMap
     * @throws throws UserDoesNotExistException, LucasRuntimeException
     * @author DiepLe
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getAssignedUserGroups(List<String> userList) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (userList == null || userList.size() == 0) {
            throw new UserDoesNotExistException(String.format("No user exists for empty list"));
        }

        List<String> groupList = null;

        for (String username : CollectionsUtilService.nullGuard(userList)) {
            try {
                groupList = groupService.getPermissionGroupNamesByUsername(username);

                if (groupList == null || groupList.size() == 0) {
                    LOG.debug("Exception - No user exists with username");
                    throw new UserDoesNotExistException(String.format("No user exists with username %s.", username));
                }

                resultMap.put(username, groupList);

            } catch (UserDoesNotExistException e) {
                LOG.debug("Exception UserDoesNotExistException: when calling groupService.getPermissionGroupNamesByUsername()");
                throw e;
            } catch (Exception e) {
                LOG.debug("Exception Exception: when calling groupService.getPermissionGroupNamesByUsername()");
                throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return resultMap;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.lucas.services.user.UserService#updateUserActiveCanvas(com.lucas.entity.user.User)
     */

    @Transactional(readOnly = false)
    @Override
    public boolean updateUserActiveCanvas(User user) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {
        final User retrievedUser = userDAO.findByUserId(user.getUserId());
        final List<String> responseData = new ArrayList<String>();
        if (retrievedUser == null) {
            throw new UserDoesNotExistException(String.format("No user exists with username %s. Cannot update user!", user.getUserName()));
        }

        Canvas activeCanvas = user.getActiveCanvas();
        try {
            if (activeCanvas != null) {
                activeCanvas = uiService.findByCanvasId(activeCanvas.getCanvasId());
            } else {
                responseData.add(activeCanvas.getCanvasId() + " Doesn't Find in the System ");
            }

        } catch (Exception e) {
            responseData.add(activeCanvas.getCanvasId() + " Doesn't Find in the System ");
        }

        retrievedUser.setActiveCanvas(activeCanvas);
        retrievedUser.setAuditUsername(securityService);
        userDAO.save(retrievedUser);
        return true;
    }

    /**
     * (non-Javadoc)
     *
     * @see com.lucas.services.user.UserService#updateUserOpenCanvas(com.lucas.entity.user.User)
     */
    @Transactional(readOnly = false)
    @Override
    public List<String> updateUserOpenCanvas(User user) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {
        final User retrievedUser = this.userDAO.findByUserId(user.getUserId());
        final List<String> responseData = new ArrayList<String>();
        if (retrievedUser == null) {
            throw new UserDoesNotExistException(String.format("No user exists with username %s. Cannot update user!", user.getUserName()));
        }
        final List<OpenUserCanvas> openUserCanvases = user.getOpenUserCanvases();
        if (openUserCanvases != null && !openUserCanvases.isEmpty()) {
            for (OpenUserCanvas openCanvas : CollectionsUtilService.nullGuard(openUserCanvases)) {
                try {
                    final Canvas canvas = this.uiService.findByCanvasId(openCanvas.getCanvas().getCanvasId());
                    final Date date = new Date();
                    if (canvas != null) {
                        openCanvas.setCanvas(canvas);
                        openCanvas.setCreatedDateTime(date);
                        openCanvas.setCreatedByUserName(retrievedUser.getUserName());
                        openCanvas.setUpdatedDateTime(date);
                        openCanvas.setUpdatedByUserName(retrievedUser.getUserName());
                    } else {
                        responseData.add(openCanvas.getCanvas().getCanvasId() + " Doesn't Find in the System ");
                    }
                } catch (Exception e) {
                    responseData.add(openCanvas.getCanvas().getCanvasId() + " Doesn't Find in the System ");
                }
            }
        }
        retrievedUser.setOpenUserCanvases(openUserCanvases);
        retrievedUser.setAuditUsername(securityService);
        this.userDAO.save(retrievedUser);
        return responseData;
    }

    @Override
    @Transactional(readOnly = true)
    public Canvas getUserActiveCanvas(String userName) throws Exception {
        Canvas activeCanvas = null;
        final List<String> responseData = new ArrayList<String>();
        final User user = userDAO.findByUserName(userName);
            if (user == null) {
                throw new UserDoesNotExistException(String.format("No user exists with username %s. Cannot get user!", user.getUserName()));
            }
            
        try {
            activeCanvas = user.getActiveCanvas();

            if (activeCanvas != null) {
                activeCanvas = uiService.findByCanvasId(activeCanvas.getCanvasId());
            } else {
                responseData.add("No Active Canvas found in the System ");
            }
        } catch (Exception e) {
            responseData.add("No Active Canvas found in the System ");
        }
        return activeCanvas;
    }

    /**
     * Method to save the user preference data
     * @author DiepLe
     *
     * @param mapData
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Map<String, String> saveUserPreferencesData(Map<String, String> mapData) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {

        final String userName = mapData.get("userName");
        final String dateFormat = mapData.get("dateFormat");
        final String timeFormat = mapData.get("timeFormat");
        final Long dataRefreshFrequency = Long.parseLong(mapData.get("dataRefreshFrequency"));
        Map<String, String> resultMap = new HashMap<String, String>(0);

        if (userName != null) {
            try {
                // load the user object to save the user preferences
                User user = findByUserName(userName);
                if (user == null) {
                    LOG.debug("UserController.saveUserPreferencesData: user is null");
                    resultMap.put("statusCode", "1032");
                    resultMap.put("message", "No user preferences exist - user object is null");
                    return resultMap;
                }
                //final UserPreferences userPreferences = userPreferencesService.findOneUserPreferencesByUserId(user.getUserId());
                LOG.debug("UserServiceImpl.saveUserPreferencesData: current user preferences data: {}", user.getUserPreferences());
                user.getUserPreferences().setDateFormat(dateFormat);
                user.getUserPreferences().setTimeFormat(timeFormat);
                user.getUserPreferences().setDataRefreshFrequency(dataRefreshFrequency);

                LOG.debug("UserController.saveUserPreferencesData: NEW user preferences data: {}", user.getUserPreferences());
                // now update the user object to reflect the new user preferences
                user.setAuditUsername(securityService);
                userDAO.save(user);
                resultMap.put("statusCode", "1033");
                resultMap.put("message", "User preferences saved successfully");
            } catch (Exception e) {
                LOG.debug("UserController.saveUserPreferencesData: Exception is thrown: ", e);
                resultMap.put("statusCode", "1032");
                resultMap.put("message", "No user preferences exist - Exception is thrown");
                throw e;
            }
        } else {
            LOG.debug("UserController.saveUserPreferencesData: userName is null");
            resultMap.put("statusCode", "1032");
            resultMap.put("message", "No user preferences exist - username is null");
        }

        return resultMap;
    }

     /* Get selected list of user properties for all users
     *
     * @author WWong
     * @param property
     * @return resultList
     * @exception throws LucasRuntimeException
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String,String>> getAllUsersProperties(List<String> property) throws Exception {
        LOG.debug("UserServiceImpl.getAllUsersProperties: start ");
        List<String> propertyList = new ArrayList<String>();
        List<Object[]> resultList = null;
        List<Map<String,String>> resultMap = new ArrayList<Map<String,String>>();
        Map<String, String> returnMap = null;

        // if List is not empty, add userName to list so that the result set always return userName, in case some property object is null
        if (property != null && property.size() >= 1) {
            propertyList.add("userName");
            propertyList.addAll(property);
        }

        try {
            resultList = userDAO.getAllUsersProperties(propertyList);
        } catch (Exception e) {
            LOG.debug("Exception : when calling userDAO.getAllUsersProperties()");
            throw new LucasRuntimeException(e);
        }

        if (resultList == null || resultList.size() == 0)
        {
            return null;
        }

        int objIndex = 0;
        for (Object obj[] : resultList) {
            returnMap = new HashMap<String, String>();
            objIndex = 0;
            // build the result map for all properties and values

            for (String propColumn : propertyList)
            {
                if (obj[objIndex] != null) {
                    // check if object is user defined objects of user entity
                    switch (propColumn) {
                        case "u2jLanguage" :
                        case "j2uLanguage" :
                        case "amdLanguage" :
                        case "hhLanguage" :
                            // return only language code
                            SupportedLanguage lang = (SupportedLanguage) obj[objIndex];
                            returnMap.put(propColumn, lang.getLanguageCode().toString());
                            break;
                        case "shift" :
                            // return only shift name
                            Shift shift = (Shift) obj[objIndex];
                            returnMap.put(propColumn, shift.getShiftName());
                            break;
                        case "wmsUser" :
                            // return only hostLogin
                            returnMap.put(propColumn, obj[objIndex].toString());
                            break;
                        case "userPreferences" :
                            // return user preferences dateFormat, timeFormat, dataRefereshFrequency
                            returnMap.put("dateFormat", obj[objIndex].toString());
                            returnMap.put("timeFormat", obj[++objIndex].toString());
                            returnMap.put("dataRefreshFrequency", obj[++objIndex].toString());
                            break;
                        default:
                            // return string value
                            returnMap.put(propColumn, obj[objIndex].toString());
                            break;
                    }
                }
                else
                {
                    // return null as the value
                    returnMap.put(propColumn,null);
                }
                objIndex++;
            }

            // save the result map into the return map
            resultMap.add(returnMap);
        }

        return resultMap;
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> getUserNameListVerified(List<String> userNameList) throws ParseException ,Exception {

        final List<String> userList = new ArrayList<>();

        for (final String userName : userNameList) {

            final User retrievedUser = userDAO.findByUserName(userName);

            if (retrievedUser == null) {
                throw new UserDoesNotExistException(String.format(userName));
            }

            userList.add(retrievedUser.getUserName());
        }

        return userList;
    }


}
