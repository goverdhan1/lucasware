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

import com.lucas.alps.rest.ApiResponse;
import com.lucas.dto.user.PermissionGroupSummariesDTO;
import com.lucas.dto.user.PermissionGroupSummaryDTO;
import com.lucas.dto.user.PermissionGroupsDTO;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.user.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

@Controller
@RequestMapping(value="/groups")
public class GroupController {

	private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);
	private final GroupService groupService;
    private final MessageSource messageSource;

    @Inject
	public GroupController(GroupService groupService, MessageSource messageSource) {
        this.groupService = groupService;
        this.messageSource = messageSource;
	}

    /**
     * GET  -> get all the permission_group records.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<String>> getAllPermissionGroups() {
        LOG.debug("REST request to get all Groups");
        final ApiResponse <List <String>> apiResponse = new ApiResponse <List<String>>();

        List<String> groupList = null;

        try {
            groupList = groupService.getAllPermissionGroupNames();
            apiResponse.setData(groupList);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        LOG.debug("Size{}", groupList.size());
        return apiResponse;
    }

    /**
     * GET  -> get all the permission_group records for a user
     */
    @RequestMapping(value = "/{username}",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<String>> getAllPermissionGroupsByUsername(@PathVariable String username) {
        LOG.debug("REST request to get all Groups");
        final ApiResponse <List <String>> apiResponse = new ApiResponse <List<String>>();

        List<String> groupList = null;

        try {
            groupList = groupService.getPermissionGroupNamesByUsername(username);
            apiResponse.setData(groupList);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        LOG.debug("Size{}", groupList.size());
        return apiResponse;
    }

    /**
     * GET  -> get all the permission_group group by number of users
     */
    @RequestMapping(value = "/summary",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Map<String, Long>> getPermissionGroupSummary() {
        ApiResponse<Map<String, Long>> apiResponse = new ApiResponse<Map<String, Long>>();
        apiResponse.setData(groupService.getPermissionGroupSummary());

        return apiResponse;
    }

    /**
     * POST  -> get the group details for saving
     */
    @RequestMapping(value = "/{groupName}/save",
            method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Object> saveGroupDetails(@PathVariable String groupName, @RequestBody PermissionGroupsDTO permissionGroupsDTO) {
        LOG.debug("REST request to save Group Details");
        final ApiResponse<Object> apiResponse = new ApiResponse <Object>();
        boolean response = false;

        // groupName is required
        if (groupName == null || groupName.length() <=0) {
            LOG.debug("Group Name is required");
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1045");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1045", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
        }
        // Group details is required for the save
        if (permissionGroupsDTO == null) {
            LOG.debug("Group Details is required");
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1050");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1050", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
        }
        // call service layer method saveGroupDetails to save the group details
        try {
            response = groupService.saveGroupDetails(permissionGroupsDTO);
            LOG.debug("Response ", response);
            if (response == true) {
                apiResponse.setExplicitDismissal(Boolean.FALSE);
                apiResponse.setLevel(Level.INFO);
                apiResponse.setCode("1043");
                apiResponse.setStatus("success");
                apiResponse.setMessage(messageSource.getMessage("1043", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            }
            else
            {
                LOG.debug("An error has occurred");
                apiResponse.setExplicitDismissal(Boolean.TRUE);
                apiResponse.setLevel(Level.ERROR);
                apiResponse.setCode("1049");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1049", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            }
        }
        catch (IllegalArgumentException ie) {
            LOG.debug("IllegalArgumentException thrown...", ie.getMessage());
            String errorMessage = ie.getMessage();
            String code = null;
            if (errorMessage.equals("Group Name is required")) {
                code = "1044";
            }
            else if (errorMessage.equals("Assigned Permissions list is required"))
            {
                code = "1045";
            }
            else if (errorMessage.equals("Unknown permission"))
            {
                code = "1046";
            }
            else if (errorMessage.equals("Enrolled Users list is required"))
            {
                code = "1048";
            }
            else if (errorMessage.equals("Unknown user"))
            {
                code = "1047";
            }
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode(code);
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage(code, new Object[] {groupName}, LocaleContextHolder.getLocale()));
        }
        catch (Exception e) {
            LOG.debug("Exception thrown...", e.getMessage());
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1049");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1049", new Object[] {groupName}, LocaleContextHolder.getLocale()));
        }

        LOG.debug("return");
        return apiResponse;
    }

    /**
     * POST  -> return the group details
     */
    @RequestMapping(value = "/{groupName}/details",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Object> getGroupDetails(@PathVariable String groupName) {
        LOG.debug("REST request to get Group details");
        final ApiResponse<Object> apiResponse = new ApiResponse <Object>();
        PermissionGroupsDTO permissionGroupDTO = null;

        // groupName is required
        if (groupName == null || groupName.length() <=0) {
            LOG.debug("Group Name is required");
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1044");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1044", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
        }

        // call service layer method getGroupDetails to save the group details
        try {
            permissionGroupDTO = groupService.getGroupDetails(groupName);
        }
        catch (Exception e) {
            LOG.debug("Exception thrown...", e.getMessage());
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1049");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1049", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            return apiResponse;
        }
        if (permissionGroupDTO == null) {
            permissionGroupDTO = new PermissionGroupsDTO(groupName, null, new ArrayList<String>(), new ArrayList<String>());
        }
        apiResponse.setStatus("success");
        apiResponse.setData(permissionGroupDTO);

        LOG.debug("return");
        return apiResponse;
    }

    /**
     * POST  -> delete the group details
     */
    @RequestMapping(value = "/delete",
            method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Object> deleteGroupDetails(@RequestBody PermissionGroupsDTO permissionGroupsDTO) {
        LOG.debug("REST request to delete Group Details");
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        String groupName = null;
        boolean response = false;

        // Group name is required for the delete
        if (permissionGroupsDTO == null) {
            LOG.debug("Group Name is required");
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1044");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1044", new Object[]{groupName}, LocaleContextHolder.getLocale()));
            LOG.debug(apiResponse.getMessage());
            return apiResponse;
        }
        groupName = permissionGroupsDTO.getGroupName();
        // call service layer method deleteGroupDetails to delete the group
        try {
            response = groupService.deleteGroupDetails(permissionGroupsDTO);
        }
        catch (IllegalArgumentException ie) {
            LOG.debug("IllegalArgumentException thrown...", ie.getMessage());
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1044");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1044", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            LOG.debug("return");
            return apiResponse;
        }
        catch (Exception e) {
            LOG.debug("Exception thrown...", e.getMessage());
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1049");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1049", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            LOG.debug("return");
            return apiResponse;
        }
        if (response == true) {
            apiResponse.setStatus("success");
            apiResponse.setLevel(Level.INFO);
            apiResponse.setCode("1051");
            apiResponse.setMessage(messageSource.getMessage("1051", new Object[]{groupName}, LocaleContextHolder.getLocale()));
        }
        else {
            apiResponse.setExplicitDismissal(Boolean.TRUE);
            apiResponse.setLevel(Level.ERROR);
            apiResponse.setCode("1052");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1052", new Object[] {groupName}, LocaleContextHolder.getLocale()));
            LOG.debug("return");
        }
        LOG.debug("return");
        return apiResponse;
    }


    /**
     * Group search API for the new filter type search and sort criteria
     *
     * @author DiepLe

     * @param searchAndSortCriteria
     * @return
     */
    @RequestMapping(value = "/search",
            method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Object> getGroupSummaryByNewFilterTypeSearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

        final ApiResponse<Object> apiResponse = new ApiResponse<>();
        List<PermissionGroupSummaryDTO> permissionGroupSummaryDTO = null;
        Long totalRecords = null;
        PermissionGroupSummariesDTO summaries = null;

        try {
            permissionGroupSummaryDTO = groupService.getGroupSummaryByNewSearchAndSortCriteria(searchAndSortCriteria);
            totalRecords = groupService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
            summaries = new PermissionGroupSummariesDTO();
            summaries.setGroupList(permissionGroupSummaryDTO);
            summaries.setTotalRecords(totalRecords);
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
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }

        apiResponse.setData(summaries);

        return apiResponse;

    }

}