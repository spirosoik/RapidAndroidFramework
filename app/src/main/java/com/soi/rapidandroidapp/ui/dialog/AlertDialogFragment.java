package com.soi.rapidandroidapp.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class AlertDialogFragment extends DialogFragment implements Dialog.OnClickListener {

    private static final String PARAM_TITLE = "PARAM_TITLE";
    private static final String PARAM_MESSAGE = "PARAM_MESSAGE";
    private static final String PARAM_ACTION  = "PARAM_ACTION";
    
    private boolean mForceClose;

    public static AlertDialogFragment newInstance(String title, String message, boolean forceClose) {
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_MESSAGE, message);
        args.putBoolean(PARAM_ACTION, forceClose);

        AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
        alertDialogFragment.setArguments(args);
        return alertDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();

        final String paramTitle = args.getString(PARAM_TITLE);
        final String paramMessage = args.getString(PARAM_MESSAGE);
        mForceClose = args.getBoolean(PARAM_ACTION);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(paramTitle);
        builder.setMessage(paramMessage);
        builder.setPositiveButton(android.R.string.ok, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mForceClose) {
            getActivity().finish();
        }
    }
}