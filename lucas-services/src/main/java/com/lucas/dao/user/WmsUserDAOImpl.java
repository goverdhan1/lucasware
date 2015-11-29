/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.user.WmsUser;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WmsUserDAOImpl extends ResourceDAO<WmsUser> implements WmsUserDAO {

    @Override
    public WmsUser findOneWmsUserById(Long userId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(WmsUser.class).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("userId", userId));

        return (WmsUser) criteria.uniqueResult();
    }

    @Override
    public List<WmsUser> findAllWmsUsers() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(WmsUser.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

}
