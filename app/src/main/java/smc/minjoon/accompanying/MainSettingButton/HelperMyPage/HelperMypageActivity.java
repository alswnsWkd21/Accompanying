package smc.minjoon.accompanying.MainSettingButton.HelperMyPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.R;

public class HelperMypageActivity extends AppCompatActivity {
    TextView tv01;
    Button informbtn;

    String helperID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_mypage);

        tv01 = (TextView) findViewById(R.id.textView);


        SessionManager sessionManager = new SessionManager(HelperMypageActivity.this);
        helperID= sessionManager.getKeyId();
        tv01.setText(sessionManager.getKeyName());



    }
}
