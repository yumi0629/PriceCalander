package com.yumi.pricecalander;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.squareup.moshi.JsonAdapter;
import com.yumi.calendar.adapter.DefaultDayViewAdapter;
import com.yumi.calendar.adapter.PriceDayViewAdapter;
import com.yumi.calendar.decorator.CalendarCellDecorator;
import com.yumi.calendar.descriptor.MonthCellDescriptor;
import com.yumi.calendar.model.CalendarModel;
import com.yumi.calendar.model.PriceCalendarModel;
import com.yumi.calendar.model.PriceModel;
import com.yumi.calendar.CalendarPickerView;
import com.yumi.calendar.CalendarPickerView.SelectionMode;
import com.yumi.calendar.PricePickerView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yumi on 2016/9/29.
 */
public class CalendarPickActivity extends AppCompatActivity {
    // 日历显示从当月开始向后多少个月
    private static final int DEFAULT_MONTH_AFTER = 3;

    public static final int DATE_PICK_TYPE_DEFAULT = 0;
    public static final int DATE_PICK_TYPE_PRICE = 1;

    public static final String DATE_PICK_DATA_KEY = "date_pick_data";
    public static final String DATE_PICK_TYPE_KEY = "date_pick_type";
    public static final String SELECTION_MODE_SINGLE = "single";
    public static final String SELECTION_MODE_MULTIPLE = "multiple";
    public static final String SELECTION_MODE_RANGE = "range";
    public static final String SELECTION_MODE_TWICE = "twice";

    private CalendarPickerView pickerView;
    private PricePickerView pricePickerView;

    private LinearLayout main;

    private SelectionMode selectionMode = SelectionMode.SINGLE;

    private int calenderType = DATE_PICK_TYPE_DEFAULT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        main = (LinearLayout) findViewById(R.id.main);

        Intent intent = getIntent();
        if (intent != null) {
            calenderType = intent.getIntExtra(DATE_PICK_TYPE_KEY, DATE_PICK_TYPE_DEFAULT);
            String data = intent.getStringExtra(DATE_PICK_DATA_KEY);
            createCalendarView(data);
        }
    }

    private void createCalendarView(String data) {
        switch (calenderType) {
            case DATE_PICK_TYPE_PRICE:
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                JsonAdapter<PriceCalendarModel> priceJsonAdapter = Utils.getMoshi().adapter(PriceCalendarModel.class);
                try {
                    PriceCalendarModel calendarModel = priceJsonAdapter.fromJson(data);
                    pricePickerView = new PricePickerView(getApplicationContext(),
                            getPriceModelMaps(calendarModel.priceList));
                    pricePickerView.setCustomDayView(new PriceDayViewAdapter());
                    pricePickerView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                    pricePickerView.init(calendarModel.minDate, calendarModel.maxDate)
                            .inMode(getSelectionMode(calendarModel.mode));
                    pricePickerView.setOnDateSelectedListener(dateSelectedListener);
                    main.addView(pricePickerView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, DEFAULT_MONTH_AFTER);
                CalendarModel calendarModel = new CalendarModel("", new Date(), c.getTime());
                if (!TextUtils.isEmpty(data)) {
                    JsonAdapter<CalendarModel> jsonAdapter = Utils.getMoshi().adapter(CalendarModel.class);
                    try {
                        calendarModel = jsonAdapter.fromJson(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                pickerView = new CalendarPickerView(this, true);
                pickerView.setCustomDayView(new DefaultDayViewAdapter());
                pickerView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
                pickerView.init(calendarModel.minDate, calendarModel.maxDate)
                        .inMode(getSelectionMode(calendarModel.mode));
                pickerView.setOnDateSelectedListener(dateSelectedListener);
                main.addView(pickerView);
                break;
        }

    }

    private CalendarPickerView.OnDateSelectedListener dateSelectedListener = new CalendarPickerView.OnDateSelectedListener() {
        @Override
        public void onDateSelected(Date date, MonthCellDescriptor cell) {
            Utils.showToast(CalendarPickActivity.this, Utils.formatDate(date));
        }

        @Override
        public void onDateUnselected(Date date) {

        }
    };

    private SelectionMode getSelectionMode(String mode) {
        if (mode.equals(SELECTION_MODE_MULTIPLE)) {
            selectionMode = SelectionMode.MULTIPLE;
        } else if (mode.equals(SELECTION_MODE_RANGE)) {
            selectionMode = SelectionMode.RANGE;
        } else if (mode.equals(SELECTION_MODE_TWICE)) {
            selectionMode = SelectionMode.TWICE;
        }
        return selectionMode;
    }

    private ArrayMap<Date, PriceModel> getPriceModelMaps(List<PriceModel> priceList) {
        ArrayMap<Date, PriceModel> maps = new ArrayMap<>();
        if (priceList == null || priceList.size() == 0) {
            return maps;
        }
        for (PriceModel model : priceList) {
            maps.put(model.date, model);
        }
        return maps;
    }
}
