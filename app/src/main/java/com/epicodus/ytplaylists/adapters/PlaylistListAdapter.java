package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.PlaylistObj;
import com.epicodus.ytplaylists.ui.PlaylistActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/26/16.
 */
public class PlaylistListAdapter extends RecyclerView.Adapter<PlaylistListAdapter.PlaylistViewHolder> {
    public static final String TAG = PlaylistListAdapter.class.getSimpleName();
    private ArrayList<PlaylistObj> mPlaylists = new ArrayList<>();
    private Context mContext;
    private String mPlaylistName;
    private String mUId;

    public PlaylistListAdapter(Context context, ArrayList<PlaylistObj> videos, String playlistName, String uId) {
        mContext = context;
        mPlaylists = videos;
        mPlaylistName = playlistName;
        mUId = uId;

    }

    @Override
    public PlaylistListAdapter.PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_list_item, parent, false);
        PlaylistViewHolder viewHolder = new PlaylistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlaylistListAdapter.PlaylistViewHolder holder, int position) {
        holder.bindPlaylist(mPlaylists.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.playlistNameTextView) TextView mPlaylistNameTextView;
        @Bind(R.id.playlistDateTextView) TextView mPlaylistDateTextView;

        private Context mContext;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(mContext, PlaylistDetailActivity.class);
//            intent.putExtra("position", itemPosition + "");
//            Log.d(TAG, "onClick: " + mPlaylists);
//            intent.putExtra("videos", Parcels.wrap(mPlaylists));
//            mContext.startActivity(intent);

            new AlertDialog.Builder(mContext)
                    .setTitle("Add to playlist")
                    .setMessage("Do you really want to add this video to your playlist?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(mContext, "Yaay", Toast.LENGTH_SHORT).show();

                            DatabaseReference playlistRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS)
                                    .child(mUId);

                            playlistRef.child(Constants.FIREBASE_CHILD_PLAYLISTS)
                                    .child(mPlaylistName)
                                    .child(Constants.FIREBASE_CHILD_VIDEOS)
                                    .push().setValue(mPlaylists.get(itemPosition));

                            Intent intent = new Intent(mContext, PlaylistActivity.class);
                            intent.putExtra("playlistName", mPlaylistName);
                            mContext.startActivity(intent);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

        }

        public void bindPlaylist(PlaylistObj video) {
            mPlaylistNameTextView.setText(video.getPlaylistName());
            mPlaylistDateTextView.setText(video.getTimestamp());
        }
    }
}
