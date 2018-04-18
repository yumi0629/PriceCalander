package com.yumi.calendar.model;

import java.util.Date;
import java.util.List;

/**
 * Created by yumi on 2016/9/18.
 */
public class PriceCalendarModel {
    public String mode;
    public List<PriceModel> priceList;
    public Date minDate;
    public Date maxDate;

    public PriceCalendarModel() {

    }

    /**
     * @param mode ""表示单选
     * @param minDate 包括
     * @param maxDate 不包括
     */
    public PriceCalendarModel(String mode, List<PriceModel> priceList, Date minDate, Date maxDate) {
        this.mode = mode;
        this.priceList = priceList;
        this.minDate = minDate;
        this.maxDate = maxDate;

    }
}
