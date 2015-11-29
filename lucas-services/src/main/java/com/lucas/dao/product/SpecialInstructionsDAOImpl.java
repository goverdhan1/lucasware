package com.lucas.dao.product;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.product.Product;
import com.lucas.entity.product.SpecialInstructions;
import com.lucas.services.util.CollectionsUtilService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Adarsh on 3/13/15.
 */
@Repository
public class SpecialInstructionsDAOImpl extends ResourceDAO<SpecialInstructions>
        implements SpecialInstructionsDAO {

    @Override
    public List<SpecialInstructions> getSpecialInstructionsListForProduct(String productName) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(SpecialInstructions.class);
        criteria.createAlias("product", "p");
        criteria.add(Restrictions.eq("p.name", productName));
        final List<SpecialInstructions> specialInstructions=(List<SpecialInstructions>) criteria.list();
        return specialInstructions;
    }
}
