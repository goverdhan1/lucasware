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
--       TABLE: SUPPORTED_LANGUAGE                                                  
-- DESCRIPTION: Defines default set of system languages                    
--                                                                           
-- ---------------------------------------------------------------------------
USE lucasdb;

INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('EN_GB', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('EN_US', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('ES_ES', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('DE_DE', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('FR_FR', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('FR_CA', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('ES_MX', 'system', SYSDATE(), 1, 1, 1, 1);
INSERT INTO lucasdb.supported_language (language_code, created_by_username, created_date_time, amdLanguage, hhLanguage, j2uLanguage, u2jLanguage) 
    VALUES ('PT_BR', 'system', SYSDATE(), 1, 1, 1, 1);   

-- ---------------------------------------------------------------------------
--                                                                           
--       TABLE: PERMISSION                                                   
-- DESCRIPTION: Defines default set of system permissions                    
--                                                                           
-- ---------------------------------------------------------------------------
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('group-maintenance-widget-access', 'Groups', '10');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-group', 'Groups', '20');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('edit-group', 'Groups', '30');

INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('search-user-widget-access', 'Users', '10');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-edit-user-widget-access', 'Users', '20');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-user', 'Users', '30');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('edit-user', 'Users', '40');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('disable-user', 'Users', '50');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('delete-user', 'Users', '60');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('enable-user', 'Users', '70');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('retrain-voice-model', 'Users', '80');

INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-canvas', 'canvas', '10');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('publish-canvas', 'canvas', '20');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('edit-company-canvas', 'canvas', '30');

INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('user-list-download-excel', 'data-export', '80');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('user-list-download-pdf', 'data-export', '90');

INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-product', 'product-management', '100');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('view-product', 'product-management', '110');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('edit-product', 'product-management', '120');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('delete-product', 'product-management', '130');

INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-assignment', 'user-management', '140');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('view-report-productivity', 'user-management', '150');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('configure-location', 'user-management', '160');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('authenticated-user', 'user-management', '170');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('edit-multi-user', 'user-management', '210');

INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('pickline-by-wave-widget-access', 'widget-permissions', '260');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('search-product-grid-widget-access', 'widget-permissions', '280');

INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('equipment-type-widget-access', 'Equipment', '10');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('equipment-management-widget-access', 'Equipment', '20');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('create-equipment-type', 'Equipment', '60');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('edit-equipment-type',	'Equipment', '70');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-equipment-type', 'Equipment', '80');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('create-equipment', 'Equipment', '130');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('edit-equipment', 'Equipment', '140');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-equipment', 'Equipment', '150');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('unassign-user', 'Equipment', '160');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('view-checklist', 'Equipment', '170');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('search-group-widget-access', 'Groups', '10');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-group', 'Groups', '50');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('message-segments-widget-access', 'EAI', '10');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('create-message-segment', 'EAI', '20');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('edit-message-segment', 'EAI', '30');
INSERT INTO lucasdb.permission (permission_name, permission_category, display_order) VALUES ('delete-message-segment', 'EAI', '40');

INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('messages-widget-access','EAI','70');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('create-message', 'EAI', '80');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('edit-message', 'EAI', '90');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-message', 'EAI', '100');

INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('events-widget-access', 'EAI', '300');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('create-event', 'EAI', '310');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('edit-event', 'EAI', '320');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-event', 'EAI', '330');

INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('message-mappings-widget-access', 'EAI', '150');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-message-mapping', 'EAI', '160');

INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('event-handlers-widget-access','EAI','250');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('create-event-handler', 'EAI', '260');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('edit-event-handler', 'EAI', '270');
INSERT INTO `lucasdb`.`permission` (`permission_name`, `permission_category`, `display_order`) VALUES ('delete-event-handler', 'EAI', '280');


-- ----------------------------------------------------------------------------
--
--       TABLE: PERMISSION_GROUP
-- DESCRIPTION: Defines default set of system groups
--
-- ----------------------------------------------------------------------------
INSERT INTO lucasdb.permission_group (permission_group_name, description, created_by_username, created_date_time, updated_date_time) VALUES ('system', 'System group', 'system', SYSDATE(), SYSDATE());
INSERT INTO lucasdb.permission_group (permission_group_name, description, created_by_username, created_date_time, updated_date_time) VALUES ('administrator', 'Admin group', 'system', SYSDATE(), SYSDATE());
INSERT INTO lucasdb.permission_group (permission_group_name, description, created_by_username, created_date_time, updated_date_time) VALUES ('warehouse-manager', 'Warehouse Manager group', 'system', SYSDATE(), SYSDATE());
INSERT INTO lucasdb.permission_group (permission_group_name, description, created_by_username, created_date_time, updated_date_time) VALUES ('supervisor', 'Supervisor group', 'system', SYSDATE(), SYSDATE());
INSERT INTO lucasdb.permission_group (permission_group_name, description, created_by_username, created_date_time, updated_date_time) VALUES ('picker', 'Picker group', 'system', SYSDATE(), SYSDATE());
INSERT INTO lucasdb.permission_group (permission_group_name, description, created_by_username, created_date_time, updated_date_time) VALUES ('basic-authenticated-user','Basic Authenticate Group', 'system', SYSDATE(), SYSDATE());

-- ----------------------------------------------------------------------------
--
--       TABLE: PERMISSION_GROUP_PERMISSION
-- DESCRIPTION: Associates a set of permissions with each groups
--
-- ----------------------------------------------------------------------------
-- Assign permissions to group 'system'
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'group-maintenance-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-group'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-group'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'search-user-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-edit-user-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'disable-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'enable-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'retrain-voice-model'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-canvas'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-company-canvas'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'publish-canvas'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'user-list-download-excel'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'user-list-download-pdf'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'view-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-product'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-assignment'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'view-report-productivity'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'configure-location'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'authenticated-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-multi-user'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'pickline-by-wave-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'search-product-grid-widget-access')); 

-- Assign permissions to group 'administrator'
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'authenticated-user'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'publish-canvas'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'message-segments-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-message-segment'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-message-segment'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-message-segment'));


INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'message-mappings-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-message-mapping'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'event-handlers-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-event-handler'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-event-handler'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-event-handler'));



-- Assign permissions to group 'warehouse-manager'
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'warehouse-manager'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-assignment'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'warehouse-manager'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'view-report-productivity'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'warehouse-manager'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'configure-location'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'warehouse-manager'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-canvas'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'warehouse-manager'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-company-canvas'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
 (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'warehouse-manager'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'publish-canvas'));

