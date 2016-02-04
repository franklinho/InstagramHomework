package com.franklinho.instagramhomework;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

// This view is the main feed for popular instagram photos


public class PopularActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    @Bind(R.id.swipe_container) SwipeRefreshLayout swipeContainer;
    @Bind(R.id.lvPhotos) ListView lvPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        //initiate Butterknife

        ButterKnife.bind(this)
        ;

        //Set icon for actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.space_between_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Configuring swipe container
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //When refresh is triggered, pull photos
                fetchPopularPhotos();
            }
        });

        // Configure the swipe container refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //Initiate Joda Time
        JodaTimeAndroid.init(this);

        photos = new ArrayList<>();
        //Create adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        //attach listview to adapter
        lvPhotos.setAdapter(aPhotos);

        // Send out api request to popular photos
        fetchPopularPhotos();
    }

    // Trigger API Request
    public void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        client.get(url, null,new JsonHttpResponseHandler() {
            // onSuccess (worked, 200)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                photos.clear();
                // Expecting JSON object
                Log.i("DEBUG", response.toString());
                JSONArray photosJSON = null;
                try {

                    photosJSON = response.getJSONArray("data"); // array of posts
                    //Loop through posts
                    for (int i = 0; i < photosJSON.length(); i++) {
                        // get json at that position in array
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        // decode attributes into data model
                        InstagramPhoto photo = new InstagramPhoto();
                        //Population of photo object has been moved to the InstagramPhoto Model
                        photo.populateInstagramPhotoWithJSON(photoJSON);

                        //Add photo to array
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Refresh listview now that data is there
                aPhotos.notifyDataSetChanged();
                //End the refreshing animation in SwipeContainer
                swipeContainer.setRefreshing(false);
            }

            // onFailure (failed request)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    //Is triggered when someone taps show all x comments in a listview item
    public void showAllComments(View view) {

        //Get the instagram photo where the comments button was tapped
        InstagramPhoto photo = (InstagramPhoto) view.getTag();

        //Turn the comments into an array of parcelables
        ArrayList<Parcelable> commentsArray = new ArrayList<>();
        for (int c=0; c < photo.comments.size(); c++ ) {
            commentsArray.add(Parcels.wrap(photo.comments.get(c)));
        }

        // Initiate DialogFragment
        FragmentManager fm = getSupportFragmentManager();
        AllCommentsDialog alertDialog = AllCommentsDialog.newInstance(commentsArray);
        //Show dialog fragment
        alertDialog.show(fm, "fragment_all_comments");

    }
}
