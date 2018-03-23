package smc.minjoon.accompanying.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.MainSettingButton.HelperMyPage.HelperaccompanyBackgroundTask;
import smc.minjoon.accompanying.R;

public class TimeSelectActivity extends AppCompatActivity {
String helperID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);



        Button helperbtn=(Button)findViewById(R.id.helperbtn);
        Button disabledbtn=(Button)findViewById(R.id.disabledbtn);
        SessionManager sessionManager = new SessionManager(TimeSelectActivity.this);
        helperID= sessionManager.getKeyId();

        helperbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelperaccompanyBackgroundTask(TimeSelectActivity.this, helperID, "HelperAccompanyList.php?helperID=" ).execute();
            }
        });

        disabledbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HelperaccompanyBackgroundTask(TimeSelectActivity.this, helperID, "HelperReservationList.php?helperID=" ).execute();
            }
        });
    }
}
