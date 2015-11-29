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

package com.lucas.entity.application;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The primary key class for the Code_Lookup database table.
 *
 * @author DiepLe
 */

@Embeddable
public class CodeLookupPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    private String codeName;
    private String codeValue;

    @Column(name="code_name", nullable = false, length = 50)
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Column(name="code_value", nullable = false, length = 50)
    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeLookupPK that = (CodeLookupPK) o;

        if (!codeName.equals(that.codeName)) return false;
        if (!codeValue.equals(that.codeValue)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codeName.hashCode();
        result = 31 * result + codeValue.hashCode();
        return result;
    }
}