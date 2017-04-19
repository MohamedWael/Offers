package com.sunmediaeg.offers.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.sunmediaeg.offers.R;

/**
 * Created by moham on 3/30/2017.
 */

public class DialogUtil {
    private AlertDialog.Builder dialog;

    public DialogUtil(Context context) {
        this.dialog = new AlertDialog.Builder(context);
        dialog.setPositiveButton(context.getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void showMessage(String msg) {
        dialog.setMessage(msg);
        dialog.show();
    }
}
