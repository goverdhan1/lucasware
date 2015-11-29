/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.services.user;

import com.lucas.dao.user.ShiftDAO;
import com.lucas.entity.user.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

@Service("shiftService")
@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
public class ShiftServiceImpl implements ShiftService {

	private static Logger LOG = LoggerFactory.getLogger(ShiftServiceImpl.class);
    private ShiftDAO shiftDAO;


    @Inject
    public ShiftServiceImpl(ShiftDAO shiftDAO) {
        this.shiftDAO = shiftDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Shift findOneByShiftId (Long shiftId) {
        return shiftDAO.findOneByShiftId(shiftId);
    }

    @Override
    @Transactional(readOnly = true)
    public Shift findOneByShiftName(String shiftName) {
        return shiftDAO.findOneByShiftName(shiftName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shift> findAllShifts() {
        return shiftDAO.findAllShifts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllShiftNames() throws ParseException {
        return shiftDAO.findAllShiftNames();
    }
}
