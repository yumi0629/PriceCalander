package com.yumi.calendar.adapter;

import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.TextView;

import com.yumi.calendar.R;
import com.yumi.calendar.decorator.DayViewAdapter;
import com.yumi.calendar.view.CalendarCellView;

/**
 * 默认日历单个日期item
 *
 * Created by yumi on 2016/9/18.
 */
public class DefaultDayViewAdapter implements DayViewAdapter {

  @Override
  public void makeCellView(CalendarCellView parent) {
      TextView textView = new TextView(
              new ContextThemeWrapper(parent.getContext(), R.style.CalendarCell_CalendarDate));
      textView.setDuplicateParentStateEnabled(true);
      parent.setGravity(Gravity.CENTER);
      parent.addView(textView);
      parent.setDayOfMonthTextView(textView);
  }
}
