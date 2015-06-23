package com.soi.rapidandroidapp.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.soi.rapidandroidapp.events.common.BusProvider;
import com.soi.rapidandroidapp.ui.dialog.AlertDialogFragment;
import com.soi.rapidandroidapp.ui.dialog.ProgressDialogFragment;

import java.io.Serializable;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment {


    private static final String PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG";

    protected boolean mSocialNetworkManagerInitialized = false;

    public BaseFragment() {
    }

    @Override
    public void onResume()
    {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public boolean allowBackPressed()
    {
        return false;
    }

    public void onBackPressed()
    {
        getActivity().onBackPressed();
    }

    /**
     * Show progress
     * @param text
     */
    protected void showProgress(String text) {
        ProgressDialogFragment progressDialogFragment = ProgressDialogFragment.newInstance(text);
        progressDialogFragment.setTargetFragment(this, 0);
        progressDialogFragment.show(getFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    /**
     * Hide progress indicator
     */
    protected void hideProgress() {
        Fragment fragment = getFragmentManager().findFragmentByTag(PROGRESS_DIALOG_TAG);

        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * Handle error
     * @param text
     */
    protected void handleError(String text) {
        AlertDialogFragment.newInstance("Error", text, false).show(getActivity().getSupportFragmentManager(), null);
    }

    /**
     * Handle success messages
     * @param title
     * @param message
     */
    protected void handleSuccess(String title, String message) {
        AlertDialogFragment.newInstance(title, message,false).show(getActivity().getSupportFragmentManager(), null);
    }
}