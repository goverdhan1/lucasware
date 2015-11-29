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

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.MappingSearchByColumnsView;
import com.lucas.alps.view.MappingView;
import com.lucas.alps.view.MessageSegmentView;
import com.lucas.alps.viewtype.MappingDetailView;
import com.lucas.alps.viewtype.MappingSearchByColumnsDetailsView;
import com.lucas.entity.eai.mapping.Mapping;
import com.lucas.entity.eai.message.Segments;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.eai.MappingService;
import com.lucas.services.search.SearchAndSortCriteria;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 7/8/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for EAI Mappings.
 */
@Controller
public class MappingController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MappingController.class);
	private MappingService mappingService;
	private final MessageSource messageSource;
	
	@Inject
	public MappingController(MappingService mappingService, MessageSource messageSource) {
		this.mappingService = mappingService;
		this.messageSource = messageSource;
	}
	
	
	
	/** getMappingBySearchAndSortCriteria() provide the functionality for searching and sorting the mappings
	 * @param searchAndSortCriteria
	 * @return
	 */
	@ResponseView(MappingSearchByColumnsDetailsView.class)
	@RequestMapping(method = RequestMethod.POST, value = "/mappings/mappinglist/search")
	public @ResponseBody
	ApiResponse<MappingSearchByColumnsView> getMappingBySearchAndSortCriteria(
			@RequestBody SearchAndSortCriteria searchAndSortCriteria) {

		final ApiResponse<MappingSearchByColumnsView> apiResponse = new ApiResponse<MappingSearchByColumnsView>();
		List<Mapping> mappingList = null;
		Long totalRecords = null;
		MappingSearchByColumnsView mappingSearchBycolumnsView = null;
		try {
			mappingList = mappingService
					.getMappingListBySearchAndSortCriteria(searchAndSortCriteria);
			totalRecords = mappingService
					.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
			mappingSearchBycolumnsView = new MappingSearchByColumnsView(
					mappingList, totalRecords);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LucasRuntimeException(
					LucasRuntimeException.INTERNAL_ERROR, e);
		}
		apiResponse.setData(mappingSearchBycolumnsView);

		LOG.debug("Size{}", mappingList.size());
		return apiResponse;
	}
	

    /**
     * getMappingNames() provide the functionality for getting the list of
     * the mappings names from db by using MappingService and MappingDao
     * internally
     *
     * @return the instance of java.util.List containing the names of the mapping
     * in the form of string objects.
     * @throws Exception
     * @see com.lucas.services.eai.MappingService#getMappingsNames()
     * @see com.lucas.dao.eai.MappingDao#getMappingsNames()
     */
    @RequestMapping(method = RequestMethod.GET, value = "/mappings/names")
    public
    @ResponseBody
    ApiResponse<List<String>> getMappingNames() throws Exception {
        final ApiResponse<List<String>> apiResponse = new ApiResponse<List<String>>();
        try {
            final List<String> mappingsNameList = this.mappingService.getMappingsNames();
            if (mappingsNameList != null && !mappingsNameList.isEmpty()) {
                LOG.info("Mapping Names List is Not Null and Empty ");
                //mapping list is not empty
                apiResponse.setCode("1098");
                apiResponse.setStatus("success");
                apiResponse.setData(mappingsNameList);
                apiResponse.setMessage(messageSource.getMessage("1098"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
            } else {
                LOG.info("Mapping Names List is Null or Empty ");
                //mapping List is empty
                apiResponse.setCode("1099");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1099"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
            }
        } catch (Exception e) {

            LOG.error("Exception Generated while fetching Mapping Names List from db ", e);
            apiResponse.setCode("1100");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1100"
                    , new Object[]{}
                    , LocaleContextHolder.getLocale()));
        }
        return apiResponse;
    }


    /**
     * getMappingByName() provide the functionality for getting the
     * the mapping from db by using MappingService and MappingDao internally
     *
     * @return the instance of Mapping
     * @throws Exception
     * @see com.lucas.services.eai.MappingService#getMappingByName(String)
     */
    @ResponseView(MappingDetailView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/mappings/{mappingName}/definitions")
    public
    @ResponseBody
    ApiResponse<MappingView> getMappingByName(@PathVariable("mappingName") String mappingName) throws Exception {
        final ApiResponse<MappingView> apiResponse = new ApiResponse<MappingView>();
        try {
            if (mappingName != null && !mappingName.isEmpty()) {
                final com.lucas.entity.eai.mapping.Mapping mapping = this.mappingService.getMappingByName(mappingName, Boolean.TRUE);
                if (mapping != null) {
                    LOG.info("Mapping is Found in Db ");
                    //mapping list is not empty
                    apiResponse.setCode("1101");
                    apiResponse.setStatus("success");
                    apiResponse.setData(new MappingView(mapping));
                    apiResponse.setMessage(messageSource.getMessage("1101"
                            , new Object[]{mappingName}
                            , LocaleContextHolder.getLocale()));
                } else {
                    LOG.info("Mapping isn't Found in DB ");
                    //mapping is not found
                    apiResponse.setCode("1102");
                    apiResponse.setStatus("failure");
                    apiResponse.setMessage(messageSource.getMessage("1102"
                            , new Object[]{mappingName}
                            , LocaleContextHolder.getLocale()));
                }
            } else {
                LOG.info("In Sufficient Input for getting Mapping  ");
                //input for getting mapping is not sufficient
                apiResponse.setCode("1103");
                apiResponse.setStatus("failure");
                apiResponse.setMessage(messageSource.getMessage("1103"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
            }

        } catch (Exception e) {
            LOG.error("Exception Generated while fetching Mapping  from db ", e);
            apiResponse.setCode("1104");
            apiResponse.setStatus("failure");
            apiResponse.setMessage(messageSource.getMessage("1104"
                    , new Object[]{}
                    , LocaleContextHolder.getLocale()));
        }
        return apiResponse;
    }


	/**
	 * saveMapping() provide the functionality to create/update an mapping
	 *
	 * @param mappingView mappingView is accepted as the post request parameter
	 * @return apiResponse
	 */
	 @RequestMapping(method = RequestMethod.POST, value="/mappings/definitions/save")
	 public @ResponseBody ApiResponse<Object> saveMapping(@RequestBody MappingView mappingView) {
		 ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		 boolean flag = Boolean.TRUE;
	     final Mapping mapping = mappingView.getMapping();
	     if (mapping.getMappingId() != null) {
			 flag = Boolean.FALSE;
		 }
	     LOG.debug("**** MappingController.saveMapping() **** Deserialise mapping object: ******* = " + ToStringBuilder.reflectionToString(mapping, ToStringStyle.MULTI_LINE_STYLE));
	     
	     try {
	            mappingService.saveMapping(mapping);;
	        } catch (LucasRuntimeException ex) {
	            LOG.debug("Exception thrown...", ex.getMessage());
	            apiResponse.setExplicitDismissal(Boolean.FALSE);
	            apiResponse.setLevel(Level.ERROR);
	            apiResponse.setCode("2002");
	            apiResponse.setStatus("failure");
	            apiResponse.setData("");
	            apiResponse.setMessage(messageSource.getMessage("2002", new Object[] {mapping.getMappingName()}, LocaleContextHolder.getLocale()));
	            LOG.error(apiResponse.getMessage());
	            return apiResponse;
	       } catch (Exception ex) {
	            LOG.debug("Exception thrown...", ex.getMessage());
	            apiResponse.setExplicitDismissal(Boolean.FALSE);
	            apiResponse.setLevel(Level.ERROR);
	            apiResponse.setCode("1021");
	            apiResponse.setStatus("failure");
	            apiResponse.setData("");
	            apiResponse.setMessage(messageSource.getMessage("1021", new Object[]{mapping.getMappingName()}, LocaleContextHolder.getLocale()));
	            LOG.error(apiResponse.getMessage());
	            ex.printStackTrace();
	            return apiResponse;
	        }

	     apiResponse.setExplicitDismissal(Boolean.FALSE);
		 apiResponse.setLevel(Level.INFO);
	     if (flag) {
				apiResponse.setCode("2001");		
				apiResponse.setMessage(messageSource.getMessage("2001", new Object[] {mapping.getMappingName()}, LocaleContextHolder.getLocale()));
	     } else {
				apiResponse.setCode("2003");		
				apiResponse.setMessage(messageSource.getMessage("2003", new Object[] {mapping.getMappingName()}, LocaleContextHolder.getLocale()));
	     }
			LOG.debug(apiResponse.getMessage());
			return apiResponse;
	 }
	 

	/**
	 * Method to delete mapping
	 * 
	 * @param mappingView
	 *            .mappingId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mappings/delete")
	public @ResponseBody
	ApiResponse<Object> deleteMapping(@RequestBody MappingView mappingView) {
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();

		Boolean isDeleted = null;
		Long mappingId = mappingView.getMappingId();
		LOG.debug("**** MappingController.deleteMapping() **** Deserialise Mapping object: ******* = "
				+ ToStringBuilder.reflectionToString(mappingId,
						ToStringStyle.MULTI_LINE_STYLE));
		try {
			isDeleted = mappingService.deleteMapping(mappingId);
			apiResponse.setStatus("success");
			apiResponse.setCode("2005");
			apiResponse.setLevel(Level.INFO);
			apiResponse.setData(isDeleted);
		} catch (Exception e) {
			apiResponse.setData(false);
			if (e instanceof LucasRuntimeException) {
				apiResponse.setMessage(messageSource.getMessage("2004",
						new Object[] {}, LocaleContextHolder.getLocale()));
				apiResponse.setStatus("failure");
				apiResponse.setCode("2004");
			} else {
				throw new LucasRuntimeException(
						LucasRuntimeException.INTERNAL_ERROR, e);
			}
		}
		return apiResponse;
	}

}
