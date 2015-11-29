
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
-- DESCRIPTION: Create a few sample Regions
--
-- ---------------------------------------------------------------------------
INSERT INTO region (region_name)
  VALUES ('Delaware'),
         ('Chicago');

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create a warehouse for each Region
--
-- ---------------------------------------------------------------------------
INSERT INTO warehouse (warehouse_name, region_id)
  VALUES ('Best Buy', (SELECT region_id FROM region WHERE region_name = 'Delaware')),
         ('Target', (SELECT region_id FROM region WHERE region_name = 'Chicago'));

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create a bunch of dummy users
--
-- ---------------------------------------------------------------------------
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`,`start_date`, created_by_username)
    VALUES ('admin123','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','Admin','User','admin@user.com', '2014-01-01', 'system');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`,`start_date`,`title`,`mobile_number`,`birth_date`,`employee_number`,`j2uLanguage`,`u2jLanguage`,`hhLanguage`,`amdLanguage`)
    VALUES ('jill123','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','Jill','User1','user1@normal.com', '2014-01-01','MRS','1234567890','2014-01-01','1072','EN_US', 'FR_FR','DE_DE','EN_US');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`,`start_date`)
    VALUES ('jack123','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','Jack','User2','user2@normal.com', '2014-01-01');
INSERT INTO lucasdb.lucas_user (username, first_name, last_name, email_address, hashed_password, j2uLanguage, u2jLanguage, hhLanguage, amdLanguage, enable, created_by_username, created_date_time,  deleted_indicator,skill)
    VALUES ('design', 'Lucas-Design-System', 'Lucas-Design-System', 'support@lucasware.com', '$2a$10$4xiefLy4vvQQbmPb.MTeDOqKjCHwyo6oDAdoEBlNIIyr3eCA6LxTO', 'EN_GB', 'EN_GB', 'EN_GB', 'EN_GB', 1, 'system', SYSDATE(), 0,'ADVANCED');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`)
    VALUES ('joe123','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','Joe','User3','user3@normal.com');
-- needed start date for dummy user name test with the new filter type
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `start_date` )
    VALUES ('dummy-username6','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', '2014-01-01 00:00:00');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `start_date` )
    VALUES ('dummy-username7','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', '2014-01-01 00:00:00');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `start_date` )
    VALUES ('dummy-username8','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', '2014-01-01 00:00:00');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `start_date` )
    VALUES ('dummy-username9','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', '2014-01-01 00:00:00');
-- need the yesterday birthday for testing DATE filter type
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `birth_date`)
    VALUES ('dummy-username10','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', SUBDATE(NOW(),1));
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `birth_date`)
    VALUES ('dummy-username11','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', SUBDATE(NOW(),1));
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `birth_date`)
    VALUES ('dummy-username12','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', SUBDATE(NOW(),1));
