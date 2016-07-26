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
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ytplaylists.Constants;
import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.UserObj;
import com.epicodus.ytplaylists.ui.PlaylistActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/25/16.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private ArrayList<UserObj> mUsers = new ArrayList<>();
    private String mPlaylistName;
    private String mOwnerUId;


    private Context mContext;

    @Bind(R.id.userNameTextView) TextView mUserNameTextView;
    @Bind(R.id.userEmailTextView) TextView mUserEmailTextView;


    public UserListAdapter(Context context, ArrayList<UserObj> users, String playlistName, String ownerId) {
        mContext = context;
        mUsers = users;
        mPlaylistName = playlistName;
        mOwnerUId = ownerId;


    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.userNameTextView) TextView mUserNameTextView;
        @Bind(R.id.userEmailTextView) TextView mUserEmailTextView;

        private Context mContext;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindUser(UserObj user) {
            mUserNameTextView.setText(user.getName());
            mUserEmailTextView.setText(user.getEmail());

        }

        @Override
        public void onClick(View view) {
            final int itemPosition = getLayoutPosition();

            final UserObj user = mUsers.get(itemPosition);
            String email = mUserEmailTextView.getText().toString();


            final DatabaseReference playlistRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USERS)
                    .child(mOwnerUId).child(Constants.FIREBASE_CHILD_PLAYLISTS).child(mPlaylistName);


            new AlertDialog.Builder(mContext)
                    .setTitle("Share Playlist")
                    .setMessage("Do you really want to share this playlist with " + email + "?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(mContext, "Shared" + String.valueOf(itemPosition), Toast.LENGTH_SHORT).show();

                            if (user.getName)
                            playlistRef.child("sharedUsers").push()
                                    .setValue(user.getUserId());

                            Intent intent = new Intent(mContext, PlaylistActivity.class);
                            intent.putExtra("playlistName", mPlaylistName);
                            mContext.startActivity(intent);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bindUser(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}
