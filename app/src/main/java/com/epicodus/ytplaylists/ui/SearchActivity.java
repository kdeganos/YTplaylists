package com.epicodus.ytplaylists.ui;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.epicodus.ytplaylists.R;
import com.epicodus.ytplaylists.adapters.VideoListAdapter;
import com.epicodus.ytplaylists.models.VideoObj;
import com.epicodus.ytplaylists.services.SearchService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    public static final String TAG = SearchActivity.class.getSimpleName();

    private String mPlaylistName;
    private String mUId;

    @Bind(R.id.recyclerView)

    RecyclerView mRecyclerView;

    private VideoListAdapter mAdapter;
    public ArrayList<VideoObj> mVideos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String searchTerms = intent.getStringExtra("searchTerms");
        mPlaylistName = intent.getStringExtra("playlistName");
        mUId = intent.getStringExtra("uId");

        getVideos(searchTerms);

    }

    private void getVideos(String searchTerms) {
        final SearchService searchService = new SearchService();

        searchService.findVideos(searchTerms, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mVideos = searchService.processResults(response);

                SearchActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new VideoListAdapter(getApplicationContext(), mVideos, mPlaylistName, mUId);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(SearchActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                    }
                });
            }
        });
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
                getVideos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }
}
