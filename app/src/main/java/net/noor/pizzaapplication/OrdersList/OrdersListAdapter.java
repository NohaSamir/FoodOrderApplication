package net.noor.pizzaapplication.OrdersList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import net.noor.pizzaapplication.DummyData.Food;
import net.noor.pizzaapplication.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nsamir on 7/8/2019.
 */
public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ShoppingViewHolder> {

    private List<Food> items;

    public OrdersListAdapter(List<Food> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ShoppingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        Food item = items.get(position);
        holder.set(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.titleTextView)
        TextView titleTextView;

        @Bind(R.id.priceTextView)
        TextView priceTextView;

        @Bind(R.id.verticalRecycler)
        RecyclerView verticalRecycler;

        @Bind(R.id.horizontalRecycler)
        RecyclerView horizontalRecycler;

        @Bind(R.id.textView)
        TextView numOfItemsTextView;

        @Bind(R.id.minusButton)
        ImageView minusButton;

        @Bind(R.id.plusButton)
        ImageView plusButton;

        private Context mContext;
        private FoodImageAdapter mFoodImageAdapter;

        ShoppingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void set(Food item) {
            //UI setting code
            titleTextView.setText(item.getTitle());
            priceTextView.setText(mContext.getString(R.string.price, item.getPrice()));
            numOfItemsTextView.setText(String.valueOf(item.getNumOfItems()));

            mFoodImageAdapter = new FoodImageAdapter(item.getFoodType(), item.getNumOfItems());

            if (item.getFoodType().equals(Food.FoodType.PIZZA)) {
                verticalRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                verticalRecycler.setAdapter(mFoodImageAdapter);
                verticalRecycler.setVisibility(View.VISIBLE);
            } else {
                horizontalRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                horizontalRecycler.setAdapter(mFoodImageAdapter);
                horizontalRecycler.setVisibility(View.VISIBLE);
            }

            minusButton.setOnClickListener(v ->
            {
                if (item.getNumOfItems() > 0) {
                    mFoodImageAdapter.deleteItem();
                    item.setNumOfItems(item.getNumOfItems() - 1);
                    numOfItemsTextView.setText(String.valueOf(item.getNumOfItems()));

                    if (horizontalRecycler.getVisibility() == View.VISIBLE) {
                        vibrateContainerList();
                    }

                    //Delete cell in case number of item equal 0
                    if (item.getNumOfItems() == 0) {
                        items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }

                }
            });

            plusButton.setOnClickListener(v -> {
                mFoodImageAdapter.addItem();
                item.setNumOfItems(item.getNumOfItems() + 1);
                numOfItemsTextView.setText(String.valueOf(item.getNumOfItems()));

                if (horizontalRecycler.getVisibility() == View.VISIBLE) {
                    vibrateContainerList();
                }
            });
        }


        private void vibrateContainerList() {
            Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.recycler_horiontal_vibration);
            anim.setStartOffset(100);
            horizontalRecycler.startAnimation(anim);
        }
    }


}