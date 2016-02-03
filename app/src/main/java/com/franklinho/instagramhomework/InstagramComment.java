package com.franklinho.instagramhomework;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by franklinho on 2/3/16.
 */
public class InstagramComment {
    public int createdTime;
    public String username;
    public String profileImageUrl;
    public String text;

    public void populateInstagramCommentWithJSON(JSONObject json) {
        try {

            username = json.getJSONObject("from").getString("username");
            profileImageUrl = json.getJSONObject("from").getString("profile_picture");
            createdTime = json.getInt("created_time");
            text =json.getString("text");


            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

}
