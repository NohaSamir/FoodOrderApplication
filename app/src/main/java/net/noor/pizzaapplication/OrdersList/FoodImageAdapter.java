package net.noor.pizzaapplication.OrdersList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.noor.pizzaapplication.DummyData.Food;
import net.noor.pizzaapplication.R;

/**
 * Created by nsamir on 7/11/2019.
 */
public class FoodImageAdapter extends RecyclerView.Adapter<FoodImageAdapter.ImageViewHolder> {

    private Food.FoodType foodType;
    private int numOfItems;

    public FoodImageAdapter(Food.FoodType foodType, int numOfItems) {
        this.foodType = foodType;
        this.numOfItems = numOfItems;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        Context context = parent.getContext();
        if (foodType.equals(Food.FoodType.PIZZA)) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.item_pizza_image, parent, false);
        } else if (foodType.equals(Food.FoodType.DRINKS)) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.item_drink_image, parent, false);
        } else if (foodType.equals(Food.FoodType.BURGER)) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.item_buger_image, parent, false);
        }
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Animation anim = null;
        if (foodType.equals(Food.FoodType.PIZZA)) {
            anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.pizza_item_transition);
        } else {
            anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.food_item_transition);
        }

        holder.itemView.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return numOfItems;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageViewHolder(View itemView) {
            super(itemView);
        }

        public void set(ImageView item) {
            //UI setting code
        }
    }

    public void deleteItem() {
        numOfItems--;
        notifyItemRemoved(numOfItems);
    }

    public void addItem() {
        numOfItems++;
        notifyItemInserted(numOfItems);
    }

}