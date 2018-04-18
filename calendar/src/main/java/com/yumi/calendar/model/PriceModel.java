package com.yumi.calendar.model;

import java.util.Date;

/**
 * Created by yumi on 2016/9/18.
 */
public class PriceModel {
    public boolean hasPrice = false;
    public Date date = new Date();
    public int price = -1;

    public PriceModel() {}

    public PriceModel(boolean hasPrice, Date date, int price) {
        this.hasPrice = hasPrice;
        this.date = date;
        this.price = price;
    }
}
