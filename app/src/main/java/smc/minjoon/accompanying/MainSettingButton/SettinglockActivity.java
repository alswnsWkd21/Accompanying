package smc.minjoon.accompanying.MainSettingButton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.R;
import smc.minjoon.accompanying.LockScreen.ScreenService;

public class SettinglockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settinglock);

        Button lockbtn01 = (Button) findViewById(R.id.lockbtn01);
        Button lockbtn02 = (Button) findViewById(R.id.lockbtn02);

        lockbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettinglockActivity.this, ScreenService.class);
                startService(intent);
            }
        });
        lockbtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettinglockActivity.this, ScreenService.class);
                stopService(intent);
            }
        });
    }
}
