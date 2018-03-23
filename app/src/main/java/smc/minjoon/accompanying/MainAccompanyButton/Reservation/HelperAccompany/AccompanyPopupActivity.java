package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperAccompany;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import smc.minjoon.accompanying.Login.AbledMainActivity;
import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting.ChattingActivity;
import smc.minjoon.accompanying.R;

public class AccompanyPopupActivity extends AppCompatActivity {
    LocationManager lm;
    double longitude;
    double latitude;
    String presentTime;
    String starthour;
    String startminute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompanypopup);

        Button acceptbtn = (Button) findViewById(R.id.accept);
        Button canclebtn = (Button) findViewById(R.id.cancel);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        grantExternalStoragePermission();
        Intent intent = getIntent();
        final String userID=intent.getStringExtra("userID");
        final String userName=intent.getStringExtra("userName");
        final String userWork=intent.getStringExtra("userWork");
        final String userPlace=intent.getStringExtra("userPlace");
        final double userLat=intent.getDoubleExtra("userLat",0);
        final String userPhone =intent.getStringExtra("userPhone");
        final double userlong =intent.getDoubleExtra("userlong",0);
        final SessionManager sessionmanager = new SessionManager(AccompanyPopupActivity.this);
        final String helperID =sessionmanager.getKeyId();
        final String helperName = sessionmanager.getKeyName();
        final String helperPhone = sessionmanager.getKeyPhone();


        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                    여기서 채팅창으로 보내주면 될 듯
                        Calendar calendar = Calendar.getInstance();
                        final Calendar calendar2 = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int date2 = calendar.get(Calendar.DATE);
                        int hour = calendar.get(Calendar.HOUR);
                        int minute = calendar.get(Calendar.MINUTE);
                        starthour = String.valueOf(hour);
                        startminute = String.valueOf(minute);
                        presentTime = String.valueOf(hour)+"시"+String.valueOf(minute)+"분";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

//
                                    Intent i = new Intent(AccompanyPopupActivity.this, ChattingActivity.class);
                                     i.putExtra("userID", userID);
                                    i.putExtra("userName", userName);
                                    i.putExtra("userWork", userWork);
                                    i.putExtra("userPlace", userPlace);
                                    i.putExtra("userLat", userLat);
                                    i.putExtra("userPhone", userPhone);
                                    i.putExtra("userlong", userlong);
//                                    i.putExtra("starthour", starthour);
//                                    i.putExtra("startminute", startminute);

                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);

                            }else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(AccompanyPopupActivity.this);
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialo, int which) {
                                        Intent i = new Intent(AccompanyPopupActivity.this, AbledMainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        dialo.dismiss();     //닫기
                                    }
                                });
                                alert.setMessage("요청자가 도움요청을 취소하였습니다.");
                                alert.setCancelable(false);
                                alert.show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AccompanyPopupRequest accompanypopupRequest = new AccompanyPopupRequest(userID,userName,userWork, userPlace, userPhone, String.valueOf(userLat), String.valueOf(userlong), helperID, helperName, helperPhone,String.valueOf(latitude), String.valueOf(longitude) , responseListener);
                RequestQueue queue = Volley.newRequestQueue(AccompanyPopupActivity.this);
                queue.add(accompanypopupRequest);

            }
        });


        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(AccompanyPopupActivity.this, AbledMainActivity.class);
                startActivity(i);
            }
        });

    }

    private void grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
            }else {
                AlertDialog.Builder alert = new AlertDialog.Builder(AccompanyPopupActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(AccompanyPopupActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("도움길 기능을 효율적으로 사용하기 위해서 몇 가지의 권한이 필요합니다 \n\n" +
                        "위치정보:  현재의 위치를 위해 위치정보가 필요합니다 \n\n"+
                        "PHONE:   시설에 전화를 연결하기 위해 필요합니다.");
                alert.setCancelable(false);
                alert.show();
            }
        } else {
//            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "External Storage Permission is Grant ");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);

        }
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


