package com.example.eventbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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


public class MainActivity extends AppCompatActivity {

    //Button mapButton;
    DatabaseReference eventDatabase;
    RecyclerView newsfeedList;
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
       // Toast.makeText(this, "Firebase connection established", Toast.LENGTH_SHORT).show();

        eventDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventDatabase.keepSynced(true);

        newsfeedList = (RecyclerView) findViewById(R.id.recycleViewFeed);
        newsfeedList.setHasFixedSize(true);
        newsfeedList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Feed,FeedViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Feed, FeedViewHolder>
                (Feed.class,R.layout.eventcard,FeedViewHolder.class, eventDatabase) {
            @Override
            protected void populateViewHolder(FeedViewHolder feedViewHolder, Feed feed, int i) {
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


    // retrieve contents from Firebase into to the Cardview
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
        //Picasso API for image rendering and compression
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

            /* On click listener for the maps button, opens maps with the according location */
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

    public void addFavorite(View view){
        Toast.makeText(this, "Feature soon to be added...", Toast.LENGTH_SHORT).show();
    }

}