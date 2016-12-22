package com.example.asus.cancercare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMedicationActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String childName;
    private Button addButton;
    private EditText timeInterval,name,quantity,medicationHours,medicationMinutes,medicationSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        Intent intent = getIntent();

        childName = intent.getStringExtra("fname");
        addButton = (Button)findViewById(R.id.activity_add_medication_button);

        name = (EditText) findViewById(R.id.add_medication_name_interval_field);
        quantity = (EditText) findViewById(R.id.add_medication_quantity_interval_field);
        medicationHours = (EditText) findViewById(R.id.add_medication_hour);
        medicationMinutes = (EditText) findViewById(R.id.add_medication_minute);
        medicationSeconds = (EditText) findViewById(R.id.add_medication_seconds);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long medquantity = Long.valueOf(quantity.getText().toString());
                String medName = name.getText().toString();

                long h,min,s;
                if (medicationHours.getText().toString().equals("")) {
                    h = 0;
                }else{
                    h = Long.valueOf(medicationHours.getText().toString());
                }

                if (medicationMinutes.getText().toString().equals("")) {
                    min = 0;
                } else{
                    min = Long.valueOf(medicationMinutes.getText().toString());
                }
                if (medicationSeconds.getText().toString().equals("")) {
                    s = 0;
                } else{
                    s = Long.valueOf(medicationSeconds.getText().toString());
                }
                long time = h*60*60+min*60+s;

                MedicationData m = new MedicationData(medName, time, medquantity);
                m.setFname(childName.split(" ")[0]);

                mDatabase.child("medications").push().setValue(m);

                Intent intent = new Intent(AddMedicationActivity.this, ChildrenMedicationsActivity.class);
                intent.putExtra("childid", childName);
                startActivity(intent);
            }
        });
    }
}
