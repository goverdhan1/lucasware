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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.alps.rest.ApiResponse;
import com.lucas.dto.user.PermissionGroupsDTO;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("/META-INF/spring/api-bootstrap-context.xml")
})
public class GroupControllerFunctionalTests extends AbstractControllerTests{
	
	private static final Logger LOG = LoggerFactory.getLogger(GroupControllerFunctionalTests.class);
    private static final String MODULE = "GroupControllerFunctionTest.";
	private static final String USERNAME_JACK = "jack123";
    private static final String PASSWORD_JACK="secret";
	private static final String GET_GROUP_SUMMARY_FILE_PATH = "json/GetGroupSummary.json";
	
	@Test
	public void testGetGroupSummary() throws Exception {

		final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
		final String url = "/groups/summary";
		final ResultActions apiResultActions = this.mockMvc.perform(get(url)
				.header("Authentication-token", token)
		        .contentType(MediaType.APPLICATION_JSON));
		
		LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

		String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
		LOG.debug(actualJsonString);
		Assert.notNull(actualJsonString, "/groups/summary Resulted Json is Null");
		Assert.isTrue(compareJson(actualJsonString, GET_GROUP_SUMMARY_FILE_PATH), "/groups/summary Resulted Json is not up to the expectations");
	}

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsExistingGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/picker/save";
        // build the group details post body
        final String groupName = "picker";
        final String groupDesc = "Picker group details desc";
        final List<String> permsList =new ArrayList<String>();
        permsList.add("create-group");
        permsList.add("edit-group");
        permsList.add("retrain-voice-model");
        final List<String> enrolledUserList = new ArrayList<String>();
        enrolledUserList.add("admin123");
        enrolledUserList.add("jill123");
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, groupDesc, permsList, enrolledUserList );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testSaveGroupDetailsExistingGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1043"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("Group Details " + groupName + " saved successfully"));
        org.junit.Assert.assertTrue(jsonObject.isNull("data"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsNewGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/repack/save";
        // build the group details post body
        final String groupName = "repack";
        final String groupDesc = "Repacking group details desc";
        final List<String> permsList = new ArrayList<String>();
        permsList.add("create-group");
        permsList.add("edit-group");
        permsList.add("retrain-voice-model");
        final List<String> enrolledUserList = new ArrayList<String>();
        enrolledUserList.add("admin123");
        enrolledUserList.add("jill123");
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, groupDesc, permsList, enrolledUserList );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testSaveGroupDetailsNewGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsNullGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/null/save";
        // build the group details post body
        final String groupName = null;
        final String groupDesc = "Repacking group details desc";
        final List<String> permsList = new ArrayList<String>();
        permsList.add("create-group");
        permsList.add("edit-group");
        permsList.add("retrain-voice-model");
        final List<String> enrolledUserList = new ArrayList<String>();
        enrolledUserList.add("admin123");
        enrolledUserList.add("jill123");
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, groupDesc, permsList, enrolledUserList );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testSaveGroupDetailsNullGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsEmptyDetails() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/picker/save";
        // build the group details post body
        final String groupName = "picker";
        final String groupDesc = "Picker group details desc";
        final List<String> permsList = new ArrayList<String>();
        final List<String> enrolledUserList = new ArrayList<String>();
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, groupDesc, permsList, enrolledUserList );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testSaveGroupDetailsEmptyDetails: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsInvalidPermission() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/picker/save";
        // build the group details post body
        final String groupName = "picker";
        final String groupDesc = "Picker group details desc";
        final List<String> permsList = new ArrayList<String>();
        permsList.add("Invalid-permission");
        permsList.add("edit-group");
        permsList.add("retrain-voice-model");
        final List<String> enrolledUserList = new ArrayList<String>();
        enrolledUserList.add("admin123");
        enrolledUserList.add("jill123");
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, groupDesc, permsList, enrolledUserList );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testSaveGroupDetailsInvalidPermission: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSaveGroupDetailsInvalidUser() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/picker/save";
        // build the group details post body
        final String groupName = "picker";
        final String groupDesc = "Picker group details desc";
        final List<String> permsList = new ArrayList<String>();
        permsList.add("Invalid-permission");
        permsList.add("edit-group");
        permsList.add("retrain-voice-model");
        final List<String> enrolledUserList = new ArrayList<String>();
        enrolledUserList.add("invalidusername");
        enrolledUserList.add("jill123");
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, groupDesc, permsList, enrolledUserList );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testSaveGroupDetailsInvalidUser: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));

    }

    @Test
    @Transactional (readOnly = true)
    public void testGetGroupDetailsExistingGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/picker/details";

        final ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testGetGroupDetailsExistingGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));

        JSONObject dataMap = new JSONObject(jsonObject.get("data").toString());
        LOG.debug(dataMap.get("groupName").toString());
        org.junit.Assert.assertTrue(dataMap.get("groupName").equals("picker"));
        org.junit.Assert.assertTrue(dataMap.get("description").equals("Picker group"));
        org.junit.Assert.assertTrue(dataMap.get("enrolledUsers").toString().contains("jack123"));
        org.junit.Assert.assertTrue(dataMap.get("enrolledUsers").toString().contains("jill123"));
        org.junit.Assert.assertTrue(dataMap.get("assignedPermissions").toString().contains("authenticated-user"));
        org.junit.Assert.assertTrue(dataMap.get("assignedPermissions").toString().contains("create-user"));
        org.junit.Assert.assertTrue(dataMap.get("assignedPermissions").toString().contains("edit-user"));
        org.junit.Assert.assertTrue(dataMap.get("assignedPermissions").toString().contains("create-assignment"));
        org.junit.Assert.assertTrue(dataMap.get("assignedPermissions").toString().contains("create-edit-user-widget-access"));
    }

    @Test
    @Transactional (readOnly = true)
    public void testGetGroupDetailsNonExistGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/pickerABC/details";

        final ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testGetGroupDetailsNonExistGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        JSONObject dataMap = new JSONObject(jsonObject.get("data").toString());
        LOG.debug(dataMap.get("groupName").toString());
        org.junit.Assert.assertTrue(dataMap.get("groupName").equals("pickerABC"));
        org.junit.Assert.assertTrue(dataMap.get("description").equals(""));
        org.junit.Assert.assertTrue(!dataMap.get("enrolledUsers").toString().equals(""));
        org.junit.Assert.assertTrue(!dataMap.get("assignedPermissions").toString().equals(""));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteGroupDetailsExistingGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/delete";
        // build the group details post body
        final String groupName = "picker";
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, null, null, null );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testDeleteGroupDetailsNullGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1051"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteGroupDetailsNonExistingGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/delete";
        // build the group details post body
        final String groupName = "pickerABC";
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, null, null, null );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testDeleteGroupDetailsNullGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1052"));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteGroupDetailsNullGroup() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/delete";
        // build the group details post body
        final String groupName = null;
        final PermissionGroupsDTO userGroupDetails = new PermissionGroupsDTO(groupName, null, null, null );

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(userGroupDetails))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testDeleteGroupDetailsNullGroup: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        final JSONObject jsonObject = new JSONObject(jsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1044"));
    }


    /**
     * Method to test the Alphanumeric filter with the target
     * property of groupName with value that begins with 'admin%'
     * and also userCount quantity
     * This should return a single row
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetPermissionGroupWithAllPropertiesByNewSearchAndSortCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/search";

        // build the search map criteria
        final List<String> searchList = Arrays.asList("s%");
        final List<String> descList = Arrays.asList("%group%");
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> userCountMap = new LinkedHashMap<String, Object>();
        final LinkedHashMap<String, Object> descriptionMap = new LinkedHashMap<String, Object>();

        // groupName
        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);

        // description
        descriptionMap.put("filterType", FilterType.ALPHANUMERIC);
        descriptionMap.put("values", descList);

        //user count
        userCountMap.put("filterType", FilterType.NUMERIC);
        userCountMap.put("values", Arrays.asList(">2"));

        searchMap.put("userCount", userCountMap);
        searchMap.put("permissionGroupName", searchPropertyMap);
        searchMap.put("description", descriptionMap);

        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("permissionGroupName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testGetPermissionGroupSummaryByNewSearchAndSortCriteria: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        apiResultActions.andExpect(status().isOk());

        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONArray dataArray = dataObj.getJSONArray("groupList");
        String totalRecords = dataObj.get("totalRecords").toString();
        Assert.isTrue(totalRecords.equals("1"), "Total records is: " + totalRecords);
        Assert.isTrue(dataArray.length() == 1, "More results returned than expected.");

        for (int i = 0; i < dataArray.length(); i++)
        {
            if (dataArray.getString(i).contains("groupName"))
            {
                String groupName = ((JSONObject)dataArray.get(0)).get("groupName").toString();
                Assert.isTrue(groupName.equalsIgnoreCase("system"), "Group name is not system: " + groupName);
                String description = ((JSONObject)dataArray.get(0)).get("description").toString();
                Assert.isTrue(description.equalsIgnoreCase("System group"),"Actual description is: " + description);
                String userCount = ((JSONObject)dataArray.get(0)).get("userCount").toString();
                Assert.isTrue(userCount.equalsIgnoreCase("3"),"Actual userCount is: " + userCount);

            }
        }


    }

    /**
     * Method to test the Alphanumeric filter with the target
     * property of groupName with value that begins with 'admin%'
     * This should return a single row
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetPermissionGroupWithGroupNameBeginWithAdminByNewSearchAndSortCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/search";

        // build the search map criteria
        final List<String> searchList = Arrays.asList("admin%");
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("permissionGroupName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("permissionGroupName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testGetPermissionGroupSummaryByNewSearchAndSortCriteria: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        apiResultActions.andExpect(status().isOk());

        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONArray dataArray = dataObj.getJSONArray("groupList");
        String totalRecords = dataObj.get("totalRecords").toString();
        Assert.isTrue(totalRecords.equals("1"), "Total records is: " + totalRecords);
        Assert.isTrue(dataArray.length() == 1, "More results returned than expected.");

        for (int i = 0; i < dataArray.length(); i++)
        {
            if (dataArray.getString(i).contains("groupName"))
            {
                String groupName = ((JSONObject)dataArray.get(0)).get("groupName").toString();
                Assert.isTrue(groupName.equalsIgnoreCase("administrator"),"Group name is not administrator: " + groupName);
                String description = ((JSONObject)dataArray.get(0)).get("description").toString();
                Assert.isTrue(description.equalsIgnoreCase("Admin group"),"Actual description is: " + description);
                String userCount = ((JSONObject)dataArray.get(0)).get("userCount").toString();
                Assert.isTrue(userCount.equalsIgnoreCase("2"),"Actual userCount is: " + userCount);
            }
        }
    }


    /**
     * Method to test the Alphanumeric filter with the target
     * property of groupName with value that begins with 's%'
     *
     * @author DiepLe
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testGetPermissionGroupWithGroupNameBeginWithLetterSByNewSearchAndSortCriteria() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final String url = "/groups/search";

        // build the search map criteria
        final List<String> searchList = Arrays.asList("s%");
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();
        final LinkedHashMap<String, Object> searchPropertyMap = new LinkedHashMap<String, Object>();

        searchPropertyMap.put("filterType", FilterType.ALPHANUMERIC);
        searchPropertyMap.put("values", searchList);
        searchMap.put("permissionGroupName", searchPropertyMap);
        searchAndSortCriteria.setSearchMap(searchMap);

        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("permissionGroupName", SortType.ASC);
            }
        });
        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);

        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug(MODULE + ".testGetPermissionGroupSummaryByNewSearchAndSortCriteria: controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());

        final String jsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        apiResultActions.andExpect(status().isOk());

        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JSONObject obj = new JSONObject(actualJsonString);
        Assert.isTrue(obj.get("code").toString().equals("200"), "status is not 200");
        Assert.isTrue(obj.get("status").toString().equals("success"), "status is not success");
        Assert.isTrue(obj.get("message").toString().equals("Request processed successfully"), "message is not processed successfully");

        JSONObject dataObj = new JSONObject(obj.get("data").toString());
        JSONArray dataArray = dataObj.getJSONArray("groupList");
        String totalRecords = dataObj.get("totalRecords").toString();
        Assert.isTrue(totalRecords.equals("2"), "Total records is: " + totalRecords);
        Assert.isTrue(dataArray.length() == 2, "Different number of results returned than expected.");

        for (int i = 0; i < dataArray.length(); i++)
        {
            if (dataArray.getString(i).contains("groupName"))
            {
                String groupName = ((JSONObject)dataArray.get(0)).get("groupName").toString();
                if (groupName.equals("system")) {
                    Assert.isTrue(groupName.equalsIgnoreCase("system"), "Group name is not system: " + groupName);
                    String description = ((JSONObject)dataArray.get(0)).get("description").toString();
                    Assert.isTrue(description.equalsIgnoreCase("System group"),"Actual description is: " + description);
                    String userCount = ((JSONObject)dataArray.get(0)).get("userCount").toString();
                    Assert.isTrue(userCount.equalsIgnoreCase("3"),"Actual userCount is: " + userCount);
                } else if (groupName.equals("supervisor")) {
                    Assert.isTrue(groupName.equalsIgnoreCase("supervisor"), "Group name is not supervisor: " + groupName);
                    String description = ((JSONObject)dataArray.get(0)).get("description").toString();
                    Assert.isTrue(description.equalsIgnoreCase("Supervisor group"),"Actual description is: " + description);
                    String userCount = ((JSONObject)dataArray.get(0)).get("userCount").toString();
                    Assert.isTrue(userCount.equalsIgnoreCase("2"),"Actual userCount is: " + userCount);
                }

            }
        }
    }

    // WYATT TODO: ADD SOME MORE NEGATIVE TESTS HERE
}