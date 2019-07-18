package net.noor.pizzaapplication.OrdersList;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import net.noor.pizzaapplication.DummyData.Food;
import net.noor.pizzaapplication.OrderSuccess.OrderSuccessActivity;
import net.noor.pizzaapplication.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrdersListActivity extends AppCompatActivity {

    @Bind(R.id.foodRecycler)
    RecyclerView foodRecycler;

    @Bind(R.id.boxImageView)
    ImageView boxImageView;

    @Bind(R.id.bottomLayout)
    View bottomView;

    private OrdersListAdapter mAdapter;
    private List<Food> mOrderList;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ButterKnife.bind(this);

        mContext = this;

        mOrderList = Food.getDummyFoodList();
        mAdapter = new OrdersListAdapter(mOrderList);
        foodRecycler.setAdapter(mAdapter);

    }


    public static void start(Context context, ImageView imageView) {
        Intent intent = new Intent(context, OrdersListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context,
                imageView,
                ViewCompat.getTransitionName(imageView));


        context.startActivity(intent, options.toBundle());

        //Animate new activity from bottom
        //((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
    }

    @OnClick(R.id.orderButton)
    void order() {
        OrderSuccessActivity.start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    private void startAnimation() {
        rotatePizzaBox();
        translatePizzaBoxToListItem();
        animateBottomLayoutUp();

    }
    //**********************************************************************

    private void rotatePizzaBox() {

        boxImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boxImageView.setBackgroundResource(0);
                //Set pizza box image with animation drawable list
                boxImageView.setBackgroundResource(R.drawable.rotate_box_animation);
                //Get animation drawable reference
                AnimationDrawable animationDrawable = (AnimationDrawable) boxImageView.getBackground();
                //Start animation
                animationDrawable.start();
            }
        }, 200);
    }

    private void translatePizzaBoxToListItem() {

        boxImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] itemLocation = getFirstItemLocation();
                int[] boxLocation = getBoxLocation();

                AnimatorSet animSetXY = new AnimatorSet();

                ObjectAnimator x = ObjectAnimator.ofFloat(boxImageView,
                        "translationX", itemLocation[0] - boxLocation[0] + 100);

                ObjectAnimator y = ObjectAnimator.ofFloat(boxImageView,
                        "translationY", itemLocation[1] - boxLocation[1]);

                animSetXY.playTogether(x, y);
                animSetXY.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        //Change box Image size to item size in list and change image
                        editBozSize();
                        boxImageView.setBackgroundResource(R.drawable.pizza_box_x);

                        //Hide box Image
                        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.social_icons_fade_out);
                        boxImageView.startAnimation(animation);

                        translateOrderList();

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animSetXY.setInterpolator(new LinearInterpolator());
                animSetXY.setDuration(500);
                animSetXY.start();
            }
        }, 2000);

    }

    private void editBozSize() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) boxImageView.getLayoutParams();
        int[] size = getFirstItemSize();
        params.width = size[0];
        params.height = size[1];
        boxImageView.setLayoutParams(params);
    }

    private int[] getFirstItemLocation() {
        OrdersListAdapter.ShoppingViewHolder shoppingViewHolder = (OrdersListAdapter.ShoppingViewHolder) foodRecycler.findViewHolderForAdapterPosition(0);
        int[] location = new int[2];

        if (mOrderList.get(0).getFoodType().equals(Food.FoodType.PIZZA))
            shoppingViewHolder.verticalRecycler.getLocationOnScreen(location);
        else
            shoppingViewHolder.horizontalRecycler.getLocationOnScreen(location);

        return location;
    }

    private int[] getFirstItemSize() {
        OrdersListAdapter.ShoppingViewHolder shoppingViewHolder = (OrdersListAdapter.ShoppingViewHolder) foodRecycler.findViewHolderForAdapterPosition(0);
        int[] size = new int[2];
        size[0] = shoppingViewHolder.verticalRecycler.getWidth();
        size[1] = shoppingViewHolder.verticalRecycler.getHeight() / mAdapter.getItemCount();

        return size;
    }

    private int[] getBoxLocation() {
        int[] location = new int[2];

        boxImageView.getLocationOnScreen(location);

        return location;
    }

    //**********************************************************************

    public void animateBottomLayoutUp() {
        bottomView.post(new Runnable() {
            @Override
            public void run() {
                bottomView.animate().translationYBy(-1 * bottomView.getHeight()).setDuration(800).start();
            }
        });
    }

    private void translateOrderList() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.order_list_slide_up_fade_in);
        foodRecycler.startAnimation(animation);

    }
    //********************************************

}
