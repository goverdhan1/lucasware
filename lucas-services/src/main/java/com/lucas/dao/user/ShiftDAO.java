/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.dao.user;

import com.lucas.entity.user.Shift;

import java.text.ParseException;
import java.util.List;

public interface ShiftDAO {
	
    Shift findOneByShiftName(String shiftName);
    Shift findOneByShiftId(Long shiftId);
    List findAllShifts();
    public List<String> findAllShiftNames()  throws ParseException;

}
