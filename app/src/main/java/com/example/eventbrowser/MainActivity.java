package com.example.eventbrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView a,b,c,d,e;
    Button btn;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Firebase connection established", Toast.LENGTH_SHORT).show();

        a = (TextView) findViewById(R.id.txtname);
        b = (TextView) findViewById(R.id.txtloc);
        c = (TextView) findViewById(R.id.txtmap);
        d = (TextView) findViewById(R.id.txtdesc);
        e = (TextView) findViewById(R.id.txtdate);
        btn= (Button) findViewById(R.id.btnGetData);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("Event").child("1");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        String map = snapshot.child("map").getValue().toString();
                        String loc = snapshot.child("loc").getValue().toString();
                        String desc = snapshot.child("desc").getValue().toString();
                        String date = snapshot.child("date").getValue().toString();
                        a.setText(name);
                        b.setText(map);
                        c.setText(loc);
                        d.setText(desc);
                        e.setText(date);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}