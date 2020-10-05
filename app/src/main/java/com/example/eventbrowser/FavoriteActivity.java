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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FavoriteActivity extends AppCompatActivity {

    DatabaseReference eventDatabase;
    RecyclerView newsfeedList;
    /** Allows static methods to be called by other methods **/
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        /** Gets according table from firebase and keeps it synced in realtime **/
        eventDatabase = FirebaseDatabase.getInstance().getReference().child("Favorite");
        eventDatabase.keepSynced(true);

        /** References the feed and shows contents**/
        newsfeedList = (RecyclerView) findViewById(R.id.recycleViewFeed);
        newsfeedList.setHasFixedSize(true);
        newsfeedList.setLayoutManager(new LinearLayoutManager(this));

        /** Reference toolbar **/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Favorites");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Feed, MainActivity.FeedViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Feed, MainActivity.FeedViewHolder>
                (Feed.class,R.layout.eventcard_favorite, MainActivity.FeedViewHolder.class, eventDatabase) {
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


    /** retrieve contents from Firebase into to the Cardview **/
    public static class FeedViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FeedViewHolder(View itemView){
            super(itemView);
            mView=itemView;

        }
        public void setName(String name){
            TextView post_name = (TextView)mView.findViewById(R.id.post_name);
            post_name.setText(name);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        /** Picasso API for image rendering and compression **/
        public void setImage(Context ctx,String image){
            ImageView post_image=(ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);

        }
        public void setLoc(String loc){
            TextView post_loc = (TextView)mView.findViewById(R.id.post_loc);
            post_loc.setText(loc);
        }
        public void setDate(String date){
            TextView post_date = (TextView)mView.findViewById(R.id.post_date);
            post_date.setText(date);
        }

        public void setMap(final String map){
            Button post_map = (Button)mView.findViewById(R.id.post_map);

            /** Onclick listener for the maps button, opens maps with the according location **/
            post_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(map);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}