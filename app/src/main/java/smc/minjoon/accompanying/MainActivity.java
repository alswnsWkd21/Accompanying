package smc.minjoon.accompanying;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn01 =  (Button) findViewById(R.id.btn01);
        Button btn02 =  (Button) findViewById(R.id.btn02);
        Button btn03 =  (Button) findViewById(R.id.btn03);
        Button btn04 =  (Button) findViewById(R.id.btn04);
        Button btn05 =  (Button) findViewById(R.id.btn05);
        Button btn06 =  (Button) findViewById(R.id.btn06);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DaummapActivity.class);
                startActivity(i);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SosActivity.class);
                startActivity(i);
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, AlwaysOnTopService.class));
            }
        });
        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, AlwaysOnTopService.class));
            }
        });
        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(i);
            }
        });
        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(i);
            }
        });


    }

    }



