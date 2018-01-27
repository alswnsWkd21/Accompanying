package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserAccompany.AccompanyActivity;
import smc.minjoon.accompanying.R;

public class AccompanySelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accompany_select_activity);

        ImageButton helperbtn=(ImageButton)findViewById(R.id.emergencybtn1);
        ImageButton disabledbtn=(ImageButton)findViewById(R.id.reservationbtn1);


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
                Intent disabledIntent=new Intent(AccompanySelectActivity.this,ReservationSelectActivity.class);
                AccompanySelectActivity.this.startActivity(disabledIntent);
            }
        });
    }
}

