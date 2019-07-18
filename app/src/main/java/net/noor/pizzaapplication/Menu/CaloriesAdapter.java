package net.noor.pizzaapplication.Menu;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.noor.pizzaapplication.DummyData.Calories;
import net.noor.pizzaapplication.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nsamir on 7/2/2019.
 */
public class CaloriesAdapter extends RecyclerView.Adapter<CaloriesAdapter.CaloriesViewHolder> {

    private static final int ANIMATION_DURATION = 1000;
    private List<Calories> items;

    CaloriesAdapter(List<Calories> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CaloriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calorie_details, parent, false);
        return new CaloriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CaloriesViewHolder holder, int position) {
        Calories item = items.get(position);
        holder.set(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class CaloriesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tittleTextView)
        TextView tittleTextView;

        @Bind(R.id.gramTextView)
        TextView gramTextView;

        @Bind(R.id.percentageTextView)
        TextView percentageTextView;

        @Bind(R.id.rootLayout)
        ConstraintLayout rootLayout;


        CaloriesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void set(Calories item) {
            //UI setting code
            tittleTextView.setText(item.getTittle());
            startCountAnimation(this, item);
        }


        void clearAnimation() {
            rootLayout.clearAnimation();
        }

        private void startCountAnimation(final CaloriesViewHolder viewHolder, Calories calories) {
            ValueAnimator gramAnimator = ValueAnimator.ofInt(calories.getCalGram() + 20, calories.getCalGram());
            ValueAnimator percentageAnimator = ValueAnimator.ofInt(calories.getCalPercentage() + 20, calories.getCalPercentage());

            gramAnimator.setDuration(ANIMATION_DURATION);
            percentageAnimator.setDuration(ANIMATION_DURATION);

            gramAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    viewHolder.gramTextView.setText(String.format("%sg", animation.getAnimatedValue().toString()));
                }
            });

            percentageAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    viewHolder.percentageTextView.setText(String.format("%s%%", animation.getAnimatedValue().toString()));
                }
            });

            gramAnimator.start();
            percentageAnimator.start();
        }
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull CaloriesViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }


}