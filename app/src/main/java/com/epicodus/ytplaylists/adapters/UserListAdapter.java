package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.models.UserObj;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/25/16.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private ArrayList<UserObj> mUsers = new ArrayList<>();
    private Context mContext;

    public UserListAdapter(Context context, ArrayList<UserObj> users) {
        mContext = context;
        mUsers = users;
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

    public class UserViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.userNameTextView) TextView mUserNameTextView;
        @Bind(R.id.userEmailTextView) TextView mUserEmailTextView;

        private Context mContext;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindUser(UserObj user) {
            mUserNameTextView.setText(user.getName());
            mUserEmailTextView.setText(user.getEmail());
        }
    }
}
