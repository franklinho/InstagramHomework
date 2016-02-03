package com.franklinho.instagramhomework;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PopularActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Configuring swipe container
        setContentView(R.layout.activity_popular);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        // Setup refresh listener which triggers new data loading





        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_popular);

        photos = new ArrayList<>();

        //Create adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);

        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //attach listview to adapter

        lvPhotos.setAdapter(aPhotos);
//        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });


        // Sent out api request to popular photos
        fetchPopularPhotos();
    }

//    @Override
//    public void onRefresh() {
//        fetchPopularPhotos();
//    }

    // Trigger API Request
    public void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        client.get(url, null,new JsonHttpResponseHandler() {
            // onSuccess (worked, 200)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting JSON object

                Log.i("DEBUG", response.toString());
                JSONArray photosJSON = null;
                try {
                    photos.clear();
                    photosJSON = response.getJSONArray("data"); // array of posts
                    for (int i = 0; i < photosJSON.length(); i++) {
                        // get json at that position in array
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        // decode attributes into data model
                        InstagramPhoto photo = new InstagramPhoto();
//                        photo.populateInstagramPhotoWithJSON(photoJSON);
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.profileImageUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.createdTime = photoJSON.getInt("created_time");
                        photo.type = photoJSON.getString("type");
                        if (photo.type == "video") {
                            photo.videoUrl = photoJSON.getJSONObject("videos").getString("standard_resolution");
                        }


                        photos.add(photo);

                    }
                    swipeContainer.setRefreshing(false);
                    aPhotos.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            // onFailure (failed request)
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
