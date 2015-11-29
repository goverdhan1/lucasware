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
-- ---------------------------------------------------------------------------
USE lucasdb;

ALTER TABLE canvas 
  ADD CONSTRAINT canvas_name_IDX UNIQUE (name),
  ADD INDEX layout_id_IDX (layout_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: CANVAS_LAYOUT                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE canvas_layout 
  ADD CONSTRAINT display_order_IDX UNIQUE (display_order);
  
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: CANVAS_WIDGETINSTANCELIST                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE canvas_widgetinstancelist
  ADD INDEX canvas_id_IDX (canvas_canvas_id),
  ADD INDEX widget_id_IDX (widget_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_CUSTOMER_CONFIGURATION                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_customer_configuration
  ADD CONSTRAINT client_id_IDX UNIQUE (client_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_SETTING                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_setting
  ADD INDEX super_user_id_IDX (super_user_id);
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_USER                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_user
  ADD CONSTRAINT username_IDX UNIQUE (username),
  ADD INDEX shift_id_IDX (shift_id);


-- ---------------------------------------------------------------------------
--
-- TABLE: OPEN_USER_CANVAS                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE open_user_canvas
  ADD INDEX canvas_id_IDX (canvas_id),
  ADD INDEX user_id_IDX (user_user_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_USER_PERMISSION_GROUP                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE lucas_user_permission_group
  ADD INDEX permission_group_id_IDX (permission_group_id);

-- ---------------------------------------------------------------------------
--
-- TABLE: PACK_FACTOR_HIERARCHY
--
-- ---------------------------------------------------------------------------
ALTER TABLE pack_factor_hierarchy
  ADD CONSTRAINT pf_hierarchy_name_IDX UNIQUE (pf_hierarchy_name);

-- ---------------------------------------------------------------------------
--
-- TABLE: PACK_FACTOR_COMPONENT
--
-- ---------------------------------------------------------------------------
ALTER TABLE pack_factor_component
  ADD CONSTRAINT pf_component_name_IDX UNIQUE (pf_component_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: PERMISSION                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE permission
  ADD CONSTRAINT permission_name_IDX UNIQUE (permission_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: PERMISSION_GROUP                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE permission_group
  ADD CONSTRAINT permission_group_name_IDX UNIQUE (permission_group_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: PICKLINE                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE pickline
  ADD INDEX wave_id_IDX (wave_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: SHIFT                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE shift
  ADD CONSTRAINT shift_name_IDX UNIQUE (shift_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: SUPPORTED_LANGUAGE                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE supported_language
  ADD CONSTRAINT language_code_IDX UNIQUE (language_code);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: UOM                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE uom
  ADD CONSTRAINT uom_name_IDX UNIQUE (uom_name),
  ADD INDEX parent_uom_id_IDX (parent_uom_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: WAREHOUSE                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE warehouse
  ADD CONSTRAINT warehouse_id_IDX UNIQUE (warehouse_id),
  ADD INDEX region_id_IDX (region_id);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: WAVE                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE wave
  ADD CONSTRAINT wave_name_IDX UNIQUE (wave_name);
  
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: WIDGET                                                               
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE widget
  ADD CONSTRAINT name_IDX UNIQUE (name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_event
  ADD CONSTRAINT event_name_IDX UNIQUE (event_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT_HANDLER
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_event_handler
  ADD CONSTRAINT event_handler_name_IDX UNIQUE (event_handler_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_TRANSPORT
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_transport
  ADD CONSTRAINT transport_name_IDX UNIQUE (transport_name);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_MAPPING
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_mapping
  ADD CONSTRAINT mapping_name_IDX UNIQUE (mapping_name);  
  
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_MESSAGE
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_message
  ADD CONSTRAINT message_name_IDX UNIQUE (message_name),
  ADD INDEX message_format_IDX (message_format);    

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_TRANSFORMATION
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_transformation
  ADD CONSTRAINT transformation_name_IDX UNIQUE (transformation_name);
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_BUILDING
--                                                                           
-- ---------------------------------------------------------------------------

ALTER TABLE `lucasdb`.`lucas_building` 
ADD UNIQUE INDEX `building_Id_UNIQUE` (`building_Id` ASC) ;
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_DEVICE
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE `lucasdb`.`lucas_device` 
ADD UNIQUE INDEX `device_Id_UNIQUE` (`device_Id` ASC) ;
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: LUCAS_MOBILE_APP_MESSAGE_OPTION
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE `lucasdb`.`lucas_mobile_app_message_option`
ADD UNIQUE INDEX `mobile_app_option_id_UNIQUE` (`mobile_app_option_id` ASC) ;