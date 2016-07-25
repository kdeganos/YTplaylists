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
import android.widget.Button;
import android.widget.EditText;

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

public class PlaylistActivity extends AppCompatActivity implements OnStartDragListener, View.OnClickListener {
    public static final String TAG = PlaylistActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String mUId;
    private String playlistName;
    private List<VideoObj> mVideos = new ArrayList<>();

    private DatabaseReference mPlaylistReference;
    private FirebaseVideoListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.videoSearchButton) Button mVideoSearchButton;
    @Bind(R.id.videoSearchEditText) EditText mVideoSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        playlistName = intent.getStringExtra("playlistName");
        getSupportActionBar().setTitle(playlistName);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mUId = user.getUid();
                    mPlaylistReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS)
                            .child(mUId);

                    mPlaylistReference.child(Constants.FIREBASE_CHILD_PLAYLISTS).child(playlistName).child(Constants.FIREBASE_CHILD_VIDEOS)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                setUpFirebaseAdapter();

                            } else {
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

        mVideoSearchButton.setOnClickListener(this);
    }

    private void setUpFirebaseAdapter() {

        mFirebaseAdapter = new FirebaseVideoListAdapter (VideoObj.class, R.layout.video_list_item_drag, FirebaseVideoViewHolder.class,
                mPlaylistReference.child(Constants.FIREBASE_CHILD_PLAYLISTS).child(playlistName).child(Constants.FIREBASE_CHILD_VIDEOS), this, this);

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
        if(mFirebaseAdapter != null) mFirebaseAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_playlist, menu);
        ButterKnife.bind(this);


//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Intent intent = new Intent(PlaylistActivity.this, SearchActivity.class);
//                intent.putExtra("searchTerms", query);
//                intent.putExtra("playlistName", playlistName);
//                intent.putExtra("uId", mUId);
//                startActivity(intent);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        return true;

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_user) {
            return true;

        }
        return super.onOptionsItemSelected(item);    }

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

    @Override
    public void onClick(View view) {
        if (view == mVideoSearchButton) {
            String query = mVideoSearchEditText.getText().toString();
            Intent intent = new Intent(PlaylistActivity.this, SearchActivity.class);
            intent.putExtra("searchTerms", query);
            intent.putExtra("playlistName", playlistName);
            intent.putExtra("uId", mUId);
            startActivity(intent);
        }
    }
}
