package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import smc.minjoon.accompanying.Login.MainActivity;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting.ChattingActivity;
import smc.minjoon.accompanying.R;

public class AccompanyLoadingActivity extends AppCompatActivity {

    Timer timer;
    TimerTask timertask;
    String AccompanyID =null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        Log.v("three", "데이터지우기성공");
                        Intent i = new Intent(AccompanyLoadingActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }else{
                        Log.v("three", "데이터지우기실패");
                        Intent i = new Intent(AccompanyLoadingActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        AccompanyLoadingThreeRequest accompanyloadingThreeRequest = new AccompanyLoadingThreeRequest(AccompanyID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AccompanyLoadingActivity.this);
        queue.add(accompanyloadingThreeRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accompanyloading);

        //트윈 애니메이션
        ImageView loading = (ImageView) findViewById(R.id.loading);
        Button canclebtn1 = (Button)findViewById(R.id.cancelbtn1);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        loading.startAnimation(animation);

        Intent intent = getIntent();
        String id =intent.getExtras().getString("id");
        String name =intent.getExtras().getString("name");
        String work =intent.getExtras().getString("work");
        String place =intent.getExtras().getString("place");
        String phone =intent.getExtras().getString("phone");
        double lat =intent.getExtras().getDouble("lat");
        double long1 =intent.getExtras().getDouble("long");


        canclebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            Log.v("three", "데이터지우기성공");
                            Intent i = new Intent(AccompanyLoadingActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else{
                            Log.v("three", "데이터지우기실패");
                            Intent i = new Intent(AccompanyLoadingActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                AccompanyLoadingThreeRequest accompanyloadingThreeRequest = new AccompanyLoadingThreeRequest(AccompanyID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AccompanyLoadingActivity.this);
                queue.add(accompanyloadingThreeRequest);
                timer.cancel();
//                여기다가는 데이터베이스 지우는 거와 함께 지웠으면 메인으로 다시 intent이동하는 코드를 넣어야 한다.
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                JSONObject jsonResponse = new JSONObject(response);
                    AccompanyID = jsonResponse.getString("success");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        AccompanyLoadingRequest accompanyloadingRequest = new AccompanyLoadingRequest(id,name,place, work,phone, String.valueOf(lat), String.valueOf(long1), responseListener);
        RequestQueue queue = Volley.newRequestQueue(AccompanyLoadingActivity.this);
        queue.add(accompanyloadingRequest);

//        여기다가는 매순간마다 helper가 등록되었는지 확인하는거 설치!!
        timer = new Timer(true);
        timertask = new TimerTask() {
            @Override
            public void run() {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    String helperID = jsonResponse.getString("helperID");

                                    if (success) {
                                        if(helperID != "null"){
//                                          일단 테스트
                                            Log.v("helperID", helperID);
                                            timer.cancel();
                                            Intent i = new Intent(AccompanyLoadingActivity.this, ChattingActivity.class);
                                            i.putExtra("helperID" ,helperID);
                                            i.putExtra("AccompanyID", AccompanyID);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                        }else{
                                            Log.v("helperID", "null입니다.");
                                        }
                                    } else {
                                        Log.v("helperID", "실패입니다.");
//                                        확인해서 있으면 채팅창으로 넘어가야 한다.
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                        builder.setMessage("회원 등록에 실패했습니다.")
//                                                .setNegativeButton("다시 시도", null)
//                                                .create()
//                                                .show();
//
//                                        Log.v("location", "실패");
                                    }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        };
                        AccompanyLoadingTwoRequest accompanyLoadingTwoRequest = new AccompanyLoadingTwoRequest(AccompanyID,responseListener);
                        RequestQueue queue= Volley.newRequestQueue(AccompanyLoadingActivity.this);
                        queue.add(accompanyLoadingTwoRequest);
            }
        };

        timer.schedule(timertask, 3000, 3000 );

    }
}
