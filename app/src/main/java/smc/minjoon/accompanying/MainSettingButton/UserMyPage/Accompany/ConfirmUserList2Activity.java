package smc.minjoon.accompanying.MainSettingButton.UserMyPage.Accompany;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Accompany;
import smc.minjoon.accompanying.R;

public class ConfirmUserList2Activity extends AppCompatActivity {
    private ListView listView;
    private AccompanyConfirmListAdapter adapter;
    private List<Accompany> AccompanyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_user_list2);
        Intent intent = getIntent();
        listView=(ListView) findViewById(R.id.listView);
        AccompanyList=new ArrayList<Accompany>();
        adapter=new AccompanyConfirmListAdapter(getApplicationContext(),AccompanyList);
        listView.setAdapter(adapter);


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray=jsonObject.getJSONArray("response");

            int count=0;
            String userID;
            String accompanyID;
            String accompanyPlace;
            String accompanyDate;
            String accompanyTime;
            String accompanyWork;
            String helperID;
            String status;


            while(count<jsonArray.length()){
                JSONObject object=jsonArray.getJSONObject(count);
                userID=object.getString("userID");
                accompanyID =object.getString("accompanyID");
                accompanyPlace=object.getString("accompanyPlace");
                accompanyDate=object.getString("matchingTime");
                accompanyTime=object.getString("accompanyWorkTime");
                accompanyWork=object.getString("accompanyWork");
                helperID = object.getString("helperID");
                status = object.getString("status");


                Accompany accompany=new Accompany(userID,accompanyID,accompanyPlace,accompanyDate,accompanyTime,accompanyWork,helperID,status);

                AccompanyList.add(accompany);
                count++;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
