package com.franklinho.instagramhomework;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

public class ShowCommentsActivity extends AppCompatActivity {
    private ArrayList<InstagramComment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comments);
        setContentView(R.layout.fragment_all_comments);

        comments = new ArrayList<>();

        ArrayList<Parcelable> commentsArray = getIntent().getParcelableArrayListExtra("comments");
        for (int i =0; i < commentsArray.size(); i++) {
            InstagramComment currentComment =  Parcels.unwrap(commentsArray.get(i));
            comments.add(currentComment);
        }


        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        InstagramCommentsAdapter aComments = new InstagramCommentsAdapter(this, comments);
        lvComments.setAdapter(aComments);
    }
}
