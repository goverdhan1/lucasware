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

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.user.User;
import com.lucas.entity.user.UserPreferences;
import com.lucas.entity.user.WmsUser;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @author DiepLe
 *
 */

@Repository
public class UserPreferencesDAOImpl extends ResourceDAO<UserPreferences> implements UserPreferencesDAO {

    private UserDAO userDAO;


    @Inject
    public UserPreferencesDAOImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param id accept the primary key value
     * @return instance of the UserPreferences
     *          from database.
     */
    @Override
    public UserPreferences findOneUserPreferenceById(Long id) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserPreferences.class).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("preferenceId", id));

        return (UserPreferences) criteria.uniqueResult();
    }

    /**
     * findOneUserPreferenceById() retrieve a user preference records by passed in Id
     *
     * @param
     * @return List of instances of the UserPreferences
     *          from database.
     */
    @Override
    public List<UserPreferences> findAllUserPreferences() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(UserPreferences.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

}
