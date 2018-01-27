package smc.minjoon.accompanying.MainSettingButton.UserMyPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.R;

/**
 * Created by SY on 2018-01-15.
 */

public class MypageActivity extends AppCompatActivity {
    TextView tv01;
    Button informbtn;
    Button rconfirmbtn;
    Button aconfirmbtn;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        tv01 = (TextView) findViewById(R.id.textView);
//        informbtn = (Button) findViewById(R.id.infrombtn);
        rconfirmbtn= (Button) findViewById(R.id.confirmbtn);
        aconfirmbtn= (Button) findViewById(R.id.confirmbtn2);
        SessionManager sessionManager = new SessionManager(MypageActivity.this);
        userID= sessionManager.getKeyId();
        tv01.setText(sessionManager.getKeyName());

        rconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new accompanyBackgroundTask(MypageActivity.this, userID, "ReservationList.php?userID=" ).execute();
            }
        });

        aconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new accompanyBackgroundTask(MypageActivity.this, userID, "AccompanyList.php?userID=" ).execute();
            }
        });

    }
}
