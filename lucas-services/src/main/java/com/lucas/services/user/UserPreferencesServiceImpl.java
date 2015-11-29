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

import com.lucas.dao.user.UserDAO;
import com.lucas.dao.user.UserPreferencesDAO;
import com.lucas.entity.user.User;
import com.lucas.entity.user.UserPreferences;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.exception.UserPreferencesDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DiepLe
 *
 */

@Service("userPreferencesService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class UserPreferencesServiceImpl implements UserPreferencesService {

	private static Logger LOG = LoggerFactory.getLogger(UserPreferencesServiceImpl.class);
    private UserPreferencesDAO userPreferencesDAO;
    private UserDAO userDAO;


    @Inject
    public UserPreferencesServiceImpl(UserDAO userDAO, UserPreferencesDAO userPreferencesDAO) {
        this.userDAO = userDAO;
        this.userPreferencesDAO = userPreferencesDAO;
    }


    /**
     * findOneUserPreferenceByUserId() retrieve a user preference records by passed in Id
     *
     * @param userId accept the foreign key user Id value
     * @return instance of the UserPreferences
     *          from database.
     */
    @Override
    @Transactional(readOnly = true)
    public UserPreferences findOneUserPreferencesByUserId (Long userId) throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException {
        return userPreferencesDAO.findOneUserPreferenceById(userId);
    }

    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param id accept the primary key value
     * @return instance of the UserPreferences
     *          from database.
     */
    @Override
    @Transactional(readOnly = true)
    public UserPreferences findOneUserPreferencesById (Long id) throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException {
        return userPreferencesDAO.findOneUserPreferenceById(id);
    }

    /**
     * findOneUserPreferenceByUserName() retrieve a user preference records by passed in username
     *
     * @param userName the user name to search
     * @return instance of the UserPreferences
     *          from database.
     */
    @Override
    public java.util.Map<String, String> findOneUserPreferenceByUserName(String userName) throws
                UserPreferencesDoesNotExistException, LucasRuntimeException, IOException {

        Map<String, String> resultMap = new LinkedHashMap<>();

        if (userName != null && !userName.isEmpty()) {
            try {
                final User user = userDAO.findByUserName(userName);

                if (user == null) {
                    // invalid userName is passed in
                    LOG.debug("Exception - User preferences does not exist");
                    throw new UserPreferencesDoesNotExistException(String.format("User preferences does not exist for [%s]", userName));
                }

                UserPreferences userPreferences = user.getUserPreferences();

                if (userPreferences == null) {
                    // No user preferences for this user
                    LOG.debug("Exception - User preferences does not exist");
                    throw new UserPreferencesDoesNotExistException(String.format("User preferences does not exist for [%s]", userName));
                }

                // now massage the json data and pass it back to the controller to process at frontend
                resultMap.put("dateFormat", userPreferences.getDateFormat());
                resultMap.put("timeFormat", userPreferences.getTimeFormat());
                resultMap.put("dataRefreshFrequency", userPreferences.getDataRefreshFrequency().toString());

            } catch (UserPreferencesDoesNotExistException e) {
                LOG.debug("Exception UserPreferencesDoesNotExistException: when calling UserPreferencesServiceImpl.findOneUserPreferenceByUserName()");
                throw e;
            }
            catch (Exception e) {
                LOG.debug("Exception when calling UserPreferencesServiceImpl.findOneUserPreferenceByUserName(): [%s]", e);
                throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
            }
        } else {
            LOG.debug("Exception - null username exception");
            throw new UserPreferencesDoesNotExistException(String.format("Null user name exception"));
        }

        return resultMap;
    }

    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param
     * @return List of instances of the UserPreferences
     *          from database.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserPreferences> findAllUserPreferences() throws UserPreferencesDoesNotExistException, LucasRuntimeException, IOException {
        return userPreferencesDAO.findAllUserPreferences();
    }

}