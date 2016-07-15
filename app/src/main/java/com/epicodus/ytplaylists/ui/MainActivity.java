package com.epicodus.ytplaylists.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.ytplaylists.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mSearchButton) {
            String searchTerms = mSearchTermsEditText.getText().toString();
            Intent intent = new Intent(MemberActivity.this, SearchActivity.class);
            intent.putExtra("searchTerms", searchTerms);
            startActivity(intent);
        }
    }
}
