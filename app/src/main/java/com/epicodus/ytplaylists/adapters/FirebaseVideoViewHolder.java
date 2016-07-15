package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.VideoObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Guest on 7/15/16.
 */
public class FirebaseVideoViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    public FirebaseVideoViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindVideo(VideoObj video) {
        ImageView thumbnailImageView = (ImageView) mView.findViewById(R.id.thumbnailImageView);
        TextView titleTextView = (TextView) mView.findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) mView.findViewById(R.id.descriptionTextView);
        TextView publishedAtTextView = (TextView) mView.findViewById(R.id.publishedAtTextView);

        Picasso.with(mContext).load(video.getThumbnail()).into(thumbnailImageView);
        titleTextView.setText(video.getTitle());
        descriptionTextView.setText(video.getDescription());
        publishedAtTextView.setText(video.getPublishedAt());
    }

}
