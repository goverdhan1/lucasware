/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.user;

import com.lucas.entity.user.Shift;

import java.text.ParseException;
import java.util.List;


public interface ShiftService {

	Shift findOneByShiftId(Long shiftId);
	Shift findOneByShiftName(String shiftName);
    List<Shift> findAllShifts();
    List<String> findAllShiftNames() throws ParseException;

}
