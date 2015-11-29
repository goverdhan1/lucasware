package com.lucas.dao.product;

import com.lucas.dao.support.GenericDAO;
import com.lucas.entity.product.SpecialInstructions;

import java.util.List;

/**
 * Created by Adarsh on 3/13/15.
 */
public interface SpecialInstructionsDAO extends GenericDAO<SpecialInstructions> {

    public List<SpecialInstructions> getSpecialInstructionsListForProduct(String productName);
}
