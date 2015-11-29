/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;

@Repository
public class UserDAOImpl extends ResourceDAO<User> implements UserDAO {

    private static Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

	@Override
	public User findByUserName(String username) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);

		criteria.add(Restrictions.eq("userName", username));

		User user = (User) criteria.uniqueResult();

		return user;
	}

	@Override
	public User findByHashedPassword(String hashedPassword) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);

		criteria.add(Restrictions.eq("hashedPassword", hashedPassword));

		return (User) criteria.uniqueResult();
	}

	@Override
	public User getUser(String username) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class).
				setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("userName", username));

		User user = (User) criteria.uniqueResult();
		return user;
	}

	@Override
	public List<User> getUserList() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		// To avoid Lazy instantiation exception
		criteria.setFetchMode("userGroupList", FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserList(String pageSize, String pageNumber) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		Integer offset = Integer.valueOf(pageSize) * Integer.valueOf(pageNumber)  ;
		criteria.addOrder(Order.asc("userId"));
		criteria.setFirstResult(offset);
		criteria.setMaxResults(Integer.valueOf(pageSize));
		List<User> results = criteria.list();
		return results;
	}
	
	@Override
	public Long getUserCount() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.list().get(0);
	}

	@Override
	public User findByUserId(Long userId) {
		return  load(userId);
	}

	@Override
	public List<OpenUserCanvas> getOpenCanvasesForUserId(Long userId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("userId", userId));
		User user =  (User) criteria.uniqueResult();
		List<OpenUserCanvas> openUserCanvasesList = user.getOpenUserCanvases();
		return openUserCanvasesList;
	}

    @Override
    public List<String> getActiveUsernameList() throws ParseException {

        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("deletedIndicator", false));
        criteria.add(Restrictions.eq("enable", true));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("userName")));

        final List<String> nameList = criteria.list();

        return nameList;
    }

    /**
     * User Search specific override for the Search and Sort Criteria
     * To search the associated table column, in search map, the values are passed like this "wmsUser.hostlogin"
     * To get the type of hostlogin, method will get the Class and find its type with value hostlogin.
     * For supported language, since j2uLanguage is not the class returned from User object, this needs to be modified
     * to get the type of the column i.e. language code at runtime for Supported Language class.
     * 
     * This overridden method returns the type of the field as given in search and sort map.
     * 
     * @param aKey
     * @return Type of the class 
     */
    @Override
    public Type getFieldType(String aKey) {
        Class<?> type = null;

        try {
            String[] tokens = StringUtils.split(aKey, ".");
            if (tokens.length == 2) {
                Package pack = entityType.getPackage();
                String packageName = pack.getName();

                if (("j2uLanguage".equalsIgnoreCase(tokens[0])) || ("u2jLanguage".equalsIgnoreCase(tokens[0])) || ("hhLanguage".equalsIgnoreCase(tokens[0]))
                        || ("amdLanguage".equalsIgnoreCase(tokens[0]))) {
                    type = ReflectionUtils.findField(Class.forName(packageName + "." + "SupportedLanguage"), tokens[1]).getType();
                }
                else if ("userPreferences".equalsIgnoreCase(tokens[0])) {
                        type = ReflectionUtils.findField(Class.forName(packageName + "." + "UserPreferences"), tokens[1]).getType();
                } else {
                    // use fully qualified camel case class name
                    String clsName = StringUtils.capitalize(tokens[0]);
                    type = ReflectionUtils.findField(Class.forName(packageName + "." + clsName), tokens[1]).getType();
                }
            } else {
                type = ReflectionUtils.findField(Class.forName(entityType.getCanonicalName()), tokens[0]).getType();
            }
        } catch (ClassNotFoundException e) {
            throw new LucasRuntimeException(String.format("Invalid Search Field for %s", aKey));
        }

        return type;
    }

    /*
     * This method returns the user properties for all users.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getAllUsersProperties(List<String> propList) throws ParseException {

        List<Object[]> returnUserPropsList = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class, "user");
        criteria.addOrder(Order.asc("userId"));
        criteria.add(Restrictions.eq("deletedIndicator", false));
        ProjectionList projectionList = Projections.projectionList();

        if (propList != null && propList.size() > 0) {
            if (propList.contains("shift")) {
                criteria.createAlias("shift", "shift", CriteriaSpecification.LEFT_JOIN);
            }
            if (propList.contains("wmsUser"))  {
                criteria.createAlias("user.wmsUser", "wmsuser", CriteriaSpecification.LEFT_JOIN);
            }

            for (String propColumn : propList)
            {
                if ((propColumn == null) || (propColumn.length() <= 0))
                {
                    throw new LucasRuntimeException(String.format("Invalid Property Field %s", propColumn));
                }
                else {
                    if ("wmsUser".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("wmsuser.hostLogin"));
                    }
                    else if ("userPreferences".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.userPreferences.dateFormat"));
                        projectionList.add(Projections.property("user.userPreferences.timeFormat"));
                        projectionList.add(Projections.property("user.userPreferences.dataRefreshFrequency"));
                    }
                    else if ("dateFormat".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.userPreferences.dateFormat"));
                    }
                    else if ("timeFormat".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.userPreferences.timeFormat"));
                    }
                    else if ("dataRefreshFrequency".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.userPreferences.dataRefreshFrequency"));
                    }
                    // BaseEntity attributes: createdByUsername, createdDateTime, updatedByUserName, updateDateTime
                    else if ("createdByUsername".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.createdByUserName"));
                    }
                    else if ("createdDateTime".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.createdDateTime"));
                    }
                    else if ("updatedByUsername".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.updatedByUserName"));
                    }
                    else if ("updatedDateTime".equalsIgnoreCase(propColumn)) {
                        projectionList.add(Projections.property("user.updatedDateTime"));
                    }
                    else {
                        // set property name into Projection
                        projectionList.add(Projections.property(propColumn));
                    }
                }
            }
            try {
                criteria.setProjection(projectionList);
                returnUserPropsList = criteria.list();
            }
            catch (Exception e) {
                log.debug(e.toString() + " ... " + propList.toString() + " size " + propList.size());
                propList.remove("userName");
                throw new LucasRuntimeException(String.format("Unknown Property Column name in %s", propList));
            }
        }
        else {
            throw new LucasRuntimeException(String.format("Unable to get User Properties"));
        }


        return returnUserPropsList;
    }

    @Override
    protected Criteria modifyNewCriteriaForCurrentEntity(Criteria criteria, SearchAndSortCriteria searchAndSortCriteria) {
        criteria.createAlias("entity.wmsUser", "wmsUser", CriteriaSpecification.LEFT_JOIN);
        criteria.createAlias("shift", "shift", CriteriaSpecification.LEFT_JOIN);
        criteria.add(Restrictions.eq("deletedIndicator", false));
        return criteria;
    }

    @Override
    protected String modifySearchKeyForCurrentEntity(String searchPropertyKey) {
        if ("dateFormat".equalsIgnoreCase(searchPropertyKey) ||
                ("timeFormat".equalsIgnoreCase(searchPropertyKey)) ||
                ("dataRefreshFrequency".equalsIgnoreCase(searchPropertyKey)))
            return "userPreferences." + searchPropertyKey;
        if (("j2uLanguage".equalsIgnoreCase(searchPropertyKey)) ||
                ("u2jLanguage".equalsIgnoreCase(searchPropertyKey)) ||
                ("hhLanguage".equalsIgnoreCase(searchPropertyKey)) ||
                ("amdLanguage".equalsIgnoreCase(searchPropertyKey)))
            return searchPropertyKey + ".languageCode";
        if ("wmsUser".equalsIgnoreCase(searchPropertyKey))
            return searchPropertyKey + ".hostLogin";
        if ("shift".equalsIgnoreCase(searchPropertyKey))
            return searchPropertyKey + ".shiftName";
        return searchPropertyKey;
    }
}
