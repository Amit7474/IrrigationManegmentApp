package com.example.irrigationmanegmentapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.irrigationmanegmentapp.Finals.MOBILE_NUMBER;
import static com.example.irrigationmanegmentapp.Finals.ONCOMPLETE_ERROR;
import static com.example.irrigationmanegmentapp.Finals.SIGNUP_COMPLETE;
import static com.example.irrigationmanegmentapp.Finals.SIGNUP_ERROR;
import static com.example.irrigationmanegmentapp.Finals.USER_NOT_EXIST;

public class UserDBManager {

    private DatabaseReference mUsersDB;
    private Context mContext;


    public UserDBManager(Context context) {
        mUsersDB = FirebaseDatabase.getInstance().getReference().child("Users");
        mContext = context;
    }

    /**
     * This function get called inside "Sign-up activity" before creating new user in Firebase.
     * if the user is not in Firebase --> it creates is. else it sign-in to the user in Firebase.
     *
     * @param mobileNumber
     * @param firstName
     */
    public void signInToProfile(final String mobileNumber, final String firstName) {
        Query query = mUsersDB.orderByChild("mobileNumber").equalTo(mobileNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    moveToAnotherActivity(mobileNumber, ProfileActivity.class);
                } else {
                    signUpAndCreateNewUser(mobileNumber, firstName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Use query to filter all the users and find the user with the same mobile number.
     *
     * @param mobileNumber
     */
    public void checkIfNumberExist(final String mobileNumber) {
        Query query = mUsersDB.orderByChild("mobileNumber").equalTo(mobileNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    moveToAnotherActivity(mobileNumber, VerifyCodeActivity.class);
                } else {
                    Toast.makeText(mContext, USER_NOT_EXIST, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /** Creates new user in FireBase and open the user's profile activity
     * @param mobileNumber
     * @param firstName
     */
    private void signUpAndCreateNewUser(final String mobileNumber, final String firstName) {
        mUsersDB.child(mobileNumber).setValue(new User(mobileNumber, firstName)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, firstName + SIGNUP_COMPLETE, Toast.LENGTH_SHORT).show();
                    moveToAnotherActivity(mobileNumber, ProfileActivity.class);
                } else {
                    Toast.makeText(mContext, firstName + SIGNUP_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Update user's object in Firebase
     * @param user
     */
    public void updateUser(User user) {
        mUsersDB.child(user.getMobileNumber()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(mContext, ONCOMPLETE_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @param mobileNumber
     * @param activity
     */
    private void moveToAnotherActivity(String mobileNumber, Class activity) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(MOBILE_NUMBER, mobileNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}


