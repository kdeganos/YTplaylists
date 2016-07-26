package com.epicodus.ytplaylists.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.adapters.UserListAdapter;
import com.epicodus.ytplaylists.models.UserObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddUserActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    private String mPlaylistName;
    private String mOwnerUId;
    private String mSearchTerms;
    private UserListAdapter mAdapter;

    private DatabaseReference mUserReference;


    private ArrayList<UserObj> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mPlaylistName = intent.getStringExtra("playlistName");
        mOwnerUId = intent.getStringExtra("uId");
        mSearchTerms = intent.getStringExtra("searchTerms");

        mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS);

        mUserReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            mUsers.add(userSnapshot.getValue(UserObj.class));
                        }

                        mAdapter = new UserListAdapter(getApplicationContext(), mUsers, mPlaylistName, mOwnerUId);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(AddUserActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



    }

    private void getUsers() {

        mAdapter = new UserListAdapter(getApplicationContext(), mUsers, mPlaylistName, mOwnerUId);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(AddUserActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}
