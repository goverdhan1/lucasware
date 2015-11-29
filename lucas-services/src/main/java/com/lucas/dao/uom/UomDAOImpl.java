package com.lucas.dao.uom;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.uom.Uom;

@Repository
public class UomDAOImpl extends ResourceDAO<Uom> implements UomDAO { 

	@Override
	public Uom findUomByName(String uomName){
		Session session = getSession();
		Criteria criteria = session.createCriteria(Uom.class).
				setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("name", uomName));
		
		Uom uom = (Uom) criteria.uniqueResult();
		return uom;
	}
}
