package smc.minjoon.accompanying.MainSettingButton.UserMyPage.Reservation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

public class ConfirmUserListClickedActivity extends AppCompatActivity {
    private ReservationConfirmListAdapter adapter;
    private List<Reservation> ReservationList;
    String reservationID;
    String helperID;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_user_list_clicked);
        Intent intent = getIntent();

        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationConfirmListAdapter(getApplicationContext(),ReservationList);

        TextView userid=(TextView)findViewById(R.id.tv_userid);
        TextView helperid=(TextView)findViewById(R.id.tv_helperid);
        TextView place=(TextView)findViewById(R.id.tv_place);
        TextView date=(TextView)findViewById(R.id.tv_date);
        TextView time=(TextView)findViewById(R.id.tv_time);
        TextView content=(TextView)findViewById(R.id.tv_content);
        Button signup=(Button)findViewById(R.id.signup);

        userid.setText(intent.getStringExtra("userID"));
        helperid.setText(intent.getStringExtra("helperID"));
        place.setText(intent.getStringExtra("reservationPlace"));
        date.setText(intent.getStringExtra("reservationDate"));
        time.setText(intent.getStringExtra("reservationTime"));
        content.setText(intent.getStringExtra("reservationWork"));
        reservationID = intent.getStringExtra("reservationID");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmUserListClickedActivity.this);
                                        builder.setMessage("서비스 준비중에 있습니다.")
                                                .setPositiveButton("다시 시도", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .create()
                                                .show();

            }
        });

    }
}
