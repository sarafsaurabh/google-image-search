package com.self.googleimagesearch.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.self.googleimagesearch.R;

/**
 * Created by ssaraf on 9/24/15.
 */
public class SearchFiltersDialog extends DialogFragment {

    private Button btSave;
    private Button btCancel;
    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private EditText etSiteName;

    public SearchFiltersDialog() {
        // Empty constructor required for DialogFragment
    }

    public static SearchFiltersDialog newInstance(
            String imageSize, String colorFilter, String imageType, String siteName) {
        SearchFiltersDialog dialog = new SearchFiltersDialog();

        Bundle args = new Bundle();
        args.putString("imageSize", imageSize);
        args.putString("colorFilter", colorFilter);
        args.putString("imageType", imageType);
        args.putString("siteName", siteName);
        dialog.setArguments(args);
        return dialog;
    }

    public interface OnFiltersSaveListener {
        public void onFiltersSave(
                String imageSize, String colorFilter, String imageType, String siteName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_filters, container);
        getDialog().setTitle(R.string.advanced_filters);

        btSave = (Button) view.findViewById(R.id.btSave);
        btCancel = (Button) view.findViewById(R.id.btCancel);

        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        setSpinnerToValue(spImageSize, getArguments().getString("imageSize"));

        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        setSpinnerToValue(spImageType, getArguments().getString("imageType"));

        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        setSpinnerToValue(spColorFilter, getArguments().getString("colorFilter"));

        etSiteName = (EditText) view.findViewById(R.id.etSiteName);
        etSiteName.setText(
                TextUtils.isEmpty(getArguments().getString("siteName")) ? ""
                        : getArguments().getString("siteName"));

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String imageSize = spImageSize.getSelectedItem().toString();
                String colorFilter = spColorFilter.getSelectedItem().toString();
                String imageType = spImageType.getSelectedItem().toString();
                String siteName = etSiteName.getText().toString();

                OnFiltersSaveListener listener = (OnFiltersSaveListener) getActivity();
                listener.onFiltersSave(imageSize, colorFilter, imageType, siteName);
                dismiss();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }
}
