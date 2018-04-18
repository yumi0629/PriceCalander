package com.yumi.calendar.decorator;


import com.yumi.calendar.view.CalendarCellView;

/**
 * 为 {@link CalendarCellView} 提供layout的adapter
 *
 * Created by yumi on 2016/9/18.
 */
public interface DayViewAdapter {
  void makeCellView(CalendarCellView parent);
}
