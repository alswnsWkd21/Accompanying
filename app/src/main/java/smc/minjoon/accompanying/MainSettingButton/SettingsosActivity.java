package smc.minjoon.accompanying.MainSettingButton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.AlwaysOnTopService;
import smc.minjoon.accompanying.R;

public class SettingsosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsos);

        Button sosbtn01 = (Button) findViewById(R.id.sosbtn01);
        Button sosbtn02 = (Button) findViewById(R.id.sosbtn02);

        sosbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
            }
        });
        sosbtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
            }
        });
    }
}