-- this is needed for last month date filter
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `birth_date` )
    VALUES ('dummy-username13','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', SUBDATE(NOW(),35));
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `birth_date` )
    VALUES ('dummy-username14','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', SUBDATE(NOW(),28));
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address`, `birth_date` )
    VALUES ('dummy-username15','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com', SUBDATE(NOW(),20));
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username16','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username17','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username18','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username19','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username20','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username21','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username22','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username23','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username24','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username25','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username26','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username27','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username28','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username29','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username30','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username31','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username32','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username33','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username34','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username35','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username36','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username37','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username38','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username39','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username40','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username41','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username42','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username43','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username44','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username45','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username46','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username47','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username48','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username49','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username50','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username51','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username52','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username53','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username54','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username55','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username56','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username57','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username58','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username59','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username60','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username61','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username62','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username63','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username64','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username65','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username66','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username67','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username68','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username69','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username70','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username71','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username72','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username73','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username74','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username75','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username76','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username77','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username78','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username79','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username80','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username81','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username82','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username83','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username84','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username85','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username86','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username87','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username88','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username89','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username90','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username91','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username92','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username93','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username94','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username95','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username96','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username97','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username98','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username99','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username100','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username101','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username102','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username103','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username104','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username105','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username106','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username107','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username108','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username109','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username110','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username111','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username112','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username113','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username114','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username115','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username116','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username117','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username118','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username119','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username120','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username121','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username122','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username123','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username124','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username125','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username126','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username127','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username128','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username129','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username130','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username131','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username132','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username133','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username134','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username135','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username136','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username137','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username138','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username139','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username140','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username141','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username142','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username143','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username144','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username145','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username146','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username147','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username148','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username149','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username150','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username151','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username152','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username153','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username154','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username155','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username156','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username157','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username158','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username159','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username160','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username161','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username162','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username163','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username164','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username165','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username166','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username167','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username168','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username169','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username170','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username171','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username172','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username173','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username174','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username175','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username176','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username177','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username178','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username179','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username180','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username181','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username182','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username183','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username184','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username185','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username186','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username187','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username188','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username189','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username190','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username191','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username192','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username193','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username194','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username195','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username196','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username197','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username198','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username199','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO `lucasdb`.`lucas_user`(`username`,`hashed_password`,`first_name`,`last_name`,`email_address` )
    VALUES ('dummy-username200','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy','dummy-firstName','dummy-LastName' ,'dummy@email.com');
INSERT INTO lucasdb.lucas_user (username, first_name, last_name, email_address, hashed_password,j2uLanguage, 
       u2jLanguage, hhLanguage, amdLanguage, enable, created_by_username,deleted_indicator)
    VALUES ('e2euser', 'E2E', 'E2E', 'support@lucasware.com','$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy', 'EN_GB', 'EN_GB', 'EN_GB', 'EN_GB',
       1, 'system', 0);
       
-- ----------------------------------------------------------------------------
--
-- DESCRIPTION: Assign users to groups for permission testing
--
-- ----------------------------------------------------------------------------

INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'admin123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'system'));  
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'admin123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'warehouse-manager'));
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'admin123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'basic-authenticated-user'));          
        
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'jill123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'picker'));

INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'jack123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'picker'));
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'jack123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'supervisor'));

INSERT INTO `lucasdb`.`lucas_user_permission_group`
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'jack123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'warehouse-manager'));
          
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'design'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'picker'));
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'design'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'supervisor'));

INSERT INTO `lucasdb`.`lucas_user_permission_group`
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'design'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'warehouse-manager'));
          
INSERT INTO `lucasdb`.`lucas_user_permission_group`
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'joe123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'administrator'));
 
INSERT INTO lucasdb.lucas_user_permission_group (user_id, permission_group_id) 
  VALUES ((SELECT user_id FROM lucasdb.lucas_user WHERE username = 'e2euser'),
          (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'system'));
          
INSERT INTO `lucasdb`.`lucas_user_permission_group` 
  VALUES ((SELECT user_id FROM lucas_user WHERE username = 'jack123'),
          (SELECT permission_group_id FROM permission_group WHERE permission_group_name = 'administrator'));

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create some waves
--
-- ---------------------------------------------------------------------------

INSERT INTO `wave` (wave_name)
  VALUES ('Wave1')
        ,('Wave2')
        ,('Wave3')
        ,('Wave4');

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Assign picklines to each wave
--
-- ---------------------------------------------------------------------------

INSERT INTO `lucasdb`.`pickline`(`completed`,`quantity`,`wave_id`) 
  VALUES (1,1,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,2,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,3,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,4,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,5,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,6,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,7,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,8,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,9,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,10,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,11,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,12,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,13,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,14,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,15,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,16,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,17,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,18,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,19,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,20,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,1,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,2,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,3,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,4,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,5,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,6,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,7,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,8,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,9,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,10,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,11,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,12,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,13,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,14,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,15,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,16,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,17,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,18,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,19,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,20,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,1,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,2,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,3,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,4,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,5,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,6,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,7,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (0,8,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,9,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (0,10,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,11,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,12,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,13,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,14,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,15,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,16,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,17,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,18,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,19,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,20,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,1,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,2,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,3,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,4,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,5,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,6,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,7,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,8,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,9,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,10,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,11,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,12,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,13,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,14,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,15,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,16,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,17,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,18,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,19,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,20,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,1,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,2,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,3,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,4,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,5,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,6,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,7,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,8,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,9,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,10,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,11,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,12,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,13,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,14,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,15,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,16,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1')),
         (1,17,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave2')),
         (1,18,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave3')),
         (1,19,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave4')),
         (1,20,(SELECT wave_id FROM lucasdb.wave where wave_name = 'Wave1'));

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Insert location alerts
--
-- ---------------------------------------------------------------------------

INSERT INTO `alert_locations` (`alert_location_description`, `alert_location_name`
 ,`created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
  VALUES ('handle with care', 'PRE','system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create test products
--
-- ---------------------------------------------------------------------------
INSERT INTO lucasdb.`product`(`is_base_item`,`base_item_threshold`,`capture_lot_number`,`capture_serial_number`,`capture_catch_weight`,`description`,`name`,`product_code`,`product_status_code`,`customer_SKU_nbr`,`manufacturer_item_nbr`,`haz_material`,`created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
VALUES (1,1,1,1,1,'TORO PULLEY','99-4937','99-4937','Available','customer_SKU_nbr01','manufacturer_item_nbr01' ,1,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (0,1,1,1,1,'Water / Fuel Filter ASM','99-4938','99-4938','Available','customer_SKU_nbr02','manufacturer_item_nbr02',1 , 'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (0,1,1,1,1,'HOUSING ASM-REDUCER RH','99-4939','99-4939','Available','customer_SKU_nbr03','manufacturer_item_nbr03' ,1, 'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,1,1,1,'Dummy1 Product','11-4937','11-4937','Marked Out','dumm1 customer_SKU_nbr01','dummy1 manufacturer_item_nbr01',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (0,1,1,1,1,'Dummy2 Product','','11-4937','Marked Out','dummy2 customer_SKU_nbr01','dummy2 manufacturer_item_nbr01',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,1,1,1,'Dummy3 Product','','','Marked Out','dummy3 customer_SKU_nbr01','dummy3 manufacturer_item_nbr01',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,1,1,1,'Dummy4 Product','11-4937','11-4937','','dummy4 customer_SKU_nbr01','dummy4 manufacturer_item_nbr01',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,1,1,1,'Dummy5 Product','11-4937','11-4937','','dummy5 customer_SKU_nbr01','dummy5 manufacturer_item_nbr01',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,0,0,0,'','11-4937','11-4937','','','',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,0,0,0,'','11-4937','11-4937','','','',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP),
      (1,1,1,1,1,'Dummy2 Product','11-4937','11-4937','Marked Out','dummy4 customer_SKU_nbr01','dummy manufacturer_item_nbr01',0,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP);
-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Update product custom classification for the test data
--
-- ---------------------------------------------------------------------------
UPDATE lucasdb.product_custom_classification
   SET custom_classification_name = 'Brand',
       custom_classification_definition = '{\"data\":[\"Kellogg''s\",\"Nestle\",\"Quaker\"]}'
WHERE custom_classification_field_name = 'custom_classification_1';

UPDATE lucasdb.product_custom_classification
   SET custom_classification_name = 'Category'
WHERE custom_classification_field_name = 'custom_classification_2';

UPDATE lucasdb.product_custom_classification
   SET custom_classification_name = 'Sub-Category'
WHERE custom_classification_field_name = 'custom_classification_3';

-- ----------------------------------------------------------------------------
--
-- DESCRIPTION: Create factor components
--
-- ----------------------------------------------------------------------------
INSERT INTO lucasdb.pack_factor_component (pf_component_id, pf_component_name, pf_component_definition, pf_component_voicing, created_by_username, created_date_time, updated_by_username, updated_date_time)
VALUES (1, 'Each', '{ "Each": { "abbreviations": [ "EA", "EAS", "BS" ] }}', 'each', 'system', SYSDATE(), 'system', SYSDATE()),
       (2, 'Inner Pack', NULL , 'inner pack', 'system', SYSDATE(), 'system', SYSDATE()),
       (3, 'Case', '{ "Case": { "abbreviations": [ "C", "CS" ] }}' , 'case', 'system', SYSDATE(), 'system', SYSDATE()),
       (4, 'Master Pack', NULL , 'master pack', 'system', SYSDATE(), 'system', SYSDATE()),
       (5, 'Tier', NULL , 'tier', 'system', SYSDATE(), 'system', SYSDATE()),
       (6, 'Pallet', NULL , 'pallet', 'system', SYSDATE(), 'system', SYSDATE());

-- ----------------------------------------------------------------------------
--
-- DESCRIPTION: Create factor hierarchy
--
-- ----------------------------------------------------------------------------
INSERT INTO lucasdb.pack_factor_hierarchy (pf_hierarchy_id, pf_hierarchy_name, created_by_username, created_date_time, updated_by_username, updated_date_time)
VALUES (1, 'Frozen Foods 5lbs',  'system', SYSDATE(), 'system', SYSDATE()),
       (2, 'Ready to Eat Salads', 'system', SYSDATE(), 'system', SYSDATE());

-- ----------------------------------------------------------------------------
--
-- DESCRIPTION: Create factor hierarchy components
--
-- ----------------------------------------------------------------------------
INSERT INTO lucasdb.pack_factor_hierarchy_component (pf_hierarchy_id, pf_component_id, child_pf_component_id, factor, level, bottom_level_flag, default_height, default_width, default_depth, default_weight, default_cube, created_by_username, created_date_time, updated_by_username, updated_date_time)
VALUES (1, 6, 5, 3, 0, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (1, 5, 4 , 2, 1, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (1, 4, 3 , 2, 2, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (1, 3, 2 , 8, 3, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (1, 2, 1 , 4, 4, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (1, 1, NULL, NULL, 5, 1, 3.5, 3.5, 3.5, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),

       (2, 6, 5 , 3, 0, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (2, 5, 3 , 2, 1, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (2, 4, 2 , 2, 2, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (2, 3, 2 , 8, 3, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (2, 2, 1 , 4, 4, 0, NULL, NULL, NULL, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE()),
       (2, 1, NULL, NULL, 4, 1, 3.5, 3.5, 3.5, NULL, NULL, 'system', SYSDATE(), 'system', SYSDATE())
       ;

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create special instructions
--
-- ---------------------------------------------------------------------------
 INSERT INTO `special_instructions` (`response_type`,`special_instruction`,`alert_locations_id`,`product_id`
 ,`created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
  VALUES ('ACKNOWLEDGE','1',1,1,'system', CURRENT_TIMESTAMP,'system', CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create canvas layouts
--
-- ---------------------------------------------------------------------------
INSERT INTO `lucasdb`.`canvas_layout` (`created_by_username`, `default_indicator`, `display_order`) 
  VALUES ('admin123', 0, '1'),
         ('admin123', 0, '2'),
         ('admin123', 0, '3'),
         ('admin123', 0, '4'),
         ('admin123', 1, '5'),
         ('admin123', 0, '6'),
         ('admin123', 0, '7'),
         ('admin123', 0, '8');

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Create shifts, and assign to users
--
-- ---------------------------------------------------------------------------
INSERT INTO lucasdb.shift (shift_id, shift_name, start_time, end_time, created_by_username, created_date_time)
    VALUES (1, 'morning', '06:00', '15:00', 'system', SYSDATE());
INSERT INTO lucasdb.shift (shift_id, shift_name, start_time, end_time, created_by_username, created_date_time)
    VALUES (2, 'day', '09:00', '18:00', 'system', SYSDATE());
INSERT INTO lucasdb.shift (shift_id, shift_name, start_time, end_time, created_by_username, created_date_time)
    VALUES (3, 'late', '15:00', '00:00', 'system', SYSDATE());
INSERT INTO lucasdb.shift (shift_id, shift_name, start_time, end_time, created_by_username, created_date_time)
    VALUES (4, 'night', '00:00', '09:00', 'system', SYSDATE());

UPDATE `lucasdb`.`lucas_user` SET `shift_id` = 1 WHERE `user_id` < 100;
UPDATE `lucasdb`.`lucas_user` SET `shift_id` = 2 WHERE `user_id` >= 100;



-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Add test data for wms_users
--
-- ---------------------------------------------------------------------------
INSERT INTO `lucasdb`.`wms_user` (`user_id`, `host_login`, `host_hashed_password`, `created_by_username`, `created_date_time`, `updated_by_username`, `updated_date_time`)
VALUES  (1, 'system', '$2a$10$Q2HtYqv1/Oy5UqbiH/yCOOgn627a6wW8Qfa/6wc3NslEsLzgWlEyy', 'system', SYSDATE(), 'system', SYSDATE());


-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Add test data for canvas
--
-- ---------------------------------------------------------------------------

INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`) VALUES (201, 'ProductManagement', 'ProductManagement', 'COMPANY', 'system', CURRENT_TIMESTAMP);
INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`) VALUES (221, 'Work Execution', 'Work Execution', 'COMPANY', 'system', CURRENT_TIMESTAMP);
INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`) VALUES (231, 'AssignmentManagement', 'AssignmentManagement', 'COMPANY', 'system', CURRENT_TIMESTAMP);
INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`) VALUES (235, 'GroupManagement', 'GroupManagement', 'COMPANY', 'system', CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
--
-- DESCRIPTION: Adding test data for jill user for canvas and widget
--
-- ---------------------------------------------------------------------------

INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`)
 VALUES (237, 'JillCanvas1', 'JillCanvas1', 'PRIVATE', 'jill123', CURRENT_TIMESTAMP);

INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`)
VALUES (238, 'JillCanvas2', 'JillCanvas2', 'PRIVATE', 'jill123', CURRENT_TIMESTAMP);

INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (900, 'TestCanvas900', 'TestCanvas900', 'PRIVATE', 'e2euser', CURRENT_TIMESTAMP, 'e2euser', 
CURRENT_TIMESTAMP);



INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JillCanvas1'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'UserCreationFormWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JillCanvas2'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'SearchUserGridWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2}');

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'picker'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-edit-user-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'picker'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-user'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'picker'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-user'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'events-widget-access'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'edit-event'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'create-event'));

