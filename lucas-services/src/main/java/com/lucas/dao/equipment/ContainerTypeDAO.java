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
package com.lucas.dao.equipment;


import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.equipment.ContainerType;

import java.util.List;

/**
 * @Author Adarsh
 * Updated By: Adarsh kumar
 * Created On Date: 6/15/15  Time: 00:46 AM
 *  This Class provide the specification for Operations can be done on ContainerType
 */
public interface ContainerTypeDAO extends GenericDAO<ContainerType> {

    /**
     * findByContainerTypeCode() provide the specification for fetching ContainerType
     * based on the containerTypeCode.
     *
     * @param containerTypeCode filter param
     * @return ContainerType instance having persisting state.
     */
    public ContainerType findByContainerTypeCode(String containerTypeCode);

    /**
     * getAllContainerType() provide the specification for fetching all the ContainerType
     * from the db without applying any filter.
     *
     * @return list of the ContainerType
     */
    public List<ContainerType> getAllContainerType();
}
