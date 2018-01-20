package smc.minjoon.accompanying.MainSettingButton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.MainSettingButton.ContactButton.NumberActivity;
import smc.minjoon.accompanying.R;

public class HelperSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_helper);
        Button setbtn01 = (Button) findViewById(R.id.setbtn01);
        Button setbtn02 = (Button) findViewById(R.id.setbtn02);
        Button setbtn03 = (Button) findViewById(R.id.setbtn03);
        setbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HelperSettingsActivity.this, SettinglockActivity.class);
                startActivity(i);
            }
        });
        setbtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HelperSettingsActivity.this, NumberActivity.class);
                startActivity(i);
            }
        });
        setbtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HelperSettingsActivity.this, MypageActivity.class);
                startActivity(i);
            }
        });
    }

}
