package com.sunmediaeg.offers.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunmediaeg.offers.R;
import com.sunmediaeg.offers.utilities.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by moham on 3/2/2017.
 */

public class TimerView extends LinearLayout {

    private final int SECOND = 1000;
    private RemainingTime time;
    private Date now;
    private TextView tvRemainingDate, tvDay, tvHour, tvSecond, tvMinuit;
    private Date startDate, endDate;
    private View view;
//    rgLike
//            rbLike
//    rbDislike

    public TimerView(Context context) {
        super(context);
        Logger.d("constructor", "TimerView(Context context)");
        initView(context, null);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d("constructor", "TimerView(Context context, AttributeSet attrs)");
        initView(context, attrs);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.d("constructor", "TimerView(Context context, AttributeSet attrs, int defStyleAttr)");
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.timer_layout, this);
        initComponents(view);
    }

    private void initComponents(View v) {
        tvRemainingDate = (TextView) findViewById(R.id.tvRemainingDate);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvSecond = (TextView) findViewById(R.id.tvSecond);
        tvMinuit = (TextView) findViewById(R.id.tvMinuit);
        now = new Date();
        time = new RemainingTime() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy HH:MM");
            @Override
            public void totalPeriod(long totalPeriod) {
                Logger.d("totalPeriod", simpleDateFormat.format(new Date(totalPeriod)));
            }

            @Override
            public void getTime(long remainingTime) {


                Logger.d("remainingTime", simpleDateFormat.format(new Date(remainingTime)));
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTimeInMillis(remainingTime);
                int day = calendar.get(Calendar.DAY_OF_YEAR);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                tvDay.setText(day);
                tvHour.setText(hour);
                tvMinuit.setText(minute);
                tvSecond.setText(second);

                Logger.d("remaining time", second + "second " + minute + "minute " + hour + "hour " + day + "day " + month + "month " + year + "year ");
                calendar = null;
            }
        };

        if (endDate != null && startDate != null) {
            Logger.d("calculateRemainingTime", "issued");
            calculateRemainingTime();
        }
    }

    public long getStartDate() {
        if (startDate == null) {
            throwException();
        }
        return startDate.getTime();
    }

    public void setStartDate(long startDate) {
        this.startDate = new Date(startDate);
    }

    public long getEndDate() {
        if (endDate == null) {
            throwException();
        }
        return endDate.getTime();
    }

    public void setEndDate(long endDate) {
        this.endDate = new Date(endDate);
        if (this.startDate != null) {
            Logger.d("calculateTimeSetEndDate", "issued");
            calculateRemainingTime();
        }
    }

    private void throwException() {
        if (view != null)
            throw new RuntimeException(view.getContext().toString() + " must setStartDate() and setEndDate()");
    }

    private void calculateRemainingTime() {
        time.totalPeriod(endDate.getTime() - startDate.getTime());

//        if (endDate != null && startDate != null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
                    long remainingTime = -1L;
                    while (endDate.getTime() == now.getTime()) {
                        try {

                            remainingTime = endDate.getTime() - now.getTime();
                            time.getTime(remainingTime);

                            Thread.sleep(SECOND);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                }
//            }).start();
//        } else throwException();
    }

    private interface RemainingTime {
        void totalPeriod(long totalPeriod);

        void getTime(long remainingTime);
    }
}
