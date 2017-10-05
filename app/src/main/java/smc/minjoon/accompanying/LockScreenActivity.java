package smc.minjoon.accompanying;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class LockScreenActivity extends AppCompatActivity {
    int num;
    String com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        LinearLayout ll = (LinearLayout) findViewById(R.id.layout01);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        final Vibrator vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        AutoRepeatButton btnsos = new AutoRepeatButton(LockScreenActivity.this);
        btnsos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vide.vibrate(500);
            }
        });
        btnsos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vide.vibrate(1000);
                Intent intent = new Intent(LockScreenActivity.this, PopupActivity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(com.equals("Close")){
                            cancel();
                        }else{
                            sendSMS("01068800161","확인테스트");
                        }
                    }
                },10000);
                return true;
            }
        });

        ll.addView(btnsos);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                 com = data.getStringExtra("result");

            }
        }
    }




    private void sendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        break;
                }
            }
        }, new IntentFilter(SENT));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }






}
