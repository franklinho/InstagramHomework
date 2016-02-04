package com.franklinho.instagramhomework;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by franklinho on 2/2/16.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public String profileImageUrl;
    public int createdTime;
    public String type;
    public String videoUrl;
    public ArrayList<InstagramComment> comments;
    public int commentsCount;

    //Allows you to populate the photo object by just passing in the JSON
    public void populateInstagramPhotoWithJSON(JSONObject json) {

        try {

            //Parsing through JSON
            username = json.getJSONObject("user").getString("username");
            caption = json.getJSONObject("caption").getString("text");
            imageUrl = json.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            imageHeight = json.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
            likesCount = json.getJSONObject("likes").getInt("count");
            profileImageUrl = json.getJSONObject("user").getString("profile_picture");
            createdTime = json.getInt("created_time");
            type = json.getString("type");
            if (type.equals("video")) {
                videoUrl = json.getJSONObject("videos").getJSONObject("standard_resolution").getString("url");
            }

            commentsCount = json.getJSONObject("comments").getInt("count");

            comments = new ArrayList<>();

            JSONArray commentsJSON = null;
            commentsJSON = json.getJSONObject("comments").getJSONArray("data"); // array of comments
            for (int i = 0; i < commentsJSON.length(); i++) {
                // get json at that position in array
                JSONObject commentJSON = commentsJSON.getJSONObject(i);
                // decode attributes into data model
                InstagramComment comment = new InstagramComment();
                comment.populateInstagramCommentWithJSON(commentJSON);

                comments.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}

