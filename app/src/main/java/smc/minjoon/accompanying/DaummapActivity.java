package smc.minjoon.accompanying;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

public class DaummapActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";
    String a ;
    LocationManager lm;
    TextView tv01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daummap);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGrantStorage = grantExternalStoragePermission();

         tv01 = (TextView) findViewById(R.id.tv01);

        if (isGrantStorage) {
// 일반처리.   // MapView 객체생성 및 API Key 설정
            Toast.makeText(this, "권한을 허락하셨습니다.", Toast.LENGTH_SHORT).show();
            MapView mapView = new MapView(DaummapActivity.this);
            mapView.setDaumMapApiKey("fddf269318894c9791ec626cc3d6c8f9");
            RelativeLayout mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
            mapViewContainer.addView(mapView);


        }

        // Using TedPermission library


    }

    ;

    private boolean grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                Log.v(TAG, "Permission is granted");
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
                return true;
                //만약 두개의 퍼미션이 전부 허락되어있다면 true반환
            } else if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
                //만약 Fine퍼미션이 안되어 있다면 요청
            } else if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                return false;
                //만약 COARSE퍼미션이 안되어 있다면 요청
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                //만약 두개의 퍼미션이 전부 안되어 있다면 요청
                return false;
            }
        } else {
            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "External Storage Permission is Grant ");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
            return true;
        }





    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if(requestCode == 1){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    finish();
                    startActivity(new Intent(this, DaummapActivity.class));

                } else {

                    Toast.makeText(this, "access fine권한을 거절하셨습니다. 도움길서비스에 제약이 있습니다.", Toast.LENGTH_LONG).show();
                }
            }else{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    finish();
                    startActivity(new Intent(this, DaummapActivity.class));

                } else {

                    Toast.makeText(this, "access coarse권한을 거절하셨습니다. 도움길서비스에 제약이 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    private void registerLocationUpdates() {

    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                float accuracy = location.getAccuracy();
                a = Double.toString(longitude);
                tv01.setText(a);
            }
            else{
                
            }
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

