package com.yumi.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yumi.calendar.decorator.DayViewAdapter;
import com.yumi.calendar.descriptor.MonthCellDescriptor;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * TableRow：绘制单个cell之间的分隔，和{@link CalendarGridView} 一起使用
 *
 * Created by yumi on 2016/9/18.
 */
public class CalendarRowView extends ViewGroup implements View.OnClickListener {
  private boolean isHeaderRow;
  private MonthView.Listener listener;

  public CalendarRowView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public void addView(View child, int index, LayoutParams params) {
    child.setOnClickListener(this);
    super.addView(child, index, params);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    long start = System.currentTimeMillis();
    final int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
    int rowHeight = 0;
    for (int c = 0, numChildren = getChildCount(); c < numChildren; c++) {
      final View child = getChildAt(c);
      // Calculate width cells, making sure to cover totalWidth.
      int l = ((c + 0) * totalWidth) / 7;
      int r = ((c + 1) * totalWidth) / 7;
      int cellSize = r - l;
      int cellWidthSpec = makeMeasureSpec(cellSize, EXACTLY);
      int cellHeightSpec = isHeaderRow ? makeMeasureSpec(cellSize, AT_MOST) : cellWidthSpec;
      child.measure(cellWidthSpec, cellHeightSpec);
      // 最高的那个cell的高度即为行高
      if (child.getMeasuredHeight() > rowHeight) {
        rowHeight = child.getMeasuredHeight();
      }
    }
    final int widthWithPadding = totalWidth + getPaddingLeft() + getPaddingRight();
    final int heightWithPadding = rowHeight + getPaddingTop() + getPaddingBottom();
    setMeasuredDimension(widthWithPadding, heightWithPadding);
    Log.d("Row.onMeasure %d ms", System.currentTimeMillis() - start+"");
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    long start = System.currentTimeMillis();
    int cellHeight = bottom - top;
    int width = (right - left);
    for (int c = 0, numChildren = getChildCount(); c < numChildren; c++) {
      final View child = getChildAt(c);
      int l = ((c + 0) * width) / 7;
      int r = ((c + 1) * width) / 7;
      child.layout(l, 0, r, cellHeight);
    }
    Log.d("Row.onLayout %d ms", System.currentTimeMillis() - start+"");
  }

  public void setIsHeaderRow(boolean isHeaderRow) {
    this.isHeaderRow = isHeaderRow;
  }

  @Override
  public void onClick(View v) {
    // Header rows 没有点击事件
    if (listener != null) {
      listener.handleClick((MonthCellDescriptor) v.getTag());
    }
  }

  public void setListener(MonthView.Listener listener) {
    this.listener = listener;
  }

  public void setDayViewAdapter(DayViewAdapter adapter) {
    for (int i = 0; i < getChildCount(); i++) {
      if (getChildAt(i) instanceof CalendarCellView) {
        CalendarCellView cell = ((CalendarCellView) getChildAt(i));
        cell.removeAllViews();
        adapter.makeCellView(cell);
      }
    }
  }

  public void setCellBackground(int resId) {
    for (int i = 0; i < getChildCount(); i++) {
      getChildAt(i).setBackgroundResource(resId);
    }
  }

  public void setCellTextColor(int resId) {
    for (int i = 0; i < getChildCount(); i++) {
      if (getChildAt(i) instanceof CalendarCellView) {
        ((CalendarCellView) getChildAt(i)).getDayOfMonthTextView().setTextColor(resId);
      } else {
        ((TextView) getChildAt(i)).setTextColor(resId);
      }
    }
  }

  public void setCellTextColor(ColorStateList colors) {
    for (int i = 0; i < getChildCount(); i++) {
      if (getChildAt(i) instanceof CalendarCellView) {
        ((CalendarCellView) getChildAt(i)).getDayOfMonthTextView().setTextColor(colors);
      } else {
        ((TextView) getChildAt(i)).setTextColor(colors);
      }
    }
  }

  public void setTypeface(Typeface typeface) {
    for (int i = 0; i < getChildCount(); i++) {
      if (getChildAt(i) instanceof CalendarCellView) {
        ((CalendarCellView) getChildAt(i)).getDayOfMonthTextView().setTypeface(typeface);
      } else {
        ((TextView) getChildAt(i)).setTypeface(typeface);
      }
    }
  }
}
