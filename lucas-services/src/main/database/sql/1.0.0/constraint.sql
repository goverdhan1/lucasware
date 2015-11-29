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

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: CANVAS                                                                   
--                                                                           
-- ---------------------------------------------------------------------------
USE lucasdb;

ALTER TABLE canvas
  ADD CONSTRAINT FK_canvas_layout_id 
   FOREIGN KEY (layout_id) 
   REFERENCES canvas_layout (layout_id) 
   ON DELETE CASCADE 
   ON UPDATE CASCADE;
   
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: CANVAS_WIDGETINSTANCELIST                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE canvas_widgetinstancelist
  ADD CONSTRAINT FK_widgetinstance_canvas_id
    FOREIGN KEY (canvas_canvas_id) 
    REFERENCES canvas (canvas_id),
  ADD CONSTRAINT FK_widgetinstance_widget_id
    FOREIGN KEY (widget_id) 
    REFERENCES widget (widget_id);
    
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_SETTING                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_setting
  ADD CONSTRAINT FK_lucas_setting_user_id
    FOREIGN KEY (super_user_id) 
    REFERENCES lucas_user (user_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_SPECIAL_INSTRUCTIONS                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE special_instructions
  ADD CONSTRAINT FK_special_instructions_product_id 
    FOREIGN KEY (product_id)
    REFERENCES product (product_id),
  ADD CONSTRAINT FK_special_instructions_alter_location
    FOREIGN KEY (alert_locations_id)
    REFERENCES alert_locations (alert_locations_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_USER                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_user
  ADD CONSTRAINT FK_user_shift_id
    FOREIGN KEY (shift_id)
    REFERENCES shift (shift_id);

-- ---------------------------------------------------------------------------
--
-- TABLE: OPEN_USER_CANVAS                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE open_user_canvas
  ADD CONSTRAINT FK_open_canvas_user_id
    FOREIGN KEY (user_user_id) 
    REFERENCES lucas_user (user_id),
  ADD CONSTRAINT FK_open_canvas_canvas_id
    FOREIGN KEY (canvas_id)
    REFERENCES canvas (canvas_id);
    
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_USER_PERMISSION_GROUP                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_user_permission_group
  ADD CONSTRAINT FK_user_permission_group_id
    FOREIGN KEY (permission_group_id)
    REFERENCES permission_group (permission_group_id),
  ADD CONSTRAINT FK_user_permission_user_id
    FOREIGN KEY (user_id)
    REFERENCES lucas_user (user_id);


-- ---------------------------------------------------------------------------
--
-- TABLE: PACK_FACTOR_HIERARCHY_COMPONENT
--
-- ---------------------------------------------------------------------------
ALTER TABLE pack_factor_hierarchy_component
  ADD CONSTRAINT FK_pf_hierarchy_id
    FOREIGN KEY (pf_hierarchy_id)
    REFERENCES pack_factor_hierarchy (pf_hierarchy_id),
  ADD CONSTRAINT FK_pf_component_id
    FOREIGN KEY (pf_component_id)
    REFERENCES pack_factor_component (pf_component_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: PERMISSION_GROUP_PERMISSION                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE permission_group_permission
  ADD CONSTRAINT FK_group_permission_group_id
    FOREIGN KEY (permission_group_id)
    REFERENCES permission_group (permission_group_id),
  ADD CONSTRAINT FK_group_permission_id
    FOREIGN KEY (permission_id)
    REFERENCES permission (permission_id);
    
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: PICKLINE                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE pickline
  ADD CONSTRAINT FK_pickline_wave_id
    FOREIGN KEY (wave_id)
    REFERENCES wave (wave_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: UOM                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE uom
  ADD CONSTRAINT FK_uom_parent_uom_id
    FOREIGN KEY (parent_uom_id)
    REFERENCES uom (uom_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: WAREHOUSE                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE warehouse
  ADD CONSTRAINT FK_warehouse_region_id
    FOREIGN KEY (region_id)
    REFERENCES region (region_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: WMS_USER                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE wms_user
  ADD CONSTRAINT FK_wms_user_id
    FOREIGN KEY (user_id)
    REFERENCES lucas_user (user_id);


-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT_HANDLER                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_event_handler
  ADD CONSTRAINT FK_eai_event_handler_event_transport_id
    FOREIGN KEY (event_transport_id)
    REFERENCES eai_transport (transport_id),
  ADD CONSTRAINT FK_eai_event_handler_inbound_mapping_id
    FOREIGN KEY (inbound_mapping_id)
    REFERENCES eai_mapping (mapping_id),
  ADD CONSTRAINT FK_eai_event_handler_outbound_mapping_id
    FOREIGN KEY (outbound_mapping_id)
    REFERENCES eai_mapping (mapping_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT_EVENT_HANDLER                                                               
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_event_event_handler
  ADD CONSTRAINT FK_eai_event_event_handler_event_id
    FOREIGN KEY (event_id)
    REFERENCES eai_event (event_id),
  ADD CONSTRAINT FK_eai_event_event_handler_event_handler_id
    FOREIGN KEY (event_handler_id)
    REFERENCES eai_event_handler (event_handler_id);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_TRANSFORMATION_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_transformation_definition
  ADD CONSTRAINT FK_eai_transformation_definition_transformation_id
    FOREIGN KEY (transformation_id)
    REFERENCES eai_transformation (transformation_id),
  ADD CONSTRAINT FK_eai_message_definition_message_field_id
  	FOREIGN KEY (message_field_id)
  	REFERENCES eai_message_definition (message_field_id);
    
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_FILTER_CONDITION                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_filter_condition
  ADD CONSTRAINT FK_eai_filter_condition_transformation_definition_id
    FOREIGN KEY (transformation_definition_id)
    REFERENCES eai_transformation_definition (transformation_definition_id);
    
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_JOINER_CONDITION                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_joiner_condition
  ADD CONSTRAINT FK_eai_joiner_condition_transformation_definition_id
    FOREIGN KEY (transformation_definition_id)
    REFERENCES eai_transformation_definition (transformation_definition_id);
    
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING_DEFINITION                                             
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_mapping_definition
  ADD CONSTRAINT eai_mapping_definition_mapping_id
    FOREIGN KEY (mapping_id)
    REFERENCES eai_mapping (mapping_id);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING_PATH
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_mapping_path
  ADD CONSTRAINT eai_mapping_path_mapping_id
    FOREIGN KEY (mapping_id)
    REFERENCES eai_mapping (mapping_id),
  ADD CONSTRAINT eai_to_transformation_id
    FOREIGN KEY (to_transformation_id)
    REFERENCES eai_transformation (transformation_id),
  ADD CONSTRAINT eai_from_transformation_id
    FOREIGN KEY (from_transformation_id)
    REFERENCES eai_transformation (transformation_id);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_SEGMENT_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_segment_definition
  ADD CONSTRAINT eai_segment_definition_segment_id
    FOREIGN KEY (segment_id)
    REFERENCES eai_segments (segment_id);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MESSAGE_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE eai_message_definition
  ADD CONSTRAINT eai_message_definition_parent_segment_id
    FOREIGN KEY (parent_segment_id)
    REFERENCES eai_segments (segment_id),
  ADD CONSTRAINT eai_message_definition_message_id
    FOREIGN KEY (message_id)
    REFERENCES eai_message (message_id);


-- ===========================================================================
-- ---------------- EQUIPMENT MODULE RELATED TABLES CONSTRAINTS---------------
-- ===========================================================================

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------


 ALTER TABLE `equipment_type_position`
  ADD CONSTRAINT `FK_equipment_type_id`
   FOREIGN KEY (`equipment_type_id`)
   REFERENCES `equipment_type`(`equipment_type_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

 ALTER TABLE `question`
    ADD CONSTRAINT `FK_question_type_id`
    FOREIGN KEY (`question_type_id`)
    REFERENCES `question_type`(`question_type_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
 ,ADD CONSTRAINT `FK_answer_type_id`
   FOREIGN KEY (`answer_type_id`)
   REFERENCES `answer_type`(`answer_type_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_position`
   ADD CONSTRAINT `FK_equipment_id`
   FOREIGN KEY (`equipment_id`)
   REFERENCES `equipment` (`equipment_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: equipment
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment`
   ADD CONSTRAINT `FK_equipment_equipment_type_id`
	   FOREIGN KEY (`equipment_type_id`)
	   REFERENCES `equipment_type` (`equipment_type_id`)
	   ON DELETE CASCADE
	   ON UPDATE CASCADE
 ,ADD CONSTRAINT `FK_current_user_id`
	  FOREIGN KEY (`current_user_id`)
	  REFERENCES `lucas_user`(`user_id`)
	  ON DELETE CASCADE
	  ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type`
   ADD CONSTRAINT `FK_equipment_type_equipment_style_id`
	   FOREIGN KEY (`equipment_style_id`)
	   REFERENCES `equipment_style`(`equipment_style_id`)
	   ON DELETE CASCADE
	   ON UPDATE CASCADE
  ,ADD CONSTRAINT `FK_equipment_type_container_type_default`
	   FOREIGN KEY (`container_type_default`)
	   REFERENCES `container_type`(`container_code`)
	   ON DELETE CASCADE
	   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type_question`
   ADD CONSTRAINT `FK_equipment_type_question_equipment_type_id`
	   FOREIGN KEY (`equipment_type_id`)
	   REFERENCES `equipment_type` (`equipment_type_id`)
	   ON DELETE CASCADE
	   ON UPDATE CASCADE
  ,ADD CONSTRAINT `FK_equipment_type_question_question_id`
	   FOREIGN KEY (`question_id`)
	   REFERENCES `question` (`question_id`)
	   ON DELETE CASCADE
	   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: answer_evaluation_rule
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `answer_evaluation_rule`
   ADD CONSTRAINT `FK_answer_evaluation_rule_equipment_type_id`
   FOREIGN KEY (`equipment_type_id`)
   REFERENCES `equipment_type` (`equipment_type_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE
 ,ADD CONSTRAINT `FK_answer_evaluation_rule_question_id`
   FOREIGN KEY (`question_id`)
   REFERENCES `question` (`question_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: lucas_user_certified_equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `lucas_user_certified_equipment_type`
   ADD CONSTRAINT `FK_lucas_user_certified_equipment_type_equipment_type_id`
   FOREIGN KEY (`equipment_type_id`)
   REFERENCES `equipment_type` (`equipment_type_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE
 ,ADD CONSTRAINT `FK_lucas_user_id`
   FOREIGN KEY (`user_id`)
   REFERENCES `lucas_user`(`user_id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_style
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_style`
	ADD CONSTRAINT `unique_equipment_style_code`
	UNIQUE (`equipment_style_code`)
 ,ADD CONSTRAINT `unique_equipment_style_name`
	UNIQUE (`equipment_style_name`);

-- ---------------------------------------------------------------------------
--       TABLE: question_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `question_type`
	ADD CONSTRAINT `unique_question_type_name`
	UNIQUE (`question_type_name`);

-- ---------------------------------------------------------------------------
--       TABLE: unique_answer_type_name
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `answer_type`
	ADD CONSTRAINT `unique_answer_type_name`
	UNIQUE (`answer_type_name`);

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment_type`
	ADD CONSTRAINT `unique_equipment_type_name`
	UNIQUE (`equipment_type_name`);

-- ---------------------------------------------------------------------------
--       TABLE: equipment
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

ALTER TABLE `equipment`
	ADD CONSTRAINT `unique_equipment_name`
	UNIQUE (`equipment_name`);

-- ====================================================================================

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_DEVICE
--                                                                                             
-- ---------------------------------------------------------------------------

    ALTER TABLE `lucasdb`.`lucas_device` 
  ADD CONSTRAINT `FK_building_Id`
  FOREIGN KEY (`building_Id` )
  REFERENCES `lucasdb`.`lucas_building` (`building_Id` );
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_MOBILE_APP_MESSAGE_OPTION
--                                                                                             
-- ---------------------------------------------------------------------------

ALTER TABLE `lucasdb`.`lucas_mobile_app_message_option` 
  ADD CONSTRAINT `FK_msg_building_Id`
  FOREIGN KEY (`building_id` )
  REFERENCES `lucasdb`.`lucas_building` (`building_Id` );

  -- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_MAPPING                                                            
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_mapping
  ADD CONSTRAINT FK_eai_source_message_id
    FOREIGN KEY (source_message_id)
    REFERENCES eai_message (message_id),
  ADD CONSTRAINT FK_eai_destination_message_id
    FOREIGN KEY (destination_message_id)
    REFERENCES eai_message (message_id);