-- Assign permissions to group 'supervisor'

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'user-list-download-excel'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'user-list-download-pdf'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'publish-canvas'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'disable-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'enable-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-multi-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'view-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-product'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-edit-user-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'search-user-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'pickline-by-wave-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'group-maintenance-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'search-product-grid-widget-access'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-group'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-group'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-user'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'retrain-voice-model'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'equipment-type-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'equipment-management-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-equipment-type'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-equipment-type'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-equipment-type'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-equipment'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-equipment'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-equipment'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'unassign-user'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'view-checklist'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'messages-widget-access'));
	
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-message'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-message'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-message'));

-- Assign permissions to group 'picker'
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'picker'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-assignment'));
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'picker'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'authenticated-user'));


-- Assign permissions to group 'basic-authenticated-user'
INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'basic-authenticated-user'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'authenticated-user'));
	
-- Assign permissions to group 'supervisor' 

	INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'search-group-widget-access'));

	INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'supervisor'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-group'));
 
-- ----------------------------------------------------------------------------
--
--       TABLE: LUCAS_USER
-- DESCRIPTION: Creates a 'system' user
--
-- ---------------------------------------------------------------------------- 
INSERT INTO lucasdb.lucas_user (user_id, username, first_name, last_name, email_address, hashed_password, j2uLanguage, u2jLanguage, hhLanguage, amdLanguage, enable, created_by_username, created_date_time,  deleted_indicator)
    VALUES (1, 'system', 'Lucas System', 'Lucas System', 'support@lucasware.com', '$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy', 'EN_GB', 'EN_GB', 'EN_GB', 'EN_GB', 1, 'system', SYSDATE(), 0);


