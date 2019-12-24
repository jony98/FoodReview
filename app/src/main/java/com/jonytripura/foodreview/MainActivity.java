package com.jonytripura.foodreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private LinearLayout linearLayout;

    private RecyclerView myRecyclerView, myRecyclerView2;
    private DatabaseReference myRootRef;
    private PizzaAdapter pizzaAdapter;
    private BurgerAdapter burgerAdapter;
    private List<Pizza> PizzaList;
    private List<Burger> BurgerList;
    private ProgressBar progressBar;

    Animation anim_from_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progress_home);
        progressBar.setVisibility(View.VISIBLE);

        linearLayout = findViewById(R.id.home_linear);

        //Load Animations
        anim_from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);

        linearLayout .setAnimation(anim_from_bottom);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);

        myRecyclerView = findViewById(R.id.recycler_view);
        myRecyclerView2 = findViewById(R.id.recycler_view2);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);


        myRootRef = FirebaseDatabase.getInstance()
                .getReference("Burger");
        myRecyclerView2.setHasFixedSize(true);
        myRecyclerView2.setLayoutManager(new LinearLayoutManager(this));


        BurgerList = new ArrayList<>();

        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BurgerList.clear();
                for (DataSnapshot burger : dataSnapshot.getChildren()) {
                    final Burger food_list = burger.getValue(Burger.class);
                    BurgerList.add(food_list);

                }
                burgerAdapter = new BurgerAdapter(MainActivity.this, BurgerList);
                myRecyclerView2.setAdapter(burgerAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        myRootRef = FirebaseDatabase.getInstance()
                .getReference("Pizza");
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PizzaList = new ArrayList<>();

        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PizzaList.clear();
                for (DataSnapshot pizza : dataSnapshot.getChildren()) {
                    final Pizza food_list = pizza.getValue(Pizza.class);
                    PizzaList.add(food_list);

                }
                pizzaAdapter = new PizzaAdapter(MainActivity.this, PizzaList);
                myRecyclerView.setAdapter(pizzaAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.home:

                break;

            case R.id.pizza:

                Intent pizza = new Intent(getApplicationContext(),PizzaActivity.class);
                startActivity(pizza);
                break;


            case R.id.burger:
                Intent burger = new Intent(getApplicationContext(),BurgerActivity.class);
                startActivity(burger);

                break;

            case R.id.admin:
                Intent admin = new Intent(this,AdminLoginActivity.class);
                startActivity(admin);
                break;

            case R.id.about:
                Intent about = new Intent(this,AboutActivity.class);
                startActivity(about);

                break;

            case android.R.id.home:
                onBackPressed();

                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
