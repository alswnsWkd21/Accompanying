package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

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

import smc.minjoon.accompanying.R;

public class userListClickedActivity extends AppCompatActivity {

    private ReservationListAdapter adapter;
    private List<Reservation> ReservationList;
    String reservationID;
    String userID;
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
        userID = intent.getStringExtra("userID");
        id.setText(userID);

        place.setText(intent.getStringExtra("reservationPlace"));
        date.setText(intent.getStringExtra("reservationDate"));
        time.setText(intent.getStringExtra("reservationTime"));
        content.setText(intent.getStringExtra("reservationWork"));
        reservationID = intent.getStringExtra("reservationID");
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
            Intent intent=new Intent(userListClickedActivity.this, UserListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("userList", result);
            userListClickedActivity.this.startActivity(intent);
        }
    }
}
