package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.PlaylistObj;
import com.epicodus.ytplaylists.models.VideoObj;
import com.epicodus.ytplaylists.ui.PlaylistActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class FirebasePlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mUId;
    private DatabaseReference mUserReference;
    private String mPlaylistName;


    View mView;
    Context mContext;



    public FirebasePlaylistViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();

//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    String name = user.getDisplayName();
//                    mUId = user.getUid();
//                    mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS)
//                            .child(mUId).child(Constants.FIREBASE_CHILD_PLAYLISTIDS);
//
//
//                } else {
//                }
//            }
//        };


        itemView.setOnClickListener(this);
    }

    public void bindPlaylist(PlaylistObj playlist) {
        TextView playlistNameTextView = (TextView) mView.findViewById(R.id.playlistNameTextView);
        TextView dateTextView = (TextView) mView.findViewById(R.id.playlistDateTextView);
        playlistNameTextView.setText(playlist.getPlaylistName());
        dateTextView.setText(playlist.getTimestamp());

        mPlaylistName = playlist.getPlaylistName();
    }

    @Override
    public void onClick(View view) {

//        DatabaseReference ref = mUserReference;
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                PlaylistObj playlist;
//
//                playlist = dataSnapshot.getValue(PlaylistObj.class);
//
//                Intent intent = new Intent(mContext, PlaylistActivity.class);
//                intent.putExtra("playlistName", playlist.getPlaylistName());
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        Intent intent = new Intent(mContext, PlaylistActivity.class);
        intent.putExtra("playlistName", mPlaylistName);

        mContext.startActivity(intent);
    }
}
