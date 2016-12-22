package com.example.asus.cancercare;
import java.util.*;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    protected EditText passwordEditText;
    protected EditText fnameEditText;
    protected EditText lnameEditText;
    protected EditText phoneEditText;
    protected EditText addressEditText;
    protected EditText cityEditText;
    protected Spinner chilSpinner;
    private Button editChildButton;
    protected TextView usernameText;
    protected Button SaveButton;
    protected Button CancelButton;
    protected Button AddChildButton;
    protected Button EditChildButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            passwordEditText = (EditText) findViewById(R.id.editText14);
            fnameEditText = (EditText) findViewById(R.id.editText19);
            lnameEditText = (EditText) findViewById(R.id.editText20);
            usernameText = (TextView) findViewById(R.id.textView7);
            phoneEditText = (EditText) findViewById(R.id.editText);
            addressEditText = (EditText) findViewById(R.id.editText2);
            cityEditText = (EditText) findViewById(R.id.editText5);
            chilSpinner = (Spinner) findViewById(R.id.spinner);
            SaveButton = (Button) findViewById(R.id.saveButton);
            CancelButton = (Button) findViewById(R.id.cancelButton);
            AddChildButton = (Button) findViewById(R.id.button4);


            AddChildButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfilActivity.this, AddChildActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });


            CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });

            SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDatabase.child("users").child(mUserId).child("fname").setValue(fnameEditText.getText().toString().trim());
                    mDatabase.child("users").child(mUserId).child("lname").setValue(lnameEditText.getText().toString().trim());
                    mDatabase.child("users").child(mUserId).child("password").setValue(passwordEditText.getText().toString().trim());
                    mDatabase.child("users").child(mUserId).child("phone").setValue(phoneEditText.getText().toString().trim());
                    mDatabase.child("users").child(mUserId).child("address").setValue(addressEditText.getText().toString().trim());
                    mDatabase.child("users").child(mUserId).child("city").setValue(cityEditText.getText().toString().trim());
                    mFirebaseUser.updatePassword(passwordEditText.getText().toString().trim());

                    Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });

            mDatabase.child("users").child(mUserId).child("children").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<String> chil = new ArrayList<String>();
                    for (DataSnapshot chilSnapshot : dataSnapshot.getChildren()) {
                        String chilFname = chilSnapshot.child("fname").getValue(String.class);
                        chil.add(chilFname);
                    }
                    ArrayAdapter<String> chilAdapter = new ArrayAdapter<String>(ProfilActivity.this, R.layout.support_simple_spinner_dropdown_item, chil);
                    chilAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    chilSpinner.setAdapter(chilAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("users").child(mUserId).child("email").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    usernameText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("users").child(mUserId).child("password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    passwordEditText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("users").child(mUserId).child("fname").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    fnameEditText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("users").child(mUserId).child("lname").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lnameEditText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("users").child(mUserId).child("phone").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    phoneEditText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("users").child(mUserId).child("address").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    addressEditText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mDatabase.child("users").child(mUserId).child("city").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cityEditText.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            loadLogInView();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Profil Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
