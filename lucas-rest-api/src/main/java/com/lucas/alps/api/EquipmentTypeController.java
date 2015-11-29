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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.alps.support.view.ResponseView;
import com.lucas.alps.view.EquipmentTypeSearchByColumnsView;
import com.lucas.alps.view.EquipmentTypeView;
import com.lucas.alps.view.QuestionView;
import com.lucas.alps.viewtype.EquipmentTypeDetailView;
import com.lucas.alps.viewtype.EquipmentTypeSearchByColumnsDetailsView;
import com.lucas.alps.viewtype.QuestionDetailView;
import com.lucas.alps.viewtype.SelectedQuestionsAndPreferredAnswerView;
import com.lucas.entity.equipment.EquipmentStyle;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.equipment.Question;
import com.lucas.exception.Level;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.equipment.EquipmentTypeService;
import com.lucas.services.equipment.QuestionService;
import com.lucas.services.search.SearchAndSortCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.lucas.exception.Level;

/**
 * @Author Adarsh
 * Updated By: Adarsh
 * Created On Date: 6/22/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality for exposing
 * the rest end points for Equipment Types.
 */
@Controller
@RequestMapping(value = "/equipments")
public class EquipmentTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(EquipmentTypeController.class);


    private static final String EQUIPMENTTYPE = "equipmentType";
    private static final String EQUIPMENT_CHECK = "EquipCheck";
    private static final String EQUIPMENT_STYLE = "equipmentStyle";

    private EquipmentTypeService equipmentTypeService;
    private MessageSource messageSource;
    private QuestionService questionService;

    @Inject
    public EquipmentTypeController(EquipmentTypeService equipmentTypeService, QuestionService questionService, MessageSource messageSource) {
        this.equipmentTypeService = equipmentTypeService;
        this.messageSource = messageSource;
        this.questionService = questionService;
        this.messageSource = messageSource;
    }


    @ResponseView(EquipmentTypeSearchByColumnsDetailsView.class)
    @RequestMapping(method = RequestMethod.POST, value = "/equipment-type-list/search")
    public
    @ResponseBody
    ApiResponse<EquipmentTypeSearchByColumnsView> getEquipmentTypeListBySearchAndSortCriteria(@RequestBody SearchAndSortCriteria searchAndSortCriteria) {
        final ApiResponse<EquipmentTypeSearchByColumnsView> apiResponse = new ApiResponse<EquipmentTypeSearchByColumnsView>();
        try {
            if (searchAndSortCriteria != null &&  (searchAndSortCriteria.getSearchMap() !=null || searchAndSortCriteria.getSortMap() !=null )
                    && (!searchAndSortCriteria.getSearchMap().isEmpty() || !searchAndSortCriteria.getSortMap().isEmpty())) {
                final List<EquipmentType> equipmentTypeList = this.equipmentTypeService.getEquipmentTypeListBySearchAndSortCriteria(searchAndSortCriteria);
                final Long totalRecords = this.equipmentTypeService.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
                final EquipmentTypeSearchByColumnsView equipmentTypeSearchByColumnsView = new EquipmentTypeSearchByColumnsView(equipmentTypeList, totalRecords);
                apiResponse.setData(equipmentTypeSearchByColumnsView);
                apiResponse.setMessage(messageSource.getMessage("1094"
                                                , new Object[]{}
                                                , LocaleContextHolder.getLocale()));
                                apiResponse.setStatus("success");
                                apiResponse.setCode("1094");
            } else {
                apiResponse.setData(null);
                                apiResponse.setMessage(messageSource.getMessage("1096"
                                                , new Object[]{}
                                                , LocaleContextHolder.getLocale()));
                                apiResponse.setStatus("failure");
                                apiResponse.setCode("1096");
            }

        } catch (Exception e) {
            apiResponse.setData(null);
                        apiResponse.setMessage(messageSource.getMessage("1095"
                                        , new Object[]{}
                                        , LocaleContextHolder.getLocale()));
                        apiResponse.setStatus("failure");
                        apiResponse.setCode("1095");
        }
        return apiResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/equipment-types")
    public
    @ResponseBody
    ApiResponse<Map<String, List<String>>> getEquipmentTypeList() {
        final ApiResponse<Map<String, List<String>>> apiResponse = new ApiResponse<Map<String, List<String>>>();

        try {
            final List<String> equipmentTypeList = this.equipmentTypeService.getEquipmentTypeList();
            apiResponse.setData(new HashMap<String, List<String>>() {
                {
                    put(EQUIPMENTTYPE, equipmentTypeList);
                }
            });
        } catch (Exception e) {
            apiResponse.setMessage(messageSource.getMessage("1049"
                    , new Object[]{}
                    , LocaleContextHolder.getLocale()));
        }

        apiResponse.setExplicitDismissal(Boolean.FALSE);
        apiResponse.setLevel(Level.INFO);
        apiResponse.setCode("200");
        return apiResponse;
    }


    /**
     * getEquipmentStyleList() provide the functionality for rendering the list of
     * Equipment-Style based.
     *
     * @return list of the EquipmentStyle
     */
    @RequestMapping(method = RequestMethod.GET, value = "/equipment-styles-names")
    public
    @ResponseBody
    ApiResponse<Map<String, List<String>>> getEquipmentStyleList() {
        final ApiResponse<Map<String, List<String>>> apiResponse = new ApiResponse<Map<String, List<String>>>();
        try {
            final List<String> equipmentStyleNameList = this.equipmentTypeService.getAllEquipmentStyleNames();
            apiResponse.setData(new HashMap() {
                {
                    put(EQUIPMENT_STYLE, equipmentStyleNameList);
                }
            });
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        return apiResponse;
    }


    /**
     * getCheckListQuestionsAndAnswerType() provide the functionality for fetching the
     * check list question and its answer type for the equipment check.
     *
     * @return
     */
    @ResponseView(QuestionDetailView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/equipment-type-checklist-questions")
    public
    @ResponseBody
    ApiResponse<List<QuestionView>> getCheckListQuestionsAndAnswerType() {
        final ApiResponse<List<QuestionView>> apiResponse = new ApiResponse<List<QuestionView>>();
        final List<QuestionView> questionViewList = new ArrayList<>();
        try {
            final List<Question> questionList = this.questionService.getQuestionByQuestionTypeName(EQUIPMENT_CHECK);
            for (Question question : questionList) {
                questionViewList.add(new QuestionView(question));
            }
            apiResponse.setData(questionViewList);
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        return apiResponse;
    }

    /**
     * deleteEquipmentByType() provide the functionality to soft delete Equipment by Type.
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/equipment-type/delete")
    public
    @ResponseBody
    ApiResponse<Map<String, String>> deleteEquipmentByType(@RequestBody Map<String, String> equipmentTypeName) {
        final ApiResponse<Map<String, String>> apiResponse = new ApiResponse<Map<String, String>>();
        Map<String, String> resultMap = new HashMap<String, String>();
        String equipTypeName = equipmentTypeName.get("type");
        try {
            resultMap = this.equipmentTypeService.deleteEquipmentByType(equipTypeName);
            for (String value : resultMap.values()) {
                // using ArrayList#contains
                if (value.contains("EQUIPMENT_EXISTS_CODE")) {
                    apiResponse.setData(new HashMap<String, String>() {
                    });
                    apiResponse.setCode("1054");
                    apiResponse.setStatus("failure");
                    apiResponse.setMessage(messageSource.getMessage("1054", new Object[]{}, LocaleContextHolder.getLocale()));
                } else if (value.contains("CERTIFIED_USER_EXISTS_CODE")) {
                    apiResponse.setData(new HashMap<String, String>() {
                    });
                    apiResponse.setCode("1055");
                    apiResponse.setStatus("failure");
                    apiResponse.setMessage(messageSource.getMessage("1055", new Object[]{}, LocaleContextHolder.getLocale()));
                } else if (value.contains("EQUIPMENT_SOFT_DELETE_CODE")) {
                    apiResponse.setData(new HashMap<String, String>() {
                    });
                    apiResponse.setCode("1040");
                    apiResponse.setMessage(messageSource.getMessage("1040", new Object[]{}, LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("success");
                } else if (value.contains("EQUIPMENT_TYPE_NOT_FOUND")) {
                    apiResponse.setData(new HashMap<String, String>() {
                    });
                    apiResponse.setCode("1056");
                    apiResponse.setMessage(messageSource.getMessage("1056", new Object[]{}, LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("failure");
                } else if (value.contains("EQUIPMENT_TYPE_NAME_EMPTY")) {
                    apiResponse.setData(new HashMap<String, String>() {
                    });
                    apiResponse.setCode("1057");
                    apiResponse.setMessage(messageSource.getMessage("1057", new Object[]{}, LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("failure");
                }
            }
        } catch (Exception e) {
            throw new LucasRuntimeException(
                    LucasRuntimeException.INTERNAL_ERROR, e);
        }
        return apiResponse;
    }


    /**
     * getSelectedQuestionsAndPreferredAnswer() provide the functionality for getting the list of
     * selected question and preferred answer for equipment type
     *
     * @param equipmentTypeName is accepted as the get request param
     * @return the EquipmentTypeView containing EquipmentType.
     */
    @ResponseView(SelectedQuestionsAndPreferredAnswerView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/equipment-type-checklist-questions/{equipment-type}")
    public
    @ResponseBody
    ApiResponse<EquipmentTypeView> getSelectedQuestionsAndPreferredAnswer(@PathVariable("equipment-type") String equipmentTypeName) {
        final ApiResponse<EquipmentTypeView> apiResponse = new ApiResponse<EquipmentTypeView>();
        try {
            final EquipmentType equipmentType = this.equipmentTypeService.findByEquipmentTypeName(equipmentTypeName, true);
            if (equipmentType == null) {
                throw new LucasRuntimeException("Equipment Type is not Found for " + equipmentTypeName);
            }
            final EquipmentTypeView equipmentTypeView = new EquipmentTypeView(equipmentType);
            apiResponse.setData(equipmentTypeView);
        } catch (Exception e) {
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1056"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1056");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }


    /**
     * getEquipmentType() provide the functionality for fetching the EquipmentType
     *
     * @param equipmentTypeName is accepted as the get request param
     * @return EquipmentType and its dependent entities warped in EquipmentTypeView
     */
    @ResponseView(EquipmentTypeDetailView.class)
    @RequestMapping(method = RequestMethod.GET, value = "/equipment-type/{equipment-type-name}")
    public
    @ResponseBody
    ApiResponse<EquipmentTypeView> getEquipmentType(@PathVariable("equipment-type-name") String equipmentTypeName) {
        final ApiResponse<EquipmentTypeView> apiResponse = new ApiResponse<EquipmentTypeView>();
        try {
            final EquipmentType equipmentType = this.equipmentTypeService.findByEquipmentTypeName(equipmentTypeName, true);
            if (equipmentType == null) {
                throw new LucasRuntimeException("Equipment Type is not Found for " + equipmentTypeName);
            }
            final EquipmentTypeView equipmentTypeView = new EquipmentTypeView(equipmentType);
            apiResponse.setData(equipmentTypeView);
        } catch (Exception e) {
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1056"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1056");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }

    /**
     * saveEquipmentType() provide the functionality for persisting the EquipmentType into the db
     * it uses  EquipmentTypeService.saveEquipmentType() internally
     *
     * @param equipmentTypeView is accepted by this rest end point
     * @return the status of the operation which is wrapped into the ApiResponse
     * @throws LucasRuntimeException
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/equipment-type/save")
    public
    @ResponseBody
    ApiResponse<Object> saveEquipmentType(@RequestBody EquipmentTypeView equipmentTypeView) throws LucasRuntimeException, Exception {
        final ApiResponse<Object> apiResponse = new ApiResponse<Object>();
        try {
            if (equipmentTypeView != null && equipmentTypeView.getEquipmentType() != null) {
                final EquipmentType equipmentType = equipmentTypeView.getEquipmentType();
                final boolean result = this.equipmentTypeService.saveEquipmentType(equipmentType);
                if (result) {
                    apiResponse.setMessage(messageSource.getMessage("1058"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("success");
                    apiResponse.setCode("1058");
                    apiResponse.setData(result);
                } else {
                    apiResponse.setMessage(messageSource.getMessage("1059"
                            , new Object[]{}
                            , LocaleContextHolder.getLocale()));
                    apiResponse.setStatus("failure");
                    apiResponse.setCode("1059");
                    apiResponse.setData(result);
                }
            } else {
                throw new LucasRuntimeException("Equipment Type Information is Null ");
            }
        } catch (Exception e) {
            apiResponse.setData(false);
            if (e instanceof LucasRuntimeException) {
                apiResponse.setMessage(messageSource.getMessage("1056"
                        , new Object[]{}
                        , LocaleContextHolder.getLocale()));
                apiResponse.setStatus("failure");
                apiResponse.setCode("1056");
            } else {
                throw new LucasRuntimeException(
                        LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }
        return apiResponse;
    }

}
