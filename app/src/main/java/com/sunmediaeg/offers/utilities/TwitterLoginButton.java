package com.sunmediaeg.offers.utilities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.sunmediaeg.offers.R;

/**
 * Created by moham on 2/3/2017.
 */

public class TwitterLoginButton extends com.twitter.sdk.android.core.identity.TwitterLoginButton {
    private Context context;

    public TwitterLoginButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TwitterLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TwitterLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }

        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.twitter), null, null, null);
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorTransparent));
        setBackgroundResource(R.drawable.twitter);
    }
}
