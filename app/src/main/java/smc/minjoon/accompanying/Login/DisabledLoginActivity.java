package smc.minjoon.accompanying.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import smc.minjoon.accompanying.R;

public class DisabledLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_disabled);


        final EditText idText=(EditText)findViewById(R.id.idText);
        final EditText passwordText=(EditText)findViewById(R.id.passwordText);

        final Button loginBtn=(Button)findViewById(R.id.loginBtn);
        final TextView registerBtn=(TextView)findViewById(R.id.registerBtn);
        final SessionManager sessionmanager = new SessionManager(DisabledLoginActivity.this);
        AlertDialog.Builder alert = new AlertDialog.Builder(DisabledLoginActivity.this);

//        sessionmanager.checkLogin("user","user");
        if(sessionmanager.getKeyKind() !=null && !sessionmanager.getKeyKind().equals("")){
            if(sessionmanager.getKeyKind().equals("user") && sessionmanager.isLoggedIn()){
                Intent i = new Intent(DisabledLoginActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Add new Flag to start new Activity

// Staring Login Activity
                startActivity(i);
            }else{
                if(sessionmanager.getKeyKind().equals("helper") && sessionmanager.isLoggedIn()){
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sessionmanager.logoutUser();
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("이미 도우미로 로그인되어있어 도우미가 로그아웃됩니다.");
                    alert.setCancelable(false);
                    alert.show();


                }
            }
        }



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(DisabledLoginActivity.this,DisabledRegisterActivity.class);
                DisabledLoginActivity.this.startActivity(registerIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID=idText.getText().toString();
                final String userPassword=passwordText.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                String userID=jsonResponse.getString("userID");
                                String userName=jsonResponse.getString("userName");
                                String userToken=jsonResponse.getString("userToken");
                                String userPhone=jsonResponse.getString("userPhone");
//                                세션매니저 로직 변경으로 인한 create대신 login
                                sessionmanager.editor.putString("name", userName);
                                sessionmanager.editor.putString("id", userID);
                                sessionmanager.editor.putString("kind", "user");
                                sessionmanager.editor.putBoolean("IsLoggedIn", true);
                                sessionmanager.editor.putString("phone",userPhone );
                                sessionmanager.editor.commit();
                                Intent intent=new Intent(DisabledLoginActivity.this,MainActivity.class);
//                              intent.putExtra("userID",userID);
//                              intent.putExtra("userPassword", userPassword);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                DisabledLoginActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(DisabledLoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                DisabledLoginRequest disabledloginRequest=new DisabledLoginRequest(userID, userPassword, responseListener);
                RequestQueue queue= Volley.newRequestQueue(DisabledLoginActivity.this);
                queue.add(disabledloginRequest);
            }

        });
    }
}
