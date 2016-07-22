package com.epicodus.ytplaylists.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.adapters.FirebaseVideoListAdapter;
import com.epicodus.ytplaylists.adapters.FirebaseVideoViewHolder;
import com.epicodus.ytplaylists.models.VideoObj;
import com.epicodus.ytplaylists.util.OnStartDragListener;
import com.epicodus.ytplaylists.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlaylistActivity extends AppCompatActivity implements OnStartDragListener {
    public static final String TAG = PlaylistActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String mUId;
    private String mPlaylistName;
    private String mPlaylistId;
    private List<VideoObj> mVideos = new ArrayList<>();
    private boolean emptyPlaylist = true;

    private DatabaseReference mUserReference;
    private DatabaseReference mPlaylistReference;
    private FirebaseVideoListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mPlaylistName = intent.getStringExtra("playlistName");
        mPlaylistId = intent.getStringExtra("playlistId");
        getSupportActionBar().setTitle(mPlaylistName);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mUId = user.getUid();
                    mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS)
                            .child(mUId);

                    mPlaylistReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_PLAYLISTS).child(mPlaylistId);
                    mPlaylistReference.child(Constants.FIREBASE_CHILD_VIDEOS)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                emptyPlaylist = false;
                                setUpFirebaseAdapter();

                            } else {
                                emptyPlaylist = true;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError arg0) {
                        }
                    });
                } else {
                }
            }
        };


    }

    private void setUpFirebaseAdapter() {

        mFirebaseAdapter = new FirebaseVideoListAdapter (VideoObj.class, R.layout.video_list_item_drag, FirebaseVideoViewHolder.class,
                mPlaylistReference.child(Constants.FIREBASE_CHILD_VIDEOS), this, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!emptyPlaylist) mFirebaseAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);


        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
//                getVideos(query);
                Intent intent = new Intent(PlaylistActivity.this, SearchActivity.class);
                intent.putExtra("searchTerms", query);
                intent.putExtra("playlistName", mPlaylistName);
                intent.putExtra("playlistId", mPlaylistId);

                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(PlaylistActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
