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
-- GOING FORWARD PLEASE KEEP THE TABLES IN ALPHABETICAL ORDER
--
-- ---------------------------------------------------------------------------


-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: CANVAS                                                  
-- DESCRIPTION: Defines AMD Canvas details                    
--                                                                           
-- ---------------------------------------------------------------------------
USE lucasdb;

DROP TABLE IF EXISTS canvas;

CREATE TABLE canvas (
  canvas_id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  short_name varchar(30) NOT NULL,
  canvas_type varchar(30) NOT NULL,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NULL DEFAULT NULL,
  layout_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (canvas_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: CANVAS_LAYOUT                                                  
-- DESCRIPTION: Defines AMD Canvas layout properties                    
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS canvas_layout;

CREATE TABLE canvas_layout (
  layout_id bigint(20) NOT NULL AUTO_INCREMENT,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  default_indicator bit(1) NOT NULL,
  display_order int(11) DEFAULT NULL,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (layout_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: CANVAS_WIDGETINSTANCELIST
-- DESCRIPTION: Associates AMD Widgets with AMD Canvases                   
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS canvas_widgetinstancelist;

CREATE TABLE canvas_widgetinstancelist (
  widgetinstance_id bigint(20) NOT NULL AUTO_INCREMENT,
  canvas_canvas_id bigint(20) NOT NULL,
  actual_widget_view_config longtext,
  widget_id bigint(20) DEFAULT NULL,
  data_url longtext DEFAULT NULL,
  widget_interaction_config longtext DEFAULT NULL,
  PRIMARY KEY (widgetinstance_id)
);

-- ---------------------------------------------------------------------------
--
--       TABLE: CODE_LOOKUP
-- DESCRIPTION: Application Code lookup table
--
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS code_lookup;

CREATE TABLE code_lookup (
  code_name varchar(50) NOT NULL,
  code_value varchar(50) NOT NULL,
  display_order int,
  updated_by varchar(100) DEFAULT NULL,
  updated_timestamp timestamp NULL DEFAULT NULL,
  PRIMARY KEY (code_name, code_value)
);


-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: DATABASECHANGELOG                                                
-- DESCRIPTION: Unknown                  
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS databasechangelog;

CREATE TABLE databasechangelog (
  ID varchar(63) NOT NULL,
  AUTHOR varchar(63) NOT NULL,
  FILENAME varchar(200) NOT NULL,
  DATEEXECUTED datetime NOT NULL,
  ORDEREXECUTED int(11) NOT NULL,
  EXECTYPE varchar(10) NOT NULL,
  MD5SUM varchar(35) DEFAULT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  COMMENTS varchar(255) DEFAULT NULL,
  TAG varchar(255) DEFAULT NULL,
  LIQUIBASE varchar(20) DEFAULT NULL,
  PRIMARY KEY (ID, AUTHOR, FILENAME)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: DATABASECHANGELOGLOCK                                                
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS databasechangeloglock;

CREATE TABLE databasechangeloglock (
  ID int(11) NOT NULL,
  LOCKED tinyint(1) NOT NULL,
  LOCKGRANTED datetime DEFAULT NULL,
  LOCKEDBY varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LOG_LINE                                                
-- DESCRIPTION: Unknown                  
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS log_line;

CREATE TABLE log_line (
  log_line_id bigint(20) NOT NULL AUTO_INCREMENT,
  cpu_usage_percent varchar(255) DEFAULT NULL,
  log_data varchar(2000) DEFAULT NULL,
  log_record_type varchar(255) DEFAULT NULL,
  log_severity varchar(255) DEFAULT NULL,
  relative_timestamp varchar(255) DEFAULT NULL,
  PRIMARY KEY (log_line_id)
);

-- ---------------------------------------------------------------------------
--
--       TABLE: LOG_MESSAGE
-- DESCRIPTION: Logs messages originating in the Mobile Application or AMD
--
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS log_message;

CREATE TABLE log_message (
  log_message_id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(100) DEFAULT NULL,
  message LONGTEXT DEFAULT NULL,
  device_id varchar(100) DEFAULT NULL,
  severity varchar(12) NOT NULL DEFAULT 'INFORMATION',
  source varchar(12) NOT NULL DEFAULT 'MOBILE_APP',
  log_timestamp timestamp NOT NULL,
  PRIMARY KEY (log_message_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: ALERT_LOCATIONS
-- DESCRIPTION: Unknown                  
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `lucasdb`.`alert_locations`;
CREATE TABLE  `lucasdb`.`alert_locations` (
  `alert_locations_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `alert_location_description` varchar(255) NOT NULL,
  `alert_location_name` varchar(50) NOT NULL,
  PRIMARY KEY (`alert_locations_id`),
  UNIQUE KEY `alert_location_name` (`alert_location_name`)
);



-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_CUSTOMER_CONFIGURATION                                                
-- DESCRIPTION: Unknown                  
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS lucas_customer_configuration;

CREATE TABLE lucas_customer_configuration (
  client_id bigint(20) NOT NULL AUTO_INCREMENT,
  client_name varchar(100) NOT NULL,
  default_language varchar(10) NOT NULL,
  client_logo mediumblob,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) NOT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (client_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: PRODUCT
-- DESCRIPTION: Stores product/item information                  
--                                                                           
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `lucasdb`.`product`;
CREATE TABLE  `lucasdb`.`product` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `absolute_max_factor` double DEFAULT NULL,
  `absolute_min_factor` double DEFAULT NULL,
  `base_item_threshold` bigint(20) DEFAULT NULL,
  `base_item_threshold_pf_level` varchar(50) DEFAULT NULL,
  `capture_catch_weight` bit(1) NOT NULL DEFAULT 0,
  `capture_expiration_date` bit(1) NOT NULL DEFAULT 0,
  `capture_lot_number` bit(1) NOT NULL DEFAULT 0,
  `capture_serial_number` bit(1) NOT NULL DEFAULT 0,
  `custom_classification_1` varchar(100) DEFAULT NULL,
  `custom_classification_10` varchar(100) DEFAULT NULL,
  `custom_classification_2` varchar(100) DEFAULT NULL,
  `custom_classification_3` varchar(100) DEFAULT NULL,
  `custom_classification_4` varchar(100) DEFAULT NULL,
  `custom_classification_5` varchar(100) DEFAULT NULL,
  `custom_classification_6` varchar(100) DEFAULT NULL,
  `custom_classification_7` varchar(100) DEFAULT NULL,
  `custom_classification_8` varchar(100) DEFAULT NULL,
  `custom_classification_9` varchar(100) DEFAULT NULL,
  `customer_SKU_nbr` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `display_as_factor` bit(1) NOT NULL DEFAULT 0,
  `enforce_catch_weight_range` bit(1) NOT NULL DEFAULT 0,
  `enforce_expiration_date_range` bit(1) NOT NULL DEFAULT 0,
  `expiration_lead_time_days` int(11) DEFAULT NULL,
  `expiration_use_international_dt_format` bit(1) NOT NULL DEFAULT 0,
  `haz_material` bit(1) NOT NULL DEFAULT 0,
  `image_url` longtext,
  `import_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `import_id` bigint(20) DEFAULT NULL,
  `is_base_item` bit(1) NOT NULL DEFAULT 0,
  `manufacturer_item_nbr` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `override_item_weight` double DEFAULT NULL,
  `perform_2nd_validation` bit(1) NOT NULL DEFAULT 0,
  `product_code` varchar(50) DEFAULT NULL,
  `product_status_code` varchar(50) DEFAULT NULL,
  `2nd_validation_counter` bigint(20) DEFAULT NULL,
  `2nd_validate_every` bigint(20) DEFAULT NULL,
  `2nd_validate_every_pf_level` varchar(50) DEFAULT NULL,
  `target_max_factor` double DEFAULT NULL,
  `target_min_factor` double DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
-- TABLE: PRODUCT_CUSTOM_CLASSIFICATION
-- DESCRIPTION: Stores product custom classification information                  
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS product_custom_classification;
CREATE TABLE product_custom_classification (
  custom_classification_field_name varchar(50) NOT NULL,
  custom_classification_name varchar(50),
  custom_classification_definition longtext,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) NOT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (custom_classification_field_name),
  UNIQUE KEY custom_classification_name (custom_classification_name)
);


-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_SETTING                                                
-- DESCRIPTION: Used to store user preferences                  
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS lucas_setting;

CREATE TABLE lucas_setting (
  configuration_id bigint(20) NOT NULL AUTO_INCREMENT,
  super_user_id bigint(20) DEFAULT NULL,
  lucas_logo mediumblob,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) NOT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (configuration_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: SPECIAL_INSTRUCTION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `lucasdb`.`special_instructions`;
CREATE TABLE  `lucasdb`.`special_instructions` (
  `special_instruction_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `response_type` varchar(50) DEFAULT NULL,
  `special_instruction` varchar(255) DEFAULT NULL,
  `alert_locations_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`special_instruction_id`),
  UNIQUE KEY `special_instruction` (`special_instruction`)
);
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_USER                                               
-- DESCRIPTION: Stores user details                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS lucas_user;

CREATE TABLE lucas_user (
  user_id bigint(20) NOT NULL AUTO_INCREMENT,
  email_address varchar(100) DEFAULT NULL,
  first_name varchar(100) DEFAULT NULL,
  hashed_password varchar(500) DEFAULT NULL,
  last_name varchar(100) DEFAULT NULL,
  username varchar(100) NOT NULL,
  title varchar(45) DEFAULT NULL,
  mobile_number varchar(45) DEFAULT NULL,
  start_date date DEFAULT NULL,
  birth_date date DEFAULT NULL,
  employee_number varchar(100) DEFAULT NULL,
  j2uLanguage varchar(10) NOT NULL DEFAULT "EN_US",
  u2jLanguage varchar(10) NOT NULL DEFAULT "EN_US",
  hhLanguage varchar(10) NOT NULL DEFAULT "EN_US",
  amdLanguage varchar(10) NOT NULL DEFAULT "EN_US",
  enable bit(1) NOT NULL DEFAULT 1,
  skill varchar(50) DEFAULT NULL,
  shift_id bigint(20) DEFAULT NULL,
  voice_model varchar(45) DEFAULT NULL,
  retrain_voice_model bit(1) NOT NULL DEFAULT 0,
  current_work_type varchar(10) DEFAULT NULL,
  home_canvas_id bigint(20) DEFAULT NULL,
  active_canvas_id bigint(20) DEFAULT NULL,
  see_home_canvas_indicator bit(1) NOT NULL DEFAULT 0,
  created_by_username varchar(100) DEFAULT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_indicator bit(1) NOT NULL DEFAULT 0,
  date_format_pref varchar(10) NOT NULL DEFAULT "DD-MM-YYYY",
  time_format_pref varchar(10) NOT NULL DEFAULT "24HR",
  data_refresh_frequency_pref bigint NOT NULL DEFAULT 120,
  PRIMARY KEY (user_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: OPEN_USER_CANVAS                                                
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS open_user_canvas;

CREATE TABLE open_user_canvas (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  displayOrder int(11) DEFAULT NULL,
  user_user_id bigint(20) NOT NULL,
  canvas_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: LUCAS_USER_PERMISSION_GROUP                                                
-- DESCRIPTION: Associates a User with one or more Groups                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS lucas_user_permission_group;

CREATE TABLE lucas_user_permission_group (
  user_id bigint(20) NOT NULL,
  permission_group_id bigint(20) NOT NULL,
  PRIMARY KEY (user_id, permission_group_id)
);

-- ---------------------------------------------------------------------------
--
--       TABLE: PACK_FACTOR_COMPONENT
-- DESCRIPTION: Defines product pack factor component
--
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS pack_factor_component;

CREATE TABLE pack_factor_component (
  pf_component_id  bigint(20) NOT NULL AUTO_INCREMENT,
  pf_component_name varchar(50) NOT NULL,
  pf_component_definition longtext DEFAULT NULL,
  pf_component_voicing varchar(50) DEFAULT NULL,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) NOT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (pf_component_id),
  UNIQUE KEY `pf_component_name` (`pf_component_name`)
);

-- ---------------------------------------------------------------------------
--
--       TABLE: PACK_FACTOR_HIERARCHY
-- DESCRIPTION: Defines product pack factor hierarchy
--
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS pack_factor_hierarchy;

CREATE TABLE pack_factor_hierarchy (
  pf_hierarchy_id bigint(20) NOT NULL AUTO_INCREMENT,
  pf_hierarchy_name varchar(50) NOT NULL,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) NOT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (pf_hierarchy_id),
  UNIQUE KEY `pf_hierarchy_name` (`pf_hierarchy_name`)
);

-- ---------------------------------------------------------------------------
--
--       TABLE: PACK_FACTOR_HIERARCHY_COMPONENT
-- DESCRIPTION: Defines product pack factor hierarchy components
--
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS pack_factor_hierarchy_component;

CREATE TABLE pack_factor_hierarchy_component (
  pf_hierarchy_id bigint(20) NOT NULL,
  pf_component_id  bigint(20) NOT NULL,
  child_pf_component_id   bigint(20),
  factor   decimal(30,5),
  level   int(10) NOT NULL,
  bottom_level_flag   boolean NOT NULL,
  default_height decimal(30,5),
  default_width decimal(30,5),
  default_depth decimal(30,5),
  default_weight decimal(30,5),
  default_cube decimal(30,5),
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) NOT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: PERMISSION                                                
-- DESCRIPTION: Defines system permissions                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS permission;

CREATE TABLE permission (
  permission_id bigint(20) NOT NULL AUTO_INCREMENT,
  permission_name varchar(50) NOT NULL,
  permission_category varchar(50) DEFAULT NULL,
  display_order bigint(20) NOT NULL,
  PRIMARY KEY (permission_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: PERMISSION_GROUP                                                
-- DESCRIPTION: Defines system groups                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS permission_group;
CREATE TABLE permission_group (
  permission_group_id bigint(20) NOT NULL AUTO_INCREMENT,
  permission_group_name varchar(50) NOT NULL,
  description varchar(100) DEFAULT NULL,
  created_by_username varchar(100) DEFAULT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (permission_group_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: PERMISSION_GROUP_PERMISSION
-- DESCRIPTION: Associates a Group with one or more Permissions                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS permission_group_permission;

CREATE TABLE permission_group_permission (
  permission_group_id bigint(20) NOT NULL,
  permission_id bigint(20) NOT NULL,
  PRIMARY KEY (permission_group_id, permission_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: PICKLINE                                                
-- DESCRIPTION: Unknown                
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS pickline;

CREATE TABLE pickline (
  pickline_id bigint(20) NOT NULL AUTO_INCREMENT,
  completed bit(1) DEFAULT NULL,
  quantity int(11) DEFAULT NULL,
  wave_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (pickline_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: REGION                                                
-- DESCRIPTION: Unknown                
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS region;

CREATE TABLE region (
  region_id bigint(20) NOT NULL AUTO_INCREMENT,
  region_name varchar(100) DEFAULT NULL,
  PRIMARY KEY (region_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: SHIFT                                                
-- DESCRIPTION: Stores information about warehouse shifts                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS shift;

CREATE TABLE shift (
  shift_id bigint(20) NOT NULL AUTO_INCREMENT,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  end_time varchar(50) DEFAULT NULL,
  shift_name varchar(50) NOT NULL,
  start_time varchar(50) DEFAULT NULL,
  PRIMARY KEY (shift_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: SUPPORTED_LANGUAGE                                                
-- DESCRIPTION: Defines the support system languages                
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS supported_language;

CREATE TABLE supported_language (
  language_code varchar(10),
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  amdLanguage bit(1) NOT NULL,
  hhLanguage bit(1) NOT NULL,
  j2uLanguage bit(1) NOT NULL,
  u2jLanguage bit(1) NOT NULL,
  PRIMARY KEY (language_code)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: UOM                                                
-- DESCRIPTION: Defines supported Unit Of Measure factors                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS uom;

CREATE TABLE uom (
  uom_id bigint(20) NOT NULL AUTO_INCREMENT,
  customer_facing_name varchar(255) DEFAULT NULL,
  dimension varchar(30) DEFAULT NULL,
  factor float(11,5) DEFAULT NULL,
  uom_name varchar(255) NOT NULL,
  parent_uom_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (uom_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: WAREHOUSE                                                
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS warehouse;

CREATE TABLE warehouse (
  warehouse_id bigint(20) NOT NULL AUTO_INCREMENT,
  warehouse_name varchar(100) NOT NULL,
  region_id bigint(20) NOT NULL,
  PRIMARY KEY (warehouse_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: WAVE                                                
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS wave;

CREATE TABLE wave (
  wave_id bigint(20) NOT NULL AUTO_INCREMENT,
  wave_name varchar(255) NOT NULL,
  PRIMARY KEY (wave_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: WIDGET                                                
-- DESCRIPTION: Defines the properties of available widgets                
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS widget;

CREATE TABLE widget (
  DTYPE varchar(31) NOT NULL,
  widget_id bigint(20) NOT NULL AUTO_INCREMENT,
  action_config longtext NOT NULL,
  default_widget_view_config longtext,
  description varchar(255) DEFAULT NULL,
  license varchar(255) DEFAULT NULL,
  name varchar(255) NOT NULL,
  title varchar(255) DEFAULT NULL,
  short_name varchar(255) DEFAULT NULL,
  widget_type varchar(255) DEFAULT NULL,
  definition_data longtext,
  method_to_invoke_definition varchar(100) DEFAULT NULL,
  active bit(1) NOT NULL,
  data_url varchar(255) DEFAULT NULL,
  category varchar(50) NOT NULL,
  PRIMARY KEY (widget_id)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: WMS_USER                                                
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS wms_user;

CREATE TABLE wms_user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL,
  host_login varchar(100) NOT NULL,
  host_hashed_password varchar(100) DEFAULT NULL,
  created_by_username varchar(100) NOT NULL,
  created_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by_username varchar(100) DEFAULT NULL,
  updated_date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_EVENT                                                
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_event`;

CREATE TABLE `eai_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_category` varchar(256) NOT NULL,
  `event_description` varchar(256) NOT NULL,
  `event_name` varchar(120) NOT NULL,
  `event_source` varchar(120) NOT NULL,
  `event_sub_category` varchar(256) NOT NULL,
  `event_type` varchar(80) NOT NULL,
  PRIMARY KEY (`event_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_mapping`;

CREATE TABLE `eai_mapping` (
  `mapping_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `destination_message_id` bigint(20) DEFAULT NULL,
  `mapping_description` varchar(80) NOT NULL,
  `mapping_name` varchar(80) NOT NULL,
  `source_message_id` bigint(20) DEFAULT NULL,
  `usage_in_events_handlers` int(11) DEFAULT NULL,
  PRIMARY KEY (`mapping_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_TRANSPORT
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_transport`;

CREATE TABLE `eai_transport` (
  `transport_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delete_source_file` bit(1) DEFAULT NULL,
  `host` varchar(60) DEFAULT NULL,
  `inbound_directory` varchar(80) DEFAULT NULL,
  `password` varchar(24) DEFAULT NULL,
  `polling_frequency` int(11) DEFAULT NULL,
  `port_number` int(11) DEFAULT NULL,
  `remote_directory` varchar(80) DEFAULT NULL,
  `transport_name` varchar(80) DEFAULT NULL,
  `transport_type` varchar(80) DEFAULT NULL,
  `user_name` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`transport_id`)
);
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_EVENT_HANDLER                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_event_handler`;

CREATE TABLE `eai_event_handler` (
  `event_handler_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_handler_description` varchar(80) DEFAULT NULL,
  `event_handler_name` varchar(80) NOT NULL,
  `event_handler_type` varchar(40) NOT NULL,
  `inbound_mapping_id` bigint(20) DEFAULT NULL,
  `outbound_file_pattern` varchar(120) DEFAULT NULL,
  `usage_in_event` int(11) DEFAULT NULL,
  `outbound_mapping_id` bigint(20) DEFAULT NULL,
  `event_transport_id` bigint(20) DEFAULT NULL,
  `inbound_file_pattern` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`event_handler_id`)
);


-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_EVENT_EVENT_HANDLER                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_event_event_handler`;

CREATE TABLE `eai_event_event_handler` (
  `event_event_handler_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `execution_order` varchar(40) DEFAULT NULL,
  `seq` int(11) NOT NULL,
  `event_handler_id` bigint(20) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`event_event_handler_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_TRANSFORMATION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_transformation`;

CREATE TABLE `eai_transformation` (
  `transformation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transformation_channel` varchar(60) NOT NULL,
  `transformation_name` varchar(60) NOT NULL,
  `transformation_type` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`transformation_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_TRANSFORMATION_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_transformation_definition`;

CREATE TABLE `eai_transformation_definition` (
  `transformation_definition_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transformation_expression` varchar(80) DEFAULT NULL,
  `transformation_field_length` int(11) DEFAULT NULL,
  `transformation_field_name` varchar(80) NOT NULL,
  `transformation_field_seq` int(11) NOT NULL,
  `transformation_field_type` varchar(80) DEFAULT NULL,
  `transformation_field_var` bit(1) DEFAULT NULL,
  `transformation_id` bigint(20) NOT NULL,
  `message_field_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`transformation_definition_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_FILTER_CONDITION                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_filter_condition`;

CREATE TABLE `eai_filter_condition` (
  `filter_condition_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filter_condition` varchar(256) NOT NULL,
  `transformation_definition_id` bigint(20) NOT NULL,
  PRIMARY KEY (`filter_condition_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_JOINER_CONDITION                                               
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_joiner_condition`;

CREATE TABLE `eai_joiner_condition` (
  `joiner_condition_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `joiner_condition_detail` varchar(256) NOT NULL,
  `joiner_condition_master` varchar(256) NOT NULL,
  `joiner_condition_operator` varchar(2) NOT NULL,
  `transformation_definition_id` bigint(20) NOT NULL,
  PRIMARY KEY (`joiner_condition_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING_DEFINITION                                             
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_mapping_definition`;

CREATE TABLE `eai_mapping_definition` (
  `mapping_definition_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transformation_entity_name` varchar(120) DEFAULT NULL,
  `transformation_id` bigint(20) DEFAULT NULL,
  `mapping_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`mapping_definition_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MAPPING_PATH
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_mapping_path`;

CREATE TABLE `eai_mapping_path` (
  `mapping_path_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_transformation_id` bigint(20) DEFAULT NULL,
  `mapping_path_seq` int(11) DEFAULT NULL,
  `to_transformation_id` bigint(20) DEFAULT NULL,
  `mapping_id` bigint(20) NOT NULL,
  PRIMARY KEY (`mapping_path_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_SEGMENT
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_segments`;

CREATE TABLE `eai_segments` (
  `segment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `segment_description` varchar(80) NOT NULL,
  `segment_length` int(11) NOT NULL,
  `segment_name` varchar(16) NOT NULL,
  PRIMARY KEY (`segment_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_SEGMENT_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_segment_definition`;

CREATE TABLE `eai_segment_definition` (
  `segment_field_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `segment_field_description` varchar(80) NOT NULL,
  `segment_field_end` int(11) DEFAULT NULL,
  `segment_field_length` int(11) DEFAULT NULL,
  `segment_field_name` varchar(80) NOT NULL,
  `segment_field_seq` int(11) NOT NULL,
  `segment_field_start` int(11) DEFAULT NULL,
  `segment_field_type` varchar(80) NOT NULL,
  `segment_field_value` varchar(80) DEFAULT NULL,
  `segment_segment_id` bigint(20) DEFAULT NULL,
  `segment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`segment_field_id`)
);

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MESSAGE
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_message`;

CREATE TABLE `eai_message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lucas_predefined` bit(1) NOT NULL,
  `lucas_predefined_service` varchar(50) DEFAULT NULL,
  `message_description` varchar(120) NOT NULL,
  `message_format` varchar(80) NOT NULL,
  `message_name` varchar(80) NOT NULL,
  `usage_in_events` int(11) NOT NULL,
  `message_field_delimited_character` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
);
-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: EAI_MESSAGE_DEFINITION
-- DESCRIPTION: Unknown                 
--                                                                           
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `eai_message_definition`;

CREATE TABLE `eai_message_definition` (
  `message_field_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_field_description` varchar(80) NOT NULL,
  `message_field_ends` int(11) DEFAULT NULL,
  `message_field_length` int(11) NOT NULL,
  `message_field_name` varchar(80) NOT NULL,
  `message_field_repeat` int(11) DEFAULT NULL,
  `message_field_separator` varchar(1) DEFAULT NULL,
  `message_field_seq` int(11) NOT NULL,
  `message_field_starts` int(11) DEFAULT NULL,
  `message_field_type` varchar(80) NOT NULL,
  `message_segment_id` bigint(20) DEFAULT NULL,
  `message_id` bigint(20) NOT NULL,
  `parent_segment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`message_field_id`)
);


-- ===========================================================================
-- ---------------- EQUIPMENT MODULE RELATED TABLES --------------------------
-- ===========================================================================

-- ---------------------------------------------------------------------------
--       TABLE: equipment_style
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `equipment_style`;
CREATE TABLE  `equipment_style` (
`equipment_style_id` bigint(20) NOT NULL AUTO_INCREMENT,
`equipment_style_code` varchar(50) NOT NULL,
`equipment_style_name` varchar(50) NOT NULL,
  PRIMARY KEY (`equipment_style_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: container_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `container_type`;
CREATE TABLE  `container_type` (
`container_code` varchar(25) NOT NULL,
`container_description` varchar(255),
`length` bigint(20),
`width` bigint(20),
`height` bigint(20),
`color` varchar(50) ,
`material` varchar(50),
  PRIMARY KEY (`container_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: question_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `question_type`;
CREATE TABLE  `question_type` (
`question_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
`question_type_name` varchar(50) NOT NULL,
`question_type_description` varchar(255),
  PRIMARY KEY (`question_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: answer_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `answer_type`;
CREATE TABLE  `answer_type` (
`answer_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
`answer_type_name` varchar(50) NOT NULL,
`answer_type_description` varchar(255) ,
  PRIMARY KEY (`answer_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `equipment_type`;
CREATE TABLE  `equipment_type` (
  `equipment_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `equipment_type_name` varchar(50) NOT NULL,
  `equipment_type_description` varchar(100),
  `requires_check_list` bit(1) NOT NULL DEFAULT 0,
  `requires_certification` bit(1) NOT NULL DEFAULT 0,
  `shipping_container` bit(1) NOT NULL DEFAULT 0,
  `reinspect_time_interval` bigint(20),
  `equipment_type_status` varchar(25) DEFAULT NULL,
  `equipment_count` bigint(20),
  `equipment_type_positions` bigint(20) NOT NULL,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by_import_id` bigint(20),
  `updated_by_import_id` bigint(20),
  `equipment_style_id` bigint(20) NOT NULL,
  `container_type_default` varchar(25) NOT NULL,
  PRIMARY KEY (`equipment_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `equipment_type_position`;
CREATE TABLE  `equipment_type_position` (
  `equipment_type_position_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `equipment_type_position_nbr`bigint(20) NOT NULL,
  `x_position` bigint(20) NOT NULL ,
  `y_position` bigint(20) NOT NULL ,
  `z_position` bigint(20) NOT NULL ,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `equipment_type_id` bigint(20),
  PRIMARY KEY (`equipment_type_position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `question`;
CREATE TABLE  `question` (
`question_id` bigint(20) NOT NULL AUTO_INCREMENT,
`question_description` varchar(50) NOT NULL,
`question_prompt` LONGTEXT NOT NULL,
`question_friendly_prompt` LONGTEXT  NOT NULL,
`question_help` LONGTEXT  NOT NULL ,
 `question_type_id` bigint(20) NOT NULL ,
 `answer_type_id` bigint(20) NOT NULL ,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `equipment_position`;
CREATE TABLE  `equipment_position` (
  `equipment_position_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `equipment_position_nbr`bigint(20) NOT NULL,
  `equipment_position_checkstring` varchar(20),
  `equipment_position_name` varchar(50),
  `x_position` bigint(20) NOT NULL ,
  `y_position` bigint(20) NOT NULL ,
  `z_position` bigint(20) NOT NULL ,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `equipment_id` bigint(20) NOT NULL,
   `container_id` bigint(20),
   `permanent_container_id` bigint(20),
    PRIMARY KEY (`equipment_position_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: equipment
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `equipment`;
CREATE TABLE  `equipment` (
 `equipment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `equipment_name` varchar(50) NOT NULL,
  `equipment_description` varchar(100),
  `equipment_status` varchar(25) DEFAULT NULL,
  `equipment_positions` bigint(20),
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by_import_id` bigint(20),
  `updated_by_import_id` bigint(20),
  `equipment_type_id`  bigint(20) NOT NULL,
  `current_user_id`  bigint(20),
  `current_assignment_id` bigint(20),
  PRIMARY KEY (`equipment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `equipment_type_question`;
CREATE TABLE  `equipment_type_question` (
  `equipment_type_question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `equipment_type_id` bigint(20) NOT NULL ,
  `question_id` bigint(20) NOT NULL ,
  PRIMARY KEY (`equipment_type_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: answer_evaluation_rule
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `answer_evaluation_rule`;
CREATE TABLE  `answer_evaluation_rule` (
  `answer_evaluation_rule_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator` varchar(20),
  `operand` varchar(50),
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `equipment_type_id` bigint(20),
  `question_id` bigint(20),
  PRIMARY KEY (`answer_evaluation_rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ---------------------------------------------------------------------------
--       TABLE: lucas_user_certified_equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS `lucas_user_certified_equipment_type`;
CREATE TABLE  `lucas_user_certified_equipment_type` (
  `user_certified_equipment_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by_username` varchar(100) NOT NULL,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by_username` varchar(100) NOT NULL,
  `updated_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `equipment_type_id` bigint(20),
  `user_id` bigint(20),
   PRIMARY KEY (`user_certified_equipment_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- =============================================================================================

-- ---------------------------------------------------------------------------
--
--       TABLE: LUCAS_BUILDING
-- 
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS `lucas_building`;

CREATE  TABLE `lucasdb`.`lucas_building` (
  `building_Id` BIGINT(20) NOT NULL ,
  `building_name` VARCHAR(50) NOT NULL ,
  `address1` VARCHAR(100) NULL ,
  `address2` VARCHAR(100) NULL ,
  `city` VARCHAR(50) NULL ,
  `state` VARCHAR(100) NULL ,
  `zipcode` VARCHAR(10) NULL ,
  `phone` VARCHAR(50) NULL ,
  `fax` VARCHAR(50) NULL ,
  `email` VARCHAR(100) NULL ,
  PRIMARY KEY (`building_Id`) );
-- ---------------------------------------------------------------------------
--
--       TABLE: LUCAS_DEVICE
-- 
-- ---------------------------------------------------------------------------

  DROP TABLE IF EXISTS `lucas_device`;
  
  CREATE  TABLE `lucasdb`.`lucas_device` (
  `device_Id` VARCHAR(20) NOT NULL ,
  `ip_address` VARCHAR(20) NULL ,
  `qualified_server_name` VARCHAR(50) NULL ,
  `building_Id` BIGINT(20) NULL ,
  PRIMARY KEY (`device_Id`));
-- ---------------------------------------------------------------------------
--
--       TABLE: LUCAS_MOBILE_APP_MESSAGE_OPTION
-- 
-- ---------------------------------------------------------------------------

  DROP TABLE IF EXISTS `lucas_mobile_app_message_option`;
  
  CREATE  TABLE `lucasdb`.`lucas_mobile_app_message_option` (
  `mobile_app_option_id` VARCHAR(20) NOT NULL ,
  `current_server_utc` TIMESTAMP,
  `building_id` BIGINT(20) NOT NULL ,
  `require_password` BIT NULL ,
  `application_name` VARCHAR(50) NULL ,
  `application_version` VARCHAR(20) NULL ,
  `spapi_messaging_version` VARCHAR(2) NULL ,
  `default_display_language_code` VARCHAR(5) NULL ,
  `additional_options` VARCHAR(45) NULL ,
  PRIMARY KEY (`mobile_app_option_id`) );
