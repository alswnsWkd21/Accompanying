package smc.minjoon.accompanying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import smc.minjoon.accompanying.MainMapButton.TmapActivity;
import smc.minjoon.accompanying.MainSettingButton.SettingsActivity;
import smc.minjoon.accompanying.MainSosButton.InformSos;

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
        Intent i = new Intent(MainActivity.this, TmapActivity.class);
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
