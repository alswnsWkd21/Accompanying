package smc.minjoon.accompanying.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-18.
 */

public class SessionRequest extends StringRequest {

    final static private String URL ="http://syoung1394.cafe24.com/SessionUpdate.php";
    private Map<String, String> parameters;

    public SessionRequest(String kind, String id,  String token, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("kind", kind);
        parameters.put("id", id);
        parameters.put("token", token);

    }



    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}