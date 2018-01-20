package smc.minjoon.accompanying.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import smc.minjoon.accompanying.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText=(EditText)findViewById(R.id.idText);
        final EditText passwordText=(EditText)findViewById(R.id.passwordText);
        final Button loginBtn=(Button)findViewById(R.id.loginBtn);
        final TextView registerBtn=(TextView)findViewById(R.id.registerBtn);
        //       여기에서 세션값을 확인
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        final SessionManager sessionmanager = new SessionManager(LoginActivity.this);
//        sessionmanager.checkLogin("helper","helper");
// getKeyKind 와 helper 문자 비교할 때 ==는 안될 때가 있어서 equals를 썻다.
        if(sessionmanager.getKeyKind() !=null && !sessionmanager.getKeyKind().equals("")){
            if(sessionmanager.getKeyKind().equals("helper") && sessionmanager.isLoggedIn()){
                Intent i = new Intent(LoginActivity.this, AbledMainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// Add new Flag to start new Activity

// Staring Login Activity
                startActivity(i);
            }else{
                if(sessionmanager.getKeyKind().equals("user") && sessionmanager.isLoggedIn()){
                    Log.v("kind2",sessionmanager.getKeyKind());
                    Log.v("log2",String.valueOf(sessionmanager.isLoggedIn()));

                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sessionmanager.logoutUser();
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("이미 장애인으로 로그인되어있어 장애인 로그인은 로그아웃됩니다.");
                    alert.setCancelable(false);
                    alert.show();


                }
        }

    }




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                final String helperID=idText.getText().toString();
                final String helperPassword=passwordText.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try{

                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            Log.v("success", String.valueOf(success));
                            if(success){
                                String helperID=jsonResponse.getString("helperID");
                                String helperName=jsonResponse.getString("helperName");
                                String helperToken= jsonResponse.getString("helperToken");
                                String helperPhone=jsonResponse.getString("helperPhone");
                                Log.v("name", helperName);
                                Log.v("ID", helperID);
                                sessionmanager.editor.putString("name", helperName);
                                sessionmanager.editor.putString("id", helperID);
                                sessionmanager.editor.putString("kind", "helper");
                                sessionmanager.editor.putBoolean("IsLoggedIn", true);
                                sessionmanager.editor.putString("phone",helperPhone );
                                sessionmanager.editor.commit();

//                                sessionmanager.createLoginSession(helperName, helperID, helperToken,"helper" )
                                Intent intent=new Intent(LoginActivity.this,AbledMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                LoginActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
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
                LoginRequest loginRequest=new LoginRequest(helperID, helperPassword, responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }

        });
    }

}
