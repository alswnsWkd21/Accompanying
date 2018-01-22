package smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;

import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.ChattingRequest;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.ChattingRequest2;
import smc.minjoon.accompanying.R;
import smc.minjoon.accompanying.TmapLibrary;

public class ChattingActivity extends AppCompatActivity {
    LocationManager lm;
    TMapView tmapview1;
    ListView listView;
    EditText editText;
    Button sendButton;;
    double longitude;
    double latitude;
    String accompanyID;
    ArrayAdapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    Location userlocation;
    TMapData tmapdata = new TMapData();
    ImageButton imgbtn01;
    TmapLibrary tmaplibrary;
    String tar_id;
    String tar_name;
    double tar_lat;
    double tar_long;
    String me_id;
    String me_name;
    static int room =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_activity);
        imgbtn01 = (ImageButton) findViewById(R.id.imgbtn1);
        listView = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.edittext);
        sendButton = (Button) findViewById(R.id.button);
        room +=1;
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        grantExternalStoragePermission();
        tmapview1 = new TMapView(ChattingActivity.this);
        tmapview1.setCompassMode(true);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);
        tmaplibrary = new TmapLibrary(this, tmapview1);
        final SessionManager sessionmanager = new SessionManager(ChattingActivity.this);
        me_id = sessionmanager.getKeyId();
        me_name = sessionmanager.getKeyName();
        tmapview1.setSKPMapApiKey("60842859-875b-31b3-8fa1-944bcfd3d2d6");
        LinearLayout mapViewContainer = (LinearLayout) findViewById(R.id.map_view);
        mapViewContainer.addView(tmapview1);
        if(sessionmanager.getKeyKind().equals("helper")){
            Intent intent = getIntent();
            tar_id =intent.getStringExtra("userID");
            tar_name=intent.getStringExtra("userName");
            tar_lat=intent.getDoubleExtra("userLat",0);
            tar_long =intent.getDoubleExtra("userlong",0);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            accompanyID = jsonResponse.getString("accompanyID");
                            databaseReference.child(accompanyID).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                                    adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) { }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                                @Override
                                public void onCancelled(DatabaseError databaseError) { }
                            });


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            ChattingRequest2 chattingRequest2 = new ChattingRequest2(me_id, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ChattingActivity.this);
            queue.add(chattingRequest2);

        }else {
            Intent intent = getIntent();
            final String helperID = intent.getStringExtra("helperID");
            accompanyID = intent.getStringExtra("AccompanyID");
            databaseReference.child(accompanyID).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                    adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            tar_id = jsonResponse.getString("helperID");
                            tar_name = jsonResponse.getString("helpName");
                            tar_lat = jsonResponse.getDouble("helperLat");
                            tar_long = jsonResponse.getDouble("helperlong");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
                ChattingRequest chattingRequest = new ChattingRequest(helperID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChattingActivity.this);
                queue.add(chattingRequest);

        }


        me_id = sessionmanager.getKeyId();
        me_name = sessionmanager.getKeyName();



        imgbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview1.setTrackingMode(true);
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChatData chatData = new ChatData(me_id, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
                databaseReference.child(accompanyID).push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                editText.setText("");
            }
        });




    }


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
            TMapPoint endPoint = new TMapPoint(tar_lat, tar_long);
        /*여기수정*/

            TMapPoint startPoint = new TMapPoint(latitude, longitude);
            tmaplibrary.searchRoute(startPoint,endPoint );

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
                AlertDialog.Builder alert = new AlertDialog.Builder(ChattingActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(ChattingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("동행길을 효율적으로 사용하기 위해서한 가지의 권한이 필요합니다 \n\n" +
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
