package com.yumi.calendar;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.yumi.calendar.descriptor.MonthCellDescriptor;
import com.yumi.calendar.descriptor.PriceMonthCellDescriptor;
import com.yumi.calendar.model.PriceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 价格日历
 * 暂时只能用构造函数构造，不能写在xml里面（因为价格数据必须在构造函数中初始化）
 * <p/>
 * Created by yumi on 2016/9/18.
 */
public class PricePickerView extends CalendarPickerView {

    private ArrayMap<Date, PriceModel> maps;

    public PricePickerView(Context context, ArrayMap<Date, PriceModel> maps) {
        super(context, true);
        this.maps = maps;
    }

    public PricePickerView(Context context, ArrayMap<Date, PriceModel> maps, boolean displayHeader) {
        super(context, displayHeader);
        this.maps = maps;
    }


    @Override
    public MonthAdapter getMonthAdapter() {
        return new PriceMonthAdapter();
    }

    @Override
    protected MonthCellDescriptor getWeekCell(Date date, boolean isCurrentMonth, boolean isSelectable, boolean isSelected, boolean isToday, boolean isHighlighted, int value, MonthCellDescriptor.RangeState rangeState) {
        PriceModel model = new PriceModel();
        if (maps != null) {
            model = maps.get(date);
        }
        return new PriceMonthCellDescriptor(date, isCurrentMonth, isSelectable, isSelected,
                isToday, isHighlighted, value, rangeState, model != null && model.hasPrice, model == null ? -1 : model.price);
    }

    public List<PriceMonthCellDescriptor> getSelectedPriceCells() {
        List<PriceMonthCellDescriptor> cells = new ArrayList<>();
        for (MonthCellDescriptor descriptor : getSelectedCells()) {
            if (descriptor instanceof PriceMonthCellDescriptor) {
                cells.add((PriceMonthCellDescriptor) descriptor);
            }
        }
        return cells;
    }

    private class PriceMonthAdapter extends MonthAdapter {

        private PriceMonthAdapter() {
            super();
        }

        @Override
        protected int getMonthViewResId() {
            return R.layout.price_month;
        }
    }
}
