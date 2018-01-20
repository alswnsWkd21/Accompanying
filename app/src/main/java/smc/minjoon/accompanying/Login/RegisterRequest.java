package smc.minjoon.accompanying.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SY on 2017-12-10.
 */

public class RegisterRequest extends StringRequest {

    final static private String URL ="http://syoung1394.cafe24.com/HelperRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String helperID, String helperPassword, String helperName, String helperPhone, String helperToken ,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("helperID", helperID);
        parameters.put("helperPassword", helperPassword);
        parameters.put("helperName", helperName);
        parameters.put("helperPhone", helperPhone);
        parameters.put("helperToken", helperToken);
    }



    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
