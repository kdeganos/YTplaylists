package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.VideoObj;
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

    public VideoListAdapter(Context context, ArrayList<VideoObj> videos) {
        mContext = context;
        mVideos = videos;
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

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.thumbnailImageView) ImageView mthumbnailImageView;
        @Bind(R.id.titleTextView) TextView mTitleTextView;
        @Bind(R.id.descriptionTextView) TextView mDescriptionTextView;
        @Bind(R.id.publishedAtTextView) TextView mPublishedAtTextView;

        private Context mContext;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            int itemPosition = getLayoutPosition();
//            Intent intent = new Intent(mContext, VideoDetailActivity.class);
//            intent.putExtra("position", itemPosition + "");
//            Log.d(TAG, "onClick: " + mVideos);
//            intent.putExtra("videos", Parcels.wrap(mVideos));
//            mContext.startActivity(intent);
//        }

        public void bindVideo(VideoObj video) {
            Picasso.with(mContext).load(video.getThumbnail()).into(mthumbnailImageView);
            mTitleTextView.setText(video.getTitle());
            mDescriptionTextView.setText(video.getDescription());
            mPublishedAtTextView.setText(video.getPublishedAt());
        }
    }
}