package com.franklinho.instagramhomework;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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

//Adapter for popular photos listview
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto>{

    public ViewHolder holder;
    // What data do we need from the activity
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

        //Rewrote this to use Viewholder
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        // Insert model data into view items
        holder.tvCaption.setText(photo.caption);
        holder.tvCaption.setText(Html.fromHtml("<b><font color='#125688'>" + photo.username + "</font></b>"+" "+photo.caption));
        holder.tvUsername.setText(photo.username);
        holder.btnAllComments.setText("View all " + Integer.toString(photo.commentsCount) + " comments");
        //Attach photo tag to button
        holder.btnAllComments.setTag(photo);
        if (photo.commentsCount >= 2) {
            InstagramComment comment1 = (InstagramComment) photo.comments.get(0);
            InstagramComment comment2 = (InstagramComment) photo.comments.get(1);
            //Formats comments with username
            holder.tvComment1.setText(Html.fromHtml("<b><font color='#125688'>" + comment1.username + "</font></b>"+" "+ comment1.text));
            holder.tvComment2.setText(Html.fromHtml("<b><font color='#125688'>" + comment2.username + "</font></b>"+" "+ comment2.text));
        }
        DateTime createdDateTime = new DateTime((long)photo.createdTime*1000);
        DateTime currentDateTime = new DateTime();
        //Handle video functionality
        if (photo.type != null && photo.type.equals("video") ) {
//            holder.btnPlayVideo.setAlpha((float) 0.5);
            holder.btnPlayVideo.setVisibility(View.VISIBLE);
            holder.vvInstagramVideo.setVisibility(View.VISIBLE);
            holder.vvInstagramVideo.setVideoPath(photo.videoUrl);
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(holder.vvInstagramVideo);
            holder.vvInstagramVideo.setMediaController(mediaController);
            holder.vvInstagramVideo.requestFocus();
            holder.vvInstagramVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    holder.ivPhoto.setVisibility(View.INVISIBLE);
                    holder.btnPlayVideo.setVisibility(View.INVISIBLE);
                    holder.vvInstagramVideo.start();
                }
            });
//        }
        } else {
            //Resets listviewitem for reuse
            holder.ivPhoto.setVisibility(View.VISIBLE);
            holder.btnPlayVideo.setVisibility(View.INVISIBLE);
            holder.vvInstagramVideo.setVisibility(View.GONE);
            holder.vvInstagramVideo.stopPlayback();
//            holder.vvInstagramVideo.setVideoURI(null);
        }

        //This formats the timestamp to the relative date string
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

        //Clear imageview for reuse
        holder.ivPhoto.setImageResource(0);
        holder.ivProfilePhoto.setImageResource(0);

        //Insert image using picasso
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.photo_placeholder).into(holder.ivPhoto);
        Picasso.with(getContext()).load(photo.profileImageUrl).into(holder.ivProfilePhoto);

        //Formats the like count to have commas
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        holder.tvLikeCount.setText(formatter.format(photo.likesCount) + " likes");

        return convertView;

    }

    // Viewholder pattern
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
        @Bind(R.id.vvInstagramVideo) VideoView vvInstagramVideo;

        public ViewHolder (View view) {
            //Utilizing Butterknife
            ButterKnife.bind(this, view);
        }
    }
}

