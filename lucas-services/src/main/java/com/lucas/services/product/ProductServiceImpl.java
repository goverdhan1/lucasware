/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.product;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;

import com.lucas.dao.product.ProductCustomClassificationDAO;
import com.lucas.dao.product.SpecialInstructionsDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lucas.dao.product.ProductDAO;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.ProductCustomClassification;
import com.lucas.entity.product.SpecialInstructions;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.services.search.SearchAndSortCriteria;
import com.lucas.services.user.UserServiceImpl;
import com.lucas.services.util.CollectionsUtilService;

@Service("productService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService {

    private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final ProductDAO productDAO;
    private SpecialInstructionsDAO specialInstructionsDAO;
    
    private ProductCustomClassificationDAO productCustomClassificationDAO;

    @Inject
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Inject
    public void setSpecialInstructionsDAO(SpecialInstructionsDAO specialInstructionsDAO) {
        this.specialInstructionsDAO = specialInstructionsDAO;
    }

    @Inject
    public void setProductCustomClassificationDAO(ProductCustomClassificationDAO productCustomClassificationDAO) {
      this.productCustomClassificationDAO = productCustomClassificationDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductListBySearchAndSortCriteria(SearchAndSortCriteria searchAndSortCriteria)
            throws JsonParseException, JsonMappingException, IOException, ParseException {
        return this.getFullyHydratedProductList(productDAO.getProductListBySearchAndSortCriteria(searchAndSortCriteria));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getFullyHydratedProductList(List<Product> productList) throws JsonParseException, JsonMappingException, IOException {
        for (Product product : productList) {
            if (product != null) {
                // Logic to load collections  (here specialinstructionslist which is a collection is missing)
                final List<SpecialInstructions> specialInstructionsList = this.getSpecialInstructionsListForProduct(product.getName());
                product.setSpecialInstructionsList(specialInstructionsList);
            }
        }
        return productList;
    }

    @Transactional(readOnly = true)
    private List<SpecialInstructions> getSpecialInstructionsListForProduct(String productName) {
        final List<SpecialInstructions> specialInstructions = specialInstructionsDAO.getSpecialInstructionsListForProduct(productName);
        return specialInstructions;
    }
    
    /**
     * This method returns list of data for ProductCustomClassification from DAO.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductCustomClassification> getAllProductCustomClassification() throws ParseException {
      return productCustomClassificationDAO.getAllProductCustomClassification();
    }

    /**
     * This method returns data for ProductCustomClassification using custom classification field name.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductCustomClassification getProductCustomClassificationByCustomClassificationFieldName(String customClassificationFieldName) {
      return productCustomClassificationDAO.getProductCustomClassificationByCustomClassificationFieldName(customClassificationFieldName);
    }


}
