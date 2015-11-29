/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.user;

import com.lucas.entity.user.SupportedLanguage;

import java.text.ParseException;
import java.util.List;


public interface SupportedLanguageService {

    SupportedLanguage findOneLanguageByCode(String languageCode);
    List<SupportedLanguage> findAllLanguages();
    List<String> findAllLanguageCodes()  throws ParseException;

}
