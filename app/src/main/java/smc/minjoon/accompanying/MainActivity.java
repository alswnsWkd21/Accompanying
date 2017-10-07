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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btn01 = (ImageButton ) findViewById(R.id.btn01);
        ImageButton btn02 = (ImageButton ) findViewById(R.id.btn02);
        ImageButton btn03 = (ImageButton ) findViewById(R.id.btn03);
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
                Intent i = new Intent(MainActivity.this, InformSos.class);
                startActivity(i);
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

    }

    }
