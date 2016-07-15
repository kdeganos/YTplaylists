package com.epicodus.ytplaylists.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.adapters.FirebaseVideoViewHolder;
import com.epicodus.ytplaylists.models.VideoObj;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlaylistActivity extends AppCompatActivity {
    private DatabaseReference mVideoReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist);
        ButterKnife.bind(this);

        mVideoReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<VideoObj, FirebaseVideoViewHolder>
                (VideoObj.class, R.layout.video_list_item, FirebaseVideoViewHolder.class,
                        mVideoReference) {
            @Override
            protected void populateViewHolder (FirebaseVideoViewHolder viewHolder,
                                               VideoObj model, int position) {
                viewHolder.bindVideo(model);

            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
