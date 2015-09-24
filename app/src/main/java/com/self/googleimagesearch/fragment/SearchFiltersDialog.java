package com.self.googleimagesearch.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.self.googleimagesearch.R;

/**
 * Created by ssaraf on 9/24/15.
 */
public class SearchFiltersDialog extends DialogFragment {

    public SearchFiltersDialog() {
        // Empty constructor required for DialogFragment
    }

    public static SearchFiltersDialog newInstance() {
        SearchFiltersDialog dialog = new SearchFiltersDialog();
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_filters, container);
        return view;
    }


}
