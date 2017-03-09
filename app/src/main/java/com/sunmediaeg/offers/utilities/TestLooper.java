package com.sunmediaeg.offers.utilities;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;

/**
 * Created by moham on 3/6/2017.
 */

public class TestLooper extends Thread {

    private static TestLooper looper;
    private Handler handler;
    private ThreadHandler threadHandler, defaultThreadHandler;

    public TestLooper(ThreadHandler threadHandler, final Handler.Callback callback) {
        this.threadHandler = threadHandler;
        if (threadHandler == null) {
            this.defaultThreadHandler = new ThreadHandler() {
                @Override
                public Handler getHandler() {
                    return new Handler(callback);
                }
            };
        }

        start();
    }

    public TestLooper(final Handler.Callback callback, final HandlerMessage handlerMessage) {
        if (threadHandler == null) {
            this.defaultThreadHandler = new ThreadHandler() {
                @Override
                public Handler getHandler() {
                    return new Handler(callback){
                        @Override
                        public void handleMessage(Message msg) {
                            handlerMessage.handle(msg);
                        }
                    };
                }
            };
        }
        start();
    }


    @Override
    public void run() {
        Looper.prepare();
        if (threadHandler == null) {
            defaultThreadHandler.getHandler();
        } else {
            threadHandler.getHandler();
        }
        Looper.loop();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void processThenStop() {
        getCurrentLooper().quitSafely();
    }

    public void stopAndQuit() {
        getCurrentLooper().quit();
    }

    public Looper getCurrentLooper() {
        return Looper.myLooper();
    }

    public Handler getThreadHandler() {
        if (threadHandler == null) return defaultThreadHandler.getHandler();
        else return threadHandler.getHandler();
    }

    public interface ThreadHandler {
        Handler getHandler();
    }

    public interface HandlerMessage{
        void handle(Message msg);
    }
}
