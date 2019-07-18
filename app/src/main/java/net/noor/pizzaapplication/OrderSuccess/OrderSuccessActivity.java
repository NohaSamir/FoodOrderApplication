package net.noor.pizzaapplication.OrderSuccess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import net.noor.pizzaapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderSuccessActivity extends AppCompatActivity {

    @Bind(R.id.checkImageView)
    ImageView checkMarkImageView;


    @Bind(R.id.circleImageView)
    ImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        ButterKnife.bind(this);


    }


    public static void start(Context context) {
        Intent intent = new Intent(context, OrderSuccessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //Animate new activity from bottom
        ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.hold);
    }

    int longestAnimationTime = 1000; //milliseconds, circle animation time defined in XML

    /**
     * Animate check mark using vector drawable animation
     * https://www.androiddesignpatterns.com/2016/11/introduction-to-icon-animation-techniques.html
     * https://stackoverflow.com/questions/44477341/animation-drawing-vector-circle
     */
    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();


        ((Animatable) circleImageView.getDrawable()).start();

        checkMarkImageView.postDelayed(new Runnable() {

            @Override
            public void run() {
                ((Animatable) checkMarkImageView.getDrawable()).start();
            }
        }, longestAnimationTime);
    }
}
