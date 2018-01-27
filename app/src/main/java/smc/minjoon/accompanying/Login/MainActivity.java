package smc.minjoon.accompanying.Login;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation.AccompanySelectActivity;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.LocationReceiverService;
import smc.minjoon.accompanying.MainMapButton.TmapActivity;
import smc.minjoon.accompanying.MainSettingButton.SettingsActivity;
import smc.minjoon.accompanying.MainSettingButton.SettingsosActivity;
import smc.minjoon.accompanying.MainSosButton.InformSos;
import smc.minjoon.accompanying.R;

public class MainActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";
    ImageButton hamburger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        onCoachMark();
        //트윈 애니메이션
        ImageView ivbtn = (ImageView) findViewById(R.id.imageView);

        ImageView iv = (ImageView) findViewById(R.id.ball);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        iv.startAnimation(animation);

        ImageButton btn01 = (ImageButton ) findViewById(R.id.btn01);
        ImageButton btn02 = (ImageButton ) findViewById(R.id.btn02);
        ImageButton btn03 = (ImageButton ) findViewById(R.id.btn03);
        final SessionManager sessionmanager = new SessionManager(MainActivity.this);
        sessionmanager.checkLogin("user","user");
        //햄버거 버튼
         hamburger=(ImageButton)findViewById(R.id.hamburger);



        hamburger.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, hamburger);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        String a =String.valueOf(item);
                        if(a.equals("도움말")){


                            Intent i = new Intent(MainActivity.this, InformSos.class);
                            startActivity(i);
                        }
                        if(a.equals("로그아웃")){
                            sessionmanager.logoutUser();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });//closing the setOnClickListener method


        grantExternalStoragePermission();
        Intent location = new Intent(MainActivity.this, LocationReceiverService.class);
        startService(location);



        if(sessionmanager.getKeyOk().equals("refresh")){
            String id =sessionmanager.getKeyId();
            String token=sessionmanager.getKeyToken();
            String kind=sessionmanager.getKeyKind();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            sessionmanager.editor.putString("ok", "refreshed");
                            sessionmanager.editor.commit();
                            Log.v("refresh","리프레쉬됨");
                        } else {
                            Log.v("refresh","안됨");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            };
            SessionRequest sessionRequest1 = new SessionRequest(kind,id, token, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(sessionRequest1);

        }

        ivbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AccompanySelectActivity.class);
                startActivity(i);
            }
        });
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, TmapActivity.class);
        startActivity(i);
        }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsosActivity.class);
                startActivity(i);
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

    }
    private void grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    &&(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)) {

                //만약 두개의 퍼미션이 전부 허락되어있다면 true반환
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("동행길 앱을 효율적으로 사용하기 위해서 몇 가지의 권한이 필요합니다 \n\n" +
                        "위치정보:  도움요청을 위해 위치정보가 필요합니다 \n\n"+
                        "문자:  도움문자를 보내기위해 권한이 필요합니다\n\n" +
                        "PHONE:  도움길기능에서 시설에 전화를 연결하기 위해 필요합니다.");
                alert.setCancelable(false);
                alert.show();
            }
        } else {
//            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "External Storage Permission is Grant ");


        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (Build.VERSION.SDK_INT >= 23) {
//            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialo, int which) {
//                        dialo.dismiss();     //닫기
//                    }
//                });
//                alert.setMessage("권한을 수락하여 도우미에 대한 동행길 기능이 활성화됩니다.");
//                alert.setCancelable(false);
//                alert.show();
//            }else{
//                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
//                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialo, int which) {
//                        dialo.dismiss();     //닫기
//                    }
//                });
//                alert.setMessage("권한을 거절하여 지도에 대한 기능이 제한됩니다.\n" +
//                        "도우미에 대한 기능을 적절히 수행할 수 없습니다.");
//                alert.setCancelable(false);
//                alert.show();
//            }
//        }
//    }
public void onCoachMark(){
    final Dialog dialog= new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setContentView(R.layout.popup_help);
    dialog.setCanceledOnTouchOutside(true);
    View masterView = dialog.findViewById(R.id.help);
//    masterView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            dialog.dismiss();
//        }
//    });
    Button btn = (Button) dialog.findViewById(R.id.button);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    });
    dialog.show();
}
    }
