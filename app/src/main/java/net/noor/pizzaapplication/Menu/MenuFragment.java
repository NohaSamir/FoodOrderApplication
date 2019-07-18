package net.noor.pizzaapplication.Menu;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import net.noor.pizzaapplication.DummyData.Food;
import net.noor.pizzaapplication.OrderItem.OrderItemFragment;
import net.noor.pizzaapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Created by nsamir on 6/25/2019.
 */

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * MenuFragment display 4 menu items
 * ice cream - pizza - Burger - drinks
 * I implement pizza view only
 * <p>
 * when user order item from the view, we direct to {@link OrderItemFragment}
 */
public class MenuFragment extends Fragment {

    @Bind(R.id.scrollView)
    ScrollView scrollView;


    @Bind(R.id.pizzaImageView)
    ImageView pizzaImageView;

    @Bind(R.id.pizzaBgImageView)
    ImageView pizzaBgImageView;

    @Bind(R.id.titleTV)
    TextView titleTV;

    @Bind(R.id.subtitleTV)
    TextView subtitleTV;

    @Bind(R.id.caloriesRecycler)
    RecyclerView caloriesRecycler;

    @Bind(R.id.ratingLayout)
    ConstraintLayout ratingLayout;

    @Bind(R.id.socialIconsLayout)
    ConstraintLayout socialIconsLayout;

    private Food mFood;
    private Context mContext;
    private CaloriesAdapter mCaloriesAdapter;
    private boolean mCaloriesRecyclerVisibility, mRatingBarVisibility, mSocialIconsVisibility;


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        mContext = getContext();

        mFood = Food.getDummyPizza();

