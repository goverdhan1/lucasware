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
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.EquipmentSearchByColumnsView;
import com.lucas.alps.view.EquipmentView;
import com.lucas.alps.viewtype.EquipmentDetailView;
import com.lucas.alps.viewtype.EquipmentSearchByColumnsDetailsView;
import com.lucas.entity.equipment.Equipment;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.equipment.EquipmentService;
import com.lucas.services.search.SearchAndSortCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 7/8/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for Equipment.
 */
@Controller
@RequestMapping(value = "/equipments")
public class EquipmentController {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentController.class);

    private final static String equipmentName = "equipmentName";
    private final static String userName = "userName";
    private EquipmentService equipmentService;
    private MessageSource messageSource;


    @Inject
    public EquipmentController(EquipmentService equipmentTypeService, MessageSource messageSource) {
        this.equipmentService = equipmentTypeService;
        this.messageSource = messageSource;

    }

    /**
     * getEquipment() provide the functionality for fetching the Equipment
     *
     * @param equipmentName is accepted as the get request param
     * @return Equipment and its dependent entities warped in EquipmentView
     */
    @ResponseView(EquipmentDetailView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/equipment/{equipment-name}")
    public
    @ResponseBody
    ApiResponse<EquipmentView> getEquipment(@PathVariable("equipment-name") String equipmentName) {
        final ApiResponse<EquipmentView> apiResponse = new ApiResponse<EquipmentView>();
        try {
            final Equipment equipment = this.equipmentService.findByEquipmentName(equipmentName, true);
            if (equipment == null) {
                throw new LucasRuntimeException("Equipment is not Found for " + equipmentName);
            }
            final EquipmentView equipmentView = new EquipmentView(equipment);
            apiResponse.setData(equipmentView);
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1071"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1071");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }


    /**
     * saveEquipment() provide the functionality for persisting the Equipment into the db
     * it uses  EquipmentService.saveEquipment() internally
     *
     * @param equipmentView is accepted by this rest end point
     * @return the status of the operation which is wrapped into the ApiResponse
     * @throws LucasRuntimeException
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/equipment/save")
    public
    @ResponseBody
    ApiResponse<Object> saveEquipment(@RequestBody EquipmentView equipmentView) throws LucasRuntimeException, Exception {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        try {
            if (equipmentView != null && equipmentView.getEquipmentType() != null) {
                final Equipment equipment = equipmentView.getEquipment();
                final boolean result = this.equipmentService.saveEquipment(equipment);
                if (result) {
                    apiResponse.setMessage(messageSource.getMessage("1072"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("success");
                    apiResponse.setCode("1072");
                    apiResponse.setData(result);
                } else {
                    apiResponse.setMessage(messageSource.getMessage("1073"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("failure");
                    apiResponse.setCode("1073");
                    apiResponse.setData(result);
                }
            } else {
                throw new LucasRuntimeException("Equipment Type Information is Null ");
            }
        } catch (Exception e) {
            apiResponse.setData(false);
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1073"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1073");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }


    /**
     * deleteEquipment() provide the functionality for deletion of the Equipment
     * based on the equipment name which is warp into the Map instance in key value pair.
     *
     * @param deletionInput is the instance of the Map in key value pair input wil be provided.
     * @return the status of the operation which is wrapped into the ApiResponse
     */
    @RequestMapping(method = RequestMethod.POST, value = "/equipment/delete")
    public
    @ResponseBody
    ApiResponse<Object> deleteEquipment(@RequestBody Map<String, String> deletionInput) {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        try {
            if (deletionInput != null && !deletionInput.isEmpty()) {
                final String equipmentNameValue = deletionInput.get(equipmentName);
                final boolean result = this.equipmentService.deleteEquipmentByName(equipmentNameValue);
                if (result) {
                    apiResponse.setData(result);
                    apiResponse.setMessage(messageSource.getMessage("1074"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("success");
                    apiResponse.setCode("1074");
                } else {
                    throw new LucasRuntimeException("Equipment Not Deleted");
                }
            } else {
                apiResponse.setData(Boolean.FALSE);
                apiResponse.setMessage(messageSource.getMessage("1076"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1076");
            }
        } catch (Exception e) {
            if (e.getMessage().contains("Equipment In Use")) {
                apiResponse.setData(Boolean.FALSE);
                apiResponse.setMessage(messageSource.getMessage("1077"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1077");
            } else {
                apiResponse.setData(Boolean.FALSE);
                apiResponse.setMessage(messageSource.getMessage("1075"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1075");
            }
        }
        return apiResponse;
    }


    /**
     * unAssignUserFromEquipment() provide the functionality for un-assign user
     * form the equipment it uses EquipmentService.unAssignUserFromEquipment() intern
     * @param inputData as the instance of java.util.Map is accepted as the input param
     *                  which contains userName and equipmentName key value pair.
     * @return the status of the operation which is wrapped into the ApiResponse.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/equipment/unassign")
    public
    @ResponseBody
    ApiResponse<Object> unAssignUserFromEquipment(@RequestBody Map<String, String> inputData) {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        try {
            if (inputData != null && !inputData.isEmpty()) {
                final String equipmentNameValue = inputData.get(equipmentName);
                final String userNameValue = inputData.get(userName);
                final boolean result = this.equipmentService.unAssignUserFromEquipment(equipmentNameValue,userNameValue);
                if (result) {
                    apiResponse.setData(result);
                    apiResponse.setMessage(messageSource.getMessage("1078"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("success");
                    apiResponse.setCode("1078");
                } else {
                    throw new LucasRuntimeException("Un Assign Equipment is Failed");
                }
            }else {
                apiResponse.setData(Boolean.FALSE);
                apiResponse.setMessage(messageSource.getMessage("1080"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1080");
            }
        }catch (Exception e){
                apiResponse.setData(Boolean.FALSE);
                apiResponse.setMessage(messageSource.getMessage("1079"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1079");
        }
       return apiResponse;
     }

    /**
     * getEquipmentListBySearchAndSortCriteria() provide the functionality for getting the Equipment from db
     * based on the SearchAndSortCriteria passed from client.
     * @param searchAndSortCriteria is accepted by this rest end point
     * @return EquipmentSearchByColumnsView is return from this rest end point wrapped into the ApiResponse
     */
    @ResponseView(EquipmentSearchByColumnsDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/equipment-manager-list/search")
    public
    @ResponseBody
    ApiResponse<EquipmentSearchByColumnsView> getEquipmentListBySearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {
        final ApiResponse<EquipmentSearchByColumnsView> apiResponse = new ApiResponse<EquipmentSearchByColumnsView>();
        try {
            if(searchAndSortCriteria !=null && (!searchAndSortCriteria.getSearchMap().isEmpty() || !searchAndSortCriteria.getSortMap().isEmpty())){
                final List<Equipment> equipmentList = this.equipmentService.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
                final Long totalRecords = this.equipmentService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
                final EquipmentSearchByColumnsView equipmentSearchByColumnsView = new EquipmentSearchByColumnsView(equipmentList, totalRecords);
                apiResponse.setData(equipmentSearchByColumnsView);
                apiResponse.setMessage(messageSource.getMessage("1085"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("success");
                apiResponse.setCode("1085");
            }else{
                apiResponse.setData(null);
                apiResponse.setMessage(messageSource.getMessage("1087"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1087");
            }
        } catch (Exception e) {
            apiResponse.setData(null);
            apiResponse.setMessage(messageSource.getMessage("1086"
                    , new Object[]{}
                    , LocaleContextHolder.getLocale()));
            apiResponse.setStatus("failure");
            apiResponse.setCode("1086");
        }
        return apiResponse;
    }

}
