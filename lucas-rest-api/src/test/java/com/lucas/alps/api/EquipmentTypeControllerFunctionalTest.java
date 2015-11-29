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
import com.lucas.alps.view.*;
import com.lucas.entity.equipment.*;
import com.lucas.entity.user.User;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/22/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality test for exposed
 * the rest end points for Equipment Types.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EquipmentTypeControllerFunctionalTest extends AbstractControllerTests {

    private static final Logger LOG = LoggerFactory
            .getLogger(EquipmentTypeControllerFunctionalTest.class);


    /**
     * testGetAllEquipmentTypeBySearchAnsSort() provide the testing functionality
     * on the EquipmentType based on EquipmentType using SearchAndSortCriteria.
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentTypeBySearchAnsSort() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentTypeName", new ArrayList<String>() {
                            {
                                add("%Pallet Jack%");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-list/search";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Equipment Type Data Json is Null");
        resultActions.andExpect(status().isOk());
        LOG.info(jsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<EquipmentTypeSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentTypeSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1094"), "status is not 1094");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment type process successfully"), "message is not correct");
        final EquipmentTypeSearchByColumnsView equipmentTypeSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentTypeSearchByColumnsView.getTotalRecords() >= 3, "Equipment Type Records count is invalid ");
        Assert.notNull(equipmentTypeSearchByColumnsView.getEquipmentTypes(), "Equipment Type list is null");
        Assert.notEmpty(equipmentTypeSearchByColumnsView.getEquipmentTypes(), "Equipment Type list is empty ");
        Assert.isTrue(equipmentTypeSearchByColumnsView.getEquipmentTypes().size() >= 3, "Equipment Type Records is not having expected Types ");
        for (EquipmentType equipmentType : equipmentTypeSearchByColumnsView.getEquipmentTypes()) {
            Assert.notNull(equipmentType.getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipmentType.getEquipmentTypeName(), "Equipment Type Name is null");
        }
    }

    /**
     * testGetAllEquipmentTypeByEquipmentStyleSearchAnsSort() provide the testing functionality
     * on the EquipmentType based on EquipmentStyle using SearchAndSortCriteria.
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentTypeByEquipmentStyleSearchAnsSort() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentStyle.equipmentStyleName", new ArrayList<String>() {
                            {
                                add("generic");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentStyle.equipmentStyleName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-list/search";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Equipment Type Data Json is Null");
        resultActions.andExpect(status().isOk());
        LOG.info(jsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<EquipmentTypeSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentTypeSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1094"), "status is not 1094");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment type process successfully"), "message is not correct");
        final EquipmentTypeSearchByColumnsView equipmentTypeSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentTypeSearchByColumnsView.getTotalRecords() >= 3, "Equipment Type Records count is invalid ");
        Assert.notNull(equipmentTypeSearchByColumnsView.getEquipmentTypes(), "Equipment Type list is null");
        Assert.notEmpty(equipmentTypeSearchByColumnsView.getEquipmentTypes(), "Equipment Type list is empty ");
        Assert.isTrue(equipmentTypeSearchByColumnsView.getEquipmentTypes().size() >= 3, "Equipment Type Records is not having expected Types ");
        for (EquipmentType equipmentType : equipmentTypeSearchByColumnsView.getEquipmentTypes()) {
            Assert.notNull(equipmentType.getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipmentType.getEquipmentTypeName(), "Equipment Type Name is null");
        }
    }


    @Test
    @Transactional
    public void testGetAllEquipmentTypeBySearchAnsSortWithNullSearchValue() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentTypeName", new ArrayList<String>() {
                            {
                                add(null);
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-list/search";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Equipment Type Data Json is Null");
        resultActions.andExpect(status().isOk());
        LOG.info(jsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<EquipmentTypeSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentTypeSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1095"), "status is not 1095");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process is unsuccessful"), "message is not correct");
        final EquipmentTypeSearchByColumnsView equipmentTypeSearchByColumnsView = apiResponse.getData();
        Assert.isNull(equipmentTypeSearchByColumnsView, "Data isn't Null ");
    }


    @Test
    @Transactional
    public void testGetAllEquipmentTypeBySearchAndSortWithEmpty() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>());
                setSortMap(new HashMap<String, SortType>());
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-list/search";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token).content(super.getJsonString(searchAndSortCriteria))
                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON));
        final String jsonString = resultActions.andReturn().getResponse()
                .getContentAsString();
        org.junit.Assert.assertNotNull(jsonString, "Equipment Type Data Json is Null");
        resultActions.andExpect(status().isOk());
        LOG.info(jsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<EquipmentTypeSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentTypeSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1096"), "status is not 1096");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("insufficient input for search sort for equipment type"), "message is not correct");
        final EquipmentTypeSearchByColumnsView equipmentTypeSearchByColumnsView = apiResponse.getData();
        Assert.isNull(equipmentTypeSearchByColumnsView, "Data isn't Null ");
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testGetEquipmentTypeList() throws Exception {

        String token = generateTokenWithAuthenticatedUser();
        String url = "/equipments/equipment-types";

        ResultActions apiResultActions = this.mockMvc.perform(get(url)
                .header("Authentication-token", token)
                .contentType(MediaType.APPLICATION_JSON));
        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map<String, List<String>>> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map<String, List<String>>>>() {
        });
        final Map<String, List<String>> stringListMap = apiResponse.getData();
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");
        final List<String> typeNameList = stringListMap.get("equipmentType");
        Assert.isTrue(typeNameList.size() > 0, "Equipment Style Records count is invalid ");
    }


    @Test
    @Transactional
    public void testGetAllEquipmentStyles() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-styles-names";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Map<String, List<String>>> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Map<String, List<String>>>>() {
        });
        final Map<String, List<String>> stringListMap = apiResponse.getData();
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("200"), "status is not 200");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("Request processed successfully"), "message is not processed successfully");
        final List<String> nameList = stringListMap.get("equipmentStyle");
        Assert.isTrue(nameList.size() >= 6, "Equipment Style Records count is invalid ");
    }


    @Test
    @Transactional
    public void testEquipmentQuestion() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-checklist-questions";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        org.junit.Assert.assertTrue(jsonObject.has("data"));
        final JSONArray jsonArray = jsonObject.getJSONArray("data");
        final int size = jsonObject.getJSONArray("data").length();
        org.junit.Assert.assertTrue("Size of the Question list is not Up to Expectation ", size >= 19);
        for (int i = 0; i < size; i++) {
            final JSONObject questionJsonObject = jsonArray.getJSONObject(i);
            org.junit.Assert.assertNotNull("Question is Null ", questionJsonObject);
            final JSONObject answerType = questionJsonObject.getJSONObject("answerType");
            org.junit.Assert.assertNotNull("Answer Type  is Null ", answerType);
            org.junit.Assert.assertNotNull("Answer Type Id is Null ", answerType.get("answerTypeId"));
            org.junit.Assert.assertNotNull("Answer Type Name is Null ", answerType.get("answerTypeName"));
            final JSONObject questionType = questionJsonObject.getJSONObject("questionType");
            org.junit.Assert.assertNotNull("Question Type  is Null ", questionType);
            org.junit.Assert.assertNotNull("Question Type Id is Null ", questionType.get("questionTypeId"));
            org.junit.Assert.assertNotNull("Question Type Name is Null ", questionType.get("questionTypeName"));
            questionJsonObject.get("questionId");
            questionJsonObject.get("questionDescription");
            questionJsonObject.get("questionFriendlyPrompt");
            questionJsonObject.get("questionHelp");
            questionJsonObject.get("questionPrompt");
        }
    }
    
    /* Test method to soft delete an EquipmentType and to verify the appropriate message
    *
    * @throws Exception
    *
    */
    @Rollback(value=true)
    @Test
    @Transactional
    public void testdeleteEquipmentByType() throws Exception {

        final Map<String, String> deleteEquipment = new HashMap<String, String>();
        deleteEquipment.put("type", "3 Pallet Jack");

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url).header("Authentication-token", token).content(super.getJsonString(deleteEquipment)).contentType(MediaType.APPLICATION_JSON));

        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);

        JSONObject response = new JSONObject(resultActions.andReturn().getResponse().getContentAsString());

        org.junit.Assert.assertTrue("API Response does not have property 'status'", response.has("status"));
        org.junit.Assert.assertTrue("API response status was not 'success'", response.get("status").equals("failure"));

        org.junit.Assert.assertTrue("API Response does not have property 'code'", response.has("code"));
        org.junit.Assert.assertTrue("API response code was not '1054'", response.get("code").equals("1054"));

        org.junit.Assert.assertTrue("API Response does not have property 'message'", response.has("message"));
        org.junit.Assert.assertTrue("API response message was not 'Request processed successfully'", response.get("message").equals("equipment_exists"));
    }



    /**
     * testSelectedQuestionsAndPreferredAnswer() provide the functionality for testing the
     * rendering the selected question and its preferred answers based on the equipment type
     * this will test when equipment type is valid
     * @throws Exception
     */
    @Test
    @Transactional
    public void testSelectedQuestionsAndPreferredAnswer() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-checklist-questions/1 Pallet Jack";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        org.junit.Assert.assertTrue(jsonObject.has("data"));
        final JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentStyle"));
        org.junit.Assert.assertNotNull("EquipmentStyle is null", jsonObject1.get("equipmentStyle"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentTypeName"));
        org.junit.Assert.assertNotNull("EquipmentTypeName is null ", jsonObject1.get("equipmentTypeName"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentTypeDescription"));
        org.junit.Assert.assertNotNull("EquipmentTypeDescription is null ", jsonObject1.get("equipmentTypeDescription"));
        org.junit.Assert.assertTrue(jsonObject1.has("requiresCheckList"));
        org.junit.Assert.assertNotNull("RequiresCheckList is null ", jsonObject1.get("requiresCheckList"));
        org.junit.Assert.assertTrue(jsonObject1.has("requiresCertification"));
        org.junit.Assert.assertNotNull("RequiresCertification is null ", jsonObject1.get("requiresCertification"));
        org.junit.Assert.assertTrue(jsonObject1.has("shippingContainer"));
        org.junit.Assert.assertNotNull("ShippingContainer is null ", jsonObject1.get("shippingContainer"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentCount"));
        org.junit.Assert.assertNotNull("EquipmentCount is null ",jsonObject1.get("equipmentCount"));
        org.junit.Assert.assertTrue(jsonObject1.has("selectedQuestion"));
        final JSONArray jsonArray=jsonObject1.getJSONArray("selectedQuestion");
        for(int i=0;i<jsonArray.length();i++){
            final JSONObject object= jsonArray.getJSONObject(i);
            org.junit.Assert.assertNotNull("Question Id is null ",object.get("questionId"));
            org.junit.Assert.assertNotNull("Operand is null ",object.get("operand"));
            org.junit.Assert.assertNotNull("Operator is null ",object.get("operator"));
            org.junit.Assert.assertNotNull("AnswerEvaluationRuleId is null ",object.get("answerEvaluationRuleId"));
        }
    }

    /**
     * testSelectedQuestionsAndPreferredAnswerForNonExistingEquipmentType() provide the functionality for testing the
     * rendering the selected question and its preferred answers based on the equipment type
     * this will test when equipment type is invalid
     * @throws Exception
     */
    @Test
    @Transactional
    public void testSelectedQuestionsAndPreferredAnswerForNonExistingEquipmentType() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-checklist-questions/1";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1056"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("equipment type not found"));
    }

    /**
     * testSelectedQuestionsAndPreferredAnswerWithNullEquipmentType() provide the functionality for testing the
     * rendering the selected question and its preferred answers based on the equipment type
     * this will test when equipment type is null
     * @throws Exception
     */
    @Test
    @Transactional
    public void testSelectedQuestionsAndPreferredAnswerWithNullEquipmentType() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type-checklist-questions/" + null;
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1056"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("equipment type not found"));
    }


    /**
     * testEquipmentTypeInformation() provide the functionality for testing
     * the rendering the equipment-type for the existing equipment-type
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEquipmentTypeInformation() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/1 Pallet Jack";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("200"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("Request processed successfully"));
        org.junit.Assert.assertTrue(jsonObject.has("data"));
        final JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentStyle"));
        org.junit.Assert.assertNotNull("EquipmentStyle is null", jsonObject1.get("equipmentStyle"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentTypeName"));
        org.junit.Assert.assertNotNull("EquipmentTypeName is null ", jsonObject1.get("equipmentTypeName"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentTypeDescription"));
        org.junit.Assert.assertNotNull("EquipmentTypeDescription is null ", jsonObject1.get("equipmentTypeDescription"));
        org.junit.Assert.assertTrue(jsonObject1.has("requiresCheckList"));
        org.junit.Assert.assertNotNull("RequiresCheckList is null ", jsonObject1.get("requiresCheckList"));
        org.junit.Assert.assertTrue(jsonObject1.has("requiresCertification"));
        org.junit.Assert.assertNotNull("RequiresCertification is null ", jsonObject1.get("requiresCertification"));
        org.junit.Assert.assertTrue(jsonObject1.has("shippingContainer"));
        org.junit.Assert.assertNotNull("ShippingContainer is null ", jsonObject1.get("shippingContainer"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentTypePositions"));
        org.junit.Assert.assertNotNull("EquipmentCount is null ", jsonObject1.get("equipmentTypePositions"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentTypePositionList"));
        final JSONArray jsonArray = jsonObject1.getJSONArray("equipmentTypePositionList");
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject object = jsonArray.getJSONObject(i);
            org.junit.Assert.assertNotNull("EquipmentTypePositionId Id is null ", object.get("equipmentTypePositionId"));
            org.junit.Assert.assertNotNull("EquipmentTypePositionNbr is null ", object.get("equipmentTypePositionNbr"));
            org.junit.Assert.assertNotNull("X position is null ", object.get("xposition"));
            org.junit.Assert.assertNotNull("Y position is null ", object.get("yposition"));
            org.junit.Assert.assertNotNull("Z position is null ", object.get("zposition"));
        }
    }



    /**
     * testEquipmentTypeInformationForNonExistingEquipmentType() provide the functionality for testing
     * the rendering the equipment-type for the non existing equipment-type
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEquipmentTypeInformationForNonExistingEquipmentType() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/1";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1056"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("equipment type not found"));
    }



    /**
     * testEquipmentTypeInformationWithNullEquipmentType() provide the functionality for testing
     * the rendering the equipment-type for the null equipment-type
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEquipmentTypeInformationWithNullEquipmentType() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/null";
        final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        LOG.debug("controller returns : {}", actualJsonString);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue(jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1056"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("equipment type not found"));
    }

    /**
     * testSaveOrUpdateEquipmentType() provide the testing functionality for  the persisting
     * equipment type and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentType() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));

    }

    /**
     * testSaveOrUpdateEquipmentTypeWithoutEquipmentTypePosition() provide the testing functionality for  the persisting
     * equipment type and its dependent objects where EquipmentTypePosition is missing.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentTypeWithoutEquipmentTypePosition() throws Exception {

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(null);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1059"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type is not saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * testSaveOrUpdateEquipmentTypeWithOutEquipmentStyle() provide the testing functionality for  the persisting
     * equipment type and its dependent objects. where EquipmentStyle is missing.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentTypeWithOutEquipmentStyle() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType(null);
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1059"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type is not saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));

    }


    /**
     * testSaveOrUpdateEquipmentTypeWithOutContainerType() provide the testing functionality for  the persisting
     * equipment type and its dependent objects. where ContainerType is missing.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentTypeWithOutContainerType() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType(null);
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1059"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type is not saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }



    /**
     * testSaveOrUpdateEquipmentTypeWithoutAnswerEvaluationRule() provide the testing functionality for  the persisting
     * equipment type and its dependent objects where AnswerEvaluationRule is missing.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentTypeWithoutAnswerEvaluationRule() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(null);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
    }


    /**
     * testSaveOrUpdateEquipmentTypeWithoutLucasUserCertifiedEquipmentType() provide the testing functionality for  the persisting
     * equipment type and its dependent objects where LucasUserCertifiedEquipmentType is missing .
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentTypeWithoutLucasUserCertifiedEquipmentType() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(null);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
    }

    /**
     * testUpdateEquipmentType() provide the testing functionality for  the persisting a new
     * equipment type and its dependent objects then followed by update operation
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipmentType() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));


        //update operation
        equipmentTypeView.setEquipmentTypePosition(new EquipmentTypePositionView() {
            {
                setEquipmentTypePositionNbr(3L);
                setXPosition(3L);
                setYPosition(3L);
                setZPosition(3L);
            }
        });
        equipmentTypeView.setEquipmentTypePosition(new EquipmentTypePositionView() {
            {
                setEquipmentTypePositionNbr(4L);
                setXPosition(4L);
                setYPosition(4L);
                setZPosition(4L);
            }
        });

        equipmentTypeView.setAnswerEvaluationRule(new AnswerEvaluationRuleView() {
            {
                setQuestion(3L);
                setOperator("=");
                setOperand("True");
            }
        });
        equipmentTypeView.setAnswerEvaluationRule(new AnswerEvaluationRuleView() {
            {
                setQuestion(4L);
                setOperator("=");
                setOperand("True");
            }
        });

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(true));

    }


    /**
     * testUpdateEquipmentTypeWithOutEquipmentTypePosition() provide the testing functionality for  the persisting a new
     * equipment type and its dependent objects then followed by update operation where EquipmentTypePosition is missing.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipmentTypeWithOutEquipmentTypePosition() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));

        equipmentTypeView.getEquipmentType().setEquipmentTypePositionList(null);

        equipmentTypeView.setAnswerEvaluationRule(new AnswerEvaluationRuleView() {
            {
                setQuestion(3L);
                setOperator("=");
                setOperand("True");
            }
        });
        equipmentTypeView.setAnswerEvaluationRule(new AnswerEvaluationRuleView() {
            {
                setQuestion(4L);
                setOperator("=");
                setOperand("True");
            }
        });

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1059"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment type is not saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(false));
    }


    /**
     * testUpdateEquipmentTypeWithOutEquipmentStyle() provide the testing functionality for  the persisting a new
     * equipment type and its dependent objects then followed by update operation where EquipmentStyle is missing.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipmentTypeWithOutEquipmentStyle() throws Exception {
        final List<EquipmentTypePositionView> equipmentTypePositionList = new ArrayList<EquipmentTypePositionView>() {
            {
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentTypePositionView() {
                    {
                        setEquipmentTypePositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<AnswerEvaluationRuleView>() {
            {
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(1L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
                add(new AnswerEvaluationRuleView() {
                    {
                        setQuestion(2L);
                        setOperator("=");
                        setOperand("True");
                    }
                });
            }
        };

        final List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypeList=new ArrayList<LucasUserCertifiedEquipmentType>(){
            {
                add(new LucasUserCertifiedEquipmentType(){
                    {
                        setUser(new User(){
                            {
                                setUserName("jack123");
                            }
                        });
                    }
                });
            }
        };

        final EquipmentTypeView equipmentTypeView = new EquipmentTypeView() {
            {
                setEquipmentStyle("generic");
                setEquipmentTypeName("EquipmentTypeName");
                setEquipmentTypeDescription("EquipmentTypeDescription");
                setEquipmentTypePositionList(equipmentTypePositionList);
                setAnswerEvaluationRules(answerEvaluationRuleViews);
                setContainerType("Carton");
                setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypeList);
                setEquipmentTypePositions(2L);
                setEquipmentCount(0L);
                setEquipments(null);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-type/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1058"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment type saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));


        //update operation
        equipmentTypeView.setEquipmentTypePosition(new EquipmentTypePositionView() {
            {
                setEquipmentTypePositionNbr(3L);
                setXPosition(3L);
                setYPosition(3L);
                setZPosition(3L);
            }
        });
        equipmentTypeView.setEquipmentTypePosition(new EquipmentTypePositionView() {
            {
                setEquipmentTypePositionNbr(4L);
                setXPosition(4L);
                setYPosition(4L);
                setZPosition(4L);
            }
        });

        equipmentTypeView.setAnswerEvaluationRule(new AnswerEvaluationRuleView() {
            {
                setQuestion(3L);
                setOperator("=");
                setOperand("True");
            }
        });
        equipmentTypeView.setAnswerEvaluationRule(new AnswerEvaluationRuleView() {
            {
                setQuestion(4L);
                setOperator("=");
                setOperand("True");
            }
        });

        equipmentTypeView.getEquipmentType().setEquipmentStyle(null);

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentTypeView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1059"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment type is not saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(false));

    }

}