-- ----------------------------------------------------------------------------
--
--       TABLE: LUCAS_USER_PERMISSION_GROUP
-- DESCRIPTION: Assigns user 'system' to group 'system'
--
-- ---------------------------------------------------------------------------- 
INSERT INTO lucasdb.lucas_user_permission_group (user_id, permission_group_id) VALUES (
    (SELECT user_id FROM lucasdb.lucas_user WHERE username = 'system'),
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'));
   
-- ----------------------------------------------------------------------------
--
--       TABLE: CANVAS
-- DESCRIPTION: Default canvases
--
-- ----------------------------------------------------------------------------     
INSERT INTO lucasdb.canvas (name, short_name, canvas_type, created_by_username, created_date_time) 
    VALUES ('Perishable Goods Canvas', 'Perishable', 'PRIVATE', 'jack123', SYSDATE()); 
INSERT INTO lucasdb.canvas (name, short_name, canvas_type, created_by_username, created_date_time) 
    VALUES ('Morning Shift Canvas', 'MorningShift', 'PRIVATE', 'jack123', SYSDATE());
INSERT INTO lucasdb.canvas (name, short_name, canvas_type, created_by_username, created_date_time) 
    VALUES ('Hazmat Canvas', 'Hazmat', 'COMPANY', 'system', SYSDATE());
INSERT INTO lucasdb.canvas (name, short_name, canvas_type, created_by_username, created_date_time) 
    VALUES ('User Management Canvas', 'UserManagement', 'COMPANY', 'system', SYSDATE());
INSERT INTO lucasdb.canvas (name, short_name, canvas_type, created_by_username, created_date_time) 
    VALUES ('Pick Monitoring Canvas', 'PickMonitoring', 'LUCAS','system', SYSDATE());

-- ----------------------------------------------------------------------------
--
--       TABLE: WIDGET
-- DESCRIPTION: Default widgets
--
-- ---------------------------------------------------------------------------- 
INSERT INTO lucasdb.widget
    VALUES ('UserProductivityWidget',1,'{}','{\"anchor\":[275,295],\"minimumWidth\":567,\"minimumHeight\":240, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1}','GRID-UserProductivityWidget','TODO','user-productivity-widget','User Productivity','pick','GRID',NULL,NULL,0,NULL,''),
           ('AssignmentManagementWidget',2,'{}','{\"anchor\":[30,30],\"minimumWidth\":567,\"minimumHeight\":440,\"orientation\":{\"option\":[{\"legend\":\"horizontal\",\"value\":\"h\"},{\"legend\":\"vertical\",\"value\":\"v\"}],\"selected\":\"h\"}, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1}','GRID-AssignmentManagementWidget','TODO','assignment-management-widget','Assignment Management','pick','GRAPH_2D',NULL,NULL,0,NULL,''),
           ('AssignmentCreationWidget',3,'{}','{\"anchor\":[0,863],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1}','GRID-AssignmentCreationWidget','TODO','assignment-creation-widget','Assignment Creation','Alert','GRAPH_2D',NULL,NULL,0,NULL,''),
           ('ProductivityByZoneWidget',5,'{}','{\"anchor\":[30,662],\"minimumWidth\":567,\"minimumHeight\":440, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1}','GRID-ProductivityByZoneWidget','TODO','productivity-by-zone-widget','Productivity By Zone','pick','GRAPH_2D',NULL,NULL,0,NULL,''),
           ('PicklineProgressWidget',6,'{}','{\"anchor\":[0,0],\"minimumWidth\":600,\"minimumHeight\":400, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1}','GRID-PicklineProgressWidget','TODO','pickline-progress-widget','Pickline Progress','pick','GRID',NULL,NULL,0,NULL,''),
           ('AlertWidget',7,'{}','{\"anchor\":[0,\"700\"],\"minimumWidth\":600,\"minimumHeight\":400, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1}','GRID-AlertWidget','TODO','alert-widget','Alert','alert','GRAPH_2D',NULL,NULL,0,NULL,''),
           ('UserCreationFormWidget',8,'{\"widget-access\":{\"create-edit-user-widget-access\":false},\"widget-actions\":{\"create-user\":false,\"edit-user\":false}}','{\"anchor\":[275,295],\"minimumWidth\":567,\"minimumHeight\":240, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1,\"listensForList\": [ \"userName\"],\"isMaximized\":false,\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]}}','GRID-UserCreationFormWidget','TODO','create-or-edit-user-form-widget','Create Or Edit User','CreateUser','FORM',NULL,'getUserCreationFormWidgetDefinition',1,NULL,'Administration'),
		       ('SearchUserGridWidget',9,'{\"widget-access\":{\"search-user-widget-access\":false},\"widget-actions\":{\"disable-user\":false,\"delete-user\":false,\"enable-user\":false,\"retrain-voice-model\":false}}','{\"anchor\":[1,363],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"600\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1,\"listensForList\":[\"userName\"],\"gridColumns\":{\"1\":{\"name\":\"Lucas Login\",\"fieldName\":\"userName\",\"visible\":true,\"allowFilter\":true,\"order\":\"1\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"2\":{\"name\":\"Host Login\",\"fieldName\":\"wmsUser\",\"visible\":false,\"allowFilter\":true,\"order\":\"2\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"3\":{\"name\":\"First Name\",\"fieldName\":\"firstName\",\"visible\":true,\"allowFilter\":true,\"order\":\"3\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"4\":{\"name\":\"Last Name\",\"fieldName\":\"lastName\",\"visible\":true,\"allowFilter\":true,\"order\":\"4\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"5\":{\"name\":\"Skill\",\"fieldName\":\"skill\",\"visible\":true,\"allowFilter\":true,\"order\":\"5\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ENUMERATION\"},\"6\":{\"name\":\"Shifts\",\"fieldName\":\"shift\",\"visible\":true,\"allowFilter\":true,\"order\":\"6\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ENUMERATION\"},\"7\":{\"name\":\"J2U Language\",\"fieldName\":\"j2uLanguage\",\"visible\":true,\"allowFilter\":true,\"order\":\"7\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ENUMERATION\"},\"8\":{\"name\":\"U2J Language\",\"fieldName\":\"u2jLanguage\",\"visible\":true,\"allowFilter\":true,\"order\":\"8\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ENUMERATION\"},\"9\":{\"name\":\"HH Language\",\"fieldName\":\"hhLanguage\",\"visible\":true,\"allowFilter\":true,\"order\":\"9\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ENUMERATION\"},\"10\":{\"name\":\"AMD Language\",\"fieldName\":\"amdLanguage\",\"visible\":true,\"allowFilter\":true,\"order\":\"10\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ENUMERATION\"},\"11\":{\"name\":\"Status\",\"fieldName\":\"enable\",\"visible\":true,\"allowFilter\":true,\"order\":\"11\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"BOOLEAN\"},\"12\":{\"name\":\"Employee Id\",\"fieldName\":\"employeeId\",\"visible\":false,\"allowFilter\":true,\"order\":\"12\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"13\":{\"name\":\"Title\",\"fieldName\":\"title\",\"visible\":false,\"allowFilter\":true,\"order\":\"13\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"14\":{\"name\":\"Start Date\",\"fieldName\":\"startDate\",\"visible\":true,\"allowFilter\":true,\"order\":\"14\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"DATE\"},\"15\":{\"name\":\"Birth Date\",\"fieldName\":\"birthDate\",\"visible\":false,\"allowFilter\":true,\"order\":\"15\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"DATE\"},\"16\":{\"name\":\"Mobile Number\",\"fieldName\":\"mobileNumber\",\"visible\":false,\"allowFilter\":true,\"order\":\"16\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"},\"17\":{\"name\":\"Email Address\",\"fieldName\":\"emailAddress\",\"visible\":true,\"allowFilter\":true,\"order\":\"17\",\"sortOrder\":\"1\", \"width\": 150, \"filterType\":\"ALPHANUMERIC\"}},\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]}, \"autoRefreshConfig\":{\"globalOverride\":false, \"enabled\":true, \"interval\":120}}','GRID-SearchUserGridWidget','TODO','search-user-grid-widget','Search User','SearchUser','GRID',NULL,'getSearchUserGridWidgetDefinition',1,'{\"url\": \"/users/userlist/search\",\"searchCriteria\": {\"pageSize\": \"100\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}','Administration'),
           ('PicklineByWaveBarChartWidget',10,'{\"widget-access\":{\"pickline-by-wave-widget-access\":false},\"widget-actions\":{}}','{\"anchor\": [1,2],\"minimumWidth\": 500,\"minimumHeight\": 300, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\": 1,\"orientation\": {\"option\": [{\"legend\": \"horizontal\",\"value\": \"h\"},{\"legend\": \"vertical\",\"value\": \"v\"}],\"selected\": \"h\"},\"dateFormat\": {\"selectedFormat\": \"mm-dd-yyyy\",\"availableFormats\": [\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]}}','PicklineByWaveBarChartWidget','TODO','pickline-by-wave-barchart-widget','Pickline By Wave Bar chart','PicklineByWave','GRAPH_2D',NULL,'getPicklineByWaveBarChartWidgetDefinition',1,'{\"url\": \"/waves/picklines\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}','Reporting'),
           ('GroupMaintenanceWidget',12,'{\"widget-access\":{\"group-maintenance-widget-access\":false},\"widget-actions\":{\"create-group\":false,\"edit-group\":false}}','{\"minimumHeight\": 500,\"minimumWidth\": 1100,\"anchor\": [2,2], \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\": 1,\"listensForList\": [\"groupName\"]}','FORM-GroupMaintenanceWidget','TODO','group-maintenance-widget','Group Maintenance','GroupMaintenance','FORM',NULL,'getGroupMaintenanceWidgetDefinition',1,'{\"url\": \"/users/groups/permissions\",\"searchCriteria\": {\"pageNumber\": \"0\",\"pageSize\": \"2147483647\",\"searchMap\": {},\"sortMap\": { \"permissionGroupName\": \"ASC\"} } }','Administration'),
           ('SearchProductGridWidget',14,'{\"widget-access\":{\"search-product-grid-widget-access\":false},\"widget-actions\":{\"create-product\":false,\"view-product\":false,\"edit-product\":false,\"delete-product\":false}}','{\"anchor\":[1,363],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"540\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1,\"listensForList\":[\"productName\"],\"gridColumns\" : {\"1\":{\"name\":\"Product Code\",\"fieldName\":\"productCode\",\"visible\":false,\"allowFilter\":true,\"order\":\"1\",\"sortOrder\":\"1\", \"width\": 150},\"2\":{\"name\":\"Product Name\",\"fieldName\":\"name\",\"visible\":true,\"allowFilter\":true,\"order\":\"2\",\"sortOrder\":\"1\", \"width\": 150},\"3\":{\"name\":\"Description\",\"fieldName\":\"description\",\"visible\":true,\"allowFilter\":true,\"order\":\"3\",\"sortOrder\":\"1\", \"width\": 150},\"4\":{\"name\":\"Status\",\"fieldName\":\"productStatusCode\",\"visible\":true,\"allowFilter\":true,\"order\":\"4\",\"sortOrder\":\"1\", \"width\": 150},\"5\":{\"name\":\"2nd Validation\",\"fieldName\":\"performSecondValidation\",\"visible\":true,\"allowFilter\":true,\"order\":\"5\",\"sortOrder\":\"1\", \"width\": 150},\"6\":{\"name\":\"Capture Lot Nbr\",\"fieldName\":\"captureLotNumber\",\"visible\":true,\"allowFilter\":true,\"order\":\"6\",\"sortOrder\":\"1\", \"width\": 150},\"7\":{\"name\":\"Capture Serial Nbr\",\"fieldName\":\"captureSerialNumber\",\"visible\":true,\"allowFilter\":true,\"order\":\"7\",\"sortOrder\":\"1\", \"width\": 150},\"8\":{\"name\":\"Base Item\",\"fieldName\":\"isBaseItem\",\"visible\":true,\"allowFilter\":true,\"order\":\"8\",\"sortOrder\":\"1\", \"width\": 150},\"9\":{\"name\":\"Capture Catch Weight\",\"fieldName\":\"captureCatchWeight\",\"visible\":true,\"allowFilter\":true,\"order\":\"9\",\"sortOrder\":\"1\", \"width\": 150},\"10\":{\"name\":\"HeadsUp Messages\",\"fieldName\":\"headsUpMessages\",\"visible\":true,\"allowFilter\":true,\"order\":\"10\",\"sortOrder\":\"1\", \"width\": 150},\"11\":{\"name\":\"Haz Material\",\"fieldName\":\"hazardousMaterial\",\"visible\":true,\"allowFilter\":true,\"order\":\"11\",\"sortOrder\":\"1\", \"width\": 150},\"12\":{\"name\":\"Customer SKU Number\",\"fieldName\":\"customerSkuNbr\",\"visible\":true,\"allowFilter\":true,\"order\":\"12\",\"sortOrder\":\"1\", \"width\": 150},\"13\":{\"name\":\"Manufacturer Item Number\",\"fieldName\":\"manufacturerItemNbr\",\"visible\":true,\"allowFilter\":true,\"order\":\"13\",\"sortOrder\":\"1\", \"width\": 150},\"14\":{\"name\":\"Expiration Date Check\",\"fieldName\":\"captureExpirationDate\",\"visible\":true,\"allowFilter\":true,\"order\":\"14\",\"sortOrder\":\"1\", \"width\": 150}}}','GRID-SearchProductGridWidget','TODO','search-product-grid-widget','Search Product','SearchProduct','GRID',NULL,'getSearchProductGridWidgetDefinition',1,'{\"url\": \"/products/productlist/search\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}','Work Execution'),
           ('SearchGroupGridWidget',15,'{"widget-access":{"search-group-widget-access":false},"widget-actions":{"delete-group":false}}','{"anchor":[275,295],"minimumWidth":567,"minimumHeight":240, "deviceWidths":{"320":"mobile", "600":"tablet", "800":"desktop", "1200":"wideScreen"},"zindex":1,"listensForList":["groupName"],"gridColumns":{"0": {"name": "Group Name","fieldName":"groupName","order":"1","sortOrder": "1","visible": true,"allowFilter": true, "filterType": "ALPHANUMERIC"},"1": { "name": "Description","fieldName": "description","order": "2","sortOrder": "0","visible": true,"allowFilter": true, "filterType": "ALPHANUMERIC"},"2": {"name": "User Count","fieldName": "userCount","order": "3","sortOrder": "0","visible": true,"allowFilter": true, "filterType": "NUMERIC"}},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"autoRefreshConfig":{"globalOverride":false, "enabled":true, "interval":120}}','GRID-SearchGroupGridWidget','TODO','search-group-grid-widget','Search group','SearchGroup','GRID',NULL,	'getSearchGroupGridWidgetDefinition',1,'{"url": "/groups/grouplist/search","searchCriteria": {"pageSize": "10","pageNumber": "0","searchMap": {}, "sortMap": {}}}','Administration'),
           ('EquipmentTypeGridWidget' ,16 ,'{\"widget-access\":{\"equipment-type-widget-access\":false} ,\"widget-actions\":{\"create-equipment-type\":false,\"edit-equipment-type\":false,\"delete-equipment-type\":false} }' ,'{\"anchor\":[1,363],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"600\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"} ,\"zindex\":1 ,\"listensForList\":[\"equipmentType\"] ,\"gridColumns\":{ \"1\":{\"name\":\"Equipment Style\",\"fieldName\":\"equipmentStyle.equipmentStyleName\",\"visible\":true,\"allowFilter\":true,\"order\":\"1\",\"sortOrder\":\"1\", \"width\": 150} ,\"2\":{\"name\":\"Equipment Type\",\"fieldName\":\"equipmentTypeName\",\"visible\":false,\"allowFilter\":true,\"order\":\"2\",\"sortOrder\":\"1\", \"width\": 150} ,\"3\":{\"name\":\"Description\",\"fieldName\":\"equipmentTypeDescription\",\"visible\":true,\"allowFilter\":true,\"order\":\"3\",\"sortOrder\":\"1\", \"width\": 150} ,\"4\":{\"name\":\"Requires Check List\",\"fieldName\":\"requiresCheckList\",\"visible\":true,\"allowFilter\":true,\"order\":\"4\",\"sortOrder\":\"1\", \"width\": 150} ,\"5\":{\"name\":\"Shipping Container\",\"fieldName\":\"shippingContainer\",\"visible\":true,\"allowFilter\":true,\"order\":\"5\",\"sortOrder\":\"1\", \"width\": 150} ,\"6\":{\"name\":\"Requires Certification\",\"fieldName\":\"requiresCertification\",\"visible\":true,\"allowFilter\":true,\"order\":\"6\",\"sortOrder\":\"1\", \"width\": 150} ,\"7\":{\"name\":\"Total Positions\",\"fieldName\":\"equipmentTypePositions\",\"visible\":true,\"allowFilter\":true,\"order\":\"7\",\"sortOrder\":\"1\", \"width\": 150} ,\"8\":{\"name\":\"Count\",\"fieldName\":\"equipmentCount\",\"visible\":true,\"allowFilter\":true,\"order\":\"8\",\"sortOrder\":\"1\", \"width\": 150} ,\"9\":{\"name\":\"Created\",\"fieldName\":\"createdDateTime\",\"visible\":true,\"allowFilter\":true,\"order\":\"9\",\"sortOrder\":\"1\", \"width\": 150} ,\"10\":{\"name\":\"Updated\",\"fieldName\":\"updatedDateTime\",\"visible\":true,\"allowFilter\":true,\"order\":\"10\",\"sortOrder\":\"1\", \"width\": 150}} ,\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]} , \"autoRefreshConfig\":{\"globalOverride\":false, \"enabled\":true, \"interval\":120}}' ,'GRID-EquipmentTypeGridWidget' ,'TODO' ,'equipment-type-grid-widget' ,'Equipment Type' ,'EquipmentType' ,'GRID' ,NULL ,'getEquipmentTypeGridWidgetDefinition' ,1 ,'{\"url\": \"equipments/equipment-type-list/search\",\"searchCriteria\": {\"pageSize\": \"100\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}' ,'Work Execution'),
 			('EquipmentManagerGridWidget' ,17 ,'{\"widget-access\":{\"equipment-management-widget-access\":false} ,\"widget-actions\":{\"create-equipment\":false,\"edit-equipment\":false,\"delete-equipment\":false,\"unassign-user\":false,\"view-checklist\":false} }' ,'{\"anchor\":[1,363],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"600\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1 ,\"listensForList\":[\"equipmentType\"],\"gridColumns\":{\"1\":{\"name\":\"Equipment ID\",\"fieldName\":\"equipmentName\",\"visible\":true,\"allowFilter\":true,\"order\":\"1\",\"sortOrder\":\"1\", \"width\": 150},\"2\":{\"name\":\"Description\",\"fieldName\":\"equipmentDescription\",\"visible\":true,\"allowFilter\":true,\"order\":\"2\",\"sortOrder\":\"1\", \"width\": 150} ,\"3\":{\"name\":\"Equipment Type\",\"fieldName\":\"equipmentType.equipmentTypeName\",\"visible\":false,\"allowFilter\":true,\"order\":\"3\",\"sortOrder\":\"1\", \"width\": 150},\"4\":{\"name\":\"Total Positions\",\"fieldName\":\"equipmentPositions\",\"visible\":true,\"allowFilter\":true,\"order\":\"4\",\"sortOrder\":\"1\", \"width\": 150},\"5\":{\"name\":\"Current User Name\",\"fieldName\":\"user.userName\",\"visible\":true,\"allowFilter\":true,\"order\":\"5\",\"sortOrder\":\"1\", \"width\": 150},\"6\":{\"name\":\"First Name\",\"fieldName\":\"user.firstName\",\"visible\":true,\"allowFilter\":true,\"order\":\"6\",\"sortOrder\":\"1\", \"width\": 150},\"7\":{\"name\":\"Last Name\",\"fieldName\":\"user.lastName\",\"visible\":true,\"allowFilter\":true,\"order\":\"7\",\"sortOrder\":\"1\", \"width\": 150},\"8\":{\"name\":\"Current Assignment\",\"fieldName\":\"currentAssignmentId\",\"visible\":true,\"allowFilter\":true,\"order\":\"8\",\"sortOrder\":\"1\", \"width\": 150},\"9\":{\"name\":\"Equipment Style\",\"fieldName\":\"equipmentStyle.equipmentStyleName\",\"visible\":true,\"allowFilter\":true,\"order\":\"9\",\"sortOrder\":\"1\", \"width\": 150},\"10\":{\"name\":\"Status\",\"fieldName\":\"equipmentStatus\",\"visible\":true,\"allowFilter\":true,\"order\":\"10\",\"sortOrder\":\"1\", \"width\": 150},\"11\":{\"name\":\"Created\",\"fieldName\":\"createdDateTime\",\"visible\":true,\"allowFilter\":true,\"order\":\"11\",\"sortOrder\":\"1\", \"width\": 150},\"12\":{\"name\":\"Updated\",\"fieldName\":\"createdDateTime\",\"visible\":true,\"allowFilter\":true,\"order\":\"12\",\"sortOrder\":\"1\", \"width\": 150}} ,\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]} , \"autoRefreshConfig\":{\"globalOverride\":false, \"enabled\":true, \"interval\":120}}' ,'GRID-EquipmentManagerGridWidget' ,'TODO' ,'equipment-manager-grid-widget' ,'Equipment Manager' ,'EquipmentManager' ,'GRID' ,NULL ,'getEquipmentManagerGridWidgetDefinition' ,1 ,'{\"url\": \"equipments/equipment-manager-list/search\",\"searchCriteria\": {\"pageSize\": \"100\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}' ,'Work Execution'), 
 			('MessageSegmentGridWidget',18,'{\"widget-access\":{\"message-segments-widget-access\":false},\"widget-actions\":{\"create-message-segment\":false,\"edit-message-segment\":false,\"delete-message-segment\":false}}','{\"minimumWidth\":320,\"minimumHeight\":300,\"gridColumns\":{\"1\":{\"name\":\"SegmentName\",\"fieldName\":\"segmentName\",\"allowFilter\":true,\"visible\":true,\"order\":\"1\",\"width\":150,\"sortOrder\":\"1\"},\"2\":{\"name\":\"SegmentDescription\",\"fieldName\":\"segmentDescription\",\"allowFilter\":true,\"visible\":true,\"order\":\"2\",\"width\":150,\"sortOrder\":\"1\"},\"3\":{\"name\":\"Length\",\"fieldName\":\"length\",\"allowFilter\":true,\"visible\":true,\"order\":\"3\",\"width\":150,\"sortOrder\":\"1\"}},\"listensForList\":[\"userName\"],\"deviceWidths\":{\"320\":\"mobile\",\"600\":\"tablet\",\"800\":\"desktop\",\"1200\":\"wideScreen\"},\"autoRefreshConfig\":{\"interval\":120,\"enabled\":true,\"globalOverride\":false},\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMMdd,yyyy\",\"dd-mm-yyyy\"]},\"anchor\":[1,363],\"zindex\":1}','GRID-MessageSegmentGridWidget','TODO','message-segment-grid-widget','MessageSegments','MessageSegment','GRID',NULL,'getEaiMessageSegmentGridWidgetDefinition',1,'{\"url\":\"/segments/segmentlist/search\",\"searchCriteria\":{\"pageNumber\":\"0\",\"pageSize\":\"100\",\"searchMap\":{},\"sortMap\":{}}}','EAI'),
		   ('EventGridWidget',19,'{\"widget-access\":{\"events-widget-access\":false},\"widget-actions\":{\"create-event\":false,\"edit-event\":false,\"delete-event\":false}}','{\"anchor\":[1,363],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"600\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1,\"gridColumns\":{\"1\":{\"name\":\"Event Name\",\"fieldName\":\"eventName\",\"visible\":true,\"allowFilter\":true,\"order\":\"1\",\"sortOrder\":\"1\", \"width\": 150},\"2\":{\"name\":\"Description\",\"fieldName\":\"eventDescription\",\"visible\":false,\"allowFilter\":true,\"order\":\"2\",\"sortOrder\":\"1\", \"width\": 150},\"3\":{\"name\":\"Type\",\"fieldName\":\"eventType\",\"visible\":true,\"allowFilter\":true,\"order\":\"3\",\"sortOrder\":\"1\", \"width\": 150},\"4\":{\"name\":\"Source\",\"fieldName\":\"eventSource\",\"visible\":true,\"allowFilter\":true,\"order\":\"4\",\"sortOrder\":\"1\", \"width\": 150},\"5\":{\"name\":\"Category\",\"fieldName\":\"eventCategory\",\"visible\":true,\"allowFilter\":true,\"order\":\"5\",\"sortOrder\":\"1\", \"width\": 150},\"6\":{\"name\":\"Sub-Category\",\"fieldName\":\"eventSubCategory\",\"visible\":true,\"allowFilter\":true,\"order\":\"6\",\"sortOrder\":\"1\", \"width\": 150}},\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]}, \"autoRefreshConfig\":{\"globalOverride\":false, \"enabled\":true, \"interval\":120}}','GRID-EventGridWidget','TODO','event-grid-widget','Events','Event','GRID',NULL,'getEventGridWidgetDefinition',1,'{\"url\": \"/events/eventlist/search\",\"searchCriteria\": {\"pageSize\": \"100\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}','EAI'),
		   ('MessageGridWidget' ,20 ,'{\"widget-access\":{\"messages-widget-access\":false} ,\"widget-actions\":{\"create-message\":false,\"edit-message\":false,\"delete-message\":false,\"unassign-user\":false,\"view-checklist\":false} }' ,'{\"anchor\":[1,363],\"minimumWidth\":485,\"minimumHeight\":375, \"deviceWidths\":{\"320\":\"mobile\", \"600\":\"tablet\", \"800\":\"desktop\", \"1200\":"wideScreen"},\"zindex\":1 ,\"listensForList\":[\"messageName\"],\"gridColumns\":{\"1\":{\"name\":\"Message Name\",\"fieldName\":\"messageName\",\"visible\":true,\"allowFilter\":true,\"order\":\"1\",\"sortOrder\":\"1\", \"width\": 150},\"2\":{\"name\":\"Message Description\",\"fieldName\":\"messageDescription\",\"visible\":true,\"allowFilter\":true,\"order\":\"2\",\"sortOrder\":\"1\", \"width\": 150} ,\"3\":{\"name\":\"Message Format\",\"fieldName\":\"messageFormat\",\"visible\":false,\"allowFilter\":true,\"order\":\"3\",\"sortOrder\":\"1\", \"width\": 150},\"4\":{\"name\":\"Lucas Pre-defined?\",\"fieldName\":\"lucasPredefined\",\"visible\":true,\"allowFilter\":true,\"order\":\"4\",\"sortOrder\":\"1\", \"width\": 150},\"5\":{\"name\":\"Lucas Predefined Service\",\"fieldName\":\"lucasPredefinedService\",\"visible\":true,\"allowFilter\":true,\"order\":\"5\",\"sortOrder\":\"1\", \"width\": 150}} ,\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMM dd, yyyy\",\"dd-mm-yyyy\"]} , \"autoRefreshConfig\":{\"globalOverride\":false, \"enabled\":true, \"interval\":120}}' ,'GRID-MessageGridWidget' ,'TODO' ,'message-grid-widget' ,'Messages' ,'Message' ,'GRID' ,NULL ,'getMessageGridWidgetDefinition' ,1 ,'{\"url\": \"/messages/messagelist/search\",\"searchCriteria\": {\"pageSize\": \"100\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}' ,'EAI'),
		   ('MessageMappingGridWidget',21,'{\"widget-access\":{\"message-mappings-widget-access\":false},\"widget-actions\":{\"delete-message-mapping\":false}}','{\"minimumWidth\":320,\"minimumHeight\":300,\"gridColumns\":{\"1\":{\"name\":\"Mapping Name\",\"fieldName\":\"mappingName\",\"allowFilter\":true,\"visible\":true,\"order\":\"1\",\"width\":150,\"sortOrder\":\"1\"},\"2\":{\"name\":\"Message Description\",\"fieldName\":\"mappingDiscription\",\"allowFilter\":true,\"visible\":true,\"order\":\"2\",\"width\":150,\"sortOrder\":\"1\"},\"3\":{\"name\":\"Sorce Message\",\"fieldName\":\"sourceMessage.name\",\"allowFilter\":true,\"visible\":true,\"order\":\"3\",\"width\":150,\"sortOrder\":\"1\"},\"4\":{\"name\":\"Destination Message\",\"fieldName\":\"destinationMessage.name\",\"allowFilter\":true,\"visible\":true,\"order\":\"3\",\"width\":150,\"sortOrder\":\"1\"}},\"listensForList\":[\"userName\"],\"deviceWidths\":{\"320\":\"mobile\",\"600\":\"tablet\",\"800\":\"desktop\",\"1200\":\"wideScreen\"},\"autoRefreshConfig\":{\"interval\":120,\"enabled\":true,\"globalOverride\":false},\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMMdd,yyyy\",\"dd-mm-yyyy\"]},\"anchor\":[1,363],\"zindex\":1}','GRID-MessageMappingGridWidget','TODO','message-mappings-grid-widget','Message Mappings','MessageMapping','GRID',NULL,'getMessageMappingGridWidgetDefinition',1,'{\"url\":\"/mappings/mappinglist/search",\"searchCriteria\":{\"pageNumber\":\"0\",\"pageSize\":\"100\",\"searchMap\":{},\"sortMap\":{}}}','EAI'),
 			('EventHandlerGridWidget',22,'{\"widget-access\":{\"event-handlers-widget-access\":false},\"widget-actions\":{\"create-event-handler\":false,\"edit-event-handler\":false,\"delete-event-handler\":false}}','{\"minimumWidth\":320,\"minimumHeight\":300,\"gridColumns\":{\"1\":{\"name\":\"Event Handler Name\",\"fieldName\":\"eventHandlerName\",\"allowFilter\":true,\"visible\":true,\"order\":\"1\",\"width\":150,\"sortOrder\":\"1\"},\"2\":{\"name\":\"Description\",\"fieldName\":\"eventHandlerDescription\",\"allowFilter\":true,\"visible\":true,\"order\":\"2\",\"width\":150,\"sortOrder\":\"1\"},\"3\":{\"name\":\"Type\",\"fieldName\":\"eventHandlerType\",\"allowFilter\":true,\"visible\":true,\"order\":\"3\",\"width\":150,\"sortOrder\":\"1\"},\"4\":{\"name\":\"Inbound Mapping\",\"fieldName\":\"inboundMapping.mappingName\",\"allowFilter\":true,\"visible\":true,\"order\":\"4\",\"width\":150,\"sortOrder\":\"1\"},\"5\":{\"name\":\"Outbound Mapping\",\"fieldName\":\"outboundMapping.mappingName\",\"allowFilter\":true,\"visible\":true,\"order\":\"5\",\"width\":150,\"sortOrder\":\"1\"}},\"listensForList\":[\"eventHandlerName\"],\"deviceWidths\":{\"320\":\"mobile\",\"600\":\"tablet\",\"800\":\"desktop\",\"1200\":\"wideScreen\"},\"autoRefreshConfig\":{\"interval\":120,\"enabled\":true,\"globalOverride\":false},\"dateFormat\":{\"selectedFormat\":\"mm-dd-yyyy\",\"availableFormats\":[\"mm-dd-yyyy\",\"MMMdd,yyyy\",\"dd-mm-yyyy\"]},\"anchor\":[1,363],\"zindex\":1}','GRID-EventHandlerGridWidget','TODO','event-handler-grid-widget','EventHandlers','EventHandler','GRID',NULL,'getEventHandlerGridWidgetDefinition',1,'{\"url\":\"/eventhandlers/eventhandlerlist/search\",\"searchCriteria\":{\"pageNumber\":\"0\",\"pageSize\":\"100\",\"searchMap\":{},\"sortMap\":{}}}','EAI');
-- ----------------------------------------------------------------------------
--
--       TABLE: CANVAS_WIDGETINSTANCELIST
-- DESCRIPTION: Associates canvases with widgets
--
-- ---------------------------------------------------------------------------- 
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Perishable Goods Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'UserProductivityWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":1}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Perishable Goods Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'AssignmentManagementWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":2}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Morning Shift Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'AssignmentCreationWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":1}');
-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create default products custom classification
--
-- ---------------------------------------------------------------------------     
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time)
 VALUES ('custom_classification_1', null, null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time)
