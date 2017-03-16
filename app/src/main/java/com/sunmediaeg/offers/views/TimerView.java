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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by moham on 3/2/2017.
 */

public class TimerView extends LinearLayout {

    private TextView tvRemainingDate, tvDay, tvHour, tvSecond, tvMinuit;
    private View view;

    public TimerView(Context context) {
        super(context);
//        Logger.d("constructor", "TimerView(Context context)");
        initView(context);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Logger.d("constructor", "TimerView(Context context, AttributeSet attrs)");
        initView(context);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        Logger.d("constructor", "TimerView(Context context, AttributeSet attrs, int defStyleAttr)");
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.timer_view_layout, this);
        initComponents(view);
    }

    private void initComponents(View v) {
        tvRemainingDate = (TextView) findViewById(R.id.tvRemainingDate);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvSecond = (TextView) findViewById(R.id.tvSecond);
        tvMinuit = (TextView) findViewById(R.id.tvMinuit);
    }

    public void setTime(long startDate, long endDate, TimerViewCounter.RemainingTime remainingTime) {
        setTime(new Date(startDate), new Date(endDate), remainingTime);
    }

    public void setTime(String startDate, String endDate, TimerViewCounter.RemainingTime time) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TimerViewCounter.STANDARD_DATE_FORMAT);
        setTime(format.parse(startDate), format.parse(endDate), time);
    }

    public void setTime(Date startDate, Date endDate, final TimerViewCounter.RemainingTime time) {
        SimpleDateFormat format = new SimpleDateFormat(TimerViewCounter.STANDARD_DATE_FORMAT);
        if (endDate.getTime() < System.currentTimeMillis())
            tvRemainingDate.setText(getContext().getString(R.string.timeUp));
        else
            tvRemainingDate.setText(format.format(endDate));
        final TimerViewCounter counter = new TimerViewCounter(startDate, endDate);
        counter.calculateRemainingTime(getContext(), new TimerViewCounter.RemainingTime() {
            @Override
            public void onTimeOut() {
                if (time != null) time.onTimeOut();
            }

            @Override
            public void totalPeriod(long totalPeriod) {
//                Logger.d("remaining days", TimerViewCounter.getDurationInDays(totalPeriod) + "");
                if (time != null) time.totalPeriod(totalPeriod);
            }

            @Override
            public void getTime(long remainingTime) {
                if (time != null) time.getTime(remainingTime);
//                Logger.d("Days", TimerViewCounter.getDurationInDays(remainingTime) + "");
//                Logger.d("remainingTime", remainingTime + "");
                HashMap<Integer, Long> relativeTime = counter.getRelativeTime(remainingTime);

                long day = relativeTime.get(TimerViewCounter.DAY);
                long second = relativeTime.get(TimerViewCounter.SECOND);
                long minute = relativeTime.get(TimerViewCounter.MINUTE);
                long hour = relativeTime.get(TimerViewCounter.HOUR);

                tvDay.setText(day + "");
                tvHour.setText(hour + "");
                tvMinuit.setText(minute + "");
                tvSecond.setText(second + "");
//                Logger.d("remaining time", second + " second, " + minute + " minute, " + hour + " hour, " + day + " day, "/* + month + " month"*/);
            }
        });
    }

}
