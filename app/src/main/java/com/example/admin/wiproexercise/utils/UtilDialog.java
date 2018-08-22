package com.example.admin.wiproexercise.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

import com.example.admin.wiproexercise.R;


/**
 * All the dialogs used in app are created & shown from here. From object of
 * this class we can show ProgressDialog, AlertDialog & other common utility
 * dialogs.
 */

public class UtilDialog {

    private final String TAG = getClass().getSimpleName();

    public final static int PROGRESS_DIALOG = 12;
    public final static int ALERT_DIALOG = 13;

    private static UtilDialog instance;
    private static Context appContext;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    public UtilDialog() {
        Resources resources = appContext.getResources();
        alertDialog = new AlertDialog.Builder(appContext).create();
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                resources.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
    }

    public static synchronized UtilDialog getInstance(Context context) {
        appContext = context;
        if (instance == null) {
            instance = new UtilDialog();
        }
        return instance;
    }

    /**
     * Shows the dialog.
     *
     * @param message    : ChatModel to show on dialog.
     * @param dialogCode : Code for dialog to show PROGRESS_DIALOG or ALERT_DIALOG.
     */
    public void showDialog(String title, String message, int dialogCode) {
        switch (dialogCode) {
            case PROGRESS_DIALOG:
                progressDialog = new ProgressDialog(appContext);
                progressDialog.setCancelable(false);

                progressDialog.setTitle(title);
                progressDialog.setMessage(message);
                progressDialog.show();
                break;
            case ALERT_DIALOG:
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.show();
                break;
        }

    }

    /**
     * Dismisses the already visible dialog.
     *
     * @param dialogCode : Code for dialog to dismiss PROGRESS_DIALOG or ALERT_DIALOG.
     */
    public void dismissDialog(int dialogCode) {
        switch (dialogCode) {
            case PROGRESS_DIALOG:
                progressDialog.dismiss();
                break;
            case ALERT_DIALOG:
                alertDialog.dismiss();
                break;
        }
    }

    /**
     * Check whether a dialog is visible or not.
     *
     * @param dialogCode : Code for dialog to inquiry PROGRESS_DIALOG or ALERT_DIALOG.
     * @return true if dialog is visible, false otherwise
     */
    public boolean isDialogShowing(int dialogCode) {
        switch (dialogCode) {
            case PROGRESS_DIALOG:
                return progressDialog.isShowing();
            case ALERT_DIALOG:
                return alertDialog.isShowing();
        }
        return false;
    }

}
