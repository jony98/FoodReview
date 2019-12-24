package com.jonytripura.foodreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class FoodDetailsActivity extends AppCompatActivity {

    private  TextView title, price, rating, des;
    private ImageView foodPhoto;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private RatingBar ratingBar;

    Animation anim_from_bottom, anim_from_top, anim_from_left,getAnim_from_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);


        title = findViewById(R.id.food_title);
        price = findViewById(R.id.food_price);
        rating= findViewById(R.id.food_rating);
        des = findViewById(R.id.food_des);
        foodPhoto = findViewById(R.id.food_photo);

        linearLayout = findViewById(R.id.linear);

        toolbar = findViewById(R.id.toolbar_food_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Food Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Load Animations
        anim_from_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        anim_from_top = AnimationUtils.loadAnimation(this, R.anim.anim_from_top);
        anim_from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);
        getAnim_from_right = AnimationUtils.loadAnimation(this, R.anim.anim_from_right);

        title.setAnimation(getAnim_from_right);
        foodPhoto.setAnimation(anim_from_left);
        linearLayout.setAnimation(anim_from_top);


        Intent intent = getIntent();
        String food_title = intent.getStringExtra("title");
        String food_price= intent.getStringExtra("price");
        String food_rating = intent.getStringExtra("rating");
       //String food_ratingBar = intent.getStringExtra("ratingBar");
        String description = intent.getStringExtra("description");
        String photo = intent.getStringExtra("photo");


        title.setText(food_title);
        price.setText("BDT  "+food_price);
        rating.setText(food_rating);
        //Float RatingBar = Float.parseFloat(food_ratingBar);
        //ratingBar.setRating(RatingBar);
        des.setText(description);
        Picasso.get().load(photo).placeholder(R.drawable.header).into(foodPhoto);
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



    }

    public void order(View view) {
        Toast.makeText(this, "Next Update Coming Soon", Toast.LENGTH_SHORT).show();
    }
}
