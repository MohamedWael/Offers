package com.sunmediaeg.offers.views;

import android.content.Context;
import android.os.Handler;

import com.sunmediaeg.offers.utilities.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by moham on 3/10/2017.
 */

public class TimerViewCounter {

    public static final int MILLIS = 1000;
    public static final int SECOND = 1 * MILLIS;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    public static final String STANDARD_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd";
    private final String STANDARD_TIME_FORMAT2 = "yyyy-MM-dd HH:mm:ss.S";
    private Date startDate, endDate;

    public TimerViewCounter(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void calculateRemainingTime(Context context, final RemainingTime time) {
        final Handler handler = new Handler(context.getMainLooper());
        time.totalPeriod(Math.abs(endDate.getTime() - startDate.getTime()));
        if (endDate != null && startDate != null) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    long remainingTime = -1L;
                    while (endDate.getTime() >= System.currentTimeMillis()) {
                        try {
                            remainingTime = Math.abs(endDate.getTime() - System.currentTimeMillis());
                            final long finalRemainingTime = remainingTime;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    time.getTime(finalRemainingTime);
                                }
                            });
                            Thread.sleep(SECOND);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            Logger.d("OnTimeOut", "Time Out");
                            time.onTimeOut();
                        }
                    });

                }
            }).start();
        } else {
            throwException();
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
    }

    private void throwException() {
        throw new RuntimeException(" must setStartDate() and setEndDate()");
    }

    public static long getDurationInDays(long time) {
        return TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS);
    }

    public static long getDurationInHours(long time) {
        return TimeUnit.HOURS.convert(time, TimeUnit.MILLISECONDS);
    }

    public HashMap<Integer, Long> getRelativeTime(long time) {
        HashMap<Integer, Long> relativeTime = new HashMap<>();
        if (time > DAY) {
            relativeTime.put(DAY, time / DAY);
            time %= DAY;
        } else relativeTime.put(DAY, 0L);
        if (time > HOUR) {
            relativeTime.put(HOUR, time / HOUR);
            time %= HOUR;
        } else relativeTime.put(HOUR, 0L);
        if (time > MINUTE) {
            relativeTime.put(MINUTE, time / MINUTE);
            time %= MINUTE;
        } else relativeTime.put(MINUTE, 0L);
        if (time > SECOND) {
            relativeTime.put(SECOND, time / SECOND);
            time %= SECOND;
        } else relativeTime.put(SECOND, 0L);
        return relativeTime;
    }

    public interface RemainingTime {
        void onTimeOut();

        void totalPeriod(long totalPeriod);

        void getTime(long remainingTime);
    }
}
