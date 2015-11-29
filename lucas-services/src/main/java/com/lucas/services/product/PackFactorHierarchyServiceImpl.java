/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.product;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.product.PackFactorHierarchyDAO;
import com.lucas.entity.product.PackFactorHierarchy;
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
 * Service class for pack factor hierarchy entity object
 * @author DiepLe
 *
 */



@Service("packFactorHierarchyService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class PackFactorHierarchyServiceImpl implements PackFactorHierarchyService {


    private static Logger LOG = LoggerFactory.getLogger(PackFactorHierarchyServiceImpl.class);
    private final PackFactorHierarchyDAO packFactorHierarchyDAO;

    @Inject
    public PackFactorHierarchyServiceImpl(PackFactorHierarchyDAO packFactorHierarchyDAO) {
        this.packFactorHierarchyDAO = packFactorHierarchyDAO;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PackFactorHierarchy> getPackFactorHierarchyListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws JsonParseException, JsonMappingException, IOException, ParseException {

        return packFactorHierarchyDAO.getPackFactorHierarchyListBySearchAndSortCriteria(searchAndSortCriteria);
    }

}
