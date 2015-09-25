package com.self.googleimagesearch.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.self.googleimagesearch.R;
import com.self.googleimagesearch.adapter.ImageResultsAdapter;
import com.self.googleimagesearch.fragment.SearchFiltersDialog;
import com.self.googleimagesearch.model.ImageResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity
        implements SearchFiltersDialog.OnFiltersSaveListener {

    private static final String SEARCH_URL =
            "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private String imageSize = "any";
    private String colorFilter = "any";
    private String imageType = "any";
    private String siteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<>();

        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                i.putExtra("imageResult", result);
                startActivity(i);
            }
        });
    }

    private void setupViews() {
        gvResults = (GridView) findViewById(R.id.gvResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.mi_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                searchForImages(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void searchForImages(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = getCompleteSearchUrl(query);

        Log.d(getClass().toString(), "Sending Google Search Request for URL: " + url);

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(getClass().toString(), "Google Search Response: " + response.toString());
                imageResults.clear();
                try {
                    aImageResults.addAll(ImageResult.fromJson(
                            response.getJSONObject("responseData").getJSONArray("results")));
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.google_api_error), Toast.LENGTH_SHORT).show();
                    Log.e(getClass().toString(), "Error encountered while parsing JSON", e);
                }
            }
        });
    }

    public void onSelectSettings(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        SearchFiltersDialog dialog = SearchFiltersDialog.newInstance(
                imageSize, colorFilter, imageType, siteName);
        dialog.show(fm, "fragment_search_filters");
    }

    @Override
    public void onFiltersSave(
            String imageSize, String colorFilter, String imageType, String siteName) {
        this.imageSize = imageSize;
        this.colorFilter = colorFilter;
        this.imageType = imageType;
        this.siteName = siteName;
    }

    private String getCompleteSearchUrl(String query) {
        Uri.Builder b = Uri.parse(SEARCH_URL).buildUpon();
        b.appendQueryParameter("q", query);
        b.appendQueryParameter("rsz", "8");
        if(!TextUtils.isEmpty(siteName)) {
            b.appendQueryParameter("as_sitesearch", siteName);
        }
        if(!imageSize.equals("any")) {
            b.appendQueryParameter("imgsz", imageSize);
        }
        if(!colorFilter.equals("any")) {
            b.appendQueryParameter("imgcolor", colorFilter);
        }
        if(!imageType.equals("any")) {
            b.appendQueryParameter("imgtype", imageType);
        }
        return b.toString();
    }
}
