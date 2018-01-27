package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import smc.minjoon.accompanying.Login.MainActivity;
import smc.minjoon.accompanying.R;

public class ReservationTimeCheck extends AppCompatActivity {
    String starthour;
    String startminute;
    String startTime;
    String endTime;

    String endhour;
    String endminute;
    String reservationWorkTime;
    String reservationID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_time_check);
        final EditText starttime = (EditText) findViewById(R.id.starttime);
        final EditText endtime = (EditText) findViewById(R.id.endtime);
        Intent i = getIntent();
         reservationID = i.getStringExtra("reservationID");
        Calendar calendar = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date2 = calendar.get(Calendar.DATE);
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        final TimePickerDialog timeDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String day2 = null;
                starthour= String.valueOf(hourOfDay);
                startminute= String.valueOf(minute);
                if(hourOfDay<12){
                    day2 ="오전";
                }else if(hourOfDay==12){
                    day2 ="오후";
                }else{
                    day2 ="오후";
                    hourOfDay = hourOfDay-12;
                }
                startTime = day2 +"\t"+String.valueOf(hourOfDay)+"시\t"+String.valueOf(minute)+"분\t";
                starttime.setText(day2 +"\t"+String.valueOf(hourOfDay)+"시\t"+String.valueOf(minute)+"분\t");
            }
        },hour,minute,false);
        final TimePickerDialog timeDialog2= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                endhour= String.valueOf(hourOfDay);
                endminute= String.valueOf(minute);
                String day2 = null;
                if(hourOfDay<12){
                    day2 ="오전";
                }else if(hourOfDay==12){
                    day2 ="오후";
                }else{
                    day2 ="오후";
                    hourOfDay = hourOfDay-12;
                }
                endTime=day2 +"\t"+String.valueOf(hourOfDay)+"시\t"+String.valueOf(minute)+"분\t";
                endtime.setText(day2 +"\t"+String.valueOf(hourOfDay)+"시\t"+String.valueOf(minute)+"분\t");
            }
        },hour,minute,false);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog2.show();
            }
        });





        Button reservationWorkTimeBtn = (Button) findViewById(R.id.reservationWorkTimeBtn);
        reservationWorkTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.KOREA);
                try {
                    Date d1 = f.parse(endhour+":"+endminute);
                    Date d2 = f.parse(starthour+":"+startminute);
                    long diff = d1.getTime() - d2.getTime();
                    int h = (int) (diff / 3600000);
                    int m = (int) ((diff-(3600000*h))/60000);

                    reservationWorkTime= String.valueOf(h)+"시간"+ String.valueOf(m)+"분";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                 AlertDialog.Builder alert = new AlertDialog.Builder(ReservationTimeCheck.this);
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialo, int which) {
                                        dialo.dismiss();     //닫기
                                        Intent a = new Intent(ReservationTimeCheck.this, MainActivity.class);
                                        startActivity(a);
                                    }
                                });
                                alert.setMessage("동행이 성공적으로 마무리 되었습니다. ");
                                alert.setCancelable(false);
                                alert.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                ReservationTimeRequest reservationTimeRequest= new ReservationTimeRequest(reservationID,reservationWorkTime,startTime, endTime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationTimeCheck.this);
                queue.add(reservationTimeRequest);
            }
        });

    }
}
