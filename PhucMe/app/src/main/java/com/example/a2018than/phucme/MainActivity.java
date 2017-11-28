package com.example.a2018than.phucme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private TextView mView;
    private TextView mView2;
    private Button mButton;
    private EditText mEditText;
    private String mId;
    private StatusesService statusesService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editLmao);
        mButton = (Button) findViewById(R.id.submit);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("obIgTLEVw3hV2pj44q72O7Ypn", "3EjVyesNesHF1EF4c1tJMWtZ3IY7x6uGWyCvL9HKgtynV4laMm"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        statusesService = twitterApiClient.getStatusesService();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mView2 = (TextView) findViewById(R.id.hello);
                mId = mEditText.getText().toString();
                mEditText.setText("");
                Call<List<Tweet>> callUser = statusesService.userTimeline(null, mId, null, null, null, null, null, null, null);

                callUser.enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        mView2.setText("Latest Tweets by: " + mId);
                        mView = (TextView) findViewById(R.id.usersList);
                        String message = "";
                        for(Tweet lmao:result.data){
                            message += "\n" + lmao.text;
                        }
                        mView.setText(message);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result to the login button.
//        loginButton.onActivityResult(requestCode, resultCode, data);
//    }
}
