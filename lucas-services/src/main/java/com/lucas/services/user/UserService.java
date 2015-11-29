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
import com.lucas.dto.user.UserFormFieldsDTO;
import com.lucas.dto.user.UsersAssignedGroupsDTO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


public interface UserService {

	User findByUserName(String username);
	User findByUserID(Long userId);
	
	User findUserByPlainTextCredentials(String username, String encryptedPassword) ;

	User findUserByHashedCredentials(String username, String encryptedPassword) ;
	
	boolean isAuthenticateByHashedCredentials(String userName, String hashedPassword);
	
	boolean isAuthenticateByPlainTextCredentials(String userName, String plainTextPassword);
	
	void updateUser(User user) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;

    boolean createUser(User user) throws Exception;

    public Map<String,String> deleteUser(Long userId);

    public Map<String,String> deleteUser(String username);

    public Map<String,String> deleteUser(User user);
	
	User getUser(String username);
	
	User getUser(String username, boolean includePermission) ;
	
    public List<User> getUserList(List<String> userNameList);

	List<User> getUserList();

	List<User> getUserList(String PageSize , String resultSetStart);
	
	Long getUserCount();

    public Map<String,String> disableUser(List<String> userList);

    public Map<String,String> enableUser(List<String> userList);

    public Map<String,String> enableOrDisableUser(List<String> userList,boolean enable);

    public void saveUsers(List<User> userList) throws Exception;

    public Map<String,String> deleteUser(List<String> userNameList);
	
	User getFullyHydratedUser(String username, String password) throws JsonParseException, JsonMappingException, IOException;

    List<User> getFullyHydratedUserList(final List<User> userList)
            throws JsonParseException, JsonMappingException, IOException;

    List<User> getUserListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws ParseException , IllegalArgumentException, Exception;
    
    /**
     * Service call to fetch the total records from UserDAO given search and sort criteria.
     * @param searchAndSortCriteria
     * @return
     * @throws ParseException
     * @throws Exception
     */
    Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)throws ParseException ,Exception;

    /**
     * getUserListFromUserNameList() provide the functionality for fetching the
     * persistence state user object from the data base.based on the user name list.
     * @param usernameList accept the instance of java.util.List containing the
     *                     user name in the form of the java.lang.String object.
     * @return the instance of java.util.List containing the persistence state
     *         com.lucas.entity.user.User instance containing the data.
     */
    public List<User> getUserListFromUserNameList(List<String> usernameList);

	Map<String,String> getUserMutablePairMap(List<String> usernameList) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException,LucasRuntimeException;
	
	void saveMultiUser(List<String> usernameList, UserFormFieldsDTO userFormFieldsDTO) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ParseException;
	
	List<PermissionGroup> getPermissionGroupsBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException;

    List<String> getActiveUsernameList() throws ParseException ,Exception;

    boolean saveUsersAssignedGroups(UsersAssignedGroupsDTO usersAssignedGroupsDTO);
    
    /**
     * Method to set true to retrain voices for the selected users
     * 
     * @param userList
     * @return
     */
    public Map<String, String> retrainVoiceModelForSelectedUsers(List<String> userList);

    /**
     * Method to return user assign groups for a users list
     */
    public Map<String, Object> getAssignedUserGroups(List<String> userList);
    
    /**
     * The method updates User's Active Canvas
     * @param user
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    public boolean updateUserActiveCanvas(User user) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;

    /**
     * The method updates User's Open Canvases
     * @param user
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    public List<String> updateUserOpenCanvas(User user) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;
    
    /**
     * The method gets active canvas of a user
     * @param userName
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    public Canvas getUserActiveCanvas(String userName) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;

    /**
     * Method to return user properties for all users
     */
    public List<Map<String,String>> getAllUsersProperties(List<String> property) throws Exception ;

    /**
     *Methos to save the user preferences data
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
    Map<String, String> saveUserPreferencesData(Map<String, String> mapData) throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;
    

    public List<String> getUserNameListVerified(List<String> userNameList) throws ParseException ,Exception;
    
}
