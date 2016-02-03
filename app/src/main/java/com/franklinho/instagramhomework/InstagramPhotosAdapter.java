package com.franklinho.instagramhomework;

import android.content.Context;
import android.text.Html;
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

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by franklinho on 2/2/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto>{
    // WHat data do we need from the activity
    //Context, data source
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // What our item looks like
    // Use template to display each photo

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data for this position
        //Check if using a recycled view, if not inflate
        // Look up the views for populating data
        // Insert item data into each of the view items
        // Return the created item as a view
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            //create new view form template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);

        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);
        TextView tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);

        // INsert model data into view items
        tvCaption.setText(photo.caption);

        tvCaption.setText(Html.fromHtml("<b><font color='#125688'>" + photo.username + "</font></b>"+" "+photo.caption));

        tvUsername.setText(photo.username);

        DateTime createdDateTime = new DateTime((long)photo.createdTime*1000);
        DateTime currentDateTime = new DateTime();

        int minuteDifference = Minutes.minutesBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMinutes();
        if (minuteDifference < 60) {
            tvTimestamp.setText(Integer.toString(minuteDifference)+"m");
        } else if (Hours.hoursBetween(createdDateTime.toLocalDateTime(),currentDateTime.toLocalDateTime()).getHours() < 24) {
            tvTimestamp.setText(Integer.toString(Hours.hoursBetween(createdDateTime.toLocalDateTime(),currentDateTime.toLocalDateTime()).getHours())+"h");
        } else if (Months.monthsBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMonths() < 1) {
            tvTimestamp.setText(Integer.toString(Weeks.weeksBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getWeeks()) + "w");
        } else {
            tvTimestamp.setText(Integer.toString(Months.monthsBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMonths())+"m");
        }


//        tvTimestamp.setText(Integer.toString(photo.createdTime));



        //Clear imageview
        ivPhoto.setImageResource(0);
        //Insert image using picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        ivProfilePhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.profileImageUrl).into(ivProfilePhoto);
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        tvLikeCount.setText(formatter.format(photo.likesCount) + " likes");

        return convertView;

    }


}

