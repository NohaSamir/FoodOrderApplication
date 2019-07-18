package net.noor.pizzaapplication.OrderItem;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.noor.pizzaapplication.OrdersList.OrdersListActivity;
import net.noor.pizzaapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderItemFragment extends Fragment {

    @Bind(R.id.rootLayout)
    ConstraintLayout rootLayout;

    @Bind(R.id.pizzaImageView)
    ImageView pizzaImageView;

    @Bind(R.id.boxImageView)
    ImageView boxImageView;

    @Bind(R.id.orderLayout)
    ConstraintLayout orderLayout;

    @Bind(R.id.backgroundView)
    View backgroundView;

    @Bind(R.id.orderButton)
    View orderButton;

    @Bind(R.id.backView)
    View backView;


    @Bind(R.id.checkImageView)
    ImageView checkImageView;

    @Bind(R.id.boxImageView2)
    ImageView boxImageView2;

    private Context mContext;


    public OrderItemFragment() {
        // Required empty public constructor
    }


    public static OrderItemFragment newInstance() {
        return new OrderItemFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getContext();

        View view = inflater.inflate(R.layout.fragment_order_item, container, false);

        ButterKnife.bind(this, view);


        startAnimateBackground();

        onStartAnimatePizzaBox();

        startAnimateBottomLayoutUp();

        return view;
    }


    public void startAnimateBackground() {

        /*Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.background_slide_down);
        backgroundView.startAnimation(anim);
        OR */
        backgroundView.post(new Runnable() {
            @Override
            public void run() {
                backgroundView.animate().translationYBy(backgroundView.getHeight()).setDuration(1500).start();
            }
        });
    }

    //*********************************************************************

    /**
     * Animate Pizza
     */

    public void onStartAnimatePizzaBox() {

        Animation anim = animatePizzaBoxFromTopToCenter();
        boxImageView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animatePizzaBoxing();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private Animation animatePizzaBoxFromTopToCenter() {
        boxImageView.setBackgroundResource(R.drawable.pizza_box_1);
        return AnimationUtils.loadAnimation(mContext, R.anim.pizza_box_translate_from_top_close);
    }

    private void animatePizzaBoxing() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.social_icons_fade_out);
        pizzaImageView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boxImageView.setBackgroundResource(R.drawable.pizza_box_4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //Clear box drawable image before start animation
        boxImageView.setBackgroundResource(0);
        //Set pizza box image with animation drawable list
        boxImageView.setBackgroundResource(R.drawable.close_box_animation);
        //Get animation drawable reference
        AnimationDrawable animationDrawable = (AnimationDrawable) boxImageView.getBackground();
        //Start animation
        animationDrawable.start();
    }

    //*********************************************************************

    public void startAnimateBottomLayoutUp() {
        orderLayout.post(new Runnable() {
            @Override
            public void run() {
                orderLayout.animate().translationYBy(-1 * orderLayout.getHeight()).setDuration(800).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ((Animatable) checkImageView.getDrawable()).start();

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
            }
        });
    }

    public void startAnimateBottomLayoutDown() {
        orderLayout.animate().translationYBy(orderLayout.getHeight()).setDuration(150).start();
    }

    //*********************************************************************

    /**
     * On order button click
     * 1- change background color opacity to 100% and hide all views
     * 2- translate order bottom layout out of screen
     * 3- rotate pizza box
     * 4- display shopping list view
     */

    @OnClick(R.id.orderButton)
    void order() {

        changeBackGroundColor();

        startAnimateBottomLayoutDown();

        //Hide pizza box image and use another image view
        boxImageView.clearAnimation();
        boxImageView.setVisibility(View.GONE);

        //Hide back to catalog view
        backView.setVisibility(View.GONE);

        boxImageView2.setBackgroundResource(R.drawable.box_rotate_1);

        boxImageView2.postDelayed(new Runnable() {
            @Override
            public void run() {
                OrdersListActivity.start(mContext, boxImageView2);
            }
        }, 150);

    }

    private void changeBackGroundColor() {
        int colorFrom = getResources().getColor(R.color.background_gray_transparent);
        int colorTo = getResources().getColor(R.color.background_gray);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(150); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                backgroundView.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
