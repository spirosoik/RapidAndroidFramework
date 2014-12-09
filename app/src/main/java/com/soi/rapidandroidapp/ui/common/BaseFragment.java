package com.soi.rapidandroidapp.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.soi.rapidandroidapp.events.common.BusProvider;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment {
    /**
	 * The fragment argument representing the section number for this fragment.
	 */
	protected static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static BaseFragment newInstance(Map<String, Class<? extends Serializable>> params) {
		BaseFragment fragment = new BaseFragment();
		Bundle args = new Bundle();

        for (String key : params.keySet()) {
            args.putSerializable(key, params.get(key));
        }

		fragment.setArguments(args);
		return fragment;
	}

	public BaseFragment() {
	}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean allowBackPressed() {
        return false;
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }

}