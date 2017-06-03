package com.mowael.offers.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.mowael.offers.R;

/**
 * Created by moham on 3/30/2017.
 */

public class DialogUtil {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    public DialogUtil(Context context) {
        this.dialogBuilder = new AlertDialog.Builder(context);

        dialogBuilder.setPositiveButton(context.getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = dialogBuilder.create();
    }

    public void showMessage(String msg) {
        dialogBuilder.setMessage(msg);
        dialogBuilder.show();
    }

    public void hide() {
        alertDialog.dismiss();
    }


}