VALUES ('custom_classification_2', null, null, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time)
VALUES ('custom_classification_3', null, null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_4',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_5',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_6',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_7',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_8',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_9',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
INSERT INTO `product_custom_classification` (custom_classification_field_name, custom_classification_name, custom_classification_definition, created_by_username, created_date_time, updated_by_username, updated_date_time) 
VALUES ('custom_classification_10',null,null,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP); 

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Application wide lookup codes
--
-- ---------------------------------------------------------------------------
INSERT INTO `code_lookup` (code_name, code_value, display_order, updated_by, updated_timestamp)
VALUES
('USER_STATUS', '0', '10', 'system', CURRENT_TIMESTAMP),
('USER_STATUS', '1', '20', 'system', CURRENT_TIMESTAMP),
('USER_SKILL', 'STANDARD', '10', 'system', CURRENT_TIMESTAMP),
('USER_SKILL', 'ADVANCED', '20', 'system', CURRENT_TIMESTAMP),
('DATE_FORMAT', 'MM-DD-YYYY', '10', 'system', CURRENT_TIMESTAMP),
('DATE_FORMAT', 'DD-MM-YYYY', '20', 'system', CURRENT_TIMESTAMP),
('DATE_FORMAT', 'YYYY-MM-DD', '30', 'system', CURRENT_TIMESTAMP),
('TIME_FORMAT', '12HR', '10', 'system', CURRENT_TIMESTAMP),
('TIME_FORMAT', '24HR', '20', 'system',CURRENT_TIMESTAMP),
('FILTER_COMMON', 'EMPTY', '10', 'system', CURRENT_TIMESTAMP),
('FILTER_COMMON', 'NON_EMPTY', '20', 'system', CURRENT_TIMESTAMP),
('FILTER_DATE', 'TODAY', '10', 'system', CURRENT_TIMESTAMP),
('FILTER_DATE', 'YESTERDAY', '20', 'system', CURRENT_TIMESTAMP),
('FILTER_DATE', 'THIS_WEEK', '30', 'system', CURRENT_TIMESTAMP),
('FILTER_DATE', 'LAST_WEEK', '40', 'system', CURRENT_TIMESTAMP),
('FILTER_DATE', 'THIS_MONTH', '50', 'system', CURRENT_TIMESTAMP),
('FILTER_DATE', 'LAST_MONTH', '60', 'system', CURRENT_TIMESTAMP),
('EAI_EVENT_TYPES', 'Inbound', '1', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_TYPES', 'Outbound', '2', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_SOURCE_TYPES', 'AMD', '1', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_SOURCE_TYPES', 'Host', '2', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_SOURCE_TYPES', 'Mobile App', '3', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_EXECUTION_ORDER', 'Sequential', '1', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_EXECUTION_ORDER', 'Parallel', '2', 'system', CURRENT_TIMESTAMP ),
('EAI_TRANSPORT_TYPES', 'FTP Inbound', '1', 'system', CURRENT_TIMESTAMP ),
('EAI_TRANSPORT_TYPES', 'FTP Outbound', '2', 'system', CURRENT_TIMESTAMP ),
('EAI_TRANSPORT_TYPES', 'Inbound File', '3', 'system', CURRENT_TIMESTAMP ),
('EAI_TRANSPORT_TYPES', 'Outbound File', '4', 'system', CURRENT_TIMESTAMP ),
('EAI_TRANSPORT_TYPES', 'Socket', '5', 'system', CURRENT_TIMESTAMP ),
('EAI_TRANSPORT_TYPES', 'Webservice', '6', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_HANDLER_TYPES', 'Inbound Asynchronous', '1', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_HANDLER_TYPES', 'Inbound Synchronous', '2', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_HANDLER_TYPES', 'Outbound Asynchronous', '3', 'system', CURRENT_TIMESTAMP ),
('EAI_EVENT_HANDLER_TYPES', 'Outbound Synchronous', '4', 'system', CURRENT_TIMESTAMP );


-- DESCRIPTION: EAI_EVENT
--
-- ---------------------------------------------------------------------------     

INSERT INTO `lucasdb`.`eai_event` (`event_category`, `event_description`, `event_name`, `event_source`, `event_sub_category`, `event_type`) VALUES ('Master data', 'Import user details to the lucas system', 'Users data import', 'Host', 'Users Import', 'Inbound');
INSERT INTO `lucasdb`.`eai_event` (`event_category`, `event_description`, `event_name`, `event_source`, `event_sub_category`, `event_type`) VALUES ('Voice flow', 'User logs on to mobile device', 'Mobile device login', 'Mobile device', 'User login', 'Outbound');

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MESSAGE
--
-- ---------------------------------------------------------------------------     

INSERT INTO `lucasdb`.`eai_message` (`lucas_predefined`,`lucas_predefined_service`, `message_description`, `message_format`, `message_name`, `usage_in_events`) VALUES (1,'usersImportInboundLucasServiceChannel', 'User\'s import inbound lucas message', 'LucasPredefined', 'Lucas user import lucas', '1');
INSERT INTO `lucasdb`.`eai_message` (`lucas_predefined`, `message_description`, `message_format`, `message_name`, `usage_in_events`) VALUES (1, 'User\'s export outbound lucas message', 'LucasPredefined', 'Lucas user export lucas', '1');

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MESSAGE_DEFINITION
--
-- ---------------------------------------------------------------------------     

INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s username', '100', 'userName', '1', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s first name ', '100', 'firstName', '2', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s last name', '100', 'lastName', '3', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s hashed password', '500', 'hashedPassword', '4', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s email id', '100', 'emailAddress', '5', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s mobile number', '45', 'mobileNumber', '6', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s title', '45', 'title', '7', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s employee number', '100', 'employeeNumber', '8', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of start', '23', 'startDate', '9', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of birth', '23', 'birthDate', '10', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s shift id', '20', 'shift', '11', 'Long', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s skill', '50', 'skill', '12', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Jennifer to user language', '10', 'j2uLanguage', '13', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User to Jennifer language', '10', 'u2jLanguage', '14', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Hand held device language for user', '10', 'hhLanguage', '15', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Amd language for user', '10', 'amdLanguage', '16', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Is user enable or disable', '1', 'enable', '17', 'Boolean', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import lucas'));

INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s username', '100', 'userName', '1', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s first name ', '100', 'firstName', '2', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s last name', '100', 'lastName', '3', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s hashed password', '500', 'hashedPassword', '4', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s email id', '100', 'emailAddress', '5', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s mobile number', '45', 'mobileNumber', '6', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s title', '45', 'title', '7', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s employee number', '100', 'employeeNumber', '8', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of start', '23', 'startDate', '9', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of birth', '23', 'birthDate', '10', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s shift id', '20', 'shift', '11', 'Long', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s skill', '50', 'skill', '12', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Jennifer to user language', '10', 'j2uLanguage', '13', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User to Jennifer language', '10', 'u2jLanguage', '14', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Hand held device language for user', '10', 'hhLanguage', '15', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Amd language for user', '10', 'amdLanguage', '16', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Is user enable or disable', '1', 'enable', '17', 'Boolean', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export lucas'));

-- ===========================================================================
-- -------------------- EQUIPMENT MODULE RELATED SEED DATA -------------------
-- ===========================================================================

-- ---------------------------------------------------------------------------
--       TABLE: container_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

INSERT INTO `container_type`(`container_code`,`container_description`,`length`,`width`,`height`,`color`,`material`)
VALUES('Pallet','Pallet',null,null,null,null,null)
,('Carton','Carton',null,null,null,null,null)
,('Case','Shipping Container - Case',null,null,null,null,null)
,('Tote','Shipping Container - Tote',null,null,null,null,null)
,('EquipmentSlot','Slot Location',null,null,null,null,null);

