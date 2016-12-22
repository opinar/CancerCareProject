package com.example.asus.cancercare;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    protected Button profileButton;
    protected Button AddAppointmentsButton;
    protected Button HealthCheckerButton;
    protected Button MedicationButton;
    protected Button AboutButton;
    private Spinner hosSpinner6;
    private Spinner docSpinner7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();
            profileButton = (Button)findViewById(R.id.button3);
            // hosSpinner6 = (Spinner)findViewById(R.id.spinner6);
            // hosSpinner6.setOnItemSelectedListener(this);
            //docSpinner7 = (Spinner)findViewById(R.id.spinner7);
            HealthCheckerButton = (Button)findViewById(R.id.button12);
            AddAppointmentsButton = (Button)findViewById(R.id.button8);
            MedicationButton = (Button) findViewById(R.id.button17);
            AboutButton = (Button) findViewById(R.id.button11);


            //this.addHospitals();
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            });

            AddAppointmentsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddAppointmentsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            });

            HealthCheckerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, HealthCheckerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            });

           AboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            });

            MedicationButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MedicationActivity.class);
                    startActivity(intent);
                }
            });

          /* mDatabase.child("hospitals").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<String> hos = new ArrayList<String>();
                    for (DataSnapshot hosSnapshot : dataSnapshot.getChildren()) {
                        String hName = hosSnapshot.child("name").getValue(String.class);
                        hos.add(hName);
                    }
                    ArrayAdapter<String> hosAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, hos);
                    hosAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    hosSpinner6.setAdapter(hosAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
           });
        */


        }
    }

    /*  public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                 long arg3) {
          final String idx= String.valueOf(hosSpinner6.getSelectedItemPosition()+1);
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
                  ArrayAdapter<String> docAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, doc);
                  docAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                  docSpinner7.setAdapter(docAdapter);
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });

      }

  */
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void addHospitals() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("hospitals").setValue(null);
        Doctor d1, d2, d3, d4;
        d1=new Doctor();
        d1.setName("d1");
        d1.setLastName("d11");
        d1.setBranch("branch1");
        d2=new Doctor();
        d2.setName("d2");
        d2.setLastName("d22");
        d2.setBranch("branch1");
        d3=new Doctor();
        d3.setName("d3");
        d3.setLastName("d33");
        d3.setBranch("branch2");
        d4=new Doctor();
        d4.setName("d4");
        d4.setLastName("d44");
        d4.setBranch("branch2");
        Hospital h;
        h = new Hospital();
        h.setName("medipol");
        h.setEmail("medipol@gmail.com");
        h.setAddress("maltepe");
        h.setCity("istanbul");
        h.setPhoneNumber("1234567");
        mDatabase.child("hospitals").push().setValue(h);
        h = new Hospital();
        h.setName("amerikan");
        h.setEmail("amerikan@gmail.com");
        h.setAddress("levent");
        h.setCity("istanbul");
        h.setPhoneNumber("7654321");
        mDatabase.child("hospitals").push().setValue(h);

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

}