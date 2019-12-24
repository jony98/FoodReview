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

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private Context contex;
    private List<Pizza> pizzaData;


    public PizzaAdapter(Context contex, List<Pizza> pizzaData) {
        this.contex = contex;
        this.pizzaData = pizzaData;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(contex);
        view = mInflater.inflate(R.layout.food_item_list, viewGroup, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PizzaViewHolder myViewHolder, final int position) {


        final Pizza pizza_list = pizzaData.get(position);
        myViewHolder.title.setText(pizza_list.getTitle());
        myViewHolder.price.setText("BDT "+pizza_list.getPrice());
        myViewHolder.rating.setText(pizza_list.getRating());
        //Float RatingBar = Float.parseFloat(pizza_list.rating);
        //myViewHolder.ratingBar.setRating(RatingBar);
        Picasso.get().load(pizza_list.getPhoto()).placeholder(R.drawable.header).into(myViewHolder.photo);


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex, FoodDetailsActivity.class);
                intent.putExtra("title",pizza_list.getTitle());
                intent.putExtra("price",pizza_list.getPrice());
                //intent.putExtra("ratingBar",pizza_list.getRating());
                intent.putExtra("rating",pizza_list.getRating());
                intent.putExtra("description",pizza_list.description);
                intent.putExtra("photo",pizza_list.getPhoto());
                contex.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return pizzaData.size();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {

        private TextView title,price,rating, description;
        private ImageView photo;
        private CardView cardView;
        private RatingBar ratingBar;

        public PizzaViewHolder(@NonNull View itemView) {
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
