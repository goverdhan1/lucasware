/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.user.Shift;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

@Repository
public class ShiftDAOImpl extends ResourceDAO<Shift> implements ShiftDAO {

    @Override
    public Shift findOneByShiftName(String shiftName) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(Shift.class);

        criteria.add(Restrictions.eq("shiftName", shiftName));

        return (Shift) criteria.uniqueResult();
    }

    @Override
    public Shift findOneByShiftId(Long shiftId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(Shift.class).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("shiftId", shiftId));

        return (Shift) criteria.uniqueResult();
    }

    @Override
    public List<Shift> findAllShifts() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(Shift.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

    @Override
    public List<String> findAllShiftNames() throws ParseException {

        Session session = getSession();

        Criteria criteria = session.createCriteria(Shift.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("shiftName")));

        return criteria.list();
    }
}
