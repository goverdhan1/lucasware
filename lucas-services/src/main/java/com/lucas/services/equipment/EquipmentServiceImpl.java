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
package com.lucas.services.equipment;

import com.lucas.dao.equipment.EquipmentDAO;
import com.lucas.dao.equipment.EquipmentPositionDAO;
import com.lucas.dao.equipment.EquipmentTypeDAO;
import com.lucas.dao.user.UserDAO;
import com.lucas.entity.equipment.Equipment;
import com.lucas.entity.equipment.EquipmentPosition;
import com.lucas.entity.equipment.EquipmentType;
import com.lucas.entity.equipment.EquipmentTypePosition;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.search.SearchAndSortCriteria;

import com.lucas.services.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 * This Class provide the implementation for the functionality of..
 */
@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService {

    private static Logger LOG = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private UserDAO userDAO;
    private SecurityService securityService;
    private EquipmentDAO equipmentDAO;
    private EquipmentTypeDAO equipmentTypeDAO;
    private EquipmentPositionDAO equipmentPositionDAO;

    @Inject
    public EquipmentServiceImpl(EquipmentDAO equipmentDAO
            , EquipmentPositionDAO equipmentPositionDAO, EquipmentTypeDAO equipmentTypeDAO, UserDAO userDAO
            , SecurityService securityService) {
        this.equipmentDAO = equipmentDAO;
        this.equipmentPositionDAO = equipmentPositionDAO;
        this.equipmentTypeDAO = equipmentTypeDAO;
        this.userDAO = userDAO;
        this.securityService = securityService;
    }


    @Transactional(readOnly = true)
    @Override
    public Equipment findByEquipmentId(Long equipmentId) {
        return this.equipmentDAO.findByEquipmentId(equipmentId);
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentService#findByEquipmentName()
     */
    @Transactional(readOnly = true)
    @Override

    public Equipment findByEquipmentName(String equipmentName, boolean dependency) {
        final Equipment equipment = this.equipmentDAO.findByEquipmentName(equipmentName);
        if (equipment != null && dependency) {
            final List<EquipmentPosition> equipmentPositionList = this.equipmentPositionDAO.findEquipmentPositionByEquipmentId(equipment.getEquipmentId());
            equipment.setEquipmentPositionList(equipmentPositionList);
        }
        return equipment;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Equipment> getAllEquipment() {
        final List<Equipment> equipmentList = this.equipmentDAO.getAllEquipment();
        for (Equipment equipment : equipmentList) {
            final List<EquipmentPosition> equipmentPositionList = this.equipmentPositionDAO.findEquipmentPositionByEquipmentId(equipment.getEquipmentId());
            equipment.setEquipmentPositionList(equipmentPositionList);
        }
        return equipmentList;
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentService#saveEquipment()
     */
    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipment(Equipment equipment) {
        try {
            if (equipment != null && !equipment.getEquipmentName().isEmpty()) {
                Equipment retrieveEquipment = this.findByEquipmentName(equipment.getEquipmentName(), true);
                final String currentLoggedUsername=this.securityService.getLoggedInUsername();
                if (retrieveEquipment == null) {
                    LOG.info("Equipment Found for Insert");

                    // associating the Equipment Type with Equipment.
                    if (equipment.getEquipmentType() != null
                            && equipment.getEquipmentType().getEquipmentTypeName() != null
                            && !equipment.getEquipmentType().getEquipmentTypeName().isEmpty()) {
                        LOG.info("Inserting the Equipment Type for Equipment ");
                        EquipmentType equipmentType = equipment.getEquipmentType();
                        equipmentType = this.equipmentTypeDAO.findByEquipmentTypeName(equipmentType.getEquipmentTypeName());
                        equipment.setEquipmentType(equipmentType);
                    } else {
                        throw new LucasRuntimeException("EquipmentType is not Found " + equipment.getEquipmentType().getEquipmentTypeName());
                    }

                    //Associating the User to the Equipment
                    if (equipment.getUser() != null && !equipment.getUser().getUserName().isEmpty()) {
                        final User user = this.userDAO.findByUserName(equipment.getUser().getUserName());
                        equipment.setUser(user);
                    }

                    //setting the audit columns values
                    equipment.setCreatedDateTime(new Date());
                    equipment.setCreatedByUsername(currentLoggedUsername);
                    equipment.setUpdatedDateTime(new Date());
                    equipment.setUpdatedByUsername(currentLoggedUsername);
                    retrieveEquipment = this.equipmentDAO.save(equipment);
                    LOG.info("Persisted the Equipment " + retrieveEquipment);

                    // Associating the EquipmentPosition with Equipment
                    if (equipment.getEquipmentPositionList() != null && !equipment.getEquipmentPositionList().isEmpty()) {
                        LOG.info("Inserting the Equipment Position for Equipment ");
                        final List<EquipmentPosition> equipmentPositionList = equipment.getEquipmentPositionList();
                        final List<EquipmentPosition> savedEquipmentPositionList = new ArrayList<EquipmentPosition>();
                        for (EquipmentPosition equipmentPosition : equipmentPositionList) {
                            equipmentPosition.setCreatedDateTime(new Date());
                            equipmentPosition.setCreatedByUsername(currentLoggedUsername);
                            equipmentPosition.setUpdatedDateTime(new Date());
                            equipmentPosition.setUpdatedByUsername(currentLoggedUsername);
                            equipmentPosition.setEquipment(equipment);
                            final EquipmentPosition equipmentPositionPersisted = this.equipmentPositionDAO.save(equipmentPosition);
                            String logMsg=(equipmentPositionPersisted != null)
                                    ? " EquipmentTypePosition Persisted for the Equipment " + equipmentPositionPersisted
                                    : " EquipmentTypePosition Not Persisted for the Equipment " + equipmentPositionPersisted;
                            LOG.info(logMsg);
                            savedEquipmentPositionList.add(equipmentPositionPersisted);
                        }
                        retrieveEquipment.setEquipmentPositionList(savedEquipmentPositionList);
                        retrieveEquipment.setEquipmentPositions(new Long(savedEquipmentPositionList.size()));

                    } else {
                        throw new LucasRuntimeException("EquipmentPosition is not Found " + equipment.getEquipmentType().getEquipmentTypeName());
                    }


                    //setting the audit columns values
                    retrieveEquipment.setUpdatedDateTime(new Date());
                    retrieveEquipment.setUpdatedByUsername(currentLoggedUsername);
                    final boolean result = this.equipmentDAO.saveEquipment(retrieveEquipment);
                    LOG.info((result) ? "Equipment is  Persisting" + retrieveEquipment : "Equipment isn't  Persisting" + retrieveEquipment);
                    return result;
                } else {
                    LOG.info("Equipment Found for Update");


                    retrieveEquipment.setEquipmentDescription(equipment.getEquipmentDescription());

                    if (equipment.getEquipmentType() != null
                            && equipment.getEquipmentType().getEquipmentTypeName() != null
                            && !equipment.getEquipmentType().getEquipmentTypeName().isEmpty()) {
                        LOG.info("Updating the Equipment Type for Equipment ");
                        EquipmentType equipmentType = equipment.getEquipmentType();
                        equipmentType = this.equipmentTypeDAO.findByEquipmentTypeName(equipmentType.getEquipmentTypeName());
                        retrieveEquipment.setEquipmentType(equipmentType);
                    } else {
                        throw new LucasRuntimeException("EquipmentType is not Found " + equipment.getEquipmentType().getEquipmentTypeName());
                    }

                    String logMsg=this.deleteEquipmentPositionByEquipmentId(retrieveEquipment.getEquipmentId())
                            ? "EquipmentPosition are deleted for Equipment " + retrieveEquipment.getEquipmentId()
                            : "EquipmentPosition are not deleted for Equipment " + retrieveEquipment.getEquipmentId();
                    //cleaning the equipment  position for equipment type
                    LOG.info(logMsg);

                    // Associating the EquipmentPosition with Equipment
                    if (equipment.getEquipmentPositionList() != null && !equipment.getEquipmentPositionList().isEmpty()) {
                        LOG.info("Inserting the Equipment Position for Equipment ");
                        final List<EquipmentPosition> equipmentPositionList = equipment.getEquipmentPositionList();
                        final List<EquipmentPosition> savedEquipmentPositionList = new ArrayList<EquipmentPosition>();
                        for (EquipmentPosition equipmentPosition : equipmentPositionList) {
                            equipmentPosition.setCreatedDateTime(new Date());
                            equipmentPosition.setCreatedByUsername(currentLoggedUsername);
                            equipmentPosition.setUpdatedDateTime(new Date());
                            equipmentPosition.setUpdatedByUsername(currentLoggedUsername);
                            equipmentPosition.setEquipment(retrieveEquipment);
                            final EquipmentPosition equipmentPositionPersisted = this.equipmentPositionDAO.save(equipmentPosition);
                            logMsg=(equipmentPositionPersisted != null)
                                    ? " EquipmentPosition Persisted for the Equipment " + equipmentPositionPersisted
                                    : " EquipmentPosition Not Persisted for the Equipment " + equipmentPositionPersisted;
                            LOG.info(logMsg);
                            savedEquipmentPositionList.add(equipmentPositionPersisted);
                        }
                        retrieveEquipment.setEquipmentPositionList(savedEquipmentPositionList);
                        retrieveEquipment.setEquipmentPositions(new Long(savedEquipmentPositionList.size()));
                    } else {
                        throw new LucasRuntimeException("EquipmentPosition is not Found " + equipment.getEquipmentType().getEquipmentTypeName());
                    }

                    //Associating the User to the Equipment
                    if (equipment.getUser() != null && !equipment.getUser().getUserName().isEmpty()) {
                        final User user = this.userDAO.findByUserName(equipment.getUser().getUserName());
                        retrieveEquipment.setUser(user);
                    }

                    //setting the audit columns values
                    retrieveEquipment.setUpdatedDateTime(new Date());
                    retrieveEquipment.setUpdatedByUsername(currentLoggedUsername);
                    final boolean result = this.equipmentDAO.saveEquipment(retrieveEquipment);
                    LOG.info((result) ? "Equipment is  Persisting" + retrieveEquipment : "Equipment isn't  Persisting" + retrieveEquipment);
                    return result;
                }
            } else {
                throw new LucasRuntimeException("Equipment Name isn't Found ");
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipments(List<Equipment> equipmentList) {
        return this.equipmentDAO.saveEquipments(equipmentList);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeId(Long equipmentTypeId) {
        return this.equipmentTypeDAO.findByEquipmentTypeId(equipmentTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentType findByEquipmentTypeName(String equipmentTypeName) {
        return this.equipmentTypeDAO.findByEquipmentTypeName(equipmentTypeName);
    }

    /* (non-Javadoc)
    * @see com.lucas.services.equipment.EquipmentService#deleteEquipmentPositionByEquipmentTypeId()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteEquipmentPositionByEquipmentId(final Long equipmentId) {
        return this.equipmentPositionDAO.deleteEquipmentPositionByEquipmentId(equipmentId);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentPositions(List<EquipmentPosition> equipmentPositions) {
        try {
            if (equipmentPositions != null) {
                for (EquipmentPosition equipmentPosition : equipmentPositions) {
                    this.equipmentPositionDAO.save(equipmentPosition);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentType(EquipmentType equipmentType) {
        return this.equipmentTypeDAO.saveEquipmentType(equipmentType);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean saveEquipmentTypes(List<EquipmentType> equipmentTypeList) {
        return this.equipmentTypeDAO.saveEquipmentTypes(equipmentTypeList);
    }

    @Transactional(readOnly = true)
    @Override
    public EquipmentPosition findEquipmentPositionById(Long equipmentPositionId) {
        return this.equipmentPositionDAO.findEquipmentPositionById(equipmentPositionId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EquipmentPosition> findEquipmentPositionByEquipmentId(Long equipmentId) {
        return this.equipmentPositionDAO.findEquipmentPositionByEquipmentId(equipmentId);
    }


    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentService#getEquipmentListBySearchAndSortCriteria()
     */
    @Transactional(readOnly = true)
    @Override
    public List<Equipment> getEquipmentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws  Exception {

        final List<Equipment> equipmentList = this.equipmentDAO.getEquipmentListBySearchAndSortCriteria(searchAndSortCriteria);
        for (Equipment equipment : equipmentList) {
            final List<EquipmentPosition> equipmentPosition = this.equipmentPositionDAO.findEquipmentPositionByEquipmentId(equipment.getEquipmentId());
            equipment.setEquipmentPositionList(equipmentPosition);
             if(equipment.getUser()!=null){
                 final User user = this.userDAO.findByUserName(equipment.getUser().getUserName());
                 equipment.setUser(user);
             }
            final EquipmentType equipmentType = this.equipmentTypeDAO.findByEquipmentTypeName(equipment.getEquipmentType().getEquipmentTypeName());
            equipment.setEquipmentType(equipmentType);
        }
        return equipmentList;
    }

    /* (non-Javadoc)
    * @see com.lucas.services.equipment.EquipmentService#getTotalCountForSearchAndSortCriteria()
    */
    @Transactional(readOnly = true)
    @Override
    public Long getTotalCountForSearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws  Exception {
        return this.equipmentDAO.getTotalCountForSearchAndSortCriteria(searchAndSortCriteria);
    }



    /*
    *
    * (non-Javadoc)
    * @see com.lucas.services.equipment.EquipmentService#deleteEquipmentByName()
    */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteEquipmentByName(final String equipmentName) {
        if (equipmentName != null && !equipmentName.isEmpty()) {
            final Equipment equipment = this.findByEquipmentName(equipmentName, true);
            if (equipment != null) {
                if (equipment.getUser() != null) {
                    throw new RuntimeException("Equipment In Use");
                } else {
                    equipment.setUser(null);
                }
                equipment.setEquipmentStatus(DELETE_STATUS);
                return (this.equipmentDAO.save(equipment) != null ? true : false);
            }
            return false;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.lucas.services.equipment.EquipmentService#unAssignUserFromEquipment()
     */
    @Override
    @Transactional(readOnly = false)
    public boolean unAssignUserFromEquipment(String equipmentName, String userName) {
        if (equipmentName != null && !equipmentName.isEmpty() && userName != null && !userName.isEmpty()) {
            final Equipment equipment = this.findByEquipmentName(equipmentName, true);
            if (equipment != null) {
                if (equipment.getUser() == null) {
                    throw new RuntimeException("Equipment Doesn't Assign to User");
                } else {
                    equipment.setUser(null);
                }
                return (this.equipmentDAO.save(equipment) != null ? true : false);
            }
            return false;
        }
        return false;
    }
}
