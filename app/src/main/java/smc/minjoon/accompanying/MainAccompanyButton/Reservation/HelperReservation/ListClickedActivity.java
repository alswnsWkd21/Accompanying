package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperReservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.BackgroundTask;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

import static android.view.View.GONE;

public class ListClickedActivity extends AppCompatActivity {

    private ReservationListAdapter adapter;
    private List<Reservation> ReservationList;
    String reservationID;
    String helperID;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clicked);
        Intent intent = getIntent();

        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationListAdapter(getApplicationContext(),ReservationList);
        TextView id=(TextView)findViewById(R.id.tv_id);
        TextView place=(TextView)findViewById(R.id.tv_place);
        TextView date=(TextView)findViewById(R.id.tv_date);
        TextView time=(TextView)findViewById(R.id.tv_time);
        TextView content=(TextView)findViewById(R.id.tv_content);
        Button signup=(Button)findViewById(R.id.signup);
        TextView tvstatus=(TextView)findViewById(R.id.tv_status);
        id.setText(intent.getStringExtra("userID"));
        place.setText(intent.getStringExtra("reservationPlace"));
        date.setText(intent.getStringExtra("reservationDate"));
        time.setText(intent.getStringExtra("reservationTime"));
        content.setText(intent.getStringExtra("reservationWork"));
        reservationID = intent.getStringExtra("reservationID");
        status=intent.getStringExtra("status");



        SessionManager sessionManager = new SessionManager(ListClickedActivity.this);
        helperID =sessionManager.getKeyId();

        if(status.equals("신청완료")){
            tvstatus.setText(status);
            signup.setVisibility(GONE);
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                new BackgroundTask(ListClickedActivity.this, helperID).execute();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                ReservationApplyRequest reservationApplyRequest = new ReservationApplyRequest(reservationID, helperID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ListClickedActivity.this);
                queue.add(reservationApplyRequest);


            }
        });


    }
}
