package smc.minjoon.accompanying;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import smc.minjoon.accompanying.Login.SelectiveActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Handler handler = new Handler();
        handler.postDelayed(    new Runnable() {
            public void run() {
                Intent intent = new Intent(IntroActivity.this, SelectiveActivity.class);
                startActivity(intent);
// 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
                finish();
            }
        }, 2000);
    }
}
