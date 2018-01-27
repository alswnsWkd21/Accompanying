package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserAccompany;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-24.
 */


public class AccompanyTimeRequest extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/AccompanyTimeRequest.php";
    private Map<String, String> parameters;

    public AccompanyTimeRequest(String accompanyID, String time, String startTime, String endTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("accompanyID", accompanyID);
        parameters.put("accompanyWorkTime", time);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}