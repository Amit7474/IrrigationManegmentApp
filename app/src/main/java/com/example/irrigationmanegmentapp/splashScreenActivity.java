package com.example.irrigationmanegmentapp;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.deepan.pieprogress.PieProgress;

import static com.example.irrigationmanegmentapp.Finals.VALID_NUMBER_ERROR;

public class splashScreenActivity extends AppCompatActivity {

    private PieProgress pieProgress;
    private ImageView splash_IMG_sprinkler;
    private EditText SignIn_TXT_phoneNumber;
    private Button Splash_BTN_signup, Splash_BTN_go;
    private LinearLayout Splash_LAY_linear;
    private UserDBManager mUserDBManager;
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();
        Splash_BTN_signup.setOnClickListener(signupListener);
        Splash_BTN_go.setOnClickListener(signInListener);

        //CountDown timer handles the progress pie on splash screen
        CountDownTimer countDownTimer = new CountDownTimer(2500, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter += 1;
                pieProgress.setProgress(counter);
            }

            @Override
            public void onFinish() {
                //After 2.5 seconds the logo is animated
                animateLogo();
            }
        };
        countDownTimer.start();

    }

    /**
     * Find views and creates user database manager that manage the communication with FireBase
     */
    private void init() {
        findViews();
        mUserDBManager = new UserDBManager(getApplicationContext());
    }

    /**
     * Logo animation
     */
    private void animateLogo() {
        splash_IMG_sprinkler.animate().setStartDelay(250).scaleX(.5f).scaleY(.5f).translationYBy(-450).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                pieProgress.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animateWelcomeText();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    /**
     * TextView and buttons animation
     */
    private void animateWelcomeText() {
        Splash_LAY_linear.animate().setStartDelay(100).setDuration(1000).alpha(1).translationYBy(-20).start();
    }

    /**
     * Sign-up button listener
     */
    View.OnClickListener signupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveToSignUpActivity();
        }
    };
    /**
     * Sign-in button listener.
     * Checks if the mobile number is legal
     */
    View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mobileNumber = SignIn_TXT_phoneNumber.getText().toString().trim();
            if (mobileNumber.isEmpty() || mobileNumber.length() < 13) {
                SignIn_TXT_phoneNumber.setError(VALID_NUMBER_ERROR);
                SignIn_TXT_phoneNumber.requestFocus();
                return;
            }
            //After checking that the number is legal, the manager checks in Firebase if the number is already signed-up
            mUserDBManager.checkIfNumberExist(mobileNumber);
        }
    };

    /**
     * Move to the next activity (Sign-up)
     */
    private void moveToSignUpActivity() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Find views
     */
    private void findViews() {
        splash_IMG_sprinkler = findViewById(R.id.Splash_IMG_logo);
        Splash_BTN_signup = findViewById(R.id.Splash_BTN_signup);
        Splash_LAY_linear = findViewById(R.id.Splash_LAY_linear);
        pieProgress = findViewById(R.id.Splash_BAR_progress);
        Splash_BTN_go = findViewById(R.id.Splash_BTN_go);
        SignIn_TXT_phoneNumber = findViewById(R.id.SignIn_TXT_phoneNumber);
    }
}
