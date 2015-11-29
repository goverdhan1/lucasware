/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */

package com.lucas.alps.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lucas.services.filter.FilterType;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.sort.SortType;
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerFunctionalTests extends AbstractControllerTests {

	private static final Logger LOG = LoggerFactory.getLogger(UserControllerFunctionTests.class);

	private static final String PRODUCT_SEARCH_BY_COLUMNS_FILE_PATH = "json/ProductSearchByColumns.json";
	private static final String PRODUCT_SEARCH_BY_COLUMNS_NON_HAZARDOUS_MATERIAL_FILE_PATH = "json/ProductSearchByColumnsNonHazardousMaterial.json";
	private static final String PRODUCT_SEARCH_BY_COLUMNS_HAZARDOUS_MATERIAL_FILE_PATH = "json/ProductSearchByColumnsHazardousMaterial.json";
	private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CODE_NONEMPTY_FILE_PATH = "json/ProductSearchByColumnsProductCodeNonEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CODE_EMPTY_FILE_PATH = "json/ProductSearchByColumnsProductCodeEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_EMPTY_NAME_FILE_PATH = "json/ProductSearchByColumnsProductNameEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_DESCRIPTION_EMPTY_FILE_PATH = "json/ProductSearchByColumnsProductDescriptionEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_DESCRIPTION_NON_EMPTY_FILE_PATH = "json/ProductSearchByColumnsProductDescriptionNonEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_AVAILABLE_FILE_PATH = "json/ProductSearchByColumnsProductStatusCodeAvailable.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_MARKED_OUT_FILE_PATH = "json/ProductSearchByColumnsProductStatusCodeMarkedOut.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_EMPTY_FILE_PATH = "json/ProductSearchByColumnsProductStatusCodeEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_NON_EMPTY_FILE_PATH = "json/ProductSearchByColumnsProductStatusCodeNonEmpty.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CAPTURE_LOT_NUMBER_TRUE_FILE_PATH = "json/ProductSearchByColumnsProductCaptureLotNumberTrue.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CAPTURE_LOT_NUMBER_FALSE_FILE_PATH = "json/ProductSearchByColumnsProductCaptureLotNumberFalse.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_ISBASE_ITEM_FILE_PATH = "json/ProductSearchByColumnsProductIsBaseItem.json";
    private static final String PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_ISBASE_NOT_ITEM_FILE_PATH = "json/ProductSearchByColumnsProductIsNotBaseItem.json";
    
    private static final String PRODUCT_CUSTOM_CLASSIFICATIONS_FILE_PATH = "json/ProductCustomClassificationsData.json";
    
    private static final String USERNAME_JACK = "jack123";
    private static final String PASSWORD_JACK="secret";
    
    private static final String PRODUCTNAME_1 = "99-4937";
    private static final String PRODUCTNAME_2 = "99-4938";    
    private static final String PRODUCTNAME_3 = "99-4939";


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductHazardousMaterial() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("hazardousMaterial", new ArrayList<String>() {
            {
                add("Yes");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("hazardousMaterial", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_HAZARDOUS_MATERIAL_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductNonHazardousMaterial() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("hazardousMaterial", new ArrayList<String>() {
            {
                add("No");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("hazardousMaterial", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_NON_HAZARDOUS_MATERIAL_FILE_PATH));

    }





    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaByName() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("name", new ArrayList<String>() {
            {
                add(PRODUCTNAME_1);
                add(PRODUCTNAME_2);
                add(PRODUCTNAME_3);
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("name", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaByNameEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("name", new ArrayList<String>() {
            {
                add(FilterType.EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("name", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_EMPTY_NAME_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductCodeNonEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("productCode", new ArrayList<String>() {
            {
                add(FilterType.NON_EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("productCode", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CODE_NONEMPTY_FILE_PATH));

    }



    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductCodeEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("productCode", new ArrayList<String>() {
            {
                add(FilterType.EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("productCode", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CODE_EMPTY_FILE_PATH));

    }




    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductDescriptionEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("description", new ArrayList<String>() {
            {
                add(FilterType.EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("description", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_DESCRIPTION_EMPTY_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductDescriptionNonEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("description", new ArrayList<String>() {
            {
                add(FilterType.NON_EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("description", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_DESCRIPTION_NON_EMPTY_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductStatusCodeAvailable() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("productStatusCode", new ArrayList<String>() {
            {
                add("Available");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("productStatusCode", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_AVAILABLE_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductStatusCodeMarkedOut() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("productStatusCode", new ArrayList<String>() {
            {
                add("Marked Out");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("productStatusCode", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_MARKED_OUT_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductStatusCodeEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("productStatusCode", new ArrayList<String>() {
            {
                add(FilterType.EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("productStatusCode", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_EMPTY_FILE_PATH));

    }



    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductStatusCodeNonEmpty() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("productStatusCode", new ArrayList<String>() {
            {
                add(FilterType.NON_EMPTY.toString());
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("productStatusCode", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_STATUS_CODE_NON_EMPTY_FILE_PATH));

    }

    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductCaptureLotNumberTrue() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("captureLotNumber", new ArrayList<String>() {
            {
                add("Yes");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("captureLotNumber", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CAPTURE_LOT_NUMBER_TRUE_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductCaptureLotNumberFalse() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("captureLotNumber", new ArrayList<String>() {
            {
                add("No");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("captureLotNumber", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_CAPTURE_LOT_NUMBER_FALSE_FILE_PATH));

    }


    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductIsBaseItem() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("isBaseItem", new ArrayList<String>() {
            {
                add("Yes");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("isBaseItem", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_ISBASE_ITEM_FILE_PATH));

    }

    @Test
    @Transactional
    public void testGetProductListBySearchAndSortCriteriaForProductIsNotBaseItem() throws Exception {

        final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
        final SearchAndSortCriteria searchAndSortCriteria = new SearchAndSortCriteria();
        final Map<String, Object> searchMap = new HashMap<String, Object>();

        searchMap.put("isBaseItem", new ArrayList<String>() {
            {
                add("No");
            }
        });


        searchAndSortCriteria.setSearchMap(searchMap);
        searchAndSortCriteria.setSortMap(new HashMap<String, SortType>() {
            {
                put("isBaseItem", SortType.ASC);
            }
        });

        searchAndSortCriteria.setPageNumber(0);
        searchAndSortCriteria.setPageSize(3);


        final String url = "/products/productlist/search";
        final ResultActions apiResultActions = this.mockMvc.perform(post(url)
                .header("Authentication-token", token)
                .content(super.getJsonString(searchAndSortCriteria))
                .contentType(MediaType.APPLICATION_JSON));

        LOG.debug("controller returns : {}", apiResultActions.andReturn().getResponse().getContentAsString());
        String actualJsonString = apiResultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        org.junit.Assert.assertNotNull(actualJsonString);

        Assert.isTrue(compareJson(actualJsonString, PRODUCT_SEARCH_BY_COLUMNS_PRODUCT_ISBASE_NOT_ITEM_FILE_PATH));

    }
    
    /**
     * Test for Getting all the Product Custom Classification from test data
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void testGetAllProductCustomClassification() throws Exception {

      final String token = super.getAuthenticatedToken(USERNAME_JACK, PASSWORD_JACK);
      final String url = "/products/productcustomclassifications";
      final ResultActions resultActions = this.mockMvc.perform(get(url).header("Authentication-token", token).accept(MediaType.APPLICATION_JSON));

      LOG.info("Product controller for Product Custom Classification returns : {}", resultActions.andReturn().getResponse().getContentAsString());

      String actualJsonString = resultActions.andReturn().getResponse().getContentAsString();
      LOG.info(actualJsonString);
      Assert.notNull(actualJsonString, "/product/productcustomclassifications Resulted Json is Null");
      Assert.isTrue(compareJson(actualJsonString, PRODUCT_CUSTOM_CLASSIFICATIONS_FILE_PATH), "/product/productcustomclassifications Resulted Json is not up to the expectations");
    }
}
