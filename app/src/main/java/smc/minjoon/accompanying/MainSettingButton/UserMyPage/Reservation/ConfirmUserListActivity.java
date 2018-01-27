package smc.minjoon.accompanying.MainSettingButton.UserMyPage.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

public class ConfirmUserListActivity extends AppCompatActivity {
    private ListView listView;
    private ReservationConfirmListAdapter adapter;
    private List<Reservation> ReservationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_user_list);

        Intent intent = getIntent();
        listView=(ListView) findViewById(R.id.listView);
        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationConfirmListAdapter(getApplicationContext(),ReservationList);
        listView.setAdapter(adapter);

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray=jsonObject.getJSONArray("response");
            int count=0;
            String userID;
            String reservationID;
            String reservationPlace;
            String reservationDate;
            String reservationTime;
            String reservationWork;
            String helperID;
            String status;

            while(count<jsonArray.length()){
                JSONObject object=jsonArray.getJSONObject(count);
                userID=object.getString("u_ID");
                reservationID =object.getString("reservationID");
                reservationPlace=object.getString("reservationPlace");
                reservationDate=object.getString("reservationDate");
                reservationTime=object.getString("reservationWorkTime");
                reservationWork=object.getString("reservationWork");
                helperID = object.getString("h_ID");
                status = object.getString("status");


                Reservation reservation=new Reservation(userID,reservationID,reservationPlace,reservationDate,reservationTime,reservationWork,helperID,status);

                ReservationList.add(reservation);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
