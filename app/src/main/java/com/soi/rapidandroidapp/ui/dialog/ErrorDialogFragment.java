package com.soi.rapidandroidapp.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.Serializable;
import java.util.HashMap;

/* A fragment to display an error dialog */
public class ErrorDialogFragment extends DialogFragment {

    public static final String KEY_DIALOG_ERROR =  "dialogError";
    public static final String KEY_REQUEST_RESOLVE_ERROR =  "requestResolveError";

    public static ErrorDialogFragment newInstance(HashMap<String, Object> params)
    {
        ErrorDialogFragment fragment = new ErrorDialogFragment();
        if (params != null && params.size() > 0) {

            Bundle args = new Bundle();
            for (String key : params.keySet()) {
                args.putSerializable(key, (Serializable) params.get(key));
            }
            fragment.setArguments(args);
        }
        return fragment;
    }

    public ErrorDialogFragment() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int errorCode = this.getArguments().getInt(KEY_DIALOG_ERROR);
        int requestCode = this.getArguments().getInt(KEY_REQUEST_RESOLVE_ERROR);
        return GooglePlayServicesUtil.getErrorDialog(errorCode, this.getActivity(), requestCode);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dialog.dismiss();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null ) {
            setShowsDialog( false );
        }
        super.onActivityCreated(savedInstanceState);
    }
}