package smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-20.
 */

public class AccompanyChattingRequest extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/AccompanyChattingRequest.php";
    private Map<String, String> parameters;

    public AccompanyChattingRequest(String accompanyID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("accompanyID", accompanyID);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}