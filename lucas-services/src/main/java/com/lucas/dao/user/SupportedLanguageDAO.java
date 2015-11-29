/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.entity.user.SupportedLanguage;

import java.text.ParseException;
import java.util.List;

public interface SupportedLanguageDAO {
	
    SupportedLanguage findOneLanguageByCode(String languageCode);
    List findAllLanguages();
    List findAllLanguageCodes() throws ParseException;;

}
