/*
 * Lucas Systems Inc 11279 Perry Highway Wexford PA 15090 United States
 * 
 * 
 * The information in this file contains trade secrets and confidential information which is the
 * property of Lucas Systems Inc.
 * 
 * All trademarks, trade names, copyrights and other intellectual property rights created,
 * developed, embodied in or arising in connection with this software shall remain the sole property
 * of Lucas Systems Inc.
 * 
 * Copyright (c) Lucas Systems, 2014 ALL RIGHTS RESERVED
 */
package com.lucas.services.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.MutablePair;

import com.lucas.entity.user.SupportedLanguage;
import com.lucas.entity.user.User;
import com.lucas.exception.LucasRuntimeException;

public class MultiCompare {

    private static Boolean multiComapreResultNull = Boolean.FALSE;
    private static Boolean multiComapreResultNullAndDifferent = Boolean.FALSE;
    private static Boolean multiComapreResultNotNullAndDifferent = Boolean.FALSE;
    private static Boolean multiComapreResultEqual = Boolean.FALSE;
    private static Boolean multiComapreResultAllNull = Boolean.FALSE;

    public static void reset() {
        multiComapreResultNull = Boolean.FALSE;
        multiComapreResultNullAndDifferent = Boolean.FALSE;
        multiComapreResultNotNullAndDifferent = Boolean.FALSE;
        multiComapreResultEqual = Boolean.FALSE;
    }


    public static Boolean isMultiComapreResultNull() {
        return multiComapreResultNull;
    }


    public static Boolean isMultiComapreResultNullAndDifferent() {
        return multiComapreResultNullAndDifferent;
    }


    public static Boolean isMultiComapreResultNotNullAndDifferent() {
        return multiComapreResultNotNullAndDifferent;
    }

    public static Boolean isMultiComapreResultEqual() {
        return multiComapreResultEqual;
    }

    public static Map<String, String> getMutablePairMap(List<User> domainObjectList, List<String> propertyToCompareList) throws IntrospectionException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, ParseException, LucasRuntimeException {
        Object valueToBeSet = null;
        Map<String, String> editableFields = new HashMap<String, String>();
        Object previous = null;
        Object curr = null;
        Object next = null;

        for (String propertyToCompare : propertyToCompareList) {
            multiComapreResultAllNull = Boolean.TRUE;
            for (int i = 0; i < domainObjectList.size() - 1; i++) {

                if (previous == null) {
                    try {
                        curr = new PropertyDescriptor(propertyToCompare, domainObjectList.get(0).getClass()).getReadMethod().invoke(domainObjectList.get(i));

                        if (curr instanceof String) {
                            curr = curr;
                        } else if (curr instanceof com.lucas.entity.user.SupportedLanguage) {

                            SupportedLanguage s = (SupportedLanguage) new PropertyDescriptor(propertyToCompare, domainObjectList.get(0).getClass()).getReadMethod().invoke(domainObjectList.get(i));
                            if (s != null) {
                                curr = s.getLanguageCode();
                            }

                        }

                    } catch (IllegalArgumentException e) {
                        throw new LucasRuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new LucasRuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new LucasRuntimeException(e);
                    } catch (IntrospectionException e) {
                        throw new LucasRuntimeException(e);
                    }
                } else {
                    curr = previous;
                }
                try {
                    next = new PropertyDescriptor(propertyToCompare, domainObjectList.get(0).getClass()).getReadMethod().invoke(domainObjectList.get(i + 1));

                    if (next instanceof String) {
                        next = next;
                    } else if (next instanceof com.lucas.entity.user.SupportedLanguage) {

                        SupportedLanguage s = (SupportedLanguage) new PropertyDescriptor(propertyToCompare, domainObjectList.get(0).getClass()).getReadMethod().invoke(domainObjectList.get(i + 1));
                        if (s != null) {
                            next = s.getLanguageCode();
                        }

                    }

                } catch (IllegalArgumentException e) {
                    throw new LucasRuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new LucasRuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new LucasRuntimeException(e);
                } catch (IntrospectionException e) {
                    throw new LucasRuntimeException(e);
                }
                // for every member variable of the domain object , do the following
                // check for null
                if (curr == null || next == null) {
                    multiComapreResultNull = Boolean.TRUE;
                    if (i == (domainObjectList.size() - 2)) {
                        multiComapreResultEqual = Boolean.TRUE;
                        valueToBeSet = curr;
                    }
                    if (i >= 0 && curr == null || next == null) {
                        try {
                            previous = new PropertyDescriptor(propertyToCompare, domainObjectList.get(0).getClass()).getReadMethod().invoke(domainObjectList.get(i == 0 ? 0 : i - 1));

                            if (previous instanceof String) {
                                previous = previous;
                            } else if (previous instanceof com.lucas.entity.user.SupportedLanguage) {

                                SupportedLanguage s =
                                        (SupportedLanguage) new PropertyDescriptor(propertyToCompare, domainObjectList.get(0).getClass()).getReadMethod().invoke(
                                                domainObjectList.get(i == 0 ? 0 : i - 1));
                                if (s != null) {
                                    previous = s.getLanguageCode();
                                }

                            }

                        } catch (IllegalArgumentException e) {
                            throw new LucasRuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new LucasRuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new LucasRuntimeException(e);
                        } catch (IntrospectionException e) {
                            throw new LucasRuntimeException(e);
                        }
                        curr = previous;
                    }

                    if (!(i == domainObjectList.size() - 1)) {
                        continue;
                    }

                } else {
                    multiComapreResultAllNull = Boolean.FALSE;
                    valueToBeSet = curr;
                    if (!curr.equals(next)) {
                        multiComapreResultNotNullAndDifferent = Boolean.TRUE;
                        if (isMultiComapreResultNull()) {
                            multiComapreResultNullAndDifferent = Boolean.TRUE;
                        }
                        break;
                    } else {
                        multiComapreResultEqual = Boolean.TRUE;
                        valueToBeSet = curr;
                    }
                }

                if (i == (domainObjectList.size() - 2)) {
                    multiComapreResultEqual = Boolean.TRUE;
                    valueToBeSet = curr;
                    break;
                }
            }


            if (isMultiComapreResultNull() && isMultiComapreResultEqual()) {
                String value = (valueToBeSet == null) ? null : valueToBeSet.toString();
                editableFields.put(propertyToCompare, value);
            }

            if (!isMultiComapreResultNull() && isMultiComapreResultEqual()) {
                editableFields.put(propertyToCompare, valueToBeSet.toString());
            }

            if (isMultiComapreResultNullAndDifferent()) {
                editableFields.put(propertyToCompare, null);
            }

            if (isMultiComapreResultNotNullAndDifferent()) {
                editableFields.put(propertyToCompare, null);
            }

            if (isMultiComapreResultAllNull()) {
                editableFields.put(propertyToCompare, null);
            }

            reset();
        }
        return editableFields;
    }

    public static Boolean isMultiComapreResultAllNull() {
        return multiComapreResultAllNull;
    }
}