        bindFoodDetails(mFood);
        scrollView.scrollTo(0, 0);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {

            int scrollY = scrollView.getScrollY(); // For ScrollView
            int scrollX = scrollView.getScrollX(); // For HorizontalScrollView
            // DO SOMETHING WITH THE SCROLL COORDINATES

            startAnimation(scrollY);

        });

        return view;
    }

    private void startAnimation(int scrollingValue) {
        startAnimatePizza(scrollingValue);
        startAnimateCaloriesList();
        startAnimateRatingBar();
        startAnimateSocialIcons();
    }

    /**
     * Use newInstance method to create instance of meny view
     *
     * @return MenuFragment
     */
    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    //********************************************************************

    /**
     * Check if the view visible during scrolling
     *
     * @param view view
     * @return visible/ not visible
     */
    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        //Check if the view totally visible
        if (//scrollBounds.top < top &&
                scrollBounds.bottom > bottom) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * On order button click , Display Order item View using {@link OrderItemFragment}
     */
    @OnClick(R.id.orderButton)
    public void onOrder() {

        if (getFragmentManager() != null) {
            FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();

            //add an animation, you can create your custom animation. Show below
            //mFragmentTransaction.setCustomAnimations(R.anim.order_view_slide_up_from_bottom, R.anim.order_view_slide_down_to_bottom);
            OrderItemFragment mFragment = OrderItemFragment.newInstance();
            mFragmentTransaction.add(R.id.orderFragment, mFragment);
            mFragmentTransaction.addToBackStack(OrderItemFragment.class.getSimpleName()).commit();
        }
    }


    /**
     * @param food food details
     */
    public void bindFoodDetails(Food food) {
        mCaloriesAdapter = new CaloriesAdapter(food.getCalories());
        caloriesRecycler.setAdapter(mCaloriesAdapter);
    }


    //********************************************************************

    /*Animation*/


    private int pizzaBgToDegree = 0;
    private int pizzaBgCurrentDegree = 0;
    private int previousScrollingValue = 0;
    private final int MAX_ROTATION_VALUE = 15;
    private final int TAB_LAYOUT_HEIGHT = 100;

    enum Direction {CLOCKWISE, ANTICLOCKWISE}

    Direction direction;

    /**
     * when scroll up Rotate pizza background anti clockwise and pizza slice in
     * when scroll down Rotate pizza background with clockwise and pizza slice out
     *
     * @param scrollingValue scrolling value
     */
    public void startAnimatePizza(int scrollingValue) {

        if (scrollingValue > previousScrollingValue)
            direction = Direction.ANTICLOCKWISE;
        else
            direction = Direction.CLOCKWISE;

        if (scrollingValue > TAB_LAYOUT_HEIGHT) {
            previousScrollingValue = scrollingValue;

            if (isViewVisible(pizzaBgImageView)) {

                if (direction == Direction.CLOCKWISE) {
                    pizzaBgToDegree = pizzaBgCurrentDegree + 1;
                    pizzaImageView.setImageResource(R.drawable.pizza_slice_out);
                } else {
                    pizzaBgToDegree = pizzaBgCurrentDegree - 1;
                    pizzaImageView.setImageResource(R.drawable.pizza_slice_in);
                }

                if (pizzaBgToDegree < MAX_ROTATION_VALUE && pizzaBgToDegree >= -MAX_ROTATION_VALUE) {
                    RotateAnimation rotate = new RotateAnimation(pizzaBgCurrentDegree, pizzaBgToDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(10);
                    rotate.setFillAfter(true);
                    rotate.setInterpolator(new LinearInterpolator());
                    pizzaBgImageView.startAnimation(rotate);

                    pizzaBgCurrentDegree = pizzaBgToDegree;
                }
            }
        }
    }

    /**
     * Animate Calories List numbers
     */
    public void startAnimateCaloriesList() {
        if (isViewVisible(caloriesRecycler) && !mCaloriesRecyclerVisibility) {
            mCaloriesRecyclerVisibility = true;
            mCaloriesAdapter.notifyDataSetChanged();
        } else if (!isViewVisible(caloriesRecycler) && mCaloriesRecyclerVisibility) {
            mCaloriesRecyclerVisibility = false;
        }

    }


    /**
     * Animate Rating bar stars by zoom_in in a sequential order
     */
    public void startAnimateRatingBar() {

        if (isViewVisible(ratingLayout) && !mRatingBarVisibility) {
            mRatingBarVisibility = true;

            // 250ms delay between Animations

            int delayBetweenAnimations = 250;

            for (int i = 0; i < ratingLayout.getChildCount(); i++) {

                final View view = ratingLayout.getChildAt(i);

                final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rating_bar_zoom_in);

                // We calculate the delay for this Animation, each animation starts 100ms
                // after the previous one
                int delay = i * delayBetweenAnimations;

                view.postDelayed(() ->
                {
                    view.startAnimation(animation);

                }, delay);
            }


        } else if (!isViewVisible(ratingLayout) && mRatingBarVisibility) {
            mRatingBarVisibility = false;
        }
    }


    /**
     * Start animate social icons
     * fading in on appear
     * fading out on disappear
     */
    public void startAnimateSocialIcons() {
        if (isViewVisible(socialIconsLayout) && !mSocialIconsVisibility) {
            mSocialIconsVisibility = true;
            socialIconsLayout.setVisibility(View.VISIBLE);

            // 250ms delay between Animations

            int delayBetweenAnimations = 250;

            for (int i = 0; i < socialIconsLayout.getChildCount(); i++) {
                final View view = socialIconsLayout.getChildAt(i);

                final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.social_icons_fade_in);

                // We calculate the delay for this Animation, each animation starts 250ms
                // after the previous one

                int delay = i * delayBetweenAnimations;

                view.postDelayed(() -> view.startAnimation(animation), delay);
            }


        } else if (!isViewVisible(socialIconsLayout) && mSocialIconsVisibility) {
            mSocialIconsVisibility = false;
            hideSocialIcons();

        }

    }


    public void hideSocialIcons() {
        for (int i = 0; i < socialIconsLayout.getChildCount(); i++) {
            final View view = socialIconsLayout.getChildAt(i);
            final Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.social_icons_fade_out);
            view.startAnimation(animation);
        }
    }
    //********************************************************************


}
