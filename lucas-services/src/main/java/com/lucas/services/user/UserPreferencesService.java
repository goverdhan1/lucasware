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
import com.lucas.entity.user.UserPreferences;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.exception.UserPreferencesDoesNotExistException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author DiepLe
 *
 */

public interface UserPreferencesService {

    /**
     * findOneUserPreferenceByUserId() retrieve a user preference records by passed in Id
     *
     * @param userId accept the foreign key user Id value
     * @return instance of the UserPreferences
     *          from database.
     */
	UserPreferences findOneUserPreferencesByUserId(Long userId) throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException;

    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param id accept the primary key value
     * @return instance of the UserPreferences
     *          from database.
     */
	UserPreferences findOneUserPreferencesById(Long id) throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException;

    /**
     * findOneUserPreferenceByUserName() retrieve a user preference records by passed in username
     *
     * @param userName accept the user name value
     * @return instance of the UserPreferences
     *          from database.
     */
    Map<String, String> findOneUserPreferenceByUserName(String userName) throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException;

    /**
     * findAllUserPreferences() retrieve all user preference records from database
     *
     * @param
     * @return List of instances of the UserPreferences
     *          from database.
     */
    List<UserPreferences> findAllUserPreferences() throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException;

}