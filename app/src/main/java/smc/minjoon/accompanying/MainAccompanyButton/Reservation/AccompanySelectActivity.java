package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import smc.minjoon.accompanying.R;

public class AccompanySelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompany_select);

        Button helperbtn=(Button)findViewById(R.id.emergencybtn);
        Button disabledbtn=(Button)findViewById(R.id.reservationbtn);


        helperbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helperIntent=new Intent(AccompanySelectActivity.this,AccompanyActivity.class);
                helperIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
// Add new Flag to start new Activity
                helperIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Staring Login Activity
                AccompanySelectActivity.this.startActivity(helperIntent);
            }
        });

        disabledbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent disabledIntent=new Intent(AccompanySelectActivity.this,ReservationActivity.class);
                AccompanySelectActivity.this.startActivity(disabledIntent);
            }
        });
    }
}

