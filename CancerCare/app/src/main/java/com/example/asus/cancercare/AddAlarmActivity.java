package com.example.asus.cancercare;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.os.Bundle;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAlarmActivity extends AppCompatActivity {

    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;

    String v0,v1,v2,v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        pickerDate = (DatePicker)findViewById(R.id.pickerdate);
        pickerTime = (TimePicker)findViewById(R.id.pickertime);

        Intent intent = getIntent();
        v0 = intent.getStringExtra("v0");
        v1 = intent.getStringExtra("v1");
        v2 = intent.getStringExtra("v2");
        v3 = intent.getStringExtra("v3");

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setMinute(now.get(Calendar.MINUTE));

        buttonSetAlarm = (Button)findViewById(R.id.setalarm);
        buttonSetAlarm.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = new GregorianCalendar();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getHour(),
                        pickerTime.getMinute(),
                        00);

                if(cal.compareTo(current) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                }else{
                    setAlarm(cal);
                }

            }});
    }

    private void setAlarm(Calendar targetCal){

        Intent schedule = new Intent(Intent.ACTION_INSERT);
        schedule.setType("vnd.android.cursor.item/event");
        schedule.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,   targetCal.getTimeInMillis());
        schedule.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, targetCal.getTimeInMillis() + 1000*60*30);
        schedule.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

        schedule.putExtra(CalendarContract.Events.TITLE, v0 + " " + v1);
        schedule.putExtra(CalendarContract.Events.EVENT_LOCATION, v2);
        schedule.putExtra(CalendarContract.Events.DESCRIPTION, v3);

        Appointments app = new Appointments(v1, v2, v3);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("appointments").push().setValue(app);
        startActivity(schedule);

    }
}