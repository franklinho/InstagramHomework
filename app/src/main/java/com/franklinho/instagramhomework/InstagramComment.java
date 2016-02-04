package com.franklinho.instagramhomework;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by franklinho on 2/3/16.
 */
@Parcel
public class InstagramComment implements Serializable {
    public int createdTime;
    public String username;
    public String profileImageUrl;
    public String text;

    //Necessary for class to be Parcelable
    public InstagramComment() {
        // Normal actions performed by class, since this is still a normal object!
    }

    //Allows comment object to be populated by passing in JSON
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
