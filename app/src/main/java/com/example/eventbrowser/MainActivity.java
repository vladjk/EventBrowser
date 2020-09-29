package com.example.eventbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView a,b,c,d,e;
    Button btn;
    DatabaseReference eventDatabase;
    RecyclerView newsfeedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Firebase connection established", Toast.LENGTH_SHORT).show();

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
            feedViewHolder.setImage(getApplicationContext(),feed.getImage());


            }
        };

        newsfeedList.setAdapter(firebaseRecyclerAdapter);
    }

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
        public void setImage(Context ctx,String image){
            ImageView post_image=(ImageView)mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }
}