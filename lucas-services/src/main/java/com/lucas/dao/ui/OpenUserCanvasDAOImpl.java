/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.dao.ui.OpenUserCanvasDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.canvas.OpenUserCanvas;
import com.lucas.entity.user.Shift;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OpenUserCanvasDAOImpl extends ResourceDAO<OpenUserCanvas> implements OpenUserCanvasDAO {




    @Override
    public OpenUserCanvas findOneOpenUserCanvasId(Long userId, Long canvasId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(Shift.class);

        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("canvasId", canvasId));

        return (OpenUserCanvas) criteria.uniqueResult();

    }
    
    @Override
    public OpenUserCanvas findExistingOpenUserCanvas(Long userId, Canvas canvas) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(OpenUserCanvas.class);

        criteria.add(Restrictions.eq("userId", userId));
        criteria.add(Restrictions.eq("canvas", canvas));

        return (OpenUserCanvas) criteria.uniqueResult();

    }
    
    

    @Override
    public List findAllOpenUserCanvases() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(OpenUserCanvas.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }
}
