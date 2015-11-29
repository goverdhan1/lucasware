-- ---------------------------------------------------------------------------
--
-- Lucas Systems Inc                                                         
-- 11279 Perry Highway                                                       
-- Wexford                                                                   
-- PA 15090                                                                  
-- United States                                                             
--                                                                           
--                                                                           
-- The information in this file contains trade secrets and confidential      
-- information which is the property of Lucas Systems Inc.                   
--                                                                           
-- All trademarks, trade names, copyrights and other intellectual property   
-- rights created, developed, embodied in or arising in connection with      
-- this software shall remain the sole property of Lucas Systems Inc.        
--                                                                           
-- Copyright (c) Lucas Systems, 2015                                         
-- ALL RIGHTS RESERVED       
--                                                
-- ---------------------------------------------------------------------------

--
-- DROP FOREIGN KEYS
--
ALTER TABLE canvas 
  DROP FOREIGN KEY FK_canvas_layout_id;

ALTER TABLE canvas_widgetinstancelist 
  DROP FOREIGN KEY FK_widgetinstance_canvas_id,
  DROP FOREIGN KEY FK_widgetinstance_widget_id;

ALTER TABLE lucas_setting
  DROP FOREIGN KEY FK_lucas_setting_user_id;
  
ALTER TABLE lucas_special_instructions
  DROP FOREIGN KEY FK_special_instructions_product_id,
  DROP FOREIGN KEY FK_special_instructions_alter_location;
  
ALTER TABLE lucas_user
  DROP FOREIGN KEY FK_user_shift_id;

ALTER TABLE open_user_canvas
  DROP FOREIGN KEY FK_open_canvas_user_id,
  DROP FOREIGN KEY FK_open_canvas_canvas_id;  
  
ALTER TABLE lucas_user_permission_group
  DROP FOREIGN KEY FK_user_permission_group_id,
  DROP FOREIGN KEY FK_user_permission_user_id;  
  
ALTER TABLE permission_group_permission
  DROP FOREIGN KEY FK_group_permission_group_id,
  DROP FOREIGN KEY FK_group_permission_id;  
  
ALTER TABLE pickline
  DROP FOREIGN KEY FK_pickline_wave_id;

ALTER TABLE uom
  DROP FOREIGN KEY FK_uom_parent_uom_id; 
  
ALTER TABLE warehouse
  DROP FOREIGN KEY FK_warehouse_region_id;  
  
ALTER TABLE wms_user
  DROP FOREIGN KEY FK_wms_user_id;

ALTER TABLE pack_factor_hierarchy_component
  DROP FOREIGN KEY FK_pf_hierarchy_id,
  DROP FOREIGN KEY  FK_pf_component_id;
  

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT_HANDLER                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_event_handler
  DROP FOREIGN KEY FK_eai_event_handler_event_transport_id,
  DROP FOREIGN KEY FK_eai_event_handler_inbound_mapping_id,
  DROP FOREIGN KEY FK_eai_event_handler_outbound_mapping_id;

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT_EVENT_HANDLER                                                               
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_event_event_handler
  DROP FOREIGN KEY FK_eai_event_event_handler_event_id,
  DROP FOREIGN KEY FK_eai_event_event_handler_event_handler_id;

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_TRANSFORMATION_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_transformation_definition
  DROP FOREIGN KEY FK_eai_transformation_definition_transformation_id,
  DROP FORIEGN KEY FK_eai_message_definition_message_field_id;
    
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_FILTER_CONDITION                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_filter_condition
  DROP FOREIGN KEY FK_eai_filter_condition_transformation_definition_id;
    
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_JOINER_CONDITION                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_joiner_condition
  DROP FOREIGN KEY FK_eai_joiner_condition_transformation_definition_id;
    
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING_DEFINITION                                             
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_mapping_definition
  DROP FOREIGN KEY eai_mapping_definition_mapping_id;

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING_PATH
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_mapping_path
  DROP FOREIGN KEY eai_mapping_path_mapping_id;

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_SEGMENT_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_segment_definition
  DROP FOREIGN KEY eai_segment_definition_segment_id;

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MESSAGE_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_message_definition
  DROP FOREIGN KEY eai_message_definition_parent_segment_id,
  DROP FOREIGN KEY eai_message_definition_message_id;


-- ===========================================================================
-- ---------- EQUIPMENT MODULE RELATED TABLES CONSTRAINTS ROLLBACK------------
-- ===========================================================================


-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type_position`
   DROP FOREIGN KEY `FK_equipment_type_id`;

-- ---------------------------------------------------------------------------
--       TABLE: question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `question`
   DROP FOREIGN KEY `FK_question_type_id`
  ,DROP FOREIGN KEY `FK_answer_type_id` ;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_position`
  DROP FOREIGN KEY `FK_equipment_id`;

-- ---------------------------------------------------------------------------
--       TABLE: equipment
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment`
   DROP FOREIGN KEY `FK_equipment_equipment_type_id`
  ,DROP FOREIGN KEY `FK_current_user_id`;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type`
   DROP FOREIGN KEY `FK_equipment_type_equipment_style_id`
  ,DROP FOREIGN KEY `FK_equipment_type_container_type_default`;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type_question`
   DROP FOREIGN KEY `FK_equipment_type_question_equipment_type_id`
  ,DROP FOREIGN KEY `FK_equipment_type_question_question_id`;

-- ---------------------------------------------------------------------------
--       TABLE: answer_evaluation_rule
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `answer_evaluation_rule`
  DROP FOREIGN KEY `FK_answer_evaluation_rule_equipment_type_id`
 ,DROP FOREIGN KEY `FK_answer_evaluation_rule_question_id`;

-- ---------------------------------------------------------------------------
--       TABLE: lucas_user_certified_equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `lucas_user_certified_equipment_type`
  DROP FOREIGN KEY `FK_lucas_user_certified_equipment_type_equipment_type_id`
 ,DROP FOREIGN KEY `FK_lucas_user_id`;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_style
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_style`
 DROP INDEX `unique_equipment_style_code`;

 ALTER TABLE `equipment_style`
 DROP INDEX `unique_equipment_style_name`;

-- ---------------------------------------------------------------------------
--       TABLE: question_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `question_type`
 DROP INDEX `unique_question_type_name`;

-- ---------------------------------------------------------------------------
--       TABLE: answer_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `answer_type`
 DROP INDEX `unique_answer_type_name`;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type`
 DROP INDEX `unique_equipment_type_name`;

-- ---------------------------------------------------------------------------
--       TABLE: equipment
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment`
 DROP INDEX `unique_equipment_name`;
-- ============================================================================


-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_DEVICE
--       DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE lucas_device
  DROP FOREIGN KEY FK_building_Id;
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_MOBILE_APP_MESSAGE_OPTION
--       DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE lucas_mobile_app_message_option
  DROP FOREIGN KEY FK_msg_building_Id;
