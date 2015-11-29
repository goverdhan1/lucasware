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
-- DROP INDICIES
--
ALTER TABLE canvas 
  DROP INDEX canvas_name_IDX,
  DROP INDEX layout_id_IDX;

ALTER TABLE canvas_layout 
  DROP INDEX display_order_IDX;
  
ALTER TABLE canvas_widgetinstancelist
  DROP INDEX canvas_id_IDX,
  DROP INDEX widget_id_IDX;

ALTER TABLE lucas_alert_locations
  DROP INDEX friendly_name_IDX;

ALTER TABLE lucas_customer_configuration
  DROP INDEX client_id_IDX;

ALTER TABLE lucas_product
  DROP INDEX product_number_IDX;

ALTER TABLE lucas_setting
  DROP INDEX super_user_id_IDX;

ALTER TABLE lucas_special_instructions
  DROP INDEX special_instruction_IDX,
  DROP INDEX product_id_IDX,
  DROP INDEX alert_location_IDX;

ALTER TABLE lucas_user
  DROP INDEX username_IDX,
  DROP INDEX shift_id_IDX;

ALTER TABLE open_user_canvas
  DROP INDEX canvas_id_IDX,
  DROP INDEX user_id_IDX;

ALTER TABLE lucas_user_permission_group
  DROP INDEX permission_group_id_IDX;

ALTER TABLE pack_factor_hiearchy
  DROP INDEX pf_hierarchy_name_IDX;

ALTER TABLE pack_factor_component
  DROP INDEX pf_component_name_IDX;

ALTER TABLE permission
  DROP INDEX permission_name_IDX;

ALTER TABLE permission_group
  DROP INDEX permission_group_name_IDX;

ALTER TABLE pickline
  DROP INDEX wave_id_IDX;

ALTER TABLE shift
  DROP INDEX shift_name_IDX;

ALTER TABLE supported_language
  DROP INDEX language_code_IDX;

ALTER TABLE uom
  DROP INDEX uom_name_IDX,
  DROP INDEX parent_uom_id_IDX;

ALTER TABLE warehouse
  DROP INDEX warehouse_id_IDX,
  DROP INDEX region_id_IDX;

ALTER TABLE wave
  DROP INDEX wave_name_IDX;
  
ALTER TABLE widget
  DROP INDEX name_IDX;

ALTER TABLE wms_user
  DROP INDEX host_login_IDX;

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_event
  DROP INDEX event_name_IDX;

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_EVENT_HANDLER
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_event_handler
  DROP INDEX event_handler_name_IDX;

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_TRANSPORT
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_transport
  DROP INDEX transport_name_IDX;

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_MAPPING
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_mapping
  DROP INDEX mapping_name_IDX;  
  
-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_MESSAGE
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_message
  DROP INDEX message_name_IDX,
  DROP INDEX message_format_IDX;    

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: EAI_TRANSFORMATION
--                                                                           
-- ---------------------------------------------------------------------------
ALTER TABLE eai_transformation
  DROP INDEX transformation_name_IDX;     


