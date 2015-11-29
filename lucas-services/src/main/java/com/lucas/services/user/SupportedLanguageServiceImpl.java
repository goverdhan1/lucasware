/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.user;

import com.lucas.dao.user.SupportedLanguageDAO;
import com.lucas.entity.user.SupportedLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

@Service("supportedLanguageService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class SupportedLanguageServiceImpl implements SupportedLanguageService {

	private static Logger LOG = LoggerFactory.getLogger(SupportedLanguageServiceImpl.class);
    private SupportedLanguageDAO supportedLanguageDAO;

    @Inject
    public SupportedLanguageServiceImpl(SupportedLanguageDAO supportedLanguageDAO) {
        this.supportedLanguageDAO = supportedLanguageDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public SupportedLanguage findOneLanguageByCode (String languageCode) {
        return supportedLanguageDAO.findOneLanguageByCode(languageCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportedLanguage> findAllLanguages() {
        return supportedLanguageDAO.findAllLanguages();
    }

    @Override
    public List<String> findAllLanguageCodes() throws ParseException {
        return supportedLanguageDAO.findAllLanguageCodes();
    }
}