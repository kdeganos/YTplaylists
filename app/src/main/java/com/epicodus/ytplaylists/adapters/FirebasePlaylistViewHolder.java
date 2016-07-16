package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.PlaylistObj;
import com.epicodus.ytplaylists.models.VideoObj;
import com.squareup.picasso.Picasso;

/**
 * Created by Guest on 7/15/16.
 */
public class FirebasePlaylistViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    public FirebasePlaylistViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }

    public void bindPlaylist(PlaylistObj playlist) {
        TextView playlistNameTextView = (TextView) mView.findViewById(R.id.playlistNameTextView);
        TextView dateTextView = (TextView) mView.findViewById(R.id.playlistDateTextView);

        playlistNameTextView.setText(playlist.getPlaylistName());
        dateTextView.setText(playlist.getTimestamp());
    }

}
