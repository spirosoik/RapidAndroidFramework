package com.soi.rapidandroidapp.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.soi.rapidandroidapp.R;


public class DialogsHelper {

    public static ProgressDialog waitingDialog;
    private static DialogsHelper _instance;

    /**
     * Singleton pattern for static methods and variables
     * You must run the singletons which you will use in the first Activity
     */
    public static void initInstance() {
        if (_instance == null) {
            _instance = new DialogsHelper();
        }
    }

    public static DialogsHelper getInstance() {
        return _instance;
    }

    /**
     * Close waiting dialog
     */
    public static void closeWaitingDialog() {
        if (waitingDialog != null && waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
    }

    /**
     * Shows a dialog with a custom msg, two buttons (OK and Cancel) and the logo of the app
     * WARNING!!! Must show the dialog in Android's runOnUiThread method of the current activity
     * because of can't show a dialog out of the activity's ui thread
     *
     * @param act is the current activity eg. SoiActivity.this
     * @param msg is the msg which we want to show in our dialog
     */
    public void showDialog(final Activity act, final String msg) {
        try {
            Builder builder = new Builder(act);
            builder.setMessage(msg)
                    .setTitle(act.getResources().getString(R.string.app_name))
                    .setCancelable(true)
                    .setPositiveButton(act.getResources().getString(R.string.txt_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
            final AlertDialog alert = builder.create();
            act.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    alert.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a dialog with a default msg for "No Internet Access", two buttons (OK and Cancel) and the logo of the app
     * WARNING!!! Must show the dialog in Android's runOnUiThread method of the current activity
     *
     * @param act is the current activity eg. SoiActivity.this
     */
    public void showNoInternet(final Activity act) {
        try {
            Builder builder = new Builder(act);
            builder.setMessage(act.getResources().getString(R.string.txt_no_internet))
                    .setTitle(act.getResources().getString(R.string.app_name))
                    .setCancelable(false)
                            //.setIcon(R.drawable.logoicon)
                    .setPositiveButton(act.getResources().getString(R.string.txt_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
            final AlertDialog alert = builder.create();
            act.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    alert.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Showing a custom styled dialog and adding actions to the buttons
     * @param context
     * @param title
     * @param text
     */
    public Dialog getCustomDialog(Context context, String title, String text) {

        final Dialog dialog = new Dialog(context,
                android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_dialog);

        //Views
        Button btnCancel = (Button) dialog.findViewById(R.id.btncancel);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView dialogText = (TextView) dialog.findViewById(R.id.dialog_text);

        dialogTitle.setText(title);
        dialogText.setText(text);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.cancel();
            }

        });

        final ImageView myImage = (ImageView) dialog.findViewById(R.id.loader);
        myImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate) );

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x7f000000));

        return dialog;
    }
    
    /**
     * Shows a progress dialog when must run tasks,jobs for some time like an async event
     * WARNING!!! Must show the dialog in Android's runOnUiThread method of the current activity
     *
     * @param act     is the current activity eg. SoiActivity.this
     * @param message
     */
    public void showWaitingDialog(Activity act, String message) {
        if (waitingDialog != null) {
            if (waitingDialog.isShowing())
                return;
        }
        waitingDialog = ProgressDialog.show(act, act.getResources().getString(R.string.app_name), message, true);
        waitingDialog.setIndeterminate(true);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(40 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (waitingDialog != null && waitingDialog.isShowing()) {
                    closeWaitingDialog();
                }
            }
        }).start();
        act.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                waitingDialog.show();
            }
        });
        waitingDialog.setCancelable(false);
    }
}