INSERT INTO lucasdb.permission_group_permission (permission_group_id, permission_id) VALUES (
    (SELECT permission_group_id FROM lucasdb.permission_group WHERE permission_group_name = 'administrator'),
	(SELECT permission_id FROM lucasdb.permission WHERE permission_name = 'delete-event'));

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Perishable Goods Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'UserProductivityWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":3}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Perishable Goods Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'AssignmentManagementWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":4}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Perishable Goods Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'AssignmentCreationWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":5}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Morning Shift Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'UserProductivityWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":2}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Morning Shift Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'AssignmentManagementWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":3}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'Morning Shift Canvas'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'AssignmentCreationWidget'),
            '{"anchor":[2,2],"minimumWidth":2,"minimumHeight":2,"zindex":2,"position":4}');

            
-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_SEGMENT
--
-- --------------------------------------------------------------------------------------
           
            
 INSERT INTO lucasdb.eai_segments (segment_description, segment_length, segment_name) VALUES ('Control records for transfer order', '125','Control');
 
 
-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_SEGMENT_DEFINITION
--
-- --------------------------------------------------------------------------------------

INSERT INTO lucasdb.eai_segment_definition (segment_field_description, segment_field_end, segment_field_length, 
segment_field_name, segment_field_seq, segment_field_start, segment_field_type, segment_field_value, 
segment_id) VALUES ('Segment Identifier', '5','5','SegmentID','1','1','SegID','HH',(SELECT segment_id from 
lucasdb.eai_segments where segment_name = 'Control'));

INSERT INTO lucasdb.eai_segment_definition (segment_field_description, segment_field_end, segment_field_length, 
segment_field_name, segment_field_seq, segment_field_start, segment_field_type, segment_id) 
VALUES ('TabName', '15','10','TabName','2','6','String',(SELECT segment_id from 
lucasdb.eai_segments where segment_name = 'Control'));


INSERT INTO lucasdb.eai_segment_definition (segment_field_description, segment_field_end, segment_field_length, 
segment_field_name, segment_field_seq, segment_field_start, segment_field_type, segment_id) 
VALUES ('TabName', '18','3','ManDt','3','16','String',(SELECT segment_id from 
lucasdb.eai_segments where segment_name = 'Control'));


INSERT INTO lucasdb.eai_segment_definition (segment_field_description, segment_field_end, segment_field_length, 
segment_field_name, segment_field_seq, segment_field_start, segment_field_type, segment_id) 
VALUES ('Document Number', '28','10','DocNum','4','19','Integer',(SELECT segment_id from 
lucasdb.eai_segments where segment_name = 'Control'));


