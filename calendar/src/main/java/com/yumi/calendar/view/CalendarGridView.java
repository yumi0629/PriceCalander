package com.yumi.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.yumi.calendar.R;
import com.yumi.calendar.decorator.DayViewAdapter;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * 用来绘制一组calendar cells的ViewGroup。
 * 所有的子view都必须是 {@link CalendarRowView} 。
 * 第一行默认是星期header，没有绘制分割线
 * <p/>
 * Created by yumi on 2016/9/18.
 */
public class CalendarGridView extends ViewGroup {
    /**
     * grid lines don't exactly line up on certain devices (Nexus 7, Nexus 5). Fudging the
     * co-ordinates by half a point seems to fix this without breaking other devices.
     */
    private static final float FLOAT_FUDGE = 0.5f;

    private final Paint dividerPaint = new Paint();
    private int oldWidthMeasureSize;
    private int oldNumRows;

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dividerPaint.setColor(getResources().getColor(R.color.calendar_divider));
    }

    public void setDividerColor(int color) {
        dividerPaint.setColor(color);
    }

    public void setDayViewAdapter(DayViewAdapter adapter) {
        for (int i = 0; i < getChildCount(); i++) {
            ((CalendarRowView) getChildAt(i)).setDayViewAdapter(adapter);
        }
    }

    public void setDayBackground(int resId) {
        for (int i = 1; i < getChildCount(); i++) {
            ((CalendarRowView) getChildAt(i)).setCellBackground(resId);
        }
    }

    public void setDayTextColor(int resId) {
        for (int i = 0; i < getChildCount(); i++) {
            ColorStateList colors = getResources().getColorStateList(resId);
            ((CalendarRowView) getChildAt(i)).setCellTextColor(colors);
        }
    }

    public void setDisplayHeader(boolean displayHeader) {
        getChildAt(0).setVisibility(displayHeader ? VISIBLE : GONE);
    }

    public void setHeaderTextColor(int color) {
        ((CalendarRowView) getChildAt(0)).setCellTextColor(color);
    }

    public void setTypeface(Typeface typeface) {
        for (int i = 0; i < getChildCount(); i++) {
            ((CalendarRowView) getChildAt(i)).setTypeface(typeface);
        }
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() == 0) {
            ((CalendarRowView) child).setIsHeaderRow(true);
        }
        super.addView(child, index, params);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        final ViewGroup row = (ViewGroup) getChildAt(1);
        int top = row.getTop();
        int bottom = getBottom();
        // Left side border.
        final int left = row.getChildAt(0).getLeft() + getLeft();
        canvas.drawLine(left + FLOAT_FUDGE, top, left + FLOAT_FUDGE, bottom, dividerPaint);

        // Each cell's right-side border.
        for (int c = 0; c < 7; c++) {
            float x = left + row.getChildAt(c).getRight() - FLOAT_FUDGE;
            canvas.drawLine(x, top, x, bottom, dividerPaint);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean retVal = super.drawChild(canvas, child, drawingTime);
        // Draw a bottom border.
        final int bottom = child.getBottom() - 1;
        canvas.drawLine(child.getLeft(), bottom, child.getRight() - 2, bottom, dividerPaint);
        return retVal;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("Grid.onMeasure", MeasureSpec.toString(widthMeasureSpec) + "  " +
                MeasureSpec.toString(heightMeasureSpec));
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        if (oldWidthMeasureSize == widthMeasureSize) {
            Log.d("", "SKIP Grid.onMeasure");
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }
        long start = System.currentTimeMillis();
        oldWidthMeasureSize = widthMeasureSize;
        int cellSize = widthMeasureSize / 7;
        // Remove any extra pixels since /7 is unlikely to give whole nums.
        widthMeasureSize = cellSize * 7;
        int totalHeight = 0;
        final int rowWidthSpec = makeMeasureSpec(widthMeasureSize, EXACTLY);
        final int rowHeightSpec = makeMeasureSpec(cellSize, EXACTLY);
        for (int c = 0, numChildren = getChildCount(); c < numChildren; c++) {
            final View child = getChildAt(c);
            if (child.getVisibility() == View.VISIBLE) {
                if (c == 0) { // It's the header: height should be wrap_content.
                    measureChild(child, rowWidthSpec, makeMeasureSpec(cellSize, AT_MOST));
                } else {
                    measureChild(child, rowWidthSpec, rowHeightSpec);
                }
                totalHeight += child.getMeasuredHeight();
            }
        }
        final int measuredWidth = widthMeasureSize + 2; // Fudge factor to make the borders show up.
        setMeasuredDimension(measuredWidth, totalHeight);
        Log.d("Grid.onMeasure %d ms", System.currentTimeMillis() - start + "");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long start = System.currentTimeMillis();
        top = 0;
        for (int c = 0, numChildren = getChildCount(); c < numChildren; c++) {
            final View child = getChildAt(c);
            final int rowHeight = child.getMeasuredHeight();
            child.layout(left, top, right, top + rowHeight);
            top += rowHeight;
        }
        Log.d("Grid.onLayout %d ms", System.currentTimeMillis() - start + "");
    }

    public void setNumRows(int numRows) {
        if (oldNumRows != numRows) {
            // If the number of rows changes, make sure we do a re-measure next time around.
            oldWidthMeasureSize = 0;
        }
        oldNumRows = numRows;
    }
}
