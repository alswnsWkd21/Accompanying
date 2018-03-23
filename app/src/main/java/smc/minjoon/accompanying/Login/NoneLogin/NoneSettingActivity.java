package smc.minjoon.accompanying.Login.NoneLogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.MainSettingButton.ContactButton.NumberActivity;
import smc.minjoon.accompanying.MainSettingButton.SettinglockActivity;
import smc.minjoon.accompanying.R;

public class NoneSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_none_setting);
        Button setbtn01 = (Button) findViewById(R.id.setbtn01);
        Button setbtn02 = (Button) findViewById(R.id.setbtn02);
        Button setbtn03 = (Button) findViewById(R.id.setbtn03);
        setbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoneSettingActivity.this, SettinglockActivity.class);
                startActivity(i);
            }
        });
        setbtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoneSettingActivity.this, NumberActivity.class);
                startActivity(i);
            }
        });
        setbtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(NoneSettingActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("로그인을 하여야 이용가능한 서비스입니다.");
                alert.setCancelable(false);
                alert.show();
            }
        });
    }
}
