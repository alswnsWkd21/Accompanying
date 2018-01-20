package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-20.
 */

public class ChattingRequest extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/ChattingRequest.php";
    private Map<String, String> parameters;

    public ChattingRequest(String helperID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("helperID", helperID);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}