INSERT INTO lucasdb.eai_segment_definition (segment_field_description, segment_field_end, segment_field_length, 
segment_field_name, segment_field_seq, segment_field_start, segment_field_type, segment_id) 
VALUES ('Doc Dt (YYYYMMDD)', '36','8','DocDate','5','29','DateTime',(SELECT segment_id from 
lucasdb.eai_segments where segment_name = 'Control'));

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MESSAGE
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_message` (`lucas_predefined`, `message_description`, `message_format`, `message_name`, `usage_in_events`,`message_field_delimited_character`) VALUES ( 0, 'User\'s import inbound host message', 'Delimited', 'Lucas user import host', '1',',');
INSERT INTO `lucasdb`.`eai_message` (`lucas_predefined`, `lucas_predefined_service`, `message_description`, `message_format`, `message_name`, `usage_in_events`, `message_field_delimited_character`) VALUES (0, 'usersExportOutboundLucasServiceChannel', 'User\'s export outbound host message', 'Delimited', 'Lucas user export host', '1', ',');

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MAPPING
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_mapping` (`destination_message_id`, `mapping_description`, `mapping_name`, `source_message_id`, `usage_in_events_handlers`)
VALUES ((SELECT message_id FROM lucasdb.eai_message where message_name="Lucas user import lucas"), 'Transform inbound user details', 'Transform inbound user details', (SELECT message_id FROM lucasdb.eai_message where message_name = "Lucas user import host"), '1');
INSERT INTO `lucasdb`.`eai_mapping` (`destination_message_id`, `mapping_description`, `mapping_name`, `source_message_id`, `usage_in_events_handlers`)
VALUES ((SELECT message_id FROM lucasdb.eai_message where message_name = "Lucas user export host"), 'Transform outbound user logon details', 'Transform outbound user logon details', (SELECT message_id FROM lucasdb.eai_message where message_name = "Lucas user export lucas"), '1');

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_TRANSPORT
--
-- --------------------------------------------------------------------------------------
	
