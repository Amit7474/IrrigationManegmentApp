package com.example.irrigationmanegmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.irrigationmanegmentapp.Finals.FIRST_NAME;
import static com.example.irrigationmanegmentapp.Finals.FIRST_NAME_ERROR;
import static com.example.irrigationmanegmentapp.Finals.MOBILE_NUMBER;
import static com.example.irrigationmanegmentapp.Finals.VALID_NUMBER_ERROR;

public class SignUpActivity extends AppCompatActivity {

    private Button SignUp_BTN_signup;
    private TextView SignUp_TXT_firstName, SignUp_TXT_phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        SignUp_BTN_signup.setOnClickListener(signUpListener);
    }

    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Gets the name and the mobile number from the user
            String mobileNumber = SignUp_TXT_phoneNumber.getText().toString().trim();
            String firstName = SignUp_TXT_firstName.getText().toString().trim();

            //Checks the name
            if (firstName.isEmpty()) {
                SignUp_TXT_firstName.setError(FIRST_NAME_ERROR);
                SignUp_TXT_firstName.requestFocus();
                return;
            }
            //Checks if the number is legal
            if (mobileNumber.isEmpty() || mobileNumber.length() < 13) {
                SignUp_TXT_phoneNumber.setError(VALID_NUMBER_ERROR);
                SignUp_TXT_phoneNumber.requestFocus();
                return;
            }
            moveToVerifyCodeActivity(mobileNumber, firstName);
        }
    };

    /**
     * fins all the views
     */
    private void findViews() {
        SignUp_BTN_signup = findViewById(R.id.SignUp_BTN_signup);
        SignUp_TXT_firstName = findViewById(R.id.SignUp_TXT_firstName);
        SignUp_TXT_phoneNumber = findViewById(R.id.SignUp_TXT_phoneNumber);
    }

    /**
     * Pass the user input to the next activity (Verify Code)
     */
    private void moveToVerifyCodeActivity(String mobileNumber, String firstName) {
        Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
        intent.putExtra(MOBILE_NUMBER, mobileNumber);
        intent.putExtra(FIRST_NAME, firstName);
        startActivity(intent);
        finish();
    }

}
