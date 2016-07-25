package com.epicodus.ytplaylists.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.adapters.UserListAdapter;
import com.epicodus.ytplaylists.models.UserObj;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddUserActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private UserListAdapter mAdapter;

    public ArrayList<UserObj> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);

//        Intent intent = getEmailtIntent();

    }
}
