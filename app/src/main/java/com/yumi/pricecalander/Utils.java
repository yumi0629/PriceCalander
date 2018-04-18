package com.yumi.pricecalander;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yumi on 2016/9/29.
 */
public class Utils {
    /**
     * 格式化日期的标准字符串
     */
    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static Toast mToast = null;

    public static void showToast(Context context, int textId) {
        showToast(context, context.getString(textId));
    }

    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static Moshi getMoshi() {
        return new Moshi.Builder().add(new DateJsonAdapter()).build();
    }

    public static class DateJsonAdapter {
        @FromJson
        Date dateFromJson(String dateJson) {
            return parseDate(dateJson);
        }

        @ToJson
        String dateToJson(Date date) {
            return formatDate(date);
        }
    }

    /**
     * 将日期字符串转换为Date对象
     *
     * @param date 日期字符串，必须为"yyyy-MM-dd HH:mm:ss"
     * @return 日期字符串的Date对象表达形式
     */
    public static Date parseDate(String date) {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        try {
            dt = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }

    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date Date对象
     * @return Date对象的字符串表达形式"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(date);
    }

}
