package com.yumi.calendar.decorator;

import com.yumi.calendar.view.CalendarCellView;

import java.util.Date;

/**
 * Created by yumi on 2016/9/18.
 */
public interface CalendarCellDecorator {
  void decorate(CalendarCellView cellView, Date date);
}
