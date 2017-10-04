package smc.minjoon.accompanying;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class LockScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        Button btnsos01 = (Button) findViewById(R.id.btnsos01);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        btnsos01.setOnTouchListener(new View.OnTouchListener() {
            long time_down;
            long time_up;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        time_down =  System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        time_up = System.currentTimeMillis();
                        if(time_up - time_down > 5000){
                            Toast.makeText(LockScreenActivity.this, "5초를 초과했어 성공", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LockScreenActivity.this, "일반 클릭", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

                return true;
            }
        });

    }
}
