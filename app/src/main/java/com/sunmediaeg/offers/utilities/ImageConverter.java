package com.sunmediaeg.offers.utilities;

import android.widget.ImageView;

/**
 * Created by moham on 4/7/2017.
 */

public class ImageConverter {

    public void getStringImage(final VolleySingleton volley, final ImageView imageView, final ConversionListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.OnConversionComplete(volley.getReducedStringImageJPG(volley.getBitmap(imageView)));

            }
        }).start();
    }

    public interface ConversionListener {
        void OnConversionComplete(String image);
    }
}
