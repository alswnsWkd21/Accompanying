package smc.minjoon.accompanying.MainSettingButton;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.Login.MainActivity;
import smc.minjoon.accompanying.MainSosButton.Sos.AlwaysOnTopService;
import smc.minjoon.accompanying.R;

public class SettingsosActivity extends AppCompatActivity {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsos);

        Button sosbtn01 = (Button) findViewById(R.id.sosbtn01);
        Button sosbtn02 = (Button) findViewById(R.id.sosbtn02);
        grantExternalStoragePermission();
        sosbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
//                startService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
            }
        });
        sosbtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
            }
        });
    }
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsosActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        dialo.dismiss();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                           //닫기
                    }
                });
                alert.setMessage("SOS버튼을 활성화 하기위해 권한이 필요합니다.");
                alert.setCancelable(false);
                alert.show();// 체크

            } else {
                startService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
            }
        } else {
            startService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // TODO 동의를 얻지 못했을 경우의 처리
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsosActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        dialo.dismiss();
                       Intent i = new Intent(SettingsosActivity.this, MainActivity.class);
                        startActivity(i);

                    }
                });
                alert.setMessage("동의하지않아 SOS서비스를 이용하실 수 없습니다.");
                alert.setCancelable(false);
                alert.show();
            } else {
                startService(new Intent(SettingsosActivity.this, AlwaysOnTopService.class));
            }
        }
    }
    private void grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    &&(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)) {

                //만약 두개의 퍼미션이 전부 허락되어있다면 true반환
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsosActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(SettingsosActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("SOS기능을 효율적으로 사용하기 위해서 몇 가지의 권한이 필요합니다 \n\n" +
                        "위치정보: 도움요청을 위해 위치정보가 필요합니다 \n\n"+
                        "문자:  도움문자를 보내기위해 권한이 필요합니다");
                alert.setCancelable(false);
                alert.show();
            }
        } else {
//            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "External Storage Permission is Grant ");


        }
    }
}
