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

import com.lucas.dao.user.PermissionDAO;
import com.lucas.dao.user.PermissionGroupDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.dto.user.PermissionGroupSummaryDTO;
import com.lucas.dto.user.PermissionGroupsDTO;
import com.lucas.entity.user.Permission;
import com.lucas.entity.user.PermissionGroup;
import com.lucas.entity.user.User;
import com.lucas.services.application.CodeLookupService;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.security.SecurityService;
import com.lucas.services.util.CollectionsUtilService;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("groupService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class GroupServiceImpl implements GroupService {

	private static Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);
	private final PermissionGroupDAO permissionGroupDAO;
    private final PermissionDAO permissionDAO;
    private final UserDAO userDAO;
    private final SecurityService securityService;
    private final CodeLookupService codeLookupService;

	@Inject
	public GroupServiceImpl(PermissionGroupDAO permissionGroupDAO,
                            PermissionDAO permissionDAO,
                            UserDAO userDAO,
                            SecurityService securityService,
                            CodeLookupService codeLookupService) {
		this.permissionGroupDAO = permissionGroupDAO;
        this.permissionDAO = permissionDAO;
        this.userDAO = userDAO;
        this.securityService = securityService;
        this.codeLookupService = codeLookupService;
	}



    @Override
    public List<String> getAllPermissionGroupNames() throws ParseException {
        return this.permissionGroupDAO.getPermissionGroupNames();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPermissionGroupNamesByUsername (String username) {
        return permissionGroupDAO.getPermissionGroupNamesByUsername(username);
    }

    @Override
    public Map<String, Long> getPermissionGroupSummary() {
        return permissionGroupDAO.getPermissionGroupSummary();
    }


    /* Get the group details, permissions lists and enrolled users
     *
     * @author WWong
     * @param groupName
     * @return PermissionGroupsDTO
     * @exception throws LucasRuntimeException
     */
    @Override
    @Transactional (readOnly = true)
    public PermissionGroupsDTO getGroupDetails(String groupName) throws Exception
    {
        PermissionGroup permissionGroup = null;
        PermissionGroupsDTO permissionGroupsDTO = null;
        // retrieve permission Group
        try {
            permissionGroup = permissionGroupDAO.getPermissionGroupByName(groupName);
            if (permissionGroup != null) {
                permissionGroupsDTO  = new PermissionGroupsDTO();
                permissionGroupsDTO.setPermissionGroup(permissionGroup);
            }
        }
        catch (Exception e) {
            LOG.debug("An exception has occurred: " + e.getStackTrace());
        }
        if (permissionGroup == null) {
            // default the group Details for unknown group name
            permissionGroupsDTO = new PermissionGroupsDTO(groupName, "", new ArrayList<String>(), new ArrayList<String>());
        }
        return permissionGroupsDTO;

    }

   /* Save the group details, permissions lists and enrolled users
    *
    * @author WWong
    * @param PermissionGroupsDTO
    * @exception throws LucasRuntimeException
    */
    @Override
    @Transactional
    public boolean saveGroupDetails(PermissionGroupsDTO permissionGroupDTO) throws IllegalArgumentException, Exception
    {

        PermissionGroup permissionGroup = null;
        Permission permission = null;
        User user = null;
        String groupName = null;
        String groupDesc = null;
        List<String> dtoAssignedPermissionsList = permissionGroupDTO.getAssignedPermissions();
        List<String> dtoEnrolledUsersList = permissionGroupDTO.getEnrolledUsers();

        List<Permission> groupAssignedPermissionsList = new ArrayList<Permission>();
        Set<User> groupEnrolledUsers = new HashSet<User>();

        // Get group details from PermissionGroupDTO
        if (permissionGroupDTO.getGroupName() == null || permissionGroupDTO.getGroupName().length() <= 0) {
            throw new IllegalArgumentException("Group Name is required");
        }
        else
        {
            groupName = permissionGroupDTO.getGroupName();
            groupDesc = permissionGroupDTO.getDescription();
            // retrieve permission Group
            try {
                permissionGroup = permissionGroupDAO.getPermissionGroupByName(groupName);
            }
            catch (Exception e) {
                LOG.debug("An exception has occurred: " + e.getStackTrace());
            }
            if (permissionGroup == null)
            {
                // create a new permission group and set the group name
                permissionGroup = new PermissionGroup();
                permissionGroup.setPermissionGroupName(groupName);
            }
        }
        LOG.debug("GroupServiceImpl.saveUserGroupDetails() - data: groupName [" + groupName + "] description [" + groupDesc + "]");

        // validate and build assigned permissions list
        if (dtoAssignedPermissionsList == null) {
            throw new IllegalArgumentException("Assigned Permissions list is required");
        }
        else
        {
            // validate permissions
            for (String permissionName : dtoAssignedPermissionsList)
            {
                if (permissionName != null)
                {
                    try {
                        permission = permissionDAO.getPermissionByName(permissionName);
                    }
                    catch (Exception e){
                        LOG.debug("An exception has occurred while retrieving permission " + e.getStackTrace());
                        throw (e);
                    }
                    if (permission != null)
                    {
                        groupAssignedPermissionsList.add(permission);
                    }
                    else {
                        throw new IllegalArgumentException("Unknown permission");
                    }
                }
            }
        }

        // validate and build enrolled users list
        if (dtoEnrolledUsersList == null) {
            throw new IllegalArgumentException("Enrolled Users list is required");
        }
        else
        {
            // validate users list
            for (String userName : dtoEnrolledUsersList)
            {
                if (userName != null)
                {
                    try {
                        user = userDAO.getUser(userName);
                    }
                    catch (Exception e){
                        LOG.debug("An exception has occurred while retrieving user " + e.getStackTrace());
                        throw (e);
                    }
                    if (user != null)
                    {
                        groupEnrolledUsers.add(user);
                    }
                    else {
                        throw new IllegalArgumentException("Unknown user");
                    }
                }
            }
        }

        // save group details data into permissionGroup
        permissionGroup.setPermissionDescription(groupDesc);
        permissionGroup.setPermissionGroupPermissionList(groupAssignedPermissionsList);
        permissionGroup.setUserSet(groupEnrolledUsers);
        // update auditing columns updatedByUsername and updatedDateTime
        permissionGroup.setAuditUsername(securityService);
        // save data
        this.permissionGroupDAO.save(permissionGroup);

        return true;
    }

    /* Delete the group, permissions lists and enrolled users
     *
     * @author WWong
     * @param PermissionGroupsDTO (groupName)
     * @return true or false
     * @exception throws LucasRuntimeException
     */
    @Override
    @Transactional
    public boolean deleteGroupDetails(PermissionGroupsDTO permissionGroupDTO) throws IllegalArgumentException, Exception{
        PermissionGroup permissionGroup = null;
        String groupName = null;
        // Get group name from PermissionGroupDTO
        if (permissionGroupDTO.getGroupName() == null || permissionGroupDTO.getGroupName().length() <= 0) {
            throw new IllegalArgumentException("Group Name is required");
        }
        else {
            groupName = permissionGroupDTO.getGroupName();
        }
        try {
            permissionGroup = permissionGroupDAO.getPermissionGroupByName(groupName);
            // if group does not exist, then just return true
            if (permissionGroup == null)
                return false;
            // delete group
            this.permissionGroupDAO.delete(permissionGroup);
        }
        catch (Exception e) {
            LOG.debug("An exception has occurred: " + e.getStackTrace());
            throw (e);
        }

        return true;
    }


    /* Get the group details by search and sort criteria
     *
     * @author DiepLe
     *
     * @param searchAndSortCriteria
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     * @throws Exception
     */
    @Override
    @Transactional (readOnly = true)
    public List<PermissionGroupSummaryDTO> getGroupSummaryByNewSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws IllegalArgumentException, IllegalStateException, Exception
    {

        List<String> dateEnumList = codeLookupService.getCodeLookupValueBySingleKey("FILTER_DATE");

        // if it's not valid or any exceptions then it will propagate
        // all the way back to caller/controller
        searchAndSortCriteria.checkSearchMapData(dateEnumList);

        PermissionGroupSummaryDTO permissionGroupSummaryDTO;
        List<PermissionGroupSummaryDTO> resultDTO = new ArrayList<PermissionGroupSummaryDTO>();

        List<Object[]> permissionGroupList = permissionGroupDAO.getPermissionGroupSummaryBySearchAndSortCriteria(searchAndSortCriteria);

        if (permissionGroupList != null && permissionGroupList.size() > 0) {
            for (Object[] pgObj : permissionGroupList) {
                if (pgObj != null) {
	                	permissionGroupSummaryDTO = new PermissionGroupSummaryDTO();
	                    permissionGroupSummaryDTO.setGroupName(pgObj[0].toString());
	                    permissionGroupSummaryDTO.setDescription(pgObj[1].toString());
	                    permissionGroupSummaryDTO.setUserCount(Long.parseLong(pgObj[2].toString()));
	                    resultDTO.add(permissionGroupSummaryDTO);
                }
            }
        }

    return resultDTO;
    }


    /*
     * Service method implementation to get the total records from UserDAO given a search and sort criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria) throws ParseException, Exception {
        return permissionGroupDAO.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
    }


}