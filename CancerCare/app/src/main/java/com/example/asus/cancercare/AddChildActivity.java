package com.example.asus.cancercare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddChildActivity extends AppCompatActivity {
    protected EditText fnameEditText;
    protected EditText lnameEditText;
    protected EditText birthdayEditText;
    protected EditText bloodTypeEditText;

    protected Button SaveButton;
    protected Button CancelButton;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            fnameEditText = (EditText)findViewById(R.id.editText6);
            lnameEditText = (EditText)findViewById(R.id.editText10);
            birthdayEditText = (EditText)findViewById(R.id.editText11);
            bloodTypeEditText = (EditText)findViewById(R.id.editText12);
            SaveButton = (Button)findViewById(R.id.button6);
            CancelButton = (Button)findViewById(R.id.button5);
            CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddChildActivity.this, ProfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });
            SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fname, lname, birthday, blood;
                    fname = fnameEditText.getText().toString().trim();
                    lname = lnameEditText.getText().toString().trim();
                    birthday = birthdayEditText.getText().toString().trim();
                    blood = bloodTypeEditText.getText().toString().trim();
                    Child pat = new Child(fname, lname, birthday, blood);
                    mDatabase.child("users").child(mUserId).child("children").push().setValue(pat);
                    Intent intent = new Intent(AddChildActivity.this, ProfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });
        }
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
