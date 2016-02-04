package com.franklinho.instagramhomework;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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

import butterknife.Bind;
import butterknife.ButterKnife;


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
        ViewHolder holder;
        // get data for this position
        //Check if using a recycled view, if not inflate
        // Look up the views for populating data
        // Insert item data into each of the view items
        // Return the created item as a view
        InstagramPhoto photo = getItem(position);


        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }



//        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
//        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
//        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
//        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);
//        TextView tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
//        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
//        ImageButton btnPlayVideo = (ImageButton) convertView.findViewById(R.id.btnPlayVideo);
//        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
//        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);
//        Button btnAllComments = (Button) convertView.findViewById(R.id.btnAllComments);

        // INsert model data into view items
        holder.tvCaption.setText(photo.caption);

        holder.tvCaption.setText(Html.fromHtml("<b><font color='#125688'>" + photo.username + "</font></b>"+" "+photo.caption));

        holder.tvUsername.setText(photo.username);

        holder.btnAllComments.setText("View all " + Integer.toString(photo.commentsCount) + " comments");
        holder.btnAllComments.setTag(photo);


        if (photo.commentsCount >= 2) {
            InstagramComment comment1 = (InstagramComment) photo.comments.get(0);
            InstagramComment comment2 = (InstagramComment) photo.comments.get(1);

            holder.tvComment1.setText(Html.fromHtml("<b><font color='#125688'>" + comment1.username + "</font></b>"+" "+ comment1.text));
            holder.tvComment2.setText(Html.fromHtml("<b><font color='#125688'>" + comment2.username + "</font></b>"+" "+ comment2.text));
        }

        DateTime createdDateTime = new DateTime((long)photo.createdTime*1000);
        DateTime currentDateTime = new DateTime();

        if (photo.type == "video") {
            holder.btnPlayVideo.setVisibility(View.VISIBLE);
        }

        int minuteDifference = Minutes.minutesBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMinutes();
        if (minuteDifference < 60) {
            holder.tvTimestamp.setText(Integer.toString(minuteDifference)+"m");
        } else if (Hours.hoursBetween(createdDateTime.toLocalDateTime(),currentDateTime.toLocalDateTime()).getHours() < 24) {
            holder.tvTimestamp.setText(Integer.toString(Hours.hoursBetween(createdDateTime.toLocalDateTime(),currentDateTime.toLocalDateTime()).getHours())+"h");
        } else if (Months.monthsBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMonths() < 1) {
            holder.tvTimestamp.setText(Integer.toString(Weeks.weeksBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getWeeks()) + "w");
        } else {
            holder.tvTimestamp.setText(Integer.toString(Months.monthsBetween(createdDateTime.toLocalDateTime(), currentDateTime.toLocalDateTime()).getMonths())+"m");
        }


//        tvTimestamp.setText(Integer.toString(photo.createdTime));



        //Clear imageview
        holder.ivPhoto.setImageResource(0);
        //Insert image using picasso
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.photo_placeholder).into(holder.ivPhoto);

        holder.ivProfilePhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.profileImageUrl).into(holder.ivProfilePhoto);
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        holder.tvLikeCount.setText(formatter.format(photo.likesCount) + " likes");



        return convertView;

    }

    static class ViewHolder {
        @Bind(R.id.tvCaption) TextView tvCaption ;
        @Bind(R.id.ivPhoto)ImageView ivPhoto;
        @Bind(R.id.tvUsername)TextView tvUsername;
        @Bind(R.id.ivProfilePhoto)ImageView ivProfilePhoto;
        @Bind(R.id.tvLikeCount)TextView tvLikeCount;
        @Bind(R.id.tvTimestamp)TextView tvTimestamp;
        @Bind(R.id.btnPlayVideo)ImageButton btnPlayVideo;
        @Bind(R.id.tvComment1)TextView tvComment1;
        @Bind(R.id.tvComment2)TextView tvComment2;
        @Bind(R.id.btnAllComments)Button btnAllComments;

        public ViewHolder (View view) {
            ButterKnife.bind(this, view);
        }
    }
}

