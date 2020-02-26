package vortex.solutions.employers.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vortex.solutions.employers.R;

public class SplashScreen extends AppCompatActivity {

    //constants
    private static final String TAG = "SplashScreen";
    private static final int TIME_OUT = 3000;

    //vars
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        ImageView logotype = findViewById(R.id.splashscreen_logotype);
        logotype.setAnimation(createAnimation());
    }

    private void goToLogin(FirebaseUser currentUser) {
        if (currentUser != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, Dashboard.class));
                }
            }, TIME_OUT);   
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreen.this, Login.class));
                }
            }, TIME_OUT);
        }
    }

    private AnimationSet createAnimation() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        Animation scale = new ScaleAnimation(
                0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setFillAfter(true);
        scale.setDuration(1300);

        AnimationSet manageAnim = new AnimationSet(false);
        manageAnim.addAnimation(scale);
        manageAnim.addAnimation(fadeIn);

        return manageAnim;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        goToLogin(currentUser);
    }
}
