package com.franklinho.instagramhomework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Weeks;

import java.util.List;

/**
 * Created by franklinho on 2/3/16.
 */
public class InstagramCommentsAdapter extends ArrayAdapter<InstagramComment> {
    // WHat data do we need from the activity
    //Context, data source
    public InstagramCommentsAdapter(Context context, List<InstagramComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // What our item looks like
    // Use template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
        // get data for this position
        //Check if using a recycled view, if not inflate
        // Look up the views for populating data
        // Insert item data into each of the view items
        // Return the created item as a view
        InstagramComment comment = getItem(position);
        if (convertView == null) {
            //create new view form template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);

        }

        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        TextView tvCommentText = (TextView) convertView.findViewById(R.id.tvCommentText);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);


        // INsert model data into view items
        tvUsername.setText(comment.username);
        tvCommentText.setText(comment.text);


        DateTime createdDateTime = new DateTime((long)comment.createdTime*1000);
        DateTime currentDateTime = new DateTime();



        int minuteDifference = Minutes.minutesBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMinutes();
        if (minuteDifference < 60) {
            tvTimestamp.setText(Integer.toString(minuteDifference)+"m");
        } else if (Hours.hoursBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getHours() < 24) {
            tvTimestamp.setText(Integer.toString(Hours.hoursBetween(createdDateTime.toLocalDateTime(),currentDateTime.toLocalDateTime()).getHours())+"h");
        } else if (Months.monthsBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMonths() < 1) {
            tvTimestamp.setText(Integer.toString(Weeks.weeksBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getWeeks()) + "w");
        } else {
            tvTimestamp.setText(Integer.toString(Months.monthsBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMonths())+"m");
        }


        //Clear imageview
        ivProfilePhoto.setImageResource(0);
        //Insert image using picasso
        Picasso.with(getContext()).load(comment.profileImageUrl).into(ivProfilePhoto);




        return convertView;

    }


}
