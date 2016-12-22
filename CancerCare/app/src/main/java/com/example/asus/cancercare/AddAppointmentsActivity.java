package com.example.asus.cancercare;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddAppointmentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected EditText pnameEditText;
    protected EditText hospitalEditText;
    protected EditText drnameEditText;

    protected Button SaveButton;
    protected Button TakeButton;
    protected Button CancelButton;
    protected  Button PickAppointmentDateButton;
    private Spinner hosSpinner6;
    private Spinner docSpinner7;
    private Spinner chilSpinner5;


    protected Button AddAppointmentButton;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    public String doctorName;
    public String hospitalName;
    public String chilName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointments);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            docSpinner7 = (Spinner)findViewById(R.id.spinner7);
            hosSpinner6 = (Spinner)findViewById(R.id.spinner6);
            chilSpinner5 = (Spinner)findViewById(R.id.spinner5);

            hosSpinner6.setOnItemSelectedListener(this);
            SaveButton = (Button) findViewById(R.id.button9);
            CancelButton = (Button) findViewById(R.id.button10);
            TakeButton = (Button) findViewById(R.id.button14);

            CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddAppointmentsActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });

            TakeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doctorName = String.valueOf(docSpinner7.getSelectedItem());
                    hospitalName = String.valueOf(hosSpinner6.getSelectedItem());
                    chilName = String.valueOf(chilSpinner5.getSelectedItem());

                    Intent intent = new Intent(AddAppointmentsActivity.this, AddAlarmActivity.class);
                    intent.putExtra("v0","Appointment ");
                    intent.putExtra("v1",chilName);
                    intent.putExtra("v2",hospitalName);
                    intent.putExtra("v3",doctorName);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            });


            SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String pname, hospital, drname;
                    pname = pnameEditText.getText().toString().trim();
                    hospital = hospitalEditText.getText().toString().trim();
                    drname = drnameEditText.getText().toString().trim();
                    Appointments pat = new Appointments(pname, hospital, drname);
                    mFirebaseAuth = FirebaseAuth.getInstance();
                    mFirebaseUser = mFirebaseAuth.getCurrentUser();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mUserId = mFirebaseUser.getUid();
                    mDatabase.child("appointments").child(mUserId).child("pname").setValue(pnameEditText.getText().toString());
                    mDatabase.child("appointments").child(mUserId).child("hospital").setValue(hospitalEditText.getText().toString());
                    mDatabase.child("appointments").child(mUserId).child("drname").setValue(drnameEditText.getText().toString());
                    mDatabase.child("appointments").child(mUserId).child("appointments").push().setValue(pat);
                    Intent intent = new Intent(AddAppointmentsActivity.this, MainActivity.class);
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
                    ArrayAdapter<String> chilAdapter = new ArrayAdapter<String>(AddAppointmentsActivity.this, R.layout.support_simple_spinner_dropdown_item, chil);
                    chilAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    chilSpinner5.setAdapter(chilAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("hospitals").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<String> hos = new ArrayList<String>();
                    for (DataSnapshot hosSnapshot : dataSnapshot.getChildren()) {
                        String hName = hosSnapshot.child("name").getValue(String.class);
                        hos.add(hName);
                    }
                    ArrayAdapter<String> hosAdapter = new ArrayAdapter<String>(AddAppointmentsActivity.this, R.layout.support_simple_spinner_dropdown_item, hos);
                    hosAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    hosSpinner6.setAdapter(hosAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        String idx;
        if (hosSpinner6.getSelectedItemPosition()<9)
            idx = "0";
        else
            idx = "";
        idx = idx + String.valueOf(hosSpinner6.getSelectedItemPosition()+1);
        mDatabase.child("hospitals").child("hospital"+idx).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> doc = new ArrayList<String>();
                int id=1;

                for (DataSnapshot docSnapshot : dataSnapshot.child("doctors").getChildren()) {
                    String name = dataSnapshot.child("doctors").child("doctor"+id).child("name").getValue(String.class);
                    String lastname = dataSnapshot.child("doctors").child("doctor"+id).child("lastname").getValue(String.class);
                    doc.add(name+" "+lastname);
                    id++;
                }
                ArrayAdapter<String> docAdapter = new ArrayAdapter<String>(AddAppointmentsActivity.this, R.layout.support_simple_spinner_dropdown_item, doc);
                docAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                docSpinner7.setAdapter(docAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }








    private void loadLogInView () {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}





