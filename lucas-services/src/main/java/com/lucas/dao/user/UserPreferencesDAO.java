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

package com.lucas.dao.user;

import com.lucas.entity.user.UserPreferences;

import java.util.List;
import java.util.Map;


/**
 * @author DiepLe
 *
 */

public interface UserPreferencesDAO {

    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param id accept the primary key value
     * @return instance of the UserPreferences
     *          from database.
     */
    UserPreferences findOneUserPreferenceById(Long id);


    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param
     * @return List of instances of the UserPreferences
     *          from database.
     */
    List<UserPreferences> findAllUserPreferences();

}
