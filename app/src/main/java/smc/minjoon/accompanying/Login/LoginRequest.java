package smc.minjoon.accompanying.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SY on 2017-12-10.
 */

public class LoginRequest extends StringRequest {

    final static private String URL ="http://syoung1394.cafe24.com/HelperLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String helperID, String helperPassword,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("helperID", helperID);
        parameters.put("helperPassword", helperPassword);

    }



    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}


