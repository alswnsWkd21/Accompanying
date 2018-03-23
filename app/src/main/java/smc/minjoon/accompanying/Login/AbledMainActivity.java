package smc.minjoon.accompanying.Login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.BackgroundTask;
import smc.minjoon.accompanying.MainAccompanyButton.Reservation.LocationReceiverService;
import smc.minjoon.accompanying.MainSettingButton.HelperSettingsActivity;
import smc.minjoon.accompanying.MainSosButton.InformSosHelper;
import smc.minjoon.accompanying.R;

public class AbledMainActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";
    double longitude;
    double latitude;
    String helperID;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_helper);

        //트윈 애니메이션

        ImageView iv = (ImageView) findViewById(R.id.ball);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        iv.startAnimation(animation);
        onCoachMark();
        ImageView mainbtn=(ImageView)findViewById(R.id.mainBtn);
        ImageButton btn01 = (ImageButton ) findViewById(R.id.btn01);
        ImageButton btn02 = (ImageButton ) findViewById(R.id.btn02);
        ImageButton btn03 = (ImageButton ) findViewById(R.id.btn03);
        final SessionManager sessionmanager = new SessionManager(AbledMainActivity.this);
        sessionmanager.checkLogin("helper","helper");
        helperID=sessionmanager.getKeyId();
        //햄버거 버튼
        final ImageButton hamburger=(ImageButton)findViewById(R.id.hamburger);


        hamburger.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(AbledMainActivity.this, hamburger);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String a =String.valueOf(item);
                        if(a.equals("도움말")){

                            Intent i = new Intent(AbledMainActivity.this, InformSosHelper.class);
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

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        grantExternalStoragePermission();
        Intent location = new Intent(AbledMainActivity.this, LocationReceiverService.class);
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
                                Log.v("refresh","리프레쉬됨");
                                sessionmanager.editor.putString("ok", "refreshed");
                                sessionmanager.editor.commit();
                            } else {
                                Log.v("refresh","안됨");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                SessionRequest sessionRequest1 = new SessionRequest(kind,id, token, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AbledMainActivity.this);
                queue.add(sessionRequest1);

            }



        mainbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new BackgroundTask(AbledMainActivity.this).execute();
            }
        });
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(AbledMainActivity.this, TimeSelectActivity.class);
                startActivity(i);
//                Intent i = new Intent(AbledMainActivity.this, TmapActivity.class);
//                startActivity(i);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask(AbledMainActivity.this,helperID).execute();
            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AbledMainActivity.this, HelperSettingsActivity.class);
                startActivity(i);
            }
        });


//        new AsyncTask<String, Void, Void>() {
//            @Override
//            protected Void doInBackground(String... params) {
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//
//                            if (success) {
//                                Log.v("location", "성공");
//                                Log.v("location2", sessionmanager.getKeyId());
//                            } else {
////                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
////                                        builder.setMessage("회원 등록에 실패했습니다.")
////                                                .setNegativeButton("다시 시도", null)
////                                                .create()
////                                                .show();
//                                Log.v("location", "실패");
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                };
//                SessionRequest tokenResetRequest = new SessionRequest(helperID, latitude, longitude, responseListener );
//                RequestQueue queue= Volley.newRequestQueue(AbledMainActivity.this);
//                queue.add(tokenResetRequest);
//                return null;
//            }
//        }.execute();
    }



//    public class BackgroundTask extends AsyncTask<Void,Void,String>
//    {
//        String target;
//        String param;
//        public BackgroundTask(){
//
//        }
//        public BackgroundTask(String helperID){
//            param = helperID;
//        }
//        @Override
//        protected void onPreExecute(){
//            target="http://syoung1394.cafe24.com/ListConfirm.php?"+ param;
//        }
//        @Override
//        protected String doInBackground(Void... voids) {
//            try{
//                URL url =new URL(target);
//                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
//                InputStream inputStream=httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String temp;
//                StringBuilder stringBuilder=new StringBuilder();
//                while((temp=bufferedReader.readLine())!=null)
//                {
//                    stringBuilder.append(temp+"\n");
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//
//        @Override
//        public void onProgressUpdate(Void... values){
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        public void onPostExecute(String result){
//            Intent intent=new Intent(AbledMainActivity.this, ListActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("userList", result);
//            AbledMainActivity.this.startActivity(intent);
//        }
//    }
    private void grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                Log.v(TAG, "Permission is granted");
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);

                //만약 두개의 퍼미션이 전부 허락되어있다면 true반환
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(AbledMainActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(AbledMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("도우미가 도움을 효율적으로 주기위해서 한 가지의 권한이 필요합니다\n\n" +
                        "위치정보:  장애인분들이 자신에서 가까운 위치에 있는 도우미를 찾아 도움을 요청하기 위해서는 도우미의 위치정보가 필요합니다.");
                alert.setCancelable(false);


                alert.show();
            }
        } else {
            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "External Storage Permission is Grant ");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                AlertDialog.Builder alert = new AlertDialog.Builder(AbledMainActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("권한을 수락하여 도우미에 대한 동행길 기능이 활성화됩니다.");
                alert.setCancelable(false);
                alert.show();
            }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(AbledMainActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("권한을 거절하여 지도에 대한 기능이 제한됩니다.\n" +
                        "도우미에 대한 기능을 적절히 수행할 수 없습니다.");
                alert.setCancelable(false);
                alert.show();
            }
        }
    }
    private final LocationListener mLocationListener = new LocationListener() {

        float accuracy;
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            accuracy = location.getAccuracy();

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

    public void onCoachMark(){
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_help_helper);
        dialog.setCanceledOnTouchOutside(true);
        View masterView = dialog.findViewById(R.id.help);
//        masterView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
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
