/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.entity.ui.canvas.CanvasType;

import com.lucas.entity.ui.viewconfig.ActualViewConfig;
import com.lucas.entity.ui.widgetinstance.DefaultWidgetInstance;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.ui.canvas.Canvas;
import com.lucas.entity.ui.widgetinstance.WidgetInstance;
import com.lucas.entity.user.User;

@Repository
public class CanvasDAOImpl extends ResourceDAO<Canvas> implements CanvasDAO {
    @Override
    public Canvas findByCanvasId(Long canvasId) {
        return load(canvasId);
    }

    @Override
    public Canvas getCanvasByName(String canvasName) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(Canvas.class);

        criteria.add(Restrictions.eq("name", canvasName).ignoreCase());

        Canvas canvas = (Canvas) criteria.uniqueResult();

        return canvas;
    }

    /**
     *  getCanvasByType() provide the implementation of the above
     *  specification defined.
     *
     * @param canvasType accept the instance of the
     *                   com.lucas.entity.ui.canvas.CanvasType
     *                   Containing the canvas type.
     *  @return instance of the com.lucas.entity.ui.canvas.Canvas
     *          from database
     */
    @Override
    public Canvas getCanvasByType(CanvasType canvasType) {
        final Session session = getSession();
        final Criteria criteria = session.createCriteria(Canvas.class);
        criteria.add(Restrictions.eq("canvas_type", canvasType.getName()).ignoreCase());
        final Object object = criteria.uniqueResult();
         Canvas canvas=new Canvas();
        if (object != null) {
             canvas = (Canvas) object;
            return canvas;
        }
        return canvas;
    }


    @Override
    public Set<User> getUserSetByCanvasId(Long canvasId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(WidgetInstance.class);
        criteria.createAlias("canvas", "c");
        criteria.add(Restrictions.eq("c.canvasId", canvasId));
        criteria.setFetchMode("userSet", FetchMode.JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return new HashSet<User>(criteria.list());
    }

    /**
     * deleteCanvas() provide the implementation for
     * deletion the canvas of persistence state
     * from the database.
     *
     * @param canvasId it accept the java.lang.Long
     *               instance containing the canvasId or canvasName
     *               or canvasType for the basics for deletion
     *               form database.
     * @return the instance of java.lang.Boolean containing the
     * true is the operation is successful or false if the
     * operation is unsuccessful.
     */
    @Override
    public Boolean deleteCanvas(Long canvasId) {
        Canvas canvas;
        if (canvasId != null) {
            canvas = this.findByCanvasId(canvasId);
            super.delete(canvas);
            return Boolean.TRUE;
        }  else {
            return Boolean.FALSE;
        }
    }

	/* (non-Javadoc)
	 * @see com.lucas.dao.ui.CanvasDAO#getUserPrivateCanvases(java.lang.String)
	 */
	@Override
	public List<Canvas> getUserPrivateCanvases(String username) {
		final Session session = getSession();
		Conjunction and = Restrictions.conjunction();
		final Criteria criteria = session.createCriteria(Canvas.class);
		and.add(Restrictions.eq("createdByUserName", username));

				and.add(Restrictions.eq("canvasType", CanvasType.PRIVATE));

		criteria.add(and);

		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see com.lucas.dao.ui.CanvasDAO#getCanvasesByType(com.lucas.entity.ui.canvas.CanvasType)
	 */
	@Override
	public List<Canvas> getCanvasesByType(CanvasType canvasType) {
		final Session session = getSession();
		final Criteria criteria = session.createCriteria(Canvas.class);

				criteria.add(Restrictions.eq("canvasType", canvasType));

		return criteria.list();
	}
}
