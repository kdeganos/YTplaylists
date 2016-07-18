package com.epicodus.ytplaylists.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.adapters.FirebasePlaylistViewHolder;
import com.epicodus.ytplaylists.models.PlaylistObj;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mUserReference;

    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private String mUId;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.newPlaylistButton) Button mNewPlayListButton;
    @Bind(R.id.newPlaylistNameEditText) EditText mNewPlaylistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String name = user.getDisplayName();
                    mUId = user.getUid();
                    getSupportActionBar().setTitle("Welcome, " + name + "!");
                    mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS)
                            .child(mUId).child(Constants.FIREBASE_CHILD_PLAYLISTS);

                    setupFirebaseAdapter();

                } else {
                }
            }
        };

        mNewPlayListButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mNewPlayListButton) {
            final String newPlaylistName = mNewPlaylistName.getText().toString().trim();

            mUserReference.child(newPlaylistName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        mNewPlaylistName.setError(newPlaylistName + " already exists!");
                    } else {
                        List videoIds = new ArrayList<>();
                        Date date = new Date();

                        PlaylistObj playlist = new PlaylistObj(newPlaylistName, date, videoIds);
                        mUserReference.child(newPlaylistName).setValue(playlist);
                        Intent intent = new Intent(MainActivity.this, PlaylistActivity.class);
                        intent.putExtra("playlistName", newPlaylistName);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError arg0) {
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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

    private void setupFirebaseAdapter() {
        Log.d(TAG, "setupFirebaseAdapter: " +mUserReference);
        mFirebaseAdapter = new FirebaseRecyclerAdapter<PlaylistObj, FirebasePlaylistViewHolder>
                (PlaylistObj.class, R.layout.playlist_list_item, FirebasePlaylistViewHolder.class, mUserReference) {
            @Override
            protected void populateViewHolder(FirebasePlaylistViewHolder viewHolder, PlaylistObj model, int position) {
                viewHolder.bindPlaylist(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }
}
