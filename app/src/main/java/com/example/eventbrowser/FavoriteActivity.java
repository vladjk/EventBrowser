package com.example.eventbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FavoriteActivity extends AppCompatActivity {

    DatabaseReference favoriteDatabase;
    RecyclerView newsfeedList;

    // Allows static methods to be called by other methods
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Gets according table from firebase and keeps it synced in realtime
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("Favorite");
        favoriteDatabase.keepSynced(true);

        // References the feed and shows contents
        newsfeedList = (RecyclerView) findViewById(R.id.recycleViewFeed);
        newsfeedList.setHasFixedSize(true);
        newsfeedList.setLayoutManager(new LinearLayoutManager(this));

        // Reference toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Favorites");

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Retrieves "Favorite" table specific data
        FirebaseRecyclerAdapter<Feed, MainActivity.FeedViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Feed, MainActivity.FeedViewHolder>
                (Feed.class,R.layout.eventcard_favorite, MainActivity.FeedViewHolder.class, favoriteDatabase) {
            @Override
            protected void populateViewHolder(MainActivity.FeedViewHolder feedViewHolder, Feed feed, int i) {
                feedViewHolder.setName(feed.getName());
                feedViewHolder.setDesc(feed.getDesc());
                feedViewHolder.setLoc(feed.getLoc());
                feedViewHolder.setMap(feed.getMap());
                feedViewHolder.setDate(feed.getDate());
                feedViewHolder.setImage(getApplicationContext(),feed.getImage());

            }
        };
        newsfeedList.setAdapter(firebaseRecyclerAdapter);
    }

}