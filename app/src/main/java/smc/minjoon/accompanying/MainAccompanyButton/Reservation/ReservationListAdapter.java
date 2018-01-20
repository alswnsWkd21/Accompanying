package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import smc.minjoon.accompanying.R;

import static smc.minjoon.accompanying.R.layout.reservation;

/**
 * Created by SY on 2018-01-07.
 */

public class ReservationListAdapter extends BaseAdapter {
    private Context context;
    private List<Reservation> ReservationList;

    public ReservationListAdapter(Context context, List<Reservation> ReservationList){
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
        View v=View.inflate(context, reservation,null);
        TextView userID = (TextView)v. findViewById(R.id.userID) ;
        TextView ReservationID=(TextView)v.findViewById(R.id.reservationID);
        TextView ReservationPlace=(TextView)v.findViewById(R.id.reservationPlace);
        TextView ReservationDate=(TextView)v.findViewById(R.id.reservationDate);
        TextView ReservationTime=(TextView)v.findViewById(R.id.reservationTime);
        TextView ReservationWork=(TextView)v.findViewById(R.id.reservationWork);

        ReservationID.setText(ReservationList.get(i).getReservationID());
        ReservationPlace.setText(ReservationList.get(i).getReservationPlace());
        ReservationDate.setText(ReservationList.get(i).getReservationDate());
        ReservationTime.setText(ReservationList.get(i).getReservationTime());
        ReservationWork.setText(ReservationList.get(i).getReservationWork());

        v.setTag(ReservationList.get(i).getReservationPlace());
        return v;
    }
}
