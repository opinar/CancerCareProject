package com.example.asus.cancercare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class ChildrenMedicationsActivity extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    private Button addButton;

    private List<MedicationData> list;
    private String curchild;

    private ListView ChildrenMedicationsListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_medications);

        init();

        Intent i = getIntent();
        curchild = i.getStringExtra("childid");
        final String fname = curchild.split(" ")[0];
        String lname = curchild.split(" ")[1];
        list = new ArrayList<MedicationData>();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        mDatabase.child("medications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String fnameData = childSnapshot.child("fname").getValue(String.class);
                    if (fnameData.equals(fname)){
                        String medname = childSnapshot.child("m_name").getValue(String.class);
                        long medtime = childSnapshot.child("m_time").getValue(Long.class);
                        Long medquan = childSnapshot.child("quantity").getValue(Long.class);
                        MedicationData dat = new MedicationData(medname, medtime, medquan);
                        list.add(dat);
                    }
                    refreshList();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildrenMedicationsActivity.this, AddMedicationActivity.class);
                intent.putExtra("fname", curchild);
                startActivity(intent);
            }
        });
    }


    private void init(){
        addButton = (Button) findViewById(R.id.children_medications_add_medication_btn);
        ChildrenMedicationsListview = (ListView) findViewById(R.id.children_medications_listview);
    }

    private void refreshList(){
        MedicationData[] dat = new MedicationData[list.size()];
        dat = list.toArray(dat);
        ChildrenMedicationsListview.setAdapter(new MedicationAdapter(this,dat));
    }

    class MedicationAdapter extends BaseAdapter {

        Context context;
        MedicationData[] data;
        private LayoutInflater inflater = null;

        public MedicationAdapter(Context context, MedicationData[] data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.medication_item_layout, null);
            TextView medicationItemName = (TextView) vi.findViewById(R.id.medication_item_name_id);
            TextView medicationQuantity = (TextView) vi.findViewById(R.id.medication_quantity_id);
            TextView medicationTime = (TextView) vi.findViewById(R.id.medication_time_id);
            Button addAlarmBtn = (Button) vi.findViewById(R.id.medication_alarm_add_btn);

            final String medicName = data[position].m_name;
            medicationItemName.setText(data[position].m_name);
            medicationQuantity.setText("" + data[position].quantity);
            medicationTime.setText(renderTime(splitToComponentTimes(data[position].m_time)));

            addAlarmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChildrenMedicationsActivity.this, AddAlarmActivity.class);
                    intent.putExtra("v0","Medication ");
                    intent.putExtra("v1",curchild);
                    intent.putExtra("v2","");
                    intent.putExtra("v3",medicName);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

            return vi;
        }
    }

    private int[] splitToComponentTimes(Long num)
    {
        long longVal = num;
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }

    private String renderTime(int[] num)
    {
        String time = "";
        if(num[0] != 0) {
            time += num[0]+ " hours ";
        }
        if(num[1] != 0){
            time += num[1] + " minutes ";
        }
        if(num[2] != 0){
            time += num[2] + " seconds ";
        }

        return time;
    }


}
