package com.yumi.calendar.model;

import java.util.Date;

/**
 * Created by yumi on 2016/9/18.
 */
public class CalendarModel {
    public String mode;
    public Date minDate;
    public Date maxDate;

    public CalendarModel(){

    }

    /**
     * @param mode ""表示单选
     * @param minDate 包括
     * @param maxDate 不包括
     */
    public CalendarModel(String mode, Date minDate, Date maxDate) {
        this.mode = mode;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }
}
