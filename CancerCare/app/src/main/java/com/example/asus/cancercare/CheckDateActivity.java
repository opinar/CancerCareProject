package com.example.asus.cancercare;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;




public class CheckDateActivity extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private String fname;
    private Spinner Spinner4;
    private List<HealthChecker> checkerList;
    private EditText urine,blood,fever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_date);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        fname = intent.getStringExtra("child");

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            init();

        }
    }

    private void init() {
        checkerList = new ArrayList<HealthChecker>();
        mUserId = mFirebaseUser.getUid();
        blood = (EditText) findViewById(R.id.activity_check_bloodanalysis_id);
        fever = (EditText) findViewById(R.id.activity_check_fever_id);
        urine = (EditText) findViewById(R.id.activity_check_urine_id);

        Spinner4 = (Spinner) findViewById(R.id.spinner4);

        mDatabase.child("healthchecks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                final List<String> child = new ArrayList<String>();
//                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                    String childFname = childSnapshot.child("fname").getValue(String.class);
//                    String childLname = childSnapshot.child("l_name").getValue(String.class);
//                    child.add(childFname + " " + childLname);
//                }
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String childFname = childSnapshot.child("name").getValue(String.class).split(" ")[0];
                    if (childFname.equals(fname)) {
                        String banalysis = childSnapshot.child("banalysis").getValue(String.class);
                        ;
                        String date = childSnapshot.child("date").getValue(String.class);
                        String fever = childSnapshot.child("fever").getValue(String.class);
                        String urine = childSnapshot.child("urine").getValue(String.class);
                        ;
                        HealthChecker checker = new HealthChecker(childFname, fever, banalysis, urine, date);
                        checkerList.add(checker);
                    }
                }

                String[] dates = new String[checkerList.size()];
                for (int i = 0; i < dates.length; i++) {
                    dates[i] = checkerList.get(i).getDate();
                }

                ArrayAdapter<String> chilAdapter = new ArrayAdapter<String>(CheckDateActivity.this, R.layout.support_simple_spinner_dropdown_item, dates);
                chilAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                Spinner4.setAdapter(chilAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                HealthChecker cur = checkerList.get(position);
                blood.setText(cur.getBanalysis());
                fever.setText(cur.getFever());
                urine.setText(cur.getUrine());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadLogInView () {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}

