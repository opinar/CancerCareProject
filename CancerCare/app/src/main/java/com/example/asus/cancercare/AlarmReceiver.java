package com.example.asus.cancercare;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();

    }

}