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

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.lucas.services.util.CollectionsUtilService;
import com.lucas.services.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.services.search.SearchAndSortCriteria;

@Repository
public class PermissionGroupDAOImpl extends ResourceDAO<PermissionGroup> implements PermissionGroupDAO {

	@Override
	public List<PermissionGroup> getAllPermissionGroups(List<Long> permissionGroupId) {
		
		Session session = getSession();
		Criteria criteria = session.createCriteria(PermissionGroup.class);
		criteria.add(Restrictions.in("permissionGroupId", permissionGroupId.toArray()));
		
		return criteria.list();
	}

	@Override
	public PermissionGroup getPermissionGroupByName(String permissionGroupName) {
		
		Session session = getSession();
		Criteria criteria = session.createCriteria(PermissionGroup.class);
		criteria.add(Restrictions.eq("permissionGroupName", permissionGroupName));
		PermissionGroup permissionGroup = (PermissionGroup) criteria.uniqueResult();
		
		return permissionGroup;
	}

	@Override
	public List<PermissionGroup> getUserPermissionGroupsByUsername(
			String username) {
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(PermissionGroup.class);
		
		criteria.createAlias("userSet", "u");
		criteria.add(Restrictions.eq("u.userName", username));
		
		return criteria.list();
	}

	@Override
	public List<PermissionGroup> getPermissionGroupsBySearchAndSortCriteria(
			SearchAndSortCriteria searchAndSortCriteria) throws ParseException {
		final List<PermissionGroup> permissionGroupList = this.getBySearchAndSortCriteria(searchAndSortCriteria);
        return permissionGroupList;
	}

    @Override
    public List<String> getPermissionGroupNames() throws ParseException {

        Session session = getSession();

        Criteria criteria = session.createCriteria(PermissionGroup.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("permissionGroupName")));

        return criteria.list();
    }

    @Override
    public List<String> getPermissionGroupNamesByUsername(String username) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(PermissionGroup.class);
        criteria.createAlias("userSet", "u");
        criteria.add(Restrictions.eq("u.userName", username));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("permissionGroupName")));

        return criteria.list();
    }

    @Override
    public Map<String, Long> getPermissionGroupSummary() {

        Session session = getSession();
        Criteria permissionGroupCriteria = session.createCriteria(PermissionGroup.class, "pg");

        permissionGroupCriteria.createAlias("userSet", "upg");
        ProjectionList projList = Projections.projectionList();

        projList.add(Projections.groupProperty("pg.permissionGroupName")).add(
                Projections.count("upg.userId"));

        permissionGroupCriteria.setProjection(projList);


        List<Object[]> results = permissionGroupCriteria.list();

        Map<String, Long> resultsMap = new HashMap<String, Long>();

        for (Object[] o : results) {
            resultsMap.put((String) o[0], (Long) o[1]);
        }

        return resultsMap;
    }

    // Returns a PermissionGroupSummary list instead of a PermissionGroup list, so cannot sue getBySearchAndSortCriteria
    @Override
    public List<Object[]> getPermissionGroupSummaryBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException {

        Session session = getSession();
        return (List<Object[]>) buildNewCriteria(session, searchAndSortCriteria).list();

    }

    @Override
    protected Criteria modifyNewCriteriaForCurrentEntity(Criteria criteria, SearchAndSortCriteria searchAndSortCriteria) {
        criteria.createAlias("userSet", "upg");
        ProjectionList projList = Projections.projectionList();
        String having = null;
        if (!CollectionsUtilService.isNullOrEmpty(searchAndSortCriteria.getSearchMap())
            && searchAndSortCriteria.getSearchMap().containsKey("userCount")
            && searchAndSortCriteria.getSearchMap().get("userCount") instanceof LinkedHashMap
            && ((Map<String, Object>) searchAndSortCriteria.getSearchMap().get("userCount")).get("values") != null) {
            List<String> arrList = (List<String>)((Map<String, Object>) searchAndSortCriteria.getSearchMap().get("userCount")).get("values");
            Function<String, String> lengthFunction = new Function<String, String>() {
                public String apply(String string) {
                    return "usercount" + Joiner.on("").join(StringUtils.processNumericSearchValue(string));
                }
            };
            having = Joiner.on(" and ").join(Lists.transform(arrList, lengthFunction));
        }
        projList.add(Projections.groupProperty("entity.permissionGroupName"))
                .add(Projections.groupProperty("entity.permissionDescription"))
                .add(having == null ? Projections.count("upg.userId") :
                        // the "null" makes the query syntactically correct, because it will add that as a group by
                        Projections.sqlGroupProjection("count(*) as usercount", "null having " + having,
                                new String[]{"usercount"}, new org.hibernate.type.Type[]{StandardBasicTypes.INTEGER}));
        criteria.setProjection(projList);
        return criteria;
    }

    @Override
    protected boolean skipSearchKeyForCurrentEntity(String searchPropertyKey) {
        return searchPropertyKey.equals("userCount");
        // userCount is handled with the having portion of the query in the modifyNewCriteriaForCurrentEntity
    }

    @Override
    protected String modifySearchKeyForCurrentEntity(String searchPropertyKey) {
        return searchPropertyKey.equalsIgnoreCase("description") ? "permissionDescription" : searchPropertyKey;
    }
}
