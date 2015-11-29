package com.lucas.alps.view;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucas.alps.viewtype.ShiftFormView;

import com.lucas.alps.viewtype.UserFormView;
import com.lucas.entity.user.Shift;

import java.util.Date;


public class ShiftView implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Shift shift;

    public ShiftView() {
        this.shift = new Shift();
    }

    public ShiftView(Shift shift) {
        this.shift = shift;
    }

    @JsonView({ShiftFormView.class, UserFormView.class})
    public Long getShiftId() {
        if (shift != null) {
            return shift.getShiftId();
        } else {
            return null;
        }
    }

    public void setShiftId(Long shiftId) {
        shift.setShiftId(shiftId);
    }

    @JsonView({ShiftFormView.class, UserFormView.class})
    public String getShiftName() {
        if (shift != null) {
            return shift.getShiftName();
        } else {
            return null;
        }
    }

    public void setShiftName(String shiftName) {
        shift.setShiftName(shiftName);
    }

    @JsonView({ShiftFormView.class})
    public String getStartTime() {
        if (shift != null) {
            return shift.getStartTime();
        } else {
            return null;
        }
    }

    public void setStartTime(String startTime) {
        shift.setStartTime(startTime);
    }

    @JsonView({ShiftFormView.class})
    public String getEndTime() {
        if (shift != null) {
            return shift.getEndTime();
        } else {
            return null;
        }
    }

    public void setEndTime(String endTime) {
        shift.setEndTime(endTime);
    }

    @JsonView({ShiftFormView.class})
    public String getCreatedByUserName() {
        if (shift != null) {
            return shift.getCreatedByUserName();
        } else {
            return null;
        }
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.shift.setCreatedByUserName(createdByUserName);
    }

    @JsonView({ShiftFormView.class})
    public String getUpdatedByUserName() {
        if (shift != null) {
            return shift.getUpdatedByUserName();
        } else {
            return null;
        }
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.shift.setUpdatedByUserName(updatedByUserName);
    }

    @JsonView({ShiftFormView.class})
    public Date getCreatedDateTime() {
        if (shift != null) {
            return shift.getCreatedDateTime();
        } else {
            return null;
        }
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.shift.setCreatedDateTime(createdDateTime);
    }

    @JsonView({ShiftFormView.class})
    public Date getUpdatedDateTime() {
        if (shift != null) {
            return shift.getUpdatedDateTime();
        } else {
            return null;
        }
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.shift.setUpdatedDateTime(updatedDateTime);
    }
}