INSERT INTO `lucasdb`.`eai_transport` (`delete_source_file`, `host`, `inbound_directory`, `transport_name`, `transport_type`) VALUES ( 1, 'Local directory', '/eai/inbound/async/files', 'File inbound async', 'File');
INSERT INTO `lucasdb`.`eai_transport` (`delete_source_file`, `host`, `remote_directory`, `transport_name`, `transport_type`) VALUES ( 0, 'Mobile device', '/eai/outbound/async/files', 'File outbound async', 'File');

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_EVENT_HANDLER
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_event_handler` (`event_handler_description`, `event_handler_name`, `event_handler_type`, `inbound_mapping_id`, `usage_in_event`, `event_transport_id`, `inbound_file_pattern`) VALUES ('Users data import', 'Users data import', 'Inbound Asynchronous', (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform inbound user details'), '1', (SELECT transport_id FROM lucasdb.eai_transport where transport_name = 'File inbound async') , 'users_details.*?.csv');
INSERT INTO `lucasdb`.`eai_event_handler` (`event_handler_description`, `event_handler_name`, `event_handler_type`, `outbound_file_pattern`, `usage_in_event`, `outbound_mapping_id`, `event_transport_id`) VALUES ('User logon', 'User logon', 'Outbound Asynchronous', '<username>_details', '1', (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform outbound user logon details'), (SELECT transport_id FROM lucasdb.eai_transport where transport_name = 'File outbound async'));

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_EVENT_EVENT_HANDLER
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_event_event_handler` (`execution_order`, `seq`, `event_handler_id`, `event_id`) VALUES ('1', '1', (SELECT event_handler_id FROM lucasdb.eai_event_handler where event_handler_name = 'Users data import'), (SELECT event_id FROM lucasdb.eai_event where event_name = 'Users data import'));
INSERT INTO `lucasdb`.`eai_event_event_handler` (`execution_order`, `seq`, `event_handler_id`, `event_id`) VALUES ('1', '1', (SELECT event_handler_id FROM lucasdb.eai_event_handler where event_handler_name = 'User logon'), (SELECT event_id FROM lucasdb.eai_event where event_name = 'Mobile device login'));

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MAPPING_DEFINITION
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_mapping_definition` (`transformation_entity_name`, `transformation_id`, `mapping_id`) VALUES ('Transformation', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'), (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform inbound user details'));
INSERT INTO `lucasdb`.`eai_mapping_definition` (`transformation_entity_name`, `transformation_id`, `mapping_id`) VALUES ('Transformation', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'), (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform outbound user logon details'));

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MESSAGE_DEFINITION
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s email id', '100', 'email_address', '1', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s full name ', '100', 'full_name', '2', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s hashed password', '500', 'hashed_password', '3', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s username', '100', 'username', '4', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s title', '45', 'title', '5', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s mobile number', '45', 'mobile_number', '6', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of start', '23', 'start_date', '7', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of birth', '23', 'birth_date', '8', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s employee number', '100', 'employee_number', '9', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Jennifer to user language', '10', 'j2uLanguage', '10', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User to Jennifer language', '10', 'u2jLanguage', '11', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Hand held device language for user', '10', 'hhLanguage', '12', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Amd language for user', '10', 'amdLanguage', '13', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Is user enable or disable', '1', 'enable', '14', 'Boolean', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s skill', '50', 'skill', '15', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s shift id', '20', 'shift_id', '16', 'Long', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user import host'));

INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s email id', '100', 'email_address', '1', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s full name ', '100', 'full_name', '2', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s hashed password', '500', 'hashed_password', '3', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s username', '100', 'username', '4', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s title', '45', 'title', '5', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s mobile number', '45', 'mobile_number', '6', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of start', '23', 'start_date', '7', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s date of birth', '23', 'birth_date', '8', 'Date', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s employee number', '100', 'employee_number', '9', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Jennifer to user language', '10', 'j2uLanguage', '10', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User to Jennifer language', '10', 'u2jLanguage', '11', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Hand held device language for user', '10', 'hhLanguage', '12', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Amd language for user', '10', 'amdLanguage', '13', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('Is user enable or disable', '1', 'enable', '14', 'Boolean', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s skill', '50', 'skill', '15', 'Text', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));
INSERT INTO `lucasdb`.`eai_message_definition` (`message_field_description`, `message_field_length`, `message_field_name`, `message_field_seq`, `message_field_type`, `message_id`) VALUES ('User\'s shift id', '20', 'shift_id', '16', 'Long', (SELECT message_id FROM lucasdb.eai_message where message_name = 'Lucas user export host'));

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_TRANSFORMATION
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_transformation` (`transformation_channel`, `transformation_name`, `transformation_type`) VALUES ('userExpressionTransformationChannel', 'userImportTransformation', 'Expression');
INSERT INTO `lucasdb`.`eai_transformation` (`transformation_channel`, `transformation_name`, `transformation_type`) VALUES ('userExpressionTransformationChannel', 'userLogonTransformation', 'Expression');

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_TRANSFORMATION_DEFINITION
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('email_address', '1', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'5');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('full_name', '2', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),null);
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('hashed_password', '3', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'4');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('username', '4', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'1');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('title', '5', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'7');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('mobile_number', '6', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'6');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('start_date', '7', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'9');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('birth_date', '8', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'10');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('employee_number', '9', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'8');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('j2uLanguage', '10', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'13');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('u2jLanguage', '11', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'14');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('hhLanguage', '12', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'15');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('amdLanguage', '13', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'16');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('enable', '14', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'17');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('skill', '15', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'12');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('shift_id', '16', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'11');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_expression`, `transformation_field_length`, `transformation_field_name`, `transformation_field_seq`, `transformation_field_type`, `transformation_field_var`, `transformation_id`,`message_field_id`) VALUES ('substring(full_name,1,6)', '100', 'v_first_name', '17', 'Text', 1, (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'2');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_expression`, `transformation_field_length`, `transformation_field_name`, `transformation_field_seq`, `transformation_field_type`, `transformation_field_var`, `transformation_id`,`message_field_id`) VALUES ('substring(full_name,7)', '100', 'v_last_name', '18', 'Text', 1, (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'),'3');

INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('emailAddress', '1', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'51');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('hashedPassword', '2', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'53');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('userName', '3', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'54');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_expression`, `transformation_field_length`, `transformation_field_name`, `transformation_field_seq`, `transformation_field_type`, `transformation_field_var`, `transformation_id`,`message_field_id`) VALUES ('concat(firstName,lastName)','200', 'v_full_name', '4',  'Text', 1, (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'52');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('title', '5', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'55');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('mobileNumber', '6', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'56');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('startDate', '7', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'57');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('birthDate', '8', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'58');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('employeeNumber', '9', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'59');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('j2uLanguage', '10', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'60');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('u2jLanguage', '11', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'61');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('hhLanguage', '12', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'62');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('amdLanguage', '13', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'63');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('enable', '14', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'64');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('skill', '15', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'65');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('shift', '16', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),'66');
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('firstName', '17',  (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),null);
INSERT INTO `lucasdb`.`eai_transformation_definition` (`transformation_field_name`, `transformation_field_seq`, `transformation_id`,`message_field_id`) VALUES ('lastName', '18',  (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'),null);

-- --------------------------------------------------------------------------------------
--
-- DESCRIPTION: EAI_MAPPING_PATH
--
-- --------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`eai_mapping_path` (`mapping_path_seq`, `to_transformation_id`, `mapping_id`) VALUES ('1', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'), (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform inbound user details'));
INSERT INTO `lucasdb`.`eai_mapping_path` (`from_transformation_id`, `mapping_path_seq`, `mapping_id`) VALUES ((SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userImportTransformation'), '2', (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform inbound user details'));
INSERT INTO `lucasdb`.`eai_mapping_path` (`mapping_path_seq`, `to_transformation_id`, `mapping_id`) VALUES ('1', (SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'), (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform outbound user logon details'));
INSERT INTO `lucasdb`.`eai_mapping_path` (`from_transformation_id`, `mapping_path_seq`, `mapping_id`) VALUES ((SELECT transformation_id FROM lucasdb.eai_transformation where transformation_name = 'userLogonTransformation'), '2', (SELECT mapping_id FROM lucasdb.eai_mapping where mapping_name = 'Transform outbound user logon details'));



-- -------------------------------------------------------------------------------------------------------------
--                                Rendering Canvas with all the types of widgets
-- -------------------------------------------------------------------------------------------------------------
INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (300, 'JackCanvas300', 'JackCanvas300', 'PRIVATE', 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP);
 
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'UserCreationFormWidget')
            ,'{"anchor":[275,295],"minimumWidth":567,"minimumHeight":240,"zindex":1,"deviceWidths":{"320":"mobile","540":"tablet","800":"desktop","1200":"wideScreen"},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"position":1}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config,data_url)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'SearchUserGridWidget'),
            '{"anchor":[1,363],"minimumWidth":485,"minimumHeight":375,"zindex":1,"deviceWidths":{"320":"mobile","600":"tablet","800":"desktop","1200":"wideScreen"},"autoRefreshConfig":{"globalOverride":false,"enabled":false,"interval":120},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"gridColumns":{"1":{"name":"Lucas Login","fieldName":"userName","visible":true,"allowFilter":true,"order":"1","sortOrder":"1","width":150},"2":{"name":"Host Login","fieldName":"wmsUser","visible":false,"allowFilter":true,"order":"2","sortOrder":"1","width":150},"3":{"name":"First Name","fieldName":"firstName","visible":true,"allowFilter":true,"order":"3","sortOrder":"1","width":150},"4":{"name":"Last Name","fieldName":"lastName","visible":true,"allowFilter":true,"order":"4","sortOrder":"1","width":150},"5":{"name":"Skill","fieldName":"skill","visible":true,"allowFilter":true,"order":"5","sortOrder":"1","width":150},"6":{"name":"Shifts","fieldName":"shift","visible":true,"allowFilter":true,"order":"6","sortOrder":"1","width":150},"7":{"name":"J2U Language","fieldName":"j2uLanguage","visible":true,"allowFilter":true,"order":"7","sortOrder":"1","width":150},"8":{"name":"U2J Language","fieldName":"u2jLanguage","visible":true,"allowFilter":true,"order":"8","sortOrder":"1","width":150},"9":{"name":"HH Language","fieldName":"hhLanguage","visible":true,"allowFilter":true,"order":"9","sortOrder":"1","width":150},"10":{"name":"AMD Language","fieldName":"amdLanguage","visible":true,"allowFilter":true,"order":"10","sortOrder":"1","width":150},"11":{"name":"Status","fieldName":"enable","visible":true,"allowFilter":true,"order":"11","sortOrder":"1","width":150},"12":{"name":"Employee Id","fieldName":"employeeId","visible":false,"allowFilter":true,"order":"12","sortOrder":"1","width":150},"13":{"name":"Title","fieldName":"title","visible":false,"allowFilter":true,"order":"13","sortOrder":"1","width":150},"14":{"name":"Start Date","fieldName":"startDate","visible":true,"allowFilter":true,"order":"14","sortOrder":"1","width":150},"15":{"name":"Birth Date","fieldName":"birthDate","visible":false,"allowFilter":true,"order":"15","sortOrder":"1","width":150},"16":{"name":"Mobile Number","fieldName":"mobileNumber","visible":false,"allowFilter":true,"order":"16","sortOrder":"1","width":150},"17":{"name":"Email Address","fieldName":"emailAddress","visible":true,"allowFilter":true,"order":"17","sortOrder":"1","width":150},"18":{"name":"User Id","fieldName":"userId","visible":false,"allowFilter":true,"order":"18","sortOrder":"1","width":150}},"position":2}'
            ,'{\"url\": \"/users/userlist/search\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'PicklineByWaveBarChartWidget'),
            '{"anchor":[1,2],"minimumWidth":500,"minimumHeight":300,"zindex":1,"deviceWidths":{"320":"mobile","540":"tablet","800":"desktop","1200":"wideScreen"},"orientation":{"option":[{"legend":"horizontal","value":"h"},{"legend":"vertical","value":"v"}],"selected":"h"},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"position":3}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'GroupMaintenanceWidget'),
            '{"anchor":[2,2],"minimumWidth":1100,"minimumHeight":500,"zindex":1,"deviceWidths":{"320":"mobile","540":"tablet","800":"desktop","1200":"wideScreen"},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"position":4}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config,data_url)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'SearchProductGridWidget'),
            '{"anchor":[1,363],"minimumWidth":485,"minimumHeight":375,"zindex":1,"deviceWidths":{"320":"mobile","540":"tablet","800":"desktop","1200":"wideScreen"},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"gridColumns":{"1":{"name":"Product Code","fieldName":"productCode","visible":false,"allowFilter":true,"order":"1","sortOrder":"1","width":150},"2":{"name":"Product Name","fieldName":"name","visible":true,"allowFilter":true,"order":"2","sortOrder":"1","width":150},"3":{"name":"Description","fieldName":"description","visible":true,"allowFilter":true,"order":"3","sortOrder":"1","width":150},"4":{"name":"Status","fieldName":"productStatusCode","visible":true,"allowFilter":true,"order":"4","sortOrder":"1","width":150},"5":{"name":"2nd Validation","fieldName":"performSecondValidation","visible":true,"allowFilter":true,"order":"5","sortOrder":"1","width":150},"6":{"name":"Capture Lot Nbr","fieldName":"captureLotNumber","visible":true,"allowFilter":true,"order":"6","sortOrder":"1","width":150},"7":{"name":"Capture Serial Nbr","fieldName":"captureSerialNumber","visible":true,"allowFilter":true,"order":"7","sortOrder":"1","width":150},"8":{"name":"Base Item","fieldName":"isBaseItem","visible":true,"allowFilter":true,"order":"8","sortOrder":"1","width":150},"9":{"name":"Capture Catch Weight","fieldName":"captureCatchWeight","visible":true,"allowFilter":true,"order":"9","sortOrder":"1","width":150},"10":{"name":"HeadsUp Messages","fieldName":"headsUpMessages","visible":true,"allowFilter":true,"order":"10","sortOrder":"1","width":150},"11":{"name":"Haz Material","fieldName":"hazardousMaterial","visible":true,"allowFilter":true,"order":"11","sortOrder":"1","width":150},"12":{"name":"Customer SKU Number","fieldName":"customerSkuNbr","visible":true,"allowFilter":true,"order":"12","sortOrder":"1","width":150},"13":{"name":"Manufacturer Item Number","fieldName":"manufacturerItemNbr","visible":true,"allowFilter":true,"order":"13","sortOrder":"1","width":150},"14":{"name":"Expiration Date Check","fieldName":"captureExpirationDate","visible":true,"allowFilter":true,"order":"14","sortOrder":"1","width":150}},"position":5}'
            ,'{\"url\": \"/products/productlist/search\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config,data_url)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'SearchGroupGridWidget'),
            '{"anchor":[2,2],"minimumWidth":300,"minimumHeight":350,"deviceWidths":{"320":"mobile","600":"tablet","800":"desktop","1200":"wideScreen"},"zindex":1,"gridColumns":{"1":{"name":"Group Name","fieldName":"groupName","order":"1","sortOrder":"1","visible":true,"allowFilter":true,"width":150},"2":{"name":"Description","fieldName":"description","order":"2","sortOrder":"2","visible":true,"allowFilter":true,"width":150},"3":{"name":"User Count","fieldName":"userCount","order":"3","sortOrder":"3","visible":true,"allowFilter":true,"width":150}},"dateFormat":{"selectedFormat":"mm-dd-yyyy","availableFormats":["mm-dd-yyyy","MMM dd, yyyy","dd-mm-yyyy"]},"position":6}'
            ,'{\"url\": \"/groups/grouplist/search\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}');

INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config,data_url)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'EquipmentTypeGridWidget'),
            '{"anchor":[2,2],"minimumWidth":300,"minimumHeight":350,"deviceWidths":{"320":"mobile","600":"tablet","800":"desktop","1200":"wideScreen"},"zindex":1,"gridColumns":{"1":{"allowFilter":true,"order":"1","visible":true,"sortOrder":"1","width":150,"name":"Equipment Style","fieldName":"equipmentStyle.equipmentStyleName"},"2":{"allowFilter":true,"order":"2","visible":false,"sortOrder":"1","width":150,"name":"Equipment Type","fieldName":"equipmentTypeName"},"3":{"allowFilter":true,"order":"3","visible":true,"sortOrder":"1","width":150,"name":"Description","fieldName":"equipmentTypeDescription"},"4":{"allowFilter":true,"order":"4","visible":true,"sortOrder":"1","width":150,"name":"Requires Check List","fieldName":"requiresCheckList"},"5":{"allowFilter":true,"order":"5","visible":true,"sortOrder":"1","width":150,"name":"Shipping Container","fieldName":"shippingContainer"},"6":{"allowFilter":true,"order":"6","visible":true,"sortOrder":"1","width":150,"name":"Requires Certification","fieldName":"requiresCertification"},"7":{"allowFilter":true,"order":"7","visible":true,"sortOrder":"1","width":150,"name":"Total Positions","fieldName":"equipmentTypePositions"},"8":{"allowFilter":true,"order":"8","visible":true,"sortOrder":"1","width":150,"name":"Count","fieldName":"equipmentCount"}},"position":6}'
            ,'{\"url\": \"/equipments/equipment-type-list/search\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}');
INSERT INTO lucasdb.canvas_widgetinstancelist (canvas_canvas_id, widget_id, actual_widget_view_config,data_url)
    VALUES ((SELECT canvas_id FROM lucasdb.canvas WHERE name = 'JackCanvas300'),
            (SELECT widget_id FROM lucasdb.widget WHERE DTYPE = 'EquipmentManagerGridWidget'),
            '{"anchor":[2,2],"minimumWidth":300,"minimumHeight":350,"deviceWidths":{"320":"mobile","600":"tablet","800":"desktop","1200":"wideScreen"},"zindex":1,"gridColumns":{"1":{"allowFilter":true,"order":"1","visible":true,"sortOrder":"1","width":150,"name":"Equipment ID","fieldName":"equipmentType.equipmentTypeName"},"2":{"allowFilter":true,"order":"2","visible":false,"sortOrder":"1","width":150,"name":"Description","fieldName":"equipmentType.equipmentTypeDescription"},"3":{"allowFilter":true,"order":"3","visible":true,"sortOrder":"1","width":150,"name":"Equipment Type","fieldName":"equipmentType.equipmentTypeName"},"4":{"allowFilter":true,"order":"4","visible":true,"sortOrder":"1","width":150,"name":"Total Positions","fieldName":"equipmentType.equipmentTypePositions"},"5":{"allowFilter":true,"order":"5","visible":true,"sortOrder":"1","width":150,"name":"Current User Name","fieldName":"user.userId"},"6":{"allowFilter":true,"order":"6","visible":true,"sortOrder":"1","width":150,"name":"First Name","fieldName":"user.firstName"},"7":{"allowFilter":true,"order":"7","visible":true,"sortOrder":"1","width":150,"name":"Last Name","fieldName":"user.lastName"},"8":{"allowFilter":true,"order":"8","visible":true,"sortOrder":"1","width":150,"name":"Current Assignment","fieldName":"equipment.currentAssignmentId"},"9":{"allowFilter":true,"order":"9","visible":true,"sortOrder":"1","width":150,"name":"Equipment Style","fieldName":"equipmentStyle.equipmentStyleName"},"10":{"allowFilter":true,"order":"10","visible":true,"sortOrder":"1","width":150,"name":"Status","fieldName":"equipmentType.equipmentTypeStatus"}},"position":6}'
            ,'{\"url\": \"/equipments/equipment-manager-list/search\",\"searchCriteria\": {\"pageSize\": \"10\",\"pageNumber\": \"0\",\"searchMap\": {}, \"sortMap\": {}}}');
-- -------------------------------------------------------------------------------------------------------------
--                                Rendering Open Canvas for the Jack & design user
-- -------------------------------------------------------------------------------------------------------------
INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (401, 'JackCanvas401', 'JackCanvas401', 'PRIVATE', 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP);

 INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (402, 'JackCanvas402', 'JackCanvas402', 'PRIVATE', 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP);

 INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (403, 'JackCanvas403', 'JackCanvas403', 'PRIVATE', 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP);

 INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (404, 'JackCanvas404', 'JackCanvas404', 'PRIVATE', 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP);

 INSERT INTO `canvas`(`canvas_id`, `name`, `short_name`, `canvas_type`, `created_by_username`, `created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (405, 'JackCanvas405', 'JackCanvas405', 'PRIVATE', 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP);

 INSERT INTO `open_user_canvas`(`id`,`displayOrder`,`user_user_id`,`canvas_id` ,`created_by_username`,`created_date_time`,`updated_by_username`,`updated_date_time`)
 VALUES (1,5,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='ProductManagement'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(2,6,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='Work Execution'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(3,7,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='AssignmentManagement'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(4,8,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='GroupManagement'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(5,9,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='PickMonitoring'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(6,10,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='JackCanvas401'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(7,4,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='JackCanvas402'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(8,3,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='JackCanvas403'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(9,2,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='JackCanvas404'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(10,1,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='JackCanvas405'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(11,11,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='Perishable'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(12,12,(SELECT user_id FROM lucas_user where username='jack123') ,(SELECT canvas_id FROM canvas where short_name='JackCanvas300'), 'jack123', CURRENT_TIMESTAMP, 'jack123', CURRENT_TIMESTAMP)
 ,(122,5,(SELECT user_id FROM lucas_user where username='e2euser') ,(SELECT canvas_id FROM canvas where short_name='TestCanvas900'), 'e2euser', CURRENT_TIMESTAMP, 'e2euser', CURRENT_TIMESTAMP);

-- -------------------------------------------------------------------------------------------------------------
--                                Updating preferences for user Jill123, Joe123
-- -------------------------------------------------------------------------------------------------------------

UPDATE lucas_user SET data_refresh_frequency_pref=200 WHERE username='joe123';
UPDATE lucas_user SET data_refresh_frequency_pref=240 WHERE username='jill123';

-- -------------------------------------------------------------------------------------------------------------
--                                Updating active canvas for user Jack123, e2euser
-- -------------------------------------------------------------------------------------------------------------

UPDATE lucas_user SET active_canvas_id = (SELECT canvas_id FROM canvas WHERE short_name='JackCanvas405') WHERE username='jack123';
UPDATE lucasdb.lucas_user SET active_canvas_id = (SELECT canvas_id FROM canvas WHERE short_name='TestCanvas900') WHERE username='e2euser';
-- -------------------------------------------------------------------------------------------------------------


-- ===========================================================================
-- -------------------- EQUIPMENT MODULE RELATED TEST DATA -------------------
-- ===========================================================================

-- ---------------------------------------------------------------------------
--       TABLE: equipment_type
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------
INSERT INTO `equipment_type` (  `equipment_type_id`  ,`equipment_type_name`  ,`equipment_type_description`  ,`requires_check_list`  ,`requires_certification`  ,`shipping_container`  ,`reinspect_time_interval`  ,`equipment_style_id`  ,`container_type_default`  ,`equipment_type_status`  ,`equipment_count` ,`equipment_type_positions` ,`created_by_username` ,`created_date_time` ,`updated_by_username`  ,`updated_date_time` )
values(1	,'1 Pallet Jack',   'Single Position Pallet Jack',	1,	0,	0,	24,	  (SELECT equipment_style_id FROM equipment_style where equipment_style_code ='generic'),	'Pallet',  NULL,	 NULL,	1,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
     ,(2	,'2 Pallet Jack',	'Two Position Pallet Jack',	    1,	0,	0,	48,	  (SELECT equipment_style_id FROM equipment_style where equipment_style_code ='generic'),	'Pallet',	NULL,	 NULL,	2,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
     ,(3	,'3 Pallet Jack',	'Three Position Pallet Jack',   0,	1,	0,	NULL,  (SELECT equipment_style_id FROM equipment_style where equipment_style_code ='generic'),	'Pallet',	NULL,	    5,	3,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
     ,(4	,'4 Pallet Jack',	'Four Position Pallet Jack',	0,	1,	0,	NULL,	(SELECT equipment_style_id FROM equipment_style where equipment_style_code ='generic'),	'Pallet',	'deleted', 0,  4,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
     ,(5	,'8 Position Cart', '8 Position One Sided Cart',	0,	0,	0,	NULL,	(SELECT equipment_style_id FROM equipment_style where equipment_style_code ='fork_lift'),	'Tote',	NULL,	    6,  8,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);

INSERT INTO `equipment_type` (  `equipment_type_id`  ,`equipment_type_name`  ,`equipment_type_description`  ,`requires_check_list`  ,`requires_certification`  ,`shipping_container`  ,`reinspect_time_interval`  ,`equipment_style_id`  ,`container_type_default`  ,`equipment_type_status`  ,`equipment_count` ,`equipment_type_positions` ,`created_by_username` ,`created_date_time` ,`updated_by_username`  ,`updated_date_time` )
values(6	,'8 Test Position Cart', '8 Test Position One Sided Cart',	0,	0,	0,	NULL,	(SELECT equipment_style_id FROM equipment_style where equipment_style_code ='fork_lift'),	'Tote',	NULL,	    6,  8,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);


INSERT INTO `equipment_type` (  `equipment_type_id`  ,`equipment_type_name`  ,`equipment_type_description`  ,`requires_check_list`  ,`requires_certification`  ,`shipping_container`  ,`reinspect_time_interval`  ,`equipment_style_id`  ,`container_type_default`  ,`equipment_type_status`  ,`equipment_count` ,`equipment_type_positions` ,`created_by_username` ,`created_date_time` ,`updated_by_username`  ,`updated_date_time` )
values(7	,'7 Test Position Cart', '7 Test Position One Sided Cart',	0,	0,	0,	NULL,	(SELECT equipment_style_id FROM equipment_style where equipment_style_code ='fork_lift'),	'Tote',	NULL,	    7,  7,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);


-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------

INSERT INTO `equipment_type_position` (`equipment_type_position_id`, `equipment_type_id`, `equipment_type_position_nbr`, `x_position`, `y_position`, `z_position`, `created_by_username`, `created_date_time`, `updated_by_username`, `updated_date_time`)
VALUES (1,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '1 Pallet Jack'),	1,	0,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(2,  (SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '2 Pallet Jack'), 1,  0,  0,  0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(3,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '2 Pallet Jack'),	2,	1,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(4,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '3 Pallet Jack'),	1,	0,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(5,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '3 Pallet Jack'),	2,	1,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(6,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '3 Pallet Jack'),	3,	2,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(7,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '4 Pallet Jack'),	1,	0,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(8,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '4 Pallet Jack'),	2,	1,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(9,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '4 Pallet Jack'),	3,	2,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(10, (SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name` = '4 Pallet Jack'),	4,	3,	0,	0, 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);
-- ---------------------------------------------------------------------------
--       TABLE: equipment_type_question
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------
INSERT INTO `equipment_type_question` (`equipment_type_question_id` ,`equipment_type_id`,`question_id`,`created_by_username`,`created_date_time`,`updated_by_username`,`updated_date_time`)
VALUES
 (1,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name`='1 Pallet Jack'),	(SELECT question_id FROM question where question_description='SafetyLights'),'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
,(2,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name`='1 Pallet Jack'),	(SELECT question_id FROM question where question_description='Gauges'),'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
,(3,	(SELECT `equipment_type_id` FROM `equipment_type` where `equipment_type_name`='1 Pallet Jack'),	(SELECT question_id FROM question where question_description='Horn'),'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
--       TABLE: answer_evaluation_rule
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------
INSERT INTO `answer_evaluation_rule`(`answer_evaluation_rule_id`,`equipment_type_id`,`question_id`,`operator`,`operand`,`created_by_username`,`created_date_time`,`updated_by_username`,`updated_date_time`)
VALUES (1,	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='1 Pallet Jack'),	(SELECT question_id FROM question where question_description='Horn'),	        '=',	'True', 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(2,	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='1 Pallet Jack'),	(SELECT question_id FROM question where question_description='SafetyLights'),	'=',	'True', 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(3,	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='1 Pallet Jack'),	(SELECT question_id FROM question where question_description='Gauges'),      	'=',	'True', 'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(4,	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='2 Pallet Jack'),	(SELECT question_id FROM question where question_description='Tires'),	      '=',	'False','system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(5,	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='2 Pallet Jack'),	(SELECT question_id FROM question where question_description='HourMeter'),	  NULL,  NULL,'  system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
--       TABLE: equipment
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------
INSERT INTO `equipment`(`equipment_type_id`,`equipment_name`,`equipment_description`,`equipment_status`,`current_user_id` ,`current_assignment_id`,`equipment_positions`,`created_by_username`,`created_date_time`,`updated_by_username`,`updated_date_time`)
VALUES (	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='3 Pallet Jack'),	  '01',	'01 - Pallet Jack',	     NULL,	   NULL,	NULL,	3,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='8 Position Cart'),	'30',	'30 - 8 Position Cart',  NULL,	 	 25,	NULL,	8,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(	(SELECT equipment_type_id FROM equipment_type where equipment_type_name='8 Position Cart'),	'31',	'31 - 8 Position Cart',	'deleted',	25,	  NULL,	8,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
--       TABLE: equipment_position
--       DESCRIPTION: Unknown
-- ---------------------------------------------------------------------------
INSERT INTO `equipment_position`(`equipment_position_id`,equipment_id,`equipment_position_nbr`,`equipment_position_checkstring`,`equipment_position_name`,`x_position`,`y_position`,`z_position`,`container_id`,`permanent_container_id`,`created_by_username`,`created_date_time`,`updated_by_username`,`updated_date_time`)
VALUES (1,	(SELECT equipment_id FROM equipment where equipment_name='01'),	1,	NULL,	NULL,	0,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(2,	(SELECT equipment_id FROM equipment where equipment_name='01'),	2,	NULL,	NULL,	1,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(3,	(SELECT equipment_id FROM equipment where equipment_name='01'),	3,	NULL,	NULL,	2,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(4,	(SELECT equipment_id FROM equipment where equipment_name='30'),	1,	44,	  NULL,	0,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(5,	(SELECT equipment_id FROM equipment where equipment_name='30'),	2,	25,	  NULL,	1,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(6,	(SELECT equipment_id FROM equipment where equipment_name='30'),	3,	35,		NULL,	2,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(7,	(SELECT equipment_id FROM equipment where equipment_name='30'),	4,	16,		NULL,	3,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(8,	(SELECT equipment_id FROM equipment where equipment_name='30'),	5,	18,		NULL,	0,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(9,	(SELECT equipment_id FROM equipment where equipment_name='30'),	6,	68,		NULL,	1,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(10,	(SELECT equipment_id FROM equipment where equipment_name='30'),	7,	75,		NULL,	2,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(11,	(SELECT equipment_id FROM equipment where equipment_name='30'),	8,	84,		NULL,	3,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(12,	(SELECT equipment_id FROM equipment where equipment_name='31'),	1,	98,		NULL,	0,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(13,	(SELECT equipment_id FROM equipment where equipment_name='31'),	2,	89,		NULL,	1,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(14,	(SELECT equipment_id FROM equipment where equipment_name='31'),	3,	16,		NULL,	2,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(15,	(SELECT equipment_id FROM equipment where equipment_name='31'),	4,	27,		NULL,	3,	0,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(16,	(SELECT equipment_id FROM equipment where equipment_name='31'),	5,	51,		NULL,	0,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(17,	(SELECT equipment_id FROM equipment where equipment_name='31'),	6,	83,		NULL,	1,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(18,	(SELECT equipment_id FROM equipment where equipment_name='31'),	7,	77,		NULL,	2,	1,	0,	NULL,	NULL,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP)
      ,(19,	(SELECT equipment_id FROM equipment where equipment_name='31'),	8,	66,		NULL,	3,	1,	0,	NULL,	NULL ,'system',CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP);

-- -------------------------------------------------------------------------------------------------------------
--                                Insert Data for Mobile App Set Options
-- -------------------------------------------------------------------------------------------------------------
INSERT INTO `lucasdb`.`lucas_building` (`building_Id`, `building_name`, `address1`, `address2`, `city`, `state`, `zipcode`) VALUES ('1234', 'lucas-building1', 'address1', 'address2', 'city', 'state', '500045');
INSERT INTO `lucasdb`.`lucas_device` (`device_Id`, `ip_address`, `qualified_server_name`, `building_Id`) VALUES ('32085421875d5167', '131.12.45.9', 'lucas.system.1388', '1234');
INSERT INTO `lucasdb`.`lucas_mobile_app_message_option` (`mobile_app_option_id`, `building_id`, `require_password`, `application_name`, `application_version`, `spapi_messaging_version`, `default_display_language_code`, `current_server_utc`,`additional_options`) VALUES ('1', '1234', 0, 'Framework 8.0', '8.0.0.1234', '3', 'en-us', '2015/06/15 13:01:48','');
-- ---------------------------------------------------------------------------------------------------------------
--              table:lucas_user_certified_equipment_type
-- ---------------------------------------------------------------------------------------------------------------

INSERT INTO `lucasdb`.`lucas_user_certified_equipment_type` 
(`user_certified_equipment_type_id`, `created_by_username`, `created_date_time`, `updated_by_username`, `updated_date_time`, `equipment_type_id`, `user_id`) VALUES ('1', 'system', '2015-06-30 16:55:39', 'jack123', '2015-06-30 16:55:39', '7', '4');

INSERT INTO `lucasdb`.`lucas_user_certified_equipment_type` 
(`user_certified_equipment_type_id`, `created_by_username`, `created_date_time`, `updated_by_username`, `updated_date_time`, `equipment_type_id`, `user_id`) VALUES ('2', 'system', '2015-06-30 16:55:39', 'design', '2015-06-30 16:55:39', '7', '5');