-- ---------------------------------------------------------------------------
--       TABLE: question_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

INSERT INTO `question_type`(`question_type_id`,`question_type_name` ,`question_type_description`)
VALUES(1,'EquipCheck','EquipmentCheck')
,(2,'TrailerCheck','TrailerCheck');

-- ---------------------------------------------------------------------------
--       TABLE: answer_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

INSERT INTO `answer_type`(`answer_type_id`,`answer_type_name` ,`answer_type_description`)
VALUES(1,'Confirm','Confirm/Cancel')
 ,(2,'Digit','Digits')
,(3,'Character','Characters')
,(4,'Alphanum','Alphanumeric Values');

-- ---------------------------------------------------------------------------
--       TABLE: question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

INSERT INTO `question`(`question_id`,`question_type_id`,`answer_type_id`,`question_description`,`question_prompt`,`question_friendly_prompt`,`question_help`)
VALUES
 (1,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),	(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Tires','do_the_tires_wheels_and_casters_look_ok','Do the tires wheels and casters look Ok','say_yes_or_no')
,(2,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Headlights','do_the_headlights_work','Do the headlights work','say_yes_or_no')
,(3,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'WarningLights','do_the_warning_lights_work	','Do the warning lights work','say_yes_or_no')
,(4,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'SafetyLights','do_the_safety_lights_work','Do the safety lights work','say_yes_or_no')
,(5,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Digit'),	'HourMeter','enter_hour_meter_reading','Enter hour meter reading','enter_3_to_5_digit_hour_meter_reading')
,(7,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),  'Gauges','do_the_gauges_and_instruments_look_ok','Do the gauges and instruments look Ok','say_yes_or_no')
,(8,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Blades','do_the_forkliftblades_look_ok','Do the forklift blades look Ok','say_yes_or_no')
,(9,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'BackRest','is_the_backrest_loaded','Is the back rest loaded','say_yes_or_no')
,(10,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Handles','do_the_handles_buttons_and_knobs_look_ok','Do the handles buttons and knobs look Ok','say_yes_or_no')
,(11,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Horn','does_the_horn_work','Does the horn work','say_yes_or_no')
,(12,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Steering','does_the_steering_work','Does the steering work','say_yes_or_no')
,(14,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'ServiceBrakes','do_the_service_brakes_work','Do the service brakes work','say_yes_or_no')
,(15,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'ParkingBrakes','do_the_parking_brakes_work','Do the parking brakes work','say_yes_or_no')
,(16,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Hydraulic','do_the_hydraulic_controls_work','Do the hydraulic controls work','say_yes_or_no')
,(17,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Chains','do_the_chains_hoses_and_belts_work','Do the chains, hoses and belts work','say_yes_or_no')
,(18,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'BackupAlarm','does_the_backup_alarm_work','Does the backup alarm work','say_yes_or_no')
,(19,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'Battery','is_the_battery_charge_and_level_sufficient','Is the battery charge and water level sufficient','say_yes_or_no')
,(20,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'SafetyGlasses','are_you_wearing_safety_glasses','Are you wearing safety glasses','say_yes_or_no')
,(21,(SELECT question_type_id FROM question_type where question_type_name ='EquipCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),	'OddNoises','are_there_any_odd_noises','Are there any odd noises','say_yes_or_no')
,(29,(SELECT question_type_id FROM question_type where question_type_name ='TrailerCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Confirm'),'TruckPreCooled','IsTruckPreCooledQ','Is the truck pre cooled','say_yes_or_no')
,(30,(SELECT question_type_id FROM question_type where question_type_name ='TrailerCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Digit'),	'FrozenProductTemp','EnterFrozenProductTemperature','Enter frozen product temperature','enter_1_to_2_digit_temperature')
,(31,(SELECT question_type_id FROM question_type where question_type_name ='TrailerCheck'),(SELECT answer_type_id FROM answer_type where answer_type_name='Digit'),	'TruckWheelsChocked','AreTheTruckWheelsChockedQ','Are the truck wheels chocked','say_yes_or_no');

-- ---------------------------------------------------------------------------
--       TABLE: equipment_style
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

INSERT INTO `equipment_style`(`equipment_style_code`,`equipment_style_name`)
VALUES('generic','Generic')
,('pallet_jack','Pallet Jack')
,('fork_lift','ForkLift')
,('one_sided_cart','One Sided Cart')
,('man_up','ManUp')
,('two_sided_cart','Two Sided Cart');
-- =============================================================================
