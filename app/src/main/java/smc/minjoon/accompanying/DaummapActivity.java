package smc.minjoon.accompanying;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

public class DaummapActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";
    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daummap);
        boolean isGrantStorage = grantExternalStoragePermission();

        if(isGrantStorage){
// 일반처리.   // MapView 객체생성 및 API Key 설정
            Toast.makeText(this, "권한을 허락하셨습니다.",Toast.LENGTH_SHORT).show();
            MapView mapView = new MapView(DaummapActivity.this);
            mapView.setDaumMapApiKey("fddf269318894c9791ec626cc3d6c8f9");
            RelativeLayout mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
            mapViewContainer.addView(mapView);


        }

        // Using TedPermission library


    };
    private boolean grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            }else{
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return false;
            }
        }else{
            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "External Storage Permission is Grant ");
            return true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                finish();
                startActivity(new Intent(this, DaummapActivity.class));

            }else{

                    Toast.makeText(this, "권한을 거절하셨습니다. 도움길서비스에 제약이 있습니다.",Toast.LENGTH_LONG).show();
            }
        }
    }

    
}

