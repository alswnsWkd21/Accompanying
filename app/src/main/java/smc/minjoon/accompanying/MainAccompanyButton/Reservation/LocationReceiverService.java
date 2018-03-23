package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import smc.minjoon.accompanying.Login.SessionManager;

public class LocationReceiverService extends Service {
    public LocationReceiverService() {
    }

    Timer timer;
    TimerTask timertask;
    LocationManager lm;
    private String TAG = "permissionstatus";
    double longitude;
    double latitude;

    @Override
    public void onCreate() {
        super.onCreate();
        final SessionManager sessionmanager = new SessionManager(LocationReceiverService.this);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.'
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);

        }


        final String helperID =sessionmanager.getKeyId();
        final double helperLat = latitude;
        final double helperlong = longitude;
        timer = new Timer(true);
        timertask = new TimerTask() {
            @Override
            public void run() {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
//                                        Log.v("location", "성공");
//                                        Log.v("location2", sessionmanager.getKeyId());
//                                        Log.v("location3", String.valueOf(latitude));
//                                        Log.v("location4", String.valueOf(longitude));
                                    } else {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                        builder.setMessage("회원 등록에 실패했습니다.")
//                                                .setNegativeButton("다시 시도", null)
//                                                .create()
//                                                .show();
                                        Log.v("location", "실패");
                                    }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        };
                        LocationRegisterRequest locationrequest = new LocationRegisterRequest(helperID, latitude, longitude, responseListener );
                        RequestQueue queue= Volley.newRequestQueue(LocationReceiverService.this);
                        queue.add(locationrequest);

                    }


        };

        timer.schedule(timertask, 10000, 180000 );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final LocationListener mLocationListener = new LocationListener() {

        float accuracy;
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            accuracy = location.getAccuracy();

            lm.removeUpdates( this );
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };

}
