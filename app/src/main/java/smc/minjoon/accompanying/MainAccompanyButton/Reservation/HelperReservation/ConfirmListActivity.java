package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperReservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

public class ConfirmListActivity extends AppCompatActivity {

    private ListView listView;
    private ReservationConfirmListAdapter adapter;
    private List<Reservation> ReservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_list);
        Intent intent = getIntent();

        listView=(ListView) findViewById(R.id.listView1);
        ReservationList=new ArrayList<Reservation>();
        adapter=new ReservationConfirmListAdapter(getApplicationContext(),ReservationList);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject=new JSONObject(intent.getStringExtra("userList"));
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
                reservationTime=object.getString("reservationTime");
                reservationWork=object.getString("reservationWork");
                helperID = object.getString("h_ID");
                status = object.getString("status");


                Reservation reservation=new Reservation(userID,reservationID,reservationPlace,reservationDate,reservationTime,reservationWork,helperID,status);

                ReservationList.add(reservation);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String userID=((Reservation)adapter.getItem(i)).getUserID();
                String reservationID=((Reservation)adapter.getItem(i)).getReservationID();
                String reservationPlace=((Reservation)adapter.getItem(i)).getReservationPlace();
                String reservationDate=((Reservation)adapter.getItem(i)).getReservationDate();
                String reservationTime=((Reservation)adapter.getItem(i)).getReservationTime();
                String reservationWork=((Reservation)adapter.getItem(i)).getReservationWork();
                String helperID = ((Reservation)adapter.getItem(i)).getHelperID();
                String status = ((Reservation)adapter.getItem(i)).getStatus();

//                Intent intent=new Intent(ListActivity.this,ListClickedActivity.class);
//                    intent.putExtra("userID",ReservationList.get(i).getUserID());
//                    intent.putExtra("reservationID",ReservationList.get(i).getReservationID());
//                    intent.putExtra("reservationPlace",ReservationList.get(i).getReservationPlace());
//                    intent.putExtra("reservationDate",ReservationList.get(i).getReservationDate());
//                    intent.putExtra("reservationTime",ReservationList.get(i).getReservationTime());
//                    intent.putExtra("reservationWork",ReservationList.get(i).getReservationWork());
//                    intent.putExtra("helperID", ReservationList.get(i).getHelperID());
//                    intent.putExtra("status", ReservationList.get(i).getStatus());
//
//                    startActivity(intent);

            }

        });

    }
}

