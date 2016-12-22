package com.example.asus.cancercare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SignUpActivity extends AppCompatActivity {

    protected EditText passwordEditText;
    protected EditText emailEditText;
    protected EditText phoneEditText;
    protected EditText addressEditText;
    protected EditText fnameEditText;
    protected EditText lnameEditText;
    protected EditText cityEditText;

    protected Button signUpButton;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        emailEditText = (EditText)findViewById(R.id.emailField);
        phoneEditText = (EditText)findViewById(R.id.editText3);
        addressEditText = (EditText)findViewById(R.id.editText4);
        signUpButton = (Button)findViewById(R.id.signupButton);
        fnameEditText = (EditText)findViewById(R.id.editText7);
        lnameEditText = (EditText)findViewById(R.id.editText8);
        cityEditText = (EditText)findViewById(R.id.editText9);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordEditText.getText().toString();
                email = emailEditText.getText().toString();

                password = password.trim();
                email = email.trim();

                if (password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                        mDatabase = FirebaseDatabase.getInstance().getReference();


                                        mUserId = mFirebaseUser.getUid();
                                        // Initialize Firebase Auth and Database Reference
                                        mDatabase.child("users").child(mUserId).child("email").setValue(email);
                                        mDatabase.child("users").child(mUserId).child("password").setValue(password);
                                        mDatabase.child("users").child(mUserId).child("fname").setValue(fnameEditText.getText().toString().trim());
                                        mDatabase.child("users").child(mUserId).child("lname").setValue(lnameEditText.getText().toString().trim());
                                        mDatabase.child("users").child(mUserId).child("address").setValue(addressEditText.getText().toString().trim());
                                        mDatabase.child("users").child(mUserId).child("phone").setValue(phoneEditText.getText().toString().trim());
                                        mDatabase.child("users").child(mUserId).child("city").setValue(cityEditText.getText().toString().trim());


                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                        builder.setMessage(task.getException().getMessage())
                                                .setTitle(R.string.login_error_title)
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
