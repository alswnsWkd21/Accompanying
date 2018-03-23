package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-17.
 */

public class LocationRegisterRequest extends StringRequest{
    final static private String URL ="http://syoung1394.cafe24.com/LocationUpdate.php";
    private Map<String, String> parameters;

    public LocationRegisterRequest(String helperID, double helperLat, double helperlong, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("helperID", helperID);
        parameters.put("helperLat", String.valueOf(helperLat));
        parameters.put("helperlong", String.valueOf(helperlong));
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
