package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserAccompany;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-18.
 */

public class AccompanyRequest  extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/Accompany.php";
    private Map<String, String> parameters;

    public AccompanyRequest(String userid,String userName,String userPhone, String userPlace, String userWork, String userLat, String userlong ,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("userPlace", userPlace);
        parameters.put("userWork", userWork);
        parameters.put("userLat", userLat);
        parameters.put("userlong", userlong);
        parameters.put("userName", userName);
        parameters.put("userPhone", userPhone);
        parameters.put("u_id", userid);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
