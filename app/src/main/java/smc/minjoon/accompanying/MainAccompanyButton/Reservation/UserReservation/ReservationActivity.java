package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.skp.Tmap.TMapAddressInfo;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.ReservationRequest;
import smc.minjoon.accompanying.R;
import smc.minjoon.accompanying.TmapLibrary;

public class ReservationActivity extends AppCompatActivity {

    private AlertDialog dialog;
    LocationManager lm;
    private String TAG = "permissionstatus";

    TMapView tmapview1;
    Location userlocation;
    double longitude;
    double latitude;
    EditText place;
    ImageButton imgbtn01;
    TMapData tmapdata = new TMapData();
    Handler handler = new Handler();
    TmapLibrary tmaplibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        final EditText date = (EditText) findViewById(R.id.date);
        final EditText time = (EditText) findViewById(R.id.time);
        place= (EditText) findViewById(R.id.place);
        final EditText work = (EditText) findViewById(R.id.work);
        final EditText editsearch = (EditText) findViewById(R.id.editsearch);
        ImageButton btnsearch = (ImageButton) findViewById(R.id.btnsearch);
        imgbtn01 = (ImageButton) findViewById(R.id.imgbtn1);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        grantExternalStoragePermission();
        Button reservationBtn = (Button) findViewById(R.id.reservationBtn);
        tmapview1 = new TMapView(ReservationActivity.this);
        tmapview1.setCompassMode(true);
        tmaplibrary = new TmapLibrary(this, tmapview1);
        tmapview1.setSKPMapApiKey("60842859-875b-31b3-8fa1-944bcfd3d2d6");
        LinearLayout mapViewContainer = (LinearLayout) findViewById(R.id.map_view);
        mapViewContainer.addView(tmapview1);

//        if (isGrantStorage) {
//// 일반처리.   // MapView 객체생성 및 API Key 설정
//            Toast.makeText(this, "권한을 허락하셨습니다.", Toast.LENGTH_SHORT).show();
//
//            lm.removeUpdates( mLocationListener );    // Stop the update if it is in progress.
//            lm.requestLocationUpdates( LocationManager.GPS_PROVIDER , 100, 1, mLocationListener );
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
//        }
        imgbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview1.setTrackingMode(true);
            }
        });
//            지도 클릭하면 클릭한 곳 마커 표시 그리고 그 클릭한 곳 주소 가져오기

        tmapview1.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, final TMapPoint tMapPoint, PointF pointF) {
//tmapview에다가 클릭했을 때 생성하게 하는 코드
//                    TMapMarkerItem marker = new TMapMarkerItem();
//                    marker.setTMapPoint(tMapPoint);
//                    marker.setVisible(TMapMarkerItem.VISIBLE);
//                    Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.target);
//                    marker.setIcon(bitmap);
                tmapview1.setLocationPoint(tMapPoint.getLongitude(), tMapPoint.getLatitude());
                tmapview1.setCenterPoint(tMapPoint.getLongitude(), tMapPoint.getLatitude());
//                tmapview1.setTrackingMode(true);
//                tmapview1.setIconVisibility(true);
//                    tmapview1.addMarkerItem("click",marker);
//                리버스 지오코딩(gps좌표를 주소로 변환)
                    tmapdata.reverseGeocoding(tMapPoint.getLatitude(), tMapPoint.getLongitude(), "A03", new TMapData.reverseGeocodingListenerCallback() {
                    @Override
                    public void onReverseGeocoding(final TMapAddressInfo addressInfo) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                            try{
                                place.setText(addressInfo.strFullAddress);

                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }

                            }
                        });
                    }
                });
                return false;
            }
            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {

                return false;
            }
        });
        //       현재 시간 구하기
        Calendar calendar = Calendar.getInstance();
        final Calendar calendar2 = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date2 = calendar.get(Calendar.DATE);
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

//    검색버튼을 눌렀을 때 검색 수행한 후 마커 표시하기

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview1.removeAllMarkerItem();
                tmaplibrary.searchPOI(editsearch.getText().toString(), latitude, longitude);
            }
        });
        final DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar2.set(year, month, dayOfMonth);
                        int nweek =calendar2.get(Calendar.DAY_OF_WEEK);
                String week = null;
                switch (nweek){
                    case 1:
                        week= "일요일";
                        break;
                    case 2:
                        week= "월요일";
                        break;
                    case 3:
                        week= "화요일";
                        break;
                    case 4:
                        week= "수요일";
                        break;
                    case 5:
                        week= "목요일";
                        break;
                    case 6:
                        week= "금요일";
                        break;
                    case 7:
                        week= "토요일";
                        break;

                }
                        date.setText(String.valueOf(year)+"년\t"+String.valueOf(month+1)+"월\t"+String.valueOf(dayOfMonth)+"일\t"+ week );
            }
        }, year, month, date2);
        final TimePickerDialog timeDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String day2 = null;
                if(hourOfDay<12){
                    day2 ="오전";
                }else if(hourOfDay==12){
                    day2 ="오후";
                }else{
                    day2 ="오후";
                    hourOfDay = hourOfDay-12;
                }
                time.setText(day2 +"\t"+String.valueOf(hourOfDay)+"시\t"+String.valueOf(minute)+"분\t");
            }
        },hour,minute,false);
        final SessionManager sessionmanager = new SessionManager(ReservationActivity.this);
        sessionmanager.checkLogin("user","user");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String reservationPlace = place.getText().toString();
                final String reservationDate = date.getText().toString();
                final String reservationTime = time.getText().toString();
                final String reservationWork = work.getText().toString();
                HashMap<String, String> user = sessionmanager.getUserDetails();
                String u_id=user.get("id");
                if ( reservationPlace.equals("") || reservationDate.equals("") || reservationTime.equals("") || reservationWork.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                    dialog = builder.setMessage("입력되지 않은 내용이 있습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setMessage("예약에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(ReservationActivity.this, ReservationSelectActivity.class);
                                                ReservationActivity.this.startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setMessage("예약에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                ReservationRequest reservationRequest = new ReservationRequest(u_id,reservationPlace, reservationDate, reservationTime, reservationWork, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationActivity.this);
                queue.add(reservationRequest);
            }
        });

    }

//    위치변경 시 리스너
    private final LocationListener mLocationListener = new LocationListener() {

        float accuracy;
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            accuracy = location.getAccuracy();
            userlocation = location;
            tmapview1.setLocationPoint(longitude, latitude);
            tmapview1.setCenterPoint(longitude, latitude);
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.location);
            tmapview1.setIcon(bitmap);
            tmapview1.setTrackingMode(true);
            tmapview1.setIconVisibility(true);
            tmapview1.setZoomLevel(17);



            tmapdata.reverseGeocoding(latitude, longitude, "A03", new TMapData.reverseGeocodingListenerCallback() {
                @Override
                public void onReverseGeocoding(final TMapAddressInfo addressInfo) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            place.setText(addressInfo.strFullAddress);
                        }
                    });
                }
            });
//            place.setText("현재위치입니다.");
            lm.removeUpdates( this );
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
            }else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReservationActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(ReservationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("동행길을 효율적으로 사용하기 위해서 한 가지의 권한이 필요합니다 \n\n" +
                        "위치정보:  현재의 위치를 표시하기 위해 위치정보가 필요합니다");
                alert.setCancelable(false);
                alert.show();
            }
        } else {
//            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "External Storage Permission is Grant ");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);

        }
    }



}
