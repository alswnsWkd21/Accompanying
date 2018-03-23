package smc.minjoon.accompanying.MainSettingButton.UserMyPage.Reservation;

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

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Reservation;
import smc.minjoon.accompanying.R;

import static smc.minjoon.accompanying.R.layout.confirm_reservation_item;

/**
 * Created by SY on 2018-01-07.
 */

public class ReservationConfirmListAdapter extends BaseAdapter {
    private Context context;
    private List<Reservation> ReservationList;
    String userID1;
    String reservationID1;
    String reservationPlace1;
    String reservationDate1;
    String reservationTime1;
    String reservationWork1;
    String helperID1;
    String status1;
    public ReservationConfirmListAdapter(Context context, List<Reservation> ReservationList){
        this.context=context;
        this.ReservationList=ReservationList;
    }

    @Override
    public int getCount() {
        return ReservationList.size();
    }

    @Override
    public Object getItem(int i) {
        return ReservationList.get(i);
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

        userID1 = ReservationList.get(i).getUserID();
        reservationID1 = ReservationList.get(i).getReservationID();
        reservationPlace1 = ReservationList.get(i).getReservationPlace();
        reservationDate1 = ReservationList.get(i).getReservationDate();
        reservationTime1 = ReservationList.get(i).getReservationTime();
        reservationWork1 = ReservationList.get(i).getReservationWork();
        helperID1 = ReservationList.get(i).getHelperID();
        status1 = ReservationList.get(i).getStatus();
        ReservationID.setText(reservationID1);
        ReservationPlace.setText(reservationPlace1);
        ReservationDate.setText(reservationDate1);
        ReservationTime.setText(reservationTime1);
        ReservationWork.setText(reservationWork1);
        statusBtn.setTag(ReservationList.get(i));
            statusBtn.setText(status1);
            statusBtn.setBackgroundColor(Color.parseColor("#D4F4FA"));
            statusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Reservation name = (Reservation)v.getTag();
                    Intent intent=new Intent(context,ConfirmUserListClickedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("userID",name.getUserID());
                    intent.putExtra("reservationID",name.getReservationID());
                    intent.putExtra("reservationPlace",name.getReservationPlace());
                    intent.putExtra("reservationDate",name.getReservationDate());
                    intent.putExtra("reservationTime",name.getReservationTime());
                    intent.putExtra("reservationWork",name.getReservationWork());
                    intent.putExtra("helperID", name.getHelperID());
                    intent.putExtra("status", name.getStatus());

                    context.startActivity(intent);
                }
            });

        v.setTag(ReservationList.get(i).getReservationPlace());
        return v;
    }
}
