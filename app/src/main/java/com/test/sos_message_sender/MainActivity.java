package com.test.sos_message_sender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private LocationManager manager;
    private GPSReceiver receiver;
    double latitude = 0;
    double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButtonListenerMethod();
        receiver = new GPSReceiver();
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, receiver);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, receiver);
        }
    }

    public void myButtonListenerMethod() {
        Button button = (Button) findViewById(R.id.sendSOS);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                String phoneNumber = "9531596121";
                String messageBody = "Please take me from longitude:" + Double.toString(longitude) + " and latitude:" + Double.toString(latitude);
                try {
                    sms.sendTextMessage(phoneNumber, null, messageBody, null, null);

                    Toast.makeText(getApplicationContext(), "S.O.S. message sent !", Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Message sending failed !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class GPSReceiver implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(getApplicationContext(),
                        "READY TO SEND!!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "NOT READY YET...", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(getApplicationContext(), "GPS Enabled !", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(getApplicationContext(), "Please enable GPS!", Toast.LENGTH_LONG).show();
        }
    }

}