package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.VideoObj;
import com.epicodus.ytplaylists.ui.PlaylistActivity;
import com.epicodus.ytplaylists.ui.SearchActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/8/16.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    public static final String TAG = VideoListAdapter.class.getSimpleName();
    private ArrayList<VideoObj> mVideos = new ArrayList<>();
    private Context mContext;
    private String mPlaylistName;
    private String mPlaylistId;


    public VideoListAdapter(Context context, ArrayList<VideoObj> videos, String playlistName, String playlistId) {
        mContext = context;
        mVideos = videos;
        mPlaylistName = playlistName;
        mPlaylistId = playlistId;

    }

    @Override
    public VideoListAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoListAdapter.VideoViewHolder holder, int position) {
        holder.bindVideo(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.thumbnailImageView) ImageView mthumbnailImageView;
        @Bind(R.id.titleTextView) TextView mTitleTextView;
        @Bind(R.id.descriptionTextView) TextView mDescriptionTextView;
        @Bind(R.id.publishedAtTextView) TextView mPublishedAtTextView;

        private Context mContext;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(mContext, VideoDetailActivity.class);
//            intent.putExtra("position", itemPosition + "");
//            Log.d(TAG, "onClick: " + mVideos);
//            intent.putExtra("videos", Parcels.wrap(mVideos));
//            mContext.startActivity(intent);

            new AlertDialog.Builder(mContext)
                    .setTitle("Add to playlist")
                    .setMessage("Do you really want to add this video to your playlist?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(mContext, "Video added", Toast.LENGTH_SHORT).show();

                            DatabaseReference playlistRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_PLAYLISTS);

                            playlistRef.child(mPlaylistId)
                                    .child(Constants.FIREBASE_CHILD_VIDEOS)
                                    .child(mVideos.get(itemPosition).getVideoId()).setValue(mVideos.get(itemPosition));

                            Intent intent = new Intent(mContext, PlaylistActivity.class);
                            intent.putExtra("playlistName", mPlaylistName);
                            intent.putExtra("playlistId", mPlaylistId);

                            mContext.startActivity(intent);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

        }

        public void bindVideo(VideoObj video) {
            Picasso.with(mContext).load(video.getThumbnail()).into(mthumbnailImageView);
            mTitleTextView.setText(video.getTitle());
            mDescriptionTextView.setText(video.getDescription());
            mPublishedAtTextView.setText(video.getPublishedAt());
        }
    }
}