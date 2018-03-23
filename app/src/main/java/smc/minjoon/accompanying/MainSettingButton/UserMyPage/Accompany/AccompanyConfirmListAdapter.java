package smc.minjoon.accompanying.MainSettingButton.UserMyPage.Accompany;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Accompany;
import smc.minjoon.accompanying.R;

import static smc.minjoon.accompanying.R.layout.confirm_reservation_item;

/**
 * Created by SY on 2018-01-07.
 */

public class AccompanyConfirmListAdapter extends BaseAdapter {
    private Context context;
    private List<Accompany> AccompanyList;
    String userID1;
    String accompanyID1;
    String accompanyPlace1;
    String accompanyDate1;
    String accompanyTime1;
    String accompanyWork1;
    String helperID1;
    String status1;
    public AccompanyConfirmListAdapter(Context context, List<Accompany> AccompanyList){
        this.context=context;
        this.AccompanyList=AccompanyList;
    }

    @Override
    public int getCount() {
        return AccompanyList.size();
    }

    @Override
    public Object getItem(int i) {
        return AccompanyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(context, confirm_reservation_item, null);
        TextView userID = (TextView)v. findViewById(R.id.userID) ;
        TextView ReservationID=(TextView)v.findViewById(R.id.reservationID);
        TextView ReservationPlace=(TextView)v.findViewById(R.id.reservationPlace);
        TextView ReservationDate=(TextView)v.findViewById(R.id.reservationDate);
        TextView ReservationTime=(TextView)v.findViewById(R.id.reservationTime);
        TextView ReservationWork=(TextView)v.findViewById(R.id.reservationWork);
        Button statusBtn = (Button)v.findViewById(R.id.statusBtn);
        LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.bg2);

        userID1 = AccompanyList.get(i).getUserID();
        accompanyID1 = AccompanyList.get(i).getAccompanyID();
        accompanyPlace1 = AccompanyList.get(i).getAccompanyPlace();
        accompanyDate1 = AccompanyList.get(i).getAccompanyDate();
        accompanyTime1 = AccompanyList.get(i).getAccompanyTime();
        accompanyWork1 = AccompanyList.get(i).getAccompanyWork();
        helperID1 = AccompanyList.get(i).getHelperID();
        status1 = AccompanyList.get(i).getStatus();
        ReservationID.setText(accompanyID1);
        ReservationPlace.setText(accompanyPlace1);
        ReservationDate.setText(accompanyDate1);
        ReservationTime.setText(accompanyTime1);
        ReservationWork.setText(accompanyWork1);
        statusBtn.setTag(AccompanyList.get(i));
            statusBtn.setText(status1);
            statusBtn.setBackgroundColor(Color.parseColor("#D4F4FA"));
            statusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Accompany name = (Accompany) v.getTag();
                    Intent intent=new Intent(context,ConfirmUserListClickedActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("userID",name.getUserID());
                    intent.putExtra("accompanyID",name.getAccompanyID());
                    intent.putExtra("accompanyPlace",name.getAccompanyPlace());
                    intent.putExtra("accompanyDate",name.getAccompanyDate());
                    intent.putExtra("accompanyTime",name.getAccompanyTime());
                    intent.putExtra("accompanyWork",name.getAccompanyWork());
                    intent.putExtra("helperID", name.getHelperID());
                    intent.putExtra("status", name.getStatus());

                    context.startActivity(intent);
                }
            });

        v.setTag(AccompanyList.get(i).getAccompanyPlace());
        return v;
    }
}
