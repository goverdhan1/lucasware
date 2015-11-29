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
-- REMOVE SEED DATA
--
DELETE 
  FROM supported_language
  WHERE 1 = 1;

DELETE 
  FROM permission
  WHERE 1 = 1;

DELETE 
  FROM permission_group
  WHERE 1 = 1;
  
DELETE 
  FROM permission_group_permission
  WHERE 1 = 1;
  
DELETE 
  FROM lucas_user
  WHERE 1 = 1;
  
DELETE 
  FROM lucas_user_permission_group
  WHERE 1 = 1;  

DELETE 
  FROM canvas
  WHERE 1 = 1;   
  
DELETE 
  FROM widget
  WHERE 1 = 1;    
  
DELETE 
  FROM canvas_widgetinstancelist
  WHERE 1 = 1;
  
-- Remove product custom classification
DELETE
  FROM product_custom_classification
 WHERE 1 = 1;

DELETE
  FROM code_lookup
 WHERE 1 = 1;

 -- Remove data from eai event table
 TRUNCATE `lucasdb`.`eai_event`;
 
 -- Remove data from eai message table
 TRUNCATE `lucasdb`.`eai_message`;
 
 -- Remove data from eai message definition table
 TRUNCATE `lucasdb`.`eai_message_definition`;
 
