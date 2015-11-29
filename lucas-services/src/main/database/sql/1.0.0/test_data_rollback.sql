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

-- Remove regions
DELETE 
  FROM region 
 WHERE 1 = 1;
 
-- Remove warehouses
DELETE
  FROM warehouses
 WHERE 1 = 1;
 
-- Remove test users
DELETE 
  FROM lucas_user 
 WHERE username <> 'system';

-- Remove test user group assignments
DELETE 
  FROM lucas_user_permission_group
 WHERE user_id not in (SELECT user_id 
                         FROM lucas_user 
                        WHERE username = 'system');
 
-- Remove Waves and Picklines
DELETE
  FROM wave
 WHERE 1 = 1;

DELETE
  FROM pickline
 WHERE 1 = 1;

-- Remove location alerts
DELETE
  FROM lucas_alert_locations
 WHERE 1 = 1;
 
-- Remove products
DELETE
  FROM lucas_product
 WHERE 1 = 1;
 
-- Remove special instructions
DELETE
  FROM lucas_special_instructions
 WHERE 1 = 1;

-- Remove canvas layouts
DELETE
  FROM canvas_layout
 WHERE 1 = 1;
 
-- Remove shifts
DELETE
  FROM shift
 WHERE 1 = 1;
                      
-- Remove canvas
DELETE
  FROM canvas
 WHERE 1 = 1;

  -- Remove data from eai event event handler table
 TRUNCATE `lucasdb`.`eai_event_event_handler`;

-- Remove data from eai event table
 TRUNCATE `lucasdb`.`eai_event`;

  -- Remove data from eai transport table
 TRUNCATE `lucasdb`.`eai_transport`;

 -- Remove data from eai event handler table
 TRUNCATE `lucasdb`.`eai_event_handler`;

 -- Remove data from eai mapping path table
 TRUNCATE `lucasdb`.`eai_mapping_path`;
 
 -- Remove data from eai mapping definition table
 TRUNCATE `lucasdb`.`eai_mapping_definition`;

 -- Remove data from eai mapping table
 TRUNCATE `lucasdb`.`eai_mapping`;
 
  -- Remove data from eai joiner condition table
 TRUNCATE `lucasdb`.`eai_joiner_condition`;
 
 -- Remove data from eai filter condition table
 TRUNCATE `lucasdb`.`eai_filter_condition`;

 -- Remove data from eai transformation definition table
 TRUNCATE `lucasdb`.`eai_transformation_definition`;
 
 -- Remove data from eai transformation table
 TRUNCATE `lucasdb`.`eai_transformation`;
   
 -- Remove data from eai segment definition table
 TRUNCATE `lucasdb`.`eai_segment_definition`;
 
 -- Remove data from eai segments table
 TRUNCATE `lucasdb`.`eai_segments`;

 -- Remove data from eai message definition table
 TRUNCATE `lucasdb`.`eai_message_definition`;
 
 -- Remove data from eai message table
 TRUNCATE `lucasdb`.`eai_message`;
 
  -- Remove data from open user canvas table
 TRUNCATE `lucasdb`.`open_user_canvas`;
 
   -- Remove data from lucas_building
 TRUNCATE `lucasdb`.`lucas_building`;
 
    -- Remove data from lucas_device
 TRUNCATE `lucasdb`.`lucas_device`;
 
    -- Remove data from lucas_mobile_app_message_option
 TRUNCATE `lucasdb`.`lucas_mobile_app_message_option`;