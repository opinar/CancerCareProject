package com.example.asus.cancercare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HealthCheckerActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    protected Button profileButton;
    protected Button SaveButton;
    protected Button CheckButton;
    protected Button setDateButton;
    protected Button AddAppointmentsButton;
    private EditText birthdayEditText;
    private EditText bloodAnalysisEditText;
    private EditText feverEditText;
    private EditText DateEditText;
    private EditText moodEditText;
    private EditText urineEditText;
    private EditText dateEditText;
    private String childName;

    protected Spinner chilSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checker);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            birthdayEditText = (EditText) findViewById(R.id.editText17);
            bloodAnalysisEditText= (EditText) findViewById(R.id.editText18);
            feverEditText = (EditText) findViewById(R.id.editText21);
            dateEditText = (EditText) findViewById(R.id.editText16);
            urineEditText = (EditText) findViewById(R.id.editText22);
            SaveButton = (Button)findViewById(R.id.button7);
            CheckButton = (Button) findViewById(R.id.button16);

            chilSpinner = (Spinner) findViewById(R.id.spinner2);
            mDatabase.child("users").child(mUserId).child("children").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<String> child = new ArrayList<String>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String childFname = childSnapshot.child("fname").getValue(String.class);
                        String childLname = childSnapshot.child("l_name").getValue(String.class);
                        child.add(childFname + " " + childLname);
                    }

                    ArrayAdapter<String> chilAdapter = new ArrayAdapter<String>(HealthCheckerActivity.this, R.layout.support_simple_spinner_dropdown_item, child);
                    chilAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    chilSpinner.setAdapter(chilAdapter);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name, fev, ban, ur , Date;
                    name = chilSpinner.getSelectedItem().toString().trim();
                    fev = feverEditText.getText().toString().trim();
                    ban = bloodAnalysisEditText.getText().toString().trim();
                    ur = urineEditText.getText().toString().trim();
                    Date = dateEditText.getText().toString().trim();
                    HealthChecker h = new HealthChecker(name, fev, ban, ur ,Date);
                    mDatabase.child("healthchecks").push().setValue(h);
                    Intent intent = new Intent(HealthCheckerActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });

            CheckButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HealthCheckerActivity.this, CheckDateActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("child", childName);
                    startActivity(intent);
                }

            });




            chilSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {

                    mDatabase.child("users").child(mUserId).child("children").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(parent.getItemAtPosition(position).toString() == childSnapshot.child("fname").getValue(String.class) + " " + childSnapshot.child("l_name").getValue(String.class)){
                                    //birthdayEditText.setText(childSnapshot.child("birthday").getValue(String.class));
                                    bloodAnalysisEditText.setText(childSnapshot.child("bloodanalysis").getValue(String.class));
                                    feverEditText.setText(childSnapshot.child("fever").getValue(String.class));
                                    urineEditText.setText(childSnapshot.child("urine").getValue(String.class));
                                    dateEditText.setText(childSnapshot.child("date").getValue(String.class));
                                }
                            }
                            childName = parent.getItemAtPosition(position).toString().split(" ")[0];
                            int asdqwe;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(final AdapterView<?> parent) {
                    /*mDatabase.child("users").child(mUserId).child("children").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(parent.getSelectedItem().toString() == childSnapshot.child("fname").getValue(String.class)){
                                    birthdayEditText.setText(childSnapshot.child("birthday").getValue(String.class));
                                    bloodAnalysisEditText.setText(childSnapshot.child("bloodanalysis").getValue(String.class));
                                    feverEditText.setText(childSnapshot.child("fever").getValue(String.class));
                                    urineEditText.setText(childSnapshot.child("urine").getValue(String.class));

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/
                }
            });
        }




    }

    public void onDataChange(DataSnapshot dataSnapshot){
        final List<String> chil = new ArrayList<String>();
        for (DataSnapshot chilSnapshot : dataSnapshot.getChildren()) {
            String chilFname = chilSnapshot.child("fname").getValue(String.class);
            chil.add(chilFname);
        }

        ArrayAdapter<String> chilAdapter = new ArrayAdapter<String>(HealthCheckerActivity.this, R.layout.support_simple_spinner_dropdown_item, chil);
        chilAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        chilSpinner.setAdapter(chilAdapter);
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

