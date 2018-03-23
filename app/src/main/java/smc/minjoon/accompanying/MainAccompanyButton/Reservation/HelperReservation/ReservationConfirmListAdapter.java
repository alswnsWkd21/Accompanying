package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperReservation;

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

import static smc.minjoon.accompanying.R.layout.confirm_reservation_item2;

/**
 * Created by SY on 2018-01-07.
 */

public class ReservationConfirmListAdapter extends BaseAdapter {
    private Context context;
    private List<Reservation> ReservationList;
    String userID2;
    String reservationID2;
    String reservationPlace2;
    String reservationDate2;
    String reservationTime2;
    String reservationWork2;
    String helperID2;
    String status2;
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
        View v=View.inflate(context, confirm_reservation_item2, null);
        TextView userID = (TextView)v. findViewById(R.id.userID) ;
        TextView ReservationID=(TextView)v.findViewById(R.id.reservationID2);
        TextView ReservationPlace=(TextView)v.findViewById(R.id.reservationPlace2);
        TextView ReservationDate=(TextView)v.findViewById(R.id.reservationDate2);
        TextView ReservationTime=(TextView)v.findViewById(R.id.reservationTime2);
        TextView ReservationWork=(TextView)v.findViewById(R.id.reservationWork2);
        Button statusBtn = (Button)v.findViewById(R.id.statusBtn1);
        LinearLayout linearLayout = (LinearLayout)v.findViewById(R.id.bg2);



//        TextView ReservationHelperID=(TextView)v.findViewById(R.id.h_id);
        userID2 = ReservationList.get(i).getUserID();
        reservationID2 = ReservationList.get(i).getReservationID();
        reservationPlace2 = ReservationList.get(i).getReservationPlace();
        reservationDate2 = ReservationList.get(i).getReservationDate();
        reservationTime2 = ReservationList.get(i).getReservationTime();
        reservationWork2 = ReservationList.get(i).getReservationWork();
        helperID2 = ReservationList.get(i).getHelperID();
        status2 = ReservationList.get(i).getStatus();
        ReservationID.setText(reservationID2);
        ReservationPlace.setText(reservationPlace2);
        ReservationDate.setText(reservationDate2);
        ReservationTime.setText(reservationTime2);
        ReservationWork.setText(reservationWork2);
        statusBtn.setTag(ReservationList.get(i));

            statusBtn.setBackgroundColor(Color.parseColor("#D4F4FA"));
            statusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Reservation name = (Reservation)v.getTag();
                    Intent intent=new Intent(context,ListDetailClickedActivity.class);
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
