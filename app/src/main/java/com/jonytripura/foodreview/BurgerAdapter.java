package com.jonytripura.foodreview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.BurgerViewHolder> {

    private Context contex;
    private List<Burger> burgerData;

    public BurgerAdapter(Context contex, List<Burger> burgerData) {
        this.contex = contex;
        this.burgerData = burgerData;
    }

    @NonNull
    @Override
    public BurgerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(contex);
        view = mInflater.inflate(R.layout.food_item_list, viewGroup, false);
        return new BurgerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BurgerViewHolder myViewHolder, final int position) {


        final Burger burger_list = burgerData.get(position);
        myViewHolder.title.setText(burger_list.getTitle());
        myViewHolder.price.setText("BDT "+burger_list.getPrice());
        myViewHolder.rating.setText(burger_list.getRating());
        //Float RatingBar = Float.parseFloat(burger_list.rating);
       // myViewHolder.ratingBar.setRating(RatingBar);
        //myViewHolder.description.setText(burger_list.getDescription());
        Picasso.get().load(burger_list.getPhoto()).placeholder(R.drawable.header).into(myViewHolder.photo);


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex, FoodDetailsActivity.class);
                intent.putExtra("title",burger_list.getTitle());
                intent.putExtra("price",burger_list.getPrice());
               //intent.putExtra("ratingBar",burger_list.getRating());
                intent.putExtra("rating",burger_list.getRating());
                intent.putExtra("description",burger_list.description);
                intent.putExtra("photo",burger_list.getPhoto());
                contex.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return burgerData.size();
    }

    public static class BurgerViewHolder extends RecyclerView.ViewHolder {

        private TextView title,price,rating, description;
        private ImageView photo;
        private CardView cardView;
        private RatingBar ratingBar;

        public BurgerViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            //description = itemView.findViewById(R.id.desc);
            photo = itemView.findViewById(R.id.food_image);
            cardView = itemView.findViewById(R.id.cardView);


        }


    }
}
