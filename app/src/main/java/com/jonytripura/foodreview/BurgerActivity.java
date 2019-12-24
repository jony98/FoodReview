package com.jonytripura.foodreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BurgerActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private DatabaseReference myRootRef;
    private BurgerAdapter myAdapter;
    private Toolbar toolbar;
    private List<Burger> BurgerList;
    private  ProgressBar progressBar;

    Animation anim_from_bottom, anim_from_top, anim_from_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);

        toolbar = findViewById(R.id.toolbar_burger);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Burger");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progress_burger);
        progressBar.setVisibility(View.VISIBLE);


        myRootRef = FirebaseDatabase.getInstance()
                .getReference("Burger");
        myRecyclerView = findViewById(R.id.burger_recyclerview);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Load Animations
        anim_from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);

        myRecyclerView.setAnimation(anim_from_bottom);
    }

    @Override
    public void onStart() {
        super.onStart();

        BurgerList = new ArrayList<>();
        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BurgerList.clear();
                for (DataSnapshot burger : dataSnapshot.getChildren()) {
                    final Burger food_list = burger.getValue(Burger.class);
                    BurgerList.add(food_list);
                }
                myAdapter = new BurgerAdapter(BurgerActivity.this, BurgerList);
                myRecyclerView.setAdapter(myAdapter);
                progressBar.setVisibility(View.INVISIBLE);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent home = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(home);
        finish();

    }
}
