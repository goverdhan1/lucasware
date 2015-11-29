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
package com.lucas.services.product;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.product.PackFactorComponentDAO;
import com.lucas.entity.product.PackFactorComponent;
import com.lucas.entity.user.Shift;
import com.lucas.services.search.SearchAndSortCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * Service class for pack factor component entity object
 * @author DiepLe
 *
 */


@Service("packFactorComponentService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class PackFactorComponentServiceImpl implements PackFactorComponentService {

	private static Logger LOG = LoggerFactory.getLogger(PackFactorComponentServiceImpl.class);
    private PackFactorComponentDAO packFactorComponentDAO;


    @Inject
    public PackFactorComponentServiceImpl(PackFactorComponentDAO packFactorComponentDAO) {
        this.packFactorComponentDAO = packFactorComponentDAO;
    }

    @Override
    public List<PackFactorComponent> getPackFactorComponentListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws JsonParseException, JsonMappingException, IOException, ParseException {

        return packFactorComponentDAO.getPackFactorComponentListBySearchAndSortCriteria(searchAndSortCriteria);
    }

}
