/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.dao.support.ResourceDAO;
import com.lucas.entity.user.SupportedLanguage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

@Repository
public class SupportedLanguageDAOImpl extends ResourceDAO<SupportedLanguage> implements SupportedLanguageDAO {

    @Override
    public SupportedLanguage findOneLanguageByCode(String languageCode) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(SupportedLanguage.class);

        criteria.add(Restrictions.eq("languageCode", languageCode));

        return (SupportedLanguage) criteria.uniqueResult();
    }

    @Override
    public List<SupportedLanguage> findAllLanguages() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(SupportedLanguage.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

    @Override
    public List<String> findAllLanguageCodes() throws ParseException {

        Session session = getSession();

        Criteria criteria = session.createCriteria(SupportedLanguage.class);
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("languageCode")));

        return criteria.list();
        }
}
