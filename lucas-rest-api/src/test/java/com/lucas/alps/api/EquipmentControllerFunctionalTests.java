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
import com.lucas.alps.view.EquipmentPositionView;
import com.lucas.alps.view.EquipmentSearchByColumnsView;
import com.lucas.alps.view.EquipmentView;
import com.lucas.entity.equipment.Equipment;
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
 * the rest end points for Equipment .
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class EquipmentControllerFunctionalTests extends AbstractControllerTests {

    private static final Logger LOG = LoggerFactory
            .getLogger(EquipmentControllerFunctionalTests.class);

    /**
     * testEquipmentInformation() provide the functionality for testing
     * the rendering the equipment for the existing equipment
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEquipmentInformation() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/01";
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
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentId"));
        org.junit.Assert.assertNotNull("EquipmentId is null",jsonObject1.get("equipmentId"));
        org.junit.Assert.assertTrue("Invalid EquipmentId ",jsonObject1.get("equipmentId").equals(1));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentType"));
        org.junit.Assert.assertNotNull("EquipmentId is null",jsonObject1.get("equipmentType"));
        org.junit.Assert.assertTrue("Invalid EquipmentType ",jsonObject1.get("equipmentType").equals("3 Pallet Jack"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentName"));
        org.junit.Assert.assertNotNull("equipmentName is null ",jsonObject1.get("equipmentName"));
        org.junit.Assert.assertTrue("Invalid EquipmentName ",jsonObject1.get("equipmentName").equals("01"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentDescription"));
        org.junit.Assert.assertNotNull("equipmentDescription is null ",jsonObject1.get("equipmentDescription"));
        org.junit.Assert.assertTrue("Invalid EquipmentDescription ",jsonObject1.get("equipmentDescription").equals("01 - Pallet Jack"));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentPositions"));
        org.junit.Assert.assertNotNull("equipmentPositions is null ",jsonObject1.get("equipmentPositions"));
        org.junit.Assert.assertTrue("Invalid EquipmentPositions ",jsonObject1.get("equipmentPositions").equals(3));
        org.junit.Assert.assertTrue(jsonObject1.has("equipmentPositionList"));
        final JSONArray jsonArray=jsonObject1.getJSONArray("equipmentPositionList");
        for(int i=0;i<jsonArray.length();i++){
            final JSONObject object= jsonArray.getJSONObject(i);
            org.junit.Assert.assertNotNull("EquipmentTypePositionNbr is null ",object.get("equipmentPositionNbr"));
            org.junit.Assert.assertNotNull("X position is null ", object.get("xposition"));
            org.junit.Assert.assertNotNull("Y position is null ", object.get("yposition"));
            org.junit.Assert.assertNotNull("Z position is null ", object.get("zposition"));
            org.junit.Assert.assertTrue("EquipmentPositionCheckString is Missing ", object.has("equipmentPositionCheckString"));
            org.junit.Assert.assertTrue("EquipmentPositionName is Missing ", object.has("equipmentPositionName"));
        }
    }

    /**
     * testEquipmentInformationForNonExisting() provide the functionality for testing
     * the rendering the equipment for the non existing equipment
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEquipmentInformationForNonExisting() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/1";
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
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1071"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("equipment not found"));
    }


    /**
     * testEquipmentInformationForNullInput() provide the functionality for testing
     * the rendering the equipment for the non existing equipment
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEquipmentInformationForNullInput() throws Exception {
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/null";
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
        org.junit.Assert.assertTrue(jsonObject.get("code").equals("1071"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue(jsonObject.get("message").equals("equipment not found"));
    }

    /**
     * testSaveOrUpdateEquipment() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipment() throws Exception {
        final List<EquipmentPositionView> equipmentPositionList = new ArrayList<EquipmentPositionView>() {
            {
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(equipmentPositionList);
                setUser("jack123");
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
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
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1072"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));

    }


    /**
     * testSaveOrUpdateEquipmentWithOutUser() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentWithOutUser() throws Exception {
        final List<EquipmentPositionView> equipmentPositionList = new ArrayList<EquipmentPositionView>() {
            {
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(equipmentPositionList);
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
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
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1072"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));

    }

    /**
     * testSaveOrUpdateEquipment() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testSaveOrUpdateEquipmentWithOutPosition() throws Exception {

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(null);
                setUser("jack123");
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
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
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1073"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment is not saved"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * testUpdateEquipment() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipment() throws Exception {
        final List<EquipmentPositionView> equipmentPositionList = new ArrayList<EquipmentPositionView>() {
            {
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(equipmentPositionList);
                setUser("jack123");
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
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

        equipmentPositionList.add(new EquipmentPositionView() {
            {
                setEquipmentPositionNbr(3L);
                setXPosition(3L);
                setYPosition(3L);
                setZPosition(3L);
            }
        });

        equipmentPositionList.add(new EquipmentPositionView() {
            {
                setEquipmentPositionNbr(4L);
                setXPosition(4L);
                setYPosition(4L);
                setZPosition(4L);
            }
        });

        equipmentView.setEquipmentType("1 Pallet Jack");
        equipmentView.setEquipmentDescription("EquipmentUpdatedDescription");
        equipmentView.setEquipmentPositionList(equipmentPositionList);
        equipmentView.setUser("jill123");

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1072"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(true));
    }


    /**
     * testUpdateEquipmentWithOutUser() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipmentWithOutUser() throws Exception {
        final List<EquipmentPositionView> equipmentPositionList = new ArrayList<EquipmentPositionView>() {
            {
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(equipmentPositionList);
                setUser("jack123");
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));

        equipmentPositionList.add(new EquipmentPositionView() {
            {
                setEquipmentPositionNbr(3L);
                setXPosition(3L);
                setYPosition(3L);
                setZPosition(3L);
            }
        });

        equipmentPositionList.add(new EquipmentPositionView() {
            {
                setEquipmentPositionNbr(4L);
                setXPosition(4L);
                setYPosition(4L);
                setZPosition(4L);
            }
        });

        equipmentView.setEquipmentType("1 Pallet Jack");
        equipmentView.setEquipmentDescription("EquipmentUpdatedDescription");
        equipmentView.setEquipmentPositionList(equipmentPositionList);
        equipmentView.getEquipment().setUser(null);

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1072"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(true));
    }

    /**
     * testUpdateEquipmentWithOutPosition() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipmentWithOutPosition() throws Exception {
        final List<EquipmentPositionView> equipmentPositionList = new ArrayList<EquipmentPositionView>() {
            {
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(equipmentPositionList);
                setUser("jack123");
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();

        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject.get("status").equals("success"));

        equipmentView.setEquipmentType("1 Pallet Jack");
        equipmentView.setEquipmentDescription("EquipmentUpdatedDescription");
        equipmentView.getEquipment().setEquipmentPositionList(null);
        equipmentView.setUser("jill123");

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper1.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1073"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment is not saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(false));
    }


    /**
     * testUpdateEquipmentWithOutEquipmentType() provide the testing functionality for  the persisting
     * equipment  and its dependent objects.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUpdateEquipmentWithOutEquipmentType() throws Exception {
        final List<EquipmentPositionView> equipmentPositionList = new ArrayList<EquipmentPositionView>() {
            {
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(1L);
                        setXPosition(1L);
                        setYPosition(1L);
                        setZPosition(1L);
                    }
                });
                add(new EquipmentPositionView() {
                    {
                        setEquipmentPositionNbr(2L);
                        setXPosition(2L);
                        setYPosition(2L);
                        setZPosition(2L);
                    }
                });
            }
        };

        final EquipmentView equipmentView = new EquipmentView() {
            {
                setEquipmentName("EquipmentName");
                setEquipmentType("1 Pallet Jack");
                setEquipmentDescription("EquipmentDescription");
                setEquipmentPositionList(equipmentPositionList);
                setUser("jack123");
            }
        };

        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/save";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(equipmentView))
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

        equipmentPositionList.add(new EquipmentPositionView() {
            {
                setEquipmentPositionNbr(3L);
                setXPosition(3L);
                setYPosition(3L);
                setZPosition(3L);
            }
        });

        equipmentPositionList.add(new EquipmentPositionView() {
            {
                setEquipmentPositionNbr(4L);
                setXPosition(4L);
                setYPosition(4L);
                setZPosition(4L);
            }
        });

        equipmentView.getEquipment().setEquipmentType(null);
        equipmentView.setEquipmentDescription("EquipmentUpdatedDescription");
        equipmentView.setEquipmentPositionList(equipmentPositionList);
        equipmentView.setUser("jill123");

        final String token1 = super.generateTokenWithAuthenticatedUser();
        final ResultActions resultActions1 = this.mockMvc.perform(post(url)
                .header("Authentication-token", token1)
                .content(super.getJsonString(equipmentView))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString1 = resultActions1.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper1 = new ObjectMapper();
        mapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper1.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse1 = mapper.readValue(actualJsonString1, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject1 = new JSONObject(actualJsonString1);
        org.junit.Assert.assertTrue(jsonObject1.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ",jsonObject1.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject1.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject1.get("code").equals("1073"));
        org.junit.Assert.assertTrue(jsonObject1.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject1.get("message").equals("equipment is not saved"));
        org.junit.Assert.assertTrue(apiResponse1.getData().equals(false));
    }


    /**
     * testDeleteForExistingEquipment() for testing the equipment deletion
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testDeleteForExistingEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", "01");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1074"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment is deleted"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
    }


    /**
     * testDeleteForNonExistingEquipment() for testing the equipment deletion
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testDeleteForNonExistingEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", "00");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1075"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment isn't deleted"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }


    /**
     * testDeleteForNullEquipment() for testing the equipment deletion
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testDeleteForNullEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", null);
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1075"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment isn't deleted"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * testDeleteForEmptyEquipment() for testing the equipment deletion
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testDeleteForEmptyEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", "");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1075"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment isn't deleted"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * testDeleteEquipmentForInUserEquipment() for testing the equipment deletion
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testDeleteEquipmentForInUserEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", "30");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1077"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment in use"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }



    /**
     * testDeleteInputEquipment() for testing the equipment deletion
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testDeleteInputEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/delete";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1076"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("equipment deletion input is insufficient"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * testUnAssignUserForExistingEquipment() provide the testing functionality for
     * un-assign user form the equipment
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUnAssignUserForExistingEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", "30");
        inputMap.put("userName", "dummy-username25");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/unassign";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("success"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1078"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("unassignment of equipment successful"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(true));
    }


    /**
     * testUnAssignUserForNonAssignEquipment() provide the testing functionality for
     * un-assign user form the equipment
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUnAssignUserForNonAssignEquipment() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("equipmentName", "01");
        inputMap.put("userName", "dummy-username25");
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/unassign";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1079"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("unassignment of equipment failed"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }

    /**
     * testUnAssignUserForEquipmentWithEmptyInput() provide the testing functionality for
     * un-assign user form the equipment
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void testUnAssignUserForEquipmentWithEmptyInput() throws Exception {
        final Map<String, String> inputMap = new HashMap<String, String>();
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment/unassign";
        final ResultActions resultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(inputMap))
                .contentType(MediaType.APPLICATION_JSON));
        final String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final ApiResponse<Object> apiResponse = mapper.readValue(actualJsonString, new TypeReference<ApiResponse<Object>>() {
        });
        final JSONObject jsonObject = new JSONObject(actualJsonString);
        org.junit.Assert.assertTrue(jsonObject.has("status"));
        org.junit.Assert.assertTrue("Invalid Status ", jsonObject.get("status").equals("failure"));
        org.junit.Assert.assertTrue(jsonObject.has("code"));
        org.junit.Assert.assertTrue("Invalid code",jsonObject.get("code").equals("1080"));
        org.junit.Assert.assertTrue(jsonObject.has("message"));
        org.junit.Assert.assertTrue("Invalid message",jsonObject.get("message").equals("un assignment input is insufficient"));
        org.junit.Assert.assertTrue(apiResponse.getData().equals(false));
    }


    /**
     * testGetAllEquipmentBySearchAnsSortByEquipmentName() provide the functionality for getting equipment
     * based on the Search and sort having equipment name
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortByEquipmentName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentName", new ArrayList<String>() {
                            {
                                add("0");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1085"), "status is not 1085");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process successfully"), "message is not processed successfully");
        final EquipmentSearchByColumnsView equipmentSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentSearchByColumnsView.getTotalRecords() >= 2, "Equipment Records count is invalid ");
        Assert.notNull(equipmentSearchByColumnsView.getEquipment(), "Equipment list is null");
        Assert.notEmpty(equipmentSearchByColumnsView.getEquipment(), "Equipment list is empty ");
        Assert.isTrue(equipmentSearchByColumnsView.getEquipment().size() >= 2, "Equipment Records is not having expected Types ");
        for (Equipment equipment : equipmentSearchByColumnsView.getEquipment()) {
            Assert.notNull(equipment.getEquipmentType().getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipment.getEquipmentName(), "Equipment Name is null");
        }
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByEquipmentTypeName() provide the functionality for getting equipment
     * based on the Search and sort having equipment type name
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortByEquipmentTypeName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentType.equipmentTypeName", new ArrayList<String>() {
                            {
                                add("P");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("equipmentType.equipmentTypeName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1085"), "status is not 1085");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process successfully"), "message is not processed successfully");
        final EquipmentSearchByColumnsView equipmentSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentSearchByColumnsView.getTotalRecords() >= 2, "Equipment Records count is invalid ");
        Assert.notNull(equipmentSearchByColumnsView.getEquipment(), "Equipment list is null");
        Assert.notEmpty(equipmentSearchByColumnsView.getEquipment(), "Equipment list is empty ");
        Assert.isTrue(equipmentSearchByColumnsView.getEquipment().size() >= 2, "Equipment Records is not having expected Types ");
        for (Equipment equipment : equipmentSearchByColumnsView.getEquipment()) {
            Assert.notNull(equipment.getEquipmentType().getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipment.getEquipmentName(), "Equipment Name is null");
        }
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByEquipmentStyleName() provide the functionality for getting equipment
     * based on the Search and sort having equipment style name
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortByEquipmentStyleName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("equipmentStyle.equipmentStyleName", new ArrayList<String>() {
                            {
                                add("g");
                                add("f");
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
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1085"), "status is not 1085");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process successfully"), "message is not processed successfully");
        final EquipmentSearchByColumnsView equipmentSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentSearchByColumnsView.getTotalRecords() >= 2, "Equipment Records count is invalid ");
        Assert.notNull(equipmentSearchByColumnsView.getEquipment(), "Equipment list is null");
        Assert.notEmpty(equipmentSearchByColumnsView.getEquipment(), "Equipment list is empty ");
        Assert.isTrue(equipmentSearchByColumnsView.getEquipment().size() >= 2, "Equipment Records is not having expected Types ");
        for (Equipment equipment : equipmentSearchByColumnsView.getEquipment()) {
            Assert.notNull(equipment.getEquipmentType().getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipment.getEquipmentName(), "Equipment Name is null");
        }
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByUserName() provide the functionality for getting equipment
     * based on the Search and sort having user name
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortByUserName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.userName", new ArrayList<String>() {
                            {
                                add("dummy-username24");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.userName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1085"), "status is not 1085");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process successfully"), "message is not processed successfully");
        final EquipmentSearchByColumnsView equipmentSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentSearchByColumnsView.getTotalRecords() >= 1, "Equipment Records count is invalid ");
        Assert.notNull(equipmentSearchByColumnsView.getEquipment(), "Equipment list is null");
        Assert.notEmpty(equipmentSearchByColumnsView.getEquipment(), "Equipment list is empty ");
        Assert.isTrue(equipmentSearchByColumnsView.getEquipment().size() >= 1, "Equipment Records is not having expected Types ");
        for (Equipment equipment : equipmentSearchByColumnsView.getEquipment()) {
            Assert.notNull(equipment.getEquipmentType().getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipment.getEquipmentName(), "Equipment Name is null");
        }
    }

    /**
     * testGetAllEquipmentBySearchAnsSortByUserFirstName() provide the functionality for getting equipment
     * based on the Search and sort having user first name
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortByUserFirstName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.firstName", new ArrayList<String>() {
                            {
                                add("dummy-firstName");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.firstName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1085"), "status is not 1085");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process successfully"), "message is not processed successfully");
        final EquipmentSearchByColumnsView equipmentSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentSearchByColumnsView.getTotalRecords() >= 1, "Equipment Records count is invalid ");
        Assert.notNull(equipmentSearchByColumnsView.getEquipment(), "Equipment list is null");
        Assert.notEmpty(equipmentSearchByColumnsView.getEquipment(), "Equipment list is empty ");
        Assert.isTrue(equipmentSearchByColumnsView.getEquipment().size() >= 1, "Equipment Records is not having expected Types ");
        for (Equipment equipment : equipmentSearchByColumnsView.getEquipment()) {
            Assert.notNull(equipment.getEquipmentType().getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipment.getEquipmentName(), "Equipment Name is null");
        }
    }


    /**
     * testGetAllEquipmentBySearchAnsSortByUserLastName() provide the functionality for getting equipment
     * based on the Search and sort having user last name
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortByUserLastName() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>() {
                    {
                        put("user.lastName", new ArrayList<String>() {
                            {
                                add("dummy-LastName");
                            }
                        });
                    }
                });
                setSortMap(new HashMap<String, SortType>() {
                    {
                        put("user.lastName", SortType.ASC);
                    }
                });
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1085"), "status is not 1085");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("success"), "status is not success");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("search sort for equipment process successfully"), "message is not processed successfully");
        final EquipmentSearchByColumnsView equipmentSearchByColumnsView = apiResponse.getData();
        Assert.isTrue(equipmentSearchByColumnsView.getTotalRecords() >= 1, "Equipment Records count is invalid ");
        Assert.notNull(equipmentSearchByColumnsView.getEquipment(), "Equipment list is null");
        Assert.notEmpty(equipmentSearchByColumnsView.getEquipment(), "Equipment list is empty ");
        Assert.isTrue(equipmentSearchByColumnsView.getEquipment().size() >= 1, "Equipment Records is not having expected Types ");
        for (Equipment equipment : equipmentSearchByColumnsView.getEquipment()) {
            Assert.notNull(equipment.getEquipmentType().getEquipmentStyle(), "Equipment Type Style is null");
            Assert.notNull(equipment.getEquipmentName(), "Equipment Name is null");
        }
    }


    /**
     * testGetAllEquipmentBySearchAnsSortWhenSearchAndSortCriteriaIsNull() provide the functionality for getting equipment
     * based on the Search and sort having null maps
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortWhenSearchAndSortCriteriaIsNull() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(null);
                setSortMap(null);
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1087"), "status is not 1087");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("insufficient input for search sort for equipment"), "message is not correct ");
    }


    /**
     * testGetAllEquipmentBySearchAnsSortWhenSearchAndSortCriteriaIsNull() provide the functionality for getting equipment
     * based on the Search and sort having empty maps
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllEquipmentBySearchAnsSortWhenSearchAndSortIsEmpty() throws Exception {

        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria() {
            {
                setSearchMap(new HashMap<String, Object>());
                setSortMap(new HashMap<String, SortType>());
            }
        };
        final String token = super.generateTokenWithAuthenticatedUser();
        final String url = "/equipments/equipment-manager-list/search";
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
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = mapper.readValue(jsonString, new TypeReference<ApiResponse<EquipmentSearchByColumnsView>>() {
        });
        org.springframework.util.Assert.isTrue(apiResponse.getCode().equals("1087"), "status is not 1087");
        org.springframework.util.Assert.isTrue(apiResponse.getStatus().equals("failure"), "status is not failure");
        org.springframework.util.Assert.isTrue(apiResponse.getMessage().equals("insufficient input for search sort for equipment"), "message is not correct ");
    }


}
