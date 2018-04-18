package com.yumi.pricecalander;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.yumi.calendar.model.CalendarModel;
import com.yumi.calendar.model.PriceCalendarModel;
import com.yumi.calendar.model.PriceModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton single = (AppCompatButton) findViewById(R.id.single);
        single.setOnClickListener(this);
        AppCompatButton twice = (AppCompatButton) findViewById(R.id.twice);
        twice.setOnClickListener(this);
        AppCompatButton multi = (AppCompatButton) findViewById(R.id.multi);
        multi.setOnClickListener(this);
        AppCompatButton range = (AppCompatButton) findViewById(R.id.range);
        range.setOnClickListener(this);
        AppCompatButton price = (AppCompatButton) findViewById(R.id.price);
        price.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String data = "";
        int type = CalendarPickActivity.DATE_PICK_TYPE_DEFAULT;
        Calendar c = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.single:
                c.add(Calendar.MONTH, 5);  // 从今天开始往后5个月
                CalendarModel single = new CalendarModel("", new Date(), c.getTime());
                data = Utils.getMoshi().adapter(CalendarModel.class).toJson(single);
                break;
            case R.id.twice:
                c.add(Calendar.MONTH, 4);
                CalendarModel twice = new CalendarModel(CalendarPickActivity.SELECTION_MODE_TWICE, new Date(), c.getTime());
                data = Utils.getMoshi().adapter(CalendarModel.class).toJson(twice);
                break;
            case R.id.multi:
                c.add(Calendar.MONTH, 7);
                CalendarModel multi = new CalendarModel(CalendarPickActivity.SELECTION_MODE_MULTIPLE, new Date(), c.getTime());
                data = Utils.getMoshi().adapter(CalendarModel.class).toJson(multi);
                break;
            case R.id.range:
                c.add(Calendar.MONTH, 7);
                CalendarModel range = new CalendarModel(CalendarPickActivity.SELECTION_MODE_RANGE, new Date(), c.getTime());
                data = Utils.getMoshi().adapter(CalendarModel.class).toJson(range);
                break;
            case R.id.price:
                type = CalendarPickActivity.DATE_PICK_TYPE_PRICE;
                PriceCalendarModel price = getPriceCalendarModel();
                data = Utils.getMoshi().adapter(PriceCalendarModel.class).toJson(price);
                break;
            default:
                break;
        }
        Intent intent = new Intent(MainActivity.this, CalendarPickActivity.class);
        intent.putExtra(CalendarPickActivity.DATE_PICK_TYPE_KEY, type);
        intent.putExtra(CalendarPickActivity.DATE_PICK_DATA_KEY, data);
        startActivity(intent);
    }

    private PriceCalendarModel getPriceCalendarModel() {
        List<PriceModel> list = new ArrayList<>();
        list.add(new PriceModel(true, new Date(), 5800));
        list.add(new PriceModel(true, new Date(116, 8, 22), 6054));
        list.add(new PriceModel(true, new Date(116, 8, 23), 5930));
        list.add(new PriceModel(true, new Date(116, 8, 25), 6274));
        list.add(new PriceModel(true, new Date(116, 8, 28), 6353));
        list.add(new PriceModel(true, new Date(116, 8, 29), 6321));
        list.add(new PriceModel(true, new Date(116, 8, 30), 5900));
        list.add(new PriceModel(true, new Date(116, 9, 7), 7952));
        list.add(new PriceModel(true, new Date(116, 9, 10), 8741));
        list.add(new PriceModel(true, new Date(116, 9, 11), 6052));
        list.add(new PriceModel(true, new Date(116, 9, 14), 6323));
        list.add(new PriceModel(true, new Date(116, 9, 15), 6871));
        list.add(new PriceModel(true, new Date(116, 9, 17), 6321));
        list.add(new PriceModel(true, new Date(116, 9, 18), 5988));
        list.add(new PriceModel(true, new Date(116, 9, 20), 5999));
        list.add(new PriceModel(true, new Date(116, 9, 22), 6321));
        list.add(new PriceModel(true, new Date(116, 9, 23), 7062));
        list.add(new PriceModel(true, new Date(116, 9, 24), 7014));
        list.add(new PriceModel(true, new Date(116, 10, 1), 7852));
        list.add(new PriceModel(true, new Date(116, 10, 2), 7467));
        list.add(new PriceModel(true, new Date(116, 10, 17), 7343));
        list.add(new PriceModel(true, new Date(116, 10, 21), 7286));
        return new PriceCalendarModel("", list, new Date(), new Date(116, 10, 22));
    }
}
