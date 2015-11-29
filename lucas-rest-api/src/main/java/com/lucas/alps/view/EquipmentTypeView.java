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
package com.lucas.alps.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.EquipmentTypeDetailView;
import com.lucas.alps.viewtype.SelectedQuestionsAndPreferredAnswerView;
import com.lucas.entity.equipment.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Adarsh
 * @Product Lucas.
 * Created By: Adarsh
 * Created On Date: 6/25/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
public class EquipmentTypeView {

    private EquipmentType equipmentType;

    public EquipmentTypeView() {
        equipmentType = new EquipmentType();
    }

    public EquipmentTypeView(EquipmentType equipmentType) {
        if (equipmentType != null) {
            this.equipmentType = equipmentType;
        }
    }

    public Long getEquipmentTypeId() {
        if (equipmentType != null) {
            return equipmentType.getEquipmentTypeId();
        }
        return null;
    }

    public void setEquipmentTypeId(Long equipmentTypeId) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentTypeId(equipmentTypeId);
        }
    }

    @JsonView({EquipmentTypeDetailView.class, SelectedQuestionsAndPreferredAnswerView.class})
    public String getEquipmentTypeName() {
        if (equipmentType != null) {
            return equipmentType.getEquipmentTypeName();
        }
        return null;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentTypeName(equipmentTypeName);
        }
    }

    @JsonView({EquipmentTypeDetailView.class, SelectedQuestionsAndPreferredAnswerView.class})
    public String getEquipmentTypeDescription() {
        if (equipmentType != null) {
            return equipmentType.getEquipmentTypeDescription();
        }
        return null;
    }

    public void setEquipmentTypeDescription(String equipmentTypeDescription) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentTypeDescription(equipmentTypeDescription);
        }
    }

    @JsonView({EquipmentTypeDetailView.class, SelectedQuestionsAndPreferredAnswerView.class})
    public Boolean isRequiresCheckList() {
        if (equipmentType != null) {
            return this.equipmentType.isRequiresCheckList();
        }
        return null;
    }

    public void setRequiresCheckList(Boolean requiresCheckList) {
        if (equipmentType != null) {
            this.equipmentType.setRequiresCheckList(requiresCheckList);
        }
    }

    @JsonView({EquipmentTypeDetailView.class, SelectedQuestionsAndPreferredAnswerView.class})
    public Boolean isRequiresCertification() {
        if (equipmentType != null) {
            return this.equipmentType.isRequiresCertification();
        }
        return null;
    }

    public void setRequiresCertification(Boolean requiresCertification) {
        if (equipmentType != null) {
            this.equipmentType.setRequiresCertification(requiresCertification);
        }
    }

    @JsonView({EquipmentTypeDetailView.class, SelectedQuestionsAndPreferredAnswerView.class})
    public Boolean isShippingContainer() {
        if (equipmentType != null) {
            return this.equipmentType.isShippingContainer();
        }
        return null;
    }

    public void setShippingContainer(Boolean shippingContainer) {
        if (equipmentType != null) {
            this.equipmentType.setShippingContainer(shippingContainer);
        }
    }

    public Long getReinspectTimeInterval() {
        if (equipmentType != null) {
            return this.equipmentType.getReinspectTimeInterval();
        }
        return null;
    }

    public void setReinspectTimeInterval(Long reinspectTimeInterval) {
        if (equipmentType != null) {
            this.equipmentType.setReinspectTimeInterval(reinspectTimeInterval);
        }
    }

    public String getEquipmentTypeStatus() {
        if (equipmentType != null) {
            return this.equipmentType.getEquipmentTypeStatus();
        }
        return null;
    }

    public void setEquipmentTypeStatus(String equipmentTypeStatus) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentTypeStatus(equipmentTypeStatus);
        }
    }

    @JsonView({EquipmentTypeDetailView.class,SelectedQuestionsAndPreferredAnswerView.class})
    public Long getEquipmentCount() {
        if (equipmentType != null) {
            return equipmentType.getEquipmentCount();
        }
        return null;
    }

    public void setEquipmentCount(Long equipmentCount) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentCount(equipmentCount);
        }
    }

    @JsonView({EquipmentTypeDetailView.class})
    public Long getEquipmentTypePositions() {
        if (equipmentType != null) {
            return equipmentType.getEquipmentTypePositions();
        }
        return null;
    }

    public void setEquipmentTypePositions(Long equipmentTypePositions) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentTypePositions(equipmentTypePositions);
        }
    }


    public Long getCreatedByImportId() {
        if (equipmentType != null) {
            return this.equipmentType.getCreatedByImportId();
        }
        return null;
    }

    public void setCreatedByImportId(Long createdByImportId) {
        if (equipmentType != null) {
            this.equipmentType.setCreatedByImportId(createdByImportId);
        }
    }

    public Long getUpdatedByImportId() {
        if (equipmentType != null) {
            return this.equipmentType.getUpdatedByImportId();
        }
        return null;
    }

    public void setUpdatedByImportId(Long updatedByImportId) {
        if (equipmentType != null) {
            this.equipmentType.setUpdatedByImportId(updatedByImportId);
        }
    }

    @JsonView({EquipmentTypeDetailView.class, SelectedQuestionsAndPreferredAnswerView.class})
    public String getEquipmentStyle() {
        if (equipmentType != null && equipmentType.getEquipmentStyle() != null) {
            return this.equipmentType.getEquipmentStyle().getEquipmentStyleCode();
        }
        return null;
    }

    public void setEquipmentStyle(final String equipmentStyle) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentStyle(new EquipmentStyle() {
                {
                    setEquipmentStyleCode(equipmentStyle);
                }
            });
        }
    }

    public String getContainerType() {
        if (equipmentType != null) {
            return this.equipmentType.getContainerType().getContainerCode();
        }
        return null;
    }

    public void setContainerType(final String containerType) {
        if (equipmentType != null) {
            this.equipmentType.setContainerType(new ContainerType(){
                {
                    setContainerCode(containerType);
                }
            });
        }
    }

    @JsonView({EquipmentTypeDetailView.class})
    public List<EquipmentTypePositionView> getEquipmentTypePositionList() {
        if (this.equipmentType != null && this.equipmentType.getEquipmentTypePositionList() != null) {
            final List<EquipmentTypePosition> equipmentTypePositionList = this.equipmentType.getEquipmentTypePositionList();
            final List<EquipmentTypePositionView> equipmentTypePositionViews = new ArrayList<EquipmentTypePositionView>(equipmentTypePositionList.size());
            for (EquipmentTypePosition equipmentTypePosition : equipmentTypePositionList) {
                equipmentTypePositionViews.add(new EquipmentTypePositionView(equipmentTypePosition));
            }
            return equipmentTypePositionViews;
        }
        return null;
    }

    public void setEquipmentTypePosition(EquipmentTypePositionView equipmentTypePositionView) {
        if (equipmentType != null && equipmentTypePositionView != null) {
            this.equipmentType.setEquipmentTypePosition(equipmentTypePositionView.getEquipmentTypePosition());
        }
    }

    public void setEquipmentTypePositionList(List<EquipmentTypePositionView> equipmentTypePositionViews) {
        if (equipmentType != null && equipmentTypePositionViews != null) {
            final List<EquipmentTypePosition> equipmentTypePositionList = new ArrayList<>();
            for (EquipmentTypePositionView equipmentTypePositionView : equipmentTypePositionViews) {
                equipmentTypePositionList.add(equipmentTypePositionView.getEquipmentTypePosition());
            }
            this.equipmentType.setEquipmentTypePositionList(equipmentTypePositionList);
        }
    }


    public List<LucasUserCertifiedEquipmentType> getLucasUserCertifiedEquipmentTypes() {
        if (equipmentType != null) {
            return this.equipmentType.getLucasUserCertifiedEquipmentTypes();
        }
        return null;
    }

    public void setLucasUserCertifiedEquipmentTypes(List<LucasUserCertifiedEquipmentType> lucasUserCertifiedEquipmentTypes) {
        if (equipmentType != null) {
            this.equipmentType.setLucasUserCertifiedEquipmentTypes(lucasUserCertifiedEquipmentTypes);
        }
    }

    public List<Equipment> getEquipments() {
        if (equipmentType != null) {
            return equipmentType.getEquipments();
        }
        return null;
    }

    public void setEquipments(List<Equipment> equipments) {
        if (equipmentType != null) {
            this.equipmentType.setEquipments(equipments);
        }
    }

    public List<EquipmentTypeQuestion> getEquipmentTypeQuestions() {
        if (equipmentType != null) {
            return equipmentType.getEquipmentTypeQuestions();
        }
        return null;
    }

    public void setEquipmentTypeQuestions(List<EquipmentTypeQuestion> equipmentTypeQuestions) {
        if (equipmentType != null) {
            this.equipmentType.setEquipmentTypeQuestions(equipmentTypeQuestions);
        }
    }

    @JsonProperty(value = "selectedQuestion")
    @JsonView(SelectedQuestionsAndPreferredAnswerView.class)
    public List<AnswerEvaluationRuleView> getAnswerEvaluationRules() {
        if (equipmentType != null && equipmentType.getAnswerEvaluationRules() != null) {
            final List<AnswerEvaluationRule> answerEvaluationRules = this.equipmentType.getAnswerEvaluationRules();
            final List<AnswerEvaluationRuleView> answerEvaluationRuleViews = new ArrayList<>();
            for (AnswerEvaluationRule answerEvaluationRule : answerEvaluationRules) {
                answerEvaluationRuleViews.add(new AnswerEvaluationRuleView(answerEvaluationRule));
            }
            return answerEvaluationRuleViews;
        }
        return null;
    }

    public void setAnswerEvaluationRule(AnswerEvaluationRuleView answerEvaluationRuleView) {
        if (equipmentType != null && answerEvaluationRuleView != null ) {
            this.equipmentType.setAnswerEvaluationRule(answerEvaluationRuleView.getAnswerEvaluationRule());
        }
    }

    public void setAnswerEvaluationRules(List<AnswerEvaluationRuleView> answerEvaluationRuleViews) {
        if (equipmentType != null && answerEvaluationRuleViews != null && !answerEvaluationRuleViews.isEmpty()) {
            final List<AnswerEvaluationRule> answerEvaluationRules=new ArrayList<>();
            for(AnswerEvaluationRuleView answerEvaluationRuleView:answerEvaluationRuleViews){
                answerEvaluationRules.add(answerEvaluationRuleView.getAnswerEvaluationRule());
            }
            this.equipmentType.setAnswerEvaluationRules(answerEvaluationRules);
        }
    }

    public EquipmentType getEquipmentType() {
        return  this.equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }
}
