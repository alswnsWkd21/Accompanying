package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperReservation;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting.ReservationChattingActivity;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

public class ListDetailClickedActivity extends AppCompatActivity {

    private ReservationListAdapter adapter;
    private List<Reservation> ReservationList;
    String userID;
    String reservationID;
    String helperID;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_clicked);
        Intent intent = getIntent();

        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationListAdapter(getApplicationContext(),ReservationList);
        TextView id=(TextView)findViewById(R.id.tv_id);
        TextView place=(TextView)findViewById(R.id.tv_place);
        TextView date=(TextView)findViewById(R.id.tv_date);
        TextView time=(TextView)findViewById(R.id.tv_time);
        TextView content=(TextView)findViewById(R.id.tv_content);
        Button reservationCancel=(Button)findViewById(R.id.reservationCancle);
        Button reservationChatting=(Button)findViewById(R.id.reservationChatting);
        TextView tvstatus=(TextView)findViewById(R.id.tv_status);
        id.setText(intent.getStringExtra("userID"));
        place.setText(intent.getStringExtra("reservationPlace"));
        date.setText(intent.getStringExtra("reservationDate"));
        time.setText(intent.getStringExtra("reservationTime"));
        content.setText(intent.getStringExtra("reservationWork"));
        userID = intent.getStringExtra("userID");
        reservationID = intent.getStringExtra("reservationID");
        status=intent.getStringExtra("status");



        SessionManager sessionManager = new SessionManager(ListDetailClickedActivity.this);
        helperID =sessionManager.getKeyId();


        reservationCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListDetailClickedActivity.this);
                builder.setMessage("장애인분들에게 문제가 생길 수 있어 가급적이면 7일 전부터는 취소를 삼가해주세요. \n지금 취소하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if (success) {
                                                new BackgroundTask(ListDetailClickedActivity.this, helperID).execute();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                };
                                ReservationUpdateRequest reservationDeleteRequest = new ReservationUpdateRequest(reservationID, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ListDetailClickedActivity.this);
                                queue.add(reservationDeleteRequest);
                            }
                        })
                        .create()
                        .show();
            }
        });
        reservationChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ListDetailClickedActivity.this, ReservationChattingActivity.class);
                i.putExtra("userID", userID);
                i.putExtra("helperID", helperID);
                i.putExtra("reservationID", reservationID);
                startActivity(i);
//               채팅창으로 가는 코드 구현 reservationid를 통해서 채팅방구현하면 될 것같다.
            }
        });


    }
}
