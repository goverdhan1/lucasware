/**
 * Copyright (c) Lucas Systems LLC
 */
package com.lucas.alps.api;

import com.lucas.alps.rest.ApiResponse;
import com.lucas.entity.user.Shift;
import com.lucas.exception.LucasRuntimeException;
import com.lucas.services.user.ShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scala.util.parsing.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping(value="/shifts")
public class ShiftController {

	private static final Logger LOG = LoggerFactory.getLogger(ShiftController.class);
	private final ShiftService shiftService;
    private final String SHIFTID = "id";
    private final String SHIFTNAME = "name";

    @Inject
	public ShiftController(ShiftService shiftService) {
		this.shiftService = shiftService;
	}

    /**
     * POST  /shifts/data -> get one shift record by Id or name
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Shift> getOneShift(@RequestBody Map<String,String> map) {

        String shiftId = map.get(SHIFTID);
        String shiftName = map.get(SHIFTNAME);

        final ApiResponse <Shift> apiResponse = new ApiResponse<Shift>();

        Shift shift = null;

        try {
            if (shiftId != null && !shiftId.isEmpty()) {
                shift = shiftService.findOneByShiftId(Long.valueOf(shiftId));
            }
            else if (shiftName != null && !shiftName.isEmpty()) {
                shift = shiftService.findOneByShiftName(shiftName);
            }

            apiResponse.setData(shift);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;
    }


    /**
     * GET  /shifts -> get all the shifts records.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<Shift>> getAllShifts() {
        LOG.debug("REST request to get all Shifts");
        final ApiResponse <List <Shift>> apiResponse = new ApiResponse <List<Shift>>();

        List<Shift> shiftList = null;

        try {
            shiftList = shiftService.findAllShifts();
            apiResponse.setData(shiftList);

        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        LOG.debug("Size{}", shiftList.size());
        return apiResponse;
    }

    /**
     * GET  -> get all the shift name records.
     */
    @RequestMapping(value = "/names",
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<String>> findAllShiftNames() throws ParseException {
        LOG.debug("REST request to get all shift names");

        final ApiResponse <List <String>> apiResponse = new ApiResponse <List<String>>();

        List<String> shiftName = null;

        try {
            shiftName = shiftService.findAllShiftNames();
            apiResponse.setData(shiftName);
            LOG.debug("Size{}", shiftName.size());
        } catch (Exception e) {
            throw new LucasRuntimeException(LucasRuntimeException.INTERNAL_ERROR, e);
        }

        return apiResponse;

    }

}