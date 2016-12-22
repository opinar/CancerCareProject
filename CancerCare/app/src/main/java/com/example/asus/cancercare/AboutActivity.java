package com.example.asus.cancercare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView t2 = (TextView) findViewById(R.id.about_kacuv);
        t2.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
