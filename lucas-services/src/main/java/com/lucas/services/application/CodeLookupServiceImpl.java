/*
Lucas Systems Inc
11279 Perry Highway
Wexford
PA 15090
United States


The information in this file contains trade secrets and confidential
information which is the property of Lucas Systems Inc.

All trademarks, trade names, copyrights and other intellectual property
rights created, developed, embodied in or arising in connection with
this software shall remain the sole property of Lucas Systems Inc.

Copyright (c) Lucas Systems, 2014
ALL RIGHTS RESERVED

*/
package com.lucas.services.application;

import com.lucas.dao.application.CodeLookupDAO;
import com.lucas.exception.CodeLookupDoesNotExistException;
import com.lucas.exception.CodeLookupInvalidException;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.util.CollectionsUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

@Service("codeLookupService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class CodeLookupServiceImpl implements CodeLookupService {

	private static Logger LOG = LoggerFactory.getLogger(CodeLookupServiceImpl.class);
	private final CodeLookupDAO codeLookupDAO;

	@Inject
	public CodeLookupServiceImpl(CodeLookupDAO codeLookupDAO) {
		this.codeLookupDAO = codeLookupDAO;
	}

    /**
     * Get the code lookup information for frontend widgets
     *
     * @author DiepLe
     * @param codeLookupList
     * @return resultMap
     * @exception throws CodeLookupDoesNotExistException, CodeLookupInvalidException, LucasRuntimeException
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getCodeLookupData(List<String> codeLookupList) {
        Map<String, Object> resultObj = new LinkedHashMap<String, Object>();

        if (codeLookupList == null || codeLookupList.size() == 0){
            throw new CodeLookupDoesNotExistException(String.format("No lookup code exists for empty list"));
        }

        List<Object[]> codeLookupResult = null;

        for (String codeName : CollectionsUtilService.nullGuard(codeLookupList)) {
            try {
                codeLookupResult = codeLookupDAO.getCodeLookupData(codeName);

                if (codeLookupResult == null || codeLookupResult.size() == 0){
                    if(codeName == null || !codeName.isEmpty()){
                        // invalid lookup code is passed in
                        LOG.debug("Exception - lookup codes does not exist");
                        throw new CodeLookupInvalidException(String.format("Invalid lookup code name %s.", codeName));
                    }
                    // lookup codes are blanks
                    LOG.debug("Exception - lookup codes does not exist");
                    throw new CodeLookupDoesNotExistException(String.format("No lookup codes exists with code name %s.", codeName));
                }

                // massage the data in the required formats
                List<Map<String, Object>> massageData = new ArrayList<Map<String, Object>>();

                for (Object[] o : codeLookupResult) {
                    Map<String, Object> temp = new HashMap<String, Object>();
                    temp.put("key", o[0].toString());
                    temp.put("value", o[1].toString());
                    temp.put("displayOrder", o[2].toString());
                    massageData.add(temp);
                }

                resultObj.put(codeName, massageData);

            } catch (CodeLookupDoesNotExistException e) {
                LOG.debug("Exception UserDoesNotExistException: when calling codeLookupService.getCodeLookupData()");
                throw e;
            }catch (CodeLookupInvalidException e) {
                LOG.debug("Exception CodeLookupInvalidException: when calling codeLookupService.getCodeLookupData()");
                throw e;
            }
            catch (Exception e) {
                LOG.debug("Exception when calling codeLookupDAO.getCodeLookupData()");
                throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
            }
        }

        return resultObj;
    }


    /**
     * Get the code lookup values based on the passed in key
     *
     * @author DiepLe
     * @param lookupKey
     * @return
     */
    @Override
    public List<String> getCodeLookupValueBySingleKey(String lookupKey) {

        List<String> codeLookupResult = new ArrayList<String>();

        try {
            codeLookupResult = codeLookupDAO.getCodeLookupValuesBySingleKey(lookupKey);
            return codeLookupResult;
        } catch (CodeLookupDoesNotExistException e) {
            LOG.debug("Exception UserDoesNotExistException: when calling codeLookupService.getCodeLookupData()");
        } catch (CodeLookupInvalidException e) {
            LOG.debug("Exception CodeLookupInvalidException: when calling codeLookupService.getCodeLookupData()");
        } catch (Exception e) {
            LOG.debug("Exception when calling codeLookupDAO.getCodeLookupData()");
        }

        return codeLookupResult;
    }
}