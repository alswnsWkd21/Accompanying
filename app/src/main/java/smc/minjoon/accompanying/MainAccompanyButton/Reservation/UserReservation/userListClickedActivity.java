package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting.ReservationChattingActivity;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

public class userListClickedActivity extends AppCompatActivity {

    private ReservationUserListAdapter adapter;
    private List<Reservation> ReservationList;
    String reservationID;
    String userID;
    String helperID;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_userclicked);
        Intent intent = getIntent();

        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationUserListAdapter(getApplicationContext(),ReservationList);
        TextView id=(TextView)findViewById(R.id.tv_id);
        TextView place=(TextView)findViewById(R.id.tv_place);
        TextView date=(TextView)findViewById(R.id.tv_date);
        TextView time=(TextView)findViewById(R.id.tv_time);
        TextView content=(TextView)findViewById(R.id.tv_content);
        TextView statustv=(TextView)findViewById(R.id.tv_status);
        Button cancelBtn=(Button)findViewById(R.id.reservationCancle);
        Button chattingBtn=(Button)findViewById(R.id.reservationChatting);
        Button finishBtn =(Button)findViewById(R.id.reservationFinish);
        userID = intent.getStringExtra("userID");
        helperID = intent.getStringExtra("helperID");
        status = intent.getStringExtra("status");
        id.setText(helperID);
        statustv.setText(status);

        place.setText(intent.getStringExtra("reservationPlace"));
        date.setText(intent.getStringExtra("reservationDate"));
        time.setText(intent.getStringExtra("reservationTime"));
        content.setText(intent.getStringExtra("reservationWork"));
        reservationID = intent.getStringExtra("reservationID");
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                              new userBackgroundTask().execute();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                ReservationDeleteRequest reservationDeleteRequest = new ReservationDeleteRequest(reservationID ,responseListener);
                RequestQueue queue = Volley.newRequestQueue(userListClickedActivity.this);
                queue.add(reservationDeleteRequest);
            }
        });

        chattingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("신청완료")){
                    Intent i = new Intent(userListClickedActivity.this, ReservationChattingActivity.class);
                    i.putExtra("userID", userID);
                    i.putExtra("helperID", helperID);
                    i.putExtra("reservationID", reservationID);
                    startActivity(i);
                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(userListClickedActivity.this);
                    builder.setMessage("현재 매칭된 도우미가 없습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                }

//                 여기다가 채팅을 구현하면 된다.
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                Intent i = new Intent(userListClickedActivity.this, ReservationTimeCheck.class);
                                i.putExtra("reservationID", reservationID);
                                startActivity(i);

            }
        });
    }
    class userBackgroundTask extends AsyncTask<Void,Void,String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target="http://syoung1394.cafe24.com/userList.php?userID="+userID;
        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url =new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent=new Intent(userListClickedActivity.this, ReservationSelectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("userList", result);
            userListClickedActivity.this.startActivity(intent);
        }
    }
}
