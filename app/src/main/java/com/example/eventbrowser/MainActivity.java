package com.example.eventbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    DatabaseReference eventDatabase;
    DatabaseReference favoriteDatabase;
    RecyclerView newsfeedList;
    // Allows static methods to be called by other methods
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Allows static methods to be called by other methods
        mContext = this;

        // Gets according table from firebase and keeps it synced in realtime
        eventDatabase = FirebaseDatabase.getInstance().getReference().child("Event");
        eventDatabase.keepSynced(true);

        //Gets database reference of the "Favorites" database
        favoriteDatabase = FirebaseDatabase.getInstance().getReference().child("Favorite");
        favoriteDatabase.keepSynced(true);

        // References the feed and shows contents
        newsfeedList = (RecyclerView) findViewById(R.id.recycleViewFeed);
        newsfeedList.setHasFixedSize(true);
        newsfeedList.setLayoutManager(new LinearLayoutManager(this));

        // Reference toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
    }

    // Override and reference menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //Menu options, onclick listeners for menu items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_favorite:
                Intent intent1 = new Intent(this, FavoriteActivity.class);
                startActivity(intent1);
                return true;
            case R.id.item_settings:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Stores data from firebase into adapter.
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
            feedViewHolder.addFav(feed.getName(),feed.getDesc(),feed.getLoc(),feed.getMap(),feed.getDate(),feed.getImage());
            }
        };
        newsfeedList.setAdapter(firebaseRecyclerAdapter);

    }


    // Retrieve contents from the viewholder adapter into to the Cardview textviews
    public static class FeedViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FeedViewHolder(View itemView){
            super(itemView);
            mView=itemView;

        }
        public void setName(final String name){
            TextView post_name = (TextView)mView.findViewById(R.id.post_name);
            post_name.setText(name);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        // Picasso API for image rendering and compression
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

            // On click listener for the maps button, opens maps with the according location
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
        // On click listener for the Add favorite button
        public void addFav(final String name, final String desc, final String loc, final String map, final String date, final String image){
            Button post_favorite =(Button)mView.findViewById(R.id.post_favorite);


            post_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String key = FirebaseDatabase.getInstance().getReference().child("Favorite").push().getKey();

                    HashMap<String, Object> fav = new HashMap<>();
                    fav.put("name",name);
                    fav.put("map",map);
                    fav.put("loc",loc);
                    fav.put("image",image);
                    fav.put("desc",desc);
                    fav.put("date",date);
                    fav.put("id", key);

                    FirebaseDatabase.getInstance().getReference().child("Favorite").push().updateChildren(fav);

                    Toast.makeText(mContext, "Added to favorites", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

}