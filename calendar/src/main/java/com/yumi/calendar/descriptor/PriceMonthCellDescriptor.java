package com.yumi.calendar.descriptor;

import com.yumi.calendar.PricePickerView;

import java.util.Date;

/**
 * 描述{@link PricePickerView} 中的cell状态
 *
 * Created by yumi on 2016/9/18.
 */
public class PriceMonthCellDescriptor extends MonthCellDescriptor {
    /**
     * 当天是否有价格信息
     */
    public final boolean hasPrice;

    /**
     * 当天价格信息
     */
    public final int price;

    public PriceMonthCellDescriptor(Date date, boolean currentMonth, boolean selectable, boolean selected, boolean today, boolean highlighted, int value, RangeState rangeState, boolean hasPrice, int price) {
        super(date, currentMonth, selectable, selected, today, highlighted, value, rangeState);
        this.price = price;
        this.hasPrice = (price > 0);
    }
}
