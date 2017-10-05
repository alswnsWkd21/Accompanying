package smc.minjoon.accompanying;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
        ll.addView(btnsos);


    }





}
