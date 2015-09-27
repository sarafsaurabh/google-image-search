package com.self.googleimagesearch.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ssaraf on 9/22/15.
 */
public class ImageResult implements Parcelable {

    public String url;
    public String tbUrl;
    public String title;

    public ImageResult(JSONObject json) {
        try {
            this.url = json.getString("unescapedUrl");
            this.tbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e) {
            Log.e(getClass().toString(), "Error encountered while parsing JSON", e);
        }
    }

    public static Collection<ImageResult> fromJson(JSONArray array) throws JSONException {
        Collection<ImageResult> results = new ArrayList<>();
        for(int i=0; i < array.length(); i++) {
            results.add(new ImageResult(array.getJSONObject(i)));
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.tbUrl);
        dest.writeString(this.title);
    }

    protected ImageResult(Parcel in) {
        this.url = in.readString();
        this.tbUrl = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<ImageResult> CREATOR =
            new Parcelable.Creator<ImageResult>() {
        public ImageResult createFromParcel(Parcel source) {
            return new ImageResult(source);
        }

        public ImageResult[] newArray(int size) {
            return new ImageResult[size];
        }
    };
}
