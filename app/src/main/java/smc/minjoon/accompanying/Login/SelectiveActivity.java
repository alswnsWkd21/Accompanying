package smc.minjoon.accompanying.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.Login.NoneLogin.NoneMainActivity;
import smc.minjoon.accompanying.R;

public class SelectiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selective);

        Button helperbtn=(Button)findViewById(R.id.helperbtn);
        Button disabledbtn=(Button)findViewById(R.id.disabledbtn);
        Button freepassbtn=(Button)findViewById(R.id.freepassbtn);
    
        helperbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helperIntent=new Intent(SelectiveActivity.this,LoginActivity.class);
                helperIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// Add new Flag to start new Activity
                helperIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Staring Login Activity
                SelectiveActivity.this.startActivity(helperIntent);
            }
        });

        disabledbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent disabledIntent=new Intent(SelectiveActivity.this,DisabledLoginActivity.class);
                disabledIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// Add new Flag to start new Activity
                disabledIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Staring Login Activity
                SelectiveActivity.this.startActivity(disabledIntent);
            }
        });

        freepassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent free=new Intent(SelectiveActivity.this,NoneMainActivity.class);
                free.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// Add new Flag to start new Activity
                free.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Staring Login Activity
                SelectiveActivity.this.startActivity(free);
            }
        });
    }
}
