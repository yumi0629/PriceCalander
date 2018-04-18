package com.yumi.calendar.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yumi.calendar.descriptor.MonthCellDescriptor;
import com.yumi.calendar.descriptor.PriceMonthCellDescriptor;

/**
 * Created by yumi on 2016/9/18.
 */
public class PriceMonthView extends MonthView {
    public PriceMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void initCellView(CalendarCellView cellView, MonthCellDescriptor cell) {
        super.initCellView(cellView, cell);
        if (cell instanceof PriceMonthCellDescriptor) {
            PriceMonthCellDescriptor c = (PriceMonthCellDescriptor) cell;
            if (c.hasPrice) {
                cellView.getPriceTextView().setText("￥" + c.price);
            } else {
                c.isSelectable = false;
                cellView.setSelectable(false);
                cellView.setClickable(false);
                cellView.getPriceTextView().setText("");
            }
        }

    }
}
