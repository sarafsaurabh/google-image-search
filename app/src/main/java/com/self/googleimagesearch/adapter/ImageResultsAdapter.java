package com.self.googleimagesearch.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.self.googleimagesearch.R;
import com.self.googleimagesearch.RoundedTransformation;
import com.self.googleimagesearch.model.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ssaraf on 9/22/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    private static class ViewHolder {
        private ImageView ivImage;
        private TextView tvTitle;
    }

    public ImageResultsAdapter(Context context, ArrayList<ImageResult> results) {
        super(context, R.layout.item_image_result, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_image_result, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageResult imageResult = getItem(position);

        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.title));
        viewHolder.ivImage.setImageResource(0);
        Picasso.with(getContext()).load(imageResult.tbUrl)
                .transform(new RoundedTransformation(10, 0)).into(viewHolder.ivImage);

        return convertView;
    }
}
