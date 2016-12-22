package com.example.asus.cancercare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicationActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;
    final List<String> child = new ArrayList<String>();

    // Layout Components
    private ListView childList;
    StableArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        init();
    }

    private void init(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserId = mFirebaseUser.getUid();

        mDatabase.child("users").child(mUserId).child("children").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String childFname = childSnapshot.child("fname").getValue(String.class);
                    String childLname = childSnapshot.child("l_name").getValue(String.class);

                    String childName = childFname + " " + childLname;
                    child.add(childName);
                    refreshList();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        childList = (ListView) findViewById(R.id.medication_child_list_view);

        String[] childs = new String[child.size()];
        childs = child.toArray(childs);
        int[] ids = new int[childs.length];
        for ( int i = 0; i<childs.length; i++){
            ids[i] = i;
        }

        mAdapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, child);

        childList.setAdapter(mAdapter);

        childList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                String childName = child.get(position);

                Intent intent = new Intent(MedicationActivity.this, ChildrenMedicationsActivity.class);
                intent.putExtra("childid", childName);
                startActivity(intent);
            }

        });
    }

    private void refreshList(){
        String[] childs = new String[child.size()];
        childs = child.toArray(childs);
        int[] ids = new int[childs.length];
        for ( int i = 0; i<childs.length; i++){
            ids[i] = i;
        }
        mAdapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, child);

        childList.setAdapter(mAdapter);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
