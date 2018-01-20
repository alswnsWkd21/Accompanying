package smc.minjoon.accompanying.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import smc.minjoon.accompanying.R;


public class RegisterActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean validate =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final CheckBox checkService = (CheckBox)findViewById(R.id.checkService);
        final CheckBox checkPrivacy = (CheckBox)findViewById(R.id.checkPrivacy);
        final EditText idText=(EditText)findViewById(R.id.idText);
        final EditText passwordText=(EditText)findViewById(R.id.passwordText);
        final EditText nameText=(EditText)findViewById(R.id.nameText);
        final EditText phoneText=(EditText)findViewById(R.id.phoneText);
        Button registerBtn=(Button)findViewById(R.id.registerBtn);
        Button service=(Button)findViewById(R.id.service);
        Button privacy=(Button)findViewById(R.id.privacy);

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent=new Intent(RegisterActivity.this,ServiceActivity.class);
                RegisterActivity.this.startActivity(serviceIntent);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent privacyIntent=new Intent(RegisterActivity.this,PrivacyActivity.class);
                RegisterActivity.this.startActivity(privacyIntent);
            }
        });
        final Button validateBtn=(Button) findViewById(R.id.validateBtn);
        validateBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String helperID=idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(helperID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("아이디가 입력되지 않았습니다")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){

                            try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        dialog=builder.setMessage("사용할 수 있는 아이디입니다")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();
                                        idText.setEnabled(false);
                                        validate=true;
                                        idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                        validateBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                    }
                                    else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        dialog= builder.setMessage("사용할 수 없는 아이디입니다..")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog .show();
                                    }
                                }

                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    };

                    ValidateRequest validateRequest = new ValidateRequest(helperID, responseListener);
                    RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(validateRequest);
                }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String helperID = idText.getText().toString();
                final String helperPassword = passwordText.getText().toString();
                final String helperName = nameText.getText().toString();
                final String helperPhone = phoneText.getText().toString();
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                final String helperToken = FirebaseInstanceId.getInstance().getToken();
                Log.v("test", helperToken);


                if(!validate){
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("아이디 중복 검사를 해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (checkPrivacy.isChecked() == false || checkService.isChecked() == false || helperID.equals("") || helperPassword.equals("") || helperName.equals("") || helperPhone.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("약관에 동의를 하지 않았거나 입력되지 않은 내용이 있습니다.")
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원 등록에 성공했습니다.")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    RegisterActivity.this.startActivity(intent);
                                                }
                                            })
                                            .create()
                                            .show();

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원 등록에 실패했습니다.")
                                            .setNegativeButton("다시 시도", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .create()
                                            .show();
                                }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                RegisterRequest registerRequest = new RegisterRequest(helperID, helperPassword,helperName, helperPhone,helperToken, responseListener );
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }

//    @Override
//    protected void onStop(){
//        super.onStop();
//        if(dialog !=null);
//        {
//            dialog.dismiss();
//            dialog=null;
//        }
//    }
}
