package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.R;

public class userListClickedActivity extends AppCompatActivity {

    private ReservationListAdapter adapter;
    private List<Reservation> ReservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_userclicked);
        Intent intent = getIntent();

        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationListAdapter(getApplicationContext(),ReservationList);
        TextView id=(TextView)findViewById(R.id.tv_id1);
        TextView place=(TextView)findViewById(R.id.tv_place1);
        TextView date=(TextView)findViewById(R.id.tv_date1);
        TextView time=(TextView)findViewById(R.id.tv_time1);
        TextView content=(TextView)findViewById(R.id.tv_content1);
        Button signup=(Button)findViewById(R.id.signup1);

        id.setText(intent.getStringExtra("userID"));
        place.setText(intent.getStringExtra("reservationPlace"));
        date.setText(intent.getStringExtra("reservationDate"));
        time.setText(intent.getStringExtra("reservationTime"));
        content.setText(intent.getStringExtra("reservationWork"));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                여기다가 디비 취소하는거 넣으면 된다.
            }
        });



    }